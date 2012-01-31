import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


public class Test {
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
		ArrayList<Cluster> Global = new ArrayList<Cluster>();
		Global=choixHasardCluster(Donnees,k);
		//afficherKmeans2(Global);

		//Reallocation & Recentering

		//Premieres reallocation et recentering
		Global=premiereReallocation(Global,Donnees, k);
		Global=recentering(Global);
		afficherKmeans2(Global);
		
		Global=reallocation(Global,k);
		Global=recentering(Global);
		afficherKmeans2(Global);

		//Répétition jusqu'à stabilisation
		ArrayList<Cluster> Globalancien = new ArrayList<Cluster>();

		while(Global!=Globalancien){
			Global=Globalancien;
			Global=reallocation(Global,k);
			Global=recentering(Global);
			
		}

		afficherKmeans2(Global);

	}


	public static void afficherKmeans2(ArrayList<Cluster> Global){



		ArrayList<XYSeries> GlobalSeries = new ArrayList<XYSeries>();

		for (int m=0; m<Global.size();m++){
			XYSeries series = new XYSeries(m);
			GlobalSeries.add(series);
		}

		for(int j=0; j<Global.size();j++){
			for(int i=0; i<Global.get(j).getListPoints().size();i++){
				GlobalSeries.get(j).add(Global.get(j).getListPoints().get(i).getX(),Global.get(j).getListPoints().get(i).getY());	
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
	public static ArrayList<Cluster> choixHasardCluster(ArrayList<ArrayList<Point>> Donnees, int k){

		ArrayList<Cluster> Global = new ArrayList<Cluster>();
		for (int j=0; j<k;j++){
			ArrayList<Point> A=new ArrayList<Point>();
			Point G=new Point(0,0);
			Cluster C=new Cluster(A,G);
			Global.add(C);
		}
		Random r = new Random();

		for(int i=0;i<k;i++){	
			Point A= Donnees.get(0).get(r.nextInt(Donnees.size())); //cas particulier où juste une serie
			if (A.getX()!=0&&A.getY()!=0){
				Global.get(i).setCentreGravite(A);
			}
		}

		return Global;
	}

	public static ArrayList<Cluster> premiereReallocation(ArrayList<Cluster> Global, ArrayList<ArrayList<Point>> Donnees, int k){

		double dist = Double.MAX_VALUE;
		double newdist = 0;  
		int l=0;
		for(int j=0; j<Donnees.get(0).size(); j++)
		{
			for(int i=0; i<k;i++)
			{newdist=calculDistance(Donnees.get(0).get(j), Global.get(i).getCentreGravite());

			if(newdist<=dist){dist = newdist;l=i;}
			Global.get(l).getListPoints().add(Donnees.get(0).get(j));
			} 		           
		}
		System.out.println("Test premire rŽallocation"+Global);
		return Global;
		
	}

	public static ArrayList<Cluster> reallocation(ArrayList<Cluster> Global, int k){

		ArrayList<Cluster> newGlobal=new ArrayList<Cluster>();
		for (int i=0;i<Global.size();i++){
			ArrayList<Point> A=new ArrayList<Point>();
			Point G=new Point(Global.get(i).getCentreGravite().getX(),Global.get(i).getCentreGravite().getY());
			Cluster C=new Cluster(A,G);
			newGlobal.add(C);
		}
		int n=0;
		double dist = Double.MAX_VALUE;
		double newdist = 0;  
		for(int i=0; i<Global.size();i++){
			for(int j=0;j<Global.get(i).getListPoints().size();j++){
				for (int m=0;m<Global.size();m++){
					newdist=calculDistance(Global.get(i).getListPoints().get(j),Global.get(m).getCentreGravite());

					if(newdist<=dist){dist = newdist;n=m;}
					
					newGlobal.get(n).getListPoints().add(Global.get(i).getListPoints().get(j));
				}
			}
			
		}
		System.out.println("Test rŽallocation");
		return newGlobal;

	}

	public static ArrayList<Cluster> recentering(ArrayList<Cluster> Global){
		//calcul centre gravite
		for (int i=0;i<Global.size();i++){
			Point Gold=new Point(0,0);
			Point Gnew=new Point(0,0);

			Gold=Global.get(i).getCentreGravite();
			Gnew=calculCentreGravite(Global.get(i).getListPoints());

			if(Gold!=Gnew){
				Global.get(i).setCentreGravite(Gnew);
			}
		}
		System.out.println("Test recentring");
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


	public static void  main (String [] args) throws IOException{
		Fichier exemple1=new Fichier("exemple1.txt");
		//afficherKmeans(exemple1.recupererFichier());
		algoKmeans(exemple1, 2, 2);

	}
}