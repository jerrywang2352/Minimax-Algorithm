package a5.ai;

import cms.util.maybe.Maybe;

/**
 * A transposition table for an arbitrary game. It maps a game state
 * to a search depth and a heuristic evaluation of that state to the
 * recorded depth. Unlike a conventional map abstraction, a state is
 * associated with a depth, so that clients can look for states whose
 * entry has at least the desired depth.
 *
 * @param <GameState> A type representing the state of a game.
 */
public class TranspositionTable<GameState> {

    /**
     * Information about a game state, for use by clients.
     */
    public interface StateInfo {

        /**
         * The heuristic value of this game state.
         */
        int value();

        /**
         * The depth to which the game tree was searched to determine the value.
         */
        int depth();
    }

    /**
     * A Node is a node in a linked list of nodes for a chaining-based implementation of a hash
     * table.
     *
     * @param <GameState>
     */
    static private class Node<GameState> implements StateInfo {
        /**
         * The state
         */
        GameState state;
        /**
         * The depth of this entry. >= 0
         */
        int depth;
        /**
         * The value of this entry.
         */
        int value;
        /**
         * The next node in the list. May be null.
         */
        Node<GameState> next;

        Node(GameState state, int depth, int value, Node<GameState> next) {
            this.state = state;
            this.depth = depth;
            this.value = value;
            this.next = next;
        }

        public int value() {
            return value;
        }

        public int depth() {
            return depth;
        }
    }

    /**
     * The number of entries in the transposition table.
     */
    private int size;

    /**
     * The buckets array may contain null elements.
     * Class invariant:
     * All transposition table entries are found in the linked list of the
     * bucket to which they hash, and the load factor is no more than 1.
     */
    private Node<GameState>[] buckets;

    // TODO 1: implement the classInv() method. You may also
    // strengthen the class invariant. The classInv()
    // method is likely to be expensive, so you may want to turn
    // off assertions in this file, but only after you have the transposition
    // table fully tested and working.

    /**
     * depth >= 0 for every node in each bucket
     * load factor <= 1.0 (size <= number of buckets)
     * Total number of nodes in buckets is equal to size
     * GameState.HashCode % buckets.length is at the right index
     */
    boolean classInv() {
        int currSize = 0;
        for(int i = 0; i < buckets.length; i++) {
            Node<GameState> dummy = buckets[i];
            while (dummy != null) {
                if (dummy.depth() < 0 || Math.abs(dummy.state.hashCode()%buckets.length) != i) {
                    return false;
                }
                currSize++;
                dummy = dummy.next;
            }
        }
        return currSize == size && size <= buckets.length;
    }

    @SuppressWarnings("unchecked")
    /** Creates: a new, empty transposition table. */
    TranspositionTable() {
        size = 0;
        // TODO 2
        buckets = new Node[5];
    }

    /** The number of entries in the transposition table. */
    public int size() {
        return size;
    }

    /**
     * Returns: the information in the transposition table for a given
     * game state, package in an Optional. If there is no information in
     * the table for this state, returns an empty Optional.
     */
    public Maybe<StateInfo> getInfo(GameState state) {
        // TODO 3
        assert classInv();
        int index = Math.abs(state.hashCode() % buckets.length);
        Node<GameState> node = buckets[index];
        while(node != null) {
            if (node.state.equals(state)) {
                assert classInv();
                return Maybe.some(node);
            }
            node = node.next;
        }
        assert classInv();
        return Maybe.none();
    }

    /**
     * Effect: Add a new entry in the transposition table for a given
     * state and depth, or overwrite the existing entry for this state
     * with the new depth and value. Requires: if overwriting an
     * existing entry, the new depth must be greater than the old one.
     */
    public void add(GameState state, int depth, int value) {
        // TODO 4
        assert classInv();
        int index = Math.abs(state.hashCode() % buckets.length);
        Node<GameState> head = buckets[index];
        while(head != null) {
            //overwrite existing entry if this state is already in the table
            if (head.state.equals(state)) {
                head.depth = depth;
                head.value = value;
                assert classInv();
                return;
            }
            head = head.next;
        }
        //add new entry
        head = buckets[index];
        Node<GameState> newNode = new Node<>(state, depth, value, null);
        newNode.next = head;
        buckets[index] = newNode;
        size ++;
        grow(size);
        assert classInv();
    }

    /**
     * Effect: Make sure the hash table has at least {@code target} buckets.
     * Returns true if the hash table actually resized.
     */
    private boolean grow(int target) {
        // TODO 5
        if (target <= buckets.length) {return false;}
        Node<GameState>[] currTable = buckets;
        buckets = new Node[currTable.length*2];
        size = 0;
        for (Node<GameState> node : currTable) {
            while (node != null) {
                add(node.state, node.depth, node.value);
                node = node.next;
            }
        }
        return true;
    }

    // You may want to write some additional helper methods.


    /**
     * Estimate clustering. With a good hash function, clustering
     * should be around 1.0. Higher values of clustering lead to worse
     * performance.
     */
    double estimateClustering() {
        final int N = 500;
        int m = buckets.length, n = size;
        double sum2 = 0;
        for (int i = 0; i < N; i++) {
            int j = Math.abs((i * 82728353) % buckets.length);
            int count = 0;
            Node<GameState> node = buckets[j];
            while (node != null) {
                count++;
                node = node.next;
            }
            sum2 += count*count;
        }
        double alpha = (double)n/m;
        return sum2/(N * alpha * (1 - 1.0/m + alpha));
    }
}
