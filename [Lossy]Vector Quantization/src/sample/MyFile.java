package sample;
import java.io.*;
import java.lang.reflect.Array;
import java.util.Vector;

public class MyFile {

    private Node root;
    public Vector<Node>  data;
    public Vector<Node>  Q_Inverse_list;
    public String   compressedFile,
            decompressedFile;
    private int nodesNumber,vectorSize,vectorRows,vectorCols,imgHeight,imgWidth;//codeBookSize

    public MyFile(){
        nodesNumber     =0;
        //codeBookSize    =0;
        vectorSize      =0;
        imgHeight       =0;
        imgWidth        =0;
        data            =new Vector(1);
        Q_Inverse_list  =new Vector(1);
        this.compressedFile = "myFiles/compressedFile.txt";
        this.decompressedFile = "myFiles/decompressedFile.txt";

    }

    public void setData(Vector<Node> _data){
        for (Node _node:_data) {
            data.add(_node);
        }
        setRoot();
    }
    public void setNodesNumber(int bitsNumber) {
        int _nodesNumber= (int)Math.pow(2,bitsNumber+1)-1;
        this.nodesNumber = _nodesNumber;
    }
    public void setBlockSize(int rows,int cols){

        vectorSize=rows*cols;     vectorRows=rows;      vectorCols=cols;

    }
    public void setRoot(){

        root=new Node();//dataAverage(data,blockSize),new Vector<>(1)
        root.setAssociatedBlocks(data);
        root.setAverages(dataAverage(root.getAssociatedBlocks(),vectorSize));
    }
    public Node getRoot() {
        return root;
    }

