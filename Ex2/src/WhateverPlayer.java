import java.util.Random;

class WhateverPlayer implements Player{

    /**
     *play a turn on our game according to a whatever player
     */
    public void playTurn(Board board, Mark mark) {
        Random rand= new Random();
        int[][] emptyCases= emptyCases(board);
        int randIndex= rand.nextInt(emptyCases.length);
        int[] coor =emptyCases[randIndex];
        board.putMark(mark,coor[0],coor[1]);


    }

    /**
     * the function returns a list of all the empty cases in our board
     */
    private int[][] emptyCases(Board board){
        int length = 0;
        int counter=0;
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col <board.getSize() ; col++) {
                if (board.getMark(row,col)==Mark.BLANK){
                    length++;
                }

            }

        }
        int[][] empty =new  int[length][2];
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col <board.getSize() ; col++) {
                if (board.getMark(row,col)==Mark.BLANK){
                    int[] coordinates= new int[]{row, col};
                    empty[counter]=coordinates;
                    counter++;
                }

            }

        }
        return empty;
    }
}


