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

/*
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
        void testSplit_123() {
            newNode.put(1);
            newNode.put(2);
            newNode.put(3);

            assertAll(

                    () -> assertEquals((Integer) 1, newNode.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 3, newNode.Childes.get(1).Keys.get(0))
            );
        }
        @Test
        void testSplit_1234() {
            newNode.put(1);
            newNode.put(2);
            newNode.put(3);
            newNode.put(4);

            assertAll(


                    () -> assertEquals((Integer) 1, newNode.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 3, newNode.Childes.get(1).Keys.get(0)),
                    () -> assertEquals((Integer) 4, newNode.Childes.get(1).Keys.get(1))
            );



        }
        @Test
        void testSplit_12345() {
            newNode.put(1);
            newNode.put(2);
            newNode.put(3);
            newNode.put(4);
            newNode.put(5);



            assertAll(


                    () -> assertEquals((Integer) 1, newNode.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 3, newNode.Childes.get(1).Keys.get(0)),
                    () -> assertEquals((Integer) 4, newNode.Childes.get(1).Keys.get(1))
                    () -> assertEquals( 3, newNode.Childes.size())

            );
        }
    }

    @Test
    void testGetChildIndex_ThenReturnLeftmostChidl() {

        newNode.put(2);
        newNode.put(3);
        newNode.put(4);

        int actual =  newNode.getChildIndex(1);
        assertEquals(0, actual);

    }

    @Test
    void testGetChildIndex_ThenReturnRightmostChidl() {
        newNode.put(1);
        newNode.put(2);
        newNode.put(3);

        int actual =  newNode.getChildIndex(4);
        assertEquals(1, actual);

    }
    @Test
    void testGetChildIndex_ThenReturnMiddleChidl() {
        newNode.put(1);
        newNode.put(2);
        newNode.put(3);
        newNode.put(5);
        newNode.put(6);


        int actual =  newNode.getChildIndex(4);
        assertEquals(1, actual);

    }
*/
}