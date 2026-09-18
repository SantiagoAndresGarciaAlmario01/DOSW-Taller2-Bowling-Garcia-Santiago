package edu.eci.dosw.bowling;

import java.util.List;

public class BowlingScorer {
    public int calculate(List<Frame> frames) {
        int total = 0;
        for (int i = 0; i < frames.size(); i++) {
            Frame frame = frames.get(i);
            List<Integer> rolls = frame.getRolls();

            if (frame.isSpare()) {
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
        if (index + 1 >= frames.size()) {
            return 0;
        }
        return frames.get(index + 1).getRolls().get(0);
    }
}