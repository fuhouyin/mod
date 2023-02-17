package utils.page;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author fuhouyin
 * @time 2023/2/17 17:37
 */
@Data
public class PageHelper implements Serializable {
    private static final long serialVersionUID = -4653064203154489390L;

    public static final Integer MAX_PAGE_SIZE = 1000000;

    private Integer pageNum;
    private Integer pageSize;
    private Integer totalRowCount;
    private Integer nextPageNum;

    public PageHelper() {
    }

    public PageHelper(Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum <= 0) {
            this.pageNum = 1;
        } else {
            this.pageNum = pageNum;
        }

        if (pageSize == null || pageSize < 0) {
            this.pageSize = MAX_PAGE_SIZE;
        } else {
            this.pageSize = pageSize;
        }
    }

    public PageHelper(PageReqCmd reqCmd, Integer totalRowCount){
        if (reqCmd.getPageNum() == null || reqCmd.getPageNum() <= 0) {
            this.pageNum = 1;
        } else {
            this.pageNum = reqCmd.getPageNum();
        }

        if (reqCmd.getPageSize() == null || reqCmd.getPageSize() < 0) {
            this.pageSize = MAX_PAGE_SIZE;
        } else {
            this.pageSize = reqCmd.getPageSize();
        }

        setTotalRowCount(totalRowCount);
    }

    /**
     * 以页码从1开始计算的偏移量
     * @return
     */
    public Integer offset() {
        if (null == pageNum || null == totalRowCount) {
            return 0;
        }
        int offset = (pageNum - 1) * pageSize;
        return offset;
    }

    /**
     * Java分页
     * @param sourceList
     * @return
     */
    public List pageList(List sourceList){
        if (CollectionUtils.isEmpty(sourceList)) {
            return sourceList;
        }
        Integer offset = offset();
        if (offset > (totalRowCount - 1)) {
            return Collections.EMPTY_LIST;
        }
        int end =  offset + pageSize;
        end = Math.min(end,totalRowCount);
        return sourceList.subList(offset,end);
    }

}

