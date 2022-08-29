package cube;

import easygraphics.*;

public class Cube extends EasyGraphics {

	public static void main(String[] args) {
		launch(args);
	}
	
	// Variables for changing how the cube is drawn
	double horizontalFov = 100;
	int width = 1200;
	int height = 900;
	double pointScale;
	double rotationSpeedX = .01;
	double rotationSpeedY = .016;
	double cameraPositionZ = 1.74;
	Matrix scale = new Scale(1, 1, 1, 1);
	Matrix preTranslate = new Translate3d(1.5, 0, 0);
	Matrix postTranslate = new Translate3d(0, 0, 0);
	
	public void run() {
		
		Matrix[] points = new Matrix[8];
		double[][] pointsData = {
			{1,1,1,1}, {-1,1,1,1}, {-1,-1,1,1}, {1,-1,1,1},
			{1,-1,-1,1}, {-1,-1,-1,1}, {-1,1,-1,1}, {1,1,-1,1}
		};
		int[][] lines = {
			{0,1}, {1,2}, {2,3}, {3,0}, {0,7}, {1,6}, 
			{2,5}, {3,4}, {4,5}, {5,6}, {6,7}, {7,4}
		};
		double angleX = 0, angleY = 0;
		
		for (int i = 0; i < 8; i++) {
			points[i] = new Matrix(pointsData[i], 4);
		}
		makeWindow("Cube", width, height);
		setSpeed(10);
		fillRectangle(0, 0, width, height);
		setColor(0, 200, 255);
		pointScale = width / 2 / Math.tan(horizontalFov / 360 * Math.PI);
		
		while (true) {
		
			Matrix rotateX = new RotateX(angleX += rotationSpeedX);
			Matrix rotateY = new RotateY(angleY += rotationSpeedY);
			
			Matrix transformMatrix = Matrix.multiply(postTranslate, rotateX, rotateY, scale, preTranslate);
			int[][] tempPoints = new int[8][3];
			Matrix[] transformedPoints = new Matrix[8];
			
			for (int i = 0; i < 8; i++) {
				Matrix point = transformedPoints[i] = transformMatrix.multiply(points[i]);
				tempPoints[i] = getDrawnPoint(point.data);
			}
			
			for (int i = 0; i < 12; i++) {
				int[] startP = tempPoints[lines[i][0]];
				int[] endP = tempPoints[lines[i][1]];
				double startZ = transformedPoints[lines[i][0]].data[2];
				double endZ = transformedPoints[lines[i][1]].data[2];
				
				// Fixing points behind the camera
				if (startZ >= cameraPositionZ && endZ >= cameraPositionZ) startP = endP = new int[2];
				else if (startZ >= cameraPositionZ || endZ >= cameraPositionZ) {
					boolean switchOrder = startZ >= cameraPositionZ;
					if (switchOrder) startP = endP;
					endP = fixPointBehindCam(
						transformedPoints[lines[i][switchOrder ? 1 : 0]].data, 
						transformedPoints[lines[i][switchOrder ? 0 : 1]].data);
				}
				
				int lineID = drawLine(startP[0], startP[1], endP[0], endP[1]);
				if (lineID > 12) setVisible(lineID - 12, false);
			}
			
		}
		
	}
	
	private int[] fixPointBehindCam(double[] start, double[] end) {
		double scale = (cameraPositionZ - start[2]) / (end[2] - start[2]) * .99;
		double[] newEndPoint = new double[3];
		for (int i = 0; i < 3; i++) {
			newEndPoint[i] = start[i] + (end[i] - start[i]) * scale;
		}
		
		return getDrawnPoint(newEndPoint);
	}
	
	private int[] getDrawnPoint(double[] point) {
		double scale = pointScale / (cameraPositionZ - point[2]);
		return new int[] {
			(int)Math.round(point[0] * scale) + width / 2,
			(int)Math.round(point[1] * scale) + height / 2
		};
	}
	
}
