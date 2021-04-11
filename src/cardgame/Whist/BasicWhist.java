package cardgame.Whist;

import cardgame.cards.Card;
import cardgame.Whist.Trick;
import cardgame.Whist.BasicStrategy;
import cardgame.Whist.BasicPlayer;
import cardgame.Whist.AdvancedStrategy;
import cardgame.cards.Deck;
import cardgame.cards.Card.Suit;
import cardgame.cards.Deck;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ajb
 */
public class BasicWhist {
    static final int NOS_PLAYERS=4;
    static final int NOS_TRICKS=13;
    static final int WINNING_POINTS=7;
    int team1Points=0;
    int team2Points=0;
    int team1Winning = 0;
    Player[] players;
    static int winning = 0;
    public BasicWhist(Player[] pl){
        players = pl;
    }
    
    //the whole desk is dealt out to the player so each 
    //players starts with 13 cards  
    public void dealHands(Deck newDeck){
        for(int i=0;i<NOS_TRICKS*4;i++){
            players[i%NOS_PLAYERS].dealCard(newDeck.deal());
        }
    }
    
    // this method is run to play a trick
    // a new trick is created and each player places a card in to it 
    // using there strategy
    public Trick playTrick(Player firstPlayer){
        Trick t=new Trick(firstPlayer.getID());
        int playerID=firstPlayer.getID();
        for(int i=0;i<NOS_PLAYERS;i++){
            int next=(playerID+i)%NOS_PLAYERS;
            System.out.println(next);
            Card card = players[next].playCard(t);
            System.out.println(card);
            t.setCard(card,players[next]);
        }
        return t;
    }
    
    //this method plays a whole game of whist and asigns points to the winners
    // dealHands() and PlayTrick() are called here
    public void playGame(){
        int team1Rounds = 0;
        int team2Rounds = 0;
        Deck d=new Deck();
        dealHands(d);
        int firstPlayer=(int)(NOS_PLAYERS*Math.random());
        Suit trumps=Suit.randSuit();
        System.out.print("The Trump is: ");
        System.out.println(trumps);
        Trick.setTrumps(trumps);
        for(int i=0;i<NOS_PLAYERS;i++)
        {
            players[i].setTrumps(trumps);
        }
        
        for(int i=0;i<NOS_TRICKS;i++){
            Trick t=playTrick(players[firstPlayer]);  
            System.out.println(t.toString());
            try 
            {
                firstPlayer=t.findWinner();
            } catch (Trick.WinningCardNotFoundException ex) 
            {
                Logger.getLogger(BasicWhist.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Winner = "+firstPlayer);
            System.out.println(t.getSet()[firstPlayer] + "\n");
            for(int x = 0; x < NOS_PLAYERS; x++)
            {
                players[x].viewTrick(t);
            }
       
            if(firstPlayer == 0 || firstPlayer == 2)
            {
                team1Rounds++;
            }
            else if(firstPlayer == 1 || firstPlayer == 3)
            {
                team2Rounds++;
            }
        }
        
        // a team will only score point for every round they win after there 6 
        // win round
        team1Rounds = team1Rounds - 6;
        team2Rounds = team2Rounds - 6;
        
        if(team1Rounds > 0)
        {
            team1Points = team1Points + team1Rounds;
        }
        if(team2Rounds > 0)
        {
            team2Points = team2Points + team2Rounds;
        }
        
        
        System.out.println("team1 has: " + team1Points);
        System.out.println("team2 has: " + team2Points);
        System.out.println(" ");
    }

    // this method runs until A team has reach the WINNING_POINTS which is 7
    // at which point it is declared the winner
    public void playMatch(){
        team1Points=0;
        team2Points=0;
        while(team1Points<WINNING_POINTS && team2Points<WINNING_POINTS){
            playGame();
        }
        if(team1Points>=WINNING_POINTS)
        {
            System.out.println("Winning team is team1 with "+team1Points);
            // use to find the advanced AI win persentage
            team1Winning++;
        }
        else if(team2Points>=WINNING_POINTS)
            System.out.println("Winning team is team2 with "+team2Points);
            
    }
    
    // this created the player assigns them there partners 
    // and sets there strategy 
    public static void playTestGame(){
        Player[] p = new Player[NOS_PLAYERS];
        int a = 2;
        for(int i=0;i<p.length;i++)
        {
            p[i]= new BasicPlayer(i,a);
            if(a == 3)
            {
                a = 0;
            }
            else {a++;}
        }
        
        for(int i=0; i<p.length;i++)
        {
            p[i].setStrategy(new BasicStrategy());
        }
        
        BasicWhist bg=new BasicWhist(p);
        bg.playMatch(); //Just plays a single match
}
    
    // this runs a game of whist with a team use the advance strategy team1 and 
    // a team using basic strategy team2 
    public static void advancedGame()
    {
        Player[] p = new Player[NOS_PLAYERS];
        int a = 0, b = 1, c = 2, d = 3;
        
        p[0] = new BasicPlayer(0,2);
        p[1] = new BasicPlayer(1,3);
        p[2] = new BasicPlayer(2,0);
        p[3] = new BasicPlayer(3,1);
        
        p[0].setStrategy(new AdvancedStrategy());
        p[2].setStrategy(new AdvancedStrategy());
        
        p[1].setStrategy(new BasicStrategy());
        p[3].setStrategy(new BasicStrategy());
        
            BasicWhist bg = new BasicWhist(p);
            bg.playMatch();
            winning = winning + bg.team1Winning;
             
    }
    
    // runs the advanced game 1000 time and then works out the persentage of 
    // time the advanced AI (players 0 and 2) win  
    public static void advancedGameTest()
    {
        double win;
        for(int x = 0; x < 1000; x++)
        {
            
            System.out.println("RUN " + (x+1) + "!!!!!!!!!!!!");
            advancedGame();
            
        }
        
        win = winning;
        win = win/1000;
        win = win*100;
        System.out.println(win + "%");
    }
    
    // runs the basic game (playTestGame()) the advance game (advancedGame()) 
    // and the test to see how much better advanced IA is (advancedGameTest())
    // human game is run in the main of human Strategy to stop confusion
    public static void main(String[] args) {
        System.out.println("enter 0 for playTestGame(), 1 for advancedGame() "
                + "and 2 for advancedGameTest()");
        Scanner scan = new Scanner(System.in);
        int newInput = 4;
        while(newInput > 2 || newInput < 0)
        {
            try {
                newInput=Integer.parseInt(scan.nextLine());

                switch (newInput) {
                    case 0:
                        System.out.println("running playTestGame()");
                        playTestGame();
                        break;
                    case 1:
                        System.out.println("running advancedGame()");
                        advancedGame();
                        break;
                    case 2:
                        System.out.println("running advancedGameTest()");
                        advancedGameTest();
                        break;
                    default:
                        break;
                }
            }
            catch(NumberFormatException e)
            {
                System.out.println("That is not an integer, please try again." );
            }
            
            
        }
    }
    
}
