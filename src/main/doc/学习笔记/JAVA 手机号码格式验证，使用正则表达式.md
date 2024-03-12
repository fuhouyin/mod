### JAVA 手机号码格式验证，使用正则表达式

```java
public static boolean isValidPhoneNumber(String phoneNumber){

	if((phoneNumber != null) && (!phoneNumber.isEmpty())){

		return Pattern.matches("^1[3-9]d{9}$",phoneNumber);

	}

	return false;

}
```