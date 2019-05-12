/**
 * Public Transit
 * Author: Brian Schlapp and Carolyn Yao
 * Does this compile? Y
 */

/**
 * This class contains solutions to the Public, Public Transit problem in the
 * shortestTimeToTravelTo method. There is an existing implementation of a
 * shortest-paths algorithm. As it is, you can run this class and get the
 * solutions from the existing shortest-path algorithm.
 */
public class FastestRoutePublicTransit {

	/**
	 * The algorithm that could solve for shortest travel time from a station S to a
	 * station T given various tables of information about each edge (u,v)
	 *
	 * @param S         the s th vertex/station in the transit map, start From
	 * @param T         the t th vertex/station in the transit map, end at
	 * @param startTime the start time in terms of number of minutes from 5:30am
	 * @param lengths   lengths[u][v] The time it takes for a train to get between
	 *                  two adjacent stations u and v
	 * @param first     first[u][v] The time of the first train that stops at u on
	 *                  its way to v, int in minutes from 5:30am
	 * @param freq      freq[u][v] How frequently is the train that stops at u on
	 *                  its way to v
	 * @return shortest travel time between S and T
	 */
	public int myShortestTravelTime(int S, int T, int startTime, int[][] lengths, int[][] first, int[][] freq) {
		// Used geeksforgeeks dijkstras method and modified it to give me the shortest path from S to T
		int V = lengths[0].length;
		int[] times = new int[V];
		int[] prevStation = new int[V];
		int[] shortestPath = new int[V];
		int index = 0;
		Boolean sptSet[] = new Boolean[V];


		for (int i = 0; i < V; i++) {
			times[i] = Integer.MAX_VALUE;
			sptSet[i] = false;
			shortestPath[i] = -1;
		}
		times[S] = 0;

		for (int count = 0; count < V - 1; count++) {
			int u = findNextToProcess(times, sptSet);
			sptSet[u] = true;

			for (int v = 0; v < V; v++) {

				if (!sptSet[v] && lengths[u][v] != 0 && times[u] != Integer.MAX_VALUE
						&& times[u] + lengths[u][v] < times[v]) {
					times[v] = times[u] + lengths[u][v];
					prevStation[v] = u;
				}
			}
		}

		while (T != S) {
			shortestPath[index++] = T;
			T = prevStation[T];
		}
		shortestPath[index] = T;
		
		index = first[0].length - 1;
		int timeTaken = 0; 
		int nextTrain;
		int station;
		int nextStation;
		int trainTime;
		while (shortestPath[index] != S && index > 0) {
			index--;
		}

		while(index > 0) {
			station = shortestPath[index];
			nextStation = shortestPath[index - 1];
			trainTime = first[station][nextStation];
			int j = 0;
			while (trainTime < startTime) {
				// using what you said in the second paragraph of the assignment
				trainTime = first[station][nextStation] + (j * freq[station][nextStation]); 
				j++;
			}

			nextTrain = trainTime + lengths[station][nextStation];
			timeTaken = timeTaken + (nextTrain - startTime);
			startTime = nextTrain;
			index--;
		}
		return timeTaken;
	}

	/**
	 * Finds the vertex with the minimum time from the source that has not been
	 * processed yet.
	 * 
	 * @param times     The shortest times from the source
	 * @param processed boolean array tells you which vertices have been fully
	 *                  processed
	 * @return the index of the vertex that is next vertex to process
	 */
	public int findNextToProcess(int[] times, Boolean[] processed) {
		int min = Integer.MAX_VALUE;
		int minIndex = -1;

		for (int i = 0; i < times.length; i++) {
			if (processed[i] == false && times[i] <= min) {
				min = times[i];
				minIndex = i;
			}
		}
		return minIndex;
	}

	public void printShortestTimes(int times[]) {
		System.out.println("Vertex Distances (time) from Source");
		for (int i = 0; i < times.length; i++)
			System.out.println(i + ": " + times[i] + " minutes");
	}

