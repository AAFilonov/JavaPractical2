package Tree.BTree;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public class BTree<T extends Comparable<T>> implements Iterable {
    int Rank;
    Node<T> Root;

    public void insert(T value) {
        if (this.isEmpty()) Root = new Node<T>(Rank);
        insertIfLeaf(Root, value);
    }

    public void insertRange(Collection<T> range) {
        if (this.isEmpty()) Root = new Node<T>(Rank);
        for (T val : range)
            insertIfLeaf(Root, val);
    }


    public boolean isEmpty() {
        return this.Root == null;
    }


    public boolean delete(T key) {
        return false;
    }

    public void construct(Path filePath, Class<T> tClass) throws IOException {

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                String[] values = line.split(" ");
                for (String valString : values) {
                    T val = tClass.getConstructor(String.class).newInstance(valString);
                    this.insert(val);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }


    public Collection<T> findEqual(T val) {

        NodeChecker<T> Checker = new Search.SearchEqual<T>(val);

        return Checker.DoSearch(this.Root);
    }

    void insertIfLeaf(Node<T> node, T val) {
        if (node.isLeaf)
            node.insertKey(val);
        else {
            findLeaf(node, val);
        }


    }

    void findLeaf(Node<T> node, T val) {
        Node<T> child = node.getChildByKey(val);
        insertIfLeaf(child, val);
    }


    BTree(int rank) {
        this.Rank = rank;
    }


    @Override
    public Iterator iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer action) {

    }

    @Override
    public Spliterator spliterator() {
        return null;
    }
}
