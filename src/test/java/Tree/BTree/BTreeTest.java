package Tree.BTree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.Arrays;
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

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 0, -1, 200, -200, Integer.MIN_VALUE, Integer.MAX_VALUE})
        void given1234567_WhenValueInserted_ThenFindReturnThisValue(Integer val) {
            tree.insert(val);

            ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.find(val);


            assertAll(
                    () -> assertEquals(val, actualVals.get(0)),
                    () -> assertEquals((Integer) 1, actualVals.size()));
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3})
        void given123Tree_WhenValueInserted_ThenFindReturnThisValue(Integer val) {
            List<Integer> range = Arrays.asList(1, 2, 3);
            tree.insertRange(range);

            ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.find(val);

            assertAll(
                    () -> assertEquals(val, actualVals.get(0)),
                    () -> assertEquals((Integer) 1, actualVals.size()));
        }


        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7})
        void given1234567Tree_WhenValueInserted_ThenFindReturnThisValue(Integer val) {
            List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
            tree.insertRange(range);

            ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.find(val);

            assertAll(
                    () -> assertEquals(val, actualVals.get(0)),
                    () -> assertEquals((Integer) 1, actualVals.size()));
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7,8,9,10,11,12,13,14,15,16,17,18})
        void given1_18Tree_WhenValueInserted_ThenFindReturnThisValue(Integer val) {
            List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6, 7,8,9,10,11,12,13,14,15,16,17,18);
            tree.insertRange(range);

            ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.find(val);

            assertAll(
                    () -> assertEquals(val, actualVals.get(0)),
                    () -> assertEquals((Integer) 1, actualVals.size()));
        }


        @ParameterizedTest
        @ValueSource(ints = {8, 0, -1, 200, -200, Integer.MIN_VALUE, Integer.MAX_VALUE})
        void given123Tree_WhenValueNotInserted_ThenFindReturnThisValue(Integer val) {
            List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
            tree.insertRange(range);

            ArrayList<Integer> actualVals = (ArrayList<Integer>) tree.find(val);

            assertEquals(true, actualVals.isEmpty());
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
            List<Integer> range = Arrays.asList(2,3,1);
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
            List<Integer> range = Arrays.asList(1,1,1);
            tree.insertRange(range);
            Node<Integer> newNode = tree.Root;
            assertAll(

                    () -> assertEquals((Integer) 1, newNode.Childes.get(0).Keys.get(0)),
                    () -> assertEquals((Integer) 2, newNode.Keys.get(0)),
                    () -> assertEquals((Integer) 3, newNode.Childes.get(1).Keys.get(0))
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
            List<Integer> range = Arrays.asList(1, 2, 3, 4, 5, 6, 7,8,9,10,11,12,13,14,15,16,17,18);
            tree.insertRange(range);




            Node<Integer> Child4 = tree.Root.Childes.get(0);
            Node<Integer> Child12 = tree.Root.Childes.get(1);
            Node<Integer> Child2 = Child4.Childes.get(0);
            Node<Integer> Child6 = Child4.Childes.get(1);
            Node<Integer> Child10 = Child12.Childes.get(0);
            Node<Integer> Child14_16= Child12.Childes.get(1);


            assertAll(
                    () -> assertEquals(8, tree.Root.Keys.get(0)),

                    () -> assertEquals(4, Child4.Keys.get(0)),

                    () -> assertEquals(12, Child12.Keys.get(0)),

                    () -> assertEquals(2, Child2.Keys.get(0)),
                    () -> assertEquals(6, Child6.Keys.get(0)),

                    () -> assertEquals(10, Child10.Keys.get(0)),

                    () -> assertEquals(14, Child14_16.Keys.get(0)),
                    () -> assertEquals(16, Child14_16.Keys.get(1))

            );


        }
        void given1_18Tree_WhenValuesInsertedBackwards_ThenFindReturnThisValue() {
            List<Integer> range = Arrays.asList(18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1);
            tree.insertRange(range);


            Node<Integer> Child4 = tree.Root.Childes.get(0);
            Node<Integer> Child12 = tree.Root.Childes.get(1);
            Node<Integer> Child2 = Child4.Childes.get(0);
            Node<Integer> Child6 = Child4.Childes.get(1);
            Node<Integer> Child10 = Child12.Childes.get(0);
            Node<Integer> Child14_16 = Child12.Childes.get(1);


            assertAll(
                    () -> assertEquals(8, tree.Root.Keys.get(0)),

                    () -> assertEquals(4, Child4.Keys.get(0)),

                    () -> assertEquals(12, Child12.Keys.get(0)),

                    () -> assertEquals(2, Child2.Keys.get(0)),
                    () -> assertEquals(6, Child6.Keys.get(0)),

                    () -> assertEquals(10, Child10.Keys.get(0)),

                    () -> assertEquals(14, Child14_16.Keys.get(0)),
                    () -> assertEquals(16, Child14_16.Keys.get(1))

            );
        }
    }

}