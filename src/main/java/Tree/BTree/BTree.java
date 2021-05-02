package Tree.BTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.io.Reader;
import java.util.stream.Stream;

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

    public Collection<T> findEqualsByKey(T key){

       return null;
    }
    public boolean delete(T key){
        return false;
    }
    public boolean construct(String fileName) throws IOException {

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(System.out::println);
        }
        return false;
    }

    BTree(int rank){
        this.Rank = rank;
    }



}
