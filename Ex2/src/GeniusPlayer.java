
class GeniusPlayer implements Player{

    /**
     * the function puts mark,if there are more mark wanted in the row, the function will put mark in the given row
     * if there is more in the column the function will put mark in the given columns
     * @param mark X or O
     * @param board a board
     * @param rows [the best row, num_max of streak in a row ]
     * @param cols [the best col, num_max of streak in a col]
     * @param check the mark we check
     */
    private void putMarkGenius(Mark mark, Board board, int[] rows, int[] cols, Mark check) {

        int bestStreakVer= cols[1],bestStreakHoriz=rows[1];
        int bestRowH = rows[0], bestColV = cols[0];
        if (bestStreakHoriz >= bestStreakVer) {
            bestPutMarkInRow(bestRowH,mark,bestStreakHoriz,check,board);
        }
        else{
            bestPutMarkInCol(bestColV, mark,bestStreakVer,check,board);
        }

    }

    /**
     * the function finds where is the best coordinate in the given row to put mark on the board and do it
     * @param mark X or O
     * @param bestStreakHoriz num max of mark in a row
     * @param checkMark check if there is a blank in our row
     */
    private void bestPutMarkInRow(int row,Mark mark,int bestStreakHoriz,Mark checkMark, Board board){
        boolean flag = false;
        if(bestStreakHoriz!=0){
            for (int col = 0; col < board.getSize()-1 ; col++){
                if(board.getMark(row,col)==checkMark && board.getMark(row,col+1)==Mark.BLANK){
                    flag = board.putMark(mark,row,col+1);
                    break;
                }
            }
        }
        if (!flag && bestStreakHoriz!=0) {
            for (int col = board.getSize() - 1; col > 0; col--) {
                if (board.getMark(row, col) == checkMark && board.getMark(row, col-1) == Mark.BLANK) {
                    flag = board.putMark(mark, row, col-1);
                    break;
                }
            }
        }
        if (!flag) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getMark(row, col) == Mark.BLANK) {
                    board.putMark(mark, row, col);
                    break;
                }
            }
        }

    }

    /**
     * the function finds where is the best coordinate in the given columns to put mark on the board and do it
     * @param mark X or O
     * @param bestStreakVer num max of mark in a columns
     * @param check check if there is a blank in our columns
     */
    private void bestPutMarkInCol(int col,Mark mark,int bestStreakVer,Mark check,Board board) {
        boolean flag = false;
        if (bestStreakVer != 0) {
            for (int row = 0; row < board.getSize() - 1; row++) {
                if (board.getMark(row, col) == check && board.getMark(row + 1, col) == Mark.BLANK) {
                    flag = board.putMark(mark, row + 1, col);
                    break;
                }
            }
        }
        if (!flag && bestStreakVer != 0) {
            for (int row = board.getSize() - 1; row > 0; row--) {
                if (board.getMark(row, col) == check && board.getMark(row - 1, col) == Mark.BLANK) {
                    flag = board.putMark(mark, row -1, col);
                    break;
                }
            }
        }
        if(!flag){
            for (int row = 0; row < board.getSize(); row++) {
                if (board.getMark(row, col) == Mark.BLANK) {
                    board.putMark(mark, row, col);
                    break;
                }
            }
        }
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
     *play a turn on our game according to a Genius player
     */
    public void playTurn(Board board, Mark mark) {
        Mark secondPlayer;
        if(mark == Mark.O)
        {secondPlayer = Mark.X;}
        else{
            secondPlayer=Mark.O;
        }
        int[] rows =BestDir(mark,0,1,0,-1,board),
                cols =BestDir(mark,1,0,-1,0,board);
        int bestStreakVer= cols[1],bestStreakHoriz=rows[1];
        int bestFirst= Math.max(bestStreakVer,bestStreakHoriz);

        int[] rowsSecond = BestDir(secondPlayer,0,1,0,-1,board),
                colsSecond = BestDir(secondPlayer,1,0,-1,0,board);
        int bestStreakVerSec= colsSecond[1],bestStreakHorizSec=rowsSecond[1];
        int bestSecond= Math.max(bestStreakVerSec,bestStreakHorizSec);
        if (bestSecond>bestFirst){
            putMarkGenius(mark,board,rowsSecond,colsSecond,secondPlayer);
        }
        else{
            putMarkGenius(mark,board,rows,cols,mark);
        }
    }






}

