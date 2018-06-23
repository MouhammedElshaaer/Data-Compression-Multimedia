package sample;
import java.io.*;
import java.lang.reflect.Array;

public class MyFile {

    private int[]       ASCII_Freq_ARRAY;
    public String[]    ASCII_SHORT_CODE_ARRAY;

    private String 	data,
            symbols,
            freqTable,
            compressedFile,
            decompressedFile;


    //constructor
    public MyFile() {
        this.data="";
        this.symbols="";
        this.freqTable="";
        this.ASCII_Freq_ARRAY=new int[128];
        this.ASCII_SHORT_CODE_ARRAY=new String[128];
        this.compressedFile = "myFiles\\compressedFile.txt";
        this.decompressedFile = "myFiles\\decompressedFile.txt";
    }//end of constructor

    public void setData(String _data){this.data=_data;}
    public void getFreqTable(){System.out.println("freqTable: "+freqTable);}
    public void writeString(String data,File file) throws  IOException{
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
        out.println(data);
        out.close();

    }//end of writeString() method

    public void updateSymbolCode(treeNode node, String _symbolCode){

        if(node==null)
        {
            return;
        }else{
            node.symbolCode=_symbolCode;
            updateSymbolCode(node.leftChild,node.symbolCode+"0");
            node.symbolCode=_symbolCode;
            updateSymbolCode(node.rightChild,node.symbolCode+"1");
        }
    }//end of updateSymbolCode() method
    public void constructFreqTable(){

        for(int i=0;i<symbols.length();i++){
            String symbolCode=Integer.toString(ASCII_Freq_ARRAY[(int)symbols.charAt(i)]),
                    symbolChar=Character.toString(symbols.charAt(i));
            freqTable+=symbolChar+","+symbolCode+((i==symbols.length()-1)?"":"#");
        }

    }//end of constructFreqTable() method
    public linkedList constructFreqNodeList(){

        //Calculate Symbol Frequency
        for(int i=0;i<data.length();i++){
            ASCII_Freq_ARRAY[(int)(data.charAt(i) ) ]++;

            if(symbols.indexOf(data.charAt(i))==-1){
                symbols+=Character.toString(data.charAt(i));
            }
        }

        //Create list of treeNodes
        linkedList list=new linkedList();
        for(int i=0;i<symbols.length();i++){

            list.sortedInsertion(new treeNode(  Character.toString(symbols.charAt(i)) ,
                    "",
                    ASCII_Freq_ARRAY[(int)(symbols.charAt(i) ) ]));
        }



        return list;
    }//end of constructFreqNodeList() method
    public BinaryTree constructTree(linkedList list){

        while(list.head!=list.tail){
            linkedListNode  lastListNode=list.tail,
                    preLastListNode=lastListNode.prevNode;
            treeNode data=new treeNode( "",
                    "",
                    lastListNode.data.symbolFreq+preLastListNode.data.symbolFreq);

            data.leftChild=preLastListNode.data;    data.rightChild=lastListNode.data;

            updateSymbolCode(data.leftChild,"0");   updateSymbolCode(data.rightChild,"1");

            list.removeLastNode();
            list.removeLastNode();
            list.sortedInsertion(data);
        }
        BinaryTree tree=new BinaryTree(list.head.data);
        getSymbolsShortCode(tree.getTreeNode().leftChild);
        getSymbolsShortCode(tree.getTreeNode().rightChild);
        constructFreqTable();
        return tree;
    }//end of constructTree() method
    public void getSymbolsShortCode(treeNode currNode){

        if(currNode.leftChild==null&&currNode.rightChild==null){
            ASCII_SHORT_CODE_ARRAY[(int) (currNode.symbolChar.charAt(0) ) ]=currNode.symbolCode;
            return;
        }else{
            getSymbolsShortCode(currNode.leftChild);
            getSymbolsShortCode(currNode.rightChild);
        }
    }//end of getSymbolsShortCode() method

    public void compressFile() throws IOException{
        File file=new File(compressedFile);
        String compressedData="";

        for(int i=0;i<data.length();i++){
            compressedData+=ASCII_SHORT_CODE_ARRAY[(int)(data.charAt(i))];
        }
        writeString(freqTable,file);
        writeString(compressedData,file);
        System.out.println("compressedData: "+compressedData);
    }
    public void decompressFile()throws IOException{
        File file=new File(compressedFile);
        BufferedReader in=new BufferedReader(new FileReader(file));
        String  symbolFreq      =in.readLine(),
                compressedData  =in.readLine(),
                decompressedData="",
                check="";
        linkedList list=new linkedList();
        in.close();

        String[] shortCodeArray=symbolFreq.split("#");
        for(int j=0;j< Array.getLength(shortCodeArray);j++){
            String[] arr=shortCodeArray[j].split(",");
            list.sortedInsertion(new treeNode(arr[0],"",Integer.parseInt(arr[1])));
        }
        BinaryTree tree=constructTree(list);

        int i=0;
        while (i<compressedData.length()){
            check+=Character.toString(compressedData.charAt(i));

            boolean exists=false;
            for(int j=0;j<128;j++){
                if(check.equals(ASCII_SHORT_CODE_ARRAY[j])){
                    decompressedData+=(char)j;
                    exists=true;    break;
                }
            }
            if(exists==true) {
                check = "";
            }
            i++;
        }
        System.out.println("decompressedData: "+decompressedData);
        writeString(decompressedData,new File(decompressedFile));
    }

}//end of MyFile class
