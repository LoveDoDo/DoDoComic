package com.dodo.xinyue.conan.module.thumb;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dodo.xinyue.conan.R;
import com.dodo.xinyue.conan.R2;
import com.dodo.xinyue.conan.main.index.bean.IndexBean;
import com.dodo.xinyue.conan.module.thumb.adapter.ThumbPreviewAdapter;
import com.dodo.xinyue.conan.module.thumb.data.ThumbHandlerThread;
import com.dodo.xinyue.conan.module.thumb.data.ThumbPreviewDataConverter;
import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.net.RestClient;
import com.dodo.xinyue.core.util.dimen.DimenUtil;
import com.dodo.xinyue.core.util.log.DoDoLogger;
import com.dodo.xinyue.core.util.timer.BaseTimerTask;
import com.dodo.xinyue.core.util.timer.ITimerListener;
import com.eftimoff.androipathview.PathView;
import com.joanzapata.iconify.widget.IconTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 缩略图快速预览
 *
 * @author DoDo
 * @date 2018/9/18
 */
public class ThumbPreviewDelegate extends DoDoDelegate implements Handler.Callback, ITimerListener {

    //TODO 自定义view调用invalidate()有时不走onDraw(),原因是需要先设置一个背景(颜色或图)。
    //参考：https://www.jianshu.com/p/f7edabc69d1d

    private static final String TAG = "ThumbPreviewDelegate";
    private static final String ARGS_BEAN = "args_bean";
    private static final int TIME_ONE_HOUR_SECOND = 60 * 60;//一小时的秒数
    private static final int REQUEST_ING = 0;//状态_正在请求
    private static final int REQUEST_SUCCESS = 1;//状态_请求成功
    private static final int REQUEST_FAILURE = 2;//状态_请求失败

    private SimpleDateFormat mLargeDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    private SimpleDateFormat mSmallDateFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());//不足一小时
    private SimpleDateFormat mDataFormat = null;

    private int mImgNum = 0;//图片总张数

    private ThumbPreviewAdapter mAdapter = null;
    private ThumbPreviewDataConverter mDataConverter = null;
    private GridLayoutManager mLayoutManager = null;

    private String mRequestUrl = null;
    private int mTotleTime = 0;//影片时长 单位秒
    private String mPreviewImageUrl = null;
    private Handler mUIHandler = new Handler(this);
    private ThumbHandlerThread mThumbHandlerThread = null;
    private boolean mDisableSeekBarAutoScroll = false;

    private boolean mAutoPlay = false;
    private String mTitle = null;

    private boolean mAnimDone = false;
    private int mRequestStatus = REQUEST_ING;
    private boolean mSkipAnim = false;

    private AnimatorSet mAnimControl = null;

    private RestClient mRestClient = null;

    private Timer mTimer = null;
    private int mAutoMills = 8;//自动翻页的秒数

    private String mLanguage = null;
    private String mType = null;

    @BindView(R2.id.sb)
    SeekBar mSeekBar = null;
    @BindView(R2.id.tvCurrentTime)
    TextView mTvStartTime = null;
    @BindView(R2.id.tvTotleTime)
    TextView mTvTotleTime = null;
    @BindView(R2.id.llSeerBarContainer)
    LinearLayout mSeekBarContainer = null;
    @BindView(R2.id.rv)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.ivBackground)
    ImageView mIvBackground = null;
    @BindView(R2.id.pathView)
    PathView mPathView = null;
    @BindView(R2.id.tvIcon)
    IconTextView mTvIcon = null;
    @BindView(R2.id.tvTitle)
    TextView mTvTitle = null;
    @BindView(R2.id.llPlayContainer)
    LinearLayout mPlayContainer = null;
    @BindView(R2.id.tvPlayAndPause)
    IconTextView mTvAutoPlay = null;
    @BindView(R2.id.rlStatusContainer)
    RelativeLayout mStatusContainer = null;
    @BindView(R2.id.llLoadingContainer)
    LinearLayout mLoadingContainer = null;
    @BindView(R2.id.llLoadSuccessContainer)
    LinearLayout mLoadSuccessContainer = null;
    @BindView(R2.id.llLoadFailureContainer)
    LinearLayout mLoadFailureContainer = null;
    @BindView(R2.id.tvLanguage)
    TextView mTvLanguage = null;
    @BindView(R2.id.tvType)
    TextView mTvType = null;

    @OnClick(R2.id.tvPlay)
    void onTvPlayClicked() {
        ToastUtils.showShort("播放");
    }

    @OnClick(R2.id.tvBack)
    void onTvBackClicked() {
        pop();
    }

    @OnClick(R2.id.tvSkipAnim)
    void onSkipAnimClicked() {
        mSkipAnim = true;
        mLoadSuccessContainer.setVisibility(View.GONE);
        onPathViewAnimDone();
    }

    @OnClick(R2.id.tvRetry)
    void onRetryClicked() {
        mLoadFailureContainer.setVisibility(View.GONE);
        mLoadingContainer.setVisibility(View.VISIBLE);
        requestData();
    }

    @OnClick(R2.id.tvSpeedBack)
    void onSpeedBackClicked() {
        stopAutoPlay();

        if (mRecyclerView == null) {
            return;
        }
        int position = mLayoutManager.findFirstVisibleItemPosition();
        if (position > 0) {
            mRecyclerView.scrollToPosition(position - 1);
        }
    }

    @OnClick(R2.id.tvSpeedGo)
    void onSpeedGoClicked() {
        stopAutoPlay();

        if (mRecyclerView == null) {
            return;
        }
        int position = mLayoutManager.findFirstVisibleItemPosition();
        if (position + 1 < mAdapter.getItemCount()) {
            mRecyclerView.scrollToPosition(position + 1);
        }
    }

    @OnClick(R2.id.tvPlayAndPause)
    void onPlayAndPauseClicked() {
        if (mAutoPlay) {
            stopAutoPlay();
        } else {
            startAutoPlay();
        }
    }

    public static ThumbPreviewDelegate create(IndexBean bean) {
        final Bundle args = new Bundle();
        args.putSerializable(ARGS_BEAN, bean);
        final ThumbPreviewDelegate delegate = new ThumbPreviewDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_thumb_preview;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args == null) {
            return;
        }
        final IndexBean bean = (IndexBean) args.getSerializable(ARGS_BEAN);
        if (bean == null) {
            return;
        }
        mRequestUrl = "https://cache.video.iqiyi.com/jp/vi/" +
                bean.getTvQipuId() + "/" + bean.getVid() + "/";

        mTitle = bean.getTitle();
        mLanguage = bean.getLanguage();
        mType = bean.getType();

        mLargeDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));//不加这句的话，小时显示不对
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initSeekBar();
        initRecyclerView();

