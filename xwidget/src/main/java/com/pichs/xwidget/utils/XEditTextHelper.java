package com.pichs.xwidget.utils;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 编辑框帮助类
 */
public class XEditTextHelper {
    @SuppressLint("ClickableViewAccessibility")
    public static void disableCopyAndPaste(final EditText editText) {
        try {
            if (editText == null) {
                return;
            }

            editText.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return true;
                }
            });

            editText.setLongClickable(false);
            editText.setOnTouchListener((v, event) -> {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // setInsertionDisabled when user touches the view
                    setInsertionDisabled(editText);
                }
                return false;
            });

            editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    return false;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setInsertionDisabled(EditText editText) {
        try {
            Field editorField = TextView.class.getDeclaredField("mEditor");
            editorField.setAccessible(true);
            Object editorObject = editorField.get(editText);

            // if this view supports insertion handles
            Class editorClass = Class.forName("android.widget.Editor");
            Field mInsertionControllerEnabledField = editorClass.getDeclaredField("mInsertionControllerEnabled");
            mInsertionControllerEnabledField.setAccessible(true);
            mInsertionControllerEnabledField.set(editorObject, false);

            // if this view supports selection handles
            Field mSelectionControllerEnabledField = editorClass.getDeclaredField("mSelectionControllerEnabled");
            mSelectionControllerEnabledField.setAccessible(true);
            mSelectionControllerEnabledField.set(editorObject, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置光标的样式
     *
     * @param editText 编辑框
     * @param color    颜色
     * @param width    宽度
     * @param radius   圆角
     */
    public static void setCursorDrawable(EditText editText, int color, int width, int radius) {
        try {
            if (editText == null) {
                return;
            }
            Drawable drawable = XGradientHelper.getDrawable(radius, width, -1, color, 0, 0);
            setCursorDrawable(editText, drawable);
        } catch (final Throwable throwable) {
            // nothing to do
        }
    }


    /**
     * 设置光标的 drawable
     *
     * @param editText 编辑框
     * @param drawable 光标的drawable
     */
    public static void setCursorDrawable(EditText editText, Drawable drawable) {
        try {
            if (editText == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                editText.setTextCursorDrawable(drawable);
            } else {
                Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
                fCursorDrawableRes.setAccessible(true);
                // 将fCursorDrawableRes设置为0
                fCursorDrawableRes.set(editText, 0);
                @SuppressLint("BlockedPrivateApi") Field eCursorDrawable = TextView.class.getDeclaredField("mCursorDrawable");
                eCursorDrawable.setAccessible(true);
                eCursorDrawable.set(editText, drawable);

                Field fEditor = TextView.class.getDeclaredField("mEditor");
                fEditor.setAccessible(true);

                Object editor = fEditor.get(editText);
                Class<?> clazz = editor.getClass();
                Field fCursorDrawable = clazz.getDeclaredField("mDrawableForCursor");
                fCursorDrawable.setAccessible(true);
                fCursorDrawable.set(editor, drawable);
            }
        } catch (final Throwable throwable) {
            // nothing to do
        }
    }

}
