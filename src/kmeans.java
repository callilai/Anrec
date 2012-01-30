import java.io.IOException;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.*;
import org.jfree.data.*;


public class kmeans extends JPanel {

	public static void main(String[] args){



	}

	public static void afficherKmeans(ArrayList<ArrayList<Point>> Global){


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
	
	public static void algoKmeans(Fichier NotreFichier, int k, int nbVar) throws IOException{

		ArrayList<ArrayList<Point>> Donnees=new ArrayList<ArrayList<Point>>();
		Donnees=NotreFichier.recupererFichier();

		//Initialisation
		ArrayList<ArrayList<Point>> Global = new ArrayList<ArrayList<Point>>();
		Global=choixHasardCluster(Donnees,k);
		afficherKmeans2(Global);

		//Reallocation & Recentering

		//Premieres reallocation et recentering
		Global=premiereReallocation(Global,Donnees, k);
		Global=recentering(Global);
		afficherKmeans2(Global);
		
		Global=reallocation(Global,k);
		Global=recentering(Global);
		afficherKmeans2(Global);

		//Répétition jusqu'à stabilisation
		ArrayList<ArrayList<Point>> Globalancien = new ArrayList<ArrayList<Point>>();

		while(Global!=Globalancien){
			Global=Globalancien;
			Global=reallocation(Global,k);
			Global=recentering(Global);
		}

		afficherKmeans2(Global);

	}


	public static void afficherKmeans2(ArrayList<ArrayList<Point>> Global){



		ArrayList<XYSeries> GlobalSeries = new ArrayList<XYSeries>();

		for (int m=0; m<Global.size();m++){
			XYSeries series = new XYSeries(m);
			GlobalSeries.add(series);
		}

		for(int j=0; j<Global.size();j++){
			for(int i=0; i<Global.get(j).size();i++){
				GlobalSeries.get(j).add(Global.get(j).get(i).getX(),Global.get(j).get(i).getY());	
			}
		}

		XYSeriesCollection Dataset = new XYSeriesCollection();
		for(int j=0; j<GlobalSeries.size();j++){
			Dataset.addSeries(GlobalSeries.get(j));
		}

		JFreeChart chart = ChartFactory.createScatterPlot("test", "X", "Y", Dataset,PlotOrientation.VERTICAL, false, false,false);
		ChartFrame frame1=new ChartFrame("XY Chart",chart);

		frame1.setVisible(true);
		frame1.setSize(300,300);


	}




	//Methode qui place aleatoirement le barycentre de chaque cluster pour l'initialisation
	public static ArrayList<ArrayList<Point>> choixHasardCluster(ArrayList<ArrayList<Point>> Donnees, int k){

		ArrayList<ArrayList<Point>> Global = new ArrayList<ArrayList<Point>>();
		for (int j=0; j<k;j++){
			ArrayList<Point>A = new ArrayList<Point>();
			Global.add(A);
		}
		Random r = new Random();

		for(int i=0;i<k;i++){	
			Point A= Donnees.get(0).get(r.nextInt(Donnees.size())); //cas particulier où juste une serie
			if (A.getX()!=0&&A.getY()!=0){
				Global.get(i).add(A);
			}
		}

		return Global;
	}

	public static ArrayList<ArrayList<Point>> premiereReallocation(ArrayList<ArrayList<Point>> Global, ArrayList<ArrayList<Point>> Donnees, int k){

		double dist = Double.MAX_VALUE;
		double newdist = 0;        
		for(int j=0; j<Donnees.get(0).size(); j++)
		{
			for(int i=0; i<k;i++)
			{newdist=calculDistance(Donnees.get(0).get(j), Global.get(i).get(0));

			if(newdist<=dist){dist = newdist;}
			Global.get(i).add(Donnees.get(0).get(j));
			} 		           
		}
		return Global;
	}

	public static ArrayList<ArrayList<Point>> reallocation(ArrayList<ArrayList<Point>> Global, int k){

		
		ArrayList<ArrayList<Point>> newGlobal = new ArrayList<ArrayList<Point>>();
		for (int j=0; j<k;j++){
			ArrayList<Point> A = new ArrayList<Point>();
			newGlobal.add(A);
			newGlobal.get(j).add(Global.get(j).get(0));
			
		}
		
		double dist = Double.MAX_VALUE;
		double newdist = 0;  
		for(int i=0; i<Global.size();i++){
			for(int j=1;j<Global.get(i).size();j++){
				for(int m=0; m<newGlobal.size();m++){

					newdist=calculDistance(Global.get(i).get(j),newGlobal.get(m).get(0));

					if(newdist<=dist){dist = newdist;}
					newGlobal.get(m).add(Global.get(i).get(j));
				}
			}
		}
		return newGlobal;

	}

	public static ArrayList<ArrayList<Point>> recentering(ArrayList<ArrayList<Point>> Global){
		//calcul centre gravite
		for (int i=0;i<Global.size();i++){
			Point Gold=new Point(0,0);
			Point Gnew=new Point(0,0);

			Gold=Global.get(i).get(0);
			Gnew=calculCentreGravite(Global.get(i));

			if(Gold!=Gnew){
				//mettre centre gravite en premiere position
				Global.get(i).add(0, Gnew);
			}
		}
		return Global;
	}

	public static Point calculCentreGravite(ArrayList<Point> Liste){
		Point G= new Point(0,0);
		if (Liste.size()!=0){
			for (int i=0;i< Liste.size();i++){
				G.x+=Liste.get(i).getX();
				G.y+=Liste.get(i).getY();
			}
			G.x=G.x/Liste.size();
			G.y=G.y/Liste.size();
		}

		return G;

	}

	public static double calculDistance(Point A, Point B){
		double d=Math.sqrt(Math.pow(A.getX()-B.getX(),2)+Math.pow((A.getY()-B.getY()),2));
		return d;		
	}

	public static boolean mesureSimilarite(double Precision, Point A, Point B){
		boolean a=false;
		if (calculDistance(A,B)<Precision)
		{a =true;}		
		return a;
	}

}