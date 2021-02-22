package com.pichs.common.widget.utils;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class XGradientHelper {
    /**
     * 定义状态
     */
    @IntDef({
            State.state_pressed,
            State.state_checked,
            State.state_enabled,
            State.state_selected,
            State.state_activated,
            State.state_hovered,
            State.state_checkable,
            State.state_focused,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
        int state_pressed = android.R.attr.state_pressed;
        int state_checked = android.R.attr.state_checked;
        int state_enabled = android.R.attr.state_enabled;
        int state_selected = android.R.attr.state_selected;
        int state_activated = android.R.attr.state_activated;
        int state_hovered = android.R.attr.state_hovered;
        int state_checkable = android.R.attr.state_checkable;
        int state_focused = android.R.attr.state_focused;
    }

    /**
     * 获取字体颜色选择器
     *
     * @param pressedColor   按压时颜色
     * @param unEnabledColor enabled=false时颜色
     * @param checkedColor   选中时颜色
     * @param activatedColor activated=true时颜色
     * @param normalColor    反状态是的颜色（也就是正常的颜色）
     * @return
     */
    public static ColorStateList getTextColorSelector(@ColorInt int pressedColor, @ColorInt int unEnabledColor, @ColorInt int checkedColor, @ColorInt int activatedColor, @ColorInt int normalColor) {
        return new ColorStateListBuilder()
                .addPressedColor(pressedColor)
                .addUnEnabledColor(unEnabledColor)
                .addActivatedColor(activatedColor)
                .addCheckedColor(checkedColor)
                .setUnSateColor(normalColor)
                .build();
    }


    /**
     * 获取字体颜色选择器
     *
     * @param pressedColor   按压时颜色
     * @param unEnabledColor enabled=false时颜色
     * @return
     */
    public static ColorStateList getTextColorSelector(@ColorInt int pressedColor, @ColorInt int unEnabledColor, @ColorInt int normalColor) {
        return new ColorStateListBuilder()
                .addPressedColor(pressedColor)
                .addUnEnabledColor(unEnabledColor)
                .setUnSateColor(normalColor)
                .build();
    }

    /**
     * 获取字体颜色选择器
     *
     * @param pressedColor 按压时颜色
     * @return
     */
    public static ColorStateList getTextColorPressedSelector(@ColorInt int pressedColor, @ColorInt int unPressedColor) {
        return new ColorStateListBuilder()
                .addPressedColor(pressedColor)
                .setUnSateColor(unPressedColor)
                .build();
    }

    /**
     * 获取字体颜色选择器
     *
     * @param unEnabledColor 按压时颜色
     * @return
     */
    public static ColorStateList getTextColorUnEnabledSelector(@ColorInt int unEnabledColor, @ColorInt int enabledColor) {
        return new ColorStateListBuilder()
                .addUnEnabledColor(unEnabledColor)
                .setUnSateColor(enabledColor)
                .build();
    }


    /**
     * * 字体颜色选择器
     * * 添加顺序可能会影响使用效果。
     * * 建议使用顺:
     * * pressed, enabled, checked, activated , enabled
     */
    public static class ColorStateListBuilder {

        private final List<Integer> stateList = new ArrayList<>();
        private final List<Integer> unStateList = new ArrayList<>();
        private final List<Integer> colorList = new ArrayList<>();
        @ColorInt
        private int unStateColor = Color.DKGRAY;

        /**
         * 添加顺序可能会影响使用效果。
         * 建议使用顺:
         * pressed, enabled, checked, activated , enabled
         */
        public ColorStateListBuilder() {
        }

        public ColorStateListBuilder addPressedColor(@ColorInt int color) {
            return addState(State.state_pressed, color);
        }

        public ColorStateListBuilder addCheckedColor(@ColorInt int color) {
            return addState(State.state_checked, color);
        }

        public ColorStateListBuilder addUnEnabledColor(@ColorInt int color) {
            return addState(-State.state_enabled, color);
        }

        public ColorStateListBuilder addSelectedColor(@ColorInt int color) {
            return addState(State.state_selected, color);
        }

        public ColorStateListBuilder addActivatedColor(@ColorInt int color) {
            return addState(State.state_activated, color);
        }

        public ColorStateListBuilder addState(int state, @ColorInt int color) {
            stateList.add(state);
            unStateList.add(-state);
            colorList.add(color);
            return this;
        }

        public ColorStateListBuilder setUnSateColor(@ColorInt int color) {
            unStateColor = color;
            return this;
        }

        public ColorStateList build() {
            if (stateList.isEmpty()) {
                return new ColorStateList(new int[][]{new int[]{-State.state_enabled}}, new int[]{unStateColor});
            }
            int[][] state = new int[stateList.size() + 1][];
            int[] unstate = new int[unStateList.size()];
            int[] color = new int[colorList.size() + 1];
            for (int i = 0; i < stateList.size(); i++) {
                state[i] = new int[]{stateList.get(i)};
                unstate[i] = unStateList.get(i);
                color[i] = colorList.get(i);
            }
            state[stateList.size()] = unstate;
            color[colorList.size()] = unStateColor;

            return new ColorStateList(state, color);
        }

    }

    /**
     * * * 背景图片选择器
     * * * 添加顺序可能会影响使用效果。
     * * * 建议使用顺:
     * * * pressed, enabled, checked, activated , selected
     */
    public static class StateListDrawableBuilder {
        private final List<Integer> stateList = new ArrayList<>();
        private final List<Integer> unStateList = new ArrayList<>();
        private final List<Drawable> stateDrawable = new ArrayList<>();
        private Drawable unStateDrawable = new ColorDrawable(Color.LTGRAY);

        public StateListDrawableBuilder addPressedState(Drawable drawable) {
            return addState(State.state_pressed, drawable);
        }

        public StateListDrawableBuilder addPressedState(int color) {
            return addPressedState(new ColorDrawable(color));
        }

        public StateListDrawableBuilder addCheckedState(Drawable drawable) {
            return addState(State.state_checked, drawable);
        }

        public StateListDrawableBuilder addCheckedState(int color) {
            return addCheckedState(new ColorDrawable(color));
        }

        public StateListDrawableBuilder addActivatedState(Drawable drawable) {
            return addState(State.state_activated, drawable);
        }

        public StateListDrawableBuilder addActivatedState(int color) {
            return addActivatedState(new ColorDrawable(color));
        }

        public StateListDrawableBuilder addSelectedState(Drawable drawable) {
            return addState(State.state_selected, drawable);
        }

        public StateListDrawableBuilder addSelectedState(int color) {
            return addSelectedState(new ColorDrawable(color));
        }

        public StateListDrawableBuilder addUnEnabledState(Drawable drawable) {
            return addState(-State.state_enabled, drawable);
        }

        public StateListDrawableBuilder addUnEnabledState(int color) {
            return addUnEnabledState(new ColorDrawable(color));
        }


        public StateListDrawableBuilder addState(int state, Drawable drawable) {
            if (drawable != null) {
                stateList.add(state);
                unStateList.add(-state);
                stateDrawable.add(drawable);
            }
            return this;
        }

        public StateListDrawableBuilder addState(int state, @ColorInt int color) {
            if (color != 0) {
                stateList.add(state);
                unStateList.add(-state);
                stateDrawable.add(new ColorDrawable(color));
            }
            return this;
        }

        public StateListDrawableBuilder setUnState(Drawable drawable) {
            unStateDrawable = drawable;
            return this;
        }

        public StateListDrawableBuilder setUnState(@ColorInt int color) {
            unStateDrawable = new ColorDrawable(color);
            return this;
        }

        public StateListDrawable build() {
            StateListDrawable drawable = new StateListDrawable();
            int[] unstate = new int[stateList.size()];
            for (int i = 0; i < stateList.size(); i++) {
                unstate[i] = unStateList.get(i);
                drawable.addState(new int[]{stateList.get(i)}, stateDrawable.get(i));
            }
            drawable.addState(unstate, unStateDrawable);
            return drawable;
        }

    }

    /**
     * 获取背景选择器
     *
     * @param pressedDrawable   按压Drawable
     * @param unEnabledDrawable enabled=false Drawable
     * @param checkedDrawable   选中 Drawable
     * @param activatedDrawable activated Drawable
     * @param normalDrawable    正常状态的Drawable
     * @return
     */
    public static StateListDrawable getSelector(Drawable pressedDrawable, Drawable unEnabledDrawable, Drawable checkedDrawable, Drawable activatedDrawable, Drawable normalDrawable) {
        return new StateListDrawableBuilder()
                .addPressedState(pressedDrawable)
                .addUnEnabledState(unEnabledDrawable)
                .addCheckedState(checkedDrawable)
                .addActivatedState(activatedDrawable)
                .setUnState(normalDrawable)
                .build();
    }

    public static StateListDrawable getSelector(int pressedColor, int unEnabledColor, int checkedColor, int activatedColor, int normalColor) {
        return new StateListDrawableBuilder()
                .addPressedState(pressedColor)
                .addUnEnabledState(unEnabledColor)
                .addCheckedState(checkedColor)
                .addActivatedState(activatedColor)
                .setUnState(normalColor)
                .build();
    }

    public static StateListDrawable getSelector(Drawable pressedDrawable, Drawable unEnabledDrawable, Drawable unStateDrawable) {
        return new StateListDrawableBuilder()
                .addPressedState(pressedDrawable)
                .addUnEnabledState(unEnabledDrawable)
                .setUnState(unStateDrawable)
                .build();
    }

    public static StateListDrawable getSelector(int pressedColor, int unEnabledColor, int unStateColor) {
        return new StateListDrawableBuilder()
                .addPressedState(pressedColor)
                .addUnEnabledState(unEnabledColor)
                .setUnState(unStateColor)
                .build();
    }


    public static StateListDrawable getPressedSelector(Drawable pressedDrawable, Drawable unStateDrawable) {
        return new StateListDrawableBuilder()
                .addPressedState(pressedDrawable)
                .setUnState(unStateDrawable)
                .build();
    }

    public static StateListDrawable getPressedSelector(int pressedColor, int unPressedColor) {
        return new StateListDrawableBuilder()
                .addPressedState(pressedColor)
                .setUnState(unPressedColor)
                .build();
    }

    public static StateListDrawable getUnEnabledSelector(Drawable unEnabledDrawable, Drawable enabledDrawable) {
        return new StateListDrawableBuilder()
                .addPressedState(unEnabledDrawable)
                .setUnState(enabledDrawable)
                .build();
    }

    public static StateListDrawable getUnEnabledSelector(int unEnabledColor, int enabledColor) {
        return new StateListDrawableBuilder()
                .addPressedState(unEnabledColor)
                .setUnState(enabledColor)
                .build();
    }


    /**
     * 获取带圆角的 selector
     *
     * @param radius         圆角
     * @param pressedColor   按压
     * @param checkedColor   选中
     * @param activatedColor 活跃
     * @param unEnabledColor 不可操作
     * @param normalColor    普通色
     * @return StateListDrawable
     */
    public static StateListDrawable getRoundSelector(int radius, int pressedColor, int unEnabledColor, int checkedColor, int activatedColor, int normalColor) {
        return getSelector(
                getDrawable(radius, pressedColor, 0, 0),
                getDrawable(radius, unEnabledColor, 0, 0),
                getDrawable(radius, checkedColor, 0, 0),
                getDrawable(radius, activatedColor, 0, 0),
                getDrawable(radius, normalColor, 0, 0)
        );
    }


    /**
     * 获取带圆角的 selector
     *
     * @param radius         圆角
     * @param pressedColor   按压
     * @param unEnabledColor 不可操作
     * @param normalColor    普通色
     * @return StateListDrawable
     */
    public static StateListDrawable getRoundSelector(int radius, int pressedColor, int unEnabledColor, int normalColor) {
        return getSelector(
                getDrawable(radius, pressedColor, 0, 0),
                getDrawable(radius, unEnabledColor, 0, 0),
                getDrawable(radius, normalColor, 0, 0)
        );
    }


    /**
     * 有角度，点击效果
     *
     * @param normalColor  无点击效果
     * @param pressedColor 点击效果
     * @return StateListDrawable
     */
    public static StateListDrawable getPressedRoundSelector(int radius, int pressedColor, int normalColor) {
        return getPressedSelector(
                getDrawable(radius, pressedColor, 0, 0),
                getDrawable(radius, normalColor, 0, 0)
        );
    }

    /**
     * 有角度，点击效果
     *
     * @param normalColor    无点击效果
     * @param unEnabledColor 点击效果
     * @return StateListDrawable
     */
    public static StateListDrawable getUnEnabledRoundSelector(int radius, int unEnabledColor, int normalColor) {
        return getUnEnabledSelector(
                getDrawable(radius, unEnabledColor, 0, 0),
                getDrawable(radius, normalColor, 0, 0)
        );
    }

    public static class GradientDrawableBuilder {
        /**
         * 数值必须都为正数才有效
         * shape 只支持默认，rectangle
         * <p>
         * topLeftRadius     左上角圆角大小
         * topRightRadius    右上角圆角大小
         * bottomLeftRadius  左下角圆角大小
         * bottomRightRadius 右下角圆角大小
         * fillColor         背景填充色
         * strokeWidth       边框宽
         * strokeColor       边框颜色
         */
        int radius;
        int topLeftRadius;
        int topRightRadius;
        int bottomLeftRadius;
        int bottomRightRadius;

        @ColorInt
        int fillColor;
        int strokeWidth;
        @ColorInt
        int strokeColor;
        GradientDrawable.Orientation orientation;
        int[] gradientColors;

        public GradientDrawableBuilder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public GradientDrawableBuilder setTopLeftRadius(int topLeftRadius) {
            this.topLeftRadius = topLeftRadius;
            return this;
        }

        public GradientDrawableBuilder setTopRightRadius(int topRightRadius) {
            this.topRightRadius = topRightRadius;
            return this;
        }

        public GradientDrawableBuilder setBottomLeftRadius(int bottomLeftRadius) {
            this.bottomLeftRadius = bottomLeftRadius;
            return this;
        }

        public GradientDrawableBuilder setBottomRightRadius(int bottomRightRadius) {
            this.bottomRightRadius = bottomRightRadius;
            return this;
        }

        public GradientDrawableBuilder setFillColor(int fillColor) {
            this.fillColor = fillColor;
            return this;
        }

        public GradientDrawableBuilder setStrokeWidth(int strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }

        public GradientDrawableBuilder setStrokeColor(int strokeColor) {
            this.strokeColor = strokeColor;
            return this;
        }

        public GradientDrawableBuilder setOrientation(GradientDrawable.Orientation orientation) {
            this.orientation = orientation;
            return this;
        }

        public GradientDrawableBuilder setGradientColors(int[] gradientColors) {
            this.gradientColors = gradientColors;
            return this;
        }

        GradientDrawable build() {
            return getDrawable(
                    radius, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius,
                    fillColor, strokeWidth, strokeColor, orientation, gradientColors
            );
        }

    }

    /**
     * 获取Drawable
     * 数值必须都为正数才有效
     * shape 只支持默认，rectangle
     *
     * @param radius      圆角
     * @param fillColor   填充色
     * @param strokeWidth 边框宽
     * @param strokeColor 边框颜色
     * @return GradientDrawable
     */
    public static GradientDrawable getDrawable(int radius, @ColorInt int fillColor, int strokeWidth, @ColorInt int strokeColor) {
        return getDrawable(radius, 0, 0, 0, 0, fillColor, strokeWidth, strokeColor, null);
    }

    /**
     * 数值必须都为正数才有效
     * shape 只支持默认，rectangle
     *
     * @param topLeftRadius     左上角圆角大小
     * @param topRightRadius    右上角圆角大小
     * @param bottomLeftRadius  左下角圆角大小
     * @param bottomRightRadius 右下角圆角大小
     * @param fillColor         背景填充色
     * @param strokeWidth       边框宽
     * @param strokeColor       边框颜色
     * @return GradientDrawable
     */
    public static GradientDrawable getDrawable(int topLeftRadius, int topRightRadius, int bottomLeftRadius, int bottomRightRadius,
                                               @ColorInt int fillColor, int strokeWidth, @ColorInt int strokeColor) {
        return getDrawable(0, topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius, fillColor, strokeWidth, strokeColor, null);
    }

    /**
     * shape 只支持默认，rectangle
     * 渐变只支持线性渐变，其他的用xml定义更舒服
     *
     * @param radius            圆角
     * @param fillColor         填充色
     * @param strokeWidth       边框宽
     * @param strokeColor       边框颜色
     * @param topLeftRadius     左上角圆角大小
     * @param topRightRadius    右上角圆角大小
     * @param bottomLeftRadius  左下角圆角大小
     * @param bottomRightRadius 右下角圆角大小
     * @param orientation       渐变旋转角度
     * @param gradientColors    渐变色
     * @return GradientDrawable
     */
    public static GradientDrawable getDrawable(int radius, int topLeftRadius, int topRightRadius,
                                               int bottomLeftRadius, int bottomRightRadius,
                                               @ColorInt int fillColor, int strokeWidth, @ColorInt int strokeColor,
                                               GradientDrawable.Orientation orientation, @ColorInt int... gradientColors) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        // 有先使用单独设置圆角
        if (topLeftRadius > 0 || topRightRadius > 0 || bottomLeftRadius > 0 || bottomRightRadius > 0) {
            float[] radii = {topLeftRadius, topLeftRadius,
                    topRightRadius, topRightRadius,
                    bottomRightRadius, bottomRightRadius,
                    bottomLeftRadius, bottomLeftRadius
            };
            gradientDrawable.setCornerRadii(radii);
        } else if (radius > 0) {
            gradientDrawable.setCornerRadius(radius);
        }
        if (strokeColor != 0 && strokeWidth > 0) {
            gradientDrawable.setStroke(strokeWidth, strokeColor);
        }
        if (gradientColors != null && gradientColors.length >= 2) {
            gradientDrawable.setColors(gradientColors);
            if (orientation != null) {
                gradientDrawable.setOrientation(orientation);
            }
            gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        } else {
            gradientDrawable.setColor(fillColor);
        }
        return gradientDrawable;
    }


    /**
     * 设置渐变色，默认水平方向渐变
     * 不带边框
     *
     * @param radius         圆角大小 px
     * @param gradientColors 渐变数组，从左到右渐变
     * @return GradientDrawable
     * GradientDrawable.Orientation.LEFT_RIGHT
     * {@link GradientDrawable.Orientation#LEFT_RIGHT}
     */
    public static GradientDrawable getGradientDrawable(int radius, GradientDrawable.Orientation gradientOrientation, @ColorInt int[] gradientColors) {
        return getDrawable(radius, 0, 0, 0, 0, 0, 0, 0, gradientOrientation, gradientColors);
    }

}
