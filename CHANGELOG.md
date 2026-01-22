# 更新日志 (Changelog)

XWidget 框架版本更新记录

---

## [未发布] - 开发中

### 新增 ✨
- 正在开发新功能...

### 修复 🐛
- 正在修复已知问题...

---

## [2.0.0] - 2024

### 重大更新 🚀

#### 新增滚轮选择器组件
- **XWheelView** - 3D 滚轮选择器
  - 支持垂直和水平两种方向
  - 基于 RecyclerView 实现，性能优良
  - 支持 3D 旋转效果和线性效果
  - 提供丰富的自定义选项

- **XWheelParams** - 参数配置类
  - 支持方向、item数量、大小、颜色等配置
  - 提供 Builder 模式便于参数设置
  - 支持文字加粗、渐变透明度等特性

- **XWheelDrawManager** - 3D 旋转绘制管理器
  - 实现真实的 3D 旋转效果
  - 支持垂直和水平旋转
  - 支持渐变透明度效果
  - 自动计算旋转角度和偏移

- **XWheelLinearDrawManager** - 线性绘制管理器
  - 不进行旋转处理
  - 仅处理渐变透明度
  - 适合简单的选择器场景

#### 核心特性
- ✅ 支持 3D 立体旋转效果
- ✅ 支持线性平滑滚动效果
- ✅ 支持垂直和水平两种方向
- ✅ 支持自定义绘制器 (ItemPainter)
- ✅ 支持自定义绘制管理器 (DrawManager)
- ✅ 支持渐变透明度效果
- ✅ 支持文字加粗（选中/未选中）
- ✅ 支持自定义分割线样式
- ✅ 支持左中右三种对齐方式（垂直方向）
- ✅ 基于 RecyclerView，性能卓越

### 使用示例

#### XML 方式
```xml
<com.pichs.xwidget.wheel.XWheelView
    android:id="@+id/wheel_view"
    android:layout_width="match_parent"
    android:layout_height="200dp"
    app:xp_wheelOrientation="vertical"
    app:xp_wheelItemCount="5"
    app:xp_wheelItemSize="50dp"
    app:xp_wheelTextSize="20sp"
    app:xp_wheelTextColor="#AAAAAA"
    app:xp_wheelTextCenterColor="#FF6B6B"
    app:xp_wheelDividerColor="#4ECDC4"
    app:xp_wheelDividerSize="2dp"
    app:xp_wheelGradient="true"
    app:xp_wheelTextSelectedBold="true" />
```

#### Kotlin 代码方式
```kotlin
// 创建参数
val params = XWheelParams.Builder()
    .setOrientation(XWheelParams.VERTICAL)
    .setItemCount(5)
    .setItemSize(XWheelParams.dp2px(50f))
    .setTextSize(XWheelParams.sp2px(18f))
    .setTextColor(Color.parseColor("#999999"))
    .setTextCenterColor(Color.parseColor("#333333"))
    .setGradient(true)
    .build()

// 创建 WheelView
val wheelView = XWheelView(
    context,
    params,
    XWheelDrawManager(),
    XWheelView.SimpleItemPainter()
)

// 设置适配器
wheelView.setAdapter(object : XWheelView.Adapter() {
    override fun getItemCount() = 10
    override fun getItem(position: Int) = "选项 ${position + 1}"
})

// 设置选中监听
wheelView.addOnItemSelectedListener { _, index ->
    Log.d("WheelView", "Selected: $index")
}
```

### 文档更新 📚
- 新增 XWheelView 完整使用文档
- 新增日期选择器、城市选择器等高级示例
- 新增自定义绘制器教程
- 新增性能优化和最佳实践指南

---

## [1.x.x] - 2023

### 基础组件完善

#### XView 系列
- XButton - 增强按钮
- XTextView - 增强文本视图
- XImageView - 增强图片视图
- XConstraintLayout - 增强约束布局
- XLinearLayout - 增强线性布局
- XFrameLayout - 增强帧布局
- XRelativeLayout - 增强相对布局

#### XCard 系列
- XCardButton - 卡片按钮
- XCardConstraintLayout - 卡片约束布局
- XCardLinearLayout - 卡片线性布局
- XCardFrameLayout - 卡片帧布局

#### XRound 系列
- XRoundButton - 圆角按钮
- XRoundConstraintLayout - 圆角约束布局
- XRoundLinearLayout - 圆角线性布局
- XRoundImageView - 圆角图片视图
- XRoundTextView - 圆角文本视图

#### 表单控件
- XCheckBox - 复选框
- XSmoothCheckBox - 平滑动画复选框
- XRadioButton - 单选按钮
- XRadioGroup - 单选组
- XRatingBar - 评分条
- XSwitchButton - 开关按钮
- XProgressBar - 进度条
- XInputLayout - 输入布局
- XEditText - 增强输入框
- XVerificationCodeEditText - 验证码输入框

#### 布局控件
- XFlowLayout - 流式布局
- XSpace - 占位控件
- XStatusBarSpace - 状态栏占位

#### 特殊效果
- ShineButton - 闪光按钮
- XPressScaleLayout - 按压缩放布局
- XWebView - 增强网页视图

#### 嵌套滚动
- XNestedScrollLayout - 嵌套滚动布局
- XNestedTopDelegateLayout - 顶部代理布局
- XNestedBottomDelegateLayout - 底部代理布局
- XNestedTopLinearLayout - 顶部线性布局
- XNestedBottomRecyclerView - 底部列表视图
- XNestedTopRecyclerView - 顶部列表视图
- XDraggableScrollBar - 可拖拽滚动条

#### 工具类
- XAlphaHelper - 透明度管理
- XBackgroundHelper - 背景管理
- XColorHelper - 颜色处理
- XRoundBackgroundHelper - 圆角背景管理
- XCheckableHelper - 选中状态管理
- XStatusBarHelper - 状态栏管理
- XDeviceHelper - 设备信息管理
- XDisplayHelper - 屏幕显示管理
- XGradientHelper - 渐变背景管理

### 核心特性
- ✅ 多状态支持（normal、pressed、checked、activated、disabled）
- ✅ 渐变背景（线性渐变、多色渐变）
- ✅ 圆角边框（统一圆角、四角独立圆角）
- ✅ 阴影效果（自定义颜色、高度、透明度）
- ✅ 立体效果（3D立体按钮）
- ✅ 动画效果（按压缩放、透明度变化）
- ✅ 边框渐变
- ✅ 分割线支持

---

## 版本规范说明

### 版本号格式
采用语义化版本号：`主版本号.次版本号.修订号`

- **主版本号**：重大架构变更或不兼容的 API 修改
- **次版本号**：新增功能，向下兼容
- **修订号**：问题修复，向下兼容

### 更新类型标识
- 🚀 **重大更新** - 重要的新功能或架构升级
- ✨ **新增** - 新增功能
- 🐛 **修复** - Bug 修复
- 📚 **文档** - 文档更新
- ⚡ **性能** - 性能优化
- 🎨 **样式** - UI/样式改进
- ♻️ **重构** - 代码重构
- 🔧 **配置** - 配置文件修改
- 🗑️ **移除** - 移除废弃功能
- ⚠️ **破坏性变更** - 不兼容的变更

---

## 贡献指南

如果您发现任何问题或有改进建议，请：

1. 提交 Issue 说明问题
2. 提交 Pull Request 贡献代码
3. 完善文档和示例

感谢您对 XWidget 的支持！

---

## 许可证

详见项目 LICENSE 文件

---

**最后更新时间**: 2026-01-22
