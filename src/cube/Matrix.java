package cube;

public class Matrix {
	public int columns;
	public double[] data;
	public int rows;

	public Matrix(double[] array, int rows) {
		
		init(array, rows);
		
	}
	
	protected Matrix() {
		
	}
	
	protected void init(double[] array, int rows) {
		
		data = array;
		this.rows = rows;
		columns = array.length / rows;
		
	}
	
	public static Matrix multiply(Matrix... args) {
		
		if (args.length < 2) {
			System.out.println("multiply requires at least 2 parameters");
			return args[0];
		}
		Matrix result = args[args.length - 1];
		
		for (int i = args.length - 2; i >= 0; i--) {
			result = args[i].multiply(result);
		}
		
		return result;
		
	}
	
	public Matrix multiply(Matrix mat) {
		
		if (columns < mat.rows) {
			System.out.println("Provided matrix has too many rows\nCalculation cancelled");
			return mat;
		}
		
		double[] tempData = new double[rows * mat.columns];
		
		for (int i = 0; i < mat.columns; i++) {
			for (int j = 0; j < mat.rows; j++) {
				for (int k = 0; k < rows; k++) {
					tempData[i * rows + k] += mat.data[i * mat.rows + j] * data[rows * j + k];
				}
			}
		}
		
		return new Matrix(tempData, rows);
		
	}
	
	public boolean equals(Matrix mat) {
		if (columns != mat.columns || rows != mat.rows) return false;
		
		for (int i = 0; i < data.length; i++) {
			if (data[i] != mat.data[i]) return false;
		}
		return true;
	}

}
