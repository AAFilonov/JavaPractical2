package Tree.BTree;

import java.util.ArrayList;
import java.util.Collection;

public abstract class NodeChecker<T extends Comparable<T>> {

    public Collection<T> returnCollection;


    public abstract boolean isKeyRequired(T key);
    public abstract boolean isNeedToCheckChild(T key);
    public abstract boolean isNeedToStop();

    public Collection<T> DoSearch(Node<T> Root){
        checkNode(Root);
        return returnCollection;
    }

    void checkNode(Node<T> node) {

        if (!node.isLeaf)
            checkAsNode(node);
        else
            checkAsLeaf(node);


    }
    void checkAsNode(Node<T> node) {

        for (int i = 0; i < node.Keys.size(); i++) {
            T key = node.Keys.get(i);

            if (isKeyRequired(key)) {
                this.returnCollection.add(key);
                if (this.isNeedToStop()) return;
            }
            if (isNeedToCheckChild(key)) {
                checkNode(node.Childes.get(i));
                return;
            }
        }
        checkNode(node.getLastChild());
    }

    void checkAsLeaf(Node<T> node) {
        for (T key : node.Keys) {
            if (isKeyRequired(key)) {
                this.returnCollection.add(key);
                if (isNeedToStop()) return;
            }
        }

    }

    public NodeChecker(){
        returnCollection = new ArrayList<>();
    }

}
