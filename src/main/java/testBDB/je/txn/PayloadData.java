package testBDB.je.txn;

import java.io.Serializable;

public class PayloadData implements Serializable {
    private int oID;
    private String threadName;
    private double doubleData;

    PayloadData(int id, String name, double data) {
        oID = id;
        threadName = name;
        doubleData = data;
    }

    public double getDoubleData() { return doubleData; }
    public int getID() { return oID; }
    public String getThreadName() { return threadName; }
}
