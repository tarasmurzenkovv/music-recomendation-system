package core.mp3.mp3utils;

import javazoom.jl.decoder.*;
import core.mp3.dtos.Mp3FileInformationDto;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import core.utils.ArrayUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static core.utils.ArrayUtils.*;

/**
 * Resources
 * https://github.com/wsieroci/audiorecognizer/blob/master/src/view/AudioRecognizerWindow.java
 */

public class Mp3Utils {

    public static double[] computeFrequencies(Complex[] fourierSpectre) {
        double[] computedFrequencies = new double[fourierSpectre.length];
        for (int i = 0; i < fourierSpectre.length; i++) {
            double realPart = fourierSpectre[i].getReal();
            double imgPart = fourierSpectre[i].getImaginary();
            computedFrequencies[i] = Math.pow(realPart * realPart + imgPart * imgPart, 0.5);
        }
        return sortInDescendingOrder(computedFrequencies);
    }

    public static double[] computeFrequencies(Complex[] fourierSpectre, int topNumber) {
        double[] computedFrequencies = new double[fourierSpectre.length];
        for (int i = 0; i < fourierSpectre.length; i++) {
            double realPart = fourierSpectre[i].getReal();
            double imgPart = fourierSpectre[i].getImaginary();
            computedFrequencies[i] = Math.pow(realPart * realPart + imgPart * imgPart, 0.5);
        }
        sortInDescendingOrder(computedFrequencies);
        return getTrimmedNumberOfElements(computedFrequencies, topNumber);
    }

    public static Complex[] performFftOnTheGivenMp3File(short[] decodedMp3) {
        double[] decoded = ArrayUtils.transformToDoubleArrayWithLengthOfPowerOfTwo(decodedMp3);
        FastFourierTransformer fastFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
        return fastFourierTransformer.transform(decoded, TransformType.FORWARD);
    }

    public static Complex[] performFftOnTheGivenMp3File(String pathToMp3File) throws FileNotFoundException, BitstreamException, DecoderException {
        short[] decodedBytes = Mp3Utils.getDecodedMp3Stream(pathToMp3File);
        double[] decoded = ArrayUtils.transformToDoubleArrayWithLengthOfPowerOfTwo(decodedBytes);
        FastFourierTransformer fastFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
        return fastFourierTransformer.transform(decoded, TransformType.FORWARD);
    }

    public static short[] getDecodedMp3Stream(String pathToMp3File) throws FileNotFoundException, BitstreamException, DecoderException {
        short[] decodedMp3StreamInBytes = new short[]{};
        Bitstream bitStream = new Bitstream(new FileInputStream(pathToMp3File));

        Decoder decoder = new Decoder();
        Header frameHeader;
        while ((frameHeader = bitStream.readFrame()) != null) {
            SampleBuffer output = (SampleBuffer) decoder.decodeFrame(frameHeader, bitStream);
            int outputFrequency = decoder.getOutputFrequency();
            //returns the next 2304 samples
            short[] next = output.getBuffer();
            decodedMp3StreamInBytes = appendToArray(decodedMp3StreamInBytes, next);
            bitStream.closeFrame();
        }
        return decodedMp3StreamInBytes;
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
