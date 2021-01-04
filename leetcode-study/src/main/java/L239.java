import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class L239 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] != a[1] ? b[0] - a[0] : b[1] - a[1]);
        for (int i = 0; i < k; i++) {
            pq.offer(new int[]{nums[i], i});
        }
        int[] max = new int[n - k + 1];
        max[0] = pq.peek()[0];
        for (int i = k; i < n; i++) {
            pq.offer(new int[]{nums[i], i});
            while (pq.peek()[1] <= i - k) {
                pq.poll();
            }
            max[i - k + 1] = pq.peek()[0];
        }
        return max;
    }

    public static void main(String[] args) {
        int[] ints = new L239().maxSlidingWindow(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3);
        System.out.println(Arrays.toString(ints));
    }
}
