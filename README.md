# xwidget
牛逼的自定义控件

- 此次更新优化字体相关设置，用起来更随心，更舒服。

### 引入控件

      ```
      
        implementation 'com.github.pichsy:xwidget:1.3'
        
        
      ```

## 升级内容，新增字体全局设置。
- 注意：凡是继承自xwidget的基础类的textview都可以实现字体变更
1. 在Application中初始化

    
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
           
       // 重置字体恢复默认样式 
       XTypefaceHelper.resetTypeface(MainActivity.this);
    
       
       // 增加属性忽略，不想受全局字体影响，使用下面的属性，或者代码也可以
       // 默认是false
       app:xp_ignoreGlobalTypeface="true"
       
       // 默认是false
       normalBtn.setIgnoreGlobalTypeface(true);
        