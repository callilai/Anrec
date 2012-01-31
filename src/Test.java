import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class Test {
	
	public static void afficherDonnes(ArrayList<ArrayList<Point>> Global){


		XYSeries series = new XYSeries("Average Weight");

		for(int j=0; j<Global.get(0).size();j++){
		series.add(Global.get(0).get(j).getX(), Global.get(0).get(j).getY());
		}

		XYSeriesCollection Dataset = new XYSeriesCollection(series);
		//Dataset.addSeries(series);
		  JFreeChart chart = ChartFactory.createScatterPlot("test", "X", "Y", Dataset,PlotOrientation.VERTICAL, false, false,false);
		  ChartFrame frame1=new ChartFrame("XY Chart",chart);

		  frame1.setVisible(true);
		  frame1.setSize(300,300);


	}
	public static void afficherKmeans2(kmeans kms, String s){

		ArrayList<XYSeries> GlobalSeries = new ArrayList<XYSeries>();

		for (int m=0; m<kms.Global.size();m++){
			XYSeries series = new XYSeries(m);
			GlobalSeries.add(series);
		}

		for(int j=0; j<kms.Global.size();j++){
			for(int i=0; i<kms.Global.get(j).getListPoints().size();i++){
				GlobalSeries.get(j).add(kms.Global.get(j).getListPoints().get(i).getX(),kms.Global.get(j).getListPoints().get(i).getY());	
			}
		}

		XYSeriesCollection Dataset = new XYSeriesCollection();
		for(int j=0; j<GlobalSeries.size();j++){
			Dataset.addSeries(GlobalSeries.get(j));
		}

		org.jfree.chart.JFreeChart chart = ChartFactory.createScatterPlot(s, "X", "Y", Dataset,PlotOrientation.VERTICAL, false, false,false);
		ChartFrame frame1=new ChartFrame("ANREC",chart);

		frame1.setVisible(true);
		frame1.setSize(300,300);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void  main (String [] args) throws IOException{
		Fichier exemple1=new Fichier("exemple1.txt");
		//afficherKmeans(exemple1.recupererFichier());
		ArrayList<Cluster> A= new ArrayList<Cluster>();
		kmeans kms=new kmeans(A,2,2);
		kms.algoKmeans(exemple1);
		afficherKmeans2(kms,"algokmeans");
		
	}
}