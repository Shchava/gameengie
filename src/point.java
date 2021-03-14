public class point {
	public point(double x,double y,double dir,int side){
		this.x=x;
		this.y=y;
		this.side=side;
		this.dir=dir;
	}
	public double x;
	public double y;
	public double dir;
/*
 * for side:
	* 0-Left
	* 1-Up
	* 2-Right
	* 3-Down
*/
	public int side;
}
