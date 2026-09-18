package edu.eci.dosw.bowling;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
}