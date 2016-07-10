package service.recommendation.svm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static utils.vector.operations.VectorOperations.*;

// http://www.robots.ox.ac.uk/~az/lectures/ml/lect2.pdf
public class SupportVectorMachine {
    private static final double CONST = 1.0;
    private static final int MAX_NUMBER_OF_ITERATIONS = 10;
    private List<List<Double>> trainingData = new ArrayList<>();
    private List<Double> labels = new ArrayList<>();
    private Kernels<Double, List<Double>, List<Double>> linearKernel =
            (feature, weights) -> feature
                    .stream()
                    .mapToDouble(f -> f * weights.get(feature.indexOf(f)))
                    .sum();


    private List<Double> computeGradientOfLossFunction() {
        List<Double> weights = initRandomWeights();

        for (int epoche = 0; epoche < MAX_NUMBER_OF_ITERATIONS; epoche++) {
            for (int i = 0; i < trainingData.size(); i++) {
                Double learningRate = 0.0;
                Double lambda = 0.0;
                if ((scalarProduct(weights, trainingData.get(i)) * labels.get(i)) < 0) {
                    List<Double> update = substract(multiplyByScalar(lambda, weights), getZeroOrIdentity(labels.get(i), weights));
                    weights = substract(weights, multiplyByScalar(learningRate, update));
                    Double error = squaredNorm(substract(weights, substract(weights, multiplyByScalar(learningRate, update))));
                } else {
                    List<Double> update = substract(weights, multiplyByScalar(lambda * learningRate, weights));
                    weights = substract(weights, update);
                    Double error = squaredNorm(substract(weights, substract(weights, update)));
                }
            }
        }

        return weights;
    }

    private List<Double> initRandomWeights() {
        return null;
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
