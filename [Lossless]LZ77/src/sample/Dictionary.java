package sample;

import java.io.*;

public class Dictionary {
    private String data;

    Dictionary(){
        this.data="";
    }

    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public void addLine(String line){
        this.data+=line;
    }

    public boolean exists(String check){
        if (this.data.indexOf(check)!=-1)
            return true;
        else
            return false;
    }
}
