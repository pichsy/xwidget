# xwidget
最喜欢的自定义控件

- 此次更新优化字体相关设置，用起来更随心，更舒服。


### 引入控件
最新版本:  [![](https://jitpack.io/v/com.gitee.pichs/xwidget.svg)](https://jitpack.io/#com.gitee.pichs/xwidget)

        implementation 'com.gitee.pichs:xwidget:1.28'
       
## 走过路过不要错过。 最强基础组件库，超级轻量级。
## 特色杜绝属性冲突==属性完美复用，属性前缀 ‘xp_’ ,简短快速。
## 圆角，外阴影，圆头跟随高度宽度，渐变色背景->双色渐变：top->bottom，left->right,TL->BR, BL->TR
## 按压效果，仍然支持渐变，圆角等。 支持按压透明变化，disabled透明变化。
## 致力于快速开发基础控件，省去大量写xml文件的时间。效果实时可预览。
## 持续维护，已更新3年了。持续增强更有用的功能，杜绝臃肿，不属基础的坚决不加。绝对好用。

## 升级内容，新增字体全局设置。
## 升级内容，新增XRoundView系列。RoundView系列动态生成的渐变Drawable，可单独设置四个角的圆角大小。渐变背景，按压效果等。

## XNested系列，滑动组件，粘性头部，更方便，处理滑动冲突，丝滑体验。企业级---放心使用。
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

### 1.5 版本
#### 增加CircularProgressDrawable
- 此类摘录自SwipeRefreshLayout官方框架，性能高，放心使用
- 为啥单独搞下来，因为很多人都不用SwipeRefreshLayout官方框架，而是用三方的SmartRefreshLayout，<br>
所以这么好用的CircularProgressDrawable就浪费了，故本人将此类摘了备用，可用作加载进度条。效果贼好。 ~机智~

### 1.4, 1.3, 1.2, 1.1, 1.0 版本
- 优化加修bug~
