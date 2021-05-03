package Tree.BTree;

import java.util.ArrayList;
import java.util.Collection;

public abstract class NodeChecker<T extends Comparable<T>> {

    public Collection<T> returnCollection;


    public abstract boolean isKeyRequired(T key);
    public abstract boolean isNeedToCheckChild(T key);
    public abstract boolean isNeedToStop();

    public NodeChecker(){
        returnCollection = new ArrayList<>();
    }

}
