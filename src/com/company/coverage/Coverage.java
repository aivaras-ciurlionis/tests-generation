package com.company.coverage;

import java.io.*;

public class Coverage {

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stubSystem.out.println("System.out.print(1)");
        String fpath = "FFT.java";
        PrintStream o = new PrintStream(new File("FFT_new.java"));
        rewrite(fpath, o);
    }

    public static void rewrite(String fpath, PrintStream o) throws IOException
    {
        String first = "Complex[] fft";
        int ff = 0;
        int ifth = 0;
        String currentMethod = "";
        String second = "ifft(";
        String thirt = "cconvolve(";
        String fore = "convolve(";
        String end = "return";
        int main = 0;
        int fft = 14;
        FileInputStream fsmut = new FileInputStream(fpath);
        BufferedReader br = new BufferedReader(new InputStreamReader(fsmut));
        String strLine, nextline;
        for (strLine = br.readLine(); strLine !=null; strLine = nextline)
        {
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
                                    o.print("int as = 0;");
                                }

                                if (strLine.indexOf(first) >= 0)
                                {
                                    //System.out.println(strLine.indexOf(first));
                                    ff = 1;
                                    fft = 14;
                                    currentMethod = first;
                                }
                                else if (strLine.indexOf(second) >= 0)
                                {
                                    //System.out.println(strLine.indexOf(second));
                                    ff = 1;
                                    fft = 6;
                                    currentMethod = second;
                                }
                                else if (strLine.indexOf(thirt) >= 0)
                                {
                                    //System.out.println(strLine.indexOf(thirt));
                                    ff = 1;
                                    fft = 7;
                                    currentMethod = thirt;
                                }
                                else if (strLine.indexOf(fore) >= 0)
                                {
                                    //System.out.println(strLine.indexOf(fore));
                                    ff = 1;
                                    fft = 8;
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
                                    o.println(" as++;");
                                }

                                if (strLine.indexOf("throw new") == -1)
                                {
                                    if (strLine.charAt(strLine.length() - 1) == ';')
                                    {
                                        //o.println(" System.out.print(as++);");
                                        o.println(" as++;");
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
                            o.println(" System.out.print(\""+currentMethod+" Metodo kodo padengimas procentais \");");
                            o.print(" System.out.print(((double)as");
                            o.print("/");
                            o.print(fft);
                            o.print(")");
                            o.print("*100");
                            o.println(");");
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
