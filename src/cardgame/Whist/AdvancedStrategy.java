/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgame.Whist;

import cardgame.cards.Card;
import cardgame.cards.Hand;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author jsc16gru
 */
public class AdvancedStrategy implements Strategy {

    private Card[] set;
    private int id;
    private int partnerID;
    
    // these arrays are used to card count at the end of a games these should 
    // be full with cards. 
    private Card[] spade;
    private Card[] club;
    private Card[] heart;
    private Card[] diamond;
    
    // this is the contructor 
    public AdvancedStrategy()
    {
        set = new Card[4];
        spade = new Card[13];
        club = new Card[13];
        heart = new Card[13];
        diamond = new Card[13];
    }
      
    // finds the id of this play that the strategy is relating too
    public void findPartner(Trick t)
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
        
        id = a;
        partnerID = partner;
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
               // System.out.println(temp); // use for testing
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
    
    // find the position to put a card in an array for a suit
    public int arrayPosition(Card.Rank rank)
    {
        int position = 0;
        int number = Card.Rank.getValue(rank);
         if(number >= 2 && number <= 9)
                    {
                        position = number - 2;
                    }
                    else if (number == 10)
                    {
                        switch (rank) {
                            case JACK:
                                position = 9;
                                break;
                            case QUEEN:
                                position = 10;
                                break;
                            case KING:
                                position = 11;
                                break;
                            case TEN:
                                position = 8;
                                break;
                            default:
                                break;
                        }
                    }
                    else if(number == 11)
                    {
                        position = 12;
                    }
        return position;
    }
    
    //the next four function find out if for a given suit how many cards above
    //a card that has been pasted into the function have still to be played 
    // 0in the game   
    public int isItTheBestSpadeCard(Card bestCard)
    {
        Card.Rank rank = bestCard.getRank();
        int posisition = arrayPosition(rank);
        posisition++;
        int cardStillInPlay = 0;
        System.out.println("HIGHER CARD IN MEMORY");
        while(posisition < (spade.length))
        {
            
            if(spade[posisition] == null)
            {
                cardStillInPlay++;
            }
            else 
            {
                System.out.println(posisition + " " + spade[posisition].toString());
            }
            posisition++;
        }
        System.out.println(" ");
        
        return cardStillInPlay;
    }
    
    public int isItTheBestClubCard(Card bestCard)
    {
        Card.Rank rank = bestCard.getRank();
        int posisition = arrayPosition(rank);
        posisition++;
        int cardStillInPlay = 0;
        System.out.println("HIGHER CARD IN MOMORY");
        while(posisition < (club.length))
        {
            
            if(club[posisition] == null)
            {
                cardStillInPlay++;
            }
            else
            {
                System.out.println(posisition + " " + club[posisition].toString());
            }
            posisition++;
        }
        System.out.println(" ");
        
        return cardStillInPlay;
    }
    
    public int isItTheBestHeartCard(Card bestCard)
    {
        Card.Rank rank = bestCard.getRank();
        int posisition = arrayPosition(rank);
        posisition++;
        int cardStillInPlay = 0;
        System.out.println("HIGHER CARD IN MEMORY");
        while(posisition < (heart.length))
        {
            
            if(heart[posisition] == null)
            {
                cardStillInPlay++;
            }
            else 
            {
                System.out.println(posisition + " " + heart[posisition].toString());
            }
            posisition++;
        }
        System.out.println(" ");
        
        return  cardStillInPlay;
    }
    
    public int isItTheBestDiamondCard(Card bestCard)
    {
        Card.Rank rank = bestCard.getRank();
        int posisition = arrayPosition(rank);
        posisition++;
        int cardStillInPlay = 0;
        System.out.println("HIGHER CARD IN MEMORY");
        while(posisition < (diamond.length))
        {
           
            if(diamond[posisition] == null)
            {
                cardStillInPlay++;
            }
            else 
            {
                 System.out.println(posisition + " " + diamond[posisition].toString());
            }
            posisition++;
        }
        System.out.println(" ");
        
        return cardStillInPlay;
    }
    
