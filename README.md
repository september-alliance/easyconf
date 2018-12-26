基于spirngboot2.1.0开发，权限控制，历史版本，无缝集成，零侵入springboot项目和spring项目（4.x版本）。支持多种配置文件格式。

# 演示地址

http://106.12.26.82:8083/
```
admin
111111
(可以给自己新增一个账号，不要删除别人的数据哦)
```

springboot项目集成
```
  部署时只需要增加--spring.config.location=xxxxx即可，如:
  java -jar dataflow-1.3.0.jar --spring.config.location=http://106.12.26.82:8083/config/getConfig.properties/dataflow/test-192.168.1.193/1.3.0?.properties
  不要怀疑最后的?.properties和getConfig.properties的写法，这是springboot"优秀"的判断逻辑决定的。其中的dataflow是工程名称，test-192.168.1.93是环境
  1.3.0当然就是版本号了。如何在配置中心添加这个配置项呢，首先你得添加一个名为dataflow的工程，其次你得为工程dataflow创建一个配置项，设置环境名称为test-192.168.1.193,
  版本号为1.3.0。具体的操作去配置中心后台试试吧，如果不会那就算我输...
```

spring非boot项目的集成


easyconf的部署
