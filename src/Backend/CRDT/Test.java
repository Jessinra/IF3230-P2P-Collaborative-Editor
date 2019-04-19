//package Backend.CRDT;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static java.lang.Integer.max;
//
//public class Test {
//    public static ArrayList<Integer> prevPosition = new ArrayList<>();
//    public static ArrayList<Integer> nextPosition = new ArrayList<>();
//    public static ArrayList<Integer> newPosition;
//
//    public static void main(String args[]) {
//
//        System.out.println("Test case 0");
//        Setup();
//
//        TearDown();
//
//        System.out.println("Test case 1");
//        Setup();
//
//        prevPosition.add(1);
//
//        nextPosition.add(2);
//
//        TearDown();
//
//        System.out.println("Test case 2");
//        Setup();
//
//        prevPosition.add(4);
//        prevPosition.add(1);
//
//        nextPosition.add(4);
//        nextPosition.add(3);
//
//        TearDown();
//
//        System.out.println("Test case 3");
//        Setup();
//
//        prevPosition.add(4);
//        prevPosition.add(3);
//
//        nextPosition.add(5);
////        nextPosition.add(0);
//
//        TearDown();
//
//        System.out.println("Test case 4");
//        Setup();
//
//        prevPosition.add(4);
//        prevPosition.add(2);
//
//        nextPosition.add(4);
//        nextPosition.add(3);
//        TearDown();
//
//        System.out.println("Test case 5");
//        Setup();
//
//        prevPosition.add(1);
//        prevPosition.add(1);
//
//        nextPosition.add(2);
////        nextPosition.add(0);
//
//        TearDown();
//
//        System.out.println("Test case 6");
//        Setup();
//
//        prevPosition.add(4);
//        prevPosition.add(2);
////        prevPosition.add(0);
//
//        nextPosition.add(4);
//        nextPosition.add(2);
//        nextPosition.add(1);
//
//        TearDown();
//
//        System.out.println("Test case 7");
//        Setup();
//
//        prevPosition.add(4);
//        prevPosition.add(3);
//        prevPosition.add(1);
//
//        nextPosition.add(4);
//        nextPosition.add(3);
//        nextPosition.add(2);
//
//        TearDown();
//
//        System.out.println("Test case 8");
//        Setup();
//
//        prevPosition.add(4);
//        prevPosition.add(1);
//        prevPosition.add(0);
//        prevPosition.add(1);
//
//        nextPosition.add(4);
//        nextPosition.add(1);
//        nextPosition.add(1);
////        nextPosition.add(0);
//
//        TearDown();
//
//        System.out.println("Test case 9");
//        Setup();
//
//        prevPosition.add(4);
//        prevPosition.add(4);
//        prevPosition.add(3);
//
//        nextPosition.add(4);
//        nextPosition.add(5);
////        nextPosition.add(0);
//
//        TearDown();
//
//        System.out.println("Test case 10");
//        Setup();
//
//        nextPosition.add(1);
//
//        TearDown();
//
//        System.out.println("Test case 11");
//        Setup();
//
//        prevPosition.add(1);
//
//        TearDown();
//    }
//
//    private static void TearDown() {
//        newPosition = Test.generatePosBetween(prevPosition, nextPosition);
//        System.out.println(Arrays.toString(prevPosition.toArray()));
//        System.out.println(Arrays.toString(newPosition.toArray()));
//        System.out.println(Arrays.toString(nextPosition.toArray()));
//        System.out.println("==============");
//    }
//
//    public static void Setup() {
//        prevPosition.clear();
//        nextPosition.clear();
//    }
//
//    public static ArrayList<Integer> generatePosBetween(ArrayList<Integer> prevPos, ArrayList<Integer> nextPos) {
//        ArrayList<Integer> newPos = new ArrayList<>();
//
//        int maxDepth = max(prevPos.size(), nextPos.size());
//        prevPos = Test.appendZeros(prevPos, maxDepth);
//        nextPos = Test.appendZeros(nextPos, maxDepth);
//
//        return Test.generatePosBetween(prevPos, nextPos, newPos);
//    }
//
//    private static ArrayList<Integer> appendZeros(ArrayList<Integer> list, int appendedListSize) {
//        for (int i = list.size(); i < appendedListSize; i++) {
//            list.add(0);
//        }
//        return list;
//    }
//
//    public static ArrayList<Integer> generatePosBetween(List prevPos, List nextPos, ArrayList<Integer> newPos) {
//
//        // Get current sub index number
//        int prevDigit = (prevPos.size() > 0) ? (int) prevPos.get(0) : 0;
//        int nextDigit = (nextPos.size() > 0) ? (int) nextPos.get(0) : 0;
//
//        // both reach last index tie, (basis when creating new tree depth)
//        if (prevPos.size() == 0 || nextPos.size() == 0) {
//            return newPos;
//        }
//
//        // There's empty slot available for inserting directly
//        // eg: [5, 2] is available between [5, 1] and [5, 3]
//        if (nextDigit - prevDigit > 1) {
//            newPos.add(Test.generateDigitBetween(prevDigit, nextDigit));
//            return newPos;
//
//        }
//
//        // No empty slot
//        else {
//
//            // add last previous's digit ( before increasing the tree depth)
//            // eg: adding the 3 from [4, 2, 3] to make [4, 2, 3, 1]
//            newPos.add(prevDigit);
//
//            // increase the tree depth
//            // eg: adding the 1 from [4, 2, 3] to make [4, 2, 3, 1]
//            if (prevPos.size() == 1) {
//                newPos.add(1);
//            }
//
//            if (nextDigit - prevDigit == 1) {
//                List nextPos1 = prevPos.subList(1, prevPos.size());
//                return Test.generatePosBetween(nextPos1, nextPos, newPos);
//            }
//
//            if (nextDigit - prevDigit == 0) {
//                List nextPos1 = prevPos.subList(1, prevPos.size());
//                List nextPos2 = nextPos.subList(1, nextPos.size());
//                return Test.generatePosBetween(nextPos1, nextPos2, newPos);
//            }
//
//            return null;
//        }
//    }
//
//    private static int generateDigitBetween(int id1, int id2) {
//        return (id1 + 1 <= id2) ? id1 + 1 : -1;
//    }
//}
