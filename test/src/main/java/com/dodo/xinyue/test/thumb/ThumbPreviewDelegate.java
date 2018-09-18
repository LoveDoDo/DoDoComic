package com.dodo.xinyue.test.thumb;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.dodo.xinyue.core.delegates.DoDoDelegate;
import com.dodo.xinyue.core.util.log.DoDoLogger;
import com.dodo.xinyue.test.R;
import com.dodo.xinyue.test.R2;
import com.dodo.xinyue.test.thumb.adapter.ThumbPreviewAdapter;
import com.dodo.xinyue.test.thumb.rcLayout.RCRelativeLayout;

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
public class ThumbPreviewDelegate extends DoDoDelegate {

    private static final String TAG = "ThumbPreviewDelegate";
    private static final String mImgUrl = "https://preimage3.iqiyipic.com/preimage/20170322/9f/00/v_108642150_m_611_m27_160_90_1.jpg?PTID=01010021010000000000";
    private long mTestTime = 100 * 6;//影片时长 单位秒
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());
    private int mImgNum = 0;//图片总张数

    private ThumbPreviewAdapter mAdapter = null;
    private ThumbPreviewDataConverter mDataConverter = null;
    private GridLayoutManager mLayoutManager = null;

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

    @BindView(R2.id.rv)
    RecyclerView mRecyclerView = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_thumb_preview;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initSeekBar();
        initmRCLayout();
        initRecyclerView();
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
                int itemPosition = mLayoutManager.findFirstVisibleItemPosition();
                mSeekBar.setProgress(itemPosition * 6);
            }
        });
        mAdapter = ThumbPreviewAdapter.create(new ArrayList<>(), this);
        mAdapter.bindToRecyclerView(mRecyclerView);//TODO 这里忘记绑定了，找bug找了一个小时...
    }

    private void initSeekBar() {
        mSeekBar.setEnabled(true);//正式环境默认为false
        mImgNum = (int) (mTestTime / 6);
        mSeekBar.setMax((int) mTestTime);
        mSeekBar.setProgress(0);

        mTvTotleTime.setText(computeTime((int) mTestTime));

        /**
         * SeekBar滑动监听
         */
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int shang = progress / 6;
                int yushu = progress % 6;//余数
                if (yushu == 0) {
                    if (shang == 0) {
                        //说明progress==0
                        //第一张图
                        mTvStartTime.setText(computeTime(0));
//                        mTvTotleTime.setText(String.valueOf(1));
                        if (fromUser) {
                            mRecyclerView.scrollToPosition(0);
                        }

                    } else {
                        //商是多少，就是第几张图
                        mTvStartTime.setText(computeTime(progress));
//                        mTvTotleTime.setText(String.valueOf(shang));
                        if (fromUser) {
                            mRecyclerView.scrollToPosition(shang);
                        }
                    }
                } else {
                    //商+1就是第几张图
                    mTvStartTime.setText(computeTime(progress));
//                    mTvTotleTime.setText(String.valueOf(shang + 1));
                    if (fromUser) {
                        mRecyclerView.scrollToPosition(shang + 1);
                    }
                }
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
                        event.offsetLocation(-mSeekBar.getLeft(), 0);
                        return mSeekBar.onTouchEvent(event);
                    case MotionEvent.ACTION_MOVE:
                        event.offsetLocation(-mSeekBar.getLeft(), 0);
                        return mSeekBar.onTouchEvent(event);
                    case MotionEvent.ACTION_UP:
                        buhuo = false;
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
        //...
        //拿到ImgUrl之后
        List<String> urls = new ArrayList<>();
        urls.add(mImgUrl);
        handleData(urls);


    }

    private void handleData(List<String> urls) {
        mDataConverter = new ThumbPreviewDataConverter();
        mDataConverter.setListener(()
                -> getProxyActivity().runOnUiThread(()
                -> mAdapter.setNewData(mDataConverter.getData())));
        mDataConverter.setImageUrls(urls).convert();

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
}
