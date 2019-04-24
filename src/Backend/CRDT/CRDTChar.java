package Backend.CRDT;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class CRDTChar implements Serializable {

    private long timeStamp;
    private String writerId;
    private char value;
    private ArrayList<Integer> position = new ArrayList<>();

    /* =================================================================
                                   Constructor
    ================================================================= */

    CRDTChar(String writerId, char value) {
        this.writerId = writerId;
        this.value = value;

        this.timeStamp = new Date().getTime();
    }

    CRDTChar(String writerId, char value, ArrayList<Integer> position) {
        this.writerId = writerId;
        this.value = value;
        this.position = position;

        this.timeStamp = new Date().getTime();
    }

    /* =================================================================
                                  Getter Setter
    ================================================================= */

    char getValue() {
        return value;
    }

    ArrayList<Integer> getPosition() {
        return position;
    }

    void addToPosition(int i) {
        this.position.add(i);
    }

    long getTimeStamp() {
        return timeStamp;
    }

    /* =================================================================
                             Utility Methods
    ================================================================= */

    public String toString() {
        return this.writerId + " : " + this.value + " : " + Arrays.toString(position.toArray());
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CRDTChar)) {
            return false;
        }

        CRDTChar o = (CRDTChar) obj;
        return this.writerId.equals(o.writerId) &&
                this.value == o.value &&
                Arrays.equals(this.position.toArray(), o.position.toArray());
    }

    boolean deepEquals(CRDTChar obj) {
        return equals(obj) && this.timeStamp == obj.timeStamp;
    }
}

