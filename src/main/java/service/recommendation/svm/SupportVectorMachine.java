package service.recommendation.svm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// http://www.robots.ox.ac.uk/~az/lectures/ml/lect2.pdf
public class SupportVectorMachine {
    private List<List<Double>> trainingData = new ArrayList<>();
    private List<Double> labels = new ArrayList<>();
    private Kernels<Double, List<Double>, List<Double>> linearProduct =
            (feature, weights) -> feature.stream().mapToDouble(f -> weights.get(feature.indexOf(f))).sum();

    private double computeAverageLossFunction(double lambda, List<Double> weights) {

        double squaredNormOfWeights = weights.stream().mapToDouble(w -> w * w).sum();

        return trainingData
                .parallelStream()
                .mapToDouble(
                        feature -> {
                            Double particularLabel = labels.get(trainingData.indexOf(feature));
                            return (lambda * squaredNormOfWeights) / 2 +
                                    Math.max(0, 1 - particularLabel * linearProduct.apply(feature, weights));
                        })
                .average()
                .getAsDouble();
    }
}
