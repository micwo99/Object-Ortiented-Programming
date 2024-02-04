public class Tournament {
    private int rounds;
    private Renderer renderer;
    private Player[] players;
    private int winFirst;
    private int winSecond;
    private int draw;
    private static final  String ERROR_MSG="Choose a player, and start again\nThe players: [human,clever,whatever,genius]";

    public Tournament(int rounds, Renderer renderer, Player[] players){
        this.rounds=rounds;
        this.renderer=renderer;
        this.players=new Player[2];
        for(int i = 0; i < 2; i++) {
            this.players[i]=players[i];
        }
        winFirst=0;
        winSecond=0;
        draw=0;
    }

    public void playTournament(int size,int winStreak,String[] playerNames){
        int counter=0;
        Player playerX;
        Player playerO;
        while(counter!=rounds){
            if (counter%2==0){
                playerX=players[0];
                playerO=players[1];
            }
            else{
                playerX=players[1];
                playerO=players[0];
            }
            Game game= new Game(playerX,playerO,size,winStreak,renderer);
            Mark winner=game.run();
            if(winner==Mark.X){
                if (counter%2==0){
                    winFirst++;
                }
                else{
                    winSecond++;
                }
            }
            if(winner == Mark.O){
                if (counter%2==0){
                    winSecond++;
                }
                else{
                    winFirst++;
                }

            }
            if(winner == Mark.BLANK){
                draw++;
            }
            counter++;
        }
        String phrase=String.format("Player 1, %s won: %d\nPlayer 2, %s won: %d\nTies: %d"
                ,playerNames[0],winFirst,playerNames[1],winSecond,draw);
        System.out.println("######### Results #########");
        System.out.println(phrase);
    }

    public static void main(String[] args) {
        int round= Integer.parseInt(args[0]);
        int size =Integer.parseInt(args[1]);
        int winStreak=Integer.parseInt(args[2]);
        Player p1 = PlayerFactory.buildPlayer(args[4].toLowerCase());
        Player p2 =PlayerFactory.buildPlayer(args[5].toLowerCase());
        if(p1==null || p2==null){
            System.out.println(ERROR_MSG);
        }
        else {
        Renderer r=RendererFactory.buildRenderer(args[3].toLowerCase(),size);
        Tournament tournament= new Tournament(round,r,new Player[]{p1,p2});
        tournament.playTournament(size,winStreak, new String[]{args[4], args[5]});}

    }
}
