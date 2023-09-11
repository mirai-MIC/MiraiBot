# MiraiBot - 基于Mirai框架的QQ机器人

MiraiBot 是一个基于 Mirai 框架的二次封装框架，用于创建和管理 QQ 机器人。它集成了 Spring Boot
框架，使得开发和扩展机器人变得更加容易和灵活。本文档将为您提供关于 MiraiBot 的一些基本信息和用法示例。

## 功能特点

- MiraiBot 支持多种消息类型，包括文本消息、图片、视频、音频等。
- 它提供了异步消息发送功能，以确保您的机器人在处理消息时保持高响应性。
- MiraiBot 使用自定义注解来监听消息事件，让您可以轻松处理不同类型的消息。
- 该框架还支持发送视频消息，使您可以与群组成员共享视频内容。

## 快速开始

### 1. 配置项目

确保您已经正确配置了 Spring Boot 项目，并添加了 MiraiBot 依赖。

### 2. 创建 MiraiBot 实例

在您的 将此项目拉去下来之后，在[applicantion.yml](src/main/resources/application.yml) 中添加账号密码

``` yaml
mirai:
  bot:
    qq-number: qq-number
    password: password
    device-info: "cache/deviceInfo.json"
logging:
  level:
    root: info
```

# ❗使用前提说明

- *须知*
- [x] 您必须拥有qsign签名服务器
- [x] [jdk17](https://jdk.java.net/java-se-ri/17)
- **什么是签名服务器**
- 建议参考 [unidbg-fetch-qsign](https://github.com/fuqiuluo/unidbg-fetch-qsign)
- **如果您还是不太会**
- 建议再参考 [up主视频](https://space.bilibili.com/372636169?spm_id_from=333.1007.0.0)

# 如果需要建议联系

- qq群 620428906
- kook频道 [kook](https://kook.top/1069PT)
