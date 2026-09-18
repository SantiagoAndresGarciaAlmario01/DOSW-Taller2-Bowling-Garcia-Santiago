package edu.eci.dosw.bowling;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BowlingGameTest {

    @Test
    @DisplayName("A1: roll(0) - primer tiro a cero - no lanza excepcion y registra 0 pinos")
    void rollZero_doesNotThrow_andRegistersZeroPins() {
        BowlingGame game = new BowlingGame();

        assertDoesNotThrow(() -> game.roll(0));

        assertEquals(1, game.getFrames().size());
        assertEquals(0, game.getFrames().get(0).getRolls().get(0));
    }

    @Test
    @DisplayName("A2: roll(-1) - valor negativo - lanza IllegalArgumentException")
    void rollNegativePins_throwsException() {
        BowlingGame game = new BowlingGame();

        assertThrows(
            IllegalArgumentException.class,
            () -> game.roll(-1)
        );
    }

    @Test
    @DisplayName("A3: roll(11) - valor mayor a 10 - lanza IllegalArgumentException")
    void rollPinsAboveTen_throwsException() {
        BowlingGame game = new BowlingGame();

        assertThrows(
            IllegalArgumentException.class,
            () -> game.roll(11)
        );
    }

    @Test
    @DisplayName("A4: dos tiros en un frame suman mas de 10 - lanza IllegalArgumentException en el segundo tiro")
    void twoRollsInFrame_sumGreaterThanTen_throwsException() {
        BowlingGame game = new BowlingGame();
        game.roll(7);

        assertThrows(
            IllegalArgumentException.class,
            () -> game.roll(6)
        );
    }

    @Test
    @DisplayName("A5: roll() cuando el juego ya esta completo - lanza IllegalStateException")
    void rollAfterGameComplete_throwsException() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 10; i++) {
            game.roll(3);
            game.roll(4);
        }

        assertThrows(
            IllegalStateException.class,
            () -> game.roll(2)
        );
    }

    @Test
    @DisplayName("A6: roll(10) - detecta strike y avanza directamente al siguiente frame")
    void rollTen_marksStrike_andAdvancesToNextFrame() {
        BowlingGame game = new BowlingGame();
        game.roll(10);

        assertTrue(game.getFrames().get(0).isStrike());

        game.roll(5);
        assertEquals(2, game.getFrames().size());
    }

    @Test
    @DisplayName("A7: roll(5) + roll(5) - detecta spare")
    void twoRollsSumTen_marksSpare() {
        BowlingGame game = new BowlingGame();
        game.roll(5);
        game.roll(5);

        assertTrue(game.getFrames().get(0).isSpare());
    }

    @Test
    @DisplayName("A8: frame 10 con strike acepta hasta 3 tiros sin lanzar excepcion")
    void tenthFrameWithStrike_acceptsThreeRolls() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 9; i++) {
            game.roll(3);
            game.roll(4);
        }

        assertDoesNotThrow(() -> {
            game.roll(10);
            game.roll(5);
            game.roll(3);
        });

        assertEquals(10, game.getFrames().size());
        assertEquals(3, game.getFrames().get(9).getRolls().size());
    }

    @Test
    @DisplayName("C1: isComplete() al inicio del juego - false")
    void isComplete_atStart_returnsFalse() {
        BowlingGame game = new BowlingGame();

        assertFalse(game.isComplete());
    }

    @Test
    @DisplayName("C2: isComplete() despues de 9 frames completos - false")
    void isComplete_afterNineFrames_returnsFalse() {
        BowlingGame game = new BowlingGame();
        for (int i = 0; i < 9; i++) {
            game.roll(3);
            game.roll(4);
        }

        assertFalse(game.isComplete());
    }
}