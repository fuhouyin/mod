### 常用Linux命令
```shell
#jar包启动
java -jar xxx.jar
nohup java -jar xxx.jar >server.log 2>$1 $
nohup java -jar -Xmx2g -Xms2g xxx.jar > logs/serverLog.log 2>&1 &

#查询所有端口
netstat -tuln
#根据进程号查询端口
netstat -nlp | grep pid
#根据端口号查进程号
netstat -nlp | grep 端口号
#查询进程pid
ps -ef | grep
netstat  -anp |  grep PID

#文件上传
rz
#文件下载
sz

#快速查询进程号
pgrep -f xxxx
```