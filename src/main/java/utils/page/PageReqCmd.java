package utils.page;

import lombok.Data;

/**
 * @author fuhouyin
 * @time 2023/2/17 17:38
 */
@Data
public class PageReqCmd<T> implements PageReq {
    private T filter;
    private Integer pageNum = 1;
    private Integer pageSize = 20;
}
