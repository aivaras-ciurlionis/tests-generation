package com.company.runner;

public class SingleRunResult {
    public String folderName;
    public int number;
    public int count;
    public double coverage;
    public boolean success;
    public String reason;

    @Override
    public String toString() {
        return number + ", " + folderName + ", " + count + ", " + coverage + "%, " + success + ", " + reason + "\n";
    }
}
