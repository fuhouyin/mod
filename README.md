### 个人学习demo，相关示例请查看Test.java

[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)

### 可视化大屏组件
    visualization

<img src=https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/aaf0bc4ca40f44f4a2054f4e8d5260b7~tplv-k3u1fbpfcp-watermark.image width=400 height=300 />

### 时间工具类
    utils.DateTimeUtils
        daysBetween 计算两个日期之间相差的天数
        overdueAdvent 计算临期/超期天数

### 消息推送类
    1 wxMod 微信公众号模板消息推送
        wxMod.WxRequestController  微信请求本服务接口 用于验证
        wxMod.WxSendMsgController  微信公众号发送消息（用于消息模板类的消息推送通知）
        前提：需要提前准备一个微信公众号，个人订阅号无效，可申请注册微信测试号
    
    2 mail 邮件发送工具。由于依赖停止维护，请谨慎使用

    3 FeiShu 飞书模板消息推送webhook，需要建群邀请自定义机器人

### 加密工具
    1 utils.CryptoUtils (AES/CBC/PKCS7Padding 加解密)
        字符串加密/解密 encryptAESPkcs7/decryptAESPkcs7
        加解密文件参考Test.fileEncDecTest()

    2 utils.SM2Util 国密2加解密方法 (工具类中有使用示例)
        generateKey 生成公私钥
        encrypt 加密
        decrypt 解密
        sign 签名
        verifySign 验签

### 文件工具
    utils.FileUtils 
        suffix 获取base64头
        fileToBase64 file转base64
        saveFile MultipartFile保存本地
        getFile 网络文件转File
        Base64ToMultipartFile base64转MultipartFile

### 分页工具
    1 utils.page.*,详情查看Test.PageTest();
    2 utils.HandlePage 手动分页

### 回显方法
    utils.response.VoResult

### 根据实体类属性获取getAndSet方法
    utils.MethodGetAndSet  根据实体类属性名生成GET或SET方法
          getGetMethod 根据属性，获取get方法
          setValue 根据属性，拿到set方法，并把值set到对象中。参考Test.setObjectValueByField()。

### redis工具
    utils.redis.RedisCacheUtils

### 字符串处理工具
    utils.CamelCAsseUtil 将下划线分割字符串转为驼峰式 （aaa_bbb_ccc => aaaBbbCcc）
    
### 类型转换工具
    utils.EntityUtil 
        entityToMap  将实体类转为map <String, Object>
        getMultiValueMap  解析json字符串为MultiValueMap(传值为json字符串)

### 自动生成数据库所有实体类
    autoentity.pom.xml
        基于jOOQ的自动生成实体类功能，添加pom文件或者修改至自己的pom文件中，maven打包即可在对应处生成实体类
        需要在<build>内配置数据库信息 以及生成文件所在的位置信息 

## 鸣谢

感谢 [JetBrains](https://www.jetbrains.com/?from=real-url) 提供的 free JetBrains Open Source license

[![JetBrains-logo](https://i.loli.net/2020/10/03/E4h5FZmSfnGIgap.png)](https://www.jetbrains.com/?from=real-url)