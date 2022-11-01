package a5.logic;

import a5.util.PlayerRole;
import a5.util.GameType;
import a5.util.GameResult;

import java.util.Arrays;


/**
 * A Pente game, where players take turns to place stones on board.
 * When consecutive two stones are surrounded by the opponent's stones on two ends,
 * these two stones are removed (captured).
 * A player wins by placing 5 consecutive stones or capturing stones 5 times.
 */
public class Pente extends MNKGame {

    /**
     * Number of pairs captured by player 1
     * player1Stones >= 0
     */
    private int player1Pairs;

    /**
     * Number of pairs captured by player 2
     * player2Stones >= 0
     */
    private int player2Pairs;
    /**
     * Create an 8-by-8 Pente game.
     */
    public Pente() {
        super(8, 8, 5);
        // TODO 1
        player1Pairs = 0;
        player2Pairs = 0;
    }

    /**
     * Creates: a copy of the game state.
     */
    public Pente(Pente game) {
        super(game);
        // TODO 2
        this.player1Pairs = game.player1Pairs;
        this.player2Pairs = game.player2Pairs;

    }

    @Override
    public boolean makeMove(Position p) {
        // TODO 3
        if(!board().validPos(p)) {
            return false;
        }
        board().place(p,currentPlayer());

        if(board().onBoard(new Position(p.row()+3, p.col()))){  //down
            if (board().get(new Position(p.row() + 1, p.col())) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row() + 2, p.col())) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row() + 3, p.col())) == currentPlayer().boardValue()) {
                board().erase(new Position(p.row() + 1, p.col()));
                board().erase(new Position(p.row() + 2, p.col()));
                if(currentPlayer().equals(PlayerRole.FIRST_PLAYER)) {
                    player1Pairs += 1;
                } else {
                    player2Pairs += 1;
                }
            }
        }
        if(board().onBoard(new Position(p.row()-3, p.col()))) { //up
            if (board().get(new Position(p.row() - 1, p.col())) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row() - 2, p.col())) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row() - 3, p.col())) == currentPlayer().boardValue()) {
                board().erase(new Position(p.row() - 1, p.col()));
                board().erase(new Position(p.row() - 2, p.col()));
                if(currentPlayer().equals(PlayerRole.FIRST_PLAYER)) {
                    player1Pairs += 1;
                } else {
                    player2Pairs += 1;
                }
            }
        }
        if(board().onBoard(new Position(p.row(), p.col()-3))) { //left
            if (board().get(new Position(p.row(), p.col()-1)) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row(), p.col()-2)) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row(), p.col()-3)) == currentPlayer().boardValue()) {
                board().erase(new Position(p.row(), p.col()-1));
                board().erase(new Position(p.row(), p.col()-2));
                if(currentPlayer().equals(PlayerRole.FIRST_PLAYER)) {
                    player1Pairs += 1;
                } else {
                    player2Pairs += 1;
                }
            }
        }

        if(board().onBoard(new Position(p.row(), p.col()+3))) { //right
            if (board().get(new Position(p.row(), p.col()+1)) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row(), p.col()+2)) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row(), p.col()+3)) == currentPlayer().boardValue()) {
                board().erase(new Position(p.row(), p.col()+1));
                board().erase(new Position(p.row(), p.col()+2));
                if(currentPlayer().equals(PlayerRole.FIRST_PLAYER)) {
                    player1Pairs += 1;
                } else {
                    player2Pairs += 1;
                }
            }
        }

        if(board().onBoard(new Position(p.row()-3, p.col()+3))) {//diag down left
            if (board().get(new Position(p.row()-1, p.col()+1)) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row()-2, p.col()+2)) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row()-3, p.col()+3)) == currentPlayer().boardValue()) {
                board().erase(new Position(p.row()-1, p.col()+1));
                board().erase(new Position(p.row()-2, p.col()+2));
                if(currentPlayer().equals(PlayerRole.FIRST_PLAYER)) {
                    player1Pairs += 1;
                } else {
                    player2Pairs += 1;
                }
            }
        }

        if(board().onBoard(new Position(p.row()+3, p.col()+3))) {//diag down right
            if (board().get(new Position(p.row()+1, p.col()+1)) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row()+2, p.col()+2)) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row()+3, p.col()+3)) == currentPlayer().boardValue()) {
                board().erase(new Position(p.row()+1, p.col()+1));
                board().erase(new Position(p.row()+2, p.col()+2));
                if(currentPlayer().equals(PlayerRole.FIRST_PLAYER)) {
                    player1Pairs += 1;
                } else {
                    player2Pairs += 1;
                }
            }
        }

        if(board().onBoard(new Position(p.row()-3, p.col()-3))) {//diag up left
            if (board().get(new Position(p.row()-1, p.col()-1)) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row()-2, p.col()-2)) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row()-3, p.col()-3)) == currentPlayer().boardValue()) {
                board().erase(new Position(p.row()-1, p.col()-1));
                board().erase(new Position(p.row()-2, p.col()-2));
                if(currentPlayer().equals(PlayerRole.FIRST_PLAYER)) {
                    player1Pairs += 1;
                } else {
                    player2Pairs += 1;
                }
            }
        }

        if(board().onBoard(new Position(p.row()+3, p.col()-3))) { // diag up right
            if (board().get(new Position(p.row()+1, p.col()-1)) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row()+2, p.col()-2)) == currentPlayer().nextPlayer().boardValue()
                    && board().get(new Position(p.row()+3, p.col()-3)) == currentPlayer().boardValue()) {
                board().erase(new Position(p.row()+1, p.col()-1));
                board().erase(new Position(p.row()+2, p.col()-2));
                if(currentPlayer().equals(PlayerRole.FIRST_PLAYER)) {
                    player1Pairs += 1;
                } else {
                    player2Pairs += 1;
                }
            }
        }
        changePlayer();
        advanceTurn();
        return true;
    }

    /**
     * Returns: a new game state representing the state of the game after the current player takes a
     * move {@code p}.
     */
    public Pente applyMove(Position p) {
        Pente newGame = new Pente(this);
        newGame.makeMove(p);
        return newGame;
    }

    /**
     * Returns: the number of captured pairs by {@code playerRole}.
     */
    public int capturedPairsNo(PlayerRole playerRole) {
        // TODO 4
        if(playerRole.equals(PlayerRole.FIRST_PLAYER)) {
            return player1Pairs;
        } else {
            return player2Pairs;
        }
    }

    @Override
    public boolean hasEnded() {
        // TODO 5
        if(player1Pairs >= 5) {
            setResult(GameResult.FIRST_PLAYER_WON);
            return true;
        } else if(player2Pairs >= 5) {
            setResult(GameResult.SECOND_PLAYER_WON);
            return true;
        } else {
            return super.hasEnded();
        }
    }

    @Override
    public GameType gameType() {
        return GameType.PENTE;
    }


    @Override
    public String toString() {
        String board = super.toString();
        return board + System.lineSeparator() + "Captured pairs: " +
                "first: " + capturedPairsNo(PlayerRole.FIRST_PLAYER) + ", " +
                "second: " + capturedPairsNo(PlayerRole.SECOND_PLAYER);
    }

    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass()) {
            return false;
        }
        Pente p = (Pente) o;
        return stateEqual(p);
    }

    /**
     * Returns: true if the two games have the same state.
     */
    protected boolean stateEqual(Pente p) {
        // TODO 6
        return p.player1Pairs == this.player1Pairs && p.player2Pairs == this.player2Pairs && super.stateEqual(p);
    }

    @Override
    public int hashCode() {
        // TODO 7
        return Arrays.hashCode(new int[] {
                super.hashCode(),
                player1Pairs,
                player2Pairs,
        });
    }
}
