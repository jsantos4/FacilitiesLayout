package FacilitiesLayout;

public class Main {
    private final static int total = 2;
    private static int rows = 10, columns = 10;

    private static Floor ground = new Floor(rows, columns);
    private static Floor previousBest = new Floor(ground, -1);

    public static void main(String args[]) {
        System.out.println("Affinity: " + ground.getTotalAffinity());

        Floor[] floors = new Floor[total];
        Generation[] generations = new Generation[total];

        for (int i = 0; i < total; i++) {
            floors[i] = new Floor(ground, i);
        }

        loadGenerations(floors, generations);

        int genCount = 0;
        while (previousBest.getTotalAffinity() > (rows * columns) * 320) {
        //for (int j = 0; j < 1000; j++) {
            System.out.println("Generation: " + ++genCount);
            System.out.println("Affinity: " + previousBest.getTotalAffinity());
            for (int i = 0; i < total; i++){
                generations[i].swap();
            }

            Floor temp = new Floor(bestFloor(floors), -1);

            if(temp.getTotalAffinity() < previousBest.getTotalAffinity()) {
                loadFloors(floors, temp);
                loadGenerations(floors, generations);
                previousBest = new Floor(temp, -1);
            } else {
                loadFloors(floors, previousBest);
                loadGenerations(floors, generations);
            }
        }

        System.out.println("Final affinity: " + previousBest.getTotalAffinity());
    }

    private static void loadFloors(Floor[] floors, Floor best) {
        for (int i = 0; i < total; i++) {
            floors[i] = new Floor(best, i);
        }
    }

    private static Floor bestFloor(Floor[] floors) {
        int bestIndex = 0;
        for (int i = 0; i < floors.length; i++) {
            if (floors[bestIndex].getTotalAffinity() > floors[i].getTotalAffinity()) {
                bestIndex = i;
            }
        }
        return floors[bestIndex];
    }

    private static void loadGenerations(Floor[] floors, Generation[] generations) {
        for (int i = 0; i < total; i++) {
            generations[i] = new Generation(floors[i]);
        }
    }


}
