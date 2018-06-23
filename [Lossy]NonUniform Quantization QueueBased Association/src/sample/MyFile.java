package sample;
import java.io.*;
import java.lang.reflect.Array;
import java.util.Vector;

public class MyFile {

    private treeNode root;
    public Vector<Integer> data,
            Q_Inverse_list;
    private Vector<Range> RANGES;
    public String   compressedFile,
            decompressedFile;
    private int treeLevelsNumber;

    public MyFile(){
        data=new Vector<Integer>(1);
        Q_Inverse_list=new Vector<Integer>(1);
        RANGES=new Vector<Range>(1);
        this.compressedFile = "myFiles/compressedFile.txt";
        this.decompressedFile = "myFiles/decompressedFile.txt";
        treeLevelsNumber=0;
    }

    public void setData(Vector<Integer> _data){
        for (Integer value:_data) {
            data.add(value);
        }
        //System.out.println(data);
        float average=this.dataAverage(data);
        root=new treeNode(average,0,null,null);
        root.setDataList(data);
    }

    public void setTreeLevelNumber(int bitsNumber) {
        int _treeLevelNumber= (int)(Math.log10(Math.pow(2,bitsNumber))/Math.log10(2))+1;
        this.treeLevelsNumber = _treeLevelNumber;
    }

    public treeNode getRoot() {
        return root;
    }

    public void writeString(String data,File file) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
        out.println(data);
        out.close();

    }//end of writeString() method
    public int minVariance(int dataValue, int leftNodeAverage, int rightNodeAverage){
        double  variance_1=Math.pow((double) (dataValue-leftNodeAverage),2),
                variance_2=Math.pow((double) (dataValue-rightNodeAverage),2);
        return Math.min(variance_1,variance_2)==variance_1?leftNodeAverage:rightNodeAverage;
    }
    public float dataAverage(Vector<Integer> dataList){
        float average=0;
        for (Integer value:dataList) {
            average+=(float)value;
        }
        return (float)(average/dataList.size());
    }
    public void constructBinaryTree(treeNode currNode,int maxLevel){
        if(currNode.getNodeLevel()==maxLevel){
            return;
        }else{
            int numLessCurrNodeAv=(int)(Math.ceil((double)(currNode.getAverage()-(float)1)));
            int numMoreCurrNodeAv=(int)(Math.floor((double)(currNode.getAverage()+(float)1)));
            treeNode leftChild=new treeNode((float)0, currNode.getNodeLevel()+1,null,null);
            treeNode rightChild=new treeNode((float)0, currNode.getNodeLevel()+1,null,null);
            for (Integer value:currNode.getDataList()) {
                if(minVariance(value,numLessCurrNodeAv,numMoreCurrNodeAv)==numLessCurrNodeAv){
                    leftChild.getDataList().add(value);
                }else {
                    rightChild.getDataList().add(value);
                }
            }
            float leftNodeAverage=dataAverage(leftChild.getDataList());
            float rightNodeAverage=dataAverage(rightChild.getDataList());
            leftChild.setAverage(leftNodeAverage);  rightChild.setAverage(rightNodeAverage);
            if(currNode.getNodeLevel()==maxLevel-1){
                Q_Inverse_list.add((int)currNode.getAverage());
            }
            currNode.setLeftChild(leftChild);
            currNode.setRightChild(rightChild);
            //recursion
            constructBinaryTree(currNode.getLeftChild(),maxLevel);
            constructBinaryTree(currNode.getRightChild(),maxLevel);
        }
    }
    public void constructRanges(){
        for(int i=0;i<Q_Inverse_list.size();i++){
            Float  upperLimit=(float)0,
                    lowerLimit=(float)0;
            if(i==0){
                lowerLimit=Float.NEGATIVE_INFINITY;
            }else {
                lowerLimit=(float)RANGES.elementAt(i-1).getUpperLimit();
            }
            if(i==Q_Inverse_list.size()-1){
                upperLimit=Float.POSITIVE_INFINITY;
            }else {
                upperLimit=(float)((float)(Q_Inverse_list.elementAt(i)+Q_Inverse_list.elementAt(i+1))/(float)2);
            }
            RANGES.add(new Range(upperLimit,lowerLimit));
        }
        for (Range r:RANGES) {
            r.display();
        }
    }

    public void compressFile()throws IOException{
        this.constructBinaryTree(this.getRoot(),treeLevelsNumber);
        this.constructRanges();
        String compressedCode="";
        for (int i=0;i<data.size();i++) {
            for(int j=0;j<RANGES.size();j++){
                if(RANGES.elementAt(j).exists((float)data.elementAt(i))){
                    compressedCode+=Integer.toString(j);
                }
            }
            if(i!=data.size()-1){
                compressedCode+=",";
            }
        }
        writeString(compressedCode,new File(compressedFile));
        System.out.println("compressedCode: "+compressedCode);
    }
    public void decompressFile()throws IOException{
        String  dataList,
                decompressedCode="";
        File file=new File(compressedFile);
        BufferedReader in=new BufferedReader(new FileReader(file));
        dataList=in.readLine();
        String[] compressedCode=dataList.split(",");
        for(int i = 0; i< Array.getLength(compressedCode);i++){
            decompressedCode+=Integer.toString(Q_Inverse_list.elementAt(Integer.parseInt(compressedCode[i])));
            if(i!=Array.getLength(compressedCode)-1){
                decompressedCode+=",";
            }
        }
        writeString(decompressedCode,new File(decompressedFile));
        System.out.println("decompressedCode: "+decompressedCode);
        System.out.println("Original data: "+data);
    }
}
