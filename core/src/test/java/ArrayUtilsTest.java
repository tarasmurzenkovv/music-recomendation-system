import org.junit.Assert;
import org.junit.Test;

import static utils.ArrayUtils.createArrayWithLengthOfPowerOfTwoByAddingZeroes;
import static utils.ArrayUtils.normalizeArray;
import static utils.ArrayUtils.sortInDescendingOrder;

public class ArrayUtilsTest {
    @Test
    public void testCreateArrayWithLengthOfPowerOfTwoByAddingZeroes() {
        double[] arrayToExpand = new double[]
                {1.0, 2.0, 3.0, 9.0, 8.0, 16.0, 1.0, 10.0, 9.0};

        double[] actualArray = createArrayWithLengthOfPowerOfTwoByAddingZeroes(arrayToExpand);
        double[] expectedArray = new double[]
                {1.0, 2.0, 3.0, 9.0, 8.0, 16.0, 1.0, 10.0, 9.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        Assert.assertArrayEquals(expectedArray, actualArray, 0.000001);
    }

    @Test
    public void testShouldReturnTheSameArray() {
        double[] arrayToExpand = new double[]{1.0, 2.0};
        double[] actualArray = createArrayWithLengthOfPowerOfTwoByAddingZeroes(arrayToExpand);
        double[] expectedArray = new double[]{1.0, 2.0};
        Assert.assertArrayEquals(expectedArray, actualArray, 0.000001);
    }

    @Test
    public void testDescendingSorting() {
        double[] givenArray = new double[]{1.0, -1.0, 6.0, 5.0};
        double[] actualArray = sortInDescendingOrder(givenArray);
        double[] expectedArray = new double[]{6.0, 5.0, 1.0, -1.0};
        Assert.assertArrayEquals(actualArray, expectedArray, -.0001);
    }

    @Test
    public void testNormalizeArray() {
        double[] givenArray = new double[]{1.0, -1.0, 6.0, 5.0};
        double[] actualArray = normalizeArray(givenArray);
        double[] expectedArray = new double[]{-0.30579641983136324, -0.6552780424957785, 0.5679076368296746, 0.39316682549746707};
        Assert.assertArrayEquals(actualArray, expectedArray, -.0001);
    }
}
