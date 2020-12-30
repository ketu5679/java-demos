import java.lang.reflect.Array;
import java.util.Arrays;

public class L188_SellStock {
    public static void main(String[] args) {
//        System.out.println(new L188_SellStock().maxProfit(2, new int[]{2, 4, 1}));
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(new L188_SellStock().maxProfit(2, new int[]{3, 2, 6, 5, 0, 3}));
    }

    public int maxProfit(int k, int[] prices) {
        int len = prices.length;
        if(len == 0) {
            return 0;
        }

        k = Math.min(len/2, k);
        int[] buy = new int[k + 1];
        int[] sell = new int[k + 1];

        buy[0] = -prices[0];
        sell[0] = 0;
        for (int i = 1; i <= k; ++i) {
            buy[i] = sell[i] = Integer.MIN_VALUE / 2;
        }

        System.out.println("----------");
        System.out.println(Arrays.toString(buy));
        System.out.println(Arrays.toString(sell));
        for (int i = 0; i < len; i++) {
            buy[0] = Math.max(buy[0], sell[0] - prices[i]);
            System.out.println("############ " +i);
            for (int j = 1; j <= k; j++) {
                System.out.println("---------- " + j);
                System.out.println(Arrays.toString(buy));
                System.out.println(Arrays.toString(sell));
                buy[j] = Math.max(buy[j], sell[j] - prices[i]);
                sell[j] = Math.max(sell[j], buy[j-1] + prices[i]);
                System.out.println("-- after");
                System.out.println(Arrays.toString(buy));
                System.out.println(Arrays.toString(sell));
            }
        }

        return Arrays.stream(sell).max().getAsInt();
    }

}
