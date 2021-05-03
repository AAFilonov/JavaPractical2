package SearchParams;

public class SearchEqual<T> extends  SearchParams{
    T Val;
    @Override
    void checkKey() {

    }

    @Override
    void checkNode() {

    }

    public SearchEqual(T val){
        super();
        this.Val = val;
    }
}
