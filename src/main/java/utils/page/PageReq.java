package utils.page;

/**
 * @author fuhouyin
 * @time 2023/2/17 17:38
 */
public interface PageReq {

    Integer getPageNum();

    void setPageNum(Integer pageNum);

    Integer getPageSize();

    void setPageSize(Integer pageSize);
}