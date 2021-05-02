package Tree.BTree;

import java.util.ArrayList;
import java.util.Collections;

public class Node<T extends Comparable<T>> {
    int Rank;
    boolean isLeaf = true;
    ArrayList<Node<T>> Childes;
    ArrayList<T> Keys;
    Node<T> Parent;

    Node<T> getChildByKey(T val) {
        int ChildIndex = getChildIndex(val);
        return Childes.get(ChildIndex);
    }

    Integer getChildIndex(T val) {
        Integer retIndex = null;
        if (isLeftLesser(val, this.getFirstKey()))
            retIndex = 0;
        else if (isLeftGreater(val, this.getLastKey()))
            retIndex = Childes.size() - 1;
        else
            retIndex = getMiddleChildIndex(val);
        return retIndex;
    }
    Node<T> getMiddleChildByKey(T key) {
        return this.Childes.get( getMiddleChildIndex(key));
    }


    Integer getMiddleChildIndex(T val) {
        Integer retIndex = null;
        for (int i = 1; i < Keys.size() - 1; i++) {
            if (isLeftGreater(val, Keys.get(i - 1)) &&
                    isLeftLesser(val, Keys.get(i))) {
                retIndex = i;
                break;
            }
        }
        return retIndex;
    }

    void insertKey(T val) {

        Keys.add(val);
        Collections.sort(Keys);

        if (Keys.size() == this.Rank) {
            splitNode();

        }
    }

    void splitNode() {
        if (Parent != null)
            splitNonRoot();
        else
            splitRoot();
    }

    void splitNonRoot() {
        if (isLeaf) {
            Parent.Childes.add(initRightkeys(this.Parent));
            removeRightKeys();
            pushMiddleKeyToParent();

        } else {
            Parent.Childes.add(initRightChild(this.Parent));
            removeRightKeys();
            removeRightChildes();
            pushMiddleKeyToParent();

        }



    }


    void pushMiddleKeyToParent() {
        T middleKey = getMiddleKey();
        this.Keys.remove(middleKey);
        Parent.insertKey(middleKey);
    }

    T getMiddleKey() {
        return Keys.get(this.Rank / 2);
    }


    void splitRoot() {
        if (isLeaf) {
            splitLeaf();

        } else {
            splitNoNLeaf();
        }
    }

    void splitLeaf() {
        this.Childes.add(initLeftKeys(this));
        this.Childes.add(initRightkeys(this));
        RemovekeysExceptMiddle();
        this.isLeaf = false;
    }

    void splitNoNLeaf() {
        Node<T> ChildLeft = initLeftChild();
        Node<T> ChildRight = initRightChild(this);
        Childes.clear();

        this.Childes.add(ChildLeft);
        this.Childes.add(ChildRight);
        this.RemovekeysExceptMiddle();
    }

    Node<T> initLeftChild() {
        Node<T> Childleft = initLeftKeys(this);
        for (int i = 0; i < Childes.size() / 2; i++) {
            Childleft.setChild(this.Childes.get(i));

        }
        Childleft.isLeaf=false;
        return Childleft;

    }

    Node<T> initRightChild(Node<T> parent) {
        Node<T> ChildRight = initRightkeys(parent);
        for (int i = Childes.size() / 2; i < Childes.size(); i++) {
            ChildRight.setChild(this.Childes.get(i));

        }
        ChildRight.isLeaf=false;
        return ChildRight;
    }

    void setChild(Node<T> child) {
        child.Parent = this;
        this.Childes.add(child);
    }

    public Node<T> initRightkeys(Node<T> parent) {
        Node<T> ChildRight = new Node<T>(Rank, parent);
        int middleValIndex = this.Rank / 2;
        for (int i = middleValIndex + 1; i < Keys.size(); i++) {
            ChildRight.insertKey(Keys.get(i));

        }
        return ChildRight;
    }

    public Node<T> initLeftKeys(Node<T> parent) {
        Node<T> LeftChild = new Node<T>(Rank, parent);
        for (int i = 0; i < this.Rank / 2; i++)
            LeftChild.insertKey(Keys.get(i));
        return LeftChild;
    }

    public void RemovekeysExceptMiddle() {
        removeRightKeys();
        removeLeftKeys();

    }


    private void removeLeftKeys() {
        int size = Keys.size();
        for (int i = 0; i < size / 2; i++)
            Keys.remove(i);
    }

    private void removeRightKeys() {
        int size = Keys.size();
        for (int i = size / 2 + 1; i < size; i++) {
            Keys.remove(i);
        }
    }

    private void removeRightChildes() {
        ArrayList<Node<T>> childesToDelete = new ArrayList<Node<T>>();
        for (int i =  Childes.size() / 2 ; i < Childes.size() ; i++) {
            childesToDelete.add(Childes.get(i));
        }
        for(Node<T>child:childesToDelete){
            Childes.remove(child);
        }
    }


    T getFirstKey() {
        return Keys.get(0);
    }

    T getLastKey() {
        return Keys.get(Keys.size() - 1);
    }

    Node<T> getFirstChild() {
        return Childes.get(0);
    }

    Node<T> getLastChild() {
        return Childes.get(Childes.size() - 1);
    }


    private boolean areEqual(T key1, T key2) {
        return key1.compareTo(key2) == 0;
    }

    private boolean isLeftLesser(T key1, T key2) {
        return key1.compareTo(key2) < 0;
    }

    private boolean isLeftGreater(T key1, T key2) {
        return key1.compareTo(key2) > 0;
    }

    {
        Childes = new ArrayList<Node<T>>();
        Keys = new ArrayList<T>();
    }

    Node(int rank) {
        this.Rank = rank;
    }

    Node(int rank, Node<T> parent) {
        this.Rank = rank;
        this.Parent = parent;
    }


}
