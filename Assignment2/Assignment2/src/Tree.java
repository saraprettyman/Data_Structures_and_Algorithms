import com.sun.source.tree.BinaryTree;

import java.util.*;

public class Tree<E extends Comparable<? super E>> {
    private BinaryTreeNode root;  // Root of tree
    private String name;     // Name of tree

    /**
     * Create an empty tree
     *
     * @param label Name of tree
     */
    public Tree(String label) {
        name = label;
    }

    /**
     * Create BST from ArrayList
     *
     * @param arr   List of elements to be added
     * @param label Name of tree
     */
    public Tree(ArrayList<E> arr, String label) {
        name = label;
        for (E key : arr) {
            insert(key);
        }
    }

    /**
     * Create BST from Array
     *
     * @param arr   List of elements to be added
     * @param label Name of  tree
     */
    public Tree(E[] arr, String label) {
        name = label;
        for (E key : arr) {
            insert(key);
        }
    }

    /**
     * Return a string containing the tree contents as a tree with one node per line
     */
    @Override
    public String toString() {
        // TODO:
        System.out.println(name);
        return toString(root);
    }

    // Helper method of toString
    private String toString(BinaryTreeNode node) {
        // TODO:
        if (node == null) return "";
        toString(node.right);
        if (node.parent == null){
            System.out.print(node.key +  "[no parent]\n");
        }else{
            printDepth(depth(node));
            System.out.print(node.key + "[" + node.parent.key + "]\n");}
        toString(node.left);
        return "";
    }
    /**
     * Return a string containing the tree contents as a single line
     */
    // Public method of inOrderToString()
    public String inOrderToString(){
        // TODO:
        System.out.print(name + ": ");
        ArrayList<BinaryTreeNode> inorderArrayList = inOrderTraversal(root, new ArrayList<>());
        StringBuilder sb = new StringBuilder();
        for (BinaryTreeNode element: inorderArrayList){
            sb.append(element.key);
            sb.append(" ");
        }
        return sb.toString();
    }
    // Helper Method of inOrderToString()
    private ArrayList<BinaryTreeNode> inOrderTraversal(BinaryTreeNode node, ArrayList<BinaryTreeNode> inOrder){
        if (node == null) {return null;}
        inOrderTraversal(node.left, inOrder);
        inOrder.add(node);
        inOrderTraversal(node.right, inOrder);
        return inOrder;
    }

    /**
     * reverse left and right children recursively
     */
    public void flip() {
        // TODO:
        flipTree(root);
    }
    // Helper method of flip
    private void flipTree(BinaryTreeNode node){
        if(node == null){return;}
        if(node.right == null && node.left == null){return;}
        BinaryTreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
        flipTree(node.left);
        flipTree(node.right);

    }

    /**
     * Returns the in-order successor of the specified node
     * @param node node from which to find the in-order successor
     */
    public BinaryTreeNode inOrderSuccessor(BinaryTreeNode node) {
        // TODO:
        if (node.right != null){
            node = node.right;
            node = inOrderSuccessorR(node);
            return node;
        }else {
            // here is where recursion occurs
            if (node.parent.key.compareTo(node.key)>0){
                return node.parent;
            }
            return node.parent.parent;
        }
    }
    // Helper method of inOrderSuccessor
    private BinaryTreeNode inOrderSuccessorR(BinaryTreeNode node){
        if (node.left == null){return node;}
        node = node.left;
        return inOrderSuccessorR(node);
    }

    /**
     * Counts number of nodes in specified level
     *
     * @param level Level in tree, root is zero
     * @return count of number of nodes at specified level
     */
    public int nodesInLevel(int level) {
        // TODO:
        if (level > height(root)){return 0;}
        return nodesInLevel(level, inOrderTraversal(root, new ArrayList<>()));
    }
    // Helper method of nodesInLevel
    private int nodesInLevel(int level, ArrayList<BinaryTreeNode> list){
        int count = 0;
        for (BinaryTreeNode element: list){
            if (level == depth(element)){
                count++;}
        }
        return count;
    }

