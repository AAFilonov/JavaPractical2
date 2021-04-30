package Tree.BTree;

import java.util.ArrayList;
import java.util.List;

public class Node<T extends  Comparable<T>> {
    int Rank;
    ArrayList<Node<T>> Childs;
    ArrayList<T> Keys;

    T findValueByKey(T key){
        T retValue=null;
        for(int i=0;i<this.Rank-1;i++){
            if(areEqual(key,Keys.get(i))) {
               return Keys.get(i); //искомое значение

            }
            else
                //а если у узла нет поддеревьев?
                if(isLeftLesser(key,Keys.get(i))){
                    retValue= Childs.get(i).findValueByKey(key);
                    if(retValue!=null)
                        break;
                }
        }


        return retValue;
    }

    private boolean areEqual(T key1, T key2) {
        return key1.compareTo(key2) ==0;
    }
    private boolean isLeftLesser(T key1, T key2) {
     return key1.compareTo(key2) <0;
    }
    private boolean isLeftGreater(T key1, T key2) {
        return key1.compareTo(key2) >0;
    }

}
