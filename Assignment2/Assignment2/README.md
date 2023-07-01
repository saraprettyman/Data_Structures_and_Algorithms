# Assignment 2
The purpose of this assignment is demonstrate comprehension of binary trees. This is done in the `Tree.java` file. 

The generic `Tree` class consist of a few major functions.

*_toString(BinaryTreeNode node)_ takes in a BinaryTreeNode node and converts it into a string based off it's key value and it's parent. It is a recursive helper function to the _toString_ function, which allows the function to recurisve call the left and right nodes. The result of the function is a vertical representation of the tree like so:
``` 
Tree 1
    63[60]
  60[25]
      58[55]
        56[58]
    55[60]
      50[55]
25[no parent]
    14[10]
  10[25]
      9[8]
    8[10]
      6[8]

```

*_inOrderTraversal(BinaryTreeNode node, ArrayList<BinaryTreeNode> inOrder)_ recursively goes through a binary tree and returns an ArrayList of the nodes Inorder Traversed. 

*_inOrdertoString()_ uses the array created from the previous function to print out the resulting inorder traversal.

*_balanceTree()_ balances a binary tree so that the left and right subtree for any node has a height difference of one or 0. 

*inOrderSuccessor(BinaryTreeNode node) returns the in

*_printAllPaths()_ prints all paths from root to leaves of a binary tree.

There are more functions that calculate the depth, and height of a tree, insert nodes, and more. 

