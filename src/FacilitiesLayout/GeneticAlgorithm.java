package FacilitiesLayout;

public class GeneticAlgorithm {
/*
    private Generation generation;

    GeneticAlgorithm(Generation generation) {
        this.generation = generation;
    }

    private static void loadFloors(Floor[] floors, Floor best) {
        for (int i = 0; i < 2; i++) {
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
        for (int i = 0; i < 2; i++) {
            generations[i] = new Generation(floors[i]);
        }
    }


    public void run() {

        Floor previousBest = new Floor(generation.ground, -1);

        System.out.println("Affinity: " + generation.ground.getTotalAffinity());

        Floor[] floors = new Floor[2];
        Generation[] generations = new Generation[2];

        for (int i = 0; i < 2; i++) {
            floors[i] = new Floor(generation.ground, i);
        }

        loadGenerations(floors, generations);

        int genCount = 0;
        while (previousBest.getTotalAffinity() > 35000) {       //Find reasonable threshold for n-by-m sized grid
            System.out.println("Generation: " + ++genCount);
            System.out.println("Affinity: " + previousBest.getTotalAffinity());

            for (int i = 0; i < 2; i++){
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
*/
}
