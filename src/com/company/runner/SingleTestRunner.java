package com.company.runner;

import com.company.commandLine.JavaRunner;
import com.company.generator.GeneratorType;
import com.company.generator.InputArguments;
import com.company.generator.InputDataGenerator;
import com.company.generator.TestFileGenerator;

import java.util.Objects;

public class SingleTestRunner {

    SingleRunResult runSingleTest (int testNumber, String testFolderPath, String method) {
        SingleRunResult runResult = new SingleRunResult();
        JavaRunner runner = new JavaRunner();
        TestFileGenerator fileGenerator = new TestFileGenerator();
        InputDataGenerator generator = new InputDataGenerator();
        OriginalClassExecutor executor = new OriginalClassExecutor();

        int inputCount = Objects.equals(method, "fft") || Objects.equals(method, "ifft") ? 1 : 2;
        InputArguments[] generatedInputs = generator.GenerateInputs(inputCount, GeneratorType.RANDOM);

        fileGenerator.GenerateTestFile(testFolderPath, method, generatedInputs);

        String result = runner.runJavaFile("temp", "temp");
        String expectedResult = executor.executeOriginal(method, generatedInputs);
        System.out.println(result);
        System.out.println("----------------");
        System.out.println(expectedResult);
        runResult.folderName = testFolderPath;
        runResult.number = testNumber;
        return runResult;
    }

}