    /**
     * Print all paths from root to leaves
     */
    public void printAllPaths() {
        // TODO:
        printAllPaths(root, "");
        System.out.println();
    }
    // Helper method of printAllPaths
    private void printAllPaths(BinaryTreeNode node, String path){
        // Define finding a leaf node
        if (node == null){return;}
        path = path + " " + node.key;
        if (node.right == null && node.left == null){
            System.out.println(path);
        }
        printAllPaths(node.left, path);
        printAllPaths(node.right, path);
    }
    /**
     * Counts all non-null binary search trees embedded in tree
     *
     * @return Count of embedded binary search trees
     */
    public int countBST() {
        // TODO:
        int count = 0;
        return countBST(count, root);
    }
    // Helper method of countBST
    private int countBST(int count, BinaryTreeNode node){
        // base case, if empty node
        if (node == null){return count;}
        // identifying leaf nodes
        if (node.right == null && node.left == null) {
            count ++;
            return count;}
        // definition of a binary tree
        if (node.right != null && node.left != null){
            if (node.right.key.compareTo(node.key) > 0 || node.left.key.compareTo(node.key) < 0){
                count++;
            }
        }else if(node.left != null && node.left.key.compareTo(node.key) < 0){
            count++;
        }else if(node.right != null && node.right.key.compareTo(node.key) > 0){
            count++;
        }
        count = countBST(count, node.right);
        count = countBST(count, node.left);
        return count;
    }



    /**
     * Insert into a bst tree; duplicates are allowed
     *
     * @param x the item to insert.
     */
    public void insert(E x) {
        root = insert(x, root, null);
    }

    public BinaryTreeNode getByKey(E key) {
        // TODO:
        return searchForKey(root, key);
    }
    // Helper method of getByKey
    private BinaryTreeNode searchForKey(BinaryTreeNode node, E key){
        if (node == null){return null;}
        if (node.key == key){return node;}
        if (node.key.compareTo(key)< 0){
            return searchForKey(node.right, key);}
        return searchForKey(node.left, key);
    }

    /**
     * Balance the tree
     */
    public void balanceTree() {
        // TODO:
        // Perform an in order transversal, store nodes in arraylist
        ArrayList<BinaryTreeNode> inOrder = inOrderTraversal(root, new ArrayList<>());
        // Initialization of variables
        root = balanceTree(inOrder, 0, inOrder.size()-1);
        // Binary Traversal of inOrder array
    }
    // Helper method of balanceTree
    private BinaryTreeNode balanceTree(ArrayList<BinaryTreeNode> inOrder, int low, int high) {
        if (low > high){return null;}
        // Calculate mid
        int mid = (low + high) / 2;
        // adding nodes to the middle and right
        BinaryTreeNode midNode = new BinaryTreeNode(inOrder.get(mid).key);
        midNode.right = balanceTree(inOrder, mid + 1, high);
        midNode.left = balanceTree(inOrder, low, mid - 1);
        // redefining parents of each node
        if (midNode.right != null){midNode.right.parent = midNode;}
        if (midNode.left != null){midNode.left.parent = midNode;}
        return midNode;
    }


    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryTreeNode insert(E x, BinaryTreeNode t, BinaryTreeNode parent) {
        if (t == null) return new BinaryTreeNode(x, null, null, parent);

        int compareResult = x.compareTo(t.key);
        if (compareResult < 0) {
            t.left = insert(x, t.left, t);
        } else {
            t.right = insert(x, t.right, t);
        }

        return t;
    }


    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     *          SIDE EFFECT: Sets local variable curr to be the node that is found
     * @return node containing the matched item.
     */
    private boolean contains(E x, BinaryTreeNode t) {
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.key);

        if (compareResult < 0)
            return contains(x, t.left);
        else if (compareResult > 0)
            return contains(x, t.right);
        else {
            return true;    // Match
        }
    }

    // Basic node stored in unbalanced binary trees
    public class BinaryTreeNode {
        E key;            // The data/key for the node
        BinaryTreeNode left;   // Left child
        BinaryTreeNode right;  // Right child
        BinaryTreeNode parent; //  Parent node

        // Constructors
        BinaryTreeNode(E theElement) {
            this(theElement, null, null, null);
        }

        BinaryTreeNode(E theElement, BinaryTreeNode lt, BinaryTreeNode rt, BinaryTreeNode pt) {
            key = theElement;
            left = lt;
            right = rt;
            parent = pt;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(key);
            if (parent == null) {
                sb.append("<>");
            } else {
                sb.append("<");
                sb.append(parent.key);
                sb.append(">");
            }
            return sb.toString();
        }
    }

    // Determine the depth of the node in question
    private int depth(BinaryTreeNode node){
        if (node == null){return -1;}
        return depth(node.parent) + 1;
    }

    // Assist function for toString method
    private void printDepth(int n){
        for (int i = 0; i < n; i++){
            System.out.print("  ");
        }
    }

    // Determine the height of the tree
    private int height(BinaryTreeNode node){
        if (node == null){return -1;}

        int leftEnd = height(node.left);
        int rightEnd = height(node.right);

        if (leftEnd > rightEnd){
            return (leftEnd + 1);
        }
        else{
            return rightEnd + 1;
        }
    }

}
