public class Range {
    private Float  upperLimit,
            lowerLimit;
    public Range(){
        upperLimit=(float)0;   lowerLimit=(float)0;
    }
    public Range(Float _upperLimit, Float _lowerLimit){
        upperLimit=_upperLimit;   lowerLimit=_lowerLimit;
    }

    public double getUpperLimit() {
        return upperLimit;
    }
    public void setUpperLimit(Float _upperLimit) {
        this.upperLimit = _upperLimit;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }
    public void setLowerLimit(Float _lowerLimit) {
        this.lowerLimit = _lowerLimit;
    }

    public boolean exists(Float value){
        return (value>lowerLimit&&value<=upperLimit);
    }
    public void display(){
        System.out.println("lowerLimit: "+lowerLimit+" upperLimit: "+upperLimit);
    }
}
