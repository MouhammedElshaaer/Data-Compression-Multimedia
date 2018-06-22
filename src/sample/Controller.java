package sample;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;

public class Controller {
    private MyFile file;
    public TextField field;
    private String userInput;
    public Button   compress,
                    decompress;

    public Controller(){
        file=new MyFile();
    }

    public void compress()throws IOException{
        userInput= field.getText();
        file.setData(userInput);
        file.compressFile();

    }

    public void decompress() {
        file.decompressFile();
    }
}
