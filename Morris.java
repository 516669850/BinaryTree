package BinaryTree;
/**
 * 正常的递归和非递归遍历二叉树的时间复杂度都为O(N)，
 * 空间复杂度O(h)，h为树的高度
 * Morris算法实现了空间复杂度为O(1)
 * 具体算法：
 * 当前节点为cur
 * 1.如果cur无左孩子，cur = cur.right;
 * 2.如果cur有左孩子，找到左孩子最右的节点，记为mostright
 *   1）如果mostright右指针为空，让其右指针指向cur，同时cur = cur.left
 *   2) 如果mosrright右指针指向cur，让其右指针指向空，同时cur = cur.right
 *
 * 如果一个节点有左孩子，则要经过该节点两次
 * */
public class Morris {
    //先序遍历：第一次到达节点或节点没有左子树时打印
    public static void morrisPre(TreeNode node){
        if(node == null){
            return;
        }
        TreeNode cur = node;
        TreeNode mostRight = null;
        while(cur != null){
            mostRight = cur.left;
            if(mostRight != null){
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                    System.out.print(cur.val + " ");//第一次到该节点
                }else{
                    mostRight.right = null;//第二次到该节点
                }
            }else{
                System.out.print(cur.val + " ");//当前节点没左子树
            }
            cur = cur.right;
        }
    }
    //中序遍历：每次向右跳时打印
    public static void morrisIn(TreeNode node){
        if(node == null){
            return;
        }
        TreeNode cur = node;
        TreeNode mostRight = null;
        while(cur != null){
            mostRight = cur.left;
            if(mostRight != null){
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                }else{
                    mostRight.right = null;//第二次到该节点
                }
            }
            System.out.print(cur.val + " ");//每次向右跳之前打印
            cur = cur.right;
        }
    }
    //后序遍历：选择第二次到的节点，逆序打印其左子树右子孙+左子树
    //最后单独逆序打印整个树的右边界
    public static void morrisPos(TreeNode node){
        if(node == null){
            return;
        }
        TreeNode cur = node;
        TreeNode mostRight = null;
        while(cur != null){
            mostRight = cur.left;
            if(mostRight != null){
                while(mostRight.right != null && mostRight.right != cur){
                    mostRight = mostRight.right;
                }
                if(mostRight.right == null){
                    mostRight.right = cur;
                    cur = cur.left;
                }else{
                    mostRight.right = null;//第二次到该节点
                    printEdge(cur.right);
                }
            }
            cur = cur.right;
        }
        printEdge(node);
    }

    public static void printEdge(TreeNode node){
        TreeNode tail = reverseTreeNode(node);
        TreeNode cur = tail;
        while(cur != null){
            System.out.print(cur.val + " ");
            cur = cur.right;
        }
        reverseTreeNode(tail);
    }
    //翻转右子树，让右孩子指向父节点
    public static TreeNode reverseTreeNode(TreeNode node){
        TreeNode pre = null;
        TreeNode next = null;
        while(node != null){
            next = node.right;
            node.right = pre;
            pre = node;
            node = next;
        }
        return pre;
    }
}
