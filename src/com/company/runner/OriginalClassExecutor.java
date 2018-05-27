package com.company.runner;

import com.company.generator.InputArguments;
import com.company.originals.Complex;
import com.company.originals.FFT;
import com.sun.deploy.util.ArrayUtil;

import java.util.Objects;

public class OriginalClassExecutor {

    String executeOriginal(String method, InputArguments[] args){
        Complex [] result = null;
        try {
            if (Objects.equals(method, "fft")) {
                result = FFT.fft(args[0].arguments);
            }
            if (Objects.equals(method, "ifft")) {
                result = FFT.ifft(args[0].arguments);
            }
            if (Objects.equals(method, "cconvolve")) {
                result = FFT.cconvolve(args[0].arguments, args[1].arguments);
            }
            if (Objects.equals(method, "convolve")) {
                result = FFT.convolve(args[0].arguments, args[1].arguments);
            }
            StringBuilder strResult = new StringBuilder();
            assert result != null;
            for (Complex item : result) {
                strResult.append(item).append("\n");
            }
            return strResult.toString();
        }catch (Exception ex){
            return ex.toString();
        }

    }

}
