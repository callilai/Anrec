public class Point {
	double x;
	double y;
	double valeur;

	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}


	public double calculDistance(Point B){
		double d=Math.sqrt(Math.pow(this.getX()-B.getX(),2)+Math.pow((this.getY()-B.getY()),2));
		return d;		
	}
	
	public boolean mesureSimilarite(double Precision, Point B){
		boolean a=false;
		if (this.calculDistance(B)<Precision)
		{a =true;}		
		return a;
	}


}