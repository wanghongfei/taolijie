# taolijie

### 构建说明
* 由于使用了自己开发的`web-security`安全框架，因此若构建此项目，需先下载对应jar包，install至本地Maven Repository, 然后才能顺利构建项目。`web-security`的安装方法参见:[web-security](https://github.com/wanghongfei/web-security/releases)

### 部署说明
* 因为项目使用了`Redis`作为缓存服务器，所以必须安装部署`Redis`项目才能正常启动。
* 如果不想在本机搞个`Redis`，可以使用目前测试服务器上部署好的`Redis`，目前默认的配置就是连接测试服务器，因此启动项目时必须**有网络连接**
* `Redis`的配置文件在`redis-config.xml`中
