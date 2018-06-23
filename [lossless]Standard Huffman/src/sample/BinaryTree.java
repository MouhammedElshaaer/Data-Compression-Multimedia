package sample;

public class BinaryTree {

    private treeNode root;

    //default constructor
    public BinaryTree(){
        root=null;
    }
    //parametrized constructor
    public BinaryTree(treeNode _root){
        this.root=_root;
    }

    public treeNode getTreeNode(){return this.root;}

}//end of BinaryTree class
