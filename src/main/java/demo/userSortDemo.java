package demo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class userSortDemo {

    /**
     * 排名练习
     */
    public static void main(String[] args) {
        List<UserSort> userSorts = users();
        Map<String, List<UserSort>> userList = userSorts.stream().filter(e->e.getYearQuarter()!=null).collect(Collectors.groupingBy(UserSort::getYearQuarter));
        //避免多次排序：对于每个分组内的用户列表，都在循环中进行了排序操作。如果数据量较大，这将影响性能。可以考虑在收集阶段就完成排序
        //Map<String, List<UserSort>> userList = userSorts.stream().filter(e -> e.getYearQuarter() != null).sorted(Comparator.comparing(UserSort::getScore).reversed()).collect(Collectors.groupingBy(UserSort::getYearQuarter, Collectors.toList()));

        //使用并行流提高性能：如果用户数据规模很大，并且排序和分组操作可以并行执行，可以尝试使用并行流以提高处理速度：
        //Map<String, List<UserSort>> userList = userSorts.parallelStream().filter(e -> e.getYearQuarter() != null).sorted(Comparator.comparing(UserSort::getScore).reversed()).collect(Collectors.groupingByConcurrent(UserSort::getYearQuarter));

        for (String key : userList.keySet()){
            List<UserSort> usersses = userList.get(key);
            usersses.sort(Comparator.comparing(UserSort::getScore).reversed());
            for (int i = 0; i< usersses.size(); i++){
                usersses.get(i).setSort(i+1);
            }
            System.out.println(usersses);
        }

        //合并设置排名和打印输出：可以简化内部循环，直接在排序后输出带有排名信息的用户列表，减少中间变量的使用
        /*for (Map.Entry<String, List<UserSort>> entry : userList.entrySet()) {
            int rank = 1;
            for (UserSort user : entry.getValue().stream()
                    .sorted(Comparator.comparing(UserSort::getScore).reversed())
                    .peek(user -> user.setSort(rank++))
                    .toList()) {
                System.out.println(user);
            }
        }*/
    }

    private static List<UserSort> users(){
        List<UserSort> userSortList = new ArrayList<>();

        UserSort userSort = new UserSort();
        userSort.setUserName("张三");
        userSort.setYear("2022");
        userSort.setQuarter("3");
        userSort.setScore(new BigDecimal("105"));
        userSort.setYearQuarter(userSort.getYear()+ userSort.getQuarter());
        userSortList.add(userSort);

        UserSort userSort2 = new UserSort();
        userSort2.setUserName("李四");
        userSort2.setYear("2022");
        userSort2.setQuarter("3");
        userSort2.setScore(new BigDecimal("100"));
        userSort2.setYearQuarter(userSort2.getYear()+ userSort2.getQuarter());
        userSortList.add(userSort2);

        UserSort userSort3 = new UserSort();
        userSort3.setUserName("张三");
        userSort3.setYear("2022");
        userSort3.setQuarter("4");
        userSort3.setScore(new BigDecimal("95"));
        userSort3.setYearQuarter(userSort3.getYear()+ userSort3.getQuarter());
        userSortList.add(userSort3);

        UserSort userSort4 = new UserSort();
        userSort4.setUserName("李四");
        userSort4.setYear("2022");
        userSort4.setQuarter("4");
        userSort4.setScore(new BigDecimal("115"));
        userSort4.setYearQuarter(userSort4.getYear()+ userSort4.getQuarter());
        userSortList.add(userSort4);

        return userSortList;
    }

    @Data
    public static class UserSort {

        private String userName; //用户名
        private String year; //年度
        private String quarter; //季度
        private BigDecimal score; //分数
        private String yearQuarter; //年度季度
        private Integer sort; //排名
    }
}
