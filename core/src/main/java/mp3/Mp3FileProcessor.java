package mp3;

import javazoom.jl.decoder.*;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import utils.ArrayUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class Mp3FileProcessor {
    public static double[] computeFrequencies(Complex[] fourierSpectre) {
        double[] computedFrequencies = new double[fourierSpectre.length];
        for (int i = 0; i < fourierSpectre.length; i++) {
            double realPart = fourierSpectre[i].getReal();
            double imgPart = fourierSpectre[i].getImaginary();
            computedFrequencies[i] = Math.pow(realPart * realPart + imgPart * imgPart, 0.5);
        }
        return ArrayUtils.sortInDescendingOrder(computedFrequencies);
    }

    public static Complex[] performFftOnTheGivenMp3File(String pathToMp3File) throws FileNotFoundException, BitstreamException, DecoderException {
        short[] decodedBytes = Mp3FileProcessor.getDecodedMp3Stream(pathToMp3File);
        double[] decoded = ArrayUtils.transformToDoubleArrayWithLengthOfPowerOfTwo(decodedBytes);
        FastFourierTransformer fastFourierTransformer = new FastFourierTransformer(DftNormalization.STANDARD);
        Complex[] result = fastFourierTransformer.transform(decoded, TransformType.FORWARD);
        return result;
    }

    public static short[] getDecodedMp3Stream(String pathToMp3File) throws FileNotFoundException, BitstreamException, DecoderException {
        short[] decodedMp3StreamInBytes = new short[]{};
        Bitstream bitStream = new Bitstream(new FileInputStream(pathToMp3File));
        Decoder decoder = new Decoder();
        Header frameHeader;
        while ((frameHeader = bitStream.readFrame()) != null) {
            SampleBuffer output = (SampleBuffer) decoder.decodeFrame(frameHeader, bitStream); //returns the next 2304 samples
            short[] next = output.getBuffer();
            decodedMp3StreamInBytes = ArrayUtils.appendToArray(decodedMp3StreamInBytes, next);
            bitStream.closeFrame();
        }
        return decodedMp3StreamInBytes;
    }

    public static Map<Header, short[]> buildMapHeaderVsFrame(String pathToMp3File) throws FileNotFoundException, BitstreamException, DecoderException {
        Map<Header, short[]> frameHeaderVsBytes = new HashMap<>();
        Bitstream bitStream = new Bitstream(new FileInputStream(pathToMp3File));
        Decoder decoder = new Decoder();
        Header frameHeader;
        while ((frameHeader = bitStream.readFrame()) != null) {
            SampleBuffer output = (SampleBuffer) decoder.decodeFrame(frameHeader, bitStream); //returns the next 2304 samples
            short[] next = output.getBuffer();
            frameHeaderVsBytes.put(frameHeader, next);
            bitStream.closeFrame();
        }
        return frameHeaderVsBytes;
    }
}
