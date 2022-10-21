package Pojo;

import lombok.Data;

/**
 * 临期逾期实体类
 */
@Data
public class TimeOverdueAdventPojo {

    private int days;
    private byte flag; //未逾期 0 逾期 1
    private String oaFlag; //临期 advent 逾期 overdue
}
