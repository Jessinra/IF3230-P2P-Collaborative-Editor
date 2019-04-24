package Backend.CRDT;

import java.io.Serializable;

public class CRDTLog implements Serializable {

    public static int DELETE = 0;
    public static int INSERT = 1;

    private CRDTChar update;
    private int operation;
    private boolean updatedLocally = false;

    public CRDTLog(CRDTChar update, int operation) {

        this.update = update;
        this.operation = operation;
    }

    public CRDTChar getUpdate() {
        return update;
    }

    public void setUpdate(CRDTChar update) {
        this.update = update;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public void setAsUpdated() {
        this.updatedLocally = true;
    }

    public boolean isUpdatedLocally() {
        return updatedLocally;
    }

}
