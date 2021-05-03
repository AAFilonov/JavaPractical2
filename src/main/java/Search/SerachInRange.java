package SearchParams;

public class SerachInRange<T> extends SearchParams {
    public T minVal, maxVal;

    @Override
    void checkKey() {

    }

    @Override
    void checkNode() {

    }
    public SerachInRange(T minVal, T maxVal){
        super();
        this.minVal = minVal;
        this.maxVal = maxVal;
    }
}
