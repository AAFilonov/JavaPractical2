package Search;

import Tree.BTree.NodeChecker;


public class SearchEqual<T extends Comparable<T>> extends NodeChecker<T> {
    T Val;
    @Override
    public boolean isKeyRequired(T key) {
        return Val.compareTo(key) == 0;
    }

    //если искомое значение меньше ключа значит оно находится внутри этого потомка
    @Override
    public boolean isNeedToCheckChild(T key) {
        return Val.compareTo(key) < 0;
    }

    @Override
    public boolean isNeedToStop() {
        return true;
    }

    public SearchEqual(T val){
        super();
        this.Val = val;
    }
}
