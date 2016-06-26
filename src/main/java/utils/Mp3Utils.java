package utils;

import javazoom.jl.decoder.*;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.io.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static utils.FrequencyUtils.computeFrequencies;

public class Mp3Utils {

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
            decodedMp3StreamInBytes = ArrayUtils.appendToArray(decodedMp3StreamInBytes, next);
            bitStream.closeFrame();
        }
        return decodedMp3StreamInBytes;
    }
}
