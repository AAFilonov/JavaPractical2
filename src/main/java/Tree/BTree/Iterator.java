package Tree.BTree;

import java.util.function.Consumer;

public class Iterator<T extends Comparable<T>> implements java.util.Iterator<T> {
    private BTree<T> Tree;
    private  Node<T> CurrentNode;
    private  int CurrentKeyIndex;
    T CurrentKey;

    Iterator(BTree<T> tree)
    {
        this.Tree = tree;
        this.CurrentNode= tree.findMinNode();
        if(CurrentNode.getFirstKey()!=null) this.CurrentKeyIndex = -1;

    }

    @Override
    public boolean hasNext() {
        return CurrentKey ==Tree.findMax();
    }

    int getThisChildIndex(){
        T PrimeKeyOfNode = CurrentNode.getLastKey();
        for (int i = 0; i< CurrentNode.Parent.Keys.size();i++){
            T key = CurrentNode.Parent.Keys.get(i);
            if(PrimeKeyOfNode.compareTo(key) <=0)
                return i;
        }
        //последний
        return CurrentNode.Parent.Childes.size()-1;

    }
    T getCurrentKey(){
        return CurrentNode.Keys.get(CurrentKeyIndex);
    }


    @Override
    public T next() {
        if(hasNext()){
            if(CurrentKeyIndex< CurrentNode.Keys.size()) {
                CurrentKeyIndex++;
                return getCurrentKey();
            }


        }
        return null;
    }

    @Override
    public void remove() {

    }

    @Override
    public void forEachRemaining(Consumer action) {

    }
    // constructor

}


