import java.util.ArrayList;


public class Cluster {
	protected ArrayList<Point> listPoints;
	protected Point centreGravite;
	public ArrayList<Point> getListPoints() {
		return listPoints;
	}
	public void setListPoints(ArrayList<Point> listPoints) {
		this.listPoints = listPoints;
	}
	public Point getCentreGravite() {
		return centreGravite;
	}
	public void setCentreGravite(Point centreGravite) {
		this.centreGravite = centreGravite;
	}
	public Cluster(){
		this.centreGravite=null;
	}
	public Cluster(ArrayList<Point> listPoints, Point centreGravite) {
		this.listPoints = listPoints;
		this.centreGravite = centreGravite;
	}
	

}
