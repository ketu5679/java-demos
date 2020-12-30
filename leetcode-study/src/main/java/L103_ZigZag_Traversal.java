import java.util.*;
import java.util.stream.Collectors;

public class L103_ZigZag_Traversal {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }

        // 记录遍历每次层的结果
        List<List<TreeNode>> nodeList = new ArrayList<>(Collections.singletonList(
                new ArrayList<>(Collections.singletonList(root))));
        // 记录最终返回值每层的结果
        List<List<Integer>> resList = new ArrayList<>(Collections.singletonList(
                new ArrayList<>(Collections.singletonList(root.val))));


        boolean left = true;

        int i = 0;
        while (true) {
            List<TreeNode> nodeTmp = new ArrayList<>();
            left = !left;

            for (TreeNode cur : nodeList.get(i++)) {
                // 记录正常正序遍历
                if (cur.left != null) nodeTmp.add(cur.left);
                if (cur.right != null) nodeTmp.add(cur.right);
            }
            if (nodeTmp.size() == 0) {
                break;
            }
            nodeList.add(nodeTmp);

            List<Integer> resTmp = nodeTmp.stream().map(p -> p.val).collect(Collectors.toList());
            if (!left) Collections.reverse(resTmp);
            resList.add(resTmp);
        }
        return resList;
    }

    public static void main(String[] args) {
        TreeNode root = initNode(new Integer[]{
                0,-4,-3,null,-1,8,null,null,3,null,-9,-2,null,4});
        L103_ZigZag_Traversal l = new L103_ZigZag_Traversal();
        System.out.println(l.zigzagLevelOrder(root));
        System.out.println(l.zigzagLevelOrder2(root));
    }

    public static TreeNode initNode(Integer[] ints) {
        Queue<Integer> f = new LinkedList<>();
        System.out.println(Arrays.toString(ints));
        int length = ints.length;
        System.out.println(length);

        TreeNode root = new TreeNode(ints[0]);
        int i = 1;
        List<TreeNode> ls = new ArrayList<>();
        ls.add(root);
        int ii = 0;
        while (i<length) {
            TreeNode treeNode = ls.get(ii++);
            if (ints[i] != null) {
                treeNode.left = new TreeNode(ints[i]);
                ls.add(treeNode.left);
            }
            if (i+1 == length) break;
            if (ints[i + 1] != null) {
                treeNode.right = new TreeNode(ints[i + 1]);
                ls.add(treeNode.right);
            }
            i+=2;
        }
        System.out.println("ls");
        System.out.println(ls);
        System.out.println("root:");
        System.out.println(root);
        System.out.println("after init -----------------------");
        return root;
    }
    private List<List<Integer>> zigzagLevelOrder2(TreeNode root){
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        List<List<Integer>> ans = new LinkedList<>();
        boolean isLeftOrder = true;

        while (!queue.isEmpty()) {
            LinkedList<Integer> lvlList = new LinkedList<>();
            int qSize = queue.size();
            for (int i = 0; i < qSize; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
                if (isLeftOrder) {
                    lvlList.add(node.val);
                } else {
                    lvlList.addFirst(node.val);
                }
            }
            isLeftOrder = !isLeftOrder;
            System.out.println(lvlList);
            ans.add(new ArrayList<>(lvlList));

        }
        return ans;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        System.out.println("node:" + x);
        val = x;
    }

    @Override
    public String toString() {
        return " " + val + "("+(left == null ? "null" :left.val)+", "+(right == null ? "null" : right.val)+")";
    }
}
