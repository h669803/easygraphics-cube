package cube;

public class RotateY extends Matrix {

	public RotateY(double angle) {
		super();
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
		init(new double[] {cos, 0, -sin, 0, 0, 1, 0 , 0, sin, 0, cos, 0, 0, 0, 0, 1}, 4);
	}

}