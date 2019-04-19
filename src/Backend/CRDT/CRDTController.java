package Backend.CRDT;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.max;

public class CRDTController {
    private static final int minCharIdx = 0;
    private static final int maxCharIdx = 999999;

    private String clientId;
    private ArrayList<CRDTChar> textContent = new ArrayList<>();

    /* =================================================================
                                Constructor
    ================================================================= */

    public CRDTController(String clientId) {
        this.clientId = clientId;
    }

    /* =================================================================
                               Getter Setter
    ================================================================= */

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

    public void addCharToText(int index, CRDTChar c) {
        this.textContent.add(index, c);
    }

    /* =================================================================
                              Public API call
    ================================================================= */

    public CRDTChar localInsert(char value, int index) {

        CRDTChar newChar = this.generateCRDTChar(value, index);
        this.textContent.add(index, newChar);

        return newChar;
    }

    public CRDTChar localDelete(int index) {

        CRDTChar deleted = this.textContent.get(index);
        this.textContent.remove(index);

        return deleted;
    }

    public void remoteInsert(CRDTChar newChar) {
        int index = this.calculateInsertIndexOf(newChar);
        this.textContent.add(index, newChar);
    }

    public void remoteDelete(CRDTChar deletedChar) {
        this.textContent.remove(deletedChar);
    }

    public String getText() {
        StringBuilder sb = new StringBuilder();
        for (CRDTChar c : this.textContent) {
            sb.append(c.getValue());
        }
        return sb.toString();
    }

    public void resetText() {
        this.textContent.clear();
    }

    /* =================================================================
                             Private Methods
    ================================================================= */

    private CRDTChar generateCRDTChar(char value, int index) {
        ArrayList<Integer> previousCharPosition;
        ArrayList<Integer> nextCharPosition;

        try {
            previousCharPosition = this.textContent.get(index - 1).getPosition();
        } catch (Exception e) {
            previousCharPosition = new ArrayList<>();
            previousCharPosition.add(CRDTController.minCharIdx);
        }

        try {
            nextCharPosition = this.textContent.get(index).getPosition();
        } catch (Exception e) {
            nextCharPosition = new ArrayList<>();
            nextCharPosition.add(CRDTController.maxCharIdx);
        }

        ArrayList<Integer> newPosition = this.generatePositionBetween(previousCharPosition, nextCharPosition);
        return new CRDTChar(this.clientId, value, newPosition);
    }

    private ArrayList<Integer> generatePositionBetween(ArrayList<Integer> previousCharPosition,
                                                       ArrayList<Integer> nextCharPosition) {

        ArrayList<Integer> newPos = new ArrayList<>();

        int maxDepth = max(previousCharPosition.size(), nextCharPosition.size());
        previousCharPosition = this.appendZeros(previousCharPosition, maxDepth);
        nextCharPosition = this.appendZeros(nextCharPosition, maxDepth);

        return this.generatePositionBetween(previousCharPosition, nextCharPosition, newPos);
    }

    private ArrayList<Integer> generatePositionBetween(List previousCharPosition,
                                                       List nextCharPosition,
                                                       ArrayList<Integer> newPosition) {

        // Get current sub index number
        int prevDigit = (previousCharPosition.size() > 0) ? (int) previousCharPosition.get(0) : 0;
        int nextDigit = (nextCharPosition.size() > 0) ? (int) nextCharPosition.get(0) : 0;

        // both reach last index tie, (basis when creating new tree depth)
        if (previousCharPosition.size() == 0 || nextCharPosition.size() == 0) {
            return newPosition;
        }

        // There's empty slot available for inserting directly
        // eg: [5, 2] is available between [5, 1] and [5, 3]
        if (nextDigit - prevDigit > 1) {
            newPosition.add(this.generateDigitBetween(prevDigit, nextDigit));
            return newPosition;
        }

        // No empty slot
        else {

            // add last previous's digit ( before increasing the tree depth)
            // eg: adding the 3 from [4, 2, 3] to make [4, 2, 3, 1]
            newPosition.add(prevDigit);

            // increase the tree depth
            // eg: adding the 1 from [4, 2, 3] to make [4, 2, 3, 1]
            if (previousCharPosition.size() == 1) {
                newPosition.add(1);
            }

            // If the digit is exactly same -> compare next digit
            if (nextDigit - prevDigit == 0) {
                List nextCharPosition1 = previousCharPosition.subList(1, previousCharPosition.size());
                List nextCharPosition2 = nextCharPosition.subList(1, nextCharPosition.size());
                return this.generatePositionBetween(nextCharPosition1, nextCharPosition2, newPosition);
            }

            // difference is 1 -> need to expand to next sub digit.
            else {
                List nextCharPosition1 = previousCharPosition.subList(1, previousCharPosition.size());
                return this.generatePositionBetween(nextCharPosition1, nextCharPosition, newPosition);
            }
        }
    }

    private ArrayList<Integer> appendZeros(ArrayList<Integer> list, int appendedListSize) {
        for (int i = list.size(); i < appendedListSize; i++) {
            list.add(0);
        }
        return list;
    }

    private int generateDigitBetween(int a, int b) {
        return (a + 1 <= b) ? a + 1 : -1;
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

}
