package com.company.generator;

import com.company.originals.Complex;

import java.nio.charset.Charset;
import java.util.*;

public class InputDataGenerator {

    private InputArguments GenerateRandomInput(int dataCount) {
        Complex[] data = new Complex[dataCount];
        InputArguments args = new InputArguments();
        for (int i = 0; i < dataCount; i++) {
            Complex number = GetRandomComplexNumber();
            data[i] = number;
        }
        args.arguments = data;
        return args;
    }

    private Complex GetRandomComplexNumber() {
        Random rand = new Random();
        return new Complex(rand.nextInt(50) - 25, rand.nextInt(50) - 25);
    }

    private InputArguments[] GenerateRandomData(int argumentCount) {
        Random rand = new Random();
        int dataCount = 4;
        InputArguments[] result = new InputArguments[argumentCount];
        for (int i = 0; i < argumentCount; i++) {
            InputArguments input = GenerateRandomInput(dataCount);
            result[i] = input;
        }
        return result;
    }

    public InputArguments[] GenerateInputs(int argumentCount) {
        return GenerateRandomData(argumentCount);
    }

    private String exctractMethod(String fileConent, String methodName) {
        int methodIndex = fileConent.indexOf(" " + methodName);
        int i = methodIndex;
        int balance = 0;
        boolean methodStarted = false;
        boolean extracted = false;
        while (i < fileConent.length() && !extracted) {
            char sub = fileConent.charAt(i);
            if (sub == '{') {
                if (balance == 0 && !methodStarted) {
                    methodStarted = true;
                }
                balance += 1;
            }
            if (sub == '}') {
                balance -= 1;
            }
            extracted = methodStarted && balance == 0;
            i++;
        }
        return fileConent.substring(methodIndex, i);
    }

    private List<Integer> getDataLengthsForAllBranches(String[] ifLines) {
        if(ifLines.length < 1) {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(4);
            return list;
        }
        IfInputsGeneratorJS generator = new IfInputsGeneratorJS();
        List<Integer> result = new ArrayList<>();
        for (String line : ifLines) {
            int[] inputLengths = generator.getIfBranchingInputs(line);
            result.add(inputLengths[0]);
            result.add(inputLengths[1]);
        }
        return result;
    }

    public InputSuite GenerateInputsUsingFile(
            int argumentCount,
            String method,
            String mutantFilePath) {

        if(!Objects.equals(method, "fft")) {
            InputSuite suite = new InputSuite();
            suite.inputs = new SpecInput[1];
            suite.inputs[0] = new SpecInput();
            suite.inputs[0].items = GenerateInputs(argumentCount);
            return suite;
        }

        String mutantCode = TestFileGenerator.readFile(mutantFilePath, Charset.defaultCharset());
        String methodCode = exctractMethod(mutantCode, method);
        String[] methodLines = methodCode.split("\\r?\\n");
        String[] ifLines = Arrays.stream(methodLines).filter(x -> x.contains("if")).toArray(String[]::new);
        List<Integer> dataLengths = getDataLengthsForAllBranches(ifLines);

        System.out.println();
        for(Integer d: dataLengths){
            System.out.print(d+", ");
        }
        System.out.println();

        InputSuite suite = new InputSuite();
        suite.inputs = new SpecInput[dataLengths.size()];
        int i = 0;
        for (Integer data:dataLengths) {
            suite.inputs[i] = new SpecInput();
            suite.inputs[i].items = new InputArguments[argumentCount];
            for(int j = 0; j < argumentCount; j++) {
                suite.inputs[i].items[j] = GenerateRandomInput(data);
            }
            i++;
        }
        return suite;
    }

}
