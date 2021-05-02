package Tree.BTree;

import java.util.ArrayList;
import java.util.Collections;

public class Node<T extends Comparable<T>> {
    int Rank;
    boolean isLeaf = true;
    ArrayList<Node<T>> Childes;
    ArrayList<T> Keys;
    Node<T> Parent;

    Node<T> getChildByVal(T val) {
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
        Parent.Childes.add(initRightkeys(this.Parent));
        removeRightKeys();
        pushMiddleKeyToParent();
    }


    void pushMiddleKeyToParent(){
        T middleKey=getMiddleKey();
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
        Node<T> ChildRight = initRightChild();
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
        return Childleft;

    }

    Node<T> initRightChild() {
        Node<T> ChildRight = initRightkeys(this);
        for (int  i = Childes.size() / 2; i < Childes.size() ; i++) {
            ChildRight.setChild(this.Childes.get(i));

        }
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
        for (int i = 0; i < this.Rank / 2; i++)
            Keys.remove(i);
    }

    private void removeRightKeys() {
        for (int i = this.Rank / 2 + 1; i < Keys.size(); i++) {
            Keys.remove(i);
        }
    }















/* попытка сделать поиск


    Collection<T> ReturnCollection;
    Collection<T> getEqualsByKey(T key){
        fillRetCollectionEq(key);
        return CleanReturn();
    }



    private void fillRetCollectionEq(T key){
        CheckFirstEq(key);
        CheckMiddleEq(key);
        CheckLastEq(key);
    }
    Collection<T> CleanReturn(){
        Collection<T> collCopy = ReturnCollection;
        ReturnCollection.clear();
        return collCopy;
    }
    private void CheckMiddleEq(T key){
        for(int i=0;i<this.Rank-1;i++){
            CheckKeyEq(key,Values.get(i));
        }
    }
    private void CheckFirstEq(T key){
        if(areEqual(key,Values.get(0))) {
            ReturnCollection.add(Values.get(0));

        }


    }
    private void CheckLastEq(T key){
        if(areEqual(key,Values.get(0))) {
            ReturnCollection.add(Values.get(this.Rank-1));
        }
        else{
            Collection<T> childVals=  Childs.get(this.Rank-1).getEqualsByKey(key);
            ReturnCollection.addAll(childVals);
        }

    }

    private void CheckKeyEq(T key, T value){
        if(areEqual(key,value))
        {
            ReturnCollection.add(value);
        }
    }

*/

    private T getFirstKey() {
        return Keys.get(0);
    }

    private T getLastKey() {
        return Keys.get(Keys.size() - 1);
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
