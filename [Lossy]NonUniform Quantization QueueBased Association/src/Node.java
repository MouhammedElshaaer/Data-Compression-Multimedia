import java.util.Vector;

public class Node {

    private float average;
    private int splittingValue;

    private Vector<Integer> dataList;


    public Node (){
        average=0;
        splittingValue=0;
        dataList = new Vector<Integer>(1);
    }
    public Node (float _average, int _splittingValue){
        average=_average;
        splittingValue=_splittingValue;
        dataList=new Vector<Integer>(1);
    }

    public float getAverage() {
        return average;
    }
    public void setAverage(float _average) {
        this.average = _average;
    }
    public Vector<Integer> getDataList() {
        return dataList;
    }
    public void setDataList(Vector<Integer> dataList) {
        this.dataList = dataList;
    }
    public int getSplittingValue() {
        return splittingValue;
    }
    public void setSplittingValue(int splittingValue) {
        this.splittingValue = splittingValue;
    }

    public void display(){
        System.out.println("av. :"+this.average);
        System.out.print("dataList: ");
        for (Integer value:this.dataList) {
            System.out.print(value+" ");
        }
        System.out.println("");
    }
}


