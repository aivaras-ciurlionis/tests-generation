package com.company.generator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class TestFileGenerator {
    private String tempFolder = "src/temp";

    public TestFileGenerator() {
    }

    static String readFile(String path, Charset encoding) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return new String(encoded, encoding);
        } catch (Exception e) {
            return "";
        }
    }

    private void createTempFolder() {
        File file = new File(tempFolder);
        if (file.exists()) {
            try {
                Files.walk(Paths.get(tempFolder))
                        .map(Path::toFile)
                        .sorted((o1, o2) -> -o1.compareTo(o2))
                        .forEach(File::delete);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        new File(tempFolder).mkdirs();
    }

    private String getHeader() {
        return "package temp;\n" +
                "import com.company.originals.Complex;\n";
    }

    private String generateMethodArguments(String prefix, int argumentCount, int dataCount) {
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < argumentCount; i++){
            StringBuilder paramItems = new StringBuilder();
            for (int j = 0; j < dataCount; j++){
                paramItems.append(prefix).append(i).append("c").append(j).append(",");
            }
            result.append("new Complex[]{").append(paramItems.substring(0, paramItems.length() - 1))
                    .append("},");
        }
        // removes last comma
        return result.substring(0, result.length() - 1);
    }

    private String getTestClass(String method, InputArguments[] arguments) {
        StringBuilder inputDeclarations = new StringBuilder();
        int i = 0;
        for (InputArguments arg : arguments) {
            inputDeclarations.append(arg.toDeclaration("p" + i + "c"));
            i++;
        }
        String generateMethodArguments =
                generateMethodArguments("p", arguments.length, arguments[0].arguments.length);
        return "public class temp {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                inputDeclarations +
                "        Complex[] results = FFT." + method + "("+generateMethodArguments+");\n" +
                "        for(Complex result: results){\n" +
                "            System.out.println(result);\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "}";
    }

    private void saveFile(String fileContents) {
        try {
            Path tempFilePath = Paths.get(tempFolder, "temp.java");
            try (PrintWriter out = new PrintWriter(tempFilePath.toString())) {
                out.println(fileContents);
            }
        } catch (Exception e) {
            System.out.println("unable to save tests");
        }
    }

    public void GenerateTestFile(String testFileName, String method, InputArguments[] arguments) {
        Path filePath = Paths.get(testFileName, "FFT.java");
        createTempFolder();
        String testFileContent = readFile(filePath.toString(), Charset.defaultCharset());
        testFileContent = testFileContent.replace("public class FFT", "class FFT");
        String tempFileContent = getHeader() + testFileContent + getTestClass(method, arguments);
        saveFile(tempFileContent);
    }

}
