import org.junit.Assert;
import org.junit.Test;
import utils.ArrayUtils;

public class Mp3FileProcessor {
    @Test
    public void testCreateArrayWithLengthOfPowerOfTwoByAddingZeroes() {
        double[] arrayToExpand = new double[]
                {1.0, 2.0, 3.0, 9.0, 8.0, 16.0, 1.0, 10.0, 9.0};

        double[] actualArray = ArrayUtils.createArrayWithLengthOfPowerOfTwoByAddingZeroes(arrayToExpand);
        double[] expectedArray = new double[]
                {1.0, 2.0, 3.0, 9.0, 8.0, 16.0, 1.0, 10.0, 9.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        Assert.assertArrayEquals(expectedArray, actualArray, 0.000001);
    }

    @Test
    public void testShouldReturnTheSameArray() {
        double[] arrayToExpand = new double[]{1.0, 2.0};
        double[] actualArray = ArrayUtils.createArrayWithLengthOfPowerOfTwoByAddingZeroes(arrayToExpand);
        double[] expectedArray = new double[]{1.0, 2.0};
        Assert.assertArrayEquals(expectedArray, actualArray, 0.000001);
    }

    @Test
    public void testDescendingSorting() {
        double[] givenArray = new double[]{1.0, -1.0, 6.0, 5.0};
        double[] actualArray = ArrayUtils.sortInDescendingOrder(givenArray);
        double[] expectedArray = new double[]{6.0, 5.0, 1.0, -1.0};
        for (int i = 0; i < actualArray.length; i++) {
            Assert.assertEquals(actualArray[i],expectedArray[i],0.0001);
        }
    }

}
