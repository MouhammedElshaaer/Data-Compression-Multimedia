package sample;

import java.io.*;
import java.lang.reflect.Array;

public class MyFile{
    static int numberOfTags;
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
    public void writeOutputData(String data){
        try{
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File(decompressedFile))));
            out.println(data);
            out.close();
        }
        catch (IOException e){
            System.out.println("I/O error");
        }
    }//end of writeOutputData() method
    private  void writeTagToFile(Tag myTag,ObjectOutputStream out){//
        try{
            numberOfTags++;
            out.writeObject(myTag);
        }
        catch (IOException e){
            System.out.println("I/O error");
        }
    }//end of writeTagToFile() method
    public Tag[] getAllTags(String mode){ //return Tags array mode "getArray" or
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
                    System.out.println(myTag.getPointer() + ","+myTag.getLength()+","+myTag.getNextChar());
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

    public void compressFile() throws IOException {
        Dictionary myDict=new Dictionary();
        ObjectOutputStream out= openFile(compressedFile);
        int i=0;
        while(i<data.length())
        {
            String check = Character.toString(data.charAt(i));
            if(!myDict.exists(check)){
                Tag myTag=new Tag(0,0,check);   myDict.addLine(check);  i++;
                this.writeTagToFile(myTag,out);
            }//end of if statement
            else{
                char ch=check.charAt(0);
                int counter=1;
                while( (counter+i<data.length()) && (ch==data.charAt(i+counter)) )
                {check+=ch;counter++;}

                //recursive case
                if(check.length()>2){
                    File file=new File("txt.txt");
                    int     pointer =   i-myDict.getData().lastIndexOf(ch),
                            length  =   check.length();
                    String  nextChar=   Character.toString( data.charAt(i+length) );

                    Tag myTag=new Tag(pointer,length,nextChar);
                    this.writeTagToFile(myTag,out);

                    myDict.addLine(check);  myDict.addLine(nextChar);

                    i+=length+1;
                }//end of recursive case

                //non recursive case
                else{
                    int j=i+check.length();
                    while( (myDict.exists(check)) && (j<data.length()) )
                    {check+=Character.toString(data.charAt(j++));}
                    String existingStr=(check.length()==1)?check:check.substring(0,check.length()-1);

                    int     pointer =   i-myDict.getData().lastIndexOf(existingStr),
                            length  =   existingStr.length();
                    String  nextChar=   (check.length()==1)?"":Character.toString( check.charAt(check.length()-1) );

                    Tag myTag=new Tag(pointer,length,nextChar);
                    this.writeTagToFile(myTag,out);

                    myDict.addLine(check);

                    i=j;
                }//end of non recursive case

            }//end of else statement
        }//end of while loop
        out.close();
        System.out.println("The Dictionary: "+myDict.getData());
        this.getAllTags("printTags");
    }//end of compressFile() method
    public void decompressFile(){
        Tag[] myTags= getAllTags("getArray");
        Dictionary myDict=new Dictionary();
        for(int i =0; i<Array.getLength(myTags); i++){

            Tag myTag = myTags[i];

            int     pointer =myTag.getPointer(),
                    length  =myTag.getLength();
            String  nextChar=myTag.getNextChar();

            //recursion case
            if(length>pointer){
                int dictIndex= myDict.getData().length()-pointer;
                for(int j=0; j<length;j++){myDict.addLine( Character.toString( myDict.getData().charAt(dictIndex) ) );}
                myDict.addLine(nextChar);
            }//end of recursion case

            //non recursion case
            else{
                int dictIndex= myDict.getData().length()-pointer;
                for(int j=0;j<length;j++){myDict.addLine( Character.toString( myDict.getData().charAt(dictIndex++) ) );}
                myDict.addLine(nextChar);
            }//end of non recursion case

        }

        writeOutputData(myDict.getData());
        System.out.println("The decompressed Dictionary: "+myDict.getData());

    }//end of decompressFile() method


}//end of MyFIle class

