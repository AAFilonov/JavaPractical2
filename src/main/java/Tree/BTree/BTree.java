package Tree.BTree;

import Tree.BTree.Search.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

public class BTree<T extends Comparable<T>> implements Iterable<T> {
    int Rank;
    int Size = 0;
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
        return this.Root.Keys.size() == 0;
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
        return Checker.doSearch(this.Root);
    }

    public Collection<T> findLess(T minVal) {
        NodeChecker<T> Checker = new SearchLess<T>(minVal);
        return Checker.doSearch(this.Root);
    }

    public Collection<T> findGreater(T maxVal) {
        NodeChecker<T> Checker = new SearchGreater<T>(maxVal);
        return Checker.doSearch(this.Root);
    }

    public Collection<T> findInRange(T minVal, T maxVal) {
        NodeChecker<T> Checker = new SearchInRange<T>(minVal, maxVal);
        return Checker.doSearch(this.Root);
    }

    public T findMin() {
        Node<T> current = Root;
        while (!current.isLeaf)
            current = current.getFirstChild();
        return current.getFirstKey();

    }

    public T findMax() {
        Node<T> current = Root;
        while (!current.isLeaf)
            current = current.getLastChild();
        return current.getLastKey();

    }

    Node<T> findMinNode() {
        Node<T> current = Root;
        while (!current.isLeaf)
            current = current.getFirstChild();
        return current;

    }

    Node<T> findMaxNode() {
        Node<T> current = Root;
        while (!current.isLeaf)
            current = current.getLastChild();
        return current;

    }

    public void deleteRange(Collection<T> range) {
        for (T val : range)
            insert(val);
    }

    public void delete(T key) {

        DeleteEqual<T> Checker = new DeleteEqual<T>();
        Checker.doDelete(key);

    }

    BTree(int rank) {
        this.Rank = rank;
        Root = new Node<T>(Rank);
    }


    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>(this);
    }

    @Override
    public void forEach(Consumer action) {

    }

    @Override
    public Spliterator spliterator() {
        return null;
    }

    class DeleteEqual<T extends Comparable<T>> {
        T Val;
        Iterator<T> it;

        void doDelete(T val) {
            this.Val = val;
            this.it = (Iterator<T>) iterator();


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
                return;
            }
            int thisNodeIndex = it.getThisChildIndex();
            if (thisNodeIndex == 0) {

                Node<T> leftSibling = it.CurrentNode.Parent.Childes.get(thisNodeIndex + 1);
                mergeRightWithParentkey(thisNodeIndex, leftSibling);

            } else if (thisNodeIndex == it.CurrentNode.Parent.Childes.size() - 1) {
                Node<T> rightSibling = it.CurrentNode.Parent.Childes.get(thisNodeIndex - 1);
                mergeLeftwithParentkey(thisNodeIndex, rightSibling);

            } else {
                Node<T> leftSibling = it.CurrentNode.Parent.Childes.get(thisNodeIndex - 1);
                Node<T> rightSibling = it.CurrentNode.Parent.Childes.get(thisNodeIndex + 1);

                if (!doesDeleteViolateOrder(leftSibling)) {
                    replaceWithLeft(thisNodeIndex, leftSibling);
                } else if (!doesDeleteViolateOrder(rightSibling)) {
                    replaceWithRight(thisNodeIndex, rightSibling);
                } else mergeLeftwithParentkey(thisNodeIndex, leftSibling);
            }
        }

        private void mergeRightWithParentkey(int thisNodeIndex, Node<T> rightSibling) {
            it.CurrentNode.Keys.remove(Val);
            it.CurrentNode = it.CurrentNode.Parent;
            it.CurrentNode.Childes.remove(thisNodeIndex);

            T keyToMerge = it.CurrentNode.Keys.get(thisNodeIndex );
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


}



