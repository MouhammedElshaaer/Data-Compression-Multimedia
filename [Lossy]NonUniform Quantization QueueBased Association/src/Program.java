import java.io.*;
import java.lang.reflect.Array;
import java.util.Vector;

public class Program {

    public static void main(String[] args) throws IOException {

        MyFile file=new MyFile();
        Vector<Integer> dataList=new Vector(1);
        Integer[] input={6,15,17,60,100,90,66,59,18,3,5,16,14,67,63,2,98,92};
        int bitsNumber=8;
        file.setNodesNumberNumber(bitsNumber);
        for (Integer value:input) {
            dataList.add(value);
        }
        file.setData(dataList);
        file.compressFile();
        file.decompressFile();

    }

}
