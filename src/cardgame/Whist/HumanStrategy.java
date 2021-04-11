/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.Whist;

import cardgame.cards.Hand;
import cardgame.cards.Card;
import cardgame.Whist.BasicWhist;
import cardgame.Whist.Trick;
import cardgame.Whist.BasicStrategy;
import cardgame.Whist.BasicPlayer;
import java.util.Scanner;
/**
 *
 * @author jsc16gru
 */
public class HumanStrategy implements Strategy{
    
    private Card[] set;
    private final int id;
    private final int partnerID;
    
    private static final int numOfPlayers = 4;
    
    // constructer mostly for just passing your id and partnerID 
    // easily into this class
    public HumanStrategy(int id, int partnerID)
    {
        this.id = id;
        this.partnerID = partnerID;
    }
    
    // check to see if you partner has played his card
    public boolean hasYourPartnerBeen()
    {
        boolean been = false;
        if(set[partnerID] != null)
        {
            been = true;
        }
        
        return been;
    }
    
    // this method use the strategry 
    // that the human pick each card using the information provide
    // this is the trump at that time, the lead car suit if it exist, 
    // your partners card if played yet   
    @Override
    public Card chooseCard(Hand h, Trick t) {
        
        int position = 14;
        int newInput = 0;
        Card chooseCard;
        Scanner scan = new Scanner(System.in);
        set = t.getSet();
        
        boolean partner = hasYourPartnerBeen();
        
        System.out.println("the trump is " + t.getTrumps());
        
        if(t.getStartingPlayer() == id)
        {
            System.out.println("you are the first player");
        }
        else
        {
            System.out.println("The first player is " + t.getStartingPlayer());
            System.out.println("the Lead suit is " + t.getLeadSuit());
            System.out.println(t.toString());
        }
        
        if(partner)
        {
            System.out.println("your partners card is");
            System.out.println(set[partnerID].toString() + " \n");
        }
        else 
        {
            System.out.println("your partner has not been");
        }
        
        System.out.println(h.toString());
        
        System.out.println("which card from your hand would you like to play");
        System.out.println("use number 0 to " + (h.getHand().size()-1));
        
        // scanner will only except and integer number and a number less than 
        // or equal to the number of cards in this hand
        while (position > (h.getHand().size()-1))
        {
            try {
                newInput=Integer.parseInt(scan.nextLine());
                position=newInput;
                if(position > (h.getHand().size()-1))
                {
                    System.out.println("card "+ position + " does not exist "
                            + "please enter another");
                }
            }
            catch(NumberFormatException e)
            {
                System.out.println("That is not an integer, please try again." );
            }
        }   
        
        chooseCard = (Card)h.getHand().get(position);
        
        return chooseCard;
    }

    // hands stratagy the complete trick
    @Override
    public void updateData(Trick c) {
        set = c.getSet();
    }
    
    // This create and array of 4 player player 0 is a BasicPlayer 
    // With a Human Stategy the rest are BasicPlayers with BasicStrategy
    // it uses the basicGame class to run the game to avoid duplicated code
    public static void humanGame(){
        Player[] p = new Player[numOfPlayers];
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
        
        p[0].setStrategy(new HumanStrategy(0,2));
        
        for(int i=1; i<p.length;i++)
        {
            p[i].setStrategy(new BasicStrategy());
        }
        
        BasicWhist bg=new BasicWhist(p);
        bg.playMatch();
         //Just plays a single match
}
    public static void main(String[] args) {
        humanGame();
    }
    
}
