package sample;

import java.io.Serializable;


public class Tag implements Serializable{

    private int pointer,
            length;
    private String nextChar;

    //Constructors
    public Tag(){
        this.pointer=0;
        this.length=0;
        this.nextChar=" ";
    }

    public Tag(int pointer, int length, String nextChar) {
        this.pointer = pointer;
        this.length = length;
        this.nextChar = nextChar;
    }
    //end of Constructors


    //getter and setters

    public int getPointer() {
        return pointer;
    }
    public void setPointer(int pointer) {
        this.pointer = pointer;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public String getNextChar() {
        return nextChar;
    }
    public void setNextChar(String nextChar) {
        this.nextChar = nextChar;
    }

    //end of getter and setters
}
