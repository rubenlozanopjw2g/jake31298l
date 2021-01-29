package MeanShift;

import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Vector;
import java.util.Random;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class MainClass {
	
	static long startTime;
	public static void main(String[] args) {
		// MeanShift calls start ===============================================================================================================================
		Scanner reader = new Scanner(System.in); // Reading from System.in
		int n = 0;
		String filepath = "";
		
		System.out.println("Type 1 for cluster A1 with 3000 points.");
		System.out.println("Type 2 for cluster S1 with 5000 points.");
		
		int pickCluster = reader.nextInt(); // Scans the next token of the input as an int.
		
		while(pickCluster != 1 && pickCluster != 2)
		{
			System.out.println("You must input 1 or 2");
			pickCluster = reader.nextInt();
		}
		if(pickCluster == 1)
		{
			filepath = "Data/A1Data.xlsx";
			n = 3000;
		}
		else if(pickCluster == 2)
		{
			filepath = "Data/S1Data.xlsx";
			n = 5000;
		}
		
		
		
		int Q = 0;
		float[][] a1 = new float[n][6];
		Random rand = new Random();
	//	JFrame frame = new JFrame("Graph");
	//	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//	Plot plot = new Plot();
	//	Point point = new Point();
	//	frame.add(plot);
	//	frame.add(point);
	//	frame.setSize(500, 500);
	//	frame.setVisible(true);
		
		/*
		for(int i = 0; i < n; i++)
		{
			//(int i, float x, float y, float q, float a1[][])
			MeanShift.Add(i, rand.nextInt(50), rand.nextInt(50), 0, a1);
		}
		*/
				//float[][]a1 = new float[n][5];
		
			
				Plot.readExcel(filepath, a1);
				
				float[][]newClusterXY = new float[1][2];
				
				float[][]centroids = new float[n+1][4];
				
				float[][] fixClusters1 = new float[n][4];
				float[][] fixClusters2 = new float[n][4];
				
				Vector v1 = new Vector();
				Vector v2 = new Vector();
				
				float [][] problemPoints = new float[n][n];
				
				int a1Row;
				float myX;
				float myY;
				//LookHere MeanShift Arrays
				
				//LookHere MeanShift
				
				MeanShift.Print(a1, n);		//prints entire array of points just collected
				System.out.println("index 0 is " + a1[0][0]); //X
				System.out.println("index 1 is " + a1[0][1]);	// Y, testing to make sure array works correctly
				System.out.println("index 2 is " + a1[0][2]); //"in current cluster?" (0 or 1)
				System.out.println("index 3 is " + a1[0][3]); // "Has been clustered before?" (0 or 1)
				System.out.println("index 4 is " + a1[0][4]); // Current Cluster Number
				System.out.println("index 5 is " + a1[0][5]); //Weight (NO LONGER BEING CONSIDERED)
				
				
				
				myX = a1[0][0];
				myY = a1[0][1];
				System.out.println(" ");
				
				float newX = 0; // initialize varia7bles being used in forloop below
				float newY = 0;
				
				newClusterXY[0][0] = myX; //set the first x and y the cluster method will use
				newClusterXY[0][1] = myY;
				
				int clusterNumber = 1; //cluster starts at 1, declares it out of forloop scope
				//int test = 0;
				int []stop = new int[1]; // to stop forloop
				stop[0] = 0; // initialize stop to 0
				
				
		
				
				System.out.println("Input the size of the cluster's window(int) below.");
				System.out.println("Recommended size is between 5-20");
				System.out.println("Cluster with 3000 points: 6 will give ~20 clusters(accurate to dataset)");
				System.out.println("Cluster with 5000 points: 13 will give ~15 clusters(accurate to dataset)");
				
				
				
				int windowSize = reader.nextInt(); // Scans the next token of the input as an int.
				startTime = System.currentTimeMillis();
				
				
				
				
					//int windowSize = 10;	//sets window size of cluster
					for(clusterNumber = 1; clusterNumber > 0; clusterNumber++) //forloop to create each cluster
					{
		//				System.out.println("NEW CLUSTER. NUMBER: " + clusterNumber);
						newX = newClusterXY[0][0]; //sets the new x and y to be passed as a parameter to the function
						newY = newClusterXY[0][1];
						
		//				System.out.println(" ");
		//				System.out.println("Making cluster # " + clusterNumber + " with " + newX + " " + newY);
		//				System.out.println(" ");
					
						MeanShift.Cluster(newX, newY, a1, n, newClusterXY, clusterNumber, centroids, stop, fixClusters1, fixClusters2, problemPoints, windowSize, Q);
						
						
						if(stop[0] == 1) // if the clusterNumber above gets assigned a negative (clusters are done being made)
						{
		//					MeanShift.CentroidPrint(centroids, n);
							break; // break out of the for loop
							// This should also STOP clustering at this point, centroid and any unClusteredPoints are printed
						}		
					}
					
					 
				int numofClusters;	

				
				
				MeanShift.ProbPointPrint(problemPoints); //print problem points with clusters they have problems with
				MeanShift.checkCentroidWindow(centroids, a1, problemPoints, windowSize); //check centroids / merge centroids
		//		MeanShift.ProbPointPrint(problemPoints); //print problem points again, see if anything changed
		//		MeanShift.CentroidPrint(centroids, n); //print centroids after merge
				
				numofClusters = MeanShift.CentroidCount(centroids); //figure out centroid count
				
				//Try and figure out edges ? These are no longer being used with the implementation of ClusterLinkedLists below (~line 823+)
				float edgeArray[][] = new float[numofClusters][2];
				float edgePoints[][] = new float[numofClusters*2][2];
				
				float finalCentroids[][] = new float[numofClusters][5]; //create an array for finalized centroids
				
				
				
				
				
				
				MeanShift.pickClusterForPoint(a1, v1, v2, problemPoints); // assign a cluster for those problem points
		//		MeanShift.ProbPointPrint(problemPoints); // print problem points, confirming all have been solved (columns replaced with -1 when solved)
				//MeanShift.updateWeights(centroids, a1);
				MeanShift.FinalCentroids(centroids, finalCentroids); //obtain final centroids/neighborhoods
		//		MeanShift.CentroidPrint(finalCentroids, n); //print final centroids
				//MeanShift.FindEdges(a1,finalCentroids, edgeArray, edgePoints, numofClusters);
		//		MeanShift.Print(a1, windowSize); //re-print a1 array with individual points, showing updated/final neighborhoods numbers
				
				System.out.println(" ");
				System.out.println(" ");
				System.out.println(" ");
				
				
				//loop below reassigns cluster/neighborhood numbers to be orderly within the centroid array,
				//a1's points' clusterNumbers are updated to reflect the change as well
				float tempNum;
				for(int row = 0; row < finalCentroids.length; row++)
				{
					tempNum = finalCentroids[row][2];
					finalCentroids[row][2] = row+1;
					
					for(int i = 0; i < a1.length; i++)
					{
						if(a1[i][4] == tempNum)
						{
							a1[i][4] = row+1;
						}
					}
				}
			//	MeanShift.Print(a1, windowSize);
				MeanShift.CentroidPrint(finalCentroids, n);
				
				Plot.getArray(a1, finalCentroids);
				Application.launch(Plot.class, args);
		
				
				
					 
			//MeanShift calls end ============================

	}
	
	public static long getStartTime()
	{
		return startTime;
	}

}
