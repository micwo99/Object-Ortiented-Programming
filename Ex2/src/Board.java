import java.util.Random;

public class Board {
    private static final int SIZE=4;
    private Mark[][] board;
    private int size;

    private void initializeBoard(int size){
        this.size=size;
        this.board = new Mark[size][size];
        for (int i = 0; i <size ; i++) {
            for (int j = 0; j <size ; j++)
            {
                board[i][j]=Mark.BLANK;
            }
        }

    }

    /**
     * constructor
     */
    public Board() {
        initializeBoard(SIZE);
    }

    public Board(int size){
        initializeBoard(size);
    }

    public Mark[][] getBoard() {
        return board;
    }

    public int getSize(){
        return size;
    }

    /**
     * the functions put a Mark on the board at the coordinate row,col
     * @param mark X,O,BLANK
     * @param row coordinate of the row
     * @param col coordinate of the columns
     * @return true if success, false otherwise
     */
    public boolean putMark(Mark mark,int row, int col){
        if (row<0 || col<0 ||row>=size ||col>=size){
            return false;
        }
        if (getMark(row,col)!=Mark.BLANK || mark== Mark.BLANK){
            return false;
        }
        board[row][col]=mark;
        return true;
    }

    /**
     * the function returns which mark are in the board at the coordinate(row,col)
     * @param row coordinate of the row
     * @param col coordinate of the columns
     * @return  which mark are in the board at the coordinate(row,col)
     */
    public Mark getMark(int row, int col){
        if(row<0 || col<0 ||row>=size ||col>=size){
            return Mark.BLANK;
        }
        return board[row][col];
    }

}
