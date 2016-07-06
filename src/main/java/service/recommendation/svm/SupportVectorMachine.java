package service.recommendation.svm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// http://www.robots.ox.ac.uk/~az/lectures/ml/lect2.pdf
public class SupportVectorMachine {
    private static final double CONST = 1.0;
    private List<List<Double>> trainingData = new ArrayList<>();
    private List<Double> labels = new ArrayList<>();
    private Kernels<Double, List<Double>, List<Double>> linearKernel =
            (feature, weights) -> feature
                    .stream()
                    .mapToDouble(f -> f * weights.get(feature.indexOf(f)))
                    .sum();


    private List<Double> computeGradientOfLossFunction() {
        return new ArrayList<>();
    }

    private double computeFullAverageLossFunction(List<Double> weights) {
        double lambda = 2 / (CONST * trainingData.size());
        double squaredNormOfWeights = weights.stream().mapToDouble(w -> w * w).sum();

        return trainingData
                .parallelStream()
                .mapToDouble(
                        feature -> {
                            Double particularLabel = labels.get(trainingData.indexOf(feature));
                            return (lambda * squaredNormOfWeights) / 2 +
                                    Math.max(0, 1 - particularLabel * linearKernel.apply(feature, weights));
                        })
                .average()
                .getAsDouble();
    }

    private double updateWeightAtPosition(int position, double previouseWeightValue, double learningRate) throws IOException {
        double updatedWeight = 0;


        return updatedWeight;
    }
}
