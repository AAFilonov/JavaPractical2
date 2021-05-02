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
        put(Root, value);
    }

    public void insertRange(Collection<T> range) {
        if (this.isEmpty()) Root = new Node<T>(Rank);
        for (T val : range)
            put(Root, val);
    }


    public boolean isEmpty() {
        return this.Root == null;
    }

    public Collection<T> findEqualsByKey(T key) {
        Comparator<T> compareOperation = (x, y) -> x.compareTo(y);
        return null;
    }

    Collection<T> returnCollection;

    public Collection<T> find(T val) {
        returnCollection = new ArrayList<>();
        checkNode(this.Root, val);
        return returnCollection;
    }


    void checkNode(Node<T> node, T val) {
        if (node.isLeaf)
            checkAsLeaf(node, val);

        else
            checkAsNode(node, val);

    }

    void checkAsLeaf(Node<T> node, T val) {
        for (T key : node.Keys)
            if (val.compareTo(key) == 0) {
                returnCollection.add(key);
            }
    }


    void checkAsNode(Node<T> node, T val) {

        for (int i = 0; i < node.Keys.size(); i++) {
            T key = node.Keys.get(i);
            if (val.compareTo(key) == 0) {
                returnCollection.add(key);
            }
            if (val.compareTo(key) < 0) {
                checkNode(node.Childes.get(i), val);
                return;
            }
        }
        checkLastChild(node, val);
    }


    void checkLastChild(Node<T> node, T val) {
        T key = node.getLastKey();

        if (!node.isLeaf && val.compareTo(key) > 0) {
            checkNode(node.getLastChild(), val);
        }

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


    void put(Node<T> node, T val) {
        if (node.isLeaf)
            node.insertKey(val);
        else {
            findLeaf(node, val);
        }


    }

    void findLeaf(Node<T> node, T val) {
        Node<T> child = node.getChildByKey(val);
        put(child, val);
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
