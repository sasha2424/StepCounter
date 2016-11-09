
/***
 * Frank and Sasha
 */

import java.util.ArrayList;

public class StepCounter {

	public static final int WINDOWSIZE = 50;
	public static final double STANDARD_DEVIATION_SCALE = .01;

	public static final int START_WINDOW_SIZE = 9;

	public static final double START_POINT_LIMIT = .4;

	public static final int MIN_ACTIVITY_TIME = 20;

	public static int countStepsOriginal(double[] times, double[][] sensorData) {
		int total = 0;

		double[] mags = calculateMagnitudesFor(sensorData);
		double SD = calculateStandardDeviation(mags);
		double mean = calculateMean(mags);

		for (int i = 0; i < mags.length; i++) {

			if (isPeak(mags, i) && mags[i] > mean + SD * STANDARD_DEVIATION_SCALE) {
				total++;
			}

		}

		return total;
	}

	public static int countSteps(double[] times, double[][] sensorData) {
		int total = 0;

		double[] magnitudes = calculateMagnitudesFor(sensorData);

		for (int i = 0; i < magnitudes.length; i++) {
			double[] mags = getWindow(magnitudes, i, WINDOWSIZE);
			double SD = calculateStandardDeviation(mags);
			double mean = calculateMean(mags);

			if (isPeak(mags, i) && mags[i] > mean + SD * STANDARD_DEVIATION_SCALE) {
				total++;
			}

		}

		return total;
	}

	public static int countStepsCon(double[] times, double[][] sensorData) {
		int total = 0;
		double[] mags = calculateMagnitudesFor(sensorData);

		for (int i = 0; i < mags.length; i++) {
			if (isPeak(mags, i) && mags[i] > 0) {
				total++;
			}
		}
		return total;
	}

	public static double[][] flipData(double[][] d) {
		double[][] out = new double[d[0].length][d.length];
		for (int i = 0; i < d.length; i++) {
			for (int j = 0; j < d[0].length; j++) {
				out[j][i] = d[i][j];
			}
		}
		return out;
	}

	private static double[] getPeaks(double[] a) {
		ArrayList<Double> out = new ArrayList<Double>();

		for (int i = 1; i < a.length - 1; i++) {
			if (isPeak(a, i)) {
				out.add(a[i]);
			}
		}
		double[] o = new double[out.size()];
		for (int i = 0; i < out.size(); i++) {
			o[i] = out.get(i);
		}
		return o;
	}

	private static boolean isPeak(double[] a, int location) {
		if (location + 1 > a.length - 1 || location - 1 < 0) {
			return false;
		} else {
			return a[location] > a[location + 1] && a[location] > a[location - 1];
		}

	}

	private static double calculateMagnitude(double x, double y, double z) {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public static double[] calculateMagnitudesFor(double[][] sensorData) {
		double[] out = new double[sensorData[0].length];
		for (int i = 0; i < sensorData[0].length; i++) {
			out[i] = calculateMagnitude(sensorData[0][i], sensorData[1][i], sensorData[2][i]);
		}
		return out;
	}

	private static double calculateStandardDeviation(double[] a, double mean) {
		double sum = 0;
		for (int i = 0; i < a.length; i++) {
			sum += (a[i] - mean) * (a[i] - mean);
		}
		sum /= (a.length - 1);
		return sum;
	}

	private static double calculateStandardDeviation(double[] a) {
		return calculateStandardDeviation(a, calculateMean(a));
	}

	private static double calculateMean(double[] a) {
		double out = 0;
		for (double d : a) {
			out += d;
		}
		out /= a.length;
		return out;
	}

	private static double[] getWindow(double[] d, int row, int radius) {
		ArrayList<Double> rows = new ArrayList<Double>();
		for (int i = row - radius; i < row + radius; i++) {
			rows.add(getColumn(d, i));

		}

		for (int i = 0; i < rows.size(); i++) {
			if (rows.get(i) == 0) {
				rows.remove(i);
			}
		}

		double[] out = new double[rows.size()];
		for (int i = 0; i < rows.size(); i++) {
			out[i] = rows.get(i);
		}
		return out;
	}

	private static double getColumn(double[] d, int row) {
		if (row < 0 || row > d.length - 1) {
			return 0;
		} else {
			return d[row];
		}
	}
}
