package a5.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import a5.util.PlayerRole;
import org.junit.jupiter.api.Test;

class PenteTest {
    @Test
    void testConstructor() {
        // TODO 1: write at least 1 test case
        Pente p1 = new Pente();
        Pente p2 = new Pente();
        assertTrue(p1.stateEqual(p2));
        assertEquals(p1.capturedPairsNo(PlayerRole.FIRST_PLAYER),0);
        assertEquals(p1.capturedPairsNo(PlayerRole.SECOND_PLAYER),0);
        assertEquals(p1.countToWin(),5);
        assertEquals(p1.rowSize(),8);
        assertEquals(p1.colSize(),8);
    }

    @Test
    void testCopyConstructor() {
        // test case 1: can a board state be copied to an equal state
        Pente game1 = new Pente();
        game1.makeMove(new Position(2, 2));
        Pente game2 = new Pente(game1);
        assertTrue(game1.stateEqual(game2));

        // TODO 2: write at least 3 test cases
        //Test 2: Not Equals
        Pente game3 = new Pente();
        assertFalse(game3.stateEqual(game1));
        assertFalse(game3.stateEqual(game2));

        //Test 3: same board if same spaces are occupied by same players
        game3.makeMove(new Position(2,2));
        game3.makeMove(new Position(7,7));
        game1 = new Pente(game3);
        assertTrue(game3.stateEqual(game1));
        assertEquals(game1.capturedPairsNo(PlayerRole.FIRST_PLAYER),0);
        assertEquals(game1.capturedPairsNo(PlayerRole.SECOND_PLAYER),0);

        //Test 4: Test captured pieces are copied to new game
        Pente game4 = new Pente();
        game4.makeMove(new Position(2,2));
        game4.makeMove(new Position(2,3));
        game4.makeMove(new Position(7,7));
        game4.makeMove(new Position(2,4));
        game4.makeMove(new Position(2,5));
        Pente game5 = new Pente(game4);
        assertEquals(game5.capturedPairsNo(PlayerRole.FIRST_PLAYER),1);
        assertEquals(game5.capturedPairsNo(PlayerRole.SECOND_PLAYER),0);


    }

    @Test
    void testHashCode() {
        Pente game1 = new Pente();
        Pente game2 = new Pente();
        Pente game3 = new Pente();

        // test case 1: do two equal nonempty board states have the same hash code
        game1.makeMove(new Position(3, 3));
        game2.makeMove(new Position(3, 3));
        assertEquals(game1.hashCode(), game2.hashCode());

        // test case 2: non-equal board states should be very unlikely to have the
        // same hash code.
        game3.makeMove(new Position(0, 0));
        assertNotEquals(game1.hashCode(), game3.hashCode());

        // TODO 3: write at least 3 test cases
        // Test 3: Same spots occupied but by different players
        game1.makeMove(new Position(4,5));
        game1.makeMove(new Position(5,4));
        game2.makeMove(new Position(5,4));
        game2.makeMove(new Position(4,5));
        assertNotEquals(game1.hashCode(), game2.hashCode());

        //Test 4:

        //Test 5:

    }

    @Test
    void makeMove() {
        // test case 1: a simple move
        Pente game = new Pente();
        Position p = new Position(2, 2);
        game.makeMove(p); // a move by the first player
        assertEquals(PlayerRole.SECOND_PLAYER, game.currentPlayer());
        assertEquals(2, game.currentTurn());
        assertFalse(game.hasEnded());
        assertEquals(0, game.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        assertEquals(PlayerRole.FIRST_PLAYER.boardValue(), game.board().get(p));

        // test case 2: try a capture
        game.makeMove(new Position(2, 3)); // a move by the second player
        game.makeMove(new Position(3, 2)); // a move by the first player
        game.makeMove(new Position(2, 4)); // a move by the second player
        game.makeMove(new Position(2, 5)); // a move by the first player, which should capture the pair [(2, 3), (2, 4)]
        assertEquals(1, game.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game.board().get(new Position(2, 3))); // the stone should be removed
        assertEquals(0, game.board().get(new Position(2, 4))); // the stone should be removed

        // TODO 4: write at least 3 test cases
    }

    @Test
    void capturedPairsNo() {
        // test case 1: are captured pairs registered?
        Pente game = new Pente();
        game.makeMove(new Position(3, 2)); // a move by the first player
        game.makeMove(new Position(3, 3)); // a move by the second player
        game.makeMove(new Position(4, 2)); // a move by the first player
        game.makeMove(new Position(3, 4)); // a move by the second player
        game.makeMove(new Position(3, 5)); // a move by the first player, which should capture the pair [(2, 3), (2, 4)]
        assertEquals(1, game.capturedPairsNo(PlayerRole.FIRST_PLAYER));

        // TODO 5: write at least 3 test cases
    }

    @Test
    void hasEnded() {
        // test case 1: is a board with 5 in a row an ended game?
        Pente game = new Pente();
        assertFalse(game.hasEnded());
        game.makeMove(new Position(1, 1)); // a move by the first player
        game.makeMove(new Position(2, 1)); // a move by the second player
        game.makeMove(new Position(1, 2)); // a move by the first player
        game.makeMove(new Position(2, 2)); // a move by the second player
        game.makeMove(new Position(1, 3)); // a move by the first player
        game.makeMove(new Position(2, 3)); // a move by the second player
        game.makeMove(new Position(1, 4)); // a move by the first player
        game.makeMove(new Position(2, 4)); // a move by the second player
        game.makeMove(new Position(1, 5)); // a move by the first player
        assertTrue(game.hasEnded());

        // TODO 6: write at least 3 test cases
    }

    @Test
    void stateEqual() {
        Pente game1 = new Pente();
        Pente game2 = new Pente();
        Pente game3 = new Pente();

        // test 1: games with equal board states should be stateEqual()
        game1.makeMove(new Position(3, 3));
        game2.makeMove(new Position(3, 3));
        assertTrue(game1.stateEqual(game2));
        assertTrue(game2.stateEqual(game1));

        // test 2: games with unequal board states should not be stateEqual()
        game3.makeMove(new Position(0, 0));
        assertFalse(game1.stateEqual(game3));
        // TODO 7: write at least 3 test cases
    }
}