package Tree.BTree.Search;

import Tree.BTree.NodeChecker;

public class SearchGreater<T extends Comparable<T>> extends NodeChecker<T>  {
    T MaxVal;
    @Override
    public boolean isKeyRequired(T key) {
        return key.compareTo(MaxVal)>0;
    }

    @Override
    public boolean isNeedToCheckChild(T key) {

        return key.compareTo(MaxVal)>0;
    }

    @Override
    public boolean isNeedToStop(T key) {
        return false;
    }

    public SearchGreater(T maxVal){
        super();this.MaxVal =maxVal;
    }
}
