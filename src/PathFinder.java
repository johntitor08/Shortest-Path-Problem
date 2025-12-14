import java.util.*;

/**
 * PathFinder class implementing all three required algorithms
 */
public class PathFinder {
    private Graph graph;

    public PathFinder(Graph graph) {
        this.graph = graph;
    }

    /**
     * Standard DFS Algorithm using custom stack
     * Time Complexity: O(V + E) where V=vertices, E=edges
     * Finds a path (not necessarily shortest)
     */
    public List<String> dfs(String start, String goal) {
        long startTime = System.nanoTime();

        Stack<String> stack = new Stack<>();
        Map<String, Boolean> visited = new HashMap<>();
        Map<String, String> parent = new HashMap<>();
        Map<String, Integer> distances = new HashMap<>();

        for (String city : graph.getCityList()) {
            visited.put(city, false);
        }

        stack.push(start);
        visited.put(start, true);
        distances.put(start, 0);

        while (!stack.isEmpty()) {
            String current = stack.pop();

            if (current.equals(goal)) {
                long endTime = System.nanoTime();
                List<String> path = reconstructPath(parent, start, goal);
                int distance = calculatePathDistance(path);
                printResults("DFS", path, distance, endTime - startTime);
                return path;
            }

            for (Edge neighbor : graph.getNeighbors(current)) {
                if (!visited.get(neighbor.city)) {
                    visited.put(neighbor.city, true);
                    parent.put(neighbor.city, current);
                    int currentDist = distances.getOrDefault(current, 0);
                    distances.put(neighbor.city, currentDist + (int) neighbor.distance);
                    stack.push(neighbor.city);
                }
            }
        }

        long endTime = System.nanoTime();
        System.out.println("DFS - No path found from " + start + " to " + goal);
        System.out.println("Execution time: " + (endTime - startTime) + " ns");
        return new ArrayList<>();
    }

    /**
     * Modified DFS Algorithm to find shortest path
     * Time Complexity: O(V!) worst case - explores all possible paths
     * Finds the shortest path by exhaustive search
     */
    public List<String> modifiedDFS(String start, String goal) {
        long startTime = System.nanoTime();

        // Initialize shortest path tracking
        List<String> shortestPath = new ArrayList<>();
        int[] shortestDistance = new int[] { Integer.MAX_VALUE }; // Using array to allow mutation

        // Create visited set and current path
        Set<String> visited = new HashSet<>();
        List<String> currentPath = new ArrayList<>();

        // Start DFS exploration
        dfsShortestHelper(start, goal, visited, currentPath, 0,
                shortestPath, shortestDistance);

        long endTime = System.nanoTime();

        if (!shortestPath.isEmpty() && shortestDistance[0] < Integer.MAX_VALUE) {
            printResults("Modified DFS", shortestPath, shortestDistance[0], endTime - startTime);
            return shortestPath;
        } else {
            System.out.println("Modified DFS - No path found from " + start + " to " + goal);
            System.out.println("Execution time: " + (endTime - startTime) + " ns");
            return new ArrayList<>();
        }
    }

    private void dfsShortestHelper(String current, String goal, Set<String> visited,
            List<String> currentPath, int currentDistance,
            List<String> shortestPath, int[] shortestDistance) {
        // Add current node to path and mark as visited
        visited.add(current);
        currentPath.add(current);

        // Check if we reached the goal
        if (current.equals(goal)) {
            if (currentDistance < shortestDistance[0]) {
                // Update shortest path and distance
                shortestPath.clear();
                shortestPath.addAll(new ArrayList<>(currentPath));
                shortestDistance[0] = currentDistance;
            }

            // Backtrack
            visited.remove(current);
            currentPath.remove(currentPath.size() - 1);
            return;
        }

        // Explore neighbors
        for (Edge neighbor : graph.getNeighbors(current)) {
            if (!visited.contains(neighbor.city)) {
                int newDistance = currentDistance + (int) neighbor.distance;

                // Only continue if current path is promising
                if (newDistance < shortestDistance[0]) {
                    dfsShortestHelper(neighbor.city, goal, visited, currentPath,
                            newDistance, shortestPath, shortestDistance);
                }
            }
        }

        // Backtrack
        visited.remove(current);
        currentPath.remove(currentPath.size() - 1);
    }

