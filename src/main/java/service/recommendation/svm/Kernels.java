package service.recommendation.svm;


import java.util.List;

@FunctionalInterface
public interface Kernels<R, F, W> {
    R apply(F feature, W weights);
}
