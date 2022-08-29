package cube;

public class Translate3d extends Matrix {

	public Translate3d(double x, double y, double z) {
		super(new double[] {1,0,0,0,0,1,0,0,0,0,1,0,x,y,z,1}, 4);
	}

}
