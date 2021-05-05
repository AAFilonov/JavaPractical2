package Tree.BTree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
public class BtreeTestMaxDegree4  extends BTreeTest{
    @BeforeEach
    void init() {
        tree = new BTree<Integer>(4);


    }

    @Nested
    class InsertTests {
        @ParameterizedTest
        @ValueSource(ints = {2, 0, -2, Integer.MAX_VALUE, Integer.MIN_VALUE})
        void testPutVal_WhenAndPut1Val_ThenShouldBeOk(Integer val) {

            tree.insert(val);
            Node<Integer> newNode = tree.Root;
            assertEquals(val, newNode.Keys.get(0));

        }


        @Nested
        class InsertRandomRangeTests {
            // 5, 7, 3, 1, 2, 6, 4, 8, 7, 1, 6, 4, 2, 5, 3
            @Test
            void given5_When_ThentreeMatch() {
                tree.insertRange(Arrays.asList(5));

                Node<Integer> root = tree.Root;
                assertAll(
                        () -> assertEquals(5, root.Keys.get(0))

                );

            }

            @Test
            void given57_When_ThentreeMatch() {
                tree.insertRange(Arrays.asList(5, 7));

                Node<Integer> root = tree.Root;
                assertAll(
                        () -> assertEquals(5, root.Keys.get(0)),
                        () -> assertEquals(7, root.Keys.get(1))

                );

            }

            @Test
            void given573_When_ThentreeMatch() {
                tree.insertRange(Arrays.asList(5, 7, 3));

                Node<Integer> root = tree.Root;
                Node<Integer> Child3 = root.Childes.get(0);
                Node<Integer> Child7 = root.Childes.get(1);
                assertAll(
                        () -> assertEquals(5, root.Keys.get(0)),
                        () -> assertEquals(3, Child3.Keys.get(0)),
                        () -> assertEquals(7, Child7.Keys.get(0))

                );

            }

            @Test
            void given5731_When_ThentreeMatch() {
                tree.insertRange(Arrays.asList(5, 7, 3, 1));

                Node<Integer> root = tree.Root;
                Node<Integer> Child3_1 = root.Childes.get(0);
                Node<Integer> Child7 = root.Childes.get(1);
                assertAll(
                        () -> assertEquals(5, root.Keys.get(0)),
                        () -> assertEquals(1, Child3_1.Keys.get(0)),
                        () -> assertEquals(3, Child3_1.Keys.get(1)),
                        () -> assertEquals(7, Child7.Keys.get(0))

                );

            }


            @Test
            void givenVal_WhenTreeIs11to77RandomOrder_ThentreeMatch() {
                tree.insertRange(Arrays.asList(5, 7, 3, 1, 2, 6, 4, 8, 7, 1, 6, 4, 2, 5, 3));


                Node<Integer> root = tree.Root;
                Node<Integer> Child1A = root.Childes.get(0);
                Node<Integer> Child1B = Child1A.Childes.get(0);
                Node<Integer> Child2A = Child1A.Childes.get(1);

                Node<Integer> Child4 = root.Childes.get(1);
                Node<Integer> Child3AB = Child4.Childes.get(0);
                Node<Integer> Child4_5 = Child4.Childes.get(1);

                Node<Integer> Child6_7 = root.Childes.get(2);
                Node<Integer> Child6B = Child6_7.Childes.get(0);
                Node<Integer> Child7B = Child6_7.Childes.get(1);
                Node<Integer> Child8 = Child6_7.Childes.get(2);


                assertEquals(2, root.Keys.get(0));
                assertEquals(5, root.Keys.get(1));
                assertEquals(1, Child1A.Keys.get(0));
                assertEquals(1, Child1B.Keys.get(0));
                assertEquals(2, Child2A.Keys.get(0));
                assertEquals(4, Child4.Keys.get(0));
                assertEquals(3, Child3AB.Keys.get(0));
                assertEquals(3, Child3AB.Keys.get(1));
                assertEquals(4, Child4_5.Keys.get(0));
                assertEquals(5, Child4_5.Keys.get(1));
                assertEquals(6, Child6_7.Keys.get(0));
                assertEquals(7, Child6_7.Keys.get(1));
                assertEquals(6, Child6B.Keys.get(0));
                assertEquals(7, Child7B.Keys.get(0));
                assertEquals(8, Child8.Keys.get(0));

            }
        }


        @Test
        void testPutVal_WhenPutManyValLessThenRank_ThenShouldBeOk() {

            tree.insert(1);
            tree.insert(2);
            Node<Integer> newNode = tree.Root;
            assertAll(
                    () -> assertEquals((Integer) 1, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(1))
            );
        }

