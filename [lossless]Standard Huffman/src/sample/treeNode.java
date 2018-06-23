package sample;

public class treeNode {
    public treeNode     leftChild,
            rightChild;

    public String       symbolChar,
            symbolCode;

    public int          symbolFreq;

    //default constructor
    public treeNode(){

        //this.leftChild=new treeNode();
        //this.leftChild=null;
        //this.rightChild=new treeNode();
        //this.rightChild=null;

        symbolChar="";  symbolCode="";

        symbolFreq=0;

    }
    //Parametrized constructor
    public treeNode(String _symbolChar, String _symbolCode, int _symbolFreq){

        //this.leftChild=new treeNode();
        //this.rightChild=new treeNode();

        symbolChar=_symbolChar;  symbolCode=_symbolCode;

        symbolFreq=_symbolFreq;

    }

    public void display(){

        System.out.println("Symbol: "+symbolChar+" Frequency: "+symbolFreq);

    }//end of display() method

}//end of treeNode class


