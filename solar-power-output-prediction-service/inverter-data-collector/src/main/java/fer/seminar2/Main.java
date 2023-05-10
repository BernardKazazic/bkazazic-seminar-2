package fer.seminar2;

import core.DataCollector;

public class Main {
    public static void main(String[] args) throws Exception {
        DataCollector dataCollector = new DataCollector();
        float ACPower = dataCollector.getCurrentACPower();
        System.out.println("Current AC power: " + ACPower + "W");
    }
}