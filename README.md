基于spirngboot2.1.0开发，权限控制，历史版本，无缝集成，零侵入springboot项目和spring项目（4.x版本）。支持多种配置文件格式。

# 演示地址

http://106.12.26.82:8083/
```
admin
111111
(可以给自己新增一个账号，不要删除别人的数据哦)
```

## springboot项目集成

```
  部署时只需要增加--spring.config.location=xxxxx即可，如:
  java -jar dataflow-1.3.0.jar --spring.config.location=http://106.12.26.82:8083/config/getConfig.properties/dataflow/test-192.168.1.193/1.3.0?.properties
  不要怀疑最后的?.properties和getConfig.properties的写法，这是springboot"优秀"的判断逻辑决定的。其中的dataflow是工程名称，test-192.168.1.93是环境
  1.3.0当然就是版本号了。如何在配置中心添加这个配置项呢，首先你得添加一个名为dataflow的工程，其次你得为工程dataflow创建一个配置项，设置环境名称为test-192.168.1.193,
  版本号为1.3.0。具体的操作去配置中心后台试试吧，如果不会那就算我输...
```

## spring非boot项目的集成


# easyconf的部署
release目录下下载对应的版本发布包，如easyconf-1.0.1.zip，解压。

新建一个数据库，执行sql脚本easyconf-1.0.1-full.sql(如果是升级的化，需要依次执行每个版本的-upgrade.sql脚本)

修改config/application.properties，注意修改数据库地址和发邮件的账号配置

```
spring.alliance.dao.datasource[0].jdbcUrl = jdbc:mysql://localhost:3306/easyconf-show?useUnicode=true&characterEncoding=utf-8&serverTimezone=GMT
spring.alliance.dao.datasource[0].username = root
spring.alliance.dao.datasource[0].password = zzzzzzzz

spring.mail.host=smtp.qq.com
spring.mail.username=253187898@qq.com
spring.mail.password=yourpwd
```

./start.sh 启动

tail -f logs/easyconf-all.log 查看日志
