package utils;

import java.util.List;

public class HandlePage {

    /**分页  start = (page - 1) * size, end = page * size*/
    private <T> List<T> handlePage(List<T> source, int start, int end){
        int total = source.size();
//        end = end > total ? total : end;
        end = Math.min(end, total);
        if (end <= start) {
            return null;
        }
        return source.subList(start,end);
    }
}
