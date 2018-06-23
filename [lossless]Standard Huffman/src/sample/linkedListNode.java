package sample;

public class linkedListNode {

    public linkedListNode   nextNode,
            prevNode;

    public treeNode data;

    //default constructor
    public linkedListNode(){
        this.data    =new treeNode();        this.data    =null;
    }
    //parametrized constructor
    public linkedListNode(treeNode _data){

        this.data    =new treeNode();        this.data    =_data;

    }

    public void display(){
        data.display();
    }//end of display() method

}//end of linkedListNode class
