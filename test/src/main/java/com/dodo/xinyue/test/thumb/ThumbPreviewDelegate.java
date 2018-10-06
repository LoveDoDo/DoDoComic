package com.dodo.xinyue.test.thumb;

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
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.TimeUtils;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.net.RestClient;
import com.dodo.xinyue.core.util.dimen.DimenUtil;
import com.dodo.xinyue.core.util.log.DoDoLogger;
import com.dodo.xinyue.test.R;
import com.dodo.xinyue.test.R2;
import com.dodo.xinyue.test.thumb.adapter.ThumbPreviewAdapter;
import com.dodo.xinyue.test.thumb.bean.TestBean;
import com.dodo.xinyue.test.thumb.data.ThumbHandlerThread;
import com.dodo.xinyue.test.thumb.data.ThumbPreviewDataConverter;
import com.dodo.xinyue.core.ui.rcLayout.RCRelativeLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;

/**
 * 缩略图快速预览
 *
 * @author DoDo
 * @date 2018/9/18
 */
public class ThumbPreviewDelegate extends DoDoDelegate implements Handler.Callback {

    private static final String TAG = "ThumbPreviewDelegate";
    private static final String ARGS_BEAN = "args_bean";
    //    private static final String mImgUrl = "http://preimage1.iqiyipic.com/preimage/20150330/47/4f/v_107545836_m_611_160_90_1.jpg";
    //    private static final String mImgUrl = "http://preimage2.iqiyipic.com/preimage/20150512/34/be/v_108667253_m_611_160_90_1.jpg";
//    private static final String mImgUrl = "https://preimage3.iqiyipic.com/preimage/20170322/9f/00/v_108642150_m_611_m27_160_90_1.jpg?PTID=01010021010000000000";
//    private static final String mImgUrl = "http://preimage2.iqiyipic.com/preimage/20170323/6a/66/v_108642150_m_612_220_124_1.jpg";
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());
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

    @BindView(R2.id.sb)
    SeekBar mSeekBar = null;
    @BindView(R2.id.current_time)
    TextView mTvStartTime = null;
    @BindView(R2.id.totle_time)
    TextView mTvTotleTime = null;
    @BindView(R2.id.sb_parent)
    LinearLayout mSbParent = null;
    @BindView(R2.id.rc_layout)
    RCRelativeLayout mRCLayout = null;
    @BindView(R2.id.sb_zoom)
    SeekBar mmSeekBarZoom = null;//动态缩放
    @BindView(R2.id.sb_radio)
    SeekBar mmSeekBarRadio = null;//动态改变圆角

    @BindView(R2.id.rv)
    RecyclerView mRecyclerView = null;

    public static ThumbPreviewDelegate create(TestBean bean) {
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
        final TestBean bean = (TestBean) args.getSerializable(ARGS_BEAN);
        if (bean == null) {
            return;
        }
        mRequestUrl = "https://cache.video.iqiyi.com/jp/vi/" +
                bean.getTvQipuId() + "/" + bean.getVid() + "/";
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initSeekBar();
        initSeekBarZoom();
        initSeekBarRadiu();
        initmRCLayout();
        initRecyclerView();

        //缩放动画
//        YoYo.with(Techniques.ZoomIn)
//                .duration(800)
//                .playOn(mRCLayout);
    }

    private void initSeekBarRadiu() {
        mmSeekBarRadio.setEnabled(true);//正式环境默认为false
        mmSeekBarRadio.setMax(90);
        mmSeekBarRadio.setProgress(DimenUtil.dp2px(18));

        /**
         * SeekBar滑动监听
         */
        mmSeekBarRadio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //TODO 自定义view调用invalidate()有时不走onDraw(),原因是需要先设置一个背景(颜色或图)。
                //参考：https://www.jianshu.com/p/f7edabc69d1d
                mRCLayout.setRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                DoDoLogger.d(TAG, "开始滑动");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                DoDoLogger.d(TAG, "停止滑动");
            }
        });
    }

    private void initSeekBarZoom() {
        mmSeekBarZoom.setEnabled(true);//正式环境默认为false
        mmSeekBarZoom.setMax(DimenUtil.getScreenWidth() - DimenUtil.dp2px(30 * 2) - DimenUtil.dp2px(160));//限定最大宽
        mmSeekBarZoom.setProgress(0);

        /**
         * SeekBar滑动监听
         */
        mmSeekBarZoom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ViewGroup.LayoutParams params = mRCLayout.getLayoutParams();
                int width = DimenUtil.dp2px(160) + progress;
                int height = 90 * width / 160;
                params.width = width;
                params.height = height;
                mRCLayout.setLayoutParams(params);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                DoDoLogger.d(TAG, "开始滑动");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                DoDoLogger.d(TAG, "停止滑动");
            }
        });
    }

    private void initmRCLayout() {
        mRCLayout.setOnTouchListener(new View.OnTouchListener() {
            private boolean buhuo = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        buhuo = true;
                        return mRecyclerView.onTouchEvent(event);
                    case MotionEvent.ACTION_MOVE:
                        return mRecyclerView.onTouchEvent(event);
                    case MotionEvent.ACTION_UP:
                        buhuo = false;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        if (buhuo) {
                            return mRecyclerView.onTouchEvent(event);
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
                    int itemPosition = mLayoutManager.findFirstVisibleItemPosition();
                    mSeekBar.setProgress(itemPosition * 10);
                    DoDoLogger.d("itemPosition" + itemPosition);
                }
            }
        });
        mAdapter = ThumbPreviewAdapter.create(new ArrayList<>(), this);
        mAdapter.bindToRecyclerView(mRecyclerView);//TODO 这里忘记绑定adapter了，找bug找了一个小时...
    }

    private void initSeekBar() {
        mSeekBar.setEnabled(false);
//        mImgNum = (int) (mTestTime / 10);
//        mSeekBar.setMax((int) mTestTime);
//        mSeekBar.setProgress(0);
//
//        mTvTotleTime.setText(computeTime((int) mTestTime));

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
        mSbParent.setOnTouchListener(new View.OnTouchListener() {

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

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .url(mRequestUrl)
                .success(this::handleData)
                .build()
                .get();
        //...
        //拿到ImgUrl之后
//        List<String> urls = new ArrayList<>();
//        urls.add(mImgUrl);
//        handleData(urls);
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
        mThumbHandlerThread = ThumbHandlerThread.builder()
                .totleTime(mTotleTime)
                .previewImgUrl(mPreviewImageUrl)
                .handler(mUIHandler)
                .build();
        mThumbHandlerThread.start();//因为HandlerThread本身就是个线程,所以使用start启动

    }

    /**
     * 时间到文本
     *
     * @param time 时间 单位秒
     * @return
     */
    private String computeTime(int time) {
        return TimeUtils.millis2String(time * 1000, mSimpleDateFormat);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg == null || msg.getData() == null) {
            return false;
        }

        final String previewImgUrl = msg.getData().getString(ThumbHandlerThread.KEY_PREVIEW_URL);

        switch (msg.what) {
            case ThumbHandlerThread.STATUS_SUCCESS:
                final List<String> previewImgPaths = msg.getData().getStringArrayList(ThumbHandlerThread.KEY_PREVIEW_PATHS);
                final int imgNum = msg.getData().getInt(ThumbHandlerThread.KEY_IMG_NUM);
                mDataConverter = new ThumbPreviewDataConverter();
                mDataConverter.setmPreviewImgPaths(previewImgPaths);
                mDataConverter.setImgNum(imgNum);
                mAdapter.setNewData(mDataConverter.convert());

//                mImgNum = (int) (mTestTime / 10);
                mSeekBar.setEnabled(true);
                mSeekBar.setMax(mTotleTime);
                mSeekBar.setProgress(0);

                mTvTotleTime.setText(computeTime(mTotleTime));
                break;
            case ThumbHandlerThread.STATUS_FAILURE:
                break;
            default:
                break;
        }
        return true;
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
        if (mThumbHandlerThread != null) {
            mThumbHandlerThread.quitSafely();
        }
        mUIHandler.removeCallbacksAndMessages(null);
        mUIHandler = null;
    }
}
