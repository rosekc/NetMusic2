package com.android.netmusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.android.netmusic.activity.ChatActivity;
import com.android.netmusic.activity.MainActivity;
import com.android.netmusic.utils.InputMethodUtils;


/**
 * 点击软键盘区域以外自动关闭软键盘
 * @author xh2009cn
 */
public class CloseKeyboardOnOutsideContainer extends FrameLayout {

    @Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	/**
     * @param context context
     */
    public CloseKeyboardOnOutsideContainer(Context context) {
        this(context, null);
    }

    /**
     * @param context context
     * @param attrs attrs
     */
    public CloseKeyboardOnOutsideContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * @param context context
     * @param attrs attrs
     * @param defStyle defStyle
     */
    public CloseKeyboardOnOutsideContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
  	  final ChatActivity activity = (ChatActivity) getContext();
      boolean isKeyboardShowing = InputMethodUtils.isKeyboardShowing();
      boolean isEmotionPanelShowing = activity.isEmotionPanelShowing();
      if ((isKeyboardShowing || isEmotionPanelShowing) && event.getAction() == MotionEvent.ACTION_DOWN) {
          int touchY = (int) (event.getY());
          if (InputMethodUtils.isTouchKeyboardOutside(activity, touchY)) {
              if (isKeyboardShowing) {
                  InputMethodUtils.hideKeyboard(activity.getCurrentFocus());
              }
              if (isEmotionPanelShowing) {
              //	MainActivity.igbExpression.setBackgroundResource(R.drawable.expression1);
                //  activity.hideEmotionPanel();
              }
          }
      }
        return super.onTouchEvent(event);
    }
}
