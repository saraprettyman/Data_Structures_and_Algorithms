import java.awt.Color;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class HexGame {
    DisjointSet gameBoard;
    public Color[] color;
    public int TOP_EDGE;
    public int BOTTOM_EDGE;
    public int LEFT_EDGE;
    public int RIGHT_EDGE;
    public int dim;

    // Constructor
    public HexGame(int n) {
        // Create game board, with one based index
        this.gameBoard = new DisjointSet(n * n + 5);
        this.TOP_EDGE = n * n + 1; // red
        this.BOTTOM_EDGE = n * n + 2; // red
        this.LEFT_EDGE = n * n + 3; // blue
        this.RIGHT_EDGE = n * n + 4; // blue
        this.color = new Color[n*n+1];
        dim = n;
    }

    public boolean playBlue(int position, boolean displayNeighbors) {
        if (displayNeighbors) {
            displayNeighbors(-position);
        }
        // check is occupied
        if (gameBoard.size[position] != -1) {
            return false;
        } else {
            color[position] = Color.BLUE;
            ArrayList<Integer> neighbors = getNeighborsBlue(position);
            for (int neighbor : neighbors) {
                if (neighbor == LEFT_EDGE){
                    gameBoard.union(position, LEFT_EDGE);
                }else if (neighbor == RIGHT_EDGE){
                    gameBoard.union(position, RIGHT_EDGE);
                }else if (color[neighbor] == Color.BLUE) {
                    gameBoard.union(position, neighbor);
                }
            }
        }
        return gameBoard.find(LEFT_EDGE) == gameBoard.find(RIGHT_EDGE);
    }

    public boolean playRed(int position, boolean displayNeighbors) {
        if (displayNeighbors) {
            displayNeighbors(position);
        }
        // check is occupied
        if (gameBoard.size[position] != -1) {
            return false;
        } else {
            color[position] = Color.RED;
            ArrayList<Integer> neighbors = getNeighborsRed(position);
            for (int neighbor : neighbors) {
                if (neighbor == TOP_EDGE){
                    gameBoard.union(position, TOP_EDGE);
                }else if (neighbor == BOTTOM_EDGE){
                    gameBoard.union(position, BOTTOM_EDGE);
                }else if (color[neighbor] == Color.RED) {
                    gameBoard.union(position, neighbor);
                }
            }
        }
        return gameBoard.find(TOP_EDGE) == gameBoard.find(BOTTOM_EDGE);
    }

    public void displayNeighbors(int position) {
        if (position > 0) {
            System.out.println("Cell " + position + ": " + getNeighborsRed(position).toString());
        } else {
            System.out.println("Cell " + Math.abs(position) + ": " + getNeighborsBlue(Math.abs(position)).toString());
        }
    }

    // Additional Helper Functions
    private ArrayList<Integer> getNeighborsBlue(int position) {
        ArrayList<Integer> neighbors = getNeighbors(position);
        neighbors.removeIf(p -> p == 122 || p == 123);
        return neighbors;
    }

    private ArrayList<Integer> getNeighborsRed(int position) {
        ArrayList<Integer> neighbors = getNeighbors(position);
        neighbors.removeIf(p -> p == 124 || p == 125);
        return neighbors;
    }

    private ArrayList<Integer> getNeighbors(int p) {
        ArrayList<Integer> neighbors = new ArrayList<>();
        if (isLeft(p) && isTop(p)) {
            neighbors.add(LEFT_EDGE);
            neighbors.add(TOP_EDGE);
            neighbors.add(p + dim);
            neighbors.add(p + 1);
            return neighbors;
        }
        if (isLeft(p) && isBottom(p)) {
            neighbors.add(LEFT_EDGE);
            neighbors.add(BOTTOM_EDGE);
            neighbors.add(p + 1);
            neighbors.add(p - dim);
            neighbors.add(p - dim + 1);
            return neighbors;
        }
        if (isTop(p) && isRight(p)) {
            neighbors.add(TOP_EDGE);
            neighbors.add(RIGHT_EDGE);
            neighbors.add(p - 1);
            neighbors.add(p + dim - 1);
            neighbors.add(p + dim);
            return neighbors;
        }
        if (isBottom(p) && isRight(p)) {
            neighbors.add(BOTTOM_EDGE);
            neighbors.add(RIGHT_EDGE);
            neighbors.add(p - 1);
            neighbors.add(p - dim);
            return neighbors;
        }
        if (isLeft(p)) {
            neighbors.add(LEFT_EDGE);
            neighbors.add(p + 1);
            neighbors.add(p + dim);
            neighbors.add(p - dim);
            neighbors.add(p - dim + 1);
            return neighbors;
        }
        if (isTop(p)) {
            neighbors.add(TOP_EDGE);
            neighbors.add(p + dim - 1);
            neighbors.add(p + dim);
            neighbors.add(p + 1);
            neighbors.add(p - 1);
            return neighbors;
        }
        if (isRight(p)) {
            neighbors.add(RIGHT_EDGE);
            neighbors.add(p - dim);
            neighbors.add(p - 1);
            neighbors.add(p + dim);
            neighbors.add(p + dim - 1);
            return neighbors;
        }
        if (isBottom(p)) {
            neighbors.add(BOTTOM_EDGE);
            neighbors.add(p + 1);
            neighbors.add(p - 1);
            neighbors.add(p - dim);
            neighbors.add(p - dim + 1);
            return neighbors;
        }
        neighbors.add(p - dim);
        neighbors.add(p - dim + 1);
        neighbors.add(p - 1);
        neighbors.add(p + 1);
        neighbors.add(p + dim - 1);
        neighbors.add(p + dim);
        return neighbors;
    }

    private boolean isLeft(int p) {
        return (p % dim == 1);
    }

    private boolean isRight(int p) {
        return (p % dim == 0);
    }

    private boolean isTop(int p) {
        return (p <= dim);
    }

    private boolean isBottom(int p) {
        return (dim * (dim - 1) + 1 <= p && p <= dim * dim);
    }


}
