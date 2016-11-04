
/***
 * Frank and Sasha
 */

import java.util.ArrayList;

public class StepCounter {

	public static int countSteps(double[] times, double[][] sensorData) {
		sensorData = sensorData;
		int total = 0;

		double[] magnitudes = calculateMagnitudesFor(sensorData);
		
		for (int i = 0; i < sensorData.length; i++) {
			double[] mags = getWindow(magnitudes, i, 300);
			double SD = calculateStandardDeviation(mags);
			double mean = calculateMean(mags);
			
			
			if (isPeak(mags, i) && mags[i] > mean + SD * .01) {
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

	public static double calculateMagnitude(double x, double y, double z) {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public static double[] calculateMagnitudesFor(double[][] sensorData) {
		double[] out = new double[sensorData.length];
		for (int i = 0; i < sensorData.length; i++) {
			out[i] = calculateMagnitude(sensorData[i][0], sensorData[i][1], sensorData[i][2]);
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

	private static double[][] fixDataByRotation(double[][] data) {
		double[][] fixed = new double[data.length][3];
		for (int i = 0; i < data.length; i++) {
			double xa = data[i][0];
			double ya = data[i][1];
			double za = data[i][2];
			double xr = data[i][3];
			double yr = data[i][4];
			double zr = data[i][5];
			double X = 0;
			double Y = 0;
			double Z = 0;

			Y = ya * Math.cos(zr) + xa * Math.sin(zr) + ya * Math.cos(xr) + za * Math.sin(xr);
			X = ya * Math.sin(zr) + xa * Math.cos(zr) + ya * Math.sin(xr) + za * Math.cos(xr);

			double mag = calculateMagnitude(xa, ya, za);
			mag = mag * mag;
			mag -= Y * Y;
			mag -= X * X;
			Z = Math.sqrt(mag);

			fixed[i][0] = X;
			fixed[i][1] = Y;
			fixed[i][2] = Z;
		}
		return fixed;
	}

	private static void fixTime(double[][] d) {
		double start = d[0][0];
		for (int i = 0; i < d.length; i++) {
			d[i][0] = d[i][0] - start;
		}
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
