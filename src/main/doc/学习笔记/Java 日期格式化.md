### Java 日期格式化
#### 将birthday改为String类型
```java
import lombok.Data;

@Data
public class student {

    private String  birthday;
}
```
#### 直接把格式好的时间设置进去，值得注意的是DateTimeFormatter是线程安全的。
```java
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class demo {

    public static void main(String[] args) {
        student student = new student();
        String current = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        student.setBirthday(current);
        System.out.println(student);
    }
}
```
#### 效果：student(birthday=2024-02-17 01:36:22)
### （扩展）Spring框架中将Date的格式化方案
#### 在Spring框架，如果你需要将一个日期格式从后端传递给前端，你可以通过注解@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8") 进行返回。这样就可以直接在对象中(new Date())了
```java
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class student {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private String birthday;
}
```