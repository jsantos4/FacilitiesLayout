package FacilitiesLayout;

import java.util.ArrayList;
import java.util.Random;

class Floor {

    private Random random;
    private Station[][] stations;
    private int rows, columns, population, level;
    private ArrayList<Station> stationList;
    private ArrayList<int[]> posSwaps;
    private double affinity;

    Floor(int row, int col){
        this.rows = row;
        this.columns = col;
        affinity = 0;
        population = row*col;
        stationList = new ArrayList<>();
        posSwaps = new ArrayList<>();
        random = new Random();

        init();
    }

    Floor(Floor c, int num){
        this.rows = c.rows;
        this.columns = c.columns;
        this.population = c.population;
        this.stations = c.copyStations();
        stationList = new ArrayList<>();
        setStationList();
        calcTotalAffinity();
        random = new Random();
        this.posSwaps = new ArrayList<>();
        level = num;
    }

    //Initialize floor
    private void init() {
        stations = new Station[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                stations[i][j] = new Station();
                stations[i][j].setPosition(i, j);
            }
        }

        calcTotalAffinity();
        setStationList();
    }

    private Station[][] copyStations(){
        Station[][] temp = new Station[rows][columns];
        for(int r = 0; r < rows; r++){
            for(int c = 0; c < columns; c++){
                temp[r][c] = stations[r][c];
            }
        }
        return temp;
    }

    void calcTotalAffinity() {
        double totalAffinity = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++){
                ArrayList<Station> neighbors = getNeighbors(i, j);

                double localAffinity = 0;
                for (int k = 0; k < neighbors.size(); k++) {
                    localAffinity += Math.abs(stations[i][j].colorTotal- neighbors.get(k).colorTotal);
                }
                stations[i][j].affinity += localAffinity;
                totalAffinity += localAffinity;
            }
        }
        affinity = totalAffinity;
    }

    private ArrayList<Station> getNeighbors(int row, int column) {
        ArrayList<Station> temp = new ArrayList<>();
        int[] colCombos = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] rowCombos = {-1, 1, 0, 1, -1, -1, 0, 1};
        for(int i = 0; i < 8; i++){
            try{
                temp.add(stations[row+rowCombos[i]][column+colCombos[i]]);
            }
            catch(ArrayIndexOutOfBoundsException e){

            }
        }
        return temp;
    }

    //Randomly picks two stations to switch positions
    int[] randomStations(){
        int[] position = new int[4];
        do{
            for (int i = 0; i < 4; i++) {
                position[i] = random.nextInt(9);
            }

        }while(position[0] == position[2] || position[1] == position[3]);
        return position;
    }

    //Decide which stations to swap
    void generateSwaps() {
        posSwaps.clear();
        for (int i = 0; i < population/columns; i++) {
            posSwaps.add(randomStations());
        }
    }

    ArrayList<int[]> getPosSwaps() {
        return posSwaps;
    }

    void executeSwaps() {
        for (int[] swap : posSwaps) {
            Station temp = stations[swap[0]][swap[1]];
            stations[swap[0]][swap[1]] = stations[swap[2]][swap[3]];
            stations[swap[2]][swap[3]] = temp;
        }
        setStationPos();
    }

    private void setStationPos(){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                stations[i][j].setPosition(i, j);
            }
        }
        setStationList();
    }

    private void setStationList() {
        stationList.clear();

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                stationList.add(stations[i][j]);
            }
        }
    }

    int getRows() { return rows; }
    int getColumns() {return columns;}
    int getLevel() { return level; }

    ArrayList<Station> getStationList() { return stationList; }

    int getPopulation() { return population; }

    double getTotalAffinity() { return affinity; }
}
