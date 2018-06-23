package sample;
import java.util.Vector;

public class treeNode {

    private float average;
    private int nodeLevel;

    private Vector<Integer> dataList;
    private treeNode    leftChild,
            rightChild;

    public treeNode(){
        average=0;
        nodeLevel=0;
        dataList = new Vector<Integer>(1);
        leftChild= null;
        rightChild=null;
    }
    public treeNode(float _average, int _nodeLevel, treeNode _leftChild, treeNode _rightChild){
        average=_average;
        nodeLevel=_nodeLevel;
        leftChild=_leftChild;
        rightChild=_rightChild;
        dataList=new Vector<Integer>(1);
    }

    public float getAverage() {
        return average;
    }
    public void setAverage(float _average) {
        this.average = _average;
    }
    public int getNodeLevel() {
        return nodeLevel;
    }
    public treeNode getLeftChild() {
        return leftChild;
    }
    public void setLeftChild(treeNode _leftChild) {
        this.leftChild = _leftChild;
    }
    public treeNode getRightChild() {
        return rightChild;
    }
    public void setRightChild(treeNode _rightChild) {
        this.rightChild = _rightChild;
    }
    public Vector<Integer> getDataList() {
        return dataList;
    }
    public void setDataList(Vector<Integer> dataList) {
        this.dataList = dataList;
    }

    public void display(){
        System.out.println("av. :"+this.average+" nodeLevel: "+this.nodeLevel);
        System.out.print("dataList: ");
        for (Integer value:this.dataList) {
            System.out.print(value+" ");
        }
        System.out.println("");
    }
}


