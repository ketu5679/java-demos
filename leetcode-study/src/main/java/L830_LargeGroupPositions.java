import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class L830_LargeGroupPositions {
    public static void main(String[] args) {
        String s = "abcdddeeeeaabbbcd";
        for (int i = 0; i < s.length(); i++) {
            System.out.println(s.charAt(i));
        }
        System.out.println(largeGroupPosition(s));
        System.out.println(largeGroupPosition("aaa"));
    }

    public static List<List<Integer>> largeGroupPosition(String s) {
        int len = s.length();
        List<List<Integer>> res = new ArrayList<>();
        int nums = 1;
        for (int i = 0; i < len; i++) {
            // 最后一个 或者 不等于后一个
            if (i == len - 1 || s.charAt(i) != s.charAt(i + 1)) {
                if (nums >= 3) {
                    res.add(Arrays.asList(i + 1 - nums, i));
                }
                nums = 1;
            } else {
                nums++;
            }
        }

        return res;
    }
}