    public void writeString(String data,File file) throws IOException {
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
        out.println(data);
        out.close();

    }//end of writeString() method
    public int getVariance(Node valueNode,Node _node){
        int variance=0;
        for(int i=0;i<vectorSize;i++){

            variance+=(int)Math.pow((double) (valueNode.getDataList().elementAt(i)-_node.getSplittingValues().elementAt(i)),2);

        }
        return  variance;
    }
    public Node getLeastVarianceNode(Node value,Node _node,Node leastVarianceNode){
        if(leastVarianceNode==null){
            return _node;
        }else if(getVariance(value,_node)<=getVariance(value,leastVarianceNode)){
            return _node;
        }else{
            return leastVarianceNode;
        }
    }
    public Node getNearestVector(Node originalVector){
        Node nearestVector=null;
        for(Node Q_Inverse_Node:Q_Inverse_list){
            nearestVector=getLeastVarianceNode(originalVector,Q_Inverse_Node,nearestVector);
        }
        return nearestVector;
    }
    public void associate(Vector<Node> nodesVector, Vector<Node> dataList){
        for(Node _dataListNode:dataList){
            Node leastVarianceNode=null;
            for(Node _node:nodesVector){
                leastVarianceNode=getLeastVarianceNode(_dataListNode,_node,leastVarianceNode);
            }
            leastVarianceNode.getAssociatedBlocks().add(_dataListNode);
        }
    }
    public Vector<Integer> getLeftSplittingValues(Node parentNode){

        Vector<Integer> _leftSplittingValues=new Vector(1);

        for(int i=0;i<vectorSize;i++){
            int splittingValue=(int)(Math.ceil((double)(parentNode.getAverages().elementAt(i)-(float)1)));
            _leftSplittingValues.add(splittingValue);
        }

        return _leftSplittingValues;
    }
    public Vector<Integer> getRightSplittingValues(Node parentNode){

        Vector<Integer> _rightSplittingValues=new Vector(1);

        for(int i=0;i<vectorSize;i++){
            int splittingValue=(int)(Math.floor((double)(parentNode.getAverages().elementAt(i)+(float)1)));
            _rightSplittingValues.add(splittingValue);
        }

        return _rightSplittingValues;
    }
    public Vector<Float> dataAverage(Vector<Node> associatedBlocks,int blockSize){

        Vector<Float> blocksAverage=new Vector(1);
        for(int i=0;i<blockSize;i++){   //blockSize=row*col
            float average=0;
            for (Node _node:associatedBlocks) {
                average+=_node.getDataList().elementAt(i);
            }
            blocksAverage.add(average/associatedBlocks.size());
        }
        return blocksAverage;
    }
    public void LBG_SPLITTING(){
        Vector<Node> treeVector=new Vector(1);
        treeVector.add(root);
        for(int i=1,blockSize=1;i<nodesNumber;i+=(int)Math.pow(2,blockSize),blockSize++){
            Vector<Node> tempNodesVec=new Vector(1);
            for(int j=i;j<i+Math.pow(2,blockSize);j+=2){//Math.pow(2,i)
                Node parentNode=treeVector.elementAt((int)Math.ceil((double)((j-1)/2)));
                Vector<Integer> leftChildSplittingValues=getLeftSplittingValues(parentNode);
                Vector<Integer> rightChildSplittingValues=getRightSplittingValues(parentNode);
                Node leftChild  =new Node(new Vector(1),leftChildSplittingValues);
                Node rightChild =new Node(new Vector(1),rightChildSplittingValues);
                tempNodesVec.add(leftChild);    tempNodesVec.add(rightChild);
            }
            associate(tempNodesVec,data);

            for (Node _node:tempNodesVec) {
                _node.setAverages(dataAverage(_node.getAssociatedBlocks(),vectorSize));
                treeVector.add(_node);
            }
            if(i==nodesNumber-(int)Math.pow(2,blockSize)){
                for (Node _node:tempNodesVec) {
                    if(_node.getAssociatedBlocks().size()>0){
                        Node Q_INVERSE_NODE=new Node();
                        Q_INVERSE_NODE.setAverages(_node.getAverages());
                        Q_INVERSE_NODE.setSplittingValues(_node.getSplittingValues());
                        Q_Inverse_list.add(Q_INVERSE_NODE);
                    }
                }
            }
        }
        /*for (Node _node:Q_Inverse_list) {
            _node.display();
        }*/
    }
    public Vector<Node> getOriginalImgBlocks(int[][] imgPixels){

        Vector<Node> originalImgBlocks=new Vector<>(1);
        imgHeight=(imgPixels.length%vectorRows!=0)?imgPixels.length-(imgPixels.length%vectorRows):imgPixels.length;
        imgWidth=(imgPixels[0].length%vectorCols!=0)?imgPixels[0].length-(imgPixels[0].length%vectorCols):imgPixels[0].length;

        for(int x=0;x<imgHeight;x+=vectorRows){
            for(int y=0;y<imgWidth;y+=vectorCols){
                Node imgBlock=new Node();
                for(int i=x;i<x+vectorRows;i++){
                    for(int j=y;j<y+vectorCols;j++){
                        imgBlock.getDataList().add((float)imgPixels[i][j]);
                    }
                }
                originalImgBlocks.add(imgBlock);
            }
        }

        return originalImgBlocks;
    }

    public void compressFile()throws IOException{

        LBG_SPLITTING();
        String compressedCode="";
        for (int i=0;i<data.size();i++) {
            compressedCode+=Integer.toString(Q_Inverse_list.indexOf(getNearestVector(data.elementAt(i))));
            if(i!=data.size()-1){
                compressedCode+=",";
            }
        }
        writeString(compressedCode,new File(compressedFile));
        System.out.println("compressedCode: "+compressedCode);
    }
    public void decompressFile()throws IOException{

        String  dataList;
        File file=new File(compressedFile);
        BufferedReader in=new BufferedReader(new FileReader(file));
        dataList=in.readLine();
        String[] compressedCode=dataList.split(",");
        Vector<Node> compressedImgBlocks=new Vector(1);
        for(int i=0;i<Array.getLength(compressedCode);i++){
            compressedImgBlocks.add(Q_Inverse_list.elementAt(Integer.parseInt(compressedCode[i])));
        }

        int[][] compressedImgPixels=new int[imgHeight][imgWidth];

        for(int x=0;x<imgHeight;x+=vectorRows){
            for(int y=0;y<imgWidth;y+=vectorCols){
                int index=0;
                Node _node=compressedImgBlocks.remove(0);
                for(int i=x;i<x+vectorRows;i++){
                    for(int j=y;j<y+vectorCols;j++){
                        compressedImgPixels[i][j]=(_node.getAverages().elementAt(index++)).intValue();
                    }
                }
            }
        }
        ImageClass.writeImage(compressedImgPixels,"myFiles\\cameraMan_out.jpg");

    }
}
