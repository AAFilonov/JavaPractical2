package SearchParams;

import java.util.ArrayList;
import java.util.Collection;

public abstract class SearchParams<T> {

    public Collection<T> returnCollection;
    abstract void checkKey();
    abstract void checkNode();
    SearchParams(){
        returnCollection = new ArrayList<>();
    }

}
