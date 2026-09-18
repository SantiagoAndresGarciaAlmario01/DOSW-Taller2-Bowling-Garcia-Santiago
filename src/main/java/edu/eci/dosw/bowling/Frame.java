package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    private final List<Integer> rolls = new ArrayList<>();

    public void addRoll(int pins) {
        rolls.add(pins);
    }

    public List<Integer> getRolls() {
        return List.copyOf(rolls);
    }

    public boolean isStrike() {
        return rolls.size() == 1 && rolls.get(0) == 10;
    }
}