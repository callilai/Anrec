import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Fichier {

	protected String nom;
		
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Fichier(String nom){
		this.nom=nom;
		
	}

	public ArrayList<ArrayList<Point>> recupererFichier() throws IOException{

			FileReader f = new FileReader(this.nom);
			BufferedReader b = new BufferedReader(f);
			String line=b.readLine();
			String[] decompose;
			ArrayList<ArrayList<Point>> tabDonGlobal=new ArrayList<ArrayList<Point>>();
			int i=0;
			
			while(line !=null){
				decompose = line.split("	");
				for (int j=0; j<decompose.length -1;j++){
					ArrayList<Point>A = new ArrayList<Point>();
					tabDonGlobal.add(A);
				}
				 
				
				for (i=1;i<decompose.length;i++){
					//System.out.println(decompose[i]);
					double d=Double.parseDouble(decompose[i]);
					Point P=new Point(Double.parseDouble(decompose[0]),d);
					
					tabDonGlobal.get(i-1).add(P);
				}
				
				line=b.readLine();
				
			}
			
			return tabDonGlobal;	
	}


}
