package Tree.BTree;

import Tree.BTree.Search.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public class BTree<T extends Comparable<T>> implements Iterable {
    int Rank;
    int Size=0;
    Node<T> Root;


    public void insertRange(Collection<T> range) {
        for (T val : range) {
            insert(val);
        }
    }

    public void insert(T value) {
        insertIfLeaf(Root, value);
        Size++;
    }

    void insertIfLeaf(Node<T> node, T val) {
        if (node.isLeaf)
            node.insertKey(val);
        else {
            selectLeaf(node, val);
        }
    }

    void selectLeaf(Node<T> node, T val) {
        Node<T> child = node.getChildByKey(val);
        insertIfLeaf(child, val);
    }


    public boolean isEmpty() {
        return this.Root == null;
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
        NodeChecker<T> Checker = new SearchEqual<T>(val);
        return Checker.DoSearch(this.Root);
    }
    public Collection<T> findLess(T minVal) {
        NodeChecker<T> Checker = new SearchLess<T>(minVal);
        return Checker.DoSearch(this.Root);
    }
    public Collection<T> findGreater(T maxVal) {
        NodeChecker<T> Checker = new SearchGreater<T>(maxVal);
        return Checker.DoSearch(this.Root);
    }
    public Collection<T> findInRange(T minVal, T maxVal) {
        NodeChecker<T> Checker = new SearchInRange<T>(minVal,maxVal);
        return Checker.DoSearch(this.Root);
    }
    public T findMin() {
        Node<T> current = Root;
        while (!current.isLeaf)
            current =current.getFirstChild();
        return current.getFirstKey();

    }
    public T findMax() {
        Node<T> current = Root;
        while (!current.isLeaf)
            current =current.getLastChild();
        return current.getLastKey();

    }

     Node<T> findMinNode() {
        Node<T> current = Root;
        while (!current.isLeaf)
            current =current.getFirstChild();
        return current;

    }
    Node<T> findMaxNode() {
        Node<T> current = Root;
        while (!current.isLeaf)
            current =current.getLastChild();
        return current;

    }

    public void deleteRange(Collection<T> range) {
        for (T val : range)
            insert( val);
    }
    public void delete(T key) {
    }

    BTree(int rank) {
        this.Rank = rank;
        Root = new Node<T>(Rank);
    }


    @Override
    public Iterator<T> iterator() {
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
