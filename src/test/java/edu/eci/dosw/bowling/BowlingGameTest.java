package edu.eci.dosw.bowling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BowlingGameTest {

    @Test
    @DisplayName("A1: roll(0) - primer tiro a cero - no lanza excepcion y registra 0 pinos")
    void rollZero_doesNotThrow_andRegistersZeroPins() {
        BowlingGame game = new BowlingGame();

        assertDoesNotThrow(() -> game.roll(0));

        assertEquals(1, game.getFrames().size());
        assertEquals(0, game.getFrames().get(0).getRolls().get(0));
    }
}