//        GlideApp.with(this)
//                .load(R.drawable.yueyue)
////                .centerCrop()
////                .transform(new BlurTransformation(15, 6))
//                .into(mIvBackground);

        mPathView.setFillAfter(false);
        mPathView.setSvgResource(R.raw.tv);

        mTvTitle.setText(mTitle);
        mTvLanguage.setText(mLanguage);
        mTvType.setText(mType);

    }

    private void initSeekBar() {
        /**
         * SeekBar滑动监听
         */
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            private int lastPosition = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int shang = progress / 10;
                int yushu = progress % 10;//余数
                int position;
                if (yushu == 0) {
                    if (shang == 0) {
                        //说明progress==0
                        //第一张图
                        position = 0;

                    } else {
                        //商是多少，就是第几张图
                        position = shang;
                    }
                } else {
                    //商+1就是第几张图
                    position = shang + 1;
                }
                mTvStartTime.setText(computeTime(progress));
                if (fromUser) {
                    if (position != lastPosition) {
                        if (position >= mAdapter.getItemCount()) {
                            //最后一张的position超出了itemCount
                            position--;
                        }
                        mRecyclerView.scrollToPosition(position);
                        lastPosition = position;
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mDisableSeekBarAutoScroll = true;
                DoDoLogger.d(TAG, "开始滑动");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mDisableSeekBarAutoScroll = false;
                DoDoLogger.d(TAG, "停止滑动");
            }
        });
        /**
         * 增加SeekBar的触摸范围
         */
        mSeekBarContainer.setOnTouchListener(new View.OnTouchListener() {

            private boolean buhuo = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        buhuo = true;
                        mDisableSeekBarAutoScroll = true;
                        event.offsetLocation(-mSeekBar.getLeft(), 0);
                        return mSeekBar.onTouchEvent(event);
                    case MotionEvent.ACTION_MOVE:
                        event.offsetLocation(-mSeekBar.getLeft(), 0);
                        return mSeekBar.onTouchEvent(event);
                    case MotionEvent.ACTION_UP:
                        buhuo = false;
                        mDisableSeekBarAutoScroll = false;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (buhuo) {
                            event.offsetLocation(-mSeekBar.getLeft(), 0);
                            return mSeekBar.onTouchEvent(event);
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initRecyclerView() {
        mLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //滑动后使item停靠在正中间
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        //优化
        mRecyclerView.setHasFixedSize(true);
        //取消adapter.notifyItemChanged()方法自带的默认动画效果
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (!mDisableSeekBarAutoScroll) {
                    if (mSeekBar == null) {
                        return;
                    }
                    int itemPosition = mLayoutManager.findFirstVisibleItemPosition();
                    mSeekBar.setProgress(itemPosition * 10);
                    DoDoLogger.d("itemPosition" + itemPosition);
                }
            }
        });
        mAdapter = ThumbPreviewAdapter.create(new ArrayList<>(), this);
        mAdapter.bindToRecyclerView(mRecyclerView);//TODO 这里忘记绑定adapter了，找bug找了一个小时...
        //翻页
        mRecyclerView.setOnTouchListener(new View.OnTouchListener() {

            float xDown, xUp;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        xDown = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        xUp = event.getX();
                        if (xDown == xUp) {
                            //点击
                            int viewWidth = v.getWidth();
                            int position = mLayoutManager.findFirstVisibleItemPosition();
                            if (xDown <= viewWidth / 2) {
                                if (position > 0) {
                                    mRecyclerView.scrollToPosition(position - 1);
                                }
                            } else {
                                //右边
                                if (position + 1 < mAdapter.getItemCount()) {
                                    mRecyclerView.scrollToPosition(position + 1);
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);

        mLoadingContainer.setVisibility(View.VISIBLE);

        mPathView.getPathAnimator()
                .interpolator(new LinearInterpolator())
                .duration(1314 + 520)
                .listenerEnd(new PathView.AnimatorBuilder.ListenerEnd() {
                    @Override
                    public void onAnimationEnd() {
                        mAnimDone = true;
                        if (!mSkipAnim) {
                            if (mTvIcon == null) {
                                return;
                            }
                            onPathViewAnimDone();
                        }
                    }
                })
                .start();

        requestData();

    }

    private void onPathViewAnimDone() {
        if (mRequestStatus == REQUEST_SUCCESS) {
            mLoadSuccessContainer.setVisibility(View.GONE);
        }
        final ObjectAnimator pathViewAnim = ObjectAnimator.ofFloat(mPathView, "alpha", 1, 0);
        final ObjectAnimator tvIconAnim = ObjectAnimator.ofFloat(mTvIcon, "alpha", 0, 1);
        final ObjectAnimator rvAnim = ObjectAnimator.ofFloat(mRecyclerView, "alpha", 0, 1);
        mAnimControl = new AnimatorSet();
        mAnimControl.setDuration(300);
        mAnimControl.setInterpolator(new LinearInterpolator());
        mAnimControl.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mPathView == null) {
                    return;
                }
                mPathView.setVisibility(View.INVISIBLE);
                if (mRequestStatus == REQUEST_SUCCESS) {
                    mLoadSuccessContainer.setVisibility(View.GONE);

                    mSeekBarContainer.setVisibility(View.VISIBLE);
                    mPlayContainer.setVisibility(View.VISIBLE);
                }
            }
        });
        mAnimControl.play(pathViewAnim).with(tvIconAnim).with(rvAnim);
        mAnimControl.start();
    }

    private void requestData() {
        mRequestStatus = REQUEST_ING;

        mRestClient = RestClient.builder()
                .url(mRequestUrl)
                .success(this::handleData)
                .failure(() -> {
                    mRequestStatus = REQUEST_FAILURE;
                    mLoadingContainer.setVisibility(View.GONE);
                    mLoadFailureContainer.setVisibility(View.VISIBLE);
                })
                .error((code, msg) -> {
                    mRequestStatus = REQUEST_FAILURE;
                    mLoadingContainer.setVisibility(View.GONE);
                    mLoadFailureContainer.setVisibility(View.VISIBLE);
                })
                .build();

        mRestClient.get();
    }

    private void handleData(String json) {
        String markStr = "var tvInfoJs=";
        int fromIndex = json.indexOf(markStr) + markStr.length();
        JSONObject data = JSON.parseObject(json.substring(fromIndex));
        mTotleTime = data.getIntValue("plg");
        mPreviewImageUrl = data.getString("previewImageUrl");

        if (mTotleTime <= 0) {
            return;
        }
        if (TextUtils.isEmpty(mPreviewImageUrl)) {
            return;
        }
        if (mUIHandler == null) {
            return;
        }
        mThumbHandlerThread = ThumbHandlerThread.builder()
                .totleTime(mTotleTime)
                .previewImgUrl(mPreviewImageUrl)
                .handler(mUIHandler)
                .build();
        mThumbHandlerThread.start();//因为HandlerThread本身就是个线程,所以使用start启动

    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg == null || msg.getData() == null) {
            return false;
        }

        final String previewImgUrl = msg.getData().getString(ThumbHandlerThread.KEY_PREVIEW_URL);

        switch (msg.what) {
            case ThumbHandlerThread.STATUS_SUCCESS:

                if (mSeekBar == null) {
                    return true;
                }

                final List<String> previewImgPaths = msg.getData().getStringArrayList(ThumbHandlerThread.KEY_PREVIEW_PATHS);
                final int imgNum = msg.getData().getInt(ThumbHandlerThread.KEY_IMG_NUM);
                mDataConverter = new ThumbPreviewDataConverter();
                mDataConverter.setmPreviewImgPaths(previewImgPaths);
                mDataConverter.setImgNum(imgNum);
                mAdapter.setNewData(mDataConverter.convert());

                mTvTotleTime.setText(computeTime(mTotleTime));
                mSeekBar.setMax(mTotleTime);
                mSeekBar.setProgress(0);

//                mRecyclerView.setVisibility(View.VISIBLE);
//                mSeekBarContainer.setVisibility(View.VISIBLE);

                mRequestStatus = REQUEST_SUCCESS;

                mLoadingContainer.setVisibility(View.GONE);
                if (!mAnimDone) {
                    mLoadSuccessContainer.setVisibility(View.VISIBLE);
                }

                if (mAnimControl != null) {
                    if (!mAnimControl.isRunning()) {
                        mSeekBarContainer.setVisibility(View.VISIBLE);
                        mPlayContainer.setVisibility(View.VISIBLE);
                    }
                }

                break;
            case ThumbHandlerThread.STATUS_FAILURE:
                mRequestStatus = REQUEST_FAILURE;
                mLoadingContainer.setVisibility(View.GONE);
                mLoadFailureContainer.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 时间到文本
     *
     * @param time 时间 单位秒
     * @return
     */
    private String computeTime(int time) {
        if (mDataFormat == null) {
            if (time >= TIME_ONE_HOUR_SECOND) {
                mDataFormat = mLargeDateFormat;
            } else {
                mDataFormat = mSmallDateFormat;
            }
        }
        return TimeUtils.millis2String(time * 1000, mDataFormat);
    }

    private void startAutoPlay() {
        if (mAutoPlay) {
            return;
        }
        mAutoPlay = true;
        mTvAutoPlay.setText("{icon-pause}");
        mTvAutoPlay.setPadding(DimenUtil.dp2px(1), 0, 0, 0);

        mTimer = new Timer();
        final BaseTimerTask timerTask = new BaseTimerTask(this);
        /**
         * 参数二：0立即执行 >0延时xx毫秒后执行
         * 参数三：空=执行一次 非空=时钟周期，延时xx毫秒后再次执行
         */
        mTimer.schedule(timerTask, 0, 1314);

    }

    private void stopAutoPlay() {
        if (!mAutoPlay) {
            return;
        }
        mAutoPlay = false;
        mTvAutoPlay.setText("{icon-play}");
        mTvAutoPlay.setPadding(DimenUtil.dp2px(4), 0, 0, 0);

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

    }

    @Override
    public void onTimer() {
        DoDo.getHandler().post(() -> {
            if (mRecyclerView == null) {
                return;
            }
            int position = mLayoutManager.findFirstVisibleItemPosition();
            if (position + 1 < mAdapter.getItemCount()) {
                mRecyclerView.scrollToPosition(position + 1);
            } else {
                stopAutoPlay();
            }
        });

    }

    /**
     * 获取decoder
     *
     * @param imgIndex 大图索引
     * @return
     */
    public final BitmapRegionDecoder getBitmapRegionDecoder(int imgIndex) {
        return mDataConverter.getBitmapRegionDecoder(imgIndex);
    }

    /**
     * 获取options
     *
     * @return
     */
    public final BitmapFactory.Options getBitmapOptions() {
        return mDataConverter.getBitmapOptions();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRestClient != null) {
            mRestClient.cancle();
        }
        mUIHandler.removeCallbacksAndMessages(null);
        if (mThumbHandlerThread != null) {
            mThumbHandlerThread.quitSafely();
        }
        mUIHandler = null;
        if (mAnimControl != null && mAnimControl.isRunning()) {
            mAnimControl.cancel();
            mAnimControl = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }
}
