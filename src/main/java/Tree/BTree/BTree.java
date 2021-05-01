package Tree.BTree;

import java.util.Collection;

public class BTree<T extends  Comparable<T>> {
    private int Rank;
    private  Node<T> Root;

    public void insert(T value) {
        if (this.isEmpty()){
            Root = new Node<T>(Rank);
            Root.put(value);
            return;
        }


        return;
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





}
