### Java如何向数组里添加元素
#### 1.已有的数组datecolumn和list集合
```java
String[] datecolumn = {"Mon", "Tue", "Wed"};
List<String> list = new ArrayList<>();
list.add("Thu");
list.add("Fri");
list.add("Sat");
list.add("Sun");
```
#### 2.数组和集合add到titleList中
```java
List<String> titleList = new ArrayList<String>();
//将datecolum数组转换list并add到titleList中
titleList.addAll(Arrays.asList(datecolumn));
//将list添加到titleList中
titleList.addAll(list);
```
#### 3.titleList集合转换title数组
```java
String[] title = titleList.toArray(new String[titleList.size()]);
```

#### 4.遍历title数组
```java
for (String string : title) {
	System.out.println(string);
}
```