package com.pichs.xwidget.utils;

import static com.pichs.xwidget.utils.XBackgroundHelper.DEFAULT_COLOR_TRANSPARENT;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * 渐变背景帮助类
 */
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
         * pressed, enabled, checked, activated , selected
         */
        public ColorStateListBuilder() {
        }

        public ColorStateListBuilder addPressedColor(@ColorInt int color) {
            return addState(State.state_pressed, color);
        }

        public ColorStateListBuilder addCheckedColor(@ColorInt int color) {
            return addState(State.state_checked, color);
        }

        public ColorStateListBuilder addDisabledColor(@ColorInt int color) {
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

        public StateListDrawableBuilder addDisabledState(Drawable drawable) {
            return addState(-State.state_enabled, drawable);
        }

        public StateListDrawableBuilder addDisabledState(int color) {
            return addDisabledState(new ColorDrawable(color));
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
     * 建造者创建GradientDrawable
     * 数值必须都为正数才有效
     * shape 只支持默认，rectangle
     * <p>
     * radius            圆角大小      （优先级低于单独设置圆角）
     * topLeftRadius     左上角圆角大小 （优先级高） (0->radius)
     * topRightRadius    右上角圆角大小 （优先级高） (0->radius)
     * bottomLeftRadius  左下角圆角大小 （优先级高） (0->radius)
     * bottomRightRadius 右下角圆角大小 （优先级高） (0->radius)
     * strokeWidth       边框宽
     * strokeColor       边框颜色
     * fillColor         背景填充色      （优先使用gradientColors）没有会使用fillColor (优先级低)
     * gradientColors    渐变色数组，仅支持2个色，3个及上不行，不会渐变，只会分割色  (优先级高)
     */
    public static class GradientDrawableBuilder {

        int radius = 0;
        int topLeftRadius = 0;
        int topRightRadius = 0;
        int bottomLeftRadius = 0;
        int bottomRightRadius = 0;

        @ColorInt
        int fillColor = DEFAULT_COLOR_TRANSPARENT;
        int strokeWidth = 0;
        @ColorInt
        int strokeColor = DEFAULT_COLOR_TRANSPARENT;
        // 默认从左到右
        GradientDrawable.Orientation orientation = GradientDrawable.Orientation.LEFT_RIGHT;
        int[] gradientColors;

        public GradientDrawableBuilder setRadius(int radius) {
            this.radius = Math.max(radius, 0);
            return this;
        }

        public GradientDrawableBuilder setTopLeftRadius(int topLeftRadius) {
            this.topLeftRadius = Math.max(topLeftRadius, 0);
            return this;
        }

        public GradientDrawableBuilder setTopRightRadius(int topRightRadius) {
            this.topRightRadius = Math.max(topRightRadius, 0);
            return this;
        }

        public GradientDrawableBuilder setBottomLeftRadius(int bottomLeftRadius) {
            this.bottomLeftRadius = Math.max(bottomLeftRadius, 0);
            return this;
        }

        public GradientDrawableBuilder setBottomRightRadius(int bottomRightRadius) {
            this.bottomRightRadius = Math.max(bottomRightRadius, 0);
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

        public GradientDrawable build() {
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
        return new GradientDrawableBuilder()
                .setRadius(radius)
                .setFillColor(fillColor)
                .setStrokeWidth(strokeWidth)
                .setStrokeColor(strokeColor)
                .build();
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
        return new GradientDrawableBuilder()
                .setRadius(radius)
                .setOrientation(gradientOrientation)
                .setGradientColors(gradientColors)
                .build();
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
            float[] radii = {
                    topLeftRadius, topLeftRadius,
                    topRightRadius, topRightRadius,
                    bottomRightRadius, bottomRightRadius,
                    bottomLeftRadius, bottomLeftRadius
            };
            gradientDrawable.setCornerRadii(radii);
        } else if (radius > 0) {
            gradientDrawable.setCornerRadius(radius);
        }
        if (strokeColor != DEFAULT_COLOR_TRANSPARENT && strokeWidth > 0) {
            gradientDrawable.setStroke(strokeWidth, strokeColor);
        }
        if (gradientColors != null && gradientColors.length >= 2) {
            gradientDrawable.setColors(gradientColors);
            if (orientation == null) {
                orientation = GradientDrawable.Orientation.LEFT_RIGHT;
            }
            gradientDrawable.setOrientation(orientation);
            gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        } else {
            gradientDrawable.setColor(fillColor);
        }
        return gradientDrawable;
    }

    /**
     * 动态创建一个 layer-list 对应的drawable
     * 这个目前仅为 立体按钮 提供便捷，若需要全功能，不如去写xml->代码不太方便
     *
     * @param top         上面的drawable top和bottom必须同时设置才有用
     * @param bottom      下面的drawable top和bottom必须同时设置才有用
     * @param insetBottom 底部立体的高度，也就是上面drawable的底部上移距离
     * @return {@link LayerDrawable}
     */
    public static LayerDrawable getLayerDrawable(Drawable top, Drawable bottom, int insetBottom) {
        if (top == null || bottom == null) return null;
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{bottom, top});
        layerDrawable.setLayerInset(1, 0, 0, 0, insetBottom);
        return layerDrawable;
    }


    /**
     * 动态创建一个 layer-list 对应的drawable
     * 这个目前仅为 立体按钮 提供便捷，若需要全功能，不如去写xml->代码不太方便
     *
     * @param top    底部立体的高度，也就是上面drawable的底部下移距离
     * @param left   底部立体的高度，也就是上面drawable的底部右移距离
     * @param right  底部立体的高度，也就是上面drawable的底部左移距离
     * @param bottom 底部立体的高度，也就是上面drawable的底部上移距离
     * @return {@link LayerDrawable}
     */
    public static LayerDrawable getLayerDrawable(Drawable topDrawable, Drawable bottomDrawable, int left, int top, int right, int bottom) {
        if (topDrawable == null || bottomDrawable == null) return null;
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{bottomDrawable, topDrawable});
        layerDrawable.setLayerInset(1, left, top, right, bottom);
        return layerDrawable;
    }

}