	/**
	 * Given an adjacency matrix of a graph, implements
	 * 
	 * @param graph  The connected, directed graph in an adjacency matrix where if
	 *               graph[i][j] != 0 there is an edge with the weight graph[i][j]
	 * @param source The starting vertex
	 */
	public void shortestTime(int graph[][], int source) {
		int numVertices = graph[0].length;

		// This is the array where we'll store all the final shortest times
		int[] times = new int[numVertices];

		// processed[i] will true if vertex i's shortest time is already finalized
		Boolean[] processed = new Boolean[numVertices];

		// Initialize all distances as INFINITE and processed[] as false
		for (int v = 0; v < numVertices; v++) {
			times[v] = Integer.MAX_VALUE;
			processed[v] = false;
		}

		// Distance of source vertex from itself is always 0
		times[source] = 0;

		// Find shortest path to all the vertices
		for (int count = 0; count < numVertices - 1; count++) {
			// Pick the minimum distance vertex from the set of vertices not yet processed.
			// u is always equal to source in first iteration.
			// Mark u as processed.
			int u = findNextToProcess(times, processed);
			processed[u] = true;

			// Update time value of all the adjacent vertices of the picked vertex.
			for (int v = 0; v < numVertices; v++) {
				// Update time[v] only if is not processed yet, there is an edge from u to v,
				// and total weight of path from source to v through u is smaller than current
				// value of time[v]
				if (!processed[v] && graph[u][v] != 0 && times[u] != Integer.MAX_VALUE
						&& times[u] + graph[u][v] < times[v]) {
					times[v] = times[u] + graph[u][v];
				}
			}
		}

		printShortestTimes(times);
	}

	public static void main(String[] args) {
		/* length(e) */
	    int lengthTimeGraph[][] = new int[][]{
	        {0, 4, 0, 0, 0, 0, 0, 8, 0},
	        {4, 0, 8, 0, 0, 0, 0, 11, 0},
	        {0, 8, 0, 7, 0, 4, 0, 0, 2},
	        {0, 0, 7, 0, 9, 14, 0, 0, 0},
	        {0, 0, 0, 9, 0, 10, 0, 0, 0},
	        {0, 0, 4, 14, 10, 0, 2, 0, 0},
	        {0, 0, 0, 0, 0, 2, 0, 1, 6},
	        {8, 11, 0, 0, 0, 0, 1, 0, 7},
	        {0, 0, 2, 0, 0, 0, 6, 7, 0}
	      };
		FastestRoutePublicTransit t = new FastestRoutePublicTransit();
		t.shortestTime(lengthTimeGraph, 0);

		// You can create a test case for your implemented method for extra credit below

		// used the same length time graph you provided.
		int first[][] = new int[][] {    
			{0, 3, 0, 0, 0, 0, 0, 1, 0},
	        {4, 0, 8, 0, 0, 0, 0, 15, 0},
	        {0, 8, 0, 2, 0, 0, 0, 0, 2},
	        {0, 0, 0, 0, 9, 14, 0, 0, 0},
	        {0, 0, 0, 9, 0, 10, 0, 0, 0},
	        {0, 0, 4, 14, 0, 0, 2, 0, 0},
	        {0, 0, 0, 0, 0, 2, 0, 1, 6},
	        {8, 11, 0, 0, 0, 0, 1, 0, 7},
	        {3, 0, 2, 0, 0, 0, 6, 7, 0}
	      };

		int freq[][] = new int[][] {    
			{0, 7, 0, 0, 0, 0, 0, 2, 0},
	        {5, 0, 15, 0, 0, 0, 0, 4, 0},
	        {0, 8, 0, 5, 0, 0, 0, 0, 2},
	        {0, 0, 0, 0, 6, 7, 0, 0, 0},
	        {0, 0, 0, 9, 0, 8, 0, 0, 0},
	        {0, 0, 6, 6, 0, 0, 1, 0, 0},
	        {0, 0, 0, 0, 0, 2, 0, 3, 4},
	        {23, 4, 0, 0, 0, 0, 1, 0, 2},
	        {5, 0, 2, 0, 0, 0, 6, 15, 0}
	      };
		int S = 1;
		int T = 8;
		int shortestTravel = t.myShortestTravelTime(S, T, 0, lengthTimeGraph, first, freq);
		System.out.println(
				"myShortestTravelTime from Station " + S + " to Station " + T + " is " + shortestTravel);
	}
}
