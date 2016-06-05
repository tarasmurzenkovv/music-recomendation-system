package core.ml;

import java.util.List;
import java.util.function.Function;

/**
 * Papers
 * https://cseweb.ucsd.edu/~akmenon/ResearchExam.pdf
 */
public class SupportVectorMachineImpl implements SupportVectorMachine {

    private static final double EPS = 0.00001;

    @Override
    public void setKernel(Function<Double, Double> kernel) {

    }


    @Override
    public List<Double> train(List<List<Double>> data, List<Integer> labels) {
        List<Double> estimates = initEstimates();
        while (didNotConverge(estimates, data)) {
            estimates = updateEstimates(estimates, data);
        }

        return null;
    }

    double computeEmpiricalRiskFunction(List<List<Double>> data,
                                        List<Integer> labels,
                                        List<Double> weights,
                                        double hyperParameter) {

        double weightNorm = computeNorm(weights);
        int size = data.size();

        double sum = data.parallelStream()
                .mapToDouble(feature -> {
                    int position = data.indexOf(feature);
                    return Math.max(0.0, 1 - labels.get(position) * scalarProduct(weights, data.get(position)));
                }).sum();

        return (sum + hyperParameter * weightNorm) / size;
    }

    private Double scalarProduct(List<Double> weights, List<Double> doubles) {
        double scalarProduct = 0.0;
        for (int i = 0; i < weights.size(); i++) {
            scalarProduct += weights.get(i) * doubles.get(i);
        }
        return scalarProduct;
    }

    private double computeNorm(List<Double> weights) {
        return Math.pow(this.scalarProduct(weights, weights), 0.5);
    }

    private List<Double> updateEstimates(List<Double> estimates, List<List<Double>> data) {
        return null;
    }

    // use norm of the cost function
    private boolean didNotConverge(List<Double> estimates, List<List<Double>> data) {
        return false;
    }

    private List<Double> initEstimates() {
        return null;
    }

}
