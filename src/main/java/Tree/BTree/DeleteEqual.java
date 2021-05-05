package Tree.BTree;

import Tree.BTree.BTree;
import Tree.BTree.Iterator;
import Tree.BTree.Node;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DeleteFirstEqual<T extends Comparable<T>> {

    BTree<T> Tree;
    T Val;
    Iterator<T> it;

    public DeleteFirstEqual(BTree<T> tree) {
        this.Tree = tree;
    }

    public void doDelete(T val) {
        this.Val = val;
        this.it = Tree.iterator();


        while (it.hasNext()) {
            T key = it.next();
            if (key.compareTo(val) == 0) {
                this.deleteKey();
                return;
            }
        }
    }

    private void deleteKey() {
        if (it.CurrentNode.isLeaf)
            deleteKeyFromLeaf();
        else
            deleteKeyDromNode();
    }

    private void deleteKeyFromLeaf() {
        if (!doesDeleteViolateOrder(it.CurrentNode)) {
            it.CurrentNode.Keys.remove(Val);
        } else deleteAndReorderTree();
    }

    void deleteAndReorderTree() {
        int thisNodeIndex = it.getThisChildIndex();

        if (thisNodeIndex== it.CurrentNode.Parent.getFirstChildIndex()) {
            Node<T> leftSibling = it.CurrentNode.Parent.Childes.get(thisNodeIndex + 1);
            mergeRightWithParentkey(thisNodeIndex, leftSibling);

        } else if (thisNodeIndex ==  it.CurrentNode.Parent.getLastChildIndex()) {
            Node<T> rightSibling = it.CurrentNode.Parent.Childes.get(thisNodeIndex - 1);
            mergeLeftwithParentkey(thisNodeIndex, rightSibling);

        } else
            reOrderMiddleChild(thisNodeIndex);

    }

    private void reOrderMiddleChild(int thisNodeIndex) {
        Node<T> leftSibling = it.CurrentNode.Parent.Childes.get(thisNodeIndex - 1);
        Node<T> rightSibling = it.CurrentNode.Parent.Childes.get(thisNodeIndex + 1);

        if (!doesDeleteViolateOrder(leftSibling)) {
            replaceWithLeft(thisNodeIndex, leftSibling);
        } else if (!doesDeleteViolateOrder(rightSibling)) {
            replaceWithRight(thisNodeIndex, rightSibling);
        } else mergeLeftwithParentkey(thisNodeIndex, leftSibling);
    }

    private void mergeRightWithParentkey(int thisNodeIndex, Node<T> rightSibling) {
        it.CurrentNode.Keys.remove(Val);
        it.CurrentNode = it.CurrentNode.Parent;
        it.CurrentNode.Childes.remove(thisNodeIndex);

        T keyToMerge = it.CurrentNode.Keys.get(thisNodeIndex);
        rightSibling.insertKey(keyToMerge);
        it.CurrentNode.Keys.remove(keyToMerge);

    }

    private void mergeLeftwithParentkey(int thisNodeIndex, Node<T> leftSibling) {
        it.CurrentNode.Keys.remove(Val);
        it.CurrentNode = it.CurrentNode.Parent;
        it.CurrentNode.Childes.remove(thisNodeIndex);

        T keyToMerge = it.CurrentNode.Keys.get(thisNodeIndex - 1);
        leftSibling.insertKey(keyToMerge);
        it.CurrentNode.Keys.remove(keyToMerge);

    }

    private void replaceWithRight(int thisNodeIndex, Node<T> rightSibling) {
        it.CurrentNode.Keys.remove(Val);
        it.CurrentNode.insertKey(it.CurrentNode.Parent.Keys.get(thisNodeIndex));

        T newKey = rightSibling.getFirstKey();
        rightSibling.Keys.remove(newKey);

        it.CurrentNode.Parent.Keys.set(thisNodeIndex, newKey);

    }

    private void replaceWithLeft(int thisNodeIndex, Node<T> leftSibling) {
        it.CurrentNode.Keys.remove(Val);
        it.CurrentNode.insertKey(it.CurrentNode.Parent.Keys.get(thisNodeIndex - 1));

        T newKey = leftSibling.getLastKey();
        leftSibling.Keys.remove(newKey);
        it.CurrentNode.Parent.Keys.set(thisNodeIndex - 1, newKey);
    }


    boolean doesDeleteViolateOrder(Node<T> node) {
        return node.Keys.size() - 1 < node.MaxDegree / 2;
    }

    private void deleteKeyDromNode() {
        throw new NotImplementedException();
    }


}
