# code-auto-creator

它是一款使用openJFX(javaFX) 编写的一款可以将DB设计转换为sql语句的小工具。打包完成之后为可运行jar，可以在安装了JDK的机器上运行。引入mybaits-plus代码生成工具核心、openjfx11提供界面和核心功能。将驱动程序通过使用者选择，动态加载，支持10余种数据库类型，从而实现代码生成。支持mapper、entity、xml分别存分类保存。



## 运行要求

1. JDK版本大于1.8
2. JAVA_HOME需要在环境变量中配置

## 版本更新日志

### #1.0版本

1. 提供10中数据库类型生成代码。

2. 驱动程序由使用者选择，jvm动态加载从而缩小jar体积。

3. 本地记录上次的操作记录，方便下次修改。

4. 客户端操作，不需要集成在项目中，方便实用。

5. 跨平台，基于jdk编写，利用jdk的特性，在mac、windows、linux同样可以运行。

   

