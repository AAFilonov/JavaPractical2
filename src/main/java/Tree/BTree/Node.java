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

        else {
            retIndex = getMiddleChildIndex(val);
        }
        return retIndex;

    }

    private Integer getMiddleChildIndex(T val) {
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

    void innerPut(T val) {

        Keys.add(val);
        Collections.sort(Keys);

        if (Keys.size() == this.Rank) {
            split();

        }
    }


    public void split() {
        if (Parent != null)
            splitNonRoot();
        else
            splitRoot();
    }

    public void splitNonRoot() {
        Node<T> RightChild = createRightChild();
        Parent.Childes.add(RightChild);
        T midVal = getMiddleKey();
        Parent.innerPut(midVal);
    }

    public T getMiddleKey() {
        return Keys.get(this.Rank / 2);
    }




    public void splitRoot() {
        this.Childes.add(createLeftChild());
        this.Childes.add(createRightChild());
        RemovekeysExceptMiddle();

        this.isLeaf = false;
    }
    public Node<T> createRightChild() {
        Node<T> ChildRight = new Node<T>(Rank, this);
        int middleVal =this.Rank / 2 ;
        for (int i = middleVal+ 1; i < Keys.size(); i++) {
            ChildRight.innerPut(Keys.get(i));

        }
        return ChildRight;
    }
    public Node<T> createLeftChild() {
        Node<T> LeftChild = new Node<T>(Rank, this);
        for (int i = 0; i < this.Rank / 2; i++)
            LeftChild.innerPut(Keys.get(i));
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
