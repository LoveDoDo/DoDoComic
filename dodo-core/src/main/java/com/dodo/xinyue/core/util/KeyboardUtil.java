package com.dodo.xinyue.core.util;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

import com.dodo.xinyue.core.app.DoDo;

/**
 * KeyboardUtil
 * 判断软键盘开启/关闭状态
 *
 * @author DoDo
 * @date 2019/1/13
 */
public class KeyboardUtil {

    public interface OnSoftKeyboardChangeListener {
        void onSoftKeyBoardChange(int softKeyboardHeight, boolean visible);
    }

    /**
     * 监听软键盘高度和状态
     */
    public static ViewTreeObserver.OnGlobalLayoutListener observeSoftKeyboard(final OnSoftKeyboardChangeListener listener) {
        final View decorView = DoDo.getActivity().getWindow().getDecorView();
        ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            int previousKeyboardHeight = -1;
            Rect rect = new Rect();
            boolean lastVisibleState = false;

            @Override
            public void onGlobalLayout() {
                rect.setEmpty();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHeight = rect.bottom - rect.top;
                //考虑上状态栏的高度
                int height = decorView.getHeight() - rect.top;
                int keyboardHeight = height - displayHeight;
                if (previousKeyboardHeight != keyboardHeight) {
                    boolean hide = (double) displayHeight / height > 0.8;
                    if (hide != lastVisibleState) {
                        listener.onSoftKeyBoardChange(keyboardHeight, !hide);
                        lastVisibleState = hide;
                    }
                }
                previousKeyboardHeight = height;
            }
        };
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        return onGlobalLayoutListener;
    }

    public static void removeSoftKeyboardObserver(ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (listener == null) {
            return;
        }
        DoDo.getActivity().getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }

}
