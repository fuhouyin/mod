### 个人学习demo，相关示例请查看Test.java

[![996.icu](https://img.shields.io/badge/link-996.icu-red.svg)](https://996.icu)

### 可视化大屏组件
    visualization
    
<img src=https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/aaf0bc4ca40f44f4a2054f4e8d5260b7~tplv-k3u1fbpfcp-watermark.image width=400 height=300 />

### 时间工具类
    utils.DateTimeUtils
        daysBetween 计算两个日期之间相差的天数
        overdueAdvent 计算临期/超期天数
        
### wx消息推送Mod
    wxMod
        wxMod.wxRequestController
        wxMod.wxSendMsgController
    前提：需要提前准备一个微信公众号，个人订阅号无效，可申请注册微信测试号
    
### 邮件发送工具
    mail，由于依赖停止维护，请谨慎使用
    
### 加密工具
    utils.CryptoUtils
    字符串加密/解密 encryptAESPkcs7/decryptAESPkcs7
    
### 文件工具
    utils.FileUtils 
        suffix 获取base64头
        fileToBase64 file转base64
        saveFile MultipartFile保存本地
        getFile 网络文件转File
        Base64ToMultipartFile base64转MultipartFile
    
### 分页工具
    utils.page.*,详情查看Test.PageTest();
    utils.HandlePage 手动分页，可能有bug，待测试
    
### 回显方法
    utils.response.VoResult
    
### 根据实体类属性获取getAndSet方法
    utils.MethodGetAndSet 可能有bug，待测试
    
### redis工具
    utils.redis.RedisCacheUtils

## 鸣谢

感谢 [JetBrains](https://www.jetbrains.com/?from=real-url) 提供的 free JetBrains Open Source license

[![JetBrains-logo](https://i.loli.net/2020/10/03/E4h5FZmSfnGIgap.png)](https://www.jetbrains.com/?from=real-url)