        @Test
        void testSplit_123() {
            List<Integer> range = Arrays.asList(1, 2, 3);
            tree.insertRange(range);
            Node<Integer> newNode = tree.Root;
            assertAll(

                    () -> assertEquals((Integer) 1, newNode.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 3, newNode.Childes.get(1).Keys.get(0))
            );
        }

        @Test
        void testSplit_132() {
            List<Integer> range = Arrays.asList(1, 3, 2);
            tree.insertRange(range);
            Node<Integer> newNode = tree.Root;
            assertAll(

                    () -> assertEquals((Integer) 1, newNode.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 3, newNode.Childes.get(1).Keys.get(0))
            );
        }

        @Test
        void testSplit_231() {
            List<Integer> range = Arrays.asList(2, 3, 1);
            tree.insertRange(range);
            Node<Integer> newNode = tree.Root;
            assertAll(

                    () -> assertEquals((Integer) 1, newNode.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 3, newNode.Childes.get(1).Keys.get(0))
            );
        }

        @Test
        void testSplit_111() {
            List<Integer> range = Arrays.asList(1, 1, 1);
            tree.insertRange(range);
            Node<Integer> newNode = tree.Root;
            assertAll(

                    () -> assertEquals((Integer) 1, newNode.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 1, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 1, newNode.Childes.get(1).Keys.get(0))
            );
        }

        @Test
        void testSplit_1234() {
            tree.insert(1);
            tree.insert(2);
            tree.insert(3);
            tree.insert(4);
            Node<Integer> newNode = tree.Root;
            assertAll(


                    () -> assertEquals((Integer) 1, newNode.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 3, newNode.Childes.get(1).Keys.get(0)),
                    () -> assertEquals((Integer) 4, newNode.Childes.get(1).Keys.get(1))
            );


        }

        @Test
        void testSplit_12345() {
            tree.insert(1);
            tree.insert(2);
            tree.insert(3);
            tree.insert(4);
            tree.insert(5);
            Node<Integer> newNode = tree.Root;
            assertAll(
                    () -> assertEquals((Integer) 1, newNode.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 4, newNode.Keys.get(1)),
                    () -> assertEquals((Integer) 3, newNode.Childes.get(1).Keys.get(0)),
                    () -> assertEquals((Integer) 5, newNode.Childes.get(2).Keys.get(0)),
                    () -> assertEquals(3, newNode.Childes.size())
            );
        }

        @Test
        void testSplit_123456() {
            tree.insert(1);
            tree.insert(2);
            tree.insert(3);
            tree.insert(4);
            tree.insert(5);
            tree.insert(6);
            Node<Integer> newNode = tree.Root;
            assertAll(
                    () -> assertEquals((Integer) 1, newNode.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 3, newNode.Childes.get(1).Keys.get(0)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 4, newNode.Keys.get(1)),
                    () -> assertEquals((Integer) 5, newNode.Childes.get(2).Keys.get(0)),
                    () -> assertEquals((Integer) 6, newNode.Childes.get(2).Keys.get(1)),
                    () -> assertEquals(3, newNode.Childes.size())
            );
        }

        @Test
        void testSplit_1234567() {
            tree.insert(1);
            tree.insert(2);
            tree.insert(3);
            tree.insert(4);
            tree.insert(5);
            tree.insert(6);
            tree.insert(7);
            Node<Integer> newNode = tree.Root;
            Node<Integer> Child2 = newNode.Childes.get(0);
            Node<Integer> Child6 = newNode.Childes.get(1);


            assertAll(
                    () -> assertEquals((Integer) 4, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 2, Child2.Keys.get(0)),
                    () -> assertEquals((Integer) 6, Child6.Keys.get(0)),
                    () -> assertEquals((Integer) 1, Child2.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 3, Child2.Childes.get(1).Keys.get(0)),
                    () -> assertEquals((Integer) 5, Child6.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 7, Child6.Childes.get(1).Keys.get(0))

            );
        }

        @Test
        void testInsertRange_WhenInsert1234567_ThenShouldMatchwith1234567Tree() {
            List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

            tree.insertRange(range);
            Node<Integer> newNode = tree.Root;
            Node<Integer> Child2 = newNode.Childes.get(0);
            Node<Integer> Child6 = newNode.Childes.get(1);

            assertAll(
                    () -> assertEquals((Integer) 4, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 2, Child2.Keys.get(0)),
                    () -> assertEquals((Integer) 6, Child6.Keys.get(0)),
                    () -> assertEquals((Integer) 1, Child2.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 3, Child2.Childes.get(1).Keys.get(0)),
                    () -> assertEquals((Integer) 5, Child6.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 7, Child6.Childes.get(1).Keys.get(0))
            );


        }

