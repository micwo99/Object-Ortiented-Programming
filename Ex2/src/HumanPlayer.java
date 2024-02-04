
import java.util.Scanner;
class HumanPlayer implements Player {
    public HumanPlayer(){}

    /**
     * the function play a single turn of a player if the function sucess to put a mark on the board the function
     * has finished, if not we ask the user until we can put the mark on the board
     * @param board a Given board
     * @param mark X or O
     */
    public void playTurn(Board board, Mark mark){
        boolean x=true;
        while(x) {
            Scanner in = new Scanner(System.in);

            System.out.println("Player"+mark+", type coordinates: ");
            int input = in.nextInt();

            int col = (input % 10);
            int row= (input-col)/10;

            if (board.putMark(mark, row, col)) {
                x = false;
            }
            else {
                System.out.println("Invalid coordinates, type again: ");
            }
        }

    }

}
