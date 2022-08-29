package cube;

public class Scale extends Matrix {

	public Scale(double... values) {
		
		super();
		
		double[] arr = new double[values.length * values.length];
		for (int i = 0; i < values.length; i++) {
			arr[i * (values.length + 1)] = values[i];
		}
		
		init(arr, values.length);
		
	}

}
