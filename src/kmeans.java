import java.io.IOException;
import java.util.ArrayList;
/*import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;*/
import java.util.Random;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class kmeans {
	
	protected ArrayList<Cluster> Global;
	protected int k;
	protected int nbVar;
	
	public kmeans(ArrayList<Cluster> G, int k, int nbVar){
		this.Global=G;
		this.k=k;
		this.nbVar=nbVar;
	}

	public void afficherKmeans2(String s){

		ArrayList<XYSeries> GlobalSeries = new ArrayList<XYSeries>();

		for (int m=0; m<this.Global.size();m++){
			XYSeries series = new XYSeries(m);
			GlobalSeries.add(series);
		}

		for(int j=0; j<this.Global.size();j++){
			for(int i=0; i<this.Global.get(j).getListPoints().size();i++){
				GlobalSeries.get(j).add(this.Global.get(j).getListPoints().get(i).getX(),this.Global.get(j).getListPoints().get(i).getY());	
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
	public void algoKmeans(Fichier NotreFichier) throws IOException{

		ArrayList<ArrayList<Point>> Donnees=new ArrayList<ArrayList<Point>>();
		Donnees=NotreFichier.recupererFichier();

		//Initialisation
		
		this.choixHasardCluster(Donnees);
		System.out.println("Test Choix Hasard Cluster");
		String s=new String();
		
		for (int i=0; i<this.Global.size();i++){
			s=this.Global.get(i).toString();
			System.out.println(s);
		}
		
		this.afficherKmeans2("choix clusters");
		
		//Reallocation & Recentering

		//Premieres reallocation et recentering
		this.premiereReallocation(Donnees, k);
		System.out.println("Centre de gravite avant recentring"+ this.Global.get(0).getCentreGravite().getX()+" "+this.Global.get(0).getCentreGravite().getY());
		this.recentering();
		System.out.println("Centre de gravite aprs recentring"+ this.Global.get(0).getCentreGravite().getX()+" "+this.Global.get(0).getCentreGravite().getY());
		this.afficherKmeans2("premieres reallocation et recentering");
		
		//affichage 1er cluster: 
		
		for (int i=0; i<10;i++){
		System.out.println("clusterX : "+ this.Global.get(0).getListPoints().get(i).getX()+ "clusterY : "+ this.Global.get(0).getListPoints().get(i).getY());
		}

		this.reallocation();
		
		//affichage 1er cluster: 
		
		for (int i=0; i<10;i++){
		System.out.println("clusterX : "+ this.Global.get(0).getListPoints().get(i).getX()+ "clusterY : "+ this.Global.get(0).getListPoints().get(i).getY());
		}

		this.recentering();
		
		//this.afficherKmeans2();
		//affichage 1er cluster: 
		
				//for (int i=0; i<10;i++){
				//System.out.println("clusterX : "+ this.Global.get(0).getListPoints().get(i).getX()+ "clusterY : "+ this.Global.get(0).getListPoints().get(i).getY());
				//}
				
	
		//Répétition jusqu'à stabilisation
		ArrayList<Cluster> Globalancien = new ArrayList<Cluster>();

		while(this.Global!=Globalancien){
			Globalancien=this.Global;
			this.reallocation();
			this.recentering();
			
		}
		System.out.println("sortie de boucle");
		this.afficherKmeans2("sortie de boucle");
		
	}
	
	/*
        


	}*/

	//Methode qui place aleatoirement le barycentre de chaque cluster pour l'initialisation
		public void choixHasardCluster(ArrayList<ArrayList<Point>> Donnees){

			for (int j=0; j<this.k;j++){
				ArrayList<Point> LP=new ArrayList<Point>();
				Point G=new Point(0,0);
				Cluster C=new Cluster(LP,G);
				this.Global.add(C);
			}
			Random r = new Random();

			for(int i=0;i<k;i++){	
				Point A= Donnees.get(0).get(r.nextInt(Donnees.size())); //cas particulier où juste une serie
				if (A.getX()!=0&&A.getY()!=0){
					this.Global.get(i).setCentreGravite(A);
				}
			}
		}

	public void premiereReallocation(ArrayList<ArrayList<Point>> Donnees, int k){

		double dist = Double.MAX_VALUE;
		double newdist = 0;  
		int l=0;
		for(int j=0; j<Donnees.get(0).size(); j++)
		{
			for(int i=0; i<k;i++)
				{newdist=Donnees.get(0).get(j).calculDistance(this.Global.get(i).getCentreGravite());

				if(newdist<=dist){dist = newdist;l=i;}
				this.Global.get(l).getListPoints().add(Donnees.get(0).get(j));
				} 		           
			}
			System.out.println("Test premire rŽallocation"+Global);
			
		}

	public void reallocation(){

		ArrayList<Cluster> newGlobal=new ArrayList<Cluster>();
		for (int i=0;i<this.Global.size();i++){
			ArrayList<Point> A=new ArrayList<Point>();
			Point G=new Point(this.Global.get(i).getCentreGravite().getX(),this.Global.get(i).getCentreGravite().getY());
			Cluster C=new Cluster(A,G);
			newGlobal.add(C);
			
		}
	
		for (int i=0; i<this.Global.size();i++){
			for (int j=0;j<this.Global.get(i).getListPoints().size();j++){
				int indice=0;
				double Dmin=this.Global.get(i).getListPoints().get(j).calculDistance(this.Global.get(i).getCentreGravite());
				for (int l=0; l<this.Global.size();l++){
					
					double d=this.Global.get(i).getListPoints().get(j).calculDistance(this.Global.get(l).getCentreGravite());
					
							if (d<Dmin){ Dmin=d; indice=l;}
							
				}
				newGlobal.get(indice).getListPoints().add(this.Global.get(i).getListPoints().get(j));
			}}
		
		for (int i=0; i<this.Global.size();i++){
			this.Global.set(i, newGlobal.get(i));
		}
		System.out.println("Test rŽallocation");
		
			}

	public void recentering(){
		//calcul centre gravite
		for (int i=0;i<Global.size();i++){
			Point Gold=new Point(0,0);
			

			Gold=this.Global.get(i).getCentreGravite();
			this.Global.get(i).calculCentreGravite();

			if(Gold==this.Global.get(i).getCentreGravite()){
				System.out.println("Centre de gravitŽ inchangŽ pour le cluster "+i);
			}
		}
		System.out.println("Test recentring");
	
	}

}