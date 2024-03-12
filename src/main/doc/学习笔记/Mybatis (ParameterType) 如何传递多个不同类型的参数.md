### Mybatis (ParameterType) 如何传递多个不同类型的参数
#### 方法一：不需要写parameterType参数。
#### 由于是多参数那么就不能使用parameterType， 改用#｛index｝是第几个就用第几个的索引，索引从0开始
```java
public List<XXXBean> getXXXBeanList(String xxId, String xxCode);

<select id="getXXXBeanList" resultType="XXBean">
    select t.* from tableName where id = #{0} and name = #{1}
</select>
```
#### 方法二：基于注解(最简单)
#### 由于是多参数那么就不能使用parameterType， 这里用@Param来指定哪一个
```java
public List<XXXBean> getXXXBeanList(@Param("id")String id, @Param("code")String code);

<select id="getXXXBeanList" resultType="XXBean">
    select t.* from tableName where id = #{id} and name = #{code}
</select>
```
#### 方法三：Map封装
#### 其中hashmap是mybatis自己配置好的直接使用就行。map中key的名字是那个就在#{}使用那个。
```java
public List<XXXBean> getXXXBeanList(HashMap map);

<select id="getXXXBeanList" parameterType="hashmap" resultType="XXBean">
    select 字段... from XXX where id=#{xxId} code = #{xxCode}
</select>
```
#### 方法四：List封装
#### 传递list和map在资源消耗上肯定远大于方法一和方法二
```java
public List<XXXBean> getXXXBeanList(List<String> list);

<select id="getXXXBeanList" resultType="XXBean">
   select 字段... from XXX where id in
　　<foreach item="item" index="index" collection="list" open="(" separator="," close=")">
　　　　#{item}
　　</foreach>
</select>
```

```java
#{}

MyBatis处理 #{ } 占位符，使用的 JDBC 对象是 PreparedStatement 对象，执行sql语句的效率更高。

使用 PreparedStatement 对象，能够避免 sql 注入，使得sql语句的执行更加安全。

#{ } 常常作为列值使用，位于sql语句中等号的右侧；#{ } 位置的值与数据类型是相关的。

```

```java
${}

MyBatis处理 ${ } 占位符，使用的 JDBC 对象是 Statement 对象，执行sql语句的效率相对于 #{ } 占位符要更低。

${ } 占位符的值，使用的是字符串连接的方式，有 sql 注入的风险，同时也存在代码安全的问题。

${ } 占位符中的数据是原模原样的，不会区分数据类型。

${ } 占位符常用作表名或列名，这里推荐在能保证数据安全的情况下使用 ${ }。
```