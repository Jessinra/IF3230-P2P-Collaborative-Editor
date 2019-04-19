package Backend.CRDT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static ArrayList<Integer> prevPosition = new ArrayList<>();
    public static ArrayList<Integer> nextPosition = new ArrayList<>();
    public static ArrayList<Integer> newPosition;

    public static void main(String args[]) {

        System.out.println("Test case 1");
        Setup();

        prevPosition.add(1);
        nextPosition.add(2);

        TearDown();

        System.out.println("Test case 2");
        Setup();

        prevPosition.add(4);
        prevPosition.add(1);
        nextPosition.add(4);
        nextPosition.add(3);

        TearDown();

        System.out.println("Test case 3");
        Setup();

        prevPosition.add(4);
        prevPosition.add(3);

        nextPosition.add(5);
        nextPosition.add(0);

        TearDown();

        System.out.println("Test case 4");
        Setup();

        prevPosition.add(4);
        prevPosition.add(2);

        nextPosition.add(4);
        nextPosition.add(3);
        TearDown();

        System.out.println("Test case 5");
        Setup();

        prevPosition.add(1);
        prevPosition.add(1);

        nextPosition.add(2);
        nextPosition.add(0);

        TearDown();

        System.out.println("Test case 6");
        Setup();

        prevPosition.add(4);
        prevPosition.add(2);
        prevPosition.add(0);

        nextPosition.add(4);
        nextPosition.add(2);
        nextPosition.add(1);

        TearDown();

        System.out.println("Test case 7");
        Setup();

        prevPosition.add(4);
        prevPosition.add(3);
        prevPosition.add(1);

        nextPosition.add(4);
        nextPosition.add(3);
        nextPosition.add(2);

        TearDown();

        System.out.println("Test case 8");
        Setup();

        prevPosition.add(4);
        prevPosition.add(1);
        prevPosition.add(0);
        prevPosition.add(1);

        nextPosition.add(4);
        nextPosition.add(1);
        nextPosition.add(1);
        nextPosition.add(0);

        TearDown();

        System.out.println("Test case 9");
        Setup();

        prevPosition.add(4);
        prevPosition.add(4);
        prevPosition.add(3);

        nextPosition.add(4);
        nextPosition.add(5);
        nextPosition.add(0);

        TearDown();
    }

    private static void TearDown() {
        newPosition = Test.generatePosBetween(prevPosition, nextPosition);
        System.out.println(Arrays.toString(prevPosition.toArray()));
        System.out.println(Arrays.toString(newPosition.toArray()));
        System.out.println(Arrays.toString(nextPosition.toArray()));
        System.out.println("==============");
    }

    public static void Setup() {
        prevPosition.clear();
        nextPosition.clear();
    }

    public static ArrayList<Integer> generatePosBetween(List pos1, List pos2) {
        ArrayList<Integer> newPos = new ArrayList<>();
        return Test.generatePosBetween(pos1, pos2, newPos);
    }

    public static ArrayList<Integer> generatePosBetween(List prevPos, List nextPos, ArrayList<Integer> newPos) {

        // Get current sub index number
        int prevDigit = (prevPos.size() > 0) ? (int) prevPos.get(0) : 0;
        int nextDigit = (nextPos.size() > 0) ? (int) nextPos.get(0) : 0;

        // both reach last index tie, (basis when creating new tree depth)
        if (prevPos.size() == 0 || nextPos.size() == 0) {
            return newPos;
        }

        // There's empty slot available for inserting directly
        // eg: [5, 2] is available between [5, 1] and [5, 3]
        if (nextDigit - prevDigit > 1) {
            int newDigit = Test.generateIdBetween(prevDigit, nextDigit);
            newPos.add(newDigit);
            return newPos;
        }

        // Possibly need to extend the tree depth
        else {

            // add last previous's index (1 index before increasing the tree depth)
            // eg: adding 3 from [4, 2, 3] and [4, 2, 4] to make [4, 2, 3, 1]
            newPos.add(prevDigit);

            // increase the tree depth
            if (prevPos.size() == 1) {
                newPos.add(1);
            }

            //
            if (nextDigit - prevDigit == 1) {
                List nextPos1 = prevPos.subList(1, prevPos.size());
                return Test.generatePosBetween(nextPos1, nextPos, newPos);

            } else if (nextDigit - prevDigit == 0) {
                List nextPos1 = prevPos.subList(1, prevPos.size());
                List nextPos2 = nextPos.subList(1, nextPos.size());
                return Test.generatePosBetween(nextPos1, nextPos2, newPos);
            }

            return null;
        }
    }

    private static int generateIdBetween(int id1, int id2) {
        return (id1 + 1 <= id2) ? id1 + 1 : -1;
    }
}
