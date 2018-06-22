package sample;

public class Dictionary {

    //Attributes
    public String[] data;
    final String  ASCII;
    private int firstAvailableIndex;

    //constructors
    public Dictionary(){
        ASCII = null;
        firstAvailableIndex=128;
    }
    public Dictionary(int dataSize){
        firstAvailableIndex=128;
        ASCII=" !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
        data=new String[127+( (dataSize>=128/2)?128/2:dataSize )];
        for(int i = 0 ;i<ASCII.length();i++){
            data[(int)(ASCII.charAt(i))] = Character.toString(ASCII.charAt(i));
        }
    }

    //methods
    public void getData(){
        for(int i = 128; i<firstAvailableIndex;i++){
            System.out.println(data[i]);
        }//end of for loop
    }//end of getData() method
    public void addWord(String word){
        if(word.length()>1) {
            data[firstAvailableIndex] = word;
            firstAvailableIndex++;
        }
    }//end of addWord() method
    public int exists(String word){
        for(int i = 128; i<firstAvailableIndex;i++){
            if(word.equals(data[i])){return i;}
        }//end of for loop
        return -1;
    }//end of exists() method

}//end of Dictionary class
