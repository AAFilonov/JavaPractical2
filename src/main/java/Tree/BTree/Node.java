package Tree.BTree;

import java.util.ArrayList;
import java.util.Collections;

public class Node<T extends Comparable<T>> {
    int MaxDegree;
    boolean isLeaf = true;
    ArrayList<Node<T>> Childes;
    ArrayList<T> Keys;
    Node<T> Parent;

    Node<T> getChildByKey(T val) {
        int ChildIndex = getChildIndex(val);
        return Childes.get(ChildIndex);
    }

    int getChildIndex(T val) {

        for (int i = 0; i < Keys.size(); i++) {
            T key = Keys.get(i);
            if (val.compareTo(key)<=0)
               return i;
        }
        T lastKey =getLastKey();
        if(val.compareTo(lastKey)>0)
            return   Childes.size()-1;
        else return -1;
    }





    void insertKey(T val) {

        Keys.add(val);
        Collections.sort(Keys);

        if (Keys.size() == this.MaxDegree) {
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
        Parent.SortChildes();
        Parent.insertKey(middleKey);

    }
    void SortChildes(){
        Childes.sort((ch1,ch2)->
            ch1.Keys.get(0).compareTo(ch2.Keys.get(0))
        );
    }
    T getMiddleKey() {
        return Keys.get(this.MaxDegree / 2);
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
        Node<T> ChildRight = new Node<T>(MaxDegree, parent);
        int middleValIndex = this.MaxDegree / 2;
        for (int i = middleValIndex + 1; i < Keys.size(); i++) {
            ChildRight.insertKey(Keys.get(i));

        }
        return ChildRight;
    }

    public Node<T> initLeftKeys(Node<T> parent) {
        Node<T> LeftChild = new Node<T>(MaxDegree, parent);
        for (int i = 0; i < this.MaxDegree / 2; i++)
            LeftChild.insertKey(Keys.get(i));
        return LeftChild;
    }

    public void RemovekeysExceptMiddle() {
        removeRightKeys();
        removeLeftKeys();

    }


    private void removeLeftKeys() {
        ArrayList<T> KeysToDelete = new ArrayList<T>();
        for (int i = 0  ; i < Keys.size()/2 ; i++) {
            KeysToDelete.add(Keys.get(i));
        }
        for(T key:KeysToDelete){
            Keys.remove(key);
        }
    }

    private void removeRightKeys() {


        ArrayList<T> KeysToDelete = new ArrayList<T>();
        for (int i =  Keys.size() / 2+1 ; i < Keys.size() ; i++) {
            KeysToDelete.add(Keys.get(i));
        }
        for(T key:KeysToDelete){
            Keys.remove(key);
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




    {
        Childes = new ArrayList<Node<T>>();
        Keys = new ArrayList<T>();
    }

    Node(int maxDegree) {
        this.MaxDegree = maxDegree;
    }

    Node(int maxDegree, Node<T> parent) {
        this.MaxDegree = maxDegree;
        this.Parent = parent;
    }

    public int getLastChildIndex(){
        return  this.Childes.size() - 1;
    }
    public int getFirstChildIndex(){
        return  0;
    }
    public int getIndexByKey(T val){
        ArrayList<T> keys = this.Keys;
        for (int i = 0; i < keys.size(); i++) {
            T key = keys.get(i);
            if (key.compareTo(val) == 0)
                return i;
        }
        return  -1;
    }

}
