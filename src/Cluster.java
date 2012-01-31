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
	

	public void calculCentreGravite(){
		Point G= new Point(0,0);
		if (this.listPoints.size()!=0){
			for (int i=0;i<this.listPoints.size();i++){
				G.x+=this.listPoints.get(i).getX();
				G.y+=this.listPoints.get(i).getY();
			}
			G.x=G.x/this.listPoints.size();
			G.y=G.y/this.listPoints.size();
		}
		
		this.centreGravite=G;

	}
	@Override
	public String toString() {
		return "Cluster [listPoints=" + listPoints + ", centreGravite="
				+ centreGravite + "]";
	}
	
	


}
