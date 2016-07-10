package utils.vector.operations;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class VectorOperationsTest {

    @Test
    public void substractTest() {
        List<Double> vector1 = Arrays.asList(10.0, 20.0, 30.0, 40.0, 50.0);
        List<Double> vector2 = Arrays.asList(10.0, 20.0, 30.0, 40.0, 50.0);

        List<Double> result = VectorOperations.substract(vector1, vector2);
        Assert.assertEquals(result, Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0));
    }

    @Test
    public void addTest() {
        List<Double> vector1 = Arrays.asList(10.0, 20.0, 30.0, 40.0, 50.0);
        List<Double> vector2 = Arrays.asList(10.0, 20.0, 30.0, 40.0, 50.0);

        List<Double> result = VectorOperations.add(vector1, vector2);
        Assert.assertEquals(result, Arrays.asList(20.0, 40.0, 60.0, 80.0, 100.0));
    }

    @Test
    public void scalarProduct() {
        List<Double> vector1 = Arrays.asList(10.0, 20.0, 30.0, 40.0, 50.0);
        List<Double> vector2 = Arrays.asList(10.0, 20.0, 30.0, 40.0, 50.0);

        Double result = VectorOperations.scalarProduct(vector1, vector2);
        Double actualResult = 100.0 + 400.0 + 900.0 + 1600.0 + 2500.0;

        Assert.assertEquals(result, actualResult, 0.00000001);
    }
}
