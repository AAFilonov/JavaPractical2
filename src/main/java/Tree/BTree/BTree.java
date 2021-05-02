package Tree.BTree;

import java.util.Collection;

public class BTree<T extends  Comparable<T>> {
     int Rank;
     Node<T> Root;

    public void insert(T value) {
        if (this.isEmpty()) Root = new Node<T>(Rank);
        put(Root, value);
    }

     void put(Node<T> node,T val) {
        if (node.isLeaf)
            node.insertKey(val);
        else {
            findLeaf(node, val);
        }


    }
    void findLeaf(Node<T> node, T val){
        Node<T> child = node.getChildByVal(val);
        put(child,val);
    }

    public boolean isEmpty(){
        return this.Root==null;
    }

    public Collection<T> FindEqualsByKey(T key){

       return null;
    }
    public boolean Delete(T key){
        return false;
    }
    public boolean Construct(){
        return false;
    }

    BTree(int rank){
        this.Rank = rank;
    }



}
