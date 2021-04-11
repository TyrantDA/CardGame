package cardgame.Whist;
import cardgame.cards.Card;
import cardgame.Whist.BasicPlayer;
import cardgame.cards.Card.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Skeleton class for storing information about whist tricks
 * @author ajb
 */
public class Trick{
   private static Suit trumps;
   private int firstPlayer;
   private final Card[] set;
   private final int size = 4;
   private boolean hasCards;
   
   // constructs trick and gives firstplayer a value
   public Trick(int p)
   {
    firstPlayer = p;
    set = new Card[4];
    hasCards = false;
    
   }    //p is the lead player 
   
   // set trump
   public static void setTrumps(Suit s){
       trumps=s;
   }
    

    //@return the Suit of the lead card.  
    public Suit getLeadSuit(){
        if(set[firstPlayer].getSuit() == null)
        {
            System.out.println(set[firstPlayer].getSuit());
        }
        return set[firstPlayer].getSuit();
    }

    // Records the Card c played by Player p for this trick
    // @param c
    // @param p 
    public void setCard(Card c, Player p){
         int id = p.getID();
         set[id] = c;
         hasCards = true;
    }

    // Returns the card played by player with id p for this trick
    // @param p
    // @return players card  
    public Card getCard(Player p){
        int id = p.getID();
        return set[id];
    }
    
    // returns if a card has been played in this trick
    public Boolean getHadCards()
    {
        return hasCards;
    }
    
    // returns the trump
    public Suit getTrumps()
    {
        return trumps;
    }
    
    // returns the id starting player
    public int getStartingPlayer()
    {
        return firstPlayer;
    }
    
    // returns all current cards in the trick 
    public Card[] getSet()
    {
        return set;
    }
    

    // Finds the ID of the winner of a completed trick
    public int findWinner() throws WinningCardNotFoundException{
        Suit lead = getLeadSuit();
        ArrayList<Card> leadCards = new ArrayList<>();
        ArrayList<Card> trumpCards = new ArrayList<>(); 
        Card winningCard;
        
        // find all card in the trick that are part of the lead suit
        for(int x = 0; x < size; x++)
        {
            if(set[x].getSuit().equals(lead))
            {
                leadCards.add(set[x]);
            }
            // find all card in the trick of the trump suit
            else if(set[x].getSuit().equals(trumps))
            {
                trumpCards.add(set[x]);
            }
        }
        
        // if there are any trumps
        if(trumpCards.size() > 0)
        {
            //sort into rank order 
            Collections.sort(trumpCards, new compareRank());
            // highest card is the winner
            winningCard = trumpCards.get(trumpCards.size() - 1);
        }
        // if there are no trump card
        else
        {
            // sort into rank order
            Collections.sort(leadCards, new compareRank());
            // highest card is the winner
            winningCard = leadCards.get(leadCards.size() - 1);
        }
        
        // check to see if there was a winning card 
        for(int y = 0; y < size; y++)
        {
            if(winningCard.equals(set[y]))
            {
                return y;
            }
        }
        throw new WinningCardNotFoundException("No winning card found");
    }
    
    // returns a string of the cards in the trick
    public String toString()
    {
        StringBuilder trick = new StringBuilder();
        trick.append("trick").append(System.lineSeparator());
        for(int x = 0; x < 4; x++)
        {
            if(set[x] != null)
            {
                trick.append(set[x].toString()).append(System.lineSeparator());
            }
        }
        return trick.toString();
    }
    
    // if not winning card is found this exception is run
    class WinningCardNotFoundException extends Exception {
        
        WinningCardNotFoundException(String s) {
            super(s);
        }
        
    }
    
    // testing of player
    public static void main(String[] args) {
        
        Player bP = new BasicPlayer(0,2);
        Player bP1 = new BasicPlayer(1,3);
        Player bP2 = new BasicPlayer(2,0);
        Player bP3 = new BasicPlayer(3,1);
        Trick trick = new Trick(0);
        Card card1 = new Card(Card.Rank.ACE,Card.Suit.CLUB);
        Card card2 = new Card(Card.Rank.KING,Card.Suit.CLUB);
        Card card3 = new Card(Card.Rank.ACE,Card.Suit.HEART);
        Card card4 = new Card(Card.Rank.TWO,Card.Suit.SPADE);
        
        setTrumps(Card.Suit.SPADE);
        
        trick.setCard(card1, bP);
        trick.setCard(card2, bP1);
        trick.setCard(card3, bP2);
        trick.setCard(card4, bP3);
        
        int winner = 5;
       try 
       {
           winner = trick.findWinner();
       } 
       catch (WinningCardNotFoundException ex) 
       {
           Logger.getLogger(Trick.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        System.out.println(winner);
    }
}
