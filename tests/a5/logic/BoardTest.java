package a5.logic;

import static org.junit.jupiter.api.Assertions.*;

import a5.util.PlayerRole;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void testEquals() {
        Board board1 = new Board(3, 3);
        Board board2 = new Board(3, 3);

        // test 1: empty boards should be equal
        assertEquals(board1, board2);

        // test 2: adding a piece should break equality
        board2.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        assertNotEquals(board1, board2);

        // TODO 1: write at least 3 test cases
        //Test 3: Different Board sizes
        Board b1 = new Board(5,10);
        Board b2 = new Board(10,5);
        assertNotEquals(b1,b2);
        assertNotEquals(b2,b1);
        Board b3 = new Board(0,0);
        assertNotEquals(b3,b1);
        assertNotEquals(b3,b2);
        assertNotEquals(b3,board1);

        //Test 4: Position filled but with different players
        board1.place(new Position(0,0),PlayerRole.SECOND_PLAYER);
        assertNotEquals(board1,board2);
        board1.erase(new Position(0,0));
        board1.place(new Position(0,0), PlayerRole.FIRST_PLAYER);
        assertEquals(board1,board2);

        //Test 5: Same size board with pieces added
        b1.place(new Position(0,0),PlayerRole.FIRST_PLAYER);
        b2.place(new Position(0,0),PlayerRole.FIRST_PLAYER);
        assertNotEquals(b1,b2);
        Board b4 = new Board(5,10);
        b4.place(new Position(0,0),PlayerRole.FIRST_PLAYER);
        b4.place(new Position(4,9),PlayerRole.SECOND_PLAYER);
        b1.place(new Position(4,9),PlayerRole.SECOND_PLAYER);
        b4.place(new Position(4,5),PlayerRole.FIRST_PLAYER);
        b1.place(new Position(4,5),PlayerRole.FIRST_PLAYER);
        b4.place(new Position(3,6),PlayerRole.SECOND_PLAYER);
        b1.place(new Position(3,6),PlayerRole.SECOND_PLAYER);
        assertEquals(b1,b4);

    }

    @Test
    void testHashCode() {
        Board board1 = new Board(3, 3);
        Board board2 = new Board(3, 3);

        // test 1: equal boards should have the same hash code
        assertEquals(board1.hashCode(), board2.hashCode());

        // test 2: unequal boards should be very unlikely to have the
        // same hash code
        board2.place(new Position(0, 0), PlayerRole.FIRST_PLAYER);
        assertNotEquals(board1.hashCode(), board2.hashCode());

        // TODO 2: write at least 3 test cases
        //Test 3: Same board with different players in same position
        board1.place(new Position(0,0),PlayerRole.SECOND_PLAYER);
        assertNotEquals(board1.hashCode(),board2.hashCode());
        board1.place(new Position(1,1),PlayerRole.SECOND_PLAYER);
        board2.place(new Position(1,1),PlayerRole.FIRST_PLAYER);
        assertNotEquals(board1.hashCode(),board2.hashCode());

        //Test 4:Boards of different sizes
        Board board3 = new Board(4, 4);
        board3.place(new Position(0,0),PlayerRole.SECOND_PLAYER);
        Board b1 = new Board(5,10);
        Board b2 = new Board(10,5);
        assertNotEquals(board1.hashCode(),board2.hashCode());
        b1.place(new Position(0,0),PlayerRole.FIRST_PLAYER);
        b2.place(new Position(0,0),PlayerRole.FIRST_PLAYER);


        //Test 5:Same size board with pieces added
        Board b4 = new Board(5,10);
        b4.place(new Position(0,0),PlayerRole.FIRST_PLAYER);
        b4.place(new Position(4,9),PlayerRole.SECOND_PLAYER);
        b1.place(new Position(4,9),PlayerRole.SECOND_PLAYER);
        b4.place(new Position(4,5),PlayerRole.FIRST_PLAYER);
        b1.place(new Position(4,5),PlayerRole.FIRST_PLAYER);
        b4.place(new Position(3,6),PlayerRole.SECOND_PLAYER);
        b1.place(new Position(3,6),PlayerRole.SECOND_PLAYER);
        assertEquals(b1.hashCode(),b4.hashCode());
    }

    @Test
    void get() {
        Board board = new Board(5, 5);
        Position p = new Position(0, 0);
        // test 1: Check that an empty space reports 0
        assertEquals(0, board.get(p));

        // test 2: Check that a placed piece is seen by get()
        board.place(p, PlayerRole.FIRST_PLAYER);
        assertEquals(PlayerRole.FIRST_PLAYER.boardValue(), board.get(p));
    }

    @Test
    void place() {
        // test 1: do placed pieces show up where they are placed?
        Board board = new Board(5, 5);
        Position p = new Position(0, 0);
        assertEquals(0, board.get(p));
        board.place(p, PlayerRole.SECOND_PLAYER);
        assertEquals(PlayerRole.SECOND_PLAYER.boardValue(), board.get(p));
    }

    @Test
    void erase() {
        Board board = new Board(5, 5);
        Position p = new Position(0, 0);
        board.place(p, PlayerRole.SECOND_PLAYER);

        // test 1: do pieces get erased?
        board.erase(p);
        assertEquals(0, board.get(p));
    }

    @Test
    void rowSize() {
        Board board = new Board(5, 6);
        assertEquals(5, board.rowSize());
    }

    @Test
    void colSize() {
        Board board = new Board(5, 6);
        assertEquals(6, board.colSize());
    }

    @Test
    void validPos() {
        Board board = new Board(5, 6);
        Position p = new Position(3, 3);
        // test 1: is a valid position valid?
        assertTrue(board.validPos(p));
        board.place(p, PlayerRole.FIRST_PLAYER);

        // test 2: is an invalid position invalid?
        p = new Position(5, 5);
        assertFalse(board.validPos(p));
    }

    @Test
    void onBoard() {
        Board board = new Board(5, 6);
        Position p = new Position(3, 3);
        // test 1: is a valid empty position on board?
        assertTrue(board.onBoard(p));
        // test 2: is a valid nonempty position on board?
        board.place(p, PlayerRole.FIRST_PLAYER);
        assertTrue(board.onBoard(p));
        // test 3: is an invalid position on board?
        p = new Position(5, 5);
        assertFalse(board.onBoard(p));
    }
}