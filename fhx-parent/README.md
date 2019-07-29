#shiro使用
#项目启动时间: (2019年7月19日)
## 2019年7月19日
1. 项目框架搭建
2. 添加shiro rememberMe配置、过滤器配置,授权认证主体配置
3. 添加统一异常处理: jsr-303效验异常处理; shiro异常处理

## 2019年7月20日23:25
1. 拆分项目,将shiro分离; 将bean分离,将web端分离; 
2. 添加jpa-base(基础查询)



# 问题
1. 2019年7月20日23:00
```text
在拆分shiro时,启动项目时报错(错误1),检查配置确认没错,然后发现启动类位置放的位置有问题,shiro的包路径是(com.fhx.shiro),
web端启动类的包路径是(com.fhx.web),开始的时候启动类放在了web端的web路径下导致错误,根据
经验得出结论,启动类应该放在fhx路径下,最后测试项目正常运行
```

# 错误
1. Please create bean of type 'Realm' or add a shiro.ini in the root classpath (src/main/resources/shiro.ini) or in the META-INF folder (src/main/resources/META-INF/shiro.ini)

# 项目结束时间(...)


