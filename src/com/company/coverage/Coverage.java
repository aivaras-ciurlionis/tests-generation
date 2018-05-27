package com.company.coverage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.stream.IntStream;



public class Coverage {

    public static void rewrite(String fpath, PrintStream o) throws IOException
    {
        String first = "Complex[] fft";
        int ff = 0;
        int ifth = 0;
        int linenum = 0;
        String second = "ifft(";
        String thirt = "cconvolve(";
        String fore = "convolve(";
        String end = "return";
        String currentMethod = "";
        int main = 0;
        int fft = 14;
        int imports = 0;
        FileInputStream fsmut = new FileInputStream(fpath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fsmut));
        String strLine, nextline;
        for (strLine = br.readLine(); strLine !=null; strLine = nextline)
        {
            if (imports == 0)
            {
                o.println();
                o.println("import java.util.stream.IntStream;");
                o.println("import java.text.DecimalFormat;");
                o.println("import java.text.NumberFormat;");
                imports++;
            }
            nextline = br.readLine();
            o.print(strLine);
            if ( strLine != null)
            {
                if (strLine.length() != 0)
                {
                    if (strLine.indexOf("main") == -1)
                    {
                        if(nextline != null && nextline.indexOf("return") == -1)
                        {
                            if (strLine.indexOf(end) == -1 && main == 0)
                            {
                                if (ff == 1)
                                {
                                    o.println();
                                    o.print("int[] arr = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};");
                                }

                                if (strLine.indexOf(first) >= 0)
                                {
                                    //System.out.println(strLine.indexOf(first));
                                    ff = 1;
                                    fft = 15;
                                    linenum = 0;
                                    currentMethod = first;
                                }
                                else if (strLine.indexOf(second) >= 0)
                                {
                                    //System.out.println(strLine.indexOf(second));
                                    ff = 1;
                                    fft = 6;
                                    linenum = 0;
                                    currentMethod = second;
                                }
                                else if (strLine.indexOf(thirt) >= 0)
                                {
                                    //System.out.println(strLine.indexOf(thirt));
                                    ff = 1;
                                    fft = 7;
                                    linenum = 0;
                                    currentMethod = thirt;
                                }
                                else if (strLine.indexOf(fore) >= 0)
                                {
                                    //System.out.println(strLine.indexOf(fore));
                                    ff = 1;
                                    fft = 8;
                                    linenum = 0;
                                    currentMethod = fore;
                                }
                                else
                                {
                                    ff = 0;
                                }

                                if (nextline.indexOf("throw new") == -1)
                                {
                                    ifth = 0;
                                }
                                else
                                {
                                    ifth = 1;
                                }

                                if (ifth == 1)
                                {
                                    //o.println(" System.out.print(as++);");
                                    o.print(" arr[");
                                    o.print(linenum);
                                    o.println("] = 1;");

                                    o.println( "int sum = IntStream.of(arr).sum();");
                                    o.print( "double xx = ((double)sum/");
                                    o.print(fft);
                                    o.println( ")*100;");
                                    o.println(" NumberFormat formatter = new DecimalFormat(\"#0.00\");");
                                    o.println(" System.out.print(\"Metodo" + currentMethod + " kodo padengimas procentais \");");
                                    o.println(" System.out.print(formatter.format(xx));");
                                    o.println(" System.out.println(\"%\");");

                                    linenum++;
                                }

                                if (strLine.indexOf("throw new") == -1)
                                {
                                    if (strLine.charAt(strLine.length() - 1) == ';')
                                    {
                                        //o.println(" System.out.print(as++);");
                                        //o.println(" as++;");
                                        o.print(" arr[");
                                        o.print(linenum);
                                        o.println("] = 1;");


                                        linenum++;
                                    }
                                    else
                                    {
                                        o.println();
                                    }
                                }
                                else
                                {
                                    o.println();
                                }

                            }
                            else
                            {
                                o.println();
                            }
                        }
                        else if(nextline != null)
                        {
                            o.println();

                            o.print(" arr[");
                            o.print(linenum);
                            o.println("] = 1;");

                            o.println( "int sum = IntStream.of(arr).sum();");
                            o.print( "double xx = ((double)sum/");
                            o.print(fft);
                            o.println( ")*100;");
                            o.println(" NumberFormat formatter = new DecimalFormat(\"#0.00\");");
                            o.println(" System.out.print(\"Metodo " + currentMethod + " kodo padengimas procentais \");");
                            o.println(" System.out.print(formatter.format(xx));");
                            o.println(" System.out.println(\"%\");");

                        }
                    }
                    else
                    {
                        main = 1;
                    }
                }
                else
                {
                    o.println();
                }
            }
        }
        br.close();

    }
}