    // this is the method that chooses which card you wil play this trick
    // the method is passed the players hand and the trick so far
    // this strategy uses card counting  
    // this is mostly used to find card to play that have no cards of higher 
    // value left
    // e.g. can play a ten of spade if the jack, queen, king, ace of spade have 
    // already be played
    @Override
    public Card chooseCard(Hand h, Trick t) {
        // first it sorts the hand into rank order
        h.sortByRank();
        ArrayList<Card> sort = new ArrayList<>(h.getHand());
        ArrayList<Card> leads = new ArrayList<>();
        ArrayList<Card> trump = new ArrayList<>();
        ArrayList<Card> higher = new ArrayList<>();
        int[] store = new int[3];
        int[] higherBest = new int[2];
        higherBest[0] = 14;
        int highest = 0;
        int temp = 0;
        Card.Rank pictureRank = Card.Rank.TEN;
        
        set = null;
        set = t.getSet();
        // It works out what the player id is and what its partners id is
        findPartner(t);
        Card bestCard = null; 
        int numStillInPlay = 14;
        
        // resets internal memory if a new deck has been given 
        if(h.getHand().size() == 13)
        {
            spade = new Card[13];
            club = new Card[13];
            heart = new Card[13];
            diamond = new Card[13];
        }

        System.out.println("hand size "+ h.getHand().size());
        System.out.println(h.toString());
        Card.Suit trumps = t.getTrumps();      
        
        // if your id is not the stting id
        if(t.getStartingPlayer() != id)
        {
            // find the highest value card in the trick and which player it 
            // belongs to  
            store = findHigherValue(4,t);
            highest = store[0];
            System.out.println("highest " + highest);
            
            // find the lead suit for this trick
            Card.Suit lead = t.getLeadSuit();
//            System.out.println(lead); //use for testing
            // sort all card of the lead suit 
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
                
                // this works out if in the case it is necessary whether if the 
                // highest card in the trick is a king, queen or jack 
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
                
                
                
                // this for loop if used to find all card of the lead suit that
                // have a higher value than the highest in the trick 
                for(int x = 0; x < leads.size(); x++)
                {
                    temp = Card.Rank.getValue(leads.get(x).getRank());
                    if( temp >= highest)
                    {
                        if(temp == highest)
                        {
                            if(leads.get(x).getRank().ordinal() > pictureRank.ordinal())
                            {
                                higher.add(leads.get(x));
                            }
                        }
                        else
                        {
                            higher.add(leads.get(x));
                        }
                    }
                }
                
                // if the current highest card is not your partner
                if(store[1] != partnerID)
                {
                    // if there are cards that are better than the highest card
                    if(higher.size() > 0)
                    {
                        // this check which card in that arraylist is the best  
                        // to be played
                        for(int x = 0; x < higher.size(); x++)
                        {
                            bestCard = higher.get(x);
                            switch (bestCard.getSuit()) {
                                case SPADE:
                                    numStillInPlay = isItTheBestSpadeCard(bestCard);
                                    break;
                                case CLUB:
                                    numStillInPlay = isItTheBestClubCard(bestCard);
                                    break;
                                case HEART:
                                    numStillInPlay = isItTheBestHeartCard(bestCard);
                                    break;
                                case DIAMOND:
                                    numStillInPlay = isItTheBestDiamondCard(bestCard);
                                    break;
                                default:
                                    break;
                            }        
                        
                            // if there are no card that have not been played 
                            // that are better than this card it must be played
                            // this is mostly here for testing resons
                            if(numStillInPlay == 0)
                            {
                                System.out.println("no dout best lead card to play");
                                bestCard = higher.get(x);
                                return bestCard;
                            }
                            else if(numStillInPlay < higherBest[0])
                            {
                                higherBest[0] = numStillInPlay;
                                higherBest[1] = x;
                            }
                        }
                    
                        System.out.println("best highest card to play");
                        bestCard = higher.get(higherBest[1]);
                        return bestCard;
                    }
                    else {
                        System.out.println("can't find higher playing lowest lead possible");
                        bestCard = leads.get(0);
                        return bestCard;
                    }
                }
                // if partner is winning
                else 
                { 
                    System.out.println("partner winning");
                    bestCard = leads.get(0);
                    return bestCard;
                }
            }
            else
            { 
                // if the lead card is not your partner
                if(store[1] != partnerID)
                {  
                    // creates arraylist of trump cards
                    for(int x = 0; x < sort.size(); x++)
                    {
                        if(sort.get(x).getSuit().equals(trumps))
                        {
                            trump.add(sort.get(x));
                        }
                    }

                    // if you have trump cards play best trump card using 
                    // card counting
                    if(!trump.isEmpty())
                    {
                        Collections.sort(trump, new Card.compareRank());
                        bestCard = trump.get((trump.size()-1));
                        switch (bestCard.getSuit()) {
                            case SPADE:
                                numStillInPlay = isItTheBestSpadeCard(bestCard);
                                break;
                            case CLUB:
                                numStillInPlay = isItTheBestClubCard(bestCard);
                                break;
                            case HEART:
                                numStillInPlay = isItTheBestHeartCard(bestCard);
                                break;
                            case DIAMOND:
                                numStillInPlay = isItTheBestDiamondCard(bestCard);
                                break;
                            default:
                                break;
                        }

                        // will only use trump if there is no chance of it not 
                        // loosing
                        if(numStillInPlay > 0)
                        {
                            System.out.println("can't risk a trump");
                            bestCard = sort.get(0);
                            return bestCard;
                        }
                        else 
                        { 
                            System.out.println("trump will win");
                            bestCard = trump.get((trump.size()-1));
                            return bestCard;
                        }
                    }
                    // if nothing better cn be played play the worst card in your 
                    // hand
                    else 
                    { 
                        System.out.println("can't play anything better");
                        bestCard = sort.get(0);
                        return bestCard;
                    }
                }
                // if lead card is partners then don't risk trumping him
                else 
                {
                    System.out.println("partner is winning don't risk trumping him");
                    bestCard = sort.get(0);
                    int a = 1;
                    while(bestCard.getSuit() == trumps)
                    {
                        if(a == sort.size())
                        {
                            break;
                        }
                        bestCard = sort.get(a);
                        a++;
                    }
                    return bestCard;
                }
            }
        }
        // if you are the first one to play a card 
        else 
        {
            Card keepCard = null;
            // check all card going for highest value to lowest value 
            for(int x = 0; x < sort.size()-1; x++)
            {
               bestCard = sort.get(sort.size() - (x+1));
                System.out.println(bestCard.toString());
               switch (bestCard.getSuit()) {
                        case SPADE:
                            numStillInPlay = isItTheBestSpadeCard(bestCard);
                            break;
                        case CLUB:
                            numStillInPlay = isItTheBestClubCard(bestCard);
                            break;
                        case HEART:
                            numStillInPlay = isItTheBestHeartCard(bestCard);
                            break;
                        case DIAMOND:
                            numStillInPlay = isItTheBestDiamondCard(bestCard);
                            break;
                        default:
                            break;
                    }
                System.out.println("looking of card");
                    // if card has none higher than it will be sorted
                    if(numStillInPlay == 0)
                    {
                        // if it is the trump suit it will be play immediately
                        if(bestCard.getSuit().equals(trumps))
                        {
                            System.out.println("playing the best trump");
                            return bestCard;
                        }
                        keepCard = bestCard; 
                    }
                   
            }
            // if there was a suitable card from the last loop then play it
            if(keepCard != null)
            {
                System.out.println("keepcard can't loose");
                return keepCard;
            }
            // else play the worst card in the deck that is not part of the 
            // trump set
            else
            {
                bestCard = sort.get(0);
                int a = 1;
                while(bestCard.getSuit() == trumps)
                {
                    if(a == sort.size())
                    {
                        break;
                    }
                    bestCard = sort.get(a);
                    a++;
                }
                System.out.println("can't win this first play");
                return bestCard; 
            }
        }
    }

    // this update the array's of each suit in this class so that the card 
    // counting methods will work
    // the complete trick is pasted to this method 
    @Override
    public void updateData(Trick c) {
        Card.Rank rank;
        int number = 0;
        set = null;
        set = c.getSet();
        
        for (int x = 0; x < (set.length); x++) {
            rank = set[x].getRank();
            number = arrayPosition(rank);
            
            switch (set[x].getSuit()) {
                case SPADE:
                    spade[number] = set[x];
                    break;
                    
                case CLUB:
                    club[number] = set[x];
                    break;
                    
                case HEART:
                    heart[number] = set[x];
                    break;
                    
                case DIAMOND:
                    diamond[number] = set[x];
                    break;
                default:
                    break;
            }
        }   
    }
    
}
