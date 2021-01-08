package learning;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TreeNode {

    int val;
    //左子树
    TreeNode left;
    //右子树
    TreeNode right;
    //构造方法
    TreeNode(int x) {
        val = x;
    }

    // 递归先序遍历
    public static void recursionPreorderTraversal(TreeNode root) {
        if (root != null) {
            System.out.print(root.val + " ");
            recursionPreorderTraversal(root.left);
            recursionPreorderTraversal(root.right);
        }
    }

    /**
     * 前序遍历
     * @param root
     * @return
     */
    public static List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> resList=new ArrayList<>();
        Stack<TreeNode> nodeStack=new Stack<>();
        TreeNode currentNode=root;
        while (currentNode !=null || !nodeStack.isEmpty()){
            while(currentNode !=null){
//                System.out.print(currentNode.val + " ");
                resList.add(currentNode.val);
                nodeStack.push(currentNode);
                currentNode=currentNode.left;
            }
            if(!nodeStack.isEmpty()){
                currentNode=nodeStack.pop();
                currentNode=currentNode.right;
            }
        }
        return resList;
    }

    /**
     * 中序遍历
     * @param root
     * @return
     */
    public static List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> resList=new ArrayList<>();
        Stack<TreeNode> nodeStack=new Stack<>();
        TreeNode currentNode=root;
        while (currentNode !=null || !nodeStack.isEmpty()){
            while(currentNode !=null){

                nodeStack.push(currentNode);
                currentNode=currentNode.left;
            }
            if(!nodeStack.isEmpty()){
                currentNode=nodeStack.pop();
                resList.add(currentNode.val);
                currentNode=currentNode.right;
            }
        }
        return resList;
    }

    /**
     * 后续遍历
     * @param root
     * @return
     */
    public static List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> resList=new ArrayList<>();
        Stack<TreeNode> nodeStack=new Stack<>();
        TreeNode currentNode=root;
        TreeNode lastVisit =root;
        while (currentNode !=null || !nodeStack.isEmpty()){
            while(currentNode !=null){
                nodeStack.push(currentNode);
                currentNode=currentNode.left;
            }
            currentNode =nodeStack.peek();
            if(currentNode.right==null || currentNode.right==lastVisit){
                nodeStack.pop();
                resList.add(currentNode.val);
                lastVisit =currentNode;
                currentNode=null;
            }else {
                currentNode=currentNode.right;
            }
        }
        return resList;
    }


    /**
     * 层序遍历
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> resList=new ArrayList<>();
        TreeNode currentNode=root;
        List<TreeNode> currentLevelList=new ArrayList<>();
        currentLevelList.add(currentNode);
        while (currentNode !=null && currentLevelList.size()>0){
            List<Integer> valList =new ArrayList<>();
            List<TreeNode> nextLevelList=new ArrayList<>();
            for(TreeNode node :currentLevelList){
                valList.add(node.val);
                if(node.left !=null){
                    nextLevelList.add(node.left);
                }
                if(node.right !=null){
                    nextLevelList.add(node.right);
                }
            }
            resList.add(valList);
            currentLevelList=nextLevelList;
        }
        return resList;
    }

    public static void main(String[] args) {
        TreeNode treeNode =new TreeNode(3);
        treeNode.left=new TreeNode(9);
        TreeNode right1 =new TreeNode(20);
        treeNode.right=right1;
        right1.left=new TreeNode(15);
        right1.right=new TreeNode(7);
        List<List<Integer>>  resList=levelOrder(treeNode);
        for (List<Integer> list:resList){
            System.out.println(list);
        }
    }
}
