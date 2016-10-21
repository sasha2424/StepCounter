import java.util.Arrays;
import java.util.Random;
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

public class BasicPlotting {
	public static void main(String[] args) {
		String names = "xa,ya,za,xr,yr,zr";
		String[] columnNames = names.split(",");

		CSVData data = CSVData.readCSVFile("C://Users//sasha//Desktop//HIMU.txt", columnNames, 2, "#");

		Plot2DPanel plot = new Plot2DPanel();

		// add a line plot to the PlotPanel
		for (int i = 0; i < 3; i++) {
			plot.addLinePlot("X acceleration", data.getColumn(i));
		}

		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("Results");
		frame.setSize(800, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}

	private static void addNoise(double[] sample, int max) {
		for (int i = 0; i < sample.length; i++) {
			sample[i] += (-max + Math.random() * 2 * max);
		}
	}
}
