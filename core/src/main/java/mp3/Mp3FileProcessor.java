package mp3;

import javazoom.jl.decoder.*;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import utils.ArrayUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static utils.ArrayUtils.appendToArray;

public class Mp3FileProcessor {

    private static final Function<Complex, Double> getFrequency = complex -> {
        double im = complex.getImaginary();
        double real = complex.getReal();
        return Math.pow(im * im + real * real, 2);
    };

    public static double[] computeFrequencies(Complex[] fourierSpectre) {
        Comparator<Double> normal = Double::compare;
        Comparator<Double> reversed = normal.reversed();
        List<Double> frequencies = Arrays.stream(fourierSpectre).parallel().map(getFrequency).collect(Collectors.toList());
        Collections.sort(frequencies, reversed);
        return frequencies.parallelStream().mapToDouble(Double::doubleValue).toArray();
    }

    public static Complex[] performFftOnTheGivenMp3File(String pathToMp3File) throws FileNotFoundException, BitstreamException, DecoderException {
        short[] decodedBytes = Mp3FileProcessor.getDecodedMp3Stream(pathToMp3File);
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
            SampleBuffer output = (SampleBuffer) decoder.decodeFrame(frameHeader, bitStream); //returns the next 2304 samples
            short[] next = output.getBuffer();
            decodedMp3StreamInBytes = appendToArray(decodedMp3StreamInBytes, next);
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
