package a5.ai;

import static org.junit.jupiter.api.Assertions.*;

import a5.ai.TranspositionTable.StateInfo;
import a5.logic.Pente;
import a5.logic.Position;
import a5.logic.TicTacToe;
import cms.util.maybe.NoMaybeValue;
import org.junit.jupiter.api.Test;



class TranspositionTableTest {

    @Test
    void testConstructor() {
        // TODO 1: write at least 1 test case

        //Test 1: Create a new empty transposition table
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();
        assertEquals(table.size(),0);

        TranspositionTable<Pente> table2 = new TranspositionTable<>();
        assertEquals(table2.size(),0);
    }


    @Test
    void getInfo() throws NoMaybeValue {
        // test case 1: look for a state that is in the table
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();
        TicTacToe state = new TicTacToe();
        table.add(state, 0, GameModel.WIN);

        StateInfo info = table.getInfo(state).get();
        assertEquals(GameModel.WIN, info.value());
        assertEquals(0, info.depth());

        // test case 2: look for a state not in the table
        TicTacToe state2 = state.applyMove(new Position(0, 0));
        assertThrows(NoMaybeValue.class, () -> table.getInfo(state2).get());

        // TODO 2: write at least 3 more test cases
        //Test 3:
        TicTacToe s2 = new TicTacToe();
        TranspositionTable<TicTacToe> t = new TranspositionTable<>();
        t.add(s2,1,5);
        StateInfo i2 = t.getInfo(s2).get();
        TicTacToe s4 = s2.applyMove(new Position(0,0));
        TicTacToe s5 = s2.applyMove(new Position(1,1));
        TicTacToe s6 = s2.applyMove(new Position(1,2));
        TicTacToe s7 = s2.applyMove(new Position(2,2));
        t.add(s4,1,10);
        t.add(s5,2,11);
        t.add(s6,3,12);
        t.add(s7,4,13);
        StateInfo i4 = t.getInfo(s4).get();
        StateInfo i7 = t.getInfo(s7).get();
        assertEquals(13,i7.value());
        assertEquals(4,i7.depth());
        assertEquals(10,i4.value());
        assertEquals(1,i4.depth());

        //Test 4:
        TicTacToe s10 = s2.applyMove(new Position(0,1));
        TicTacToe s11 = s2.applyMove(new Position(2,1));
        TicTacToe s12 = s2.applyMove(new Position(1,0));
        assertThrows(NoMaybeValue.class, () -> t.getInfo(s10).get());
        assertThrows(NoMaybeValue.class, () -> t.getInfo(s11).get());
        assertThrows(NoMaybeValue.class, () -> t.getInfo(s12).get());
        //Test 5:
        TranspositionTable<TicTacToe> t3 = new TranspositionTable<>();
        TicTacToe s8 = s2.applyMove(new Position(2,1));
        t3.add(s2,1,5);
        t3.add(s4,1,10);
        t3.add(s5,2,11);
        t3.add(s6,3,12);
        t3.add(s7,4,13);
        t3.add(s8,5,14);
        StateInfo i5 = t3.getInfo(s5).get();
        StateInfo i8 = t3.getInfo(s8).get();
        assertEquals(11,i5.value());
        assertEquals(2,i5.depth());
        assertEquals(14,i8.value());
        assertEquals(5,i8.depth());

    }

    @Test
    void add() throws NoMaybeValue {
        TranspositionTable<TicTacToe> table = new TranspositionTable<>();

        // test case 1: add a state and check it is in there
        TicTacToe state = new TicTacToe();
        table.add(state, 0, GameModel.WIN);

        StateInfo info = table.getInfo(state).get();
        assertEquals(GameModel.WIN, info.value());
        assertEquals(0, info.depth());

        // TODO 3: write at least 3 more test cases
        //Test 2 Adding entries to bucket
        TicTacToe s2 = new TicTacToe();
        TranspositionTable<TicTacToe> t = new TranspositionTable<>();
        t.add(s2,1,5);
        StateInfo i2 = t.getInfo(s2).get();
        TicTacToe s4 = s2.applyMove(new Position(0,0));

        TicTacToe s5 = s2.applyMove(new Position(1,1));
        TicTacToe s6 = s2.applyMove(new Position(1,2));
        TicTacToe s7 = s2.applyMove(new Position(2,2));
        t.add(s4,1,10);
        t.add(s5,2,11);
        t.add(s6,3,12);
        t.add(s7,4,13);
        assertEquals(t.size(),5);
        StateInfo i4 = t.getInfo(s4).get();
        StateInfo i5 = t.getInfo(s5).get();
        StateInfo i6 = t.getInfo(s6).get();
        StateInfo i7 = t.getInfo(s7).get();
        assertEquals(10,i4.value());
        assertEquals(1,i4.depth());
        assertEquals(5,i2.value());
        assertEquals(1,i2.depth());
        assertEquals(11,i5.value());
        assertEquals(2,i5.depth());
        assertEquals(12,i6.value());
        assertEquals(3,i6.depth());
        assertEquals(13,i7.value());
        assertEquals(4,i7.depth());

        //Test 3 Overwriting existing entry
        TicTacToe s3 = new TicTacToe();
        t.add(s3,2,6);
        StateInfo i3 = t.getInfo(s3).get();
        assertEquals(6,i3.value());
        assertEquals(2,i3.depth());

        table.add(state,1,GameModel.WIN);
        info = table.getInfo(state).get();
        assertEquals(GameModel.WIN,info.value());
        assertEquals(1,info.depth());

        t.add(s6,5,100);
        info = t.getInfo(s6).get();
        assertEquals(100,info.value());
        assertEquals(5,info.depth());

        //Test 4 Growing the bucket
        TranspositionTable<TicTacToe> t3 = new TranspositionTable<>();
        TicTacToe s8 = s2.applyMove(new Position(2,1));
        t3.add(s2,1,5);
        t3.add(s4,1,10);
        t3.add(s5,2,11);
        t3.add(s6,3,12);
        t3.add(s7,4,13);
        t3.add(s8,5,14);
        assertEquals(t3.size(),6);
        i2 = t3.getInfo(s2).get();
        i4 = t3.getInfo(s4).get();
        i5 = t3.getInfo(s5).get();
        i6 = t3.getInfo(s6).get();
        i7 = t3.getInfo(s7).get();
        StateInfo i8 = t3.getInfo(s8).get();
        assertEquals(10,i4.value());
        assertEquals(1,i4.depth());
        assertEquals(5,i2.value());
        assertEquals(1,i2.depth());
        assertEquals(11,i5.value());
        assertEquals(2,i5.depth());
        assertEquals(12,i6.value());
        assertEquals(3,i6.depth());
        assertEquals(13,i7.value());
        assertEquals(4,i7.depth());
        assertEquals(14,i8.value());
        assertEquals(5,i8.depth());
    }
}