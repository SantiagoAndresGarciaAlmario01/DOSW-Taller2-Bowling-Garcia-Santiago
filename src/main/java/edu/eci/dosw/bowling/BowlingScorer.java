package edu.eci.dosw.bowling;

import java.util.List;

public class BowlingScorer {
    public int calculate(List<Frame> frames) {
        int total = 0;
        for (Frame frame : frames) {
            for (int roll : frame.getRolls()) {
                total += roll;
            }
        }
        return total;
    }
}