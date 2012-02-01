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
	
	public kmeans(ArrayList<Cluster> G, int k){
		this.Global=G;
		this.k=k;
		
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
	public void algoKmeans(ArrayList<Point> Donnees) throws IOException{
		
	
		//Initialisation
		
		this.choixHasardCluster(Donnees);
				
		//Reallocation & Recentering

		//Premieres reallocation
		this.premiereReallocation(Donnees, k);
		this.recentering();
		
			
		this.afficherKmeans2("premieres reallocation et recentering");
		
		this.reallocation();
		this.recentering();
		
		//Répètition jusqu'à stabilisation
		ArrayList<Point> GraviteAncien = new ArrayList<Point>();
		
		for (int i=0; i<this.Global.size();i++){
			GraviteAncien.add(this.Global.get(i).getCentreGravite());
		}
		
		for (int j=0; j<this.Global.size();j++){
			
			while((this.Global.get(j).getCentreGravite().getX()!=GraviteAncien.get(j).getX())&&(this.Global.get(j).getCentreGravite().getY()!=GraviteAncien.get(j).getY())){
				for(int i=0;i<this.Global.size();i++){
					GraviteAncien.set(i,this.Global.get(i).getCentreGravite());
				}
				
				this.reallocation();
				this.recentering();
			}
		}
		
		
		this.afficherKmeans2("sortie de boucle");
		
	}
	
	/*
        


	}*/

	//Methode qui place aleatoirement le barycentre de chaque cluster pour l'initialisation
		public void choixHasardCluster(ArrayList<Point> Donnees){

			for (int j=0; j<this.k;j++){
				ArrayList<Point> LP=new ArrayList<Point>();
				Point G=new Point(0,0);
				Cluster C=new Cluster(LP,G);
				this.Global.add(C);
			}
			Random r = new Random();

			for(int i=0;i<k;i++){	
				
				Point A= Donnees.get(r.nextInt(Donnees.size())); 
				if (A.getX()!=0&&A.getY()!=0){
					this.Global.get(i).setCentreGravite(A);
				}
				
			}
		}

	public void premiereReallocation(ArrayList<Point> Donnees, int k){

		double dist = Double.MAX_VALUE;
		double newdist = 0;  
		int l=0;
		for(int j=0; j<Donnees.size(); j++)
		{
			for(int i=0; i<k;i++)
				{newdist=Donnees.get(j).calculDistance(this.Global.get(i).getCentreGravite());

				if(newdist<=dist){dist = newdist;l=i;}
				this.Global.get(l).getListPoints().add(Donnees.get(j));
				} 		           
			}
			
			
		}

	public void reallocation(){
		
		
		
		ArrayList<Point> Donnees=new ArrayList<Point>();
		
		for (int i=0; i<this.Global.size();i++){
			for (int j=0; j<this.Global.get(i).getListPoints().size();j++){
				Donnees.add(this.Global.get(i).getListPoints().get(j));
				this.Global.get(i).getListPoints().remove(j);
			}
			
		}
		
		this.premiereReallocation(Donnees, this.k);
		
	}
		
	public void recentering(){
		
		//On calcul le nouveau centre de gravité. S'il est différent de l'ancien on remplace ce dernier
		//par le nouveau dans Global
		
		for (int i=0;i<Global.size();i++){
			Point Gold=new Point(0,0);

			Gold=this.Global.get(i).getCentreGravite();
			this.Global.get(i).calculCentreGravite();

			if(Gold.getX()==this.Global.get(i).getCentreGravite().getX() &&Gold.getY()==this.Global.get(i).getCentreGravite().getY() ){
				System.out.println("Centre de gravité inchangé pour le cluster "+i);
			}
		}
		
	
	}

}