package sample;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Vector;

public class Controller {
    private MyFile      file;
    public  TextField   filePath,inputBits,blockSize;
    public  String      _filePath,_inputBits,_blockSize;
    public  int         bitsNumber,rows,cols;
    public  Button      compress,
                        decompress;

    public Controller(){
        file=new MyFile();
    }

    public void compress()throws IOException{
        _filePath   =filePath.getText();
        _inputBits  =inputBits.getText();
        _blockSize  =blockSize.getText();
        int[][] imgPixels=ImageClass.readImage(_filePath);//"myFiles/cameraMan.jpg"
        String[] arr=_blockSize.split(",");
        rows=Integer.parseInt(arr[0]);  cols=Integer.parseInt(arr[1]);
        file.setBlockSize(rows,cols);
        file.setNodesNumber(Integer.parseInt(_inputBits));
        Vector<Node> originalImgBlocks=file.getOriginalImgBlocks(imgPixels);
        file.setData(originalImgBlocks);
        file.compressFile();
    }

    public void decompress() throws IOException{
        file.decompressFile();
    }
}
