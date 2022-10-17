# xwidget

最喜欢的自定义控件

- 此次更新优化字体相关设置，用起来更随心，更舒服。

### 引入控件

最新版本:  [![](https://jitpack.io/v/com.gitee.pichs/xwidget.svg)](https://jitpack.io/#com.gitee.pichs/xwidget)

        implementation 'com.gitee.pichs:xwidget:2.0.6'

## 走过路过不要错过。 最强基础组件库，超级轻量级。

### 特色杜绝属性冲突==属性完美复用，属性前缀 ‘xp_’ ,简短快速。

### 圆角，外阴影，圆头跟随高度宽度，渐变色背景->双色渐变：top->bottom，left->right,TL->BR, BL->TR

### 按压效果，仍然支持渐变，圆角等。 支持按压透明变化，disabled透明变化。

### 致力于快速开发基础控件，省去大量写xml文件的时间。效果实时可预览。

### 持续维护，已更新3年了。持续增强更有用的功能，杜绝臃肿，不属基础的坚决不加。绝对好用。

### 升级内容，新增字体全局设置。

### 升级内容，新增XRoundView系列。RoundView系列动态生成的渐变Drawable，可单独设置四个角的圆角大小。渐变背景，按压效果等。

### XNested系列，滑动组件，粘性头部，更方便，处理滑动冲突，丝滑体验。企业级---放心使用。

### 使用示例去 demo中的xml中探索吧。

- 注意：凡是继承自xwidget的基础类的textview都可以实现字体变更

1. 在Application中初始化

    ```
      // 初始化，自动缓存。
      public class App extends Application {
          @Override
          public void onCreate() {
              super.onCreate();
              XTypefaceHelper.init(this, true);
          }
      }
    
      XCardButton btn = findViewById(R.id.btn1);
      btn.setOnClickListener(v -> {
          XTypefaceHelper.setGlobalTypefaceFromAssets(getApplicationContext(), "leihong.ttf");
          XTypefaceHelper.setGlobalTypefaceStyle(getApplicationContext(), XTypefaceHelper.NONE);
      });
      XButton normalBtn = findViewById(R.id.normalBtn);
      normalBtn.setOnClickListener(v -> XTypefaceHelper.resetTypeface(MainActivity.this));
      XButton closeFont = findViewById(R.id.closeFont);
      XButton openFont = findViewById(R.id.openFont);

      closeFont.setOnClickListener(v -> XTypefaceHelper.closeTypeface(this));
      openFont.setOnClickListener(v -> XTypefaceHelper.openTypeface(this));
        
    ```

## 升级日志

### 2.0.6版本
- 增加LockableScrollView_xxx系列可以动态设置是否可滚动布局，
- 包含LockableRecyclerView,LockableNestedScrollView,LockableHorizontalScrollView

### 2.0.5版本

- jitpack打包一直失败，一直升级到此版本才成功
- 修复XCheckBox属性显示问题，修复XCheckImageView属性显示问题
- 修改XCardImageView不默认CENTER_CROP属性

### 2.0.0版本

- 最新版本，新增nested系列，丝滑嵌套，炫酷布局，简单实现。
- 修复XRound系列属性，删除不想管的属性，完善其他控件属性
- xp_pressedAlpha属性扩展多一些控件。
- 抓紧时间用起来吧，高效。

### 1.5 版本

#### 增加CircularProgressDrawable

- 此类摘录自SwipeRefreshLayout官方框架，性能高，放心使用
- 为啥单独搞下来，因为很多人都不用SwipeRefreshLayout官方框架，而是用三方的SmartRefreshLayout，<br>
  所以这么好用的CircularProgressDrawable就浪费了，故本人将此类摘了备用，可用作加载进度条。效果贼好。 ~机智~

### 1.4, 1.3, 1.2, 1.1, 1.0 版本

- 优化加修bug~
