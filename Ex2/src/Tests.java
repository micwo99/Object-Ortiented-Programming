import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


class BoardTest {
    Random random = new Random();

    @Test
    public void getSize(){
        for(int i = 0; i < 100; i++){
            int rand_int = random.nextInt(100);
            Board board = new Board(rand_int);
            assertEquals(rand_int, board.getSize());
        }
    }
    @Test
    public void CheckDefaultConstructor(){
        Board board = new Board();
        assertEquals(board.getSize(), 4);
        for(int row = 0; row < board.getSize(); row++){
            for(int col=0; col < board.getSize(); col++){
                assertEquals(board.getMark(row, col), Mark.BLANK);
            }
        }
    }
    @Test
    public void getBoard() {
        Board board = new Board(1);
        assertArrayEquals(new Mark[][]{{Mark.BLANK}}, board.getBoard());
        for (int k = 0; k < 100; k++) {
            int random_size = random.nextInt(100);
            Mark[][] expected = new Mark[random_size][random_size];
            for (int i = 0; i < random_size; i++) {
                for (int j = 0; j < random_size; j++) {
                    expected[i][j] = Mark.BLANK;
                }
            }
            board = new Board(random_size);
            assertArrayEquals(expected, board.getBoard());
        }
    }
    @Test
    public void putMark(){
        Board board = new Board(1);
        board.putMark(Mark.O, 0, 0);
        assertArrayEquals(board.getBoard(), new Mark[][]{{Mark.O}});
        //test invalid index
        assert(!board.putMark(Mark.O, 1, 0));
        assert (!board.putMark(Mark.O, 0, 1));
        assert(!board.putMark(Mark.X, -1, 0));
        assert(!board.putMark(Mark.X, 0, -1));
        //already marked
        assert(!board.putMark(Mark.X, 0, 0));
    }
    @Test
    public void getMark(){
        Board board = new Board(1);
        board.putMark(Mark.X, 0, 0);
        assertEquals(board.getMark(0, 0 ),Mark.X);
        assertNotEquals(board.getMark(0, 0), Mark.O);
        assertNotEquals(board.getMark(0, 0), Mark.BLANK);
        //invalid index
        assertEquals(board.getMark(0 ,1), Mark.BLANK);
        assertEquals(board.getMark(1 ,1), Mark.BLANK);
        assertEquals(board.getMark(0 ,-1), Mark.BLANK);
        assertEquals(board.getMark(-1 ,1), Mark.BLANK);
    }
}

class PlayerTest{
    private static final PlayerFactory playerFactory = new PlayerFactory();
    private static final String[] ValidPlayerType = new String[]{"clever", "genius", "whatever"};
    @Test
    public void PlayerputMark(){
        for(String player_type : ValidPlayerType) {
            Board board = new Board(1);
            Player Whatever = playerFactory.buildPlayer(player_type);
            Whatever.playTurn(board, Mark.O);
            assertEquals(Mark.O, board.getMark(0, 0));
        }
    }
    @Test
    public void PlayerDoNotKnowMark() {
        for(String player_type : ValidPlayerType) {
            Board board = new Board(2);
            Player player = playerFactory.buildPlayer(player_type);
            player.playTurn(board, Mark.X);
            player.playTurn(board, Mark.O);
            player.playTurn(board, Mark.O);
            int X_counter = 0;
            int O_counter = 0;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    if (board.getMark(i, j) == Mark.O) {
                        O_counter += 1;
                    }
                    if (board.getMark(i, j) == Mark.X) {
                        X_counter += 1;
                    }
                }
            }
            assertEquals(X_counter, 1);
            assertEquals(O_counter, 2);
        }
    }
    @Test
    public void PlayerCanPlayBothBoards(){
        for(String player_type : ValidPlayerType) {
            Board board1 = new Board(1);
            Board board2 = new Board(1);
            Player player = playerFactory.buildPlayer(player_type);
            player.playTurn(board1, Mark.O);
            player.playTurn(board2, Mark.X);
            assertEquals(board1.getMark(0, 0), Mark.O);
            assertEquals(board2.getMark(0, 0), Mark.X);
        }
    }
    @Test
    public void LowerAndUpperCase(){
        for(String player_type : ValidPlayerType){
            Player player1 = playerFactory.buildPlayer(player_type);
            Player player2 = playerFactory.buildPlayer(player_type);
            assertEquals(player2.getClass(), player1.getClass());
        }
    }
}

class GameTest{
    private static final PlayerFactory playerFactory = new PlayerFactory();
    private static final String[] ValidPlayerType = new String[]{"clever", "genius", "whatever"};
    private static final RendererFactory rendererFactory  = new RendererFactory();
    private static final Random rand = new Random();

    @Test
    public void SizeAndWinStrikeDefault(){
        Player player_1 = playerFactory.buildPlayer("clever");
        Player player_2 = playerFactory.buildPlayer("whatever");
        for(int i= 0; i < 10; i++) {
            int size = rand.nextInt(9);
            Renderer renderer = rendererFactory.buildRenderer("none", size);
            Game game = new Game(player_1, player_2, size, size + 1, renderer);
            assertEquals(game.getWinStreak(), size);
        }
    }
    @Test
    public void WinStrikeSmallerThan2(){
        Player player_1 = playerFactory.buildPlayer("clever");
        Player player_2 = playerFactory.buildPlayer("whatever");
        Game game = new Game(player_1, player_2, 4, 1, rendererFactory.buildRenderer("console", 4));
        assertEquals(game.getWinStreak(), 1);

    }
    @Test
    public void ConstructorWithDefaultValue(){
        Player player_1 = playerFactory.buildPlayer("clever");
        Player player_2 = playerFactory.buildPlayer("whatever");
        Renderer renderer = rendererFactory.buildRenderer("ConsOle", 9);
        Game game = new Game(player_1, player_2, renderer);
        assertEquals(game.getWinStreak(), 3);
    }
    @Test
    public void GameWinner(){
        int size = 2;
        Player player_1 = playerFactory.buildPlayer("whatever");
        Player player_2 = playerFactory.buildPlayer("whatever");
        Renderer renderer = rendererFactory.buildRenderer("console", size);
        for(int i = 0; i < 10; i++){
            Game game = new Game(player_1, player_2, size, size, renderer);
            assertEquals(game.run(), Mark.X);
        }
    }
    @Test
    public void Tie(){

        int size = 10;
        int winStrike = 10;
        int Tie_Counter = 0;
        Player player_1 = playerFactory.buildPlayer("whatever");
        Player player_2 = playerFactory.buildPlayer("whatever");
        Renderer renderer = rendererFactory.buildRenderer("none", size);
        for(int i =0; i < 1000; i++){
            Game game = new Game(player_1, player_2, size, winStrike, renderer);
            if(game.run() == Mark.BLANK){
                Tie_Counter += 1;
            }
        }
        assert(Tie_Counter >= 900);
    }
}




