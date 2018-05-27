package com.company.runner;

import com.company.commandLine.JavaRunner;
import com.company.coverage.CoverageExtractor;
import com.company.generator.*;

import java.nio.file.Paths;
import java.util.Objects;

public class SingleTestRunner {
    private String testFolderPath = "";

    private SingleSpecResult runUntilDifferWithBranchingData(int maxRetries, String method){
        JavaRunner runner = new JavaRunner();
        TestFileGenerator fileGenerator = new TestFileGenerator();
        InputDataGenerator generator = new InputDataGenerator();
        OriginalClassExecutor executor = new OriginalClassExecutor();
        ResultComparer comparer = new ResultComparer();
        SingleSpecResult specResult = new SingleSpecResult();
        CoverageExtractor extractor = new CoverageExtractor();
        int repeatCount = 0;
        boolean differ = false;
        String reason = "";
        while (repeatCount <= maxRetries && !differ){
            int inputCount = Objects.equals(method, "fft") || Objects.equals(method, "ifft") ? 1 : 2;

            InputSuite suite = generator.GenerateInputsUsingFile(
                    inputCount,
                    method,
                    Paths.get(testFolderPath, "FFT.java").toString());
            double coverage = 0;

            for(SpecInput input : suite.inputs) {
                InputArguments[] generatedInputs = input.items;
                System.out.println(input.items[0].arguments.length);
                fileGenerator.GenerateTestFile(testFolderPath, method, generatedInputs);
                String result = runner.runJavaFile("temp", "temp");
                String parsedResult = extractor.getOutputWithoutCoverageInfo(result);
                coverage = extractor.extractCoveragePercentage(method, result);
                String expectedResult = executor.executeOriginal(method, generatedInputs);
                reason = comparer.compareResults(parsedResult,expectedResult);
                differ = !reason.equals("");
                if(differ) break;
            }
            repeatCount += 1;
            specResult.success = differ;
            specResult.retries = repeatCount;
            specResult.reason = reason;
            specResult.coverage = coverage;
        }
        return specResult;
    }

    private SingleSpecResult runUntilDifferWithRandomData(int maxRetries, String method){
        JavaRunner runner = new JavaRunner();
        TestFileGenerator fileGenerator = new TestFileGenerator();
        InputDataGenerator generator = new InputDataGenerator();
        OriginalClassExecutor executor = new OriginalClassExecutor();
        ResultComparer comparer = new ResultComparer();
        SingleSpecResult specResult = new SingleSpecResult();
        int repeatCount = 0;
        boolean differ = false;
        while (repeatCount <= maxRetries && !differ){
            int inputCount = Objects.equals(method, "fft") || Objects.equals(method, "ifft") ? 1 : 2;
            InputArguments[] generatedInputs = generator.GenerateInputs(inputCount);
            fileGenerator.GenerateTestFile(testFolderPath, method, generatedInputs);
            String result = runner.runJavaFile("temp", "temp");
            String expectedResult = executor.executeOriginal(method, generatedInputs);
            String reason = comparer.compareResults(result,expectedResult);
            differ = !reason.equals("");
            repeatCount += 1;
            specResult.success = differ;
            specResult.retries = repeatCount;
            specResult.reason = reason;
        }
        return specResult;
    }

    private SingleSpecResult runUntilDiffer(int maxRetries, String method) {
     //     return runUntilDifferWithRandomData(maxRetries, method);
        return runUntilDifferWithBranchingData(maxRetries, method);
    }

    public SingleRunResult runSingleTest(int testNumber, String testFolderPath, String method) {
        final int maxRetries = 4;
        SingleRunResult runResult = new SingleRunResult();
        this.testFolderPath = testFolderPath;
        SingleSpecResult result = runUntilDiffer(maxRetries, method);
        runResult.folderName = testFolderPath;
        runResult.number = testNumber;
        runResult.count = result.retries;
        runResult.success = result.success;
        runResult.coverage = result.coverage;
        runResult.reason = result.reason;
        return runResult;
    }

}
