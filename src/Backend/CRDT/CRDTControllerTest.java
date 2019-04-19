package Backend.CRDT;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CRDTControllerTest {

    private CRDTController controller = new CRDTController("testingClient");
    private ArrayList<Integer> expectedRelativeIdx = new ArrayList<>();

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        controller.localInsert('M', 0);
        controller.localInsert('E', 1);
        controller.localInsert('G', 2);
        controller.localInsert('U', 3);
        controller.localInsert('M', 4);
        controller.localInsert('I', 5);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        controller.resetText();
        expectedRelativeIdx.clear();
    }

    @org.junit.jupiter.api.Test
    void testLocalInsert() {
        // done by setup method
        assertEquals("MEGUMI", controller.getText());
    }

    @org.junit.jupiter.api.Test
    void testLocalInsertFront() {
        controller.localInsert('a', 0);
        assertEquals("aMEGUMI", controller.getText());


        // a should have [0, 1]
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(1);
        assertEquals(expectedRelativeIdx, controller.getTextContent().get(0).getPosition());
    }

    @org.junit.jupiter.api.Test
    void testLocalInsertMultipleFront() {

        controller.localInsert('k', 0);
        controller.localInsert('a', 1);
        controller.localInsert('t', 2);
        controller.localInsert('o', 3);

        // k should have [0, 1]
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(1);
        assertEquals(expectedRelativeIdx, controller.getTextContent().get(0).getPosition());
        expectedRelativeIdx.clear();

        // a should have [0, 1, 1]
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(1);
        expectedRelativeIdx.add(1);
        assertEquals(expectedRelativeIdx, controller.getTextContent().get(1).getPosition());
        expectedRelativeIdx.clear();

        // t should have [0, 1, 1, 1]
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(1);
        expectedRelativeIdx.add(1);
        expectedRelativeIdx.add(1);
        assertEquals(expectedRelativeIdx, controller.getTextContent().get(2).getPosition());
        expectedRelativeIdx.clear();


        // o should have [0, 1, 1, 1, 1]
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(1);
        expectedRelativeIdx.add(1);
        expectedRelativeIdx.add(1);
        expectedRelativeIdx.add(1);
        assertEquals(expectedRelativeIdx, controller.getTextContent().get(3).getPosition());

        assertEquals("katoMEGUMI", controller.getText());
    }

    @org.junit.jupiter.api.Test
    void testLocalInsertMultipleFrontReverse() {
        controller.localInsert('o', 0);
        controller.localInsert('t', 0);
        controller.localInsert('a', 0);
        controller.localInsert('k', 0);

        assertEquals("katoMEGUMI", controller.getText());

        // o should have [0, 1]
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(1);
        assertEquals(expectedRelativeIdx, controller.getTextContent().get(3).getPosition());
        expectedRelativeIdx.clear();

        // t should have [0,0, 1]
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(1);
        assertEquals(expectedRelativeIdx, controller.getTextContent().get(2).getPosition());
        expectedRelativeIdx.clear();

        // a should have [0,0,0, 1]
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(1);
        assertEquals(expectedRelativeIdx, controller.getTextContent().get(1).getPosition());
        expectedRelativeIdx.clear();

        // k should have [0,0,0,0, 1]
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(0);
        expectedRelativeIdx.add(1);
        assertEquals(expectedRelativeIdx, controller.getTextContent().get(0).getPosition());
    }

    @org.junit.jupiter.api.Test
    void testLocalInsertMid() {
        controller.localInsert('a', 3);
        assertEquals("MEGaUMI", controller.getText());

        expectedRelativeIdx.add(3);
        expectedRelativeIdx.add(1);
        assertEquals(expectedRelativeIdx, controller.getTextContent().get(3).getPosition());
    }

    @org.junit.jupiter.api.Test
    void testLocalInsertEnd() {
        controller.localInsert('a', 6);
        assertEquals("MEGUMIa", controller.getText());

        expectedRelativeIdx.add(7);
        assertEquals(expectedRelativeIdx, controller.getTextContent().get(6).getPosition());
    }

    @org.junit.jupiter.api.Test
    void testLocalInsertMultipleMid() {
        controller.localInsert('a', 4);
        controller.localInsert('b', 5);
        controller.localInsert('c', 6);

        assertEquals("MEGUabcMI", controller.getText());

        // a should have [4,1]
        expectedRelativeIdx.add(4);
        expectedRelativeIdx.add(1);
        assertEquals(expectedRelativeIdx, controller.getTextContent().get(4).getPosition());
        expectedRelativeIdx.clear();

        // b should have [4, 2]
        expectedRelativeIdx.add(4);
        expectedRelativeIdx.add(2);
        assertEquals(expectedRelativeIdx, controller.getTextContent().get(5).getPosition());
        expectedRelativeIdx.clear();

        // c should have [4, 3]
        expectedRelativeIdx.add(4);
        expectedRelativeIdx.add(3);
        assertEquals(expectedRelativeIdx, controller.getTextContent().get(6).getPosition());
    }

    @org.junit.jupiter.api.Test
    void testLocalDelete() {
        controller.localDelete(2);
        assertEquals("MEUMI", controller.getText());
    }

    @org.junit.jupiter.api.Test
    void testLocalDeleteFront() {
        controller.localDelete(0);
        assertEquals("EGUMI", controller.getText());
    }

    @org.junit.jupiter.api.Test
    void testLocalDeleteEnd() {
        controller.localDelete(5);
        assertEquals("MEGUM", controller.getText());
    }

    @org.junit.jupiter.api.Test
    void testRemoteInsertFront() {

        CRDTChar r1 = new CRDTChar("remote", 'K');
        CRDTChar r2 = new CRDTChar("remote", 'A');
        CRDTChar r3 = new CRDTChar("remote", 'T');
        CRDTChar r4 = new CRDTChar("remote", 'O');

        r1.addToPosition(0);
        r1.addToPosition(1);

        r2.addToPosition(0);
        r2.addToPosition(1);
        r2.addToPosition(1);

        r3.addToPosition(0);
        r3.addToPosition(1);
        r3.addToPosition(1);
        r3.addToPosition(1);

        r4.addToPosition(0);
        r4.addToPosition(1);
        r4.addToPosition(1);
        r4.addToPosition(1);
        r4.addToPosition(1);

        controller.remoteInsert(r1);
        controller.remoteInsert(r2);
        controller.remoteInsert(r3);
        controller.remoteInsert(r4);

        assertEquals("KATOMEGUMI", controller.getText());
    }

    @org.junit.jupiter.api.Test
    void testRemoteInsertFrontReverse() {

        CRDTChar r1 = new CRDTChar("remote", 'K');
        CRDTChar r2 = new CRDTChar("remote", 'A');
        CRDTChar r3 = new CRDTChar("remote", 'T');
        CRDTChar r4 = new CRDTChar("remote", 'O');

        r1.addToPosition(0);
        r1.addToPosition(0);
        r1.addToPosition(0);
        r1.addToPosition(0);
        r1.addToPosition(1);

        r2.addToPosition(0);
        r2.addToPosition(0);
        r2.addToPosition(0);
        r2.addToPosition(1);

        r3.addToPosition(0);
        r3.addToPosition(0);
        r3.addToPosition(1);

        r4.addToPosition(0);
        r4.addToPosition(1);

        controller.remoteInsert(r4);
        controller.remoteInsert(r3);
        controller.remoteInsert(r2);
        controller.remoteInsert(r1);

        assertEquals("KATOMEGUMI", controller.getText());
    }

    @org.junit.jupiter.api.Test
    void testRemoteInsertEnd() {

        CRDTChar r1 = new CRDTChar("remote", 'K');
        CRDTChar r2 = new CRDTChar("remote", 'A');
        CRDTChar r3 = new CRDTChar("remote", 'T');
        CRDTChar r4 = new CRDTChar("remote", 'O');

        r1.addToPosition(7);
        r2.addToPosition(8);
        r3.addToPosition(9);
        r4.addToPosition(10);

        controller.remoteInsert(r1);
        controller.remoteInsert(r2);
        controller.remoteInsert(r3);
        controller.remoteInsert(r4);

        assertEquals("MEGUMIKATO", controller.getText());
    }

    @org.junit.jupiter.api.Test
    void testRemoteDeleteFront() {
        CRDTChar r1 = new CRDTChar("testingClient", 'M');
        CRDTChar r2 = new CRDTChar("testingClient", 'E');
        CRDTChar r3 = new CRDTChar("testingClient", 'G');

        r1.addToPosition(1);
        r2.addToPosition(2);
        r3.addToPosition(3);

        controller.remoteDelete(r1);
        controller.remoteDelete(r2);
        controller.remoteDelete(r3);

        assertEquals("UMI", controller.getText());
    }

    @org.junit.jupiter.api.Test
    void testRemoteDeleteMid() {
        assertEquals("MEGUMI", controller.getText());

        CRDTChar r3 = new CRDTChar("testingClient", 'G');
        r3.addToPosition(3);
        controller.remoteDelete(r3);

        assertEquals("MEUMI", controller.getText());
    }

    @org.junit.jupiter.api.Test
    void testRemoteDeleteEnd() {
        CRDTChar r1 = new CRDTChar("testingClient", 'U');
        CRDTChar r2 = new CRDTChar("testingClient", 'M');
        CRDTChar r3 = new CRDTChar("testingClient", 'I');

        r1.addToPosition(4);
        r2.addToPosition(5);
        r3.addToPosition(6);

        controller.remoteDelete(r1);
        controller.remoteDelete(r2);
        controller.remoteDelete(r3);

        assertEquals("MEG", controller.getText());
    }

    @org.junit.jupiter.api.Test
    void testRemoteDeleteNonExist() {
        CRDTChar r1 = new CRDTChar("testingClient", 'H');
        r1.addToPosition(8);
        controller.remoteDelete(r1);
        assertEquals("MEGUMI", controller.getText());
    }
}