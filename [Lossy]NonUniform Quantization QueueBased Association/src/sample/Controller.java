package sample;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Vector;

public class Controller {
    private MyFile      file;
    public  TextField   inputData,
                        inputBits;
    public  String      userInput;
    public  int         bitsNumber;
    public  Button      compress,
                        decompress;

    public Controller(){
        file=new MyFile();
    }

    public void compress()throws IOException{
        //6,15,17,60,100,90,66,59,18,3,5,16,14,67,63,2,98,92
        userInput= inputData.getText();
        bitsNumber=Integer.parseInt(inputBits.getText());
        String[] arr=userInput.split(",");
        Vector<Integer> dataList=new Vector<Integer>(1);
        for(String str:arr){
            dataList.add(Integer.parseInt(str));
        }
        file.setData(dataList);
        file.setTreeLevelNumber(bitsNumber);
        file.compressFile();
    }

    public void decompress() throws IOException{
        file.decompressFile();
    }
}
