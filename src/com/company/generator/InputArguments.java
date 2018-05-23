package com.company.generator;

import com.company.originals.Complex;

public class InputArguments {
    public Complex[] arguments;

    public String toDeclaration(String prefix){
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (Complex arg:arguments) {
            String declaration = "Complex "+prefix+i+" = new Complex(" + arg.Re() + ", " +arg.Im() + ");\n";
            result.append(declaration);
            i++;
        }
        return result.toString();
    }
}
