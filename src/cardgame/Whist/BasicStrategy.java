package cardgame.Whist;

import cardgame.cards.Card;
import cardgame.Whist.BasicPlayer;
import cardgame.cards.Card.*;
import cardgame.cards.Hand;
import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * @author jsc16gru
 */
public class BasicStrategy implements Strategy{
    
    private Card[] set;
    
    
    // finds the id of this play that the strategy is relating too
    public int findPartner(Trick t)
    {
        int partner = 0;
        int startingPlayer = t.getStartingPlayer();
        
        int a = startingPlayer;
        
        //find your place in the set as you should to the first null in the 
        //array after the starting player
        while(set[a] != null)
        {

            if(a == 3)
            {
                a = 0;
            }
            else 
            {
                a++;
            }
        }
        
        if(a > 1)
        {
            partner = a - 2;
        }
        else if(a <= 1)
        {
            partner = a + 2;
        }
        
        return partner;
    }
    
    //find the highest card in the trick and the id of the player 
    //its position in the array
    public int[] findHigherValue(int size, Trick t)
    {
        int higher = 0;
        int temp = 0;
        int setPosition = 0;
        int jqk = 0;        // this varible is for if the value of the card
                            // is 10 whether it is a ten, jack, queen or king                     
        Card.Suit lead = t.getLeadSuit();
        
        for(int x = 0; x < size; x++)
        {
            if(set[x] != null)
            {
                temp = Card.Rank.getValue(set[x].getRank());
                System.out.println(temp);
                if(temp >= higher && set[x].getSuit().equals(lead))
                {
                    if(temp == 10)
                    {
                        switch (set[x].getRank()) {
                            case JACK:
                                jqk = 1;
                                break;
                            case QUEEN:
                                jqk = 2;
                                break;
                            case KING:
                                jqk = 3;
                                break;
                            default:
                                break;
                        }
                    }
                    higher = temp;
                    setPosition = x;
                }
            }
        }
        
        int[] store = {higher,setPosition,jqk};
        return store;
    }
    
    // choose a card using the strategry 
    // if first player pick largest card in hand if more than one pick at random
    // if parnter is winning play lowest card it can (e.g. loses lead card then 
    // not lead if you have not lead
    // if parnter in not winning or has not played comare to current best card 
    // play higher if possible, if not play lowest card in lead, if you can't 
    // play lead play trump and if none of these are possible play the lowest 
    // can posiible  
    @Override
    public Card chooseCard(Hand h, Trick t) {
        h.sortByRank();
        ArrayList<Card> sort = new ArrayList<>(h.getHand());
        ArrayList<Card> leads = new ArrayList<>();
        ArrayList<Card> trump = new ArrayList<>();
        int[] store;
        int highest = 0;
        int temp = 0;
        Card.Rank pictureRank = Card.Rank.TEN;
        
        set = t.getSet();
        boolean hasCard = t.getHadCards();
        System.out.println("hand size "+ h.getHand().size());
        
        
        int partner = findPartner(t);
       
        int partnerValue = 0;
        if(set[partner] != null)
        {
            partnerValue = Rank.getValue(set[partner].getRank());
        }
  
        //Collections.sort(sort, new Card.compareRank());
        // check to see if there are cards in trick in there are none you are 
        // the first player
        if(hasCard)
        {
            // create arrayList of all lead cards
            Card.Suit lead = t.getLeadSuit();
            for(int x = 0; x < sort.size(); x++)
            {
                if(sort.get(x).getSuit().equals(lead))
                {
                    leads.add(sort.get(x));
                }
            }
            
            // if you have lead cards
            if(!leads.isEmpty())
            {
                // sort leads cards into rank order
                Collections.sort(leads, new Card.compareRank());
                store = findHigherValue(4,t);
                if(store[2] > 0)
                {
                    switch (store[2]) {
                        case 3:
                            pictureRank = Card.Rank.KING;
                            break;
                        case 2:
                            pictureRank = Card.Rank.QUEEN;
                            break;
                        case 1:
                            pictureRank = Card.Rank.JACK;
                            break;
                        default:
                            break;
                    }
                }
                
                highest = store[0];
                temp = Rank.getValue(leads.get(leads.size()-1).getRank());
                
                // if the current highest card is not your partner
                if(store[1] != partner)
                {
                    // check to see if your highest card it higher than theirs
                    if( temp >= highest)
                    {
                        if(temp == highest)
                        {
                            if(leads.get(leads.size()-1).getRank().ordinal() > pictureRank.ordinal())
                            {
                                return leads.get(leads.size()-1);
                            }
                            else
                            {
                                return leads.get(0);
                            }
                        }
                        else 
                        {
                            return leads.get(leads.size()-1);
                        }
                    }
                    // if not play lowest card in leads
                    else
                    {
                        return leads.get(0);
                    }
                }
                // if is partner play lowest card in leads
                else 
                {
                    return leads.get(0);
                }
            }
            else 
            {
                // creates arraylist of trump cards
                Card.Suit trumps = t.getTrumps();
                for(int x = 0; x < sort.size(); x++)
                {
                    if(sort.get(x).getSuit().equals(trumps))
                    {
                        trump.add(sort.get(x));
                    }
                }
                
                // if you have trump cards play best trump card
                if(!trump.isEmpty())
                {
                    Collections.sort(trump, new Card.compareRank());
                    return trump.get(trump.size()-1);
                }
                // else play lowest card possible
                else 
                {
                    return sort.get(0);
                }
            }
        }
        //else play highest card possible
        else
        {
            return sort.get(sort.size()-1);
        }
    }

    /**
    * Update internal memory to include completed trick c
    * @param c 
    */ 
    @Override
    public void updateData(Trick c) {
        set = c.getSet();
    }
    
    // use for testing
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
        
        trick.setTrumps(Card.Suit.SPADE);
        
        trick.setCard(card1, bP);
        
        ArrayList<Card> list = new ArrayList();
        list.add(card2);
        list.add(card3);
        list.add(card4);

        Hand hand = new Hand(list);
        
        Strategy basic = new BasicStrategy();
        
        bP1.setStrategy(basic);
        
        Card pick = basic.chooseCard(hand, trick);
        
        System.out.println(pick);
    }
    
}
