package Tree.BTree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BTreeTest {
    BTree<Integer> tree;

    @BeforeEach
    public void init() {
        tree = new BTree<Integer>(3);


    }

    @Nested
    class IOUderstandingTest {
        @TempDir
        File TempDir;

        @Test
        void writingToTestDirWork() throws IOException {
            assertTrue(this.TempDir.isDirectory(), "Should be a directory ");

            File letters = new File(TempDir, "letters.txt");
            List<String> lines = Arrays.asList("x", "y", "z");


            Files.write(letters.toPath(), lines);

            assertAll(
                    () -> assertTrue(Files.exists(letters.toPath()), "File should exist"),
                    () -> assertLinesMatch(lines, Files.readAllLines(letters.toPath())));
        }

        @Test
        void ReadingFromTestDirWork() throws IOException {
            File treeContent = new File(TempDir, "treeContent.txt");
            List<String> linesWrited = Arrays.asList("1", "2", "3");
            Files.write(treeContent.toPath(), linesWrited);

            if (Files.isReadable(treeContent.toPath())) {
                List<String> linesReaded;
                linesReaded = Files.readAllLines(treeContent.toPath());
                assertLinesMatch(linesWrited, linesReaded);
            } else fail();

        }


    }


    @Nested
    class ConstructTests {
        @TempDir
        File TempDir;

        @Test
        void givenFileWit123_WhenConstructWithClassParam_ThenTreeShouldMatchExpectations() throws IOException {
            File treeContent = new File(TempDir, "treeContent.txt");
            List<String> linesWrited = Arrays.asList("1 2", "3 4", "5");
            Files.write(treeContent.toPath(), linesWrited);

            tree.construct(treeContent.toPath(), Integer.class);


            Node<Integer> newNode = tree.Root;
            assertAll(
                    () -> assertFalse(
                            tree.isEmpty()),
                    () -> assertEquals((Integer) 1, newNode.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 4, newNode.Keys.get(1)),
                    () -> assertEquals((Integer) 3, newNode.Childes.get(1).Keys.get(0)),
                    () -> assertEquals((Integer) 5, newNode.Childes.get(2).Keys.get(0)),
                    () -> assertEquals(3, newNode.Childes.size())
            );
        }


    }


    @Nested
    class DeleteTests {

    }

    @Nested
    class FindTests {
        @Nested
        class FindEqualTests {
            @Nested
            class FindSingleTests {
                @ParameterizedTest
                @ValueSource(ints = {1, 2, 3})
                void given1to3_WhenTreeIs1to3_ThenReturnEveryVal(Integer val) {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findEqual(val);

                    assertAll(
                            () -> assertEquals(val, actualVals.get(0)),
                            () -> assertEquals((Integer) 1, actualVals.size()));
                }

                @ParameterizedTest
                @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7})
                void given1to7_WhenTreeIs1to7_ThenReturnEveryVal(Integer val) {
                    List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findEqual(val);

                    assertAll(
                            () -> assertEquals(val, actualVals.get(0)),
                            () -> assertEquals((Integer) 1, actualVals.size()));
                }


                @ParameterizedTest
                @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 0, -1, 200, -200, Integer.MIN_VALUE, Integer.MAX_VALUE})
                void given1234567_WhenValueInserted_ThenFindReturnThisValue(Integer val) {
                    tree.insert(val);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findEqual(val);


                    assertAll(
                            () -> assertEquals(val, actualVals.get(0)),
                            () -> assertEquals((Integer) 1, actualVals.size()));
                }

                @ParameterizedTest
                @ValueSource(ints = {1, 2, 3})
                void given123Tree_WhenValueInserted_ThenFindReturnThisValue(Integer val) {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findEqual(val);

                    assertAll(
                            () -> assertEquals(val, actualVals.get(0)),
                            () -> assertEquals((Integer) 1, actualVals.size()));
                }


                @ParameterizedTest
                @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18})
                void given1_18Tree_WhenValueInserted_ThenFindReturnThisValue(Integer val) {
                    List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findEqual(val);

                    assertAll(
                            () -> assertEquals(val, actualVals.get(0)),
                            () -> assertEquals((Integer) 1, actualVals.size()));
                }

                @ParameterizedTest
                @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18})
                void given1_18Tree_WhenValuesInsertedBackwards_ThenFindReturnThisValue(Integer Val) {
                    List<Integer> range = Arrays.asList(18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
                    tree.insertRange(range);
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findEqual(Val);

                    assertEquals(Val, actualVals.get(0));
                }


                @ParameterizedTest
                @ValueSource(ints = {8, 0, -1, 200, -200, Integer.MIN_VALUE, Integer.MAX_VALUE})
                void given123Tree_WhenValueNotInserted_ThenFindReturnThisValue(Integer val) {
                    List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findEqual(val);

                    assertEquals(true, actualVals.isEmpty());
                }

            }

            //TODO тест с перменным массивом ввода должен вернуть каждое из его значений
            @Nested
            class FindMultipleTests {

                @Test
                void given1_WhenTreeIs111_ThenReturn111() {
                    tree.insertRange(Arrays.asList(1, 1, 1));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findEqual(1);
                    assertEquals(Arrays.asList(1, 1, 1), actualVals);
                }

                @Test
                void given1_WhenTreeIs11223_ThenReturn11() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findEqual(1);
                    assertEquals(Arrays.asList(1, 1), actualVals);
                }

                @Test
                void given2_WhenTreeIs11223_ThenReturn22() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findEqual(2);
                    assertEquals(Arrays.asList(2, 2), actualVals);
                }

                @Test
                void given3_WhenTreeIs11223_ThenReturn3() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findEqual(3);
                    assertEquals(Arrays.asList(3), actualVals);
                }

                @ParameterizedTest
                @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7})
                void given1to7_WhenTreeIs11to778_ThenReturnEveryPair11to77(Integer val) {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findEqual(val);
                    assertEquals(Arrays.asList(val, val), actualVals);
                }

                @ParameterizedTest
                @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7})
                void given1to7_WhenTreeIs11to77BackWards_ThenReturnEveryPair11to77(Integer val) {
                    tree.insertRange(Arrays.asList(8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findEqual(val);
                    assertEquals(Arrays.asList(val, val), actualVals);
                }

                @ParameterizedTest
                @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7})
                void given1to7_WhenTreeIs11to77RandomOrder_ThenReturnEveryPair11to77(Integer val) {
                    tree.insertRange(Arrays.asList(5, 7, 3, 1, 2, 6, 4, 8, 7, 1, 6, 4, 2, 5, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findEqual(val);
                    assertEquals(Arrays.asList(val, val), actualVals);
                }
            }

        }

        @Nested
        class FindLessTests {
            @Nested
            class FindSingleTests {
                @Test
                void given0_WhenTreeIs1to3_ThenReturnEmpty() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findLess(0);

                    assertAll(

                            () -> assertEquals((Integer) 0, actualVals.size()));
                }

                @Test
                void given1_WhenTreeIs1to3_ThenReturnEmpty() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findLess(1);

                    assertAll(

                            () -> assertEquals((Integer) 0, actualVals.size()));
                }

                @Test
                void given2_WhenTreeIs1to3_ThenReturn1() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findLess(2);

                    assertEquals(Arrays.asList(1), actualVals);
                }

                @Test
                void given3_WhenTreeIs1to3_ThenReturn12() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findLess(3);

                    assertEquals(Arrays.asList(1, 2), actualVals);
                }

                @Test
                void given4_WhenTreeIs1to3_ThenReturn123() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findLess(4);

                    assertEquals(Arrays.asList(1, 2, 3), actualVals);

                }


            }


            @Nested
            class FindMultipleTests {

                @Test
                void given2_WhenTreeIs111_ThenReturn111() {
                    tree.insertRange(Arrays.asList(1, 1, 1));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findLess(2);
                    assertEquals(Arrays.asList(1, 1, 1), actualVals);
                }

                @Test
                void given2_WhenTreeIs11223_ThenReturn11() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findLess(2);
                    assertEquals(Arrays.asList(1, 1), actualVals);
                }

                @Test
                void given3_WhenTreeIs11223_ThenReturn1122() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findLess(3);
                    assertEquals(Arrays.asList(1, 1, 2, 2), actualVals);
                }

                @Test
                void given4_WhenTreeIs11223_ThenReturn11223() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findLess(4);
                    assertEquals(Arrays.asList(1, 1, 2, 2, 3), actualVals);
                }

                @Test
                void given5_WhenTreeIs11to778_ThenReturnEveryPairLessThen5() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findLess(5);
                    assertEquals(Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4), actualVals);
                }

                @Test
                void given5_WhenTreeIs11to77BackWards_ThenReturnEveryPair11to77() {
                    tree.insertRange(Arrays.asList(8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findLess(5);
                    assertEquals(Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4), actualVals);
                }

                @Test
                void given1to7_WhenTreeIs11to77RandomOrder_ThenReturnEveryPair11to77() {
                    tree.insertRange(Arrays.asList(5, 7, 3, 1, 2, 6, 4, 8, 7, 1, 6, 4, 2, 5, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findLess(5);
                    assertEquals(Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4), actualVals);
                }
            }

        }

        @Nested
        class FindGreaterTests {
            @Nested
            class FindSingleTests {
                @Test
                void given0_WhenTreeIs1to3_ThenReturn123() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findGreater(0);


                    assertEquals(Arrays.asList(1, 2, 3), actualVals);
                }

                @Test
                void given1_WhenTreeIs1to3_ThenReturn23() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findGreater(1);


                    assertEquals(Arrays.asList(2, 3), actualVals);
                }

                @Test
                void given2_WhenTreeIs1to3_ThenReturn3() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findGreater(2);

                    assertEquals(Arrays.asList(3), actualVals);
                }

                @Test
                void given3_WhenTreeIs1to3_ThenReturnEmpty() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findGreater(3);

                    assertEquals(Collections.emptyList(), actualVals);
                }

                @Test
                void given4_WhenTreeIs1to3_ThenReturnEmpty() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findGreater(4);


                    assertEquals(Collections.emptyList(), actualVals);
                }

            }

            @Nested
            class FindMultipleTests {

                @Test
                void given0_WhenTreeIs111_ThenReturn111() {
                    tree.insertRange(Arrays.asList(1, 1, 1));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findGreater(0);
                    assertEquals(Arrays.asList(1,1,1), actualVals);
                }

                @Test
                void given2_WhenTreeIs11223_ThenReturn3() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findGreater(2);
                    assertEquals(Arrays.asList(3), actualVals);
                }

                @Test
                void given1_WhenTreeIs11223_ThenReturn223() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findGreater(1);
                    assertEquals(Arrays.asList( 2, 2,3), actualVals);
                }

                @Test
                void given0_WhenTreeIs11223_ThenReturn11223() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findGreater(0);
                    assertEquals(Arrays.asList(1, 1, 2, 2, 3), actualVals);
                }

                @Test
                void given5_WhenTreeIs11to778_ThenReturnEveryPairLessThen5() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findGreater(5);
                    assertEquals(Arrays.asList( 6, 6, 7, 7, 8), actualVals);
                }

                @Test
                void given5_WhenTreeIs11to77BackWards_ThenReturnEveryPair11to77() {
                    tree.insertRange(Arrays.asList(8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findGreater(5);
                    assertEquals(Arrays.asList( 6, 6, 7, 7, 8), actualVals);
                }

                @Test
                void given1to7_WhenTreeIs11to77RandomOrder_ThenReturnEveryPair11to77() {
                    tree.insertRange(Arrays.asList(5, 7, 3, 1, 2, 6, 4, 8, 7, 1, 6, 4, 2, 5, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findGreater(5);
                    assertEquals(Arrays.asList( 6, 6, 7, 7, 8), actualVals);
                }
            }

        }
        @Nested
        class FindBetweenTests {
            @Nested
            class FindSingleTests {
                @Test
                void givenMin0and4_WhenTreeIs1to3_ThenReturn123() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(0,4);


                    assertEquals(Arrays.asList(1, 2, 3), actualVals);
                }

                @Test
                void given0and3_WhenTreeIs1to3_ThenReturn3() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(0,3);

                    assertEquals(Arrays.asList(1,2,3), actualVals);
                }

                @Test
                void given0and2_WhenTreeIs1to3_ThenReturn3() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(0,2);

                    assertEquals(Arrays.asList(1,2), actualVals);
                }
                @Test
                void given0and1_WhenTreeIs1to3_ThenReturn1() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(0,1);


                    assertEquals(Arrays.asList(1), actualVals);
                }
                @Test
                void givenMin1and0_WhenTreeIs1to3_ThenReturnEmpty() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(-1,0);

                    assertEquals(Collections.emptyList(), actualVals);
                }
                @Test
                void givenMin1and10_WhenTreeIs1to3_ThenReturnEmpty() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(-1,10);

                    assertEquals(Arrays.asList(1, 2, 3), actualVals);
                }
                @Test
                void given0and0_WhenTreeIs1to3_ThenReturnEmpty() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(0,0);

                    assertEquals(Collections.emptyList(), actualVals);
                }
                @Test
                void given5and5_WhenTreeIs1to3_ThenReturnEmpty() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(5,5);

                    assertEquals(Collections.emptyList(), actualVals);
                }
                @ParameterizedTest
                @ValueSource(ints = {1,2,3})
                void givenValAndVal1to3_WhenTreeIs1to3_ThenVal(Integer val) {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(val,val);

                    assertEquals(Arrays.asList(val), actualVals);
                }

            }

            @Nested
            class FindMultipleTests {

                @Test
                void given0and2_WhenTreeIs111_ThenReturn111() {
                    tree.insertRange(Arrays.asList(1, 1, 1));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(0,2);
                    assertEquals(Arrays.asList(1,1,1), actualVals);
                }

                @Test
                void given3and4_WhenTreeIs11223_ThenReturn3() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>)  tree.findInRange(3,4);
                    assertEquals(Arrays.asList(3), actualVals);
                }

                @Test
                void given2and3_WhenTreeIs11223_ThenReturn223() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>)  tree.findInRange(2,3);
                    assertEquals(Arrays.asList( 2, 2,3), actualVals);
                }

                @Test
                void given0and5_WhenTreeIs11223_ThenReturn11223() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(0,5);
                    assertEquals(Arrays.asList(1, 1, 2, 2, 3), actualVals);
                }

                @Test
                void given2and4_WhenTreeIs11to778_ThenReturn223344() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>)  tree.findInRange(2,4);
                    assertEquals(Arrays.asList(2, 2, 3, 3, 4, 4), actualVals);
                }

                @Test
                void given5_WhenTreeIs11to77BackWards_ThenReturn223344() {
                    tree.insertRange(Arrays.asList(8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>)  tree.findInRange(2,4);
                    assertEquals(Arrays.asList(2, 2, 3, 3, 4, 4), actualVals);
                }

                @Test
                void given1to7_WhenTreeIs11to77RandomOrder_ThenReturn223344() {
                    tree.insertRange(Arrays.asList(5, 7, 3, 1, 2, 6, 4, 8, 7, 1, 6, 4, 2, 5, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>)  tree.findInRange(2,4);
                    assertEquals(Arrays.asList(2, 2, 3, 3, 4, 4), actualVals);
                }
            }

        }
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