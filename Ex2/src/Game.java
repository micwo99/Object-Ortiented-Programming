import java.util.Random;

public class Game {
    private Renderer renderer;
    private Player playerX;
    private Player playerO;
    private static final int WIN_STREAK=3;
    private int winStreak= WIN_STREAK;
    private static final int SIZE=4;
    private int size;

    /**
     * constructor
     */
    public Game(Player playerX, Player playerO,Renderer renderer) {
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this.size= SIZE;
        this.winStreak=WIN_STREAK;

    }
    public Game(Player playerX, Player playerO,int size, int winStreak, Renderer renderer){
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
        this.size=size;
        if(winStreak>size || winStreak<2){
            this.winStreak=size;
        }
        else{this.winStreak=winStreak;}
    }

    public int getWinStreak() {
        return winStreak;
    }

    /**
     * the function run a single game of TicTacToe
     *
     * @return the winner
     */
    public Mark run() {
        Board board = new Board(size);
        int counter = 0;

        while (!gameEnded(board)) {
            renderer.renderBoard(board);
            if (counter % 2 == 0) {
                playerX.playTurn(board, Mark.X);
                counter++;
            }
            else {
                playerO.playTurn(board, Mark.O);
                counter++;
            }

        }
        renderer.renderBoard(board);
        return getWinner(board);
    }

    /**
     * the function checks if the game is ended
     * @return true if is ended,false otherwise
     */
    private  int countMarkInDirection(int row, int col, int rowDelta, int colDelta, Mark mark,Board board) {
        int count = 0;
        while(row < board.getSize()  && row >= 0 && col < board.getSize()  && col >= 0 && board.getMark(row,col)== mark) {
            count++;
            row += rowDelta;
            col += colDelta;
        }
        return count;
    }

    /**
     * the function checks if mark won
     * @param mark X or O
     * @return true if the mark won, false otherwise
     */
    private boolean winnerInTheGame(Mark mark,Board board) {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (countMarkInDirection(row, col, -1, 1, mark,board) == winStreak
                        || countMarkInDirection(row, col, -1, 0, mark,board) == winStreak
                        || countMarkInDirection(row, col, -1, -1, mark,board) == winStreak
                        || countMarkInDirection(row, col, 0, 1, mark,board) == winStreak
                        || countMarkInDirection(row, col, 0, -1, mark,board) == winStreak
                        || countMarkInDirection(row, col, 1, 0, mark,board) == winStreak
                        || countMarkInDirection(row, col, 1, -1, mark,board) == winStreak
                        || countMarkInDirection(row, col, 1, 1, mark,board) == winStreak) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * the function return which player have won or if there is a draw
     * @return which mark won
     */
    private Mark getWinner(Board board){
        if(winnerInTheGame(Mark.O,board)){
            return Mark.O;
        }
        if(winnerInTheGame(Mark.X,board)){
            return Mark.X;
        }
        return Mark.BLANK;

    }
    private boolean gameEnded(Board board){
        if (getWinner(board)==Mark.X||getWinner(board)==Mark.O){
            return true;
        }
        for (int row = 0; row <size ; row++) {
            for (int col = 0; col <size ; col++) {
                if(board.getMark(row,col)== Mark.BLANK){
                    return false;
                }
            }
        }
        return true;

    }


}

