package cube;

public class RotateX extends Matrix {

	public RotateX(double angle) {
		super();
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
		init(new double[] {1, 0, 0, 0, 0, cos, sin, 0, 0, -sin, cos, 0, 0, 0, 0, 1}, 4);
	}

}
