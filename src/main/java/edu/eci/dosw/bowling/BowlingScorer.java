package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

public class BowlingScorer {
    public int calculate(List<Frame> frames) {
        int total = 0;
        for (int i = 0; i < frames.size(); i++) {
            Frame frame = frames.get(i);
            List<Integer> rolls = frame.getRolls();

            if (frame.isStrike()) {
                total += 10 + nextTwoRolls(frames, i);
            } else if (frame.isSpare()) {
                total += 10 + firstRollOfNextFrame(frames, i);
            } else {
                total += sum(rolls);
            }
        }
        return total;
    }

    private int sum(List<Integer> rolls) {
        int total = 0;
        for (int roll : rolls) {
            total += roll;
        }
        return total;
    }

    private int firstRollOfNextFrame(List<Frame> frames, int index) {
        if (index + 1 < frames.size()) {
            return frames.get(index + 1).getRolls().get(0);
        }
        List<Integer> rolls = frames.get(index).getRolls();
        if (rolls.size() >= 3) {
            return rolls.get(2);
        }
        return 0;
    }

    private int nextTwoRolls(List<Frame> frames, int index) {
        List<Integer> upcoming = new ArrayList<>();
        for (int j = index + 1; j < frames.size() && upcoming.size() < 2; j++) {
            for (int roll : frames.get(j).getRolls()) {
                if (upcoming.size() < 2) {
                    upcoming.add(roll);
                }
            }
        }
        int total = 0;
        for (int roll : upcoming) {
            total += roll;
        }
        return total;
    }
}