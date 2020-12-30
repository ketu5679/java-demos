import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class L135_Candy {
    public int candy(int[] ratings) {
        if (ratings == null) {
            return 0;
        }
        if (ratings.length == 1) {
            return 1;
        }
        int[] cc = new int[ratings.length];
        cc[0] = 1;
        System.out.println(Arrays.toString(ratings));
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i - 1] > ratings[i]) {
                // 3,2,1
                cc[i] = 1;
                if (cc[i-1] == 1) {
                    add(ratings, cc, i - 1);
                }
            } else if (ratings[i] == ratings[i - 1]) {
                // 1,1,1
                cc[i] = 1;
            } else {
                // 1,2,3
                cc[i] = cc[i-1] + 1;
            }
            System.out.println(Arrays.toString(cc));
        }
        System.out.println();
        System.out.println(Arrays.toString(ratings));
        System.out.println(Arrays.toString(cc));
        return Arrays.stream(cc).sum();
    }

    public static void add(int[] ratings, int[] cc, int i) {
        if (i == 0) {
            cc[i] +=1;
            return;
        }
        if (ratings[i - 1] > ratings[i]) {
            cc[i] += 1;
            if (cc[i-1] == cc[i]) {
                add(ratings, cc, i - 1);
            }
        } else if (ratings[i - 1] <= ratings[i]) {
            cc[i] += 1;
        } else {
            cc[i] += 1;
        }
    }

    public static void main(String[] args) {
        int[] ints = new int[]{1, 3, 4, 4, 4, 5, 6, 1, 3, 2, 9, 8, 7, 5};
//        ints = new int[]{3,2,1};
        ints = new int[]{1,3,2,2,1};
        ints = new int[]{1,2,3,1,0};
        new L135_Candy().candy(ints);
    }
}
