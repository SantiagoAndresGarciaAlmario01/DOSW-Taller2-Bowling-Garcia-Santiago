package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    private final List<Integer> rolls = new ArrayList<>();
    private boolean strike = false;

    public void addRoll(int pins) {
        rolls.add(pins);
        if (rolls.size() == 1 && pins == 10) {
            strike = true;
        }
    }

    public List<Integer> getRolls() {
        return List.copyOf(rolls);
    }

    public boolean isStrike() {
        return strike;
    }

    public boolean isSpare() {
        return !strike && rolls.size() >= 2 && rolls.get(0) + rolls.get(1) == 10;
    }
}