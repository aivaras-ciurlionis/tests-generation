package com.company.coverage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CoverageExtractor {

    public String getOutputWithoutCoverageInfo(String output){
        int lastIndex = output.lastIndexOf('%');
        return lastIndex == -1 ? output : output.substring(lastIndex+2);
    }

    public double extractCoveragePercentage(String method, String output){
        String[] outputLines = output.split("\\r?\\n");
        List<String> coverages = new ArrayList<String>();
        String methodString = "Metodo " + method;
        for(String line: outputLines){
            if(line.contains(methodString)){
                coverages.add(line);
            }
        }

        double max = 0.0;

        for(String coverageLine: coverages){
            String[] lines = coverageLine.split("procentais");
            String amount = lines[1].substring(0, lines[1].length() - 1);
            double dobleAmount = Double.parseDouble(amount);
            if(dobleAmount > max){
                max = dobleAmount;
            }
        }
        return max > 100 ? 100 : max;
    }



}
