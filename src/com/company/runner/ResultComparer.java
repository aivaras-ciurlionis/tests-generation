package com.company.runner;

public class ResultComparer {

    // Returns reason why results differ
    public String compareResults(String actual, String expected){
        String normalisedActual = actual.toLowerCase();
        String normalisedExpected = expected.toLowerCase();

        System.out.println("--------------");
        System.out.println("--------------");
        System.out.println(normalisedActual);
        System.out.println("--------------");
        System.out.println(normalisedExpected);
        System.out.println("--------------");

        if(normalisedActual.equals(normalisedExpected)){
            return "";
        }

        if(normalisedActual.contains("exception") && !normalisedExpected.contains("exception")){
            return "Exception in mutant";
        }

        if(normalisedActual.contains("infinite")){
            return  "Infinite loop in mutant";
        }

        if(normalisedActual.contains("exception") && normalisedExpected.contains("exception")){
            if(normalisedActual.contains(normalisedExpected)){
                return "";
            }
            return "Exceptions differ";
        }

        return "Results don`t match";
    }

}