    /**
     * Dijkstra's Algorithm using PriorityQueue
     * Time Complexity: O((V + E) log V) with priority queue
     * Finds the guaranteed shortest path
     */
    public List<String> dijkstra(String start, String goal) {
        long startTime = System.nanoTime();

        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        Set<String> visited = new HashSet<>();

        // Initialize distances
        for (String city : graph.getCityList()) {
            distances.put(city, Integer.MAX_VALUE);
        }
        distances.put(start, 0);

        // Use priority queue (allowed for Dijkstra)
        PriorityQueue<String> pq = new PriorityQueue<>(
                Comparator.comparingInt(distances::get));
        pq.add(start);

        while (!pq.isEmpty()) {
            String current = pq.poll();

            if (visited.contains(current))
                continue;
            visited.add(current);

            if (current.equals(goal))
                break;

            for (Edge neighbor : graph.getNeighbors(current)) {
                if (!visited.contains(neighbor.city)) {
                    int newDist = distances.get(current) + (int) neighbor.distance;

                    if (newDist < distances.get(neighbor.city)) {
                        distances.put(neighbor.city, newDist);
                        previous.put(neighbor.city, current);
                        pq.add(neighbor.city);
                    }
                }
            }
        }

        // Reconstruct path
        List<String> path = new ArrayList<>();
        String current = goal;

        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }

        // Check if path is valid
        if (path.size() <= 1 || !path.get(0).equals(start)) {
            path.clear();
        }

        long endTime = System.nanoTime();

