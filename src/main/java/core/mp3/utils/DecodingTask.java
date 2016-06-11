package core.mp3.utils;

import javazoom.jl.decoder.*;

import java.util.concurrent.Callable;

class DecodingTask implements Callable<short[]> {
    private Header header;
    private Bitstream bitStream;

    DecodingTask(Header header, Bitstream bitStream) {
        this.header = header;
        this.bitStream = bitStream;
    }

    @Override
    public short[] call() throws Exception {
        Decoder decoder = new Decoder();
        SampleBuffer output = (SampleBuffer) decoder.decodeFrame(this.header, this.bitStream); //returns the next 2304 samples
        return output.getBuffer();
    }
}
