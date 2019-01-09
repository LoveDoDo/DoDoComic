package com.dodo.xinyue.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dodo.xinyue.core.delegates.DoDoDelegate;

import butterknife.OnClick;

/**
 * TestDelegate
 *
 * @author DoDo
 * @date 2018/9/17
 */
public class TestDelegate extends DoDoDelegate {

    @OnClick(R2.id.tvTest)
    void onTvTestClicked() {

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_test;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    public static TestDelegate create() {
        return new TestDelegate();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
//        RestClient.builder()
//                .url("http://iface2.iqiyi.com/video/3.0/v_play?app_k=3179f25bc69e815ad828327ccf10c539&app_v=9.8.5&platform_id=10&dev_os=8.1.0&dev_ua=1807-A01&net_sts=1&qyid=868359031985428&cupid_v=3.29.004&psp_uid=2000197906&psp_cki=25c2jm1nb7Sm11dSEUslAK5PXXrglb5f3FjXQ1r9i09CJ2U2CObBuy9qbG3iLm2MXKuB94c&imei=257d0e754bedacbd49f44950d79c00b7&aid=1663ba4374a5c8b7&mac=04:50:DA:12:F9:24&scrn_scale=3&secure_p=GPhone&secure_v=1&core=1&api_v=7.7&profile=%7B%22group%22%3A%222%22%2C%22counter%22%3A2%7D&unlog_sub=0&cust_count=&dev_hw=%7B%22cpu%22%3A0%2C%22gpu%22%3A%22%22%2C%22mem%22%3A%22451.5MB%22%7D&net_ip=%7B%22country%22%3A%22%E4%B8%AD%E5%9B%BD%22%2C%22province%22%3A%22%E5%8C%97%E4%BA%AC%22%2C%22city%22%3A%22%E5%8C%97%E4%BA%AC%22%2C%22cc%22%3A%22%E8%81%94%E9%80%9A%22%2C%22area%22%3A%22%E5%8D%8E%E5%8C%97%22%2C%22timeout%22%3A0%2C%22respcode%22%3A0%7D&scrn_sts=0&scrn_res=1080,2016&scrn_dpi=480&cupid_id=868359031985428&psp_vip=1&app_t=0&province_id=2007&service_filter=biz_qishow,biz_gamecenter,biz_appstore&service_sort=&app_p=gphone&album_id=202134201&tv_id=320572700&play_retry=0&content_type=1,2,3&secure_p=GPhone&play_res=16&play_core=1&sdk_v=73&sdk_build=73.0.985&dev_mem=5738&net_ip=%7B%22country%22%3A%22%E4%B8%AD%E5%9B%BD%22%2C%22province%22%3A%22%E5%8C%97%E4%BA%AC%22%2C%22city%22%3A%22%E5%8C%97%E4%BA%AC%22%2C%22cc%22%3A%22%E8%81%94%E9%80%9A%22%2C%22area%22%3A%22%E5%8D%8E%E5%8C%97%22%2C%22timeout%22%3A0%2C%22respcode%22%3A0%7D&ctl_dubi=0&src=0&adid=0&rate=512,16,8,&aqyid=868359031985428_1663ba4374a5c8b7_04Z50ZDAZ12ZF9Z24&pps=0&pu=2000197906&cupid_uid=868359031985428&psp_status=3&app_gv=&gps=116.412953,40.047552&lang=zh_CN&app_lm=cn&req_times=0&req_sn=1537380579404")
//
//                .success(response -> {
//                    ToastUtils.showLong("成功：" + response);
//                })
//                .failure(() -> {
//                    ToastUtils.showLong("失败");
//                })
//                .error((code, msg) -> {
//                    ToastUtils.showLong("错误：" + code + "," + msg);
//                })
//                .build()
//                .get();
    }
}
