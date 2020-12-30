import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Optional;

public class L85_MAX_Rectangle {
    public static void main(String[] args) {
        char[][] m = new char[][]{
                {'1', '0', '1', '1', '1'},
                {'0', '1', '0', '1', '0'},
                {'1', '1', '0', '1', '1'},
                {'1', '1', '0', '1', '1'},
                {'0', '1', '1', '1', '1'}
        };

        Arrays.stream(m).forEach(p -> System.out.println(Arrays.toString(p)));
        System.out.println(new L85_MAX_Rectangle().maximalRectangle2(m));
    }
    public int maximalRectangle2(char[][] matrix) {
        int rL = matrix.length;
        if (0 == rL) {
            return 0;
        }
        int cL = matrix[0].length;
        int[][] left = new int[rL][cL];

        for (int j = 0; j < cL; j++) {
            for (int i = 0; i < rL; i++) {
                if (matrix[i][j] == '1') {
                    left[i][j] = (i == 0 ? 0 : left[i -1][j]) + 1;
                }
            }
        }
        System.out.println("--");
        Arrays.stream(left).forEach(p -> System.out.println(Arrays.toString(p)));

        int max = 0;
        for (int i = 0; i < rL; i++) {
            max = Math.max(largestRectangleArea(left[i]), max);
        }

        return max;
    }

    public int maximalRectangle3(char[][] matrix) {
        int rL = matrix.length;
        if (0 == rL) {
            return 0;
        }
        int cL = matrix[0].length;
        // 计算最大矩形 增加哨兵 每行长度+2
        int[][] left = new int[rL][cL+2];

        for (int colIndex = 0; colIndex < cL; colIndex++) {
            for (int rowIndex = 0; rowIndex < rL; rowIndex++) {
                if (matrix[rowIndex][colIndex] == '1') {
                    left[rowIndex][colIndex+1] = (rowIndex == 0 ? 0 : left[rowIndex -1][colIndex+1]) + 1;
                }
            }
        }
        return Arrays.stream(left)
//                .peek(p -> System.out.println(Arrays.toString(p)))
                .map(this::largestRectangleAreaUseSentinelNoCheck)
                .max(Integer::compareTo)
                .get();
    }
    public int largestRectangleAreaUseSentinelNoCheck(int[] heights) {
        int len = heights.length;
        int area = 0;

        Deque<Integer> stack = new ArrayDeque<>();
        stack.addLast(0);
        for (int i = 1; i < len; i++) {
            while (heights[stack.peekLast()] > heights[i]) {
                int height = heights[stack.removeLast()];
                int width = i - stack.peekLast() - 1;
                area = Math.max(area, height * width);
            }
            stack.addLast(i);
        }
        return area;
    }

    public int largestRectangleAreaUseSentinel(int[] heights) {
        int len = heights.length;
        if (len == 0) return 0;
        if (len == 1) return heights[0];

        int area = 0;
        int[] newHeights = new int[len + 2];
        System.arraycopy(heights, 0, newHeights, 1, len);
        len += 2;
        heights = newHeights;

        Deque<Integer> stack = new ArrayDeque<>();
        stack.addLast(0);
        for (int i = 1; i < len; i++) {
            while (heights[stack.peekLast()] > heights[i]) {
                int height = heights[stack.removeLast()];
                int width = i - stack.peekLast() - 1;
                area = Math.max(area, height * width);
            }
            stack.addLast(i);
        }
        return area;
    }

    public int largestRectangleArea(int[] heights) {
        int len = heights.length;
        int area = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < len; i++) {
            while (!stack.isEmpty() && heights[stack.peekLast()] > heights[i]) {
                int height = heights[stack.removeLast()];
                int width = stack.isEmpty() ? i : i - stack.peekLast() - 1;
                area = Math.max(area, height * width);
            }
            stack.addLast(i);
        }

        while (!stack.isEmpty()) {
            int height = heights[stack.removeLast()];
            int width = stack.isEmpty() ? len : len - stack.peekLast() - 1;
            area = Math.max(area, height * width);
        }
        return area;
    }

    /**
     * method 1
     * @param matrix
     * @return
     */
    public int maximalRectangle(char[][] matrix) {
        int rL = matrix.length;
        if (0 == rL) {
            return 0;
        }
        int cL = matrix[0].length;
        int[][] left = new int[rL][cL];
        for (int i = 0; i < rL; i++) {
            for (int j = 0; j < cL; j++) {
                if (matrix[i][j] == '1') {
                    left[i][j] = (j == 0 ? 0 : left[i][j - 1]) + 1;
                }
            }
        }

        int max = 0;
        for (int i = 0; i < rL; i++) {
            for (int j = 0; j < cL; j++) {
                if (matrix[i][j] == '0') {
                    continue;
                }
                int width = left[i][j];
                int area = width;
                for (int k = i - 1; k >= 0; k--) {
                    width = Math.min(width, left[k][j]);
                    area = Math.max(area, (i - k + 1) * width);
                }
                max = Math.max(max, area);
            }
        }


        return max;
    }
}