        @Test
        void given1_18Tree_WhenValueInserted_ThenFindReturnThisValue() {
            List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
            tree.insertRange(range);


            Node<Integer> Child4 = tree.Root.Childes.get(0);
            Node<Integer> Child2 = Child4.Childes.get(0);
            Node<Integer> Child1 = Child2.Childes.get(0);
            Node<Integer> Child3 = Child2.Childes.get(1);
            Node<Integer> Child6 = Child4.Childes.get(1);
            Node<Integer> Child5 = Child6.Childes.get(0);
            Node<Integer> Child7 = Child6.Childes.get(1);

            Node<Integer> Child12 = tree.Root.Childes.get(1);
            Node<Integer> Child10 = Child12.Childes.get(0);
            Node<Integer> Child9 = Child10.Childes.get(0);
            Node<Integer> Child11 = Child10.Childes.get(1);

            Node<Integer> Child14_16 = Child12.Childes.get(1);
            Node<Integer> Child13 = Child14_16.Childes.get(0);
            Node<Integer> Child15 = Child14_16.Childes.get(1);
            Node<Integer> Child17_18 = Child14_16.Childes.get(2);


            assertAll(
                    () -> assertEquals(8, tree.Root.Keys.get(0)),
                    () -> assertEquals(4, Child4.Keys.get(0)),
                    () -> assertEquals(2, Child2.Keys.get(0)),
                    () -> assertEquals(1, Child1.Keys.get(0)),
                    () -> assertEquals(3, Child3.Keys.get(0)),
                    () -> assertEquals(6, Child6.Keys.get(0)),
                    () -> assertEquals(5, Child5.Keys.get(0)),
                    () -> assertEquals(7, Child7.Keys.get(0)),

                    () -> assertEquals(12, Child12.Keys.get(0)),
                    () -> assertEquals(9, Child9.Keys.get(0)),
                    () -> assertEquals(11, Child11.Keys.get(0)),
                    () -> assertEquals(14, Child14_16.Keys.get(0)),
                    () -> assertEquals(16, Child14_16.Keys.get(1)),

                    () -> assertEquals(13, Child13.Keys.get(0)),
                    () -> assertEquals(15, Child15.Keys.get(0)),

                    () -> assertEquals(17, Child17_18.Keys.get(0)),
                    () -> assertEquals(18, Child17_18.Keys.get(1))


            );


        }

        @Test
        void given1_18Tree_WhenValueInsertedBakcWards_ThenFindReturnThisValue() {
            List<Integer> range = Arrays.asList(18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
            //tree.insertRange(range);
            for (Integer val : range)
                tree.insert(val);


            Node<Integer> Child7 = tree.Root.Childes.get(0);
            Node<Integer> Child3_5 = Child7.Childes.get(0);
            Node<Integer> Child1_2 = Child3_5.Childes.get(0);
            Node<Integer> Child4 = Child3_5.Childes.get(1);
            Node<Integer> Child6 = Child3_5.Childes.get(2);

            Node<Integer> Child9 = Child7.Childes.get(1);
            Node<Integer> Child8 = Child9.Childes.get(0);
            Node<Integer> Child10 = Child9.Childes.get(1);

            Node<Integer> Child15 = tree.Root.Childes.get(1);
            Node<Integer> Child13 = Child15.Childes.get(0);
            Node<Integer> Child12 = Child13.Childes.get(0);
            Node<Integer> Child14 = Child13.Childes.get(1);
            Node<Integer> Child17 = Child15.Childes.get(1);
            Node<Integer> Child16 = Child17.Childes.get(0);
            Node<Integer> Child18 = Child17.Childes.get(1);


            assertAll(
                    () -> assertEquals(11, tree.Root.Keys.get(0)),
                    () -> assertEquals(3, Child3_5.Keys.get(0)),
                    () -> assertEquals(5, Child3_5.Keys.get(1)),
                    () -> assertEquals(1, Child1_2.Keys.get(0)),
                    () -> assertEquals(2, Child1_2.Keys.get(1)),
                    () -> assertEquals(4, Child4.Keys.get(0)),
                    () -> assertEquals(6, Child6.Keys.get(0)),
                    () -> assertEquals(9, Child9.Keys.get(0)),
                    () -> assertEquals(8, Child8.Keys.get(0)),
                    () -> assertEquals(10, Child10.Keys.get(0)),
                    () -> assertEquals(15, Child15.Keys.get(0)),
                    () -> assertEquals(13, Child13.Keys.get(0)),
                    () -> assertEquals(12, Child12.Keys.get(0)),
                    () -> assertEquals(14, Child14.Keys.get(0)),
                    () -> assertEquals(17, Child17.Keys.get(0)),
                    () -> assertEquals(16, Child16.Keys.get(0)),
                    () -> assertEquals(18, Child18.Keys.get(0))


            );


        }
    }
}