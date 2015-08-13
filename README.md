# taolijie

### 构建说明
* 由于使用了自己开发的`web-security`安全框架，因此若构建此项目，需先下载对应jar包，install至本地Maven Repository, 然后才能顺利构建项目。`web-security`的安装方法参见:[web-security](https://github.com/wanghongfei/web-security/releases)


### 部署说明
- 缓存
    * 地址默认为`127.0.0.1`
    * 密码默认为`1111111`
    * Redis配置信息在`webapp/WEB-INF/spring/appServlet/redis-cofig.xml`中
- 日志
    * 请修改`resources/log4j.xml`中第15行代码，将错误日志文件路径设置为本机上实际存在的目录
