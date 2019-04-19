package Backend.CRDT;

import java.util.ArrayList;

import static java.lang.Integer.max;

public class CRDTController {

    private String clientId;
    private ArrayList<CRDTChar> textContent = new ArrayList<>();

    public CRDTController(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ArrayList<CRDTChar> getTextContent() {
        return textContent;
    }

    public void setTextContent(ArrayList<CRDTChar> textContent) {
        this.textContent = textContent;
    }

    public String getText() {
        StringBuilder sb = new StringBuilder();
        for (CRDTChar c : this.textContent) {
            sb.append(c.getValue());
        }
        return sb.toString();
    }

    public CRDTChar localInsert(char value, int index) {

        CRDTChar newChar = this.generateCRDTChar(value, index);
        this.textContent.add(index, newChar);

        return newChar;
    }

    private CRDTChar generateCRDTChar(char value, int index) {
        ArrayList<Integer> previousCharPosition;
        ArrayList<Integer> nextCharPosition;

        try {
            previousCharPosition = this.textContent.get(index-1).getPosition();
        } catch (Exception e) {
            previousCharPosition = new ArrayList<>();
        }

        try {
            nextCharPosition = this.textContent.get(index).getPosition();
        } catch (Exception e) {
            nextCharPosition = new ArrayList<>();
        }

        ArrayList<Integer> newPosition = this.generatePositionBetween(previousCharPosition, nextCharPosition);

        return new CRDTChar(this.clientId, value, newPosition);
    }

    private ArrayList<Integer> generatePositionBetween(ArrayList<Integer> previousCharPosition,
                                                       ArrayList<Integer> nextCharPosition){
        ArrayList<Integer> newPos = new ArrayList<>();
        return generatePositionBetween(previousCharPosition, nextCharPosition, newPos);
    }

    private ArrayList<Integer> generatePositionBetween(ArrayList<Integer> previousCharPosition,
                                                       ArrayList<Integer> nextCharPosition,
                                                       ArrayList<Integer> newPosition){
        // TODO : fix this
        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(1);
        return result;
    }

    public CRDTChar localDelete(int index) {

        CRDTChar deleted = this.textContent.get(index);
        this.textContent.remove(index);

        return deleted;
    }

    public void remoteInsert(CRDTChar newChar) {
        int index = this.calculateInsertIndexOf(newChar);
        this.textContent.add(index + 1, newChar);
    }

    private int calculateInsertIndexOf(CRDTChar newChar) {
        /*
            Find absolute text index based on char position (relative index)
        */

        ArrayList<Integer> newCharPosition = newChar.getPosition();
        ArrayList<Integer> currCharPosition;

        // Iterate for each CRDTchar in textContent
        for (int i = 0; i < this.textContent.size(); i++) {

            currCharPosition = this.textContent.get(i).getPosition();
            int positionLength = max(currCharPosition.size(), newCharPosition.size());

            // Iterate for each sub-index of char position
            for (int j = 0; j < positionLength; j++) {

                int newCharNum = (j < newCharPosition.size()) ? newCharPosition.get(j) : 0;
                int currCharNum = (j < currCharPosition.size()) ? currCharPosition.get(j) : 0;

                // move to next char, current relative position is still bigger
                if (newCharNum > currCharNum) {
                    break;
                }

                // found the right position
                else if (newCharNum < currCharNum) {
                    return i;
                }
            }
        }

        // Insert as last
        return this.textContent.size();
    }

    public void remoteDelete(CRDTChar deletedChar) {
        this.textContent.remove(deletedChar);
    }
}
