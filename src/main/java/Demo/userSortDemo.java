package Demo;

import Pojo.UserSort;

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
        for (String key : userList.keySet()){
            List<UserSort> usersses = userList.get(key);
            usersses.sort(Comparator.comparing(UserSort::getScore).reversed());
            for (int i = 0; i< usersses.size(); i++){
                usersses.get(i).setSort(i+1);
            }
            System.out.println(usersses);
        }
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
}
