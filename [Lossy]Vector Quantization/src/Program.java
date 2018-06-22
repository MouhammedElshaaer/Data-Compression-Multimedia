import java.io.*;
import java.util.Vector;

public class Program {

    public static void main(String[] args) throws IOException {

        MyFile file=new MyFile();
        int[][] imgPixels=ImageClass.readImage("myFiles/cameraMan.jpg");
        file.setBlockSize(2,2);
        file.setNodesNumber(4);
        Vector<Node> originalImgBlocks=file.getOriginalImgBlocks(imgPixels);
        file.setData(originalImgBlocks);
        file.compressFile();
        file.decompressFile();

    }

}
