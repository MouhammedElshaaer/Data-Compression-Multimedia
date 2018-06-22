package sample;
import java.io.Serializable;


public class Tag implements Serializable {
    //attributes
    private int  pointer;

    //constructors
    public Tag(){pointer=0;}
    public Tag(int _pointer){this.pointer=_pointer;}

    //setters and getters
    public int getPointer() {return pointer;}

}//end of Tag class
