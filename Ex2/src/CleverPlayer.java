import java.util.Random;

class CleverPlayer implements Player {

    /**
     * the function changes randomly a blank in the given row by mark
     * @param row between o and SIZE-1
     * @param mark X or O
     */
    private void ChangeBlankToMarkInRow(int row,Mark mark,Board board){

        Random rand=new Random();

        int col=rand.nextInt(board.getSize());
        while(board.getMark(row,col)!=Mark.BLANK){
            col=rand.nextInt(board.getSize());
        }
        board.putMark(mark,row,col);
    }

    /**
     * the function changes randomly a blank in the given col by mark
     * @param col between o and SIZE-1
     * @param mark X or O
     */
    private void ChangeBlankToMarkInCol(int col,Mark mark,Board board){
        Random rand=new Random();

        int row=rand.nextInt(board.getSize());
        while(board.getMark(row,col)!=Mark.BLANK){
            row=rand.nextInt(board.getSize());
        }
        board.putMark(mark,row,col);
    }


    /**
     * the function return a list that give us the best num of mark in a row,
     * at which row and columns it begins and said to us if it's a row or a columns thanks to rowDelta and colDelta
     * @return a list :[the best streak of mark,coordinate where it begins,if it s a columns or a row  ]
     */
    private int[] BestDir(Mark mark,int deltaRow,int deltaCol,int secDeltaRow,int secDeltaCol,Board board) {
        int bestStreak = 0;
        int bestDir = 0;
        int rowOrCol;
        boolean check;
        for (int row = 0; row < board.getSize() ; row++) {
            for (int col = 0; col < board.getSize() ; col++) {
                int x = countMarkInDirection(row, col, deltaRow, deltaCol, mark,board);//1,0
                int y = countMarkInDirection(row, col, secDeltaRow, secDeltaCol, mark,board);//-1,0
                if(deltaCol==0){
                    check=CheckBlankInCol(col,board);
                    rowOrCol=col;
                }
                else{
                    check=CheckBlankInRow(row,board);
                    rowOrCol=row;
                }
                if (x >= bestStreak && check) {
                    bestDir=rowOrCol;
                    bestStreak = x;
                }
                if (y > bestStreak && check) {
                    bestDir = rowOrCol;
                    bestStreak = y;
                }

            }
        }
        return new int[]{bestDir, bestStreak};
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
     * the function gives us a columns, and we check if there is a Blank on this columns
     * @return true if there is a blank, false otherwise
     */
    private boolean CheckBlankInCol(int col,Board board){
        for (int i = 0; i <board.getSize() ; i++) {
            if(board.getMark(i,col)==Mark.BLANK){
                return true;
            }
        }
        return false;

    }

    /**
     * the function gives us a row and we check if there is a Blank on this row
     * @return true if there is a blank, false otherwise
     */
    private  boolean CheckBlankInRow(int row,Board borad){
        for (int i = 0; i <borad.getSize() ; i++) {
            if(borad.getMark(row,i)==Mark.BLANK){
                return true;
            }
        }
        return false;

    }

    /**
     *play a turn on our game according to a clever player
     */
    public void playTurn(Board board, Mark mark) {

        int[] rows = BestDir(mark,0,1,0,-1,board);
        int[] cols = BestDir(mark,1,0,-1,0,board);
        int bestRowH=rows[0];
        int bestColV=cols[0];
        int bestStreakHoriz=rows[1];
        int bestStreakVer= cols[1];
                if(bestStreakHoriz>=bestStreakVer){
                    ChangeBlankToMarkInRow(bestRowH,mark,board);
                }
                else{
                    ChangeBlankToMarkInCol(bestColV,mark,board);
                }
            }
}