package ml;

import mp3.dtos.Mp3FileInformationDto;

import java.util.List;
import java.util.function.Function;


public interface SupportVectorMachine {
    void setKernel(Function<Double, Double> cernel);
    List<Double> train(List<List<Double>> data, List<Integer> labels);
}
