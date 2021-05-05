package Tree.BTree;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

class DeleteFirstEqual<T extends Comparable<T>> {

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
            deleteKeyFromNode();
    }

    private void deleteKeyFromLeaf() {
        if (!doesDeleteViolateOrder(it.CurrentNode)) {
            it.CurrentNode.Keys.remove(Val);
        } else deleteAndReorderTree();
    }

    void deleteAndReorderTree() {
        int thisNodeIndex = it.getThisChildIndex();

        if (thisNodeIndex == it.CurrentNode.Parent.getFirstChildIndex()) {
            Node<T> leftSibling = it.CurrentNode.Parent.Childes.get(thisNodeIndex + 1);
            mergeRightWithParentKey(thisNodeIndex, leftSibling);

        } else if (thisNodeIndex == it.CurrentNode.Parent.getLastChildIndex()) {
            Node<T> rightSibling = it.CurrentNode.Parent.Childes.get(thisNodeIndex - 1);
            mergeLeftWithParentKey(thisNodeIndex, rightSibling);

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
        } else mergeLeftWithParentKey(thisNodeIndex, leftSibling);
    }

    private void mergeRightWithParentKey(int thisNodeIndex, Node<T> rightSibling) {
        it.CurrentNode.Keys.remove(Val);
        it.CurrentNode = it.CurrentNode.Parent;
        it.CurrentNode.Childes.remove(thisNodeIndex);

        T keyToMerge = it.CurrentNode.Keys.get(thisNodeIndex);
        rightSibling.insertKey(keyToMerge);
        it.CurrentNode.Keys.remove(keyToMerge);

    }

    private void mergeLeftWithParentKey(int thisNodeIndex, Node<T> leftSibling) {
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

        if (node.isLeaf) return node.Keys.size() - 1 < node.MaxDegree / 2;
        else throw new NotImplementedException();
    }

    private void deleteKeyFromNode() {
        int keyIndex = it.CurrentNode.getIndexByKey(this.Val);
        if(keyIndex==0){
            deleteFirstKeyInNode();

        }
        else if(keyIndex== it.CurrentNode.Keys.size()-1){
            deleteLastKeyInNode(keyIndex);
        }
        else{
            deleteMiddleKeyInNode(keyIndex);
        }


    }

    void deleteFirstKeyInNode(){

        if (!doesDeleteViolateOrder(it.CurrentNode.getFirstChild())){
            replaceByLastKeyOfItsChild(0);
        }
        else if(!doesDeleteViolateOrder(it.CurrentNode.Childes.get(1)))
            replaceByFirstKeyOfNextChild(0);
        else {
            mergeIstAndNextChildes(0);
        }
    }

    private void mergeIstAndNextChildes(int keyIndex) {
        Node<T> itsChild =  it.CurrentNode.Childes.get(keyIndex);
        Node<T> nextChild =  it.CurrentNode.Childes.get(keyIndex+1);

        itsChild.insertKey(nextChild.getFirstKey());
        it.CurrentNode.Childes.remove(nextChild);
        it.CurrentNode.Childes.remove(keyIndex);

    }



    private void replaceByFirstKeyOfNextChild(int keyIndex) {
        Node<T> nextChild =  it.CurrentNode.Childes.get(keyIndex+1);
        T key = nextChild.getFirstKey();
        nextChild.Keys.remove(key);
        it.CurrentNode.Keys.set(keyIndex,key);



    }




    private void replaceByLastKeyOfItsChild(int keyIndex) {
        Node<T> itsChild =  it.CurrentNode.getFirstChild();
        T key = itsChild.getLastKey();
        it.CurrentNode.Keys.set(keyIndex, key );
        it.CurrentNode.Childes.get(keyIndex).Keys.remove(key);
    }

    void deleteLastKeyInNode(int keyIndex){
        Node<T> itsChild =  it.CurrentNode.Childes.get(keyIndex);

        if (!doesDeleteViolateOrder(itsChild))
            replaceByLastKeyOfItsChild(keyIndex);

        else
            replaceByFirstKeyOfNextChild(keyIndex);

    }
    void deleteMiddleKeyInNode(int keyIndex){
        Node<T> itsChild =  it.CurrentNode.Childes.get(keyIndex);

        if (!doesDeleteViolateOrder(itsChild))
            replaceByLastKeyOfItsChild(keyIndex);
        else
            throw new NotImplementedException();

    }
}
