package Tree.BTree.Search;

import Tree.BTree.NodeChecker;

public class SearchLess<T extends Comparable<T>> extends NodeChecker<T> {
    public T MinVal;
    @Override
    public boolean isKeyRequired(T key) {
        return key.compareTo(MinVal)<0;
    }

    @Override
    public boolean isNeedToCheckChild(T key) {
        return key.compareTo(MinVal)<=0;

    }

    @Override
    public boolean isNeedToStop(T key) {
        return key.compareTo(MinVal)>0;

    }
    public SearchLess(T minVal){
        this.MinVal =minVal;
    }
}
