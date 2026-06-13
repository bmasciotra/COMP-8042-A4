package com.assignment4.EightPuzzle;

import java.util.*;

public class AStar8PuzzleSolver implements EightPuzzleSolver {

    enum solvedStatus {
        SOLVED, NOT_POSSIBLE, NOT_EXECUTED
    }

    private GameBoard                            initialBoardState;
    private GameBoard                            goalBoardState;
    BinaryHeap<GameBoardPQEntry>                 minPQ;
    private Map<GameBoard, GameBoard>            predecessors;
    private GameBoardPQEntry                     current;
    private solvedStatus                         solved;
    private QuadraticProbingHashTable<GameBoard> visited;

    // You need to decide what data structure to use to store the visited nodes, either a
    // Separate chaining hash table or a quadratic probing hash table.
    // private YourChoiceOfHashTable visited;

    public AStar8PuzzleSolver(GameBoard initial, GameBoard goal) {
        this.initialBoardState = initial;
        this.goalBoardState    = goal;

        int hashTableSize      = initial.dimension() <= 3 ? 47 : 101;
        int heapSize           = initial.dimension() <= 3 ? 50 : 1000;
        minPQ                  = new BinaryHeap<>(heapSize);
        predecessors           = new HashMap<>();
        solved                 = solvedStatus.NOT_EXECUTED;
        visited                = new QuadraticProbingHashTable<GameBoard>(hashTableSize);

        minPQ.insert(new GameBoardPQEntry(initialBoardState, 0));
    }

    public void printSolution() {
        if (solved == solvedStatus.SOLVED) {
            for (GameBoard board : reconstructPath(current.board)) {
                System.out.println(board);
            }
        }
    }

    public Iterable<GameBoard> solution() {
        if (solved == solvedStatus.SOLVED) {
            return reconstructPath(current.board);
        }
        return new ArrayList<GameBoard>();
    }

    public long numberMoves() {
        if (solved == solvedStatus.SOLVED) {
            return reconstructPath(current.board).spliterator().getExactSizeIfKnown() - 1;
        }
        return -1;
    }

    public void solve() throws BinaryHeap.UnderflowException {
        // Short circuit if the board is already solved
        if (initialBoardState.equals(goalBoardState)) {
            current = new GameBoardPQEntry(initialBoardState, 0);
            solved  = solvedStatus.SOLVED;
            return;
        }

        while (!minPQ.isEmpty() && solved != solvedStatus.SOLVED) {
            exploreNext();
        }

        if (solved != solvedStatus.SOLVED) {
            solved = solvedStatus.NOT_POSSIBLE;
        }
    }

    //Explore the next node in the frontier according to the priority queue
    private void exploreNext() throws BinaryHeap.UnderflowException {
        GameBoardPQEntry priority = minPQ.deleteMin();

        if (visited.contains(priority.board)){
            return;
        }

        visited.insert(priority.board); // add to the visited map

        if (priority.board.equals(goalBoardState)) { // if we have arrived at the success state
            current = priority;
            solved  = solvedStatus.SOLVED;
            return;
        }

        for (GameBoard neighbour : priority.board.neighbors()) {
            if (visited.contains(neighbour)) continue;

            predecessors.put(neighbour, priority.board); // add into the predecessors, marking our map
            int newGScore = priority.gScore + 1; // update the g score, 1 more move
            minPQ.insert(new GameBoardPQEntry(neighbour, newGScore)); // insert the new board into the heap to recalculate
        }

    }

    private boolean solutionReached() {
        return current.board.equals(goalBoardState);
    }

    public solvedStatus status() {
        return solved;
    }

    private Iterable<GameBoard> reconstructPath(GameBoard current) {
        /*
         * You shouldn't have to modify this method.
         */
        List<GameBoard> path = new ArrayList<>();
        while (current != null) {
            path.add(current);
            current = predecessors.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private class GameBoardPQEntry implements Comparable<GameBoardPQEntry> {
        public GameBoard board;
        public int priority;
        public int gScore;
        public int hScore;

        public GameBoardPQEntry(GameBoard board, int gScore) {
            this.board = board;
            this.gScore = gScore;
            this.hScore = board.manhattan();
            this.priority = gScore + hScore;
        }

        @Override
        public int compareTo(GameBoardPQEntry o) {
            return this.priority - o.priority;
        }
    }
}
