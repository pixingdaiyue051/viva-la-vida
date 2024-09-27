## openjfx 桌面应用
1. 迁变历史
   - javafx从java8开始内置到jdk中,用于替代swing
   - java11之后openjfx从javafx中独立成为一个单独的项目
   - jdk8,9,10可以直接使用内置api;jdk11之后需要下载sdk并引入开发项目内
   - pom.xml加载 org.openjfx.javafx-controls:17.0.12;添加vm args``--module-path=E:\plugin\javafx-sdk-17.0.12\lib --add-modules=javafx.controls``
   - jdk11可以支持到openjfx:17.0.12(LTS);后续版本需要升级jdk
2. 生命周期
   - init 初始化设置,预加载一些配置,单独一个线程
   - start 加载UI和动态逻辑,单独一个线程
   - stop 销毁前操作,由UI线程结束
3. 基本设置
   - setFullScreen 全屏显示,需先设置scene
   - setMaximized 最大化显示
   - setIconified 最小化显示
   - setResizable 不可更改视窗大小
   - close 程序关闭当前视窗
   - setWidth 初始化窗口宽度 setMaxWidth 最大能拉伸宽度 setMinWidth 最小能拉伸宽度
   - setHeight 初始化窗口高度 setMaxHeight 最大能拉伸高度 setMinHeight 最小能拉伸高度
   - setTitle 视窗标题
   - getIcons 获得当前窗口使用的图标 可以继续添加自定义图标
   - setOpacity 设置背景透明度 0.0d完全透明 1.0d不透明
   - setX 设置初始X坐标 窗口左上角距离屏幕最左边的距离
   - setY 设置初始Y坐标 窗口左上角距离屏幕最上边的距离
4. 特殊设置
   - setAlwaysOnTop 窗口始终在应用前台 不会被其他应用遮罩
   - initStyle 设置初始化样式 一共有五个枚举
     - DECORATED  默认值
     - UNDECORATED 
     - TRANSPARENT
     - UTILITY
     - UNIFIED
   - initModality 初始化模态窗口 需要配合owner使用
     - NONE 无所有事件应用都可以传递到该窗口
     - WINDOW_MODAL 阻挡事件传递到owner
     - APPLICATION_MODAL 阻挡事件传递到其他窗口