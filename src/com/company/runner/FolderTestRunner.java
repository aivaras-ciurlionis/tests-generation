package com.company.runner;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FolderTestRunner {

    private String getTestMethod(String dirName){
        switch (dirName){
            case "mutants/Complex_fft(Complex)": return "fft";
            case "mutants/Complex_ifft(Complex)": return "ifft";
            case "mutants/Complex_cconvolve(Complex,Complex)": return "cconvolve";
            case "mutants/Complex_convolve(Complex,Complex)": return "convolve";
            default: return "fft";
        }
    }

    private String[] getDirectories(String mutantsLocationPath) {
        File file = new File(mutantsLocationPath);
        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());
        return directories;
    }

    FolderRunResult runTestsInFolder(int folderNumber, String folderLocation){
//        if(!folderLocation.contains("_fft")){
//            return new FolderRunResult();
//        }
        System.out.println(folderLocation);
        String[] innerDirectories = getDirectories(folderLocation);
        String method = getTestMethod(folderLocation);
        int i = 0;
        SingleTestRunner singleRunner = new SingleTestRunner();
        SingleRunResult[] results = new SingleRunResult[innerDirectories.length];
        Arrays.sort(innerDirectories);
        for (String dirName : innerDirectories) {
            Path filePath = Paths.get(folderLocation, dirName);
            SingleRunResult result = singleRunner.runSingleTest(i, filePath.toString(), method);
            System.out.println(result);
            results[i] = result;
            i++;
        }
        FolderRunResult runResult = new FolderRunResult();
        runResult.parentFolderName = folderLocation;
        runResult.results = results;
        return runResult;
    }

}
