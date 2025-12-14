import java.util.*;

/**
 * Graph representation using adjacency list
 */
public class Graph {
    private Map<String, List<Edge>> adjacencyList;
    private List<String> cities;
    private Map<String, Integer> cityIndex;
    private int[][] distanceMatrix;

    public Graph() {
        adjacencyList = new HashMap<>();
        cities = new ArrayList<>();
        cityIndex = new HashMap<>();
    }

    /**
     * Add city: O(1) average
     */
    public void addCity(String city) {
        if (!adjacencyList.containsKey(city)) {
            adjacencyList.put(city, new ArrayList<>());
            cityIndex.put(city, cities.size());
            cities.add(city);
        }
    }

    /**
     * Add edge: O(1) average
     */
    public void addEdge(String city1, String city2, double distance) {
        if (!adjacencyList.containsKey(city1))
            addCity(city1);
        if (!adjacencyList.containsKey(city2))
            addCity(city2);

        adjacencyList.get(city1).add(new Edge(city2, distance));
        adjacencyList.get(city2).add(new Edge(city1, distance));
    }

    public List<Edge> getNeighbors(String city) {
        return adjacencyList.getOrDefault(city, new ArrayList<>());
    }

    public List<String> getCityList() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    public int getCityIndex(String city) {
        return cityIndex.get(city);
    }

    public String getCityName(int index) {
        return cities.get(index);
    }

    public int getCityCount() {
        return cities.size();
    }

    public boolean hasEdge(String city1, String city2) {
        for (Edge edge : adjacencyList.get(city1)) {
            if (edge.city.equals(city2)) {
                return true;
            }
        }
        return false;
    }

    public double getDistance(String city1, String city2) {
        for (Edge edge : adjacencyList.get(city1)) {
            if (edge.city.equals(city2)) {
                return edge.distance;
            }
        }
        return Double.MAX_VALUE;
    }

    /**
     * Build distance matrix for Dijkstra: O(V²)
     */
    public void buildDistanceMatrix() {
        int n = cities.size();
        distanceMatrix = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    distanceMatrix[i][j] = 0;
                } else {
                    String city1 = getCityName(i);
                    String city2 = getCityName(j);
                    distanceMatrix[i][j] = (int) getDistance(city1, city2);
                }
            }
        }
    }

    public int[][] getDistanceMatrix() {
        return distanceMatrix;
    }
}