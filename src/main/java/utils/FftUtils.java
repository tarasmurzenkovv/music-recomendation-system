package utils;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.io.FileNotFoundException;

public class FftUtils {
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
}
