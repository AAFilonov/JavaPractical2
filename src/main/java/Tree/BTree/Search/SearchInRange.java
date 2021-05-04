package Tree.BTree.Search;


import Tree.BTree.NodeChecker;

public class SearchInRange<T extends Comparable<T>> extends NodeChecker<T>  {
    public T MinVal, MaxVal;

    @Override
    public boolean isKeyRequired(T key) {
        return ( key.compareTo(MinVal) >=0&&key.compareTo(MaxVal) <=0);
    }

    @Override
    public boolean isNeedToCheckChild(T key)
    {
        return key.compareTo(MinVal)>=0;
    }

    @Override
    public boolean isNeedToStop(T key) {
        return key.compareTo(MaxVal)>=0;
    }

    public SearchInRange(T minVal, T maxVal){
        super();
        this.MinVal =minVal;
        this.MaxVal =maxVal;
    }
}
