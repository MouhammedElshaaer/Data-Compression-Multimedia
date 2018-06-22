package sample;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;

public class Controller {
    private MyFile      file;
    public  TextField   field;
    public  String      userInput;
    public  Button      compress,
                        decompress;

    public Controller(){
        file=new MyFile();
    }

    public void compress()throws IOException{
        userInput= field.getText();
        file.setData(userInput);
        file.compressFile();
        field.clear();
    }

    public void decompress() throws IOException{
        file.decompressFile();
    }
}
