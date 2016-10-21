import java.util.ArrayList;

public class StepCounter {

	private static int countSteps(double[] times, double[][] sensorData) {
		double[] mags = calculateMagnitudesFor(sensorData);
		double[] peaks = getPeaks(mags);
		double SD = calculateStandardDeviation(mags);
		double mean = calculateMean(mags);
		
		int total = 0;
		for(int i = 0; i < peaks.length;i++){
			if(peaks[i] > mean + SD)
				total++;
		}
		
		return total;
	}

	private static double[] getPeaks(double[] a) {
		ArrayList<Double> out = new ArrayList<Double>();

		for (int i = 1; i < a.length - 1; i++) {
			if (a[i] > a[i + 1] && a[i] > a[i - 1]) {
				out.add(a[i]);
			}
		}
		double[] o = new double[out.size()];
		for(int i = 0; i < out.size(); i++){
			o[i] = out.get(i);
		}
		return o;
	}

	public static double calculateMagnitude(double x, double y, double z) {
		return Math.sqrt(x * x + y * y + z * z);
	}

	private static double[] calculateMagnitudesFor(double[][] sensorData) {
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
		return out;
	}
}
