package Backend.CRDT;

import java.util.ArrayList;

public class CRDTChar {

    private String writerId;
    private char value;
    private ArrayList<Integer> position;

    public CRDTChar(String writerId, char value, ArrayList<Integer> position){
        this.writerId = writerId;
        this.value = value;
        this.position = position;
    }

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
}

