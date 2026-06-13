package com.assignment4.EightPuzzle;

import java.util.*;

public class GameBoard implements Comparable<GameBoard> {

    private static final int HASH_VALUE = 31;

    @Override
    public int compareTo(GameBoard o) {
        int observingHamming = o.hamming();
        int thisHamming      = hamming();

        return Integer.compare(thisHamming, observingHamming);
    }

    private static class Coordinate {
        private final int row;
        private final int col;

        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    private final int[][] tiles;
    private final int     dimension;
    private Coordinate    emptySquare;

    public GameBoard(int[][] tiles) {
        //Assume 0 denotes the empty square
        this.tiles     = tiles;
        this.dimension = tiles.length;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] == 0) {
                    emptySquare = new Coordinate(i, j);
                }
            }
        }

        if (!isValid()) {
            tiles = null;
            throw new IllegalArgumentException("Invalid board! It must include consecutive numbers and be square");
        }
    }

    public boolean isValid() {
        if (tiles == null) return false;
        if (tiles.length != tiles[0].length) return false;

        Set<Integer> unseen = new HashSet<>();
        for (int counter = 0; counter < dimension * dimension; counter++) {
            unseen.add(counter);
        }

        for (int[] row : tiles) {
            for (int tile : row) {
                if (tile < 0) {
                    return false;
                }
                unseen.remove(tile);
            }
        }
        return unseen.isEmpty();
    }

    public int[][] getTiles() {
        return tiles;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dimension).append("\n");
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                sb.append(tiles[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int dimension() {
        return dimension;
    }

    // number of tiles out of place relative to goal
    public int hamming() {

        int hamming       = 0;
        int expectedValue = 1;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                // the expected value is 0 at this point, set it
                if (i == tiles.length - 1 && j == tiles.length - 1) {
                    expectedValue = 0;
                }

                if (tiles[i][j] != expectedValue && tiles[i][j] != 0) {
                    hamming += 1;
                }

                expectedValue++;
            }
        }

        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        int expected  = 1;

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                int value = tiles[i][j];
                // we know it's out of sync, find how many moves to its goal state
                if (value != expected && value != 0) {
                    Location expectedRowColumn = findExpectedRowColumn(value); // find the indices of where the value should be
                    int rowDifference          = Math.abs(i - expectedRowColumn.row );
                    int columnDifference       = Math.abs(expectedRowColumn.column - j);
                    manhattan                  = manhattan + rowDifference + columnDifference;
                }

                expected = ((expected + 1) % (dimension * dimension));
            }
        }

        return manhattan;
    }

    public int manhattanPlusHamming() {
        return manhattan() + hamming();
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    @Override
    public int hashCode() {

        int hashcode = 0;

        for (int[] tile : tiles) {
            for (int i : tile) {
                hashcode = HASH_VALUE * hashcode + i;
            }
        }

        return hashcode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        GameBoard other = (GameBoard) obj;
        if (dimension != other.dimension) return false;

        int[][] tiles = other.getTiles();

        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j] != this.tiles[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring board states
    public Iterable<GameBoard> neighbors() {
        ArrayList<GameBoard> neighbors = new ArrayList<>();

        //Swap the empty square with all of its neighbors
        if (emptySquare.row > 0) {
            neighbors.add(swap(tiles[emptySquare.row - 1][emptySquare.col], 0));
        }
        if (emptySquare.row < dimension - 1) {
            neighbors.add(swap(tiles[emptySquare.row + 1][emptySquare.col], 0));
        }
        if (emptySquare.col > 0) {
            neighbors.add(swap(tiles[emptySquare.row][emptySquare.col - 1], 0));
        }
        if (emptySquare.col < dimension - 1) {
            neighbors.add(swap(tiles[emptySquare.row][emptySquare.col + 1], 0));
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public GameBoard swap(int tile1Value, int tile2Value) {
        //Swap the tiles at the given indices
        if (tile1Value != 0 & tile2Value != 0) {
            throw new IllegalArgumentException("One of the tiles must be the empty square!");
        } else {
            int[][] newTiles = new int[dimension][dimension];

            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    if (tiles[i][j] == tile1Value) {
                        newTiles[i][j] = tile2Value;
                    } else if (tiles[i][j] == tile2Value) {
                        newTiles[i][j] = tile1Value;
                    } else {
                        newTiles[i][j] = tiles[i][j];
                    }
                }
            }
            return new GameBoard(newTiles);
        }
    }

    private Location findExpectedRowColumn(int value) {
        if(value == 0) return new Location(dimension -1, dimension -1);

        int index = value - 1;
        int rowLocation   = index / dimension;
        int columnLocation   = index % dimension;

        return new Location(rowLocation, columnLocation);
    }

    public record Location(int row, int column) {
    }
}
