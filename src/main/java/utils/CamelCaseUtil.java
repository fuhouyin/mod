package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CamelCaseUtil {

    /**
     * 数据表字段名转换为驼峰式名字的实体类属性名
     * @param tabAttr   数据表字段名
     * @return  转换后的驼峰式命名
     */
    public static String camelize(String tabAttr){
        if(isBlank(tabAttr))
            return tabAttr;
        Pattern pattern = Pattern.compile("(.*)_(\\w)(.*)");
        Matcher matcher = pattern.matcher(tabAttr);
        if(matcher.find()){
            return camelize(matcher.group(1) + matcher.group(2).toUpperCase() + matcher.group(3));
        }else{
            return tabAttr;
        }
    }

    /**
     * 驼峰式的实体类属性名转换为数据表字段名
     * @param camelCaseStr  驼峰式的实体类属性名
     * @return  转换后的以"_"分隔的数据表字段名
     */
    public static String decamelize(String camelCaseStr){
        return isBlank(camelCaseStr) ? camelCaseStr : camelCaseStr.replaceAll("[A-Z]", "_$0").toLowerCase();
    }

    /**
     * 字符串是否为空
     * @param cs   待检查的字符串
     * @return  空：true; 非空：false
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
