package edu.eci.dosw.bowling;

import java.util.ArrayList;
import java.util.List;

public class BowlingGame {

    private final List<Frame> frames;
    private int currentFrame;

    public BowlingGame() {
        this.frames = new ArrayList<>();
        this.currentFrame = 0;
    }

    public void roll(int pins) {
        if (pins < 0 || pins > 10) {
            throw new IllegalArgumentException("pins fuera de rango: " + pins);
        }
        Frame frame = new Frame();
        frame.addRoll(pins);
        frames.add(frame);
    }

    public int score() {
        return 0;
    }

    public boolean isComplete() {
        return false;
    }

    public List<Frame> getFrames() { return List.copyOf(frames); }
}