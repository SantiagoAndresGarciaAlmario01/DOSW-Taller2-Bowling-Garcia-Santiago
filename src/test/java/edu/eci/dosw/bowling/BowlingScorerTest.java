package edu.eci.dosw.bowling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    @DisplayName("B4: strike en frame 1 y roll(4)+roll(3) - frame 1 puntua 10+4+3=17")
    void strikeInFirstFrame_addsNextTwoRollsAsBonus() {
        BowlingGame game = new BowlingGame();
        game.roll(10);
        game.roll(4);
        game.roll(3);
        rollMany(game, 16, 0);

        assertEquals(24, game.score());
    }

    @Test
    @DisplayName("B5: dos strikes consecutivos y roll(5) - el bono del primer strike suma correctamente")
    void twoConsecutiveStrikes_firstStrikeBonusSumsAcrossFrames() {
        BowlingGame game = new BowlingGame();
        game.roll(10);
        game.roll(10);
        game.roll(5);
        game.roll(2);
        rollMany(game, 14, 0);

        assertEquals(49, game.score());
    }

    private void rollAllSpares(BowlingGame game, int lastBonus) {
        for (int i = 0; i < 10; i++) {
            game.roll(5);
            game.roll(5);
        }
        game.roll(lastBonus);
    }

    @Test
    @DisplayName("B6: todos spares y ultimo tiro = 5 - score debe ser 150")
    void allSpares_scores150() {
        BowlingGame game = new BowlingGame();
        rollAllSpares(game, 5);

        assertEquals(150, game.score());
    }

    private void rollPerfectGame(BowlingGame game) {
        for (int i = 0; i < 12; i++) {
            game.roll(10);
        }
    }

    @Test
    @DisplayName("B7: juego perfecto - 12 strikes - score debe ser 300")
    void perfectGame_scores300() {
        BowlingGame game = new BowlingGame();
        rollPerfectGame(game);

        assertEquals(300, game.score());
    }

    @Test
    @DisplayName("B8: score() antes de completar el juego - lanza IllegalStateException")
    void scoreBeforeGameComplete_throwsException() {
        BowlingGame game = new BowlingGame();
        game.roll(3);
        game.roll(4);

        assertThrows(
            IllegalStateException.class,
            () -> game.score()
        );
    }
}