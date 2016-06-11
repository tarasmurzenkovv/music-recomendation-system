package core;

import core.mp3.utils.Mp3Utils;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.DecoderException;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class Mp3UtilsTest {

    @Test
    public void testMp3UtilsConcurrentDecoding() throws FileNotFoundException, BitstreamException, DecoderException {
        String pathToMp3File = getPathToResourceByResourceName("play_me_copy.mp3");
        short[] decodedMp3StreamConcurrentVersion = Mp3Utils.getDecodedMp3StreamConcurrentVersion(pathToMp3File);
    }

    private String getPathToResourceByResourceName(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(resourceName).getFile()).getAbsolutePath();
    }
}
