package net.demomaker.algorithm;

import java.util.HashMap;
import java.util.Map;

public class AlgorithmOutput {
    public int total = 0;
    public Map<String, Integer> counts = new HashMap<>();

    @Override
    public String toString() {
        StringBuilder countBuilder = new StringBuilder();
        countBuilder.append("Here is the count : \n");
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            countBuilder.append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue())
                    .append("\n");
        }
        countBuilder.append("\nTotal : ").append(total);
        return countBuilder.toString();
    }
}
