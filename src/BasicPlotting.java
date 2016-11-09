import java.util.Arrays;
import java.util.Random;
import javax.swing.JFrame;
import org.math.plot.Plot2DPanel;

public class BasicPlotting {
	public static void main(String[] args) {
		String names = "t,xa,ya,za";
		String[] columnNames = names.split(",");

		CSVData data = CSVData.readCSVFile("C://Users//sasha//Desktop//HIMU4.txt", columnNames, 1, "#");

		Plot2DPanel plot = new Plot2DPanel();

		// add a line plot to the PlotPanel
//		for (int i = 0; i < 3; i++) {
//			plot.addLinePlot("acceleration", data.getColumn(i));
//		}
		
		plot.addLinePlot("mags", StepCounter.calculateMagnitudesFor(data.getColumns(1,3)));
		
		// put the PlotPanel in a JFrame, as a JPanel
		JFrame frame = new JFrame("Results");
		frame.setSize(800, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
		
		System.out.println(StepCounter.countStepsOriginal(null,data.getColumns(1,3)));
		System.out.println(StepCounter.countSteps(null,data.getColumns(1,3)));
		System.out.println(StepCounter.countStepsCon(null,data.getColumns(1,3)));
	}
	public static void print(double[][] a){
		for(int i = 0; i < a.length;i++){
			for(int j = 0; j < a[0].length;j++){
				System.out.print(a[i][j] + " , ");
			}
			System.out.println();
		}
	}

	private static void addNoise(double[] sample, int max) {
		for (int i = 0; i < sample.length; i++) {
			sample[i] += (-max + Math.random() * 2 * max);
		}
	}
}
