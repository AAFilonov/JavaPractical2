package Tree.BTree.Search;

import Tree.BTree.Node;
import Tree.BTree.NodeChecker;


public  class SearchEqual<T extends Comparable<T>> extends NodeChecker<T> {
    protected T Val;
    @Override
    public boolean isKeyRequired(T key) {
        return Val.compareTo(key) == 0;
    }

    //если искомое значение меньше ключа значит оно находится внутри этого потомка
    @Override
    public boolean isNeedToCheckChild(T key) {
        return Val.compareTo(key) <= 0;
    }

    @Override
    public boolean isNeedToStop(T key) {
        return Val.compareTo(key)<=0;

    }

    public SearchEqual(T val){
        super();
        this.Val = val;
    }

}
