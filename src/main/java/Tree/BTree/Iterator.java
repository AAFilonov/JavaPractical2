package Tree.BTree;

import java.util.function.Consumer;

public class Iterator<T extends Comparable<T>> implements java.util.Iterator<T> {
    private BTree<T> Tree;
    Node<T> CurrentNode;
    int CurrentKeyIndex;
    T CurrentKey;

    Iterator(BTree<T> tree) {
        this.Tree = tree;
        this.CurrentNode = tree.findMinNode();
        this.CurrentKeyIndex = -1;

    }

    @Override
    public void remove() {
        Tree.delete(CurrentKey);
    }

    @Override
    public void forEachRemaining(Consumer action) {
        while (hasNext())
            action.accept(next());
    }
    
    @Override
    public boolean hasNext() {
        if (!Tree.isEmpty())
            return CurrentKey != Tree.findMax();
        else return false;
    }

    int getThisChildIndex() {
        T PrimeKeyOfNode = CurrentNode.getLastKey();
        for (int i = 0; i < CurrentNode.Parent.Keys.size(); i++) {
            T key = CurrentNode.Parent.Keys.get(i);
            if (PrimeKeyOfNode.compareTo(key) <= 0)
                return i;
        }
        //последний
        return CurrentNode.Parent.Childes.size() - 1;

    }


    @Override
    public T next() {
        if (hasNext()) {
            if (CurrentNode.isLeaf) {
                return moveRight();
            }       //идем в родителя и его ключ, или мб его следующего потомка
            else return moveDown();
        }
        return null;
    }

     T moveRight() {
        if (!isLastKeyInNode()) {
            return nextKeyInCurrentNode();
        } else
            return moveUp();

    }


     T moveUp() {
        int passedChildIndex = getThisChildIndex();
        moveToParent();
        if (isLastChildInNode(passedChildIndex))
            return moveUp();
        else {
            CurrentKeyIndex = passedChildIndex;
            CurrentKey = CurrentNode.Keys.get(CurrentKeyIndex);
            return CurrentKey;
        }
    }

     T moveDown() {

        moveToNextChild();
        CurrentKeyIndex = -1;
        if (!CurrentNode.isLeaf)
            return moveDown();
        else return moveRight();
    }

     void moveToNextChild() {
        CurrentNode = CurrentNode.Childes.get(CurrentKeyIndex + 1);
    }

     void moveToParent() {
        CurrentNode = CurrentNode.Parent;
    }


     T nextKeyInCurrentNode() {
        CurrentKeyIndex++;
        CurrentKey = CurrentNode.Keys.get(CurrentKeyIndex);
        return CurrentKey;
    }

     boolean isLastKeyInNode() {
        return CurrentKeyIndex == CurrentNode.Keys.size() - 1;
    }

     boolean isLastChildInNode(int childIndex) {
        return childIndex == CurrentNode.Childes.size() - 1;
    }




}


