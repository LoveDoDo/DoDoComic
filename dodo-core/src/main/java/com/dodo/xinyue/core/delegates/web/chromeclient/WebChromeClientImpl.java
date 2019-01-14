package com.dodo.xinyue.core.delegates.web.chromeclient;

import android.content.Intent;
import android.net.Uri;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.dodo.xinyue.core.app.DoDo;
import com.dodo.xinyue.core.delegates.web.WebConstants;

/**
 * WebChromeClient默认实现类
 *
 * @author DoDo
 * @date 2017/10/29
 */
public class WebChromeClientImpl extends WebChromeClient {

    /**
     * 拦截对话框，可以弹出自己的对话框
     */
    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    /**
     * 网页选择图片并上传
     */
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
//        ToastUtils.showShort("图片");
//        Intent intent = new Intent(
//                Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
//        DoDo.getActivity().startActivityForResult(intent, 2);
        ValueCallback<Uri[]> webViewUploadData = DoDo.getConfiguration(WebConstants.WEBVIEW_UPLOAD_DATA);
        if (webViewUploadData != null) {
            webViewUploadData.onReceiveValue(null);
        }
        webViewUploadData = filePathCallback;
        DoDo.getConfigurator().withCustomAttr(WebConstants.WEBVIEW_UPLOAD_DATA, webViewUploadData);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        if (fileChooserParams != null && fileChooserParams.getAcceptTypes() != null
                && fileChooserParams.getAcceptTypes().length > 0) {
            intent.setType(fileChooserParams.getAcceptTypes()[0]);
        } else {
            intent.setType("*/*");
        }
        DoDo.getActivity().startActivityForResult(Intent.createChooser(intent,
                "File Chooser"), WebConstants.REQUEST_CODE_WEBVIEW_UPLOAD);

        return true;
    }
}
