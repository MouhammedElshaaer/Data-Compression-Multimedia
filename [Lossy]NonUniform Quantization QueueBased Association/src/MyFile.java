import java.io.*;
import java.lang.reflect.Array;
import java.util.Vector;

public class MyFile {

    private Node root;
    public Vector<Integer>  data,
            Q_Inverse_list;
    private Vector<Range> RANGES;
    public String   compressedFile,
            decompressedFile;
    private int nodesNumber;

    public MyFile(){
        data=new Vector<Integer>(1);
        Q_Inverse_list=new Vector<Integer>(1);
        RANGES=new Vector<Range>(1);
        this.compressedFile = "myFiles/compressedFile.txt";
        this.decompressedFile = "myFiles/decompressedFile.txt";
        nodesNumber=0;
    }

    public void setData(Vector<Integer> _data){
        for (Integer value:_data) {
            data.add(value);
        }
        setRoot();

    }
    public void setNodesNumberNumber(int bitsNumber) {
        int _nodesNumber= (int)Math.pow(2,bitsNumber+1)-1;
        this.nodesNumber = _nodesNumber;
    }
    public void setRoot(){

        root=new Node(dataAverage(data),0);
        root.setDataList(data);
    }
    public Node getRoot() {
        return root;
    }

    public void writeString(String data,File file) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
        out.println(data);
        out.close();

    }//end of writeString() method
    public static int getVariance(Integer value,Node _node){
        return  (int)Math.pow((double) (value-_node.getSplittingValue()),2);
    }
    public static Node getLeastVarianceNode(Integer value,Node _node,Node leastVarianceNode){
        if(leastVarianceNode==null){
            return _node;
        }else if(getVariance(value,_node)<getVariance(value,leastVarianceNode)){
            return _node;
        }else{
            return leastVarianceNode;
        }
    }
    public static void associate(Vector<Node> nodesVector, Vector<Integer> dataList){
        for(Integer value:dataList){
            Node leastVarianceNode=null;
            for(Node _node:nodesVector){
                leastVarianceNode=getLeastVarianceNode(value,_node,leastVarianceNode);
            }
            leastVarianceNode.getDataList().add(value);
        }
    }
    public float dataAverage(Vector<Integer> dataList){
        float average=0;
        for (Integer value:dataList) {
            average+=(float)value;
        }
        return (float)(average/dataList.size());
    }
    public void LBG_SPLITTING(){
        Vector<Node> treeVector=new Vector(1);
        treeVector.add(root);
        for(int i=1,blockSize=1;i<nodesNumber;i+=(int)Math.pow(2,blockSize),blockSize++){
            Vector<Node> tempNodesVec=new Vector<Node>(1);
            for(int j=i;j<i+Math.pow(2,blockSize);j+=2){//Math.pow(2,i)
                Node parentNode=treeVector.elementAt((int)Math.ceil((double)((j-1)/2)));
                int leftChildSplittingValue=(int)(Math.ceil((double)(parentNode.getAverage()-(float)1)));
                int rightChildSplittingValue=(int)(Math.floor((double)(parentNode.getAverage()+(float)1)));
                Node leftChild=new Node(0,leftChildSplittingValue);
                Node rightChild=new Node(0,rightChildSplittingValue);
                tempNodesVec.add(leftChild);    tempNodesVec.add(rightChild);
            }
            associate(tempNodesVec,data);

            for (Node _node:tempNodesVec) {
                _node.setAverage(dataAverage(_node.getDataList()));
                treeVector.add(_node);
            }
            if(i==nodesNumber-(int)Math.pow(2,blockSize)){
                for (Node _node:tempNodesVec) {
                    if(_node.getDataList().size()>0){
                        Q_Inverse_list.add((int)_node.getAverage());
                    }
                }
            }
        }

        System.out.println("The Q Inverse: "+Q_Inverse_list);
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

        LBG_SPLITTING();
        constructRanges();
        String compressedCode="";
        for (int i=0;i<data.size();i++) {
            for(int j=0;j<RANGES.size();j++){
                if(RANGES.elementAt(j).exists((float)data.elementAt(i))){
                    compressedCode+=Integer.toString(j);
                    break;
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
