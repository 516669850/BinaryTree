package BinaryTree;

import java.util.*;
/**
 *判断一棵树是否是平衡二叉树、搜索二叉树、完全二叉树
 *
 * 平衡二叉树（任何一个子树的左子树和右子树高度差不超过一）：
 * 每个节点包含该子树是否是平衡二叉树以及树的深度，
 * 递归，左右子树是否都是平衡二叉树，且左右树高度
 * 差<=1
 *
 * 搜索二叉树（任何节点的右孩子大于左孩子）：
 * 中序遍历是递增的
 *
 * 完全二叉树（最后一层叶节点都在最左边的平衡二叉树）：
 * 按层遍历，每个节点判断：
 * 1）如果只有右子树，且没有左子树，返回false
 * 2）如果只有左子树，或者没有子树，则后面遍历的节点
 * 必须都是叶节点。(使用leaf标志表示是否遇到了这种节点)
 * */
public class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;
    }

    public String toString(){
        return "val:" + val;
    }
    //递归前序
    public static void preOrder(TreeNode treeNode){
        if(treeNode == null){
            return;
        }
        System.out.print(treeNode.val + " ");
        preOrder(treeNode.left);
        preOrder(treeNode.right);
    }
    //非递归前序
    public static ArrayList<Integer> preOrderTraversal(TreeNode treeNode){
        ArrayList<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        stack.push(treeNode);
        while(!stack.isEmpty()){
            TreeNode temp = stack.pop();
            if(temp != null){
                list.add(temp.val);
                stack.push(temp.right);
                stack.push(temp.left);
            }
        }
        return list;
    }
    //递归中序
    public static void midOrder(TreeNode treeNode){
        if(treeNode == null){
            return;
        }
        midOrder(treeNode.left);
        System.out.print(treeNode.val + " ");
        midOrder(treeNode.right);
    }
    //非递归中序
    public static ArrayList<Integer> midOrderTraversal(TreeNode treeNode){
        ArrayList<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = treeNode;

        while(cur != null || !stack.isEmpty()){
            while(cur != null){
                stack.add(cur);
                cur = cur.left;
            }
            cur = stack.pop();
            list.add(cur.val);
            cur = cur.right;
        }
        return list;
    }
    //递归后续
    public static void backOrder(TreeNode treeNode){
        if(treeNode == null){
            return;
        }
        backOrder(treeNode.left);
        backOrder(treeNode.right);
        System.out.print(treeNode.val + " ");
    }
    //非递归后续
    public static ArrayList<Integer> backOrderTraversal(TreeNode treeNode){
        ArrayList<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = treeNode;
        stack.push(cur);

        while(!stack.isEmpty()){
            TreeNode temp = stack.pop();
            if(temp != null){
                list.add(temp.val);
                stack.push(temp.left);
                stack.push(temp.right);
            }
        }
        Collections.reverse(list);
        return list;
    }
    //宽度优先遍历
    public static ArrayList<Integer> wideOrder(TreeNode treeNode){
        ArrayList<Integer> list = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(treeNode);
        while (!queue.isEmpty()){
            TreeNode temp = queue.poll();
            if(temp != null){
                list.add(temp.val);
                queue.offer(temp.left);
                queue.offer(temp.right);
            }
        }
        return list;
    }
    //获取数深度
    public static int getHeight(TreeNode treeNode){
        if(treeNode == null)
            return 0;
        return Math.max(getHeight(treeNode.left)+1,getHeight(treeNode.right)+1);
    }
    /**
     * 求完全二叉树节点个数：
     *
     * O(N)方法：遍历每个节点
     *
     * O((logN)^2)方法：
     * 求根的最左子孙节点的深度，再求根的右孩子的最左子孙节点深度，
     * 如果两个深度相等，则左子树是满二叉树，左子树的节点个数就是
     * 2^n - 1,否则右子树是满二叉树，右子树节点个数2^(n-1)-1，然后
     * 递归调用
     * */
    public static int getNodeNum(TreeNode treeNode){
        if(treeNode == null)
            return 0;
        return bs(treeNode, 1, mostLeftLevel(treeNode, 1));
    }

    public static int bs(TreeNode treeNode, int l, int h){
        if(l == h){
            return 1;
        }
        if(mostLeftLevel(treeNode.right, l + 1) == h){
            return (1 << (h - l)) + bs(treeNode.right, l + 1, h);
        }else{
            return (1 << (h - l - 1)) + bs(treeNode.left, l + 1, h);
        }
    }

    public static int mostLeftLevel(TreeNode treeNode, int level){
        while(treeNode != null){
            level++;
            treeNode = treeNode.left;
        }
        return level - 1;
    }


    public static void main(String[] args){
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode5 = new TreeNode(5);


        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.right = treeNode4;
        treeNode3.left = treeNode5;

        System.out.println("前序：");
        preOrder(treeNode1);
        System.out.println();
        System.out.println("前序非递归：" + preOrderTraversal(treeNode1));
        System.out.println("中序：");
        midOrder(treeNode1);
        System.out.println();
        System.out.println("中序非递归：" + midOrderTraversal(treeNode1));
        System.out.println("后序：");
        backOrder(treeNode1);
        System.out.println();
        System.out.println("后序非递归：" + backOrderTraversal(treeNode1));
        System.out.println("广度优先：" + wideOrder(treeNode1));
        System.out.println("深度：" + getHeight(treeNode1));
    }

}