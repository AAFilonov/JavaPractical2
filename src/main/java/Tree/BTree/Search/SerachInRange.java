package Tree.BTree.Search;


import Tree.BTree.NodeChecker;

public class SerachInRange<T> extends NodeChecker {
    public T minVal, maxVal;

    @Override
    public boolean isKeyRequired(Comparable key) {
        return false;
    }

    @Override
    public boolean isNeedToCheckChild(Comparable key) {
        return false;
    }

    @Override
    public boolean isNeedToStop() {
        return false;
    }
}
