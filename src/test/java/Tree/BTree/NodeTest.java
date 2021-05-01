package Tree.BTree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
    Node<Integer> newNode;
    @BeforeEach

    void Init(){
        newNode = new Node<Integer>(3);
    }


    @Nested
    class PutTests{
        @ParameterizedTest
        @ValueSource(ints = {2, 0, -2, Integer.MAX_VALUE, Integer.MIN_VALUE})
        void testPutVal_WhenAndPut1Val_ThenShouldBeOk(Integer val) {

            newNode.put(val);

            assertEquals(val, newNode.Keys.get(0));

        }

        @Test
        void testPutVal_WhenPutManyValLessThenRank_ThenShouldBeOk() {

            newNode.put(1);
            newNode.put(2);
            assertAll(
                    () -> assertEquals((Integer) 1, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(1))
            );
        }

        @Test
        void testPutVal_WhenPutManyValMoreThenRank_ThenNodeShouldSplit() {
            newNode.put(1);
            newNode.put(2);

            newNode.put(3);

            assertAll(

                    () -> assertNotNull(newNode.Childes.get(0)),
                    () -> assertNotNull(newNode.Childes.get(1)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(0))
            );
        }
    }



    @Test
    void testGetChildIndex() {
        newNode.put(1);
        newNode.put(2);
        newNode.put(3);

        int actual =  newNode.getChildIndex(4);
        assertEquals(1, actual);

    }

}