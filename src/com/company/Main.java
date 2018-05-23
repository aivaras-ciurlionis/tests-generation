package com.company;

import com.company.runner.TestsRunner;

public class Main {

    public static void main(String[] args) {
        TestsRunner runner = new TestsRunner();
        runner.RunTests("mutants");
    }

}
