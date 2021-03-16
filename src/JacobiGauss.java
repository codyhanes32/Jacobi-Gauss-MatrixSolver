import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.io.*;

public class JacobiGauss {
	
	public static void main(String args[]) throws Exception{
		
		Scanner keyboard = new Scanner(System.in);
		
		int[][] coefArray = null;
		
		int option = getInput();
		if(option==1)
			coefArray = commandLineInput();
		else if(option==2)
			coefArray = fileInput();
		
		int n = coefArray.length;
		
		System.out.println("1) Jacobi iterative method");
		System.out.println("2) Gauss-Seidel method");
		System.out.print(": ");
			
		option = keyboard.nextInt();
		
		if(option == 1)
			jacobiOrGauss(n, coefArray, "Jacobi");
		else if(option == 2)
			jacobiOrGauss(n, coefArray, "Gauss");
			
	}
	
	public static void jacobiOrGauss(int n, int[][] coefArray, String method) {
		
		NumberFormat formatter = new DecimalFormat("#0.000");
		
		Scanner keyboard = new Scanner(System.in);
		double[] values = new double[n];
		double[] preValues = new double[n];
		double[][] multiplier = new double[n][n+1];
		
		System.out.print("Enter desired stopping error: ");
		double stoppingError = keyboard.nextDouble();
		
		System.out.print("Enter " + n + " values for starting solution: ");
		for(int i = 0; i < n; i++)
			values[i] = keyboard.nextDouble();
		
		int divider=0;
		for(int i = 0; i < n; i++) {
			for(int j =0; j < n+1; j++) {
				if(j == divider)
					multiplier[i][j] = 0;
				else if(j != n)
					multiplier[i][j] = -1*((double)coefArray[i][j] / (double)coefArray[divider][divider]);
				else
					multiplier[i][j] = (double)coefArray[i][j] / (double)coefArray[divider][divider];
			}
			divider++;
		}
		
		
		double error = 100;
		
		int iteration = 0;
		System.out.println();
		while(error > stoppingError || iteration == 50) {
			System.out.print("Iteration " + iteration  + " = [ ");
			for(int i = 0; i < values.length; i++)
				System.out.print(formatter.format(values[i])+ " ");
			System.out.print("]");
			
			for(int i = 0; i < preValues.length; i++)
				preValues[i] = values[i];
			
			double val = 0;
			double expectedVal=0;
			for(int i = 0; i < n; i++) {
				for(int j =0; j < n+1; j++){
					if(j==n) {
						expectedVal += multiplier[i][j];
					}
					else{
						if(method == "Jacobi")
							val = preValues[j] * multiplier[i][j];
						else if(method == "Gauss")
							val = values[j] * multiplier[i][j];
						expectedVal += val;
					}
				}
				
				values[i] = expectedVal;
				expectedVal = 0;
			}
			
			double val1 = 0, val2 = 0;
			for(int i = 0; i < values.length; i++)
			{
				val1 += Math.pow((values[i] - preValues[i]),2);
				val2 = Math.pow(values[i],2);
			}
			
			error = Math.abs(Math.sqrt(val1) / Math.sqrt(val2));
			System.out.print("  " + formatter.format(error));
			System.out.println();
			
			iteration++;
		}
		
		if(iteration==50)
			System.out.println("Stopping error was not reached");
		
	}

	
	public static int getInput() {
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Enter 1 to input from command line");
		System.out.println("Enter 2 to get input from file");
		System.out.println(": ");
		
		int input = keyboard.nextInt();
		
		return input;
		
	}
	
	public static int[][] commandLineInput() {
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.print("Enter number of equations: ");
		int numEquations = keyboard.nextInt();
		
		int[][] coefArray = new int[numEquations][numEquations+1];
		
		System.out.println("Enter coefficients");
		for(int i = 0; i < numEquations; i++) {
			for(int j =0; j < numEquations +1; j++) {
				coefArray[i][j] = keyboard.nextInt();
			}
		}
		
		return coefArray;
	}
	
	public static int[][] fileInput() throws Exception{
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.print("Enter name of file: ");
		String file = keyboard.nextLine();
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		int lines = 0;

		while (reader.readLine() != null)
			lines++;
		reader.close();

		int rows = lines;
		int cols = lines + 1;
		int[][] coef = new int[rows][cols];

		reader = new BufferedReader(new FileReader(file));

		for (int i = 0; i < rows; i++) {
			String[] line = reader.readLine().trim().split(" ");
			for (int j = 0; j < cols; j++) {
				coef[i][j] = Integer.parseInt(line[j]);
			}
		}

		return coef;
	}
	
	public static void print(int n, int coefArray[][]) {
		
		for(int i = 0; i < n; i++) {
			for(int j =0; j < n +1; j++) {
				System.out.print(coefArray[i][j] + "\t");
			}
			System.out.println();
		}
	}
}
