package Tree.BTree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BTreeTest {
    BTree<Integer, String> tree;
    @BeforeEach
    public  void init(){
        tree = new BTree<Integer,String>();
    }
    @Nested
    class ConstructTests{

    }
    @Nested
    class InsertTests{

        @Test
        void testInsert_WhenTreeIsEmpty_ThenReturnTrue() {
            String val = "value";
            Integer key = 1;


            assertTrue(tree.insert( key, val));
        }

        @ParameterizedTest
        @ValueSource(
                ints = {2,0,-2,Integer.MAX_VALUE,Integer.MIN_VALUE})
        void testInsert_WhenTreeIsEmptyWithDifferentKeys_ThenReturnTrue(Integer Key) {
            String val = "value";

            assertTrue(tree.insert( Key, val));
        }


    }
    @Nested
    class DeleteTests{

    }
    @Nested
    class FindTests{

    }

}