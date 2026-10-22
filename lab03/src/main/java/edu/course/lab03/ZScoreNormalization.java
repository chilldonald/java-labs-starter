package edu.course.lab03;

public class ZScoreNormalization implements NormalizationStrategy {
    @Override
    public double[] normalize(double[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Вектор не должен быть null или пустым");
        }

        // 1. среднее
        double sum = 0;
        for (double v : values) {
            sum += v;
        }
        double mean = sum / values.length;

        // 2. дисперсия и стандартное отклонение
        double squaredDiffSum = 0;
        for (double v : values) {
            double diff = v - mean;
            squaredDiffSum += diff * diff;
        }
        double std = Math.sqrt(squaredDiffSum / values.length);

        // 3. результат в новом массиве
        double[] result = new double[values.length];
        if (std == 0) {
            return result; // все значения равны: возвращаем нули
        }
        for (int i = 0; i < values.length; i++) {
            result[i] = (values[i] - mean) / std;
        }
        return result;
    }
}