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

        //Test 4: Copies have same hashcode
        Pente game4 = new Pente();
        game4.makeMove(new Position(2,2));
        game4.makeMove(new Position(2,3));
        game4.makeMove(new Position(7,7));
        game4.makeMove(new Position(2,4));
        game4.makeMove(new Position(2,5));
        Pente game5 = new Pente(game4);
        assertEquals(game4.hashCode(), game5.hashCode());

        //Test 5: Order of moves does not matter if spots on the board are same
        Pente game6 = new Pente();
        Pente game7 = new Pente();

        assertEquals(game6.hashCode(),game7.hashCode());
        game6.makeMove(new Position(7,7)); //1
        game6.makeMove(new Position(2,2)); //2
        game6.makeMove(new Position(2,3)); //1
        game6.makeMove(new Position(2,4)); //2
        game6.makeMove(new Position(2,5)); //1

        game7.makeMove(new Position(2,3)); //1
        game7.makeMove(new Position(2,2)); //2
        game7.makeMove(new Position(2,5)); //1
        game7.makeMove(new Position(2,4)); //2
        game7.makeMove(new Position(7,7)); //1
        assertEquals(game6.hashCode(),game7.hashCode());

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
        //Test 1: new game
        Pente game1 = new Pente();
        assertEquals(PlayerRole.FIRST_PLAYER, game1.currentPlayer());
        assertEquals(1, game1.currentTurn());
        assertFalse(game1.hasEnded());
        assertEquals(0, game1.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game1.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        assertEquals(PlayerRole.FIRST_PLAYER.boardValue(), 1);

        //Test 2: Filling in empty spaces does not remove pieces
        game1.makeMove(new Position(5,5));
        game1.makeMove(new Position(5,4));
        game1.makeMove(new Position(5,2));
        game1.makeMove(new Position(5,3));
        assertEquals(0, game1.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game1.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        assertEquals(5, game1.currentTurn());
        assertFalse(game1.hasEnded());

        //Test 3: Doesn't make moves if position is not on board
        assertFalse(game1.makeMove(new Position(10,10)));
        assertFalse(game1.makeMove(new Position(5,5)));
        game1.makeMove(new Position(8,8));
        game1.makeMove(new Position(3,3));
        assertEquals(0, game1.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game1.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        assertEquals(6, game1.currentTurn());
        assertEquals(PlayerRole.SECOND_PLAYER, game1.currentPlayer());
        assertFalse(game1.hasEnded());
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

        //Test 1: Capture Right and Left
        Pente game1 = new Pente();
        game1.makeMove(new Position(4,7));
        game1.makeMove(new Position(4,5));
        game1.makeMove(new Position(4,1));
        game1.makeMove(new Position(4,6));
        game1.makeMove(new Position(7,7));
        game1.makeMove(new Position(4,2));
        game1.makeMove(new Position(0,0));
        game1.makeMove(new Position(4,3));
        game1.makeMove(new Position(4,4)); //Double Capture
        assertEquals(2, game1.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(0, game1.capturedPairsNo(PlayerRole.SECOND_PLAYER));

        //Test 2: Capture Up and Down
        Pente game2 = new Pente();
        game2.makeMove(new Position(1,2));
        game2.makeMove(new Position(4,0));
        game2.makeMove(new Position(4,1));
        game2.makeMove(new Position(4,6));
        game2.makeMove(new Position(4,2));
        game2.makeMove(new Position(7,7));
        game2.makeMove(new Position(4,4));
        game2.makeMove(new Position(0,0));
        game2.makeMove(new Position(4,5));
        game2.makeMove(new Position(4,3));
        assertEquals(0, game2.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(2, game2.capturedPairsNo(PlayerRole.SECOND_PLAYER));
        //Test 3: Capture Diagonally
        Pente game3 = new Pente();
        game3.makeMove(new Position(1,7));
        game3.makeMove(new Position(7,1));
        game3.makeMove(new Position(6,2));
        game3.makeMove(new Position(3,5));
        game3.makeMove(new Position(0,0));
        game3.makeMove(new Position(6,2));
        game3.makeMove(new Position(0,1));
        game3.makeMove(new Position(5,3));
        game3.makeMove(new Position(4,4));
        assertEquals(0, game3.capturedPairsNo(PlayerRole.FIRST_PLAYER));
        assertEquals(1, game3.capturedPairsNo(PlayerRole.SECOND_PLAYER));
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
        //Test 2: 5 in a col
        Pente game1 = new Pente();
        assertFalse(game1.hasEnded());
        game1.makeMove(new Position(0, 0));
        game1.makeMove(new Position(1, 5));
        game1.makeMove(new Position(1, 0));
        game1.makeMove(new Position(0, 3));
        game1.makeMove(new Position(2, 0));
        game1.makeMove(new Position(0, 5));
        game1.makeMove(new Position(3, 0));
        game1.makeMove(new Position(2, 7));
        game1.makeMove(new Position(4, 0));
        assertTrue(game1.hasEnded());

        //Test 3: 5 in a diagonal
        Pente game3 = new Pente();

        game3.makeMove(new Position(0, 0));
        game3.makeMove(new Position(0, 4));
        game3.makeMove(new Position(1, 1));
        game3.makeMove(new Position(0, 2));
        game3.makeMove(new Position(2, 2));
        game3.makeMove(new Position(0, 7));
        game3.makeMove(new Position(3, 3));
        assertFalse(game3.hasEnded());
        game3.makeMove(new Position(7, 7));
        game3.makeMove(new Position(4, 4));
        assertTrue(game3.hasEnded());

        //Test 4: Captured 5 pairs
        Pente game2 = new Pente();
        game2.makeMove(new Position(1,2));
        game2.makeMove(new Position(4,0));
        game2.makeMove(new Position(4,1));
        game2.makeMove(new Position(4,6));
        game2.makeMove(new Position(4,2));
        game2.makeMove(new Position(7,7));
        game2.makeMove(new Position(4,4));
        game2.makeMove(new Position(0,0));
        game2.makeMove(new Position(4,5));
        game2.makeMove(new Position(4,3));
        game2.makeMove(new Position(3,3));
        game2.makeMove(new Position(1,6));
        game2.makeMove(new Position(2,3));
        game2.makeMove(new Position(1,3));
        game2.makeMove(new Position(2,3));
        game2.makeMove(new Position(0,1));
        game2.makeMove(new Position(0,6));
        game2.makeMove(new Position(3,4));
        game2.makeMove(new Position(0,5));
        assertFalse(game2.hasEnded());
        game2.makeMove(new Position(0,7));
        game2.makeMove(new Position(0,2));
        game2.makeMove(new Position(0,4));
        assertTrue(game2.hasEnded());
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

        //Test 3: Copies are equal
        Pente g = new Pente();
        Pente g2 = new Pente(g);
        assertTrue(g.stateEqual(g2));
        assertTrue(g2.stateEqual(g));

        //Test 4: Same spots occupied but by different players
        Pente g3 = new Pente();
        Pente g4 = new Pente();
        g3.makeMove(new Position(3, 4));
        g3.makeMove(new Position(4, 3));
        g4.makeMove(new Position(4, 3));
        g4.makeMove(new Position(3, 4));
        assertFalse(g3.stateEqual(g4));
        assertFalse(g4.stateEqual(g3));

        //Test 5: Test captured pieces
        Pente game4 = new Pente();
        game4.makeMove(new Position(2,2));
        game4.makeMove(new Position(2,3));
        game4.makeMove(new Position(7,7));
        game4.makeMove(new Position(2,4));
        game4.makeMove(new Position(2,5));
        Pente game5 = new Pente();
        game5.makeMove(new Position(2,2));
        game5.makeMove(new Position(2,3));
        game5.makeMove(new Position(7,7));
        game5.makeMove(new Position(2,4));
        game5.makeMove(new Position(2,5));
        assertTrue(game5.stateEqual(game4));

    }
}