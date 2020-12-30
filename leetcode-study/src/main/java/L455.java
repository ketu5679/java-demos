import java.util.Arrays;

public class L455 {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int gL = g.length - 1;
        int sL = s.length - 1;

        int res = 0;
        while (gL >= 0 && sL >= 0) {
            // 达成一次满足 sL -1
            if (s[sL] >= g[gL]) {
                res++;
                sL--;
            }
            // 不管是否达成 gL -1
            gL--;
        }
        return res;
    }

    public int findContentChildren2(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);

        // 最多达成数量
        int gL = g.length;

        // 达成数量 从需要最小开始达成
        int gRes = 0;
        for (int value : s) {
            if (gRes < gL && g[gRes] <= value) {
                gRes++;
            }
        }

        return gRes;
    }

    public static void main(String[] args) {
        System.out.println(new L455().findContentChildren2(
                new int[]{2, 3, 5},
                new int[]{3, 4})
        );
    }
}
