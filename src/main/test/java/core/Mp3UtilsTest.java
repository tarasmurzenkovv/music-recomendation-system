package core;

import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
import org.apache.commons.math3.complex.Complex;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static utils.FftUtils.performFftOnTheGivenMp3File;
import static utils.FrequencyUtils.computeFrequencies;
import static utils.FrequencyUtils.extractFrequencyDomain;
import static utils.Mp3Utils.*;

public class Mp3UtilsTest {

    private String getPathToResourceByResourceName(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(resourceName).getFile()).getAbsolutePath();
    }

    @Test
    public void mp3Processing() throws FileNotFoundException, BitstreamException, DecoderException {
        String path = getPathToResourceByResourceName("large.mp3");
        System.out.println("Started decoding mp3. ");
        long currentTime = System.currentTimeMillis();
        short[] decodedMp3Stream = getDecodedMp3Stream(path);
        System.out.println("Finished decoding mp3. Time spent in seconds: " + (System.currentTimeMillis() - currentTime) / 1000);

        System.out.println("Started performing FFT");
        currentTime = System.currentTimeMillis();
        Complex[] complexDomain = performFftOnTheGivenMp3File(decodedMp3Stream);
        System.out.println("Finished performing FFT mp3. Time spent in seconds: " + (System.currentTimeMillis() - currentTime) / 1000);

        System.out.println("Started computing frequencies");
        currentTime = System.currentTimeMillis();
        double[] doubles = computeFrequencies(complexDomain, 100);
        System.out.println("Finished computing frequencies. Time spent in seconds: " + (System.currentTimeMillis() - currentTime) / 1000);
    }

    @Test
    public void testComputingFrequencies() throws FileNotFoundException, BitstreamException, DecoderException {
        String path = getPathToResourceByResourceName("large.mp3");
        List<double[]> outPutFrequencies = extractFrequencyDomain(path);
        outPutFrequencies.forEach(System.out::println);
    }
}
