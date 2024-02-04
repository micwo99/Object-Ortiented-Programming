public class PlayerFactory {

    private static final String CLEVER= "clever";
    private static final String WHATEVER= "whatever";
    private static final String HUMAN= "human";
    private static final String GENIUS = "genius";


    public static Player buildPlayer(String type) {
            if(type.equals(CLEVER)){
                return new CleverPlayer();
            }
            if(type.equals(WHATEVER)){
                return new WhateverPlayer();
            }
            if(type.equals(HUMAN)){
                return new HumanPlayer();
            }
            if(type.equals(GENIUS)){
                return new GeniusPlayer();
            }

            return null;
    }
}
