package utils;

import java.util.Arrays;

public class ArrayUtils {

    @FunctionalInterface
    private interface Normalize<Double> {
        Double apply(Double givenNumber, Double meanOfTheArray, Double varianceOfTheArray);
    }

    private static Normalize<Double> normalize = (element, mean, variance) -> (element - mean) / Math.pow(variance, 0.5);


    public static double[] normalizeArray(double[] array) {
        double mean = Arrays.stream(array).average().getAsDouble();
        double variance = Arrays.stream(array).map(a -> Math.pow(a - mean, 2)).sum();
        return Arrays.stream(array).parallel().map(element -> normalize.apply(element, mean, variance)).toArray();
    }

    public static double[] sortInDescendingOrder(double[] array) {
        int len = array.length;
        double[] reversedOrder = new double[len];
        Arrays.sort(array);
        for (int i = 0; i < len; i++) {
            reversedOrder[i] = array[len - i - 1];
        }
        return reversedOrder;
    }

    public static double[] transformToDoubleArrayWithLengthOfPowerOfTwo(short[] decodedBytes) {
        double[] transformedData = new double[decodedBytes.length];

        for (int i = 0; i < decodedBytes.length; i++) {
            transformedData[i] = (double) decodedBytes[i];
        }

        return (isPowerOfTwo(transformedData.length))
                ? transformedData
                : createArrayWithLengthOfPowerOfTwoByAddingZeroes(transformedData);

    }

    public static double[] createArrayWithLengthOfPowerOfTwoByAddingZeroes(double[] transformedData) {
        int arrayLength = transformedData.length;
        double powerOfTwo = Math.ceil(Math.log(arrayLength) / Math.log(2));
        int numberOfZeroesToAdd = (int) Math.pow(2, powerOfTwo) - arrayLength;
        double[] arrayWithZeroes = createArrayWithZeroesWithGivenLength(numberOfZeroesToAdd);

        return appendToArray(transformedData, arrayWithZeroes);
    }

    private static double[] createArrayWithZeroesWithGivenLength(int length) {
        double[] arrayWithZeros = new double[length];
        for (int i = 0; i < length; i++) {
            arrayWithZeros[i] = 0;
        }
        return arrayWithZeros;
    }

    private static boolean isPowerOfTwo(int number) {
        return ((Math.log(number)) / (Math.log(2))) == 0;
    }

    public static short[] appendToArray(short[] arrayWhereToAppend, short[] arrayWhatToAppend) {

        int aLen = arrayWhereToAppend.length;
        int bLen = arrayWhatToAppend.length;
        short[] finalArray = new short[aLen + bLen];

        System.arraycopy(arrayWhereToAppend, 0, finalArray, 0, aLen);
        System.arraycopy(arrayWhatToAppend, 0, finalArray, aLen, bLen);

        return finalArray;
    }

    private static double[] appendToArray(double[] arrayWhereToAppend, double[] arrayWhatToAppend) {

        int aLen = arrayWhereToAppend.length;
        int bLen = arrayWhatToAppend.length;
        double[] finalArray = new double[aLen + bLen];

        System.arraycopy(arrayWhereToAppend, 0, finalArray, 0, aLen);
        System.arraycopy(arrayWhatToAppend, 0, finalArray, aLen, bLen);

        return finalArray;
    }
}