        if (!path.isEmpty()) {
            int totalDistance = distances.get(goal);
            printResults("Dijkstra", path, totalDistance, endTime - startTime);
            return path;
        } else {
            System.out.println("Dijkstra - No path found from " + start + " to " + goal);
            System.out.println("Execution time: " + (endTime - startTime) + " ns");
            return new ArrayList<>();
        }
    }

    private List<String> reconstructPath(Map<String, String> parent, String start, String goal) {
        List<String> path = new ArrayList<>();
        String current = goal;

        while (current != null) {
            path.add(0, current);
            current = parent.get(current);
        }

        if (!path.get(0).equals(start)) {
            return new ArrayList<>();
        }

        return path;
    }

    private int calculatePathDistance(List<String> path) {
        if (path == null || path.size() < 2) {
            return 0;
        }

        int total = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            String city1 = path.get(i);
            String city2 = path.get(i + 1);

            // Find the distance between these two cities
            for (Edge edge : graph.getNeighbors(city1)) {
                if (edge.city.equals(city2)) {
                    total += edge.distance;
                    break;
                }
            }
        }
        return total;
    }

    private void printResults(String algorithm, List<String> path, int distance, long time) {
        System.out.println("\n" + algorithm + " Results:");
        System.out.println("Path: " + String.join(" -> ", path));
        System.out.println("Total Distance: " + distance + " km");
        System.out.println("Execution Time: " + time + " ns (" + (time / 1000000.0) + " ms)");
    }

    /**
     * Run comparative analysis on 8 city pairs
     */
    public void runComparativeAnalysis() {
        System.out.println("\n========== COMPARATIVE ANALYSIS ==========");
        System.out.println("Running all three algorithms on 8 city pairs...\n");

        // Define 8 city pairs (non-directly connected)
        String[][] cityPairs = {
                { "Istanbul", "Diyarbakir" },
                { "Izmir", "Trabzon" },
                { "Bursa", "Batman" },
                { "Adana", "Denizli" },
                { "Gaziantep", "Samsun" },
                { "Konya", "Malatya" },
                { "Antalya", "Urfa" },
                { "Mersin", "Kayseri" }
        };

        // Results storage
        List<ComparisonResult> results = new ArrayList<>();

        // Run algorithms for each pair
        for (int i = 0; i < cityPairs.length; i++) {
            String start = cityPairs[i][0];
            String goal = cityPairs[i][1];

            System.out.println("\n--- Pair " + (i + 1) + ": " + start + " to " + goal + " ---");

            // Run DFS
            long dfsStart = System.nanoTime();
            List<String> dfsPath = dfs(start, goal);
            long dfsTime = System.nanoTime() - dfsStart;
            int dfsDist = calculatePathDistance(dfsPath);

            // Run Modified DFS
            long modDfsStart = System.nanoTime();
            List<String> modDfsPath = modifiedDFS(start, goal);
            long modDfsTime = System.nanoTime() - modDfsStart;
            int modDfsDist = calculatePathDistance(modDfsPath);

            // Run Dijkstra
            long dijkstraStart = System.nanoTime();
            List<String> dijkstraPath = dijkstra(start, goal);
            long dijkstraTime = System.nanoTime() - dijkstraStart;
            int dijkstraDist = calculatePathDistance(dijkstraPath);

            // Store results
            results.add(new ComparisonResult(
                    start, goal,
                    dfsDist, dfsTime,
                    modDfsDist, modDfsTime,
                    dijkstraDist, dijkstraTime));
        }

        // Print summary table
        printSummaryTable(results);

        // Print complexity analysis
        printComplexityAnalysis();
    }

    private void printSummaryTable(List<ComparisonResult> results) {
        System.out.println("\n========== SUMMARY TABLE ==========");
        System.out.println("+----+---------------------+-----------------+-----------------+-----------------+");
        System.out.println("| #  | City Pair           | DFS Distance    | Mod DFS Distance| Dijkstra Distance|");
        System.out.println("+----+---------------------+-----------------+-----------------+-----------------+");

        for (int i = 0; i < results.size(); i++) {
            ComparisonResult r = results.get(i);
            System.out.printf("| %-2d | %-19s | %-15d | %-15d | %-16d |\n",
                    i + 1, r.start + "-" + r.goal,
                    r.dfsDistance, r.modDfsDistance, r.dijkstraDistance);
        }
        System.out.println("+----+---------------------+-----------------+-----------------+-----------------+");

        System.out.println("\n========== RUNTIME COMPARISON (ms) ==========");
        System.out.println("+----+---------------------+------------+----------------+-----------------+");
        System.out.println("| #  | City Pair           | DFS Time   | Mod DFS Time   | Dijkstra Time   |");
        System.out.println("+----+---------------------+------------+----------------+-----------------+");

        for (int i = 0; i < results.size(); i++) {
            ComparisonResult r = results.get(i);
            System.out.printf("| %-2d | %-19s | %-10.3f | %-14.3f | %-15.3f |\n",
                    i + 1, r.start + "-" + r.goal,
                    r.dfsTime / 1000000.0, r.modDfsTime / 1000000.0, r.dijkstraTime / 1000000.0);
        }
        System.out.println("+----+---------------------+------------+----------------+-----------------+");
    }

    private void printComplexityAnalysis() {
        System.out.println("\n========== TIME COMPLEXITY ANALYSIS ==========");
        System.out.println("1. DFS Algorithm:");
        System.out.println("   - Theoretical: O(V + E)");
        System.out.println("   - Space: O(V)");
        System.out.println("   - Finds a path, not necessarily shortest");

        System.out.println("\n2. Modified DFS Algorithm:");
        System.out.println("   - Theoretical: O(V!) worst case (explores all paths)");
        System.out.println("   - Space: O(V) for recursion stack");
        System.out.println("   - Guarantees shortest path but inefficient for large graphs");

        System.out.println("\n3. Dijkstra's Algorithm:");
        System.out.println("   - Theoretical: O((V + E) log V) with priority queue");
        System.out.println("   - Space: O(V)");
        System.out.println("   - Guarantees shortest path, efficient for weighted graphs");

        System.out.println("\nWhere V = vertices (cities), E = edges (connections)");
    }

    // Inner class to store comparison results
    private static class ComparisonResult {
        String start, goal;
        int dfsDistance, modDfsDistance, dijkstraDistance;
        long dfsTime, modDfsTime, dijkstraTime;

        ComparisonResult(String start, String goal,
                int dfsDist, long dfsTime,
                int modDfsDist, long modDfsTime,
                int dijkstraDist, long dijkstraTime) {
            this.start = start;
            this.goal = goal;
            this.dfsDistance = dfsDist;
            this.modDfsDistance = modDfsDist;
            this.dijkstraDistance = dijkstraDist;
            this.dfsTime = dfsTime;
            this.modDfsTime = modDfsTime;
            this.dijkstraTime = dijkstraTime;
        }
    }
}