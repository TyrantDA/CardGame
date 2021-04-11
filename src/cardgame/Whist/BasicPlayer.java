package cardgame.Whist;

import cardgame.cards.Card;
import cardgame.cards.Hand;

/**
 *
 * @author jsc16gru
 */
public class BasicPlayer implements Player{
    
    private Hand hand; 
    private int id;
    private int partnersId;
    private Strategy strategy;
    private Card.Suit trump;
    private Trick trick;
    
    // constructor 
    public BasicPlayer()
    {
        hand = new Hand();
        trump = null;
        trick = null;
    }
    
    // add gives id and partnerId a value
    public BasicPlayer(int id, int partnerId)
    {
        this();
        this.id = id;
        this.partnersId = partnerId;
    }

    // returns the size of the hand
    public int getHandsize()
    {
        return hand.getHand().size();
    }
    
    // add a card to the player hands 
    @Override
    public void dealCard(Card c) {
        hand.add(c);
    }

    // set the strategy of this player
    @Override
    public void setStrategy(Strategy s) {
        strategy = s;
    }

    // uses the strategy to pick a card then removes that card from your hand
    // and returns it
    @Override
    public Card playCard(Trick t) {
        //System.out.println(id);
        Card picked = strategy.chooseCard(hand, t);
        hand.remove(picked);
        
        return picked;
    }

    // gives player full trick
    @Override
    public void viewTrick(Trick t) {
        trick = t;
        strategy.updateData(t);
    }

    //set the tumps
    @Override
    public void setTrumps(Card.Suit s) {
        trump = s;
    }

    // returns the id of the player
    @Override
    public int getID() {
        return id;
    }
    
   
    
}
