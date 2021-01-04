# xwidget
牛逼的自定义控件

### 引入控件

      ```
      
        implementation 'com.github.pichsy:xwidget:1.1'
        
        
      ```

## 升级内容，新增字体全局设置。
- 注意：凡是继承自xwidget的基础类的textview都可以实现字体变更
1. 在Application中初始化


    ```
      // 初始化，自动缓存。
      public class App extends Application {
          @Override
          public void onCreate() {
              super.onCreate();
              XTypefaceHelper.init(this);
          }
      }
    
    // 代码中设置
      btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   XTypefaceHelper.setGlobalTypefaceFromAssets(getApplicationContext(), "leihong.ttf");
                   XTypefaceHelper.setGlobalTypefaceStyle(getApplicationContext(), XTypefaceHelper.BOLD_ITALIC);
               }
           });
    
    ```
