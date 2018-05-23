package com.company.generator;

import com.company.originals.Complex;

import java.util.Random;

public class InputDataGenerator {

    private InputArguments GenerateRandomInput(int dataCount) {
        Complex[] data = new Complex[dataCount];
        InputArguments args = new InputArguments();
        for(int i = 0; i < dataCount; i++) {
            Complex number = GetRandomComplexNumber();
            data[i] = number;
        }
        args.arguments = data;
        return args;
    }

    private Complex GetRandomComplexNumber() {
        Random rand = new Random();
        return new Complex(rand.nextInt(50)-25, rand.nextInt(50)-25);
    }

    private InputArguments[] GenerateRandomData(int argumentCount) {
        Random rand = new Random();
        int dataCount = 4;
        InputArguments[] result = new InputArguments[argumentCount];
        for(int i = 0; i < argumentCount; i++) {
            InputArguments input = GenerateRandomInput(dataCount);
            result[i] = input;
        }
        return result;
    }

    public InputArguments[] GenerateInputs(int argumentCount, GeneratorType type){
        if(type != GeneratorType.RANDOM){
            System.out.println("unsupported generation method");
        }
        return GenerateRandomData(argumentCount);
    }

}
