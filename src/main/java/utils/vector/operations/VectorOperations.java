package utils.vector.operations;

import java.util.List;
import java.util.stream.Collectors;

public class VectorOperations {

    public static List<Double> substract(List<Double> vector1, List<Double> vector2) {
        return vector1.stream().map(v1 -> v1 - vector2.get(vector1.indexOf(v1))).collect(Collectors.toList());
    }

    public static List<Double> add(List<Double> vector1, List<Double> vector2) {
        return vector1.stream().map(v1 -> v1 + vector2.get(vector1.indexOf(v1))).collect(Collectors.toList());
    }

    public static Double scalarProduct(List<Double> vector1, List<Double> vector2) {
        return vector1.stream().mapToDouble(v1 -> v1 * vector2.get(vector1.indexOf(v1))).sum();
    }

    public static Double squaredNorm(List<Double> vector) {
        return vector.stream().mapToDouble(e -> Math.pow(e, 2)).sum();
    }

    public static Double norm(List<Double> vector) {
        return Math.pow(squaredNorm(vector), 0.5);
    }

    public static List<Double> getZeroOrIdentity(double label, List<Double> vector) {
        if (0.0 == label) {
            return vector.stream().map(e -> 0.0).collect(Collectors.toList());
        } else {
            return vector;
        }
    }

    public static List<Double> multiplyByScalar(Double scalar, List<Double> vector) {
        return vector.stream().map(e -> scalar * e).collect(Collectors.toList());
    }

}
