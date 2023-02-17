package utils.page;

import lombok.Data;

import java.util.List;

/**
 * @author fuhouyin
 * @time 2023/2/17 17:26
 */
@Data
public class PageResp<T> {
    private List<T> list;
    private Integer totalCount;
}