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

	public static void algoKmeans(Fichier NotreFichier, int k, int nbVar) throws IOException{
		
		ArrayList<ArrayList<Point>> Donnees=new ArrayList<ArrayList<Point>>();
		Donnees=NotreFichier.recupererFichier();
		
		//Initialisation
		ArrayList<ArrayList<Point>> Global = new ArrayList<ArrayList<Point>>();
		Global=choixHasardCluster(Donnees,k);
		

		//Reallocation & Recentering
		
		//Premieres reallocation et recentering
		Global=premiereReallocation(Global,Donnees, k);
		Global=recentering(Global);
		
		//Répétition jusqu'à stabilisation
		ArrayList<ArrayList<Point>> Globalancien = new ArrayList<ArrayList<Point>>();
		
		while(Global!=Globalancien){
			Global=Globalancien;
			Global=reallocation(Global,k);
			Global=recentering(Global);
		}
		
		afficherKmeans(Global);
				

	}

	
	
	//Methode qui place aleatoirement le barycentre de chaque cluster pour l'initialisation
		public static ArrayList<ArrayList<Point>> choixHasardCluster(ArrayList<Point> Donnees, int k){
			
			ArrayList<ArrayList<Point>> Global = new ArrayList<ArrayList<Point>>();
			Random r = new Random();
					
			for(int i=0;i<k;i++){	
				Double A= Donnees.get(r.nextInt(Donnees.size()));
				Global.get(i).add(A);
	        }
			
			return Global;
		}

		public static ArrayList<ArrayList<Point>> premiereReallocation(ArrayList<ArrayList<Point>> Global, ArrayList<Point> Donnees, int k){
		 
			double dist = Double.MAX_VALUE;
	        double newdist = 0;        
			for(int j=0; j<Donnees.size(); j++)
		            {
		            	for(int i=0; i<k;i++)
		            	{newdist=calculDistance(Donnees.get(j), Global.get(i).get(0));
		            				
		            				if(newdist<=dist){dist = newdist;}
		            				Global.get(i).add(Donnees.get(j));
		            	} 		           
		            }
			return Global;
		}

		public static ArrayList<ArrayList<Point>> reallocation(ArrayList<ArrayList<Point>> Global, int k){
			
			double dist = Double.MAX_VALUE;
	        double newdist = 0;  
			for(int i=0; i<Global.size();i++){
				for(int j=1;j<Global.get(i).size();j++){
					for(int m=1; m<Global.size();m++){
								
					newdist=calculDistance(Global.get(i).get(j),Global.get(m).get(0));
					
					if(newdist<=dist){dist = newdist;}
					Global.get(m).add(Global.get(i).get(j));
					}
				}
			}
			return Global;

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
