package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {

    private final List<Frame> frames;
    private final BowlingScorer scorer;

    public BowlingGame() {
        this.frames = new ArrayList<>();
        this.scorer = new BowlingScorer();
    }

    public void roll(int pins) {
        if (isComplete()) {
            throw new IllegalStateException("el juego ya esta completo");
        }
        validatePinsRange(pins);

        if (isCurrentFrameOpen()) {
            Frame current = lastFrame();
            validateFrameSum(current, pins);
            current.addRoll(pins);
        } else {
            Frame frame = new Frame();
            frame.addRoll(pins);
            frames.add(frame);
        }
    }

    public int score() {
        if (!isComplete()) {
            throw new IllegalStateException("el juego no esta completo");
        }
        return scorer.calculate(frames);
    }

    public boolean isComplete() {
        return frames.size() >= 10 && !isCurrentFrameOpen();
    }

    public List<Frame> getFrames() {
        return List.copyOf(frames);
    }

    private void validatePinsRange(int pins) {
        if (pins < 0 || pins > 10) {
            throw new IllegalArgumentException("pins fuera de rango: " + pins);
        }
    }

    private void validateFrameSum(Frame frame, int pins) {
        if (frame.getRolls().size() == 1 && !frame.isStrike()) {
            int firstRoll = frame.getRolls().get(0);
            if (firstRoll + pins > 10) {
                throw new IllegalArgumentException(
                    "suma del frame excede 10: " + (firstRoll + pins));
            }
        }
    }

    private boolean isCurrentFrameOpen() {
        if (frames.isEmpty()) {
            return false;
        }
        Frame last = lastFrame();
        if (frames.size() < 10) {
            return last.getRolls().size() == 1 && !last.isStrike();
        }
        return isTenthFrameOpen(last);
    }

    private boolean isTenthFrameOpen(Frame frame) {
        int rollCount = frame.getRolls().size();
        if (frame.isStrike()) {
            return rollCount < 3;
        }
        if (rollCount == 1) {
            return true;
        }
        if (frame.isSpare()) {
            return rollCount < 3;
        }
        return false;
    }

    private Frame lastFrame() {
        return frames.get(frames.size() - 1);
    }
}