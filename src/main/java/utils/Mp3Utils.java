package utils;

import javazoom.jl.decoder.*;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.tritonus.share.sampled.TAudioFormat;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.util.*;

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

    public static Map<String, String> getId3Tags(String pathToMp3File) throws IOException, UnsupportedAudioFileException {
        Map<String, String> tagNameVsTagValue = new HashMap<>();
        File file = new File(pathToMp3File);
        AudioFileFormat baseFileFormat = null;
        AudioFormat baseFormat = null;
        baseFileFormat = AudioSystem.getAudioFileFormat(file);
        baseFormat = baseFileFormat.getFormat();
// TAudioFileFormat properties
        if (baseFileFormat instanceof TAudioFileFormat) {
            Map properties = baseFileFormat.properties();
            String key = "author";
            String val = (String) properties.get(key);
            key = "mp3.id3tag.v2";
            InputStream tag = (InputStream) properties.get(key);
        }
// TAudioFormat properties
        if (baseFormat instanceof TAudioFormat) {
            Map properties = ((TAudioFormat) baseFormat).properties();
            String key = "bitrate";
            Integer val = (Integer) properties.get(key);
        }

        return tagNameVsTagValue;
    }
}
