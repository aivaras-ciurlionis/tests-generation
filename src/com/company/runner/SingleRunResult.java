package com.company.runner;

public class SingleRunResult {
    public String folderName;
    public int number;
    public int count;
    public double coverage;

    @Override
    public String toString() {
        return number + ", " + folderName + ", " + count + ", " + coverage + "%\n";
    }
}
