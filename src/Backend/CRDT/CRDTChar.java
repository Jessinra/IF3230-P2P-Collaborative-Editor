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

    public CRDTChar(String writerId, char value) {
        this.writerId = writerId;
        this.value = value;

        this.timeStamp = new Date().getTime();
    }

    public CRDTChar(String writerId, char value, ArrayList<Integer> position) {
        this.writerId = writerId;
        this.value = value;
        this.position = position;

        this.timeStamp = new Date().getTime();
    }

    /* =================================================================
                                  Getter Setter
    ================================================================= */

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public ArrayList<Integer> getPosition() {
        return position;
    }

    public void setPosition(ArrayList<Integer> position) {
        this.position = position;
    }

    public void addToPosition(int i) {
        this.position.add(i);
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
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
        return this.writerId.equals(o.getWriterId()) &&
                this.value == o.getValue() &&
                Arrays.equals(this.position.toArray(), o.getPosition().toArray());
    }
}

