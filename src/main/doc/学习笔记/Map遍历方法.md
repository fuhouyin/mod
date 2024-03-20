```java
    //准备
    Map<String, String> map = new HashMap<String, String>();
    map.put("key1", "value1");
    map.put("key2", "value2");
    map.put("key3", "value3");
```
```java
    //第一种：普遍使用，二次取值
    System.out.println("通过Map.keySet遍历key和value：");
    System.out.println("map.keySet()："+map.keySet()); //[key1, key2, key3]
    for (String key : map.keySet()) {
        System.out.println("key= "+ key + " and value= " + map.get(key));
    }
```
```java
    //第二种
    System.out.println("通过Map.entrySet使用iterator遍历key和value：");
    Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
    System.out.println("map.entrySet()："+map.entrySet()); //[key1=value1, key2=value2, key3=value3]
    while (it.hasNext()) {
        Map.Entry<String, String> entry = it.next();
        System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
    }
```
```java
    //第三种：推荐，尤其是容量大时
    System.out.println("通过Map.entrySet遍历key和value");
    System.out.println("map.entrySet()："+map.entrySet()); //[key1=value1, key2=value2, key3=value3]
    for (Map.Entry<String, String> entry : map.entrySet()) {
        System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
    }
```
```java
    //第四种
    System.out.println("通过Map.values()遍历所有的value，但不能遍历key");
    System.out.println("map.values()："+map.values()); //[value1, value2, value3]
    for (String v : map.values()) {
        System.out.println("value= " + v);
    }
```
```java
    //第五种
    System.out.println("通过lamda表达式遍历key和value：");
    map.forEach((k, v) -> System.out.println("key = " + k + ", value = " + v));
```