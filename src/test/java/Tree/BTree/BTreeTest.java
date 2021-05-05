package Tree.BTree;

import org.junit.Assert;
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

abstract class BTreeTest {
    BTree<Integer> tree;

    @BeforeEach
    abstract  void init();


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
        @Nested
        class DeleteFromLeafTests {
            @Nested
            class DeleteFromLeafWithoutOrderViolationTests {
                @Test
                void givenLastLeafKey_WhenDeleteMFromMiddleLeaf_ThenBeOk() {
                    List<Integer> range = Arrays.asList(0, 10, 20, 5, 15, 8, 4, 9);
                    tree.insertRange(range);
                    tree.delete(9);


                }

                @Test
                void givenLastLeafKey_WhenDeleteFromLeftLeafDoes_ThenBeOk() {
                    List<Integer> range = Arrays.asList(0, 10, 20, 5, 15, 8, 4, 9);
                    tree.insertRange(range);

                    tree.delete(4);
                }

                @Test
                void givenLastLeafKey_WhenDeletFromRightLeaf_ThenBeOk() {
                    List<Integer> range = Arrays.asList(0, 10, 20, 5, 15, 8, 4, 9);
                    tree.insertRange(range);

                    tree.delete(20);
                }

                @Test
                void givenFirstLeafKey_WhenDeleteMFromiddleLeaf_ThenBeOk() {
                    List<Integer> range = Arrays.asList(0, 10, 20, 5, 15, 8, 4, 9);
                    tree.insertRange(range);
                    tree.delete(0);


                }

                @Test
                void givenFirstLeafKey_WhenDeletFromLeftLeafDoesNotViolateOrder_ThenBeOk() {
                    List<Integer> range = Arrays.asList(0, 10, 20, 5, 15, 8, 4, 9);
                    tree.insertRange(range);

                    tree.delete(4);
                }

                @Test
                void givenFirstLeafKey_WhenDeletFromRightLeafDoesNotViolateOrder_ThenBeOk() {
                    List<Integer> range = Arrays.asList(0, 10, 20, 5, 15, 8, 4, 9);
                    tree.insertRange(range);

                    tree.delete(15);
                }

            }

            @Nested
            class DeleteFromLeafWithOrderViolationTests {


                @Test
                void givenLeafKey_WhenReplaceFromLeft_ThenBeOk() {
                    List<Integer> range = Arrays.asList(0, 10, 20, 5, 15, 8, 4);
                    tree.insertRange(range);
                    tree.delete(8);
                }

                @Test
                void givenLeafKey_WhenReplaceFromRight_ThenBeOk() {
                    List<Integer> range = Arrays.asList(0, 10, 20, 5, 15, 8);
                    tree.insertRange(range);
                    tree.delete(8);
                }

                @Test
                void givenLeftLeafKey_WhenTreeIs12345_ThenBeOk() {
                    List<Integer> range = Arrays.asList(1, 2, 3, 4, 5);
                    tree.insertRange(range);
                    tree.delete(1);

                }

                @Test
                void givenMiddleLeafKey_WhenTreeIs12345_ThenBeOk() {
                    List<Integer> range = Arrays.asList(1, 2, 3, 4, 5);
                    tree.insertRange(range);
                    tree.delete(3);

                }

                @Test
                void givenRightLeafKey_WhenTreeIs12345_ThenBeOk() {
                    List<Integer> range = Arrays.asList(1, 2, 3, 4, 5);
                    tree.insertRange(range);
                    tree.delete(5);

                }
            }
        }

        @Nested
        class DeleteFromNodeTests {
            @Test
            void givenNodeLeftKey_WhenHZ_ThenBeOk() {
                List<Integer> range = Arrays.asList(0, 10, 20, 5, 15, 8, 12);
                tree.insertRange(range);
                tree.delete(5);

            }

            @Test
            void givenNodeRightKey_WhenHZ_ThenBeOk() {
                List<Integer> range = Arrays.asList(0, 10, 20, 5, 15, 8, 12);
                tree.insertRange(range);
                tree.delete(15);

            }

