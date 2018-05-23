package com.company.runner;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestsRunner {

    private String[] GetDirectories(String mutantsLocationPath) {
        File file = new File(mutantsLocationPath);
        String[] directories = file.list((current, name) -> new File(current, name).isDirectory());
        return directories;
    }

    public FolderRunResult[] RunTests(String mutantsLocationPath) {
        String[] directories = GetDirectories(mutantsLocationPath);
        FolderTestRunner folderTestRunner = new FolderTestRunner();
        int i = 0;
        FolderRunResult[] results = new FolderRunResult[directories.length];
        for (String dirName : directories) {
            Path filePath = Paths.get(mutantsLocationPath, dirName);
            FolderRunResult runResult = folderTestRunner.runTestsInFolder(i, filePath.toString());
            results[i] = runResult;
            i++;
        }
        return results;
    }

}
