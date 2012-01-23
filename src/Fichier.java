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

	public ArrayList<Point> recupererFichier() throws IOException{

			FileReader f = new FileReader(this.nom);
			BufferedReader b = new BufferedReader(f);
			String line=b.readLine();
			String[] decompose;
			ArrayList<Point> tabDon=new ArrayList<Point>();
			int i=0;
			int j=0;
			while(line !=null){
				decompose = line.split("	");
				
				for (i=0;i<decompose.length;i++){
					//System.out.println(decompose[i]);
					double d=Double.parseDouble(decompose[i]);
					Point P=new Point(i,j);
					P.valeur=d;
					tabDon.add(P);
				}
				
				line=b.readLine();
				j++;
			}
			
			return tabDon;	
	}


}
