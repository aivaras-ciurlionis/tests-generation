package com.company.generator;

import com.company.commandLine.JavaRunner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IfInputsGeneratorJS {

    private void saveFile(String fileContents) {
        try {
            final String tempFolder = "src/temp";
            Path tempFilePath = Paths.get(tempFolder, "ifTest.java");
            try (PrintWriter out = new PrintWriter(tempFilePath.toString())) {
                out.println(fileContents);
            }
        } catch (Exception e) {
            System.out.println("unable to save tests. " + e.getMessage());
        }
    }

    // Returns two inputs that should check both branches for given if
    int[] getIfBranchingInputs(String ifLine) {
        JavaRunner runner = new JavaRunner();

        List<Integer> possibleInputsIn = new ArrayList<Integer>();
        List<Integer> possibleInputsOut = new ArrayList<Integer>();

        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine jsEngine = mgr.getEngineByName("JavaScript");

        // Find input that branches inside IF
        for (int i = 1; i <= 10; i++) {
            boolean inside = false;
            TestFileGenerator.createTempFolder("src/temp");
            jsEngine.put("inside", inside);
            jsEngine.put("N", i);
            try {
                jsEngine.eval(
                        ifLine +
                                "inside=true;" +
                                "} else {" +
                                "inside=false" +
                                "}"
                );
                boolean value = (boolean) jsEngine.get("inside");
                if(value){
                    possibleInputsIn.add(i);
                }
            } catch (ScriptException ex) {
                ex.printStackTrace();
            }
        }

        // Find input that branches outside IF
        for (int i = 1; i <= 10; i++) {
            boolean inside = false;
            TestFileGenerator.createTempFolder("src/temp");
            jsEngine.put("inside", inside);
            jsEngine.put("N", i);
            try {
                jsEngine.eval(
                        ifLine +
                        "inside=true;" +
                                "} else {" +
                                "inside=false;" +
                                "}"
                );
                boolean value = (boolean) jsEngine.get("inside");
                if(!value){
                    possibleInputsOut.add(i);
                }
            } catch (ScriptException ex) {
                ex.printStackTrace();
            }
        }

        int inputInside = 0;
        int inputOutside = 0;
        Random rand = new Random();
        if (possibleInputsIn.size() > 0) {
            inputInside = possibleInputsIn.get(rand.nextInt(possibleInputsIn.size()));
        }
        if (possibleInputsOut.size() > 0) {
            inputOutside = possibleInputsOut.get(rand.nextInt(possibleInputsOut.size()));
        }
        IfInputsGenerator other = new IfInputsGenerator();
        return new int[]{

                inputOutside == 0 ? other.getIfBranchingInputs(ifLine)[1] : inputOutside,
                inputInside == 0 ? other.getIfBranchingInputs(ifLine)[0] : inputInside ,
        };
    }

}
