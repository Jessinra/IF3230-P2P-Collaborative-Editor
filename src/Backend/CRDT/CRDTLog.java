package Backend.CRDT;

import java.io.Serializable;

public class CRDTLog implements Serializable {

    public static int DELETE = 0;
    public static int INSERT = 1;

    private CRDTChar update;
    private int operation;

    public CRDTLog(CRDTChar update, int operation) {

        this.update = update;
        this.operation = operation;
    }

    public CRDTChar getUpdate() {
        return update;
    }

    public int getOperation() {
        return operation;
    }

}
