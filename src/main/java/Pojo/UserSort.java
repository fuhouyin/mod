package Pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserSort {

    private String userName; //用户名
    private String year; //年度
    private String quarter; //季度
    private BigDecimal score; //分数
    private String yearQuarter; //年度季度
    private Integer sort; //排名
}
