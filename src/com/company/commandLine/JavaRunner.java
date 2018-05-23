package com.company.commandLine;

public class JavaRunner {

    public String runJavaFile(String folder, String file){
        CommandRunner cmd = new CommandRunner();
        cmd.runCommand(new String[]{"javac", folder + "/" + file + ".java"});
        String result = cmd.runCommand(new String[]{"java", folder+"."+file});
        return result;
    }

}
