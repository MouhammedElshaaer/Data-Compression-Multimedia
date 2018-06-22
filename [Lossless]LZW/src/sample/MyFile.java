package sample;
import java.io.*;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Array;


public class MyFile {

    int numberOfTags;
    private String 	data,
            rawData,
            compressedFile,
            decompressedFile;

    //constructor
    public MyFile() {
        this.numberOfTags=0;
        this.data="";
        this.rawData = "myFiles/rawData.txt";
        this.compressedFile = "myFiles/compressedFile.txt";
        this.decompressedFile = "myFiles/decompressedFile.txt";
    }//end of constructor

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
        try{
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File(rawData))));
            out.println(data);
            out.close();
        }
        catch (IOException e){
            System.out.println("I/O error");
        }
    }//end of setData() method

    private static ObjectOutputStream openFile(String fileName){

        try{
            ObjectOutputStream infoToWrite=new ObjectOutputStream(
                    new FileOutputStream(fileName,true));
            return infoToWrite;
        }
        catch(IOException e){
            System.out.println("I/O error B");
            System.exit(0);
        }
        return null;
    }//end of createFile() method
    public void writeOutputData(String data) throws  IOException{

        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File(decompressedFile))));
        out.println(data);
        out.close();

    }//end of writeOutputData() method
    private  void writeTagToFile(Tag myTag,ObjectOutputStream out) throws IOException{

        numberOfTags++;
        out.writeObject(myTag);

    }//end of writeTagToFile() method
    public Tag[] getAllTags(String mode){
        //return Tags array mode "getArray" or
        // printing tags mode "printTags"
        boolean eof=false;
        Tag[] myTags=new Tag[numberOfTags];

        try{
            ObjectInputStream in=new ObjectInputStream(
                    new FileInputStream(compressedFile));
            //note that the serialization save serialized object in a .ser extension file
            int i =0;
            while(!eof){
                Tag myTag = (Tag) in.readObject();
                if(mode=="printTags"){
                    System.out.println( "< "+myTag.getPointer() +" >");
                }
                else{myTags[i++]=myTag;}
            }
            in.close();
            return myTags;
        }
        catch(EOFException e){
            eof=true;
            return myTags;
        }
        catch(IOException e){
            System.out.println("I/O error D");
            System.exit(0);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }//end of getAllTags() method

    public void compressFile()throws IOException{
        //data="ABAABABBAABAABAAAABABBBBBBBB";
        Dictionary myDict=new Dictionary(data.length());
        ObjectOutputStream out= openFile(compressedFile);
        int i = 0;
        while(i<data.length()-1){
            String check=Character.toString(data.charAt(i));
            int check_index = (int)(data.charAt(i));
            int j=i+1;

            while( ( (check.length()>1)?myDict.exists(check)!=-1:true )&&(j<data.length())){
                check+=Character.toString(data.charAt(j++));
                check_index= (myDict.exists(check)!=-1)?myDict.exists(check):check_index;
            }

            Tag myTag=new Tag(check_index); this.writeTagToFile(myTag,out);
            if(j<data.length()){myDict.addWord(check);}

            i=j-1;
        }
        out.close();
        myDict.getData();
        this.getAllTags("printTags");
    }//end of compressFile() method
    public void decompressFile() throws IOException{
        Tag[] myTags=getAllTags("getArray");
        Dictionary myDict=new Dictionary(data.length());
        String  myData="";

        for(int i=0;i<Array.getLength(myTags);i++){

            int     currDictIndex   =myTags[i].getPointer(),
                    prevDictIndex   =-1;

            String  currStr         =myDict.data[currDictIndex],
                    prevStr         ="";

            if(i>0){
                prevDictIndex   =(i==0)?0:myTags[i-1].getPointer();
                prevStr         =myDict.data[prevDictIndex];
            }

            //Empty location case handler
            if(currStr==null){
                currStr=prevStr+Character.toString(prevStr.charAt(0));
            }

            //update my data
            myData+=currStr;

            //update my dictionary
            myDict.addWord( (i==0)?"":prevStr + Character.toString(currStr.charAt(0)) );

        }
        System.out.println("myData: "+myData);
        this.writeOutputData(myData);

    }//end of decompressFile() method

}//end of MyFile Class
