package Tree.BTree;

public class BTree<T extends  Comparable<T>> {
    private int Rank;
    private  Node<T> Root;

    public boolean insert(T value) {
        return false;
    }
    public  T FindEqualByKey(T key){

       return this.Root.findValueByKey(key);
    }
    public boolean Delete(T key){
        return false;
    }
    public boolean Construct(){
        return false;
    }





}
