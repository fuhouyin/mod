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
```shell
#安装jdk
#在/opt文件夹下下载并解压jdk文件
#下载地址：
https://www.oracle.com/java/technologies/downloads/
#解压：
tar -zxvf jdk-19_linux-x64_bin.tar.gz
#设置环境变量：
vim ~/.bash_profile
#jdk
export JAVA_HOME=/opt/jdk/jdk-19.0.1
export PATH=$PATH:$HOME/bin:$JAVA_HOME/bin
#保存并推出
:wq
#使环境变量生效
source ~/.bash_profile

#测试 / java -version
```
```shell
#压缩、解压
tar -cvf fille.tar file（可以多个文件空格隔开）-c: 建立压缩档案；-v: 显示所有过程；-f: 使用档案名字，是必须的，是最后一个参数）
tar -xvf file.tar 解包到当前目录
tar -xvf file.tar -C dir 把文件解压到指定目录中
zip 压缩后文件名 源文件
zip -r 压缩后目录名 原目录
unzip file.zip -d dir 解压到指定目录
gunzip file1.gz 解压 file1.gz
gzip file1 压缩 file1
gzip -9 file 最大程度压缩文件

#示例：
01-.tar格式
解包： tar xvf FileName.tar
打包： tar cvf FileName.tar DirName（注：tar是打包，不是压缩！）
02-.gz格式
解压1： gunzip FileName.gz
解压2： gzip -d FileName.gz
压 缩： gzip FileName

03-.tar.gz格式
解压： tar zxvf FileName.tar.gz
压缩： tar zcvf FileName.tar.gz DirName

04-.bz2格式
解压1： bzip2 -d FileName.bz2
解压2： bunzip2 FileName.bz2
压 缩：  bzip2 -z FileName

05-.tar.bz2格式
解压： tar jxvf FileName.tar.bz2
压缩： tar jcvf FileName.tar.bz2 DirName

06-.bz格式
解压1： bzip2 -d FileName.bz
解压2： bunzip2 FileName.bz

07-.tar.bz格式
解压： tar jxvf FileName.tar.bz

08-.Z格式
解压： uncompress FileName.Z
压缩： compress FileName

09-.tar.Z格式
解压： tar Zxvf FileName.tar.Z
压缩： tar Zcvf FileName.tar.Z DirName

10-.tgz格式
解压： tar zxvf FileName.tgz

11-.tar.tgz格式
解压： tar zxvf FileName.tar.tgz
压缩： tar zcvf FileName.tar.tgz FileName

12-.zip格式
解压： unzip FileName.zip
压缩： zip FileName.zip DirName

13-.lha格式
解压： lha -e FileName.lha
压缩： lha -a FileName.lha FileName

14-.rar格式
解压： rar a FileName.rar
压缩： rar e FileName.rar      
rar请到：下载！
解压后请将rar_static拷贝到/usr/bin目录（其他由$PATH环境变量
指定的目录也行）： cp rar_static /usr/bin/rar
```
```shell
#文件、目录
rm -f file 强制删除文件，不提示
rm -r dir 递归删除其文件和文件夹
rm -rf dir 强制删除文件夹及其内容，不提示
mv dir/file dir 将文件或者文件夹移动到指定目录
mv -t dir file 将文件或者文件夹移动到指定目录
mkdir dir dir2 创建两个文件夹
mkdir -p /tmp/dir 创建多级目录
cp file file1 将文件file复制一份file1
cp -a file/dir dir 将文件或者文件夹复制到指定目录
cd .. 返回上一级目录
cd ../.. 返回上两级目录
cd / 返回根目录
ls 列举出当前目录中所有文件
ls -a 列举出当前目录中所有文件，包括隐藏文件
ls -l 显示文件的详细信息
ls -lrt 按时时间排序显示文件
pwd 显示当前路径

#网络相关
ip add 显示当前ip地址
ifdown eth0 禁用 ‘eth0’ 网络设备
ifup eth0 启用 ‘eth0’ 网络设备

#系统相关
su 用户名 切换用户登录
shutdown -h now 关机
shutdown -r now 重启
reboot 重启
```
```shell
#Linux查找文件命令
find / -name test.txt 在所有目录中查找名字为test.txt的文件
find / -name ‘*.txt’ 在所有目录中查找后缀名为.txt的文件
find . -name test.txt 在当前目录中查找名字为test.txt的文件
find /etc -name '*srm*' 查找/etc文件夹下所有名字中包含srm的文件
find / -amin -10 查找在系统中最后10分钟访问的文件
find / -atime -2 查找在系统中最后48小时访问的文件
find / -empty 查找在系统中为空的文件或者文件夹
find / -group cat 查找在系统中属于 groupcat的文件
find / -mmin -5 查找在系统中最后5分钟里修改过的文件
find / -mtime -1 查找在系统中最后24小时里修改过的文件
find / -nouser 查找在系统中属于作废用户的文件
find / -user fred 查找在系统中属于FRED这个用户的文件
```
```shell
#查看历史操作命令：
history
#history记录文件：
more ~/.bash_history

清除history历史命令记录:
#1.1.编辑history记录文件，删除部分不想被保存的历史命令。
vim ~/.bash_history
#1.2.清除当前用户的history命令记录。
history -c

#2.1利用vim特性删除历史命令
#使用vim打开一个文件
vi test.txt
# 设置vim不记录命令，Vim会将命令历史记录，保存在viminfo文件中。
:set history=0
# 用vim的分屏功能打开命令记录文件.bash_history，编辑文件删除历史操作命令
vsp ~/.bash_history
# 清楚保存.bash_history文件即可。
```
