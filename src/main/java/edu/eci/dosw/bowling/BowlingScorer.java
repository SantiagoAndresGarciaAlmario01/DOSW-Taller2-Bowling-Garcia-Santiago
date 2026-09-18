package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

public class BowlingScorer {

    public int calculate(List<Frame> frames) {
        int total = 0;
        for (int i = 0; i < frames.size(); i++) {
            total += frameScore(frames, i);
        }
        return total;
    }

    private int frameScore(List<Frame> frames, int index) {
        Frame frame = frames.get(index);
        boolean isLastFrame = index == frames.size() - 1;

        if (isLastFrame) {
            return sum(frame.getRolls());
        }
        if (frame.isStrike()) {
            return 10 + nextTwoRolls(frames, index);
        }
        if (frame.isSpare()) {
            return 10 + firstRollOfNextFrame(frames, index);
        }
        return sum(frame.getRolls());
    }

    private int firstRollOfNextFrame(List<Frame> frames, int index) {
        return frames.get(index + 1).getRolls().get(0);
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
        return sum(upcoming);
    }

    private int sum(List<Integer> rolls) {
        int total = 0;
        for (int roll : rolls) {
            total += roll;
        }
        return total;
    }
}