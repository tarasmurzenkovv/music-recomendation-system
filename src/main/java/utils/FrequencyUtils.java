package utils;

import javazoom.jl.decoder.*;
import org.apache.commons.math3.complex.Complex;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static utils.FftUtils.performFftOnTheGivenMp3File;

public class FrequencyUtils {
    public static double[] computeFrequencies(Complex[] fourierSpectre) {
        double[] computedFrequencies = new double[fourierSpectre.length];
        for (int i = 0; i < fourierSpectre.length; i++) {
            double realPart = fourierSpectre[i].getReal();
            double imgPart = fourierSpectre[i].getImaginary();
            computedFrequencies[i] = Math.pow(realPart * realPart + imgPart * imgPart, 0.5);
        }
        return ArrayUtils.sortInDescendingOrder(computedFrequencies);
    }

    public static double[] computeFrequencies(Complex[] fourierSpectre, int topNumber) {
        double[] computedFrequencies = new double[fourierSpectre.length];
        for (int i = 0; i < fourierSpectre.length; i++) {
            double realPart = fourierSpectre[i].getReal();
            double imgPart = fourierSpectre[i].getImaginary();
            computedFrequencies[i] = Math.pow(realPart * realPart + imgPart * imgPart, 0.5);
        }
        ArrayUtils.sortInDescendingOrder(computedFrequencies);
        return ArrayUtils.getTrimmedNumberOfElements(computedFrequencies, topNumber);
    }


    public static List<double[]> extractFrequencyDomain(String pathToMp3File) throws BitstreamException, FileNotFoundException, DecoderException {
        List<double[]> computedFrequenciesPerFrame = new LinkedList<>();
        Bitstream bitStream = new Bitstream(new FileInputStream(pathToMp3File));

        Decoder decoder = new Decoder();
        Header frameHeader;
        while ((frameHeader = bitStream.readFrame()) != null) {
            SampleBuffer output = (SampleBuffer) decoder.decodeFrame(frameHeader, bitStream);

            short[] next = output.getBuffer();
            Complex[] complexDomain = performFftOnTheGivenMp3File(next);
            computedFrequenciesPerFrame.addAll(Arrays.asList(computeFrequencies(complexDomain)));
            bitStream.closeFrame();
        }
        return computedFrequenciesPerFrame;
    }
}
