//package Backend.P2PServer;
//
//import Backend.CRDT.CRDTChar;
//import Backend.CRDT.CRDTLog;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Simulates a P2PServer network.
// */
//public class Main {
//    /**
//     * Keeps a list of assigned port numbers to avoid port number conflicts during simulation.
//     */
//    private static final List<Integer> ASSIGNED_PORTS = new ArrayList<>();
//
//    public static void main(String[] args) throws InterruptedException {
//        //Creates a number of nodes to be used in the P2PServer network.
//        Node node1 = new Node("node1", "127.0.0.1", selectPortNumber(), editorCallback);
//        Node node2 = new Node("node2", "127.0.0.1", selectPortNumber(), editorCallback);
//        Node node3 = new Node("node3", "127.0.0.1", selectPortNumber(), editorCallback);
//
//        //start nodes
//        new Thread(node1::start).start();
//        new Thread(node2::start).start();
//        new Thread(node3::start).start();
//
//        //wait for network to be ready
//        Thread.sleep(1000);
//
//        CRDTChar crdtChar = new CRDTChar(node1.getNodeId(), 'a');
//        CRDTLog crdtLog = new CRDTLog(crdtChar, 0);
//
//        //send messages
//        node1.sendMessage(new Message(node1.getNodeId(), crdtLog),
//                "127.0.0.1", node2.getInBoundPort());
//        /*
//        node2.sendMessage(new Message(node2.getNodeId(), node1.getNodeId(), "Hello my peer - from node2"),
//                "127.0.0.1", node1.getInBoundPort());
//        node1.sendMessage(new Message(node1.getNodeId(), node3.getNodeId(), "Hello my peer - from node1"),
//                "127.0.0.1", node3.getInBoundPort());
//        node3.sendMessage(new Message(node3.getNodeId(), node2.getNodeId(), "Hello my peer - from node3"),
//                "127.0.0.1", node2.getInBoundPort());
//        */
//        // infinite loop to keep the network running. Can be altered as desired.
//        while (true) ;
//
//    }
//
//    /**
//     * Gets a port number within the range 49152 to 65532
//     *
//     * @return Port number
//     */
//    public static int selectPortNumber() {
//        int portNumber; //range is from 49152 -> 65532
//        do {
//            portNumber = 49152 + (int) (Math.random() * 16381);
//        } while (ASSIGNED_PORTS.contains(portNumber));
//        ASSIGNED_PORTS.add(portNumber);
//        return portNumber;
//    }
//}
//
//package Backend.P2PServer;
//
//import Backend.CRDT.CRDTChar;
//import Backend.CRDT.CRDTLog;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Simulates a P2PServer network.
// */
//public class Main {
//    /**
//     * Keeps a list of assigned port numbers to avoid port number conflicts during simulation.
//     */
//    private static final List<Integer> ASSIGNED_PORTS = new ArrayList<>();
//
//    public static void main(String[] args) throws InterruptedException {
//        //Creates a number of nodes to be used in the P2PServer network.
//        Node node1 = new Node("127.0.0.1", selectPortNumber(), "node1");
//        Node node2 = new Node("127.0.0.1", selectPortNumber(), "node2");
//        Node node3 = new Node("127.0.0.1", selectPortNumber(), "node3");
//
//        //start nodes
//        new Thread(node1::start).start();
//        new Thread(node2::start).start();
//        new Thread(node3::start).start();
//
//        //wait for network to be ready
//        Thread.sleep(1000);
//
//        CRDTChar crdtChar = new CRDTChar(node1.getNodeId(), 'a');
//        CRDTLog crdtLog = new CRDTLog(crdtChar, 0);
//        // test join
//        System.out.println("Node 2 is joining from Node 1!");
//        node2.sendJoinRequest(node1.getIpAddress(), node1.getInBoundPort());
//        Thread.sleep(1000);
//        System.out.println("Node 3 is joining from Node 1!");
//        node3.sendJoinRequest(node1.getIpAddress(), node1.getInBoundPort());
//        Thread.sleep(1000);
//        //send messages
//        /*
//        node1.sendMessage(new Message(node1.getNodeId(), crdtLog), "127.0.0.1", node2.getInBoundPort());
//        Thread.sleep(1000);
//        node2.sendMessage(new Message(node2.getNodeId(), crdtLog), "127.0.0.1", node1.getInBoundPort());
//        */
//        while (true) ;
//
//    }
//
//    /**
//     * Gets a port number within the range 49152 to 65532
//     *
//     * @return Port number
//     */
//    public static int selectPortNumber() {
//        int portNumber; //range is from 49152 -> 65532
//        do {
//            portNumber = 49152 + (int) (Math.random() * 16381);
//        } while (ASSIGNED_PORTS.contains(portNumber));
//        ASSIGNED_PORTS.add(portNumber);
//        return portNumber;
//    }
//}
//
