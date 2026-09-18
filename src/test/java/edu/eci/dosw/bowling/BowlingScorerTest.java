package edu.eci.dosw.bowling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BowlingScorerTest {

    private void rollMany(BowlingGame game, int times, int pins) {
        for (int i = 0; i < times; i++) {
            game.roll(pins);
        }
    }

    @Test
    @DisplayName("B1: juego con todos los tiros a 0 - score debe ser 0")
    void allZeros_scoresZero() {
        BowlingGame game = new BowlingGame();
        rollMany(game, 20, 0);

        assertEquals(0, game.score());
    }

    @Test
    @DisplayName("B2: juego sin strikes ni spares - score es la suma directa de los pinos")
    void noStrikesNoSpares_scoresSumOfPins() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 10; i++) {
            game.roll(3);
            game.roll(4);
        }

        assertEquals(70, game.score());
    }

    @Test
    @DisplayName("B3: spare en frame 1 y siguiente tiro = 3 - frame 1 puntua 10+3=13")
    void spareInFirstFrame_addsNextRollAsBonus() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        game.roll(5);
        game.roll(3);
        game.roll(0);
        rollMany(game, 16, 0);
        assertEquals(16, game.score());
    }
}