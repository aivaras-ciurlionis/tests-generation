package com.company.commandLine;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class CommandRunner {

    public String runCommand(String[] args){
        StringBuilder result = new StringBuilder();
        try {
            ProcessBuilder builder = new ProcessBuilder(args);
            builder.directory(new File("src"));
            builder.redirectErrorStream(true);
            Process process = builder.start();

            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            if(!process.waitFor(5, TimeUnit.SECONDS)) {
                process.destroy(); // consider using destroyForcibly instead
                return "Timeout - possible infinite loop";
            }
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
        }catch (Exception e){
            System.out.println("Unable to run command: " + e);
            return "";
        }
        return result.toString();
    }

}
