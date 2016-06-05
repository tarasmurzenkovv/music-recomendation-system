package core.ml;

import java.util.List;
import java.util.function.Function;


public interface SupportVectorMachine {
    void setKernel(Function<Double, Double> cernel);
    List<Double> train(List<List<Double>> data, List<Integer> labels);
}