            @Test
            void givenNodeRightKey_WhenTreeIs12345_ThenBeOk() {
                List<Integer> range = Arrays.asList(1,2,3,4,5);
                tree.insertRange(range);
                tree.delete(4);

            }
            @Test
            void givenNodeLeftKey_WhenTreeIs12345_ThenBeOk() {
                List<Integer> range = Arrays.asList(1,2,3,4,5);
                tree.insertRange(range);
                tree.delete(2);

            }


        }

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
                    assertEquals(Arrays.asList(1, 1, 1), actualVals);
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
                    assertEquals(Arrays.asList(2, 2, 3), actualVals);
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
                    assertEquals(Arrays.asList(6, 6, 7, 7, 8), actualVals);
                }

                @Test
                void given5_WhenTreeIs11to77BackWards_ThenReturnEveryPair11to77() {
                    tree.insertRange(Arrays.asList(8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findGreater(5);
                    assertEquals(Arrays.asList(6, 6, 7, 7, 8), actualVals);
                }

                @Test
                void given1to7_WhenTreeIs11to77RandomOrder_ThenReturnEveryPair11to77() {
                    tree.insertRange(Arrays.asList(5, 7, 3, 1, 2, 6, 4, 8, 7, 1, 6, 4, 2, 5, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findGreater(5);
                    assertEquals(Arrays.asList(6, 6, 7, 7, 8), actualVals);
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

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(0, 4);


                    assertEquals(Arrays.asList(1, 2, 3), actualVals);
                }

                @Test
                void given0and3_WhenTreeIs1to3_ThenReturn3() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(0, 3);

                    assertEquals(Arrays.asList(1, 2, 3), actualVals);
                }

                @Test
                void given0and2_WhenTreeIs1to3_ThenReturn3() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(0, 2);

                    assertEquals(Arrays.asList(1, 2), actualVals);
                }

                @Test
                void given0and1_WhenTreeIs1to3_ThenReturn1() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(0, 1);


                    assertEquals(Arrays.asList(1), actualVals);
                }

                @Test
                void givenMin1and0_WhenTreeIs1to3_ThenReturnEmpty() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(-1, 0);

                    assertEquals(Collections.emptyList(), actualVals);
                }

                @Test
                void givenMin1and10_WhenTreeIs1to3_ThenReturnEmpty() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(-1, 10);

                    assertEquals(Arrays.asList(1, 2, 3), actualVals);
                }

                @Test
                void given0and0_WhenTreeIs1to3_ThenReturnEmpty() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(0, 0);

                    assertEquals(Collections.emptyList(), actualVals);
                }

                @Test
                void given5and5_WhenTreeIs1to3_ThenReturnEmpty() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(5, 5);

                    assertEquals(Collections.emptyList(), actualVals);
                }

                @ParameterizedTest
                @ValueSource(ints = {1, 2, 3})
                void givenValAndVal1to3_WhenTreeIs1to3_ThenVal(Integer val) {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(val, val);

                    assertEquals(Arrays.asList(val), actualVals);
                }

            }

            @Nested
            class FindMultipleTests {

                @Test
                void given0and2_WhenTreeIs111_ThenReturn111() {
                    tree.insertRange(Arrays.asList(1, 1, 1));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(0, 2);
                    assertEquals(Arrays.asList(1, 1, 1), actualVals);
                }

                @Test
                void given3and4_WhenTreeIs11223_ThenReturn3() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(3, 4);
                    assertEquals(Arrays.asList(3), actualVals);
                }

                @Test
                void given2and3_WhenTreeIs11223_ThenReturn223() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(2, 3);
                    assertEquals(Arrays.asList(2, 2, 3), actualVals);
                }

                @Test
                void given0and5_WhenTreeIs11223_ThenReturn11223() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(0, 5);
                    assertEquals(Arrays.asList(1, 1, 2, 2, 3), actualVals);
                }

                @Test
                void given2and4_WhenTreeIs11to778_ThenReturn223344() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(2, 4);
                    assertEquals(Arrays.asList(2, 2, 3, 3, 4, 4), actualVals);
                }

                @Test
                void given5_WhenTreeIs11to77BackWards_ThenReturn223344() {
                    tree.insertRange(Arrays.asList(8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(2, 4);
                    assertEquals(Arrays.asList(2, 2, 3, 3, 4, 4), actualVals);
                }

                @Test
                void given1to7_WhenTreeIs11to77RandomOrder_ThenReturn223344() {
                    tree.insertRange(Arrays.asList(5, 7, 3, 1, 2, 6, 4, 8, 7, 1, 6, 4, 2, 5, 3));
                    ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.findInRange(2, 4);
                    assertEquals(Arrays.asList(2, 2, 3, 3, 4, 4), actualVals);
                }
            }

        }

        @Nested
        class FindMinMaxTests {
            @Nested
            class FindMinTests {
                @Test
                void given_WhenTreeIs1to3_ThenReturn1() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    Integer actualVal = tree.findMin();


                    assertEquals(1, actualVal);
                }

                @Test
                void given_WhenTreeIs11to778_ThenReturn1() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8));
                    Integer actualVal = tree.findMin();
                    assertEquals(1, actualVal);
                }

                @Test
                void given_WhenTreeIs11to77BackWards_ThenReturn1() {
                    tree.insertRange(Arrays.asList(8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1));
                    Integer actualVal = tree.findMin();
                    assertEquals(1, actualVal);
                }

                @Test
                void given_WhenTreeIs11to77RandomOrder_ThenReturn1() {
                    tree.insertRange(Arrays.asList(5, 7, 3, 1, 2, 6, 4, 8, 7, 1, 6, 4, 2, 5, 3));
                    Integer actualVal = tree.findMin();
                    assertEquals(1, actualVal);
                }


            }

            @Nested
            class FindMaxTests {
                @Test
                void given_WhenTreeIs1to3_ThenReturn1() {
                    List<Integer> range = Arrays.asList(1, 2, 3);
                    tree.insertRange(range);

                    Integer actualVal = tree.findMax();


                    assertEquals(3, actualVal);
                }

                @Test
                void given_WhenTreeIs11to778_ThenReturn8() {
                    tree.insertRange(Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8));
                    Integer actualVal = tree.findMax();
                    assertEquals(8, actualVal);
                }

                @Test
                void given_WhenTreeIs11to77BackWards_ThenReturn8() {
                    tree.insertRange(Arrays.asList(8, 7, 7, 6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1));
                    Integer actualVal = tree.findMax();
                    assertEquals(8, actualVal);
                }

                @Test
                void given_WhenTreeIs11to77RandomOrder_ThenReturn8() {
                    tree.insertRange(Arrays.asList(5, 7, 3, 1, 2, 6, 4, 8, 7, 1, 6, 4, 2, 5, 3));
                    Integer actualVal = tree.findMax();
                    assertEquals(8, actualVal);
                }


            }

        }
    }




    @Nested
    class iteratorTests {

        @Nested
        class HasNextTests {
            @Test
            void givenNullPosIterator_WhenTreeIsEmpty_ThenHasNextReturnFalse() {
                Iterator<Integer> it = tree.iterator();
                assertEquals(false, it.hasNext());
            }

            @Test
            void givenNullPosIterator_WhenTreeIsNotEmpty_ThenHasNextReturnTrue() {

                tree.insertRange(Arrays.asList(1, 2, 3));

                Iterator<Integer> it = tree.iterator();
                boolean actualVal = it.hasNext();
                assertEquals(true, actualVal);
            }

            @Test
            void givenLastPosIterator_WhenTreeIsNotEmpty_ThenHasNextReturnFalse() {
                tree.insertRange(Arrays.asList(1, 2, 3));

                Iterator<Integer> it = tree.iterator();
                it.next();
                it.next();
                it.next();

                assertEquals(false, it.hasNext());
            }
        }

        @Nested
        class LoopTests {
            @Test
            void givenPrintLn_WhenTreeIs12345_ThenPrintVals() {
                tree.insertRange(Arrays.asList(1, 2, 3, 4, 5));
                for (Object val : tree)
                    System.out.println(val);
            }

            @Test
            void givenPrintLn_WhenTreeIs12345BackWards_ThenPrintVals() {
                tree.insertRange(Arrays.asList(5, 4, 3, 2, 1));
                for (Object val : tree)
                    System.out.println(val);
            }

            @Test
            void givenPrintLn_WhenTreeIs12345RandomOrder_ThenPrintVals() {
                tree.insertRange(Arrays.asList(4, 2, 3, 1, 5));
                for (Object val : tree)
                    System.out.println(val);
            }

            @Test
            void given3Trees_WhenTreesWithDifferentNodeOrder_ThenPrintSameSequence() {
                BTree<Integer> forwardOrder = new BTree<Integer>(3);
                BTree<Integer> backwardOrder = new BTree<Integer>(3);
                BTree<Integer> randomOrder = new BTree<Integer>(3);

                forwardOrder.insertRange(Arrays.asList(1, 2, 3, 4, 5));
                backwardOrder.insertRange(Arrays.asList(5, 4, 3, 2, 1));
                randomOrder.insertRange(Arrays.asList(4, 2, 3, 1, 5));

                String s1 = "", s2 = "", s3 = "";
                for (Object val : tree)
                    s1 += val.toString() + " ";
                for (Object val : tree)
                    s2 += val.toString() + " ";
                for (Object val : tree)
                    s3 += val.toString() + " ";
                String finalS1 = s1;
                String finalS2 = s2;
                String finalS3 = s3;
                assertAll(
                        () -> assertEquals(finalS1, finalS2),
                        () -> assertEquals(finalS2, finalS3));

            }
        }

        @Nested
        class NextTests {
            @Test
            void givenNullPosIterator_WhenTreeIsOneNode_ThenNextReturnFirstVal() {
                tree.insertRange(Arrays.asList(1, 2));

                Iterator<Integer> it = tree.iterator();
                Integer val1 = it.next();

                assertAll(
                        () -> assertEquals((Integer) 1, val1)
                );

            }

            @Test
            void givenNullPosIterator_WhenTreeIsOneNode_ThenNext2ReturnSecondtVal() {
                tree.insertRange(Arrays.asList(1, 2));

                Iterator<Integer> it = tree.iterator();
                it.next();
                Integer val = it.next();

                assertAll(
                        () -> assertEquals((Integer) 2, val)
                );

            }

            @Test
            void givenNullPosIterator_WhenTreeIs123_ThenNextReturnFirstVal() {
                tree.insertRange(Arrays.asList(1, 2, 3));

                Iterator<Integer> it = tree.iterator();
                Integer val1 = it.next();

                assertAll(
                        () -> assertEquals((Integer) 1, val1)
                );

            }

            @Test
            void givenNullPosIterator_WhenTreeIs123_ThenNextReturn1to3() {
                tree.insertRange(Arrays.asList(1, 2, 3));

                Iterator<Integer> it = tree.iterator();
                Integer val1 = it.next();
                Integer val2 = it.next();
                Integer val3 = it.next();
                assertAll(

                        () -> assertEquals((Integer) 1, val1),
                        () -> assertEquals((Integer) 2, val2),
                        () -> assertEquals((Integer) 3, val3)
                );
            }

            @Test
            void givenNullPosIterator_WhenTreeIs12345_ThenNextReturn1to5() {
                tree.insertRange(Arrays.asList(1, 2, 3, 4, 5));

                Iterator<Integer> it = tree.iterator();
                Integer val1 = it.next();
                Integer val2 = it.next();
                Integer val3 = it.next();
                Integer val4 = it.next();
                Integer val5 = it.next();
                assertAll(

                        () -> assertEquals((Integer) 1, val1),
                        () -> assertEquals((Integer) 2, val2),
                        () -> assertEquals((Integer) 3, val3),
                        () -> assertEquals((Integer) 4, val4),
                        () -> assertEquals((Integer) 5, val5)
                );
            }
            //TODO тест для длинного набора - делает N некстов, последний должен вернуть N-нный элемент

            @ParameterizedTest
            @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7})
            void givenNullPointIterator_WhenTree1to7_ThenFindReturnThisValue(Integer Val) {
                List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
                tree.insertRange(range);
                Iterator<Integer> it = tree.iterator();

                Integer lastVal = null;
                for (int i = 0; i < Val; i++) {
                    lastVal = it.next();
                }

                assertEquals(Val, lastVal);
            }


            @ParameterizedTest
            @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18})
            void givenNullPointIterator_WhenTree1to18_ThenFindReturnThisValue(Integer Val) {
                List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18);
                tree.insertRange(range);
                Iterator<Integer> it = tree.iterator();

                Integer lastVal = null;
                for (int i = 0; i < Val; i++) {
                    lastVal = it.next();
                }

                assertEquals(Val, lastVal);
            }

            @ParameterizedTest
            @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18})
            void givenNullPointIterator_WhenTree1to18InsertedBackwards_ThenFindReturnThisValue(Integer Val) {
                List<Integer> range = Arrays.asList(18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
                tree.insertRange(range);
                Iterator<Integer> it = tree.iterator();

                Integer lastVal = null;
                for (int i = 0; i < Val; i++) {
                    lastVal = it.next();
                }

                assertEquals(Val, lastVal);
            }


            @ParameterizedTest
            @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18})
            void givenNullPointIterator_WhenTree1to18InsertedRandomly_ThenFindReturnThisValue(Integer Val) {
                List<Integer> range = Arrays.asList(6, 5, 15, 7, 16, 4, 18, 2, 14, 9, 13, 17, 1, 12, 8, 10, 3, 11);
                tree.insertRange(range);
                Iterator<Integer> it = tree.iterator();

                Integer lastVal = null;
                for (int i = 0; i < Val; i++) {
                    lastVal = it.next();
                }

                assertEquals(Val, lastVal);
            }
        }


    }

}