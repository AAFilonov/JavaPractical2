package Tree.BTree;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.UUID.fromString;

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
    public boolean construct(Path filePath, Class<T> tClass) throws IOException {

        try {



            List<String> lines = Files.readAllLines(filePath);
            for (String line:lines) {
                String[] values = line.split(" ");
                for (String valString:values) {
                    T val = tClass.getConstructor(String.class).newInstance(valString);
                    this.insert(val);
                }
            }

        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return false;
    }

    BTree(int rank){
        this.Rank = rank;
    }



}
