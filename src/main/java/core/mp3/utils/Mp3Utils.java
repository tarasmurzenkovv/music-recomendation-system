package core.mp3.utils;

import javazoom.jl.decoder.*;
import core.mp3.dtos.Mp3FileInformationDto;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import core.utils.ArrayUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static core.utils.ArrayUtils.getTrimmedNumberOfElements;
import static core.utils.ArrayUtils.sortInDescendingOrder;

public class Mp3Utils {

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

    public static String collectInformationAboutTrack(String pathToMp3File) {
        Mp3FileInformationDto informationDto = new Mp3FileInformationDto();
        informationDto.setTrackName(pathToMp3File);
        return informationDto.toString();
    }
}
