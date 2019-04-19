//package Backend.CRDT;
//
//import java.util.ArrayList;
//
//import static java.lang.Integer.max;
//
//public class Test2 {
//    private static ArrayList<CRDTChar> textContent = new ArrayList<>();
//
//    public static void main(String args[]) {
//
//        testInsertMid();
//        testInsertStart();
//        testInsertChecker();
//    }
//
//    private static void testInsertMid() {
//        setup();
//
//        CRDTChar c1 = new CRDTChar("abc", 'm');
//        CRDTChar c2 = new CRDTChar("abc", 'e');
//        CRDTChar c3 = new CRDTChar("abc", 'g');
//
//        c1.addToPosition(1);
//        c1.addToPosition(1);
//
//        c2.addToPosition(1);
//        c2.addToPosition(2);
//
//        c3.addToPosition(1);
//        c3.addToPosition(3);
//
//        insertChar(c1);
//        insertChar(c2);
//        insertChar(c3);
//
//        teardown();
//    }
//
//    private static void testInsertStart() {
//        setup();
//
//        CRDTChar c1 = new CRDTChar("abc", 'm');
//        CRDTChar c2 = new CRDTChar("abc", 'e');
//        CRDTChar c3 = new CRDTChar("abc", 'g');
//
//        c1.addToPosition(0);
//        c1.addToPosition(1);
//
//        c2.addToPosition(0);
//        c2.addToPosition(2);
//
//        c3.addToPosition(0);
//        c3.addToPosition(3);
//
//        insertChar(c1);
//        insertChar(c2);
//        insertChar(c3);
//
//        teardown();
//    }
//
//    private static void testInsertChecker() {
//        setup();
//
//        CRDTChar c1 = new CRDTChar("abc", 'm');
//        CRDTChar c2 = new CRDTChar("abc", 'e');
//        CRDTChar c3 = new CRDTChar("abc", 'g');
//
//        c1.addToPosition(3);
//        c1.addToPosition(1);
//
//        c2.addToPosition(4);
//        c2.addToPosition(2);
//
//        c3.addToPosition(5);
//        c3.addToPosition(3);
//
//        insertChar(c1);
//        insertChar(c2);
//        insertChar(c3);
//
//        teardown();
//    }
//
//    private static void setup(){
//        CRDTChar c1 = new CRDTChar("abc", 'M');
//        CRDTChar c2 = new CRDTChar("abc", 'E');
//        CRDTChar c3 = new CRDTChar("abc", 'G');
//        CRDTChar c4 = new CRDTChar("abc", 'U');
//        CRDTChar c5 = new CRDTChar("abc", 'M');
//        CRDTChar c6 = new CRDTChar("abc", 'I');
//
//        c1.addToPosition(1);
//        c2.addToPosition(2);
//        c3.addToPosition(3);
//        c4.addToPosition(4);
//        c5.addToPosition(5);
//        c6.addToPosition(6);
//
//        insertChar(c1);
//        insertChar(c2);
//        insertChar(c3);
//        insertChar(c4);
//        insertChar(c5);
//        insertChar(c6);
//    }
//
//    private static void teardown(){
//        textContent.clear();
//    }
//
//    private static void insertChar(CRDTChar c1) {
//        int charPosition;
//
//        charPosition = Test2.calculateInsertIndexOf(c1);
//        Test2.textContent.add(charPosition, c1);
//        System.out.println(charPosition);
//        System.out.println(getText());
//    }
//
//    private static int calculateInsertIndexOf(CRDTChar newChar) {
//        /*
//            Find absolute text index based on char position (relative index)
//        */
//
//        ArrayList<Integer> newCharPosition = newChar.getPosition();
//        ArrayList<Integer> currCharPosition;
//
//        // Iterate for each CRDTchar in textContent
//        for (int i = 0; i < Test2.textContent.size(); i++) {
//
//            currCharPosition = Test2.textContent.get(i).getPosition();
//            int positionLength = max(currCharPosition.size(), newCharPosition.size());
//
//            // Iterate for each sub-index of char position
//            for (int j = 0; j < positionLength; j++) {
//
//                int newCharNum = (j < newCharPosition.size()) ? newCharPosition.get(j) : 0;
//                int currCharNum = (j < currCharPosition.size()) ? currCharPosition.get(j) : 0;
//
//                // move to next char, current relative position is still bigger
//                if (newCharNum > currCharNum) {
//                    break;
//                }
//
//                // found the right position
//                else if (newCharNum < currCharNum) {
//                    return i;
//                }
//            }
//        }
//
//        // Insert as last
//        return Test2.textContent.size();
//    }
//
//    public static String getText() {
//        StringBuilder sb = new StringBuilder();
//        for (CRDTChar c : Test2.textContent) {
//            sb.append(c.getValue());
//        }
//        return sb.toString();
//    }
//}
