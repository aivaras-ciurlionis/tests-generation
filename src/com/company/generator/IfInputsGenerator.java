package com.company.generator;

import com.company.commandLine.JavaRunner;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IfInputsGenerator {

    private String generateFileWithIf(String ifLine, int N){
        return "package temp;\n" +
                "class ifTest {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                " int N = "+N+";\n" +
                ifLine + "\n" +
                "System.out.println(\"Inside\");\n" +
                "return; \n" +
                "        }\n" +
                "System.out.println(\"Outside\");\n" +
                "return; \n" +
                "    }\n" +
                "\n" +
                "}";
    }

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
    int[] getIfBranchingInputs(String ifLine){
        JavaRunner runner = new JavaRunner();

        List<Integer> possibleInputsIn = new ArrayList<Integer>();
        List<Integer> possibleInputsOut = new ArrayList<Integer>();

        // Find input that branches inside IF
        for(int i = 1; i <= 8; i++){
            TestFileGenerator.createTempFolder("src/temp");
            String testContent = generateFileWithIf(ifLine, i);
            saveFile(testContent);
            String result = runner.runJavaFile("temp", "ifTest");
            if(result.contains("Inside")){
                possibleInputsIn.add(i);
            }
        }

        // Find input that branches outside IF
        for(int i = 1; i <= 8; i++){
            TestFileGenerator.createTempFolder("src/temp");
            String testContent = generateFileWithIf(ifLine, i);
            saveFile(testContent);
            String result = runner.runJavaFile("temp", "ifTest");
            if(result.contains("Outside")){
                possibleInputsOut.add(i);
            }
        }
        int inputInside = 4;
        int inputOutside = 4;
        Random rand = new Random();
        if(possibleInputsIn.size() > 0){
            inputInside = possibleInputsIn.get(rand.nextInt(possibleInputsIn.size()));
        }
        if(possibleInputsOut.size() > 0){
            inputOutside = possibleInputsOut.get(rand.nextInt(possibleInputsOut.size()));
        }
        return new int[]{inputOutside, inputInside};
    }

}
