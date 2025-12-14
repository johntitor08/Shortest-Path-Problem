import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Create and populate graph
        Graph graph = createGraph();

        Scanner scanner = new Scanner(System.in);
        PathFinder pathFinder = new PathFinder(graph);

        System.out.println("TURKISH CITIES SHORTEST PATH FINDER");
        System.out.println("====================================\n");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Find path between two cities");
            System.out.println("2. Run comparative analysis (8 city pairs)");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter starting city: ");
                    String start = scanner.nextLine();

                    System.out.print("Enter goal city: ");
                    String goal = scanner.nextLine();

                    if (graph.getCityList().contains(start) && graph.getCityList().contains(goal)) {
                        System.out.println("\nChoose algorithm:");
                        System.out.println("1. DFS (finds any path)");
                        System.out.println("2. Modified DFS (finds shortest path)");
                        System.out.println("3. Dijkstra (optimal shortest path)");
                        System.out.println("4. Run all three algorithms");
                        System.out.print("Enter choice: ");

                        int algoChoice = scanner.nextInt();
                        scanner.nextLine();

                        switch (algoChoice) {
                            case 1:
                                pathFinder.dfs(start, goal);
                                break;
                            case 2:
                                pathFinder.modifiedDFS(start, goal);
                                break;
                            case 3:
                                pathFinder.dijkstra(start, goal);
                                break;
                            case 4:
                                System.out.println("\n--- Running all algorithms ---");
                                pathFinder.dfs(start, goal);
                                pathFinder.modifiedDFS(start, goal);
                                pathFinder.dijkstra(start, goal);
                                break;
                            default:
                                System.out.println("Invalid choice");
                        }
                    } else {
                        System.out.println("Invalid city names!");
                    }
                    break;

                case 2:
                    pathFinder.runComparativeAnalysis();
                    break;

                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static Graph createGraph() {
        Graph graph = new Graph();

        // Add all cities
        String[] cities = {
                "Istanbul", "Ankara", "Izmir", "Bursa", "Adana",
                "Gaziantep", "Konya", "Diyarbakir", "Antalya",
                "Mersin", "Kayseri", "Urfa", "Malatya", "Samsun",
                "Denizli", "Batman", "Trabzon"
        };

        for (String city : cities) {
            graph.addCity(city);
        }

        // Complete edge setup from the Excel data
        // Istanbul connections
        graph.addEdge("Istanbul", "Ankara", 449);
        graph.addEdge("Istanbul", "Bursa", 153);
        graph.addEdge("Istanbul", "Konya", 645);
        graph.addEdge("Istanbul", "Antalya", 690);
        graph.addEdge("Istanbul", "Mersin", 956);
        graph.addEdge("Istanbul", "Kayseri", 776);
        graph.addEdge("Istanbul", "Samsun", 737);
        graph.addEdge("Istanbul", "Denizli", 649);

        // Ankara connections
        graph.addEdge("Ankara", "Izmir", 591);
        graph.addEdge("Ankara", "Bursa", 389);
        graph.addEdge("Ankara", "Adana", 484);
        graph.addEdge("Ankara", "Gaziantep", 705);
        graph.addEdge("Ankara", "Konya", 266);
        graph.addEdge("Ankara", "Diyarbakir", 1003);
        graph.addEdge("Ankara", "Antalya", 483);
        graph.addEdge("Ankara", "Mersin", 501);
        graph.addEdge("Ankara", "Kayseri", 317);
        graph.addEdge("Ankara", "Urfa", 848);
        graph.addEdge("Ankara", "Malatya", 682);
        graph.addEdge("Ankara", "Samsun", 402);
        graph.addEdge("Ankara", "Denizli", 483);
        graph.addEdge("Ankara", "Trabzon", 732);

        // Izmir connections
        graph.addEdge("Izmir", "Bursa", 333);
        graph.addEdge("Izmir", "Adana", 898);
        graph.addEdge("Izmir", "Gaziantep", 1118);
        graph.addEdge("Izmir", "Konya", 560);
        graph.addEdge("Izmir", "Antalya", 451);
        graph.addEdge("Izmir", "Mersin", 911);
        graph.addEdge("Izmir", "Kayseri", 874);
        graph.addEdge("Izmir", "Samsun", 1003);
        graph.addEdge("Izmir", "Denizli", 238);

        // Bursa connections
        graph.addEdge("Bursa", "Adana", 856);
        graph.addEdge("Bursa", "Gaziantep", 1075);
        graph.addEdge("Bursa", "Konya", 507);
        graph.addEdge("Bursa", "Antalya", 546);
        graph.addEdge("Bursa", "Mersin", 869);
        graph.addEdge("Bursa", "Kayseri", 715);
        graph.addEdge("Bursa", "Urfa", 1212);
        graph.addEdge("Bursa", "Malatya", 1049);
        graph.addEdge("Bursa", "Samsun", 750);
        graph.addEdge("Bursa", "Denizli", 480);
        graph.addEdge("Bursa", "Trabzon", 1091);

        // Adana connections
        graph.addEdge("Adana", "Gaziantep", 225);
        graph.addEdge("Adana", "Konya", 346);
        graph.addEdge("Adana", "Diyarbakir", 525);
        graph.addEdge("Adana", "Antalya", 649);
        graph.addEdge("Adana", "Mersin", 95);
        graph.addEdge("Adana", "Kayseri", 307);
        graph.addEdge("Adana", "Urfa", 369);
        graph.addEdge("Adana", "Malatya", 389);
        graph.addEdge("Adana", "Samsun", 719);
        graph.addEdge("Adana", "Denizli", 758);
        graph.addEdge("Adana", "Batman", 618);
        graph.addEdge("Adana", "Trabzon", 851);

        // Gaziantep connections
        graph.addEdge("Gaziantep", "Konya", 568);
        graph.addEdge("Gaziantep", "Diyarbakir", 315);
        graph.addEdge("Gaziantep", "Antalya", 785);
        graph.addEdge("Gaziantep", "Mersin", 311);
        graph.addEdge("Gaziantep", "Kayseri", 357);
        graph.addEdge("Gaziantep", "Urfa", 151);
        graph.addEdge("Gaziantep", "Malatya", 251);
        graph.addEdge("Gaziantep", "Samsun", 803);
        graph.addEdge("Gaziantep", "Denizli", 974);
        graph.addEdge("Gaziantep", "Batman", 409);
        graph.addEdge("Gaziantep", "Trabzon", 838);

        // Konya connections
        graph.addEdge("Konya", "Diyarbakir", 866);
        graph.addEdge("Konya", "Antalya", 303);
        graph.addEdge("Konya", "Mersin", 360);
        graph.addEdge("Konya", "Kayseri", 306);
        graph.addEdge("Konya", "Urfa", 702);
        graph.addEdge("Konya", "Malatya", 729);
        graph.addEdge("Konya", "Samsun", 663);
        graph.addEdge("Konya", "Denizli", 386);
        graph.addEdge("Konya", "Batman", 964);
        graph.addEdge("Konya", "Trabzon", 896);

        // Diyarbakir connections
        graph.addEdge("Diyarbakir", "Mersin", 610);
        graph.addEdge("Diyarbakir", "Kayseri", 571);
        graph.addEdge("Diyarbakir", "Urfa", 182);
        graph.addEdge("Diyarbakir", "Malatya", 235);
        graph.addEdge("Diyarbakir", "Samsun", 803);
        graph.addEdge("Diyarbakir", "Denizli", 1276);
        graph.addEdge("Diyarbakir", "Batman", 97);
        graph.addEdge("Diyarbakir", "Trabzon", 586);

        // Antalya connections
        graph.addEdge("Antalya", "Mersin", 631);
        graph.addEdge("Antalya", "Kayseri", 610);
        graph.addEdge("Antalya", "Urfa", 924);
        graph.addEdge("Antalya", "Denizli", 217);

        // Samsun connections
        graph.addEdge("Samsun", "Batman", 858);

        return graph;
    }
}