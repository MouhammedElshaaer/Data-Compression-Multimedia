import java.util.Vector;

public class Node {



    private Vector<Float>       dataList;
    private Vector<Integer>     splittingValues;
    private Vector<Float>       averages;
    private Vector<Node>        associatedBlocks;

    public Node (){

        dataList        = new Vector(1);
        splittingValues = new Vector(1);
        averages        = new Vector(1);
        associatedBlocks= new Vector(1);
    }
    public Node (Vector<Float> _average, Vector<Integer> _splittingValue){

        splittingValues = new Vector(1);
        averages        = new Vector(1);
        dataList        = new Vector(1);
        associatedBlocks= new Vector(1);

        for (Float value:_average) {
            averages.add(value);
        }

        for (Integer value:_splittingValue) {
            splittingValues.add(value);
        }

    }


    public Vector<Float> getDataList() {
        return dataList;
    }
    public void setDataList(Vector<Float> dataList) {

        this.dataList = dataList;
    }
    public Vector<Integer> getSplittingValues() {
        return splittingValues;
    }
    public void setSplittingValues(Vector<Integer> splittingValues) {
        this.splittingValues = splittingValues;
    }
    public Vector<Float> getAverages() {
        return averages;
    }
    public void setAverages(Vector<Float> averages) {

        this.averages = averages;
        //setDataList(averages);
    }
    public Vector<Node> getAssociatedBlocks() {
        return associatedBlocks;
    }
    public void setAssociatedBlocks(Vector<Node> _associatedBlocks) {
        for (Node _node:_associatedBlocks) {
            associatedBlocks.add(_node);
        }
    }

    public void display(){
        /*System.out.print("dataList: ");
        System.out.println(dataList);
        System.out.print("Splitting values :");
        System.out.println(splittingValues);*/
        System.out.print("Averages :");
        System.out.println(averages);
        System.out.println("");
    }
}


