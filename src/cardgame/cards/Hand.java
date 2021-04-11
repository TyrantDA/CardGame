package cardgame.cards;


import cardgame.cards.Card;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;


public class Hand implements Serializable, Iterable<Card>{
    
    static final long serialVersionUID = 300;
    private ArrayList<Card> hand;
    private ArrayList<Card> order;
    private int totalMax;
    private int numberOfAces;
    private int spade;
    private int club;
    private int heart;
    private int diamond;
    
    // this is the hand contructor 
    public Hand()
    {
        hand = new ArrayList<>();
        order = new ArrayList<>();
    }
    
    // this is a contructor that allow use to pass and arraylist of card into 
    // the hand class to become the hand 
    public Hand(ArrayList<Card> card)
    {
        this();
        for(int x = 0; x < card.size(); x++)
        {
            hand.add(card.get(x));
            SuitCount(card.get(x));
            order.add(card.get(x));
        }
        HandValue();
        
    }
    
    // this is a contructor that allow use to pass another Hand to it   
    public Hand(Hand oldHand)
    {
        this();
        for(int x = 0; x < oldHand.hand.size(); x++)
        {
            hand.add(oldHand.hand.get(x));
            SuitCount(oldHand.hand.get(x));
            order.add(oldHand.hand.get(x));
        }
        HandValue();
    }
    
    // this is a contrutor that allow us to pass an array of cards to it
    public Hand(Card[] cards)
    {
        this();
        for(int x = 0; cards[x] != null; x++)
        {
            hand.add(cards[x]);
            SuitCount(cards[x]);
            order.add(cards[x]);
        }
        HandValue();
    }
    
    // return the hand arraylist
    public ArrayList getHand()
    {
        return hand;
    }
    
    // used to count the number of cards of each suit in the hand
    // it is passed a card and the counter for that suit is increased by one
    public void SuitCount(Card card)
    {
        switch (card.getSuit()) {
            case SPADE:
                spade++;
                break;
            case HEART:
                heart++;
                break;
            case DIAMOND:
                diamond++;
                break;
            case CLUB:
                club++;
                break;
            default:
                break;
        }
        
    }
    
    // used to decrease the suit count
    // it is passed a card and the counter for that suit is decreased by one
    public void SuitRemove(Card card)
    {
        switch (card.getSuit()) {
            case SPADE:
                spade--;
                break;
            case HEART:
                heart--;
                break;
            case DIAMOND:
                diamond--;
                break;
            case CLUB:
                club--;
                break;
            default:
                break;
        }
    }
    
    // this works out the total value of the hand and find the number of aces in 
    // your hand 
    public void HandValue()
    {
        int total = 0;
        numberOfAces = 0;
        totalMax = 0; 
        int temp;
        Card.Rank rank;
        
        for(int x = 0; x < hand.size();x++)
        {
            rank = hand.get(x).getRank();
            temp = Card.Rank.getValue(rank);
            
            if(temp == 11)
            {
                numberOfAces++;
            }
            
            total = total + temp;
        }
        
        totalMax = total;
    }
    
    // adds a card to the hand 
    public void add(Card card)
    {
        hand.add(card);
        SuitCount(card);
        order.add(card);
        HandValue();
    }
    
    // adds cards that are in an arrayList to the hand
    public void add(ArrayList<Card> cards)
    {
        for(int x = 0; x < cards.size(); x++)
        {
            add(cards.get(x));
        }
    }
    
    // adds cards that are in another hand to the hand
    public void add(Hand oldHand)
    {
            add(oldHand.hand);
        
    }
    
    // removes a perticular card from this hand by givin it the card that is 
    // wanted to be removed 
    public boolean remove(Card card)
    {
        int hold;
        for(int x = 0; x < hand.size(); x++)
        {
            if(hand.get(x).equals(card))
            {
                SuitRemove(hand.get(x));
                hand.remove(x);
                for(int z = 0; z < order.size(); z++)
                {
                    if(order.get(z).equals(card))
                    {
                        order.remove(z);
                    }
                }
                HandValue();
                return true;               
            }
        }
        return false;
    }
    
    // removes several card from this hand but passing the cards that are want
    // to be removed for this hand in the form of a hand object
    public boolean remove(Hand oldHand)
    {
        boolean temp = true;
        boolean hold = true;
        for(int x = 0; x < oldHand.hand.size(); x++)
        {
            temp = remove(oldHand.hand.get(x));
            
            if(!temp)
            {
                hold = false;
            }
        }
        HandValue();
        return hold;
    }
    
    // removes a card from this hand by given the position of it 
    // in the hand array
    public Card remove (int position)
    {
        Card removedCard = hand.get(position);
        SuitRemove(hand.get(position));
        hand.remove(position);
        
        for(int x = 0; x < order.size(); x++)
        {
            if(order.get(x).equals(removedCard))
            {
                order.remove(x);
            }
        }
        HandValue();
        return removedCard;
    }
    
    // sorts the cards in your hand into asending order
    public void sort()
    {
        Card card1;
        Card card2;
        int comp;
        boolean sorted = true;
        
        do 
        {
            for(int x = 0; x < (hand.size() - 1); x++)
            {
                card1 = hand.get(x);
                card2 = hand.get(x+1);
                comp = card1.compareTo(card2);
        
                if(comp == 1)
                {
                    hand.set(x,card2);
                    hand.set(x+1, card1);
                    sorted = false;
                }  
                else { sorted = true; }
            }
        } while(!sorted);
    }
    
    // this will sort the hand by the cards rank
    public void sortByRank()
    {
        Collections.sort(hand, new Card.compareRank());
    }
    
    // this returns the number of cards in this hand of a given suit
    public int countSuit(Card.Suit suit)
    {
        switch (suit) {
            case SPADE:
                return spade;
            case HEART:
                return heart;
            case DIAMOND:
                return diamond;
            case CLUB:
                return club;
            default:
                break;
        }
        
        return -1;
    }
    
    // this count the number of card of a given rank in this hand
    public int countRank(Card.Rank rank)
    {
        int count = 0;
        
        for(int x = 0; x < hand.size(); x++)
        {
            if(hand.get(x).getRank().equals(rank))
            {
                count++;
            }
        }
        
        return count;
    }
    
    // this works out if there are any cards of a given suit in this hand 
    public boolean hasSuit(Card.Suit suit)
    {
        switch (suit) {
            case SPADE:
                if(spade == 0)
                {
                    return false;
                }
                else { return true; }
            case HEART:
                if(heart == 0)
                {
                    return false;
                }
                else { return true; }
            case DIAMOND:
                if(diamond == 0)
                {
                    return false;
                }
                else { return true; }
            case CLUB:
                if(club == 0)
                {
                    return false;
                }
                else { return true; }
            default:
                break;
        }
        return false;
    }
    
    // this return the hand in the form of a string 
    // this is mostly use to print out the hand
    @Override
    public String toString()
    {
        StringBuilder myHand = new StringBuilder();
        myHand.append("Your Hand").append(System.lineSeparator());
        for(int x = 0; x < hand.size(); x++)
        {
            myHand.append(hand.get(x).toString()).append(System.lineSeparator());
        }
        return myHand.toString();
           
      
    }
   
    // this iterator iteratate through the hand in order they were added
    @Override
    public Iterator<Card> iterator() {
        return new Iterator() {
                    private int currentIndex = 0;
            
            // check if there is another card in the hand
            @Override
            public boolean hasNext() {
               if(currentIndex == (hand.size() - 1))
               {
                   return false;
               }
               return true;
            }

            // return the card and makes currentIndex point at the next card in
            // the hand
            @Override
            public Card next() {
                if(hasNext())
                {
                    return order.get(currentIndex++);
                }
                return null;
            }
        };
    }
    
    // the main method mostly use for testing
     public static void main(String[] args) {
        Card card = new Card(Card.Rank.ACE,Card.Suit.DIAMOND);
        Card card1 = new Card(Card.Rank.FOUR,Card.Suit.DIAMOND);
        Card card2 = new Card(Card.Rank.FIVE,Card.Suit.HEART);
        Card card3 = new Card(Card.Rank.FOUR,Card.Suit.SPADE);
        Card card4 = new Card(Card.Rank.FOUR,Card.Suit.HEART);
        Card card5 = new Card(Card.Rank.FOUR,Card.Suit.CLUB);
        
        ArrayList<Card> list = new ArrayList();
        list.add(card);
        list.add(card1);
        list.add(card2);
        list.add(card3);
        list.add(card4);
        list.add(card5);
        int hold = 0;
        Hand hand = new Hand(list);
        hand.HandValue();
        System.out.println(hand.numberOfAces);
        for(int y = 0; y <= hand.numberOfAces; y++)
        {
            hold = hand.totalMax - (y*11);
            System.out.print(hold + " ");
        }
         System.out.println(" ");
        System.out.println(hand.toString());
      
        System.out.println(hand.spade + " " + hand.club + " " + 
                           hand.heart + " " + hand.diamond + "\n");
        
        System.out.println("checking sort");
        for(int z = 0; z < hand.hand.size(); z++)
        {
            System.out.println(hand.hand.get(z).toString());
        }
        
        hand.sort();
         System.out.println("\n after sort ");
        for(int z = 0; z < hand.hand.size(); z++)
        {
            System.out.println(hand.hand.get(z).toString());
        }
        
        ArrayList<Card> list1 = new ArrayList();
        list1.add(card);
        list1.add(card1);
        list1.add(card2);
        list1.add(card3);
        list1.add(card4);
        list1.add(card5);
        
         System.out.println(" ");
        Hand hand1 = new Hand(list1);
        for(int z = 0; z < hand1.hand.size(); z++)
        {
            System.out.println(hand1.hand.get(z).toString());
        } 
        
        hand1.sortByRank();
        System.out.println("\n after sort by rank");
        for(int z = 0; z < hand1.hand.size(); z++)
        {
            System.out.println(hand1.hand.get(z).toString());
        } 
        
        System.out.println(hand1.hasSuit(Card.Suit.CLUB));
        System.out.println(hand1.countSuit(Card.Suit.HEART));
        System.out.println(hand1.countRank(Card.Rank.FOUR));
        hand1.HandValue();
        
        
        ArrayList<Card> listRemove = new ArrayList();
        listRemove.add(card3);
        listRemove.add(card4);
        listRemove.add(card5);
        Hand handRemove = new Hand(listRemove);
        boolean worked = false;
        
         System.out.println("remove hand");
        worked = hand1.remove(card);
        System.out.println(worked);
        System.out.println(hand1.toString());
        worked = false;
        
        worked = hand1.remove(handRemove);
         System.out.println(worked + " " + hand1.hand.size());
        System.out.println(hand1.toString());
        
        Card returnCard = hand1.remove(0);
        System.out.println(returnCard.toString());
         
        hand1.HandValue();
        System.out.println(hand1.hasSuit(Card.Suit.CLUB));
        System.out.println(hand1.countSuit(Card.Suit.HEART));
        for(int y = 0; y <= hand1.numberOfAces; y++)
        {
            hold = hand1.totalMax - (y*11);
            System.out.print(hold + " ");
        }
        
        System.out.println("testing Add");
        hand1.add(card);
        System.out.println(hand1.toString());
        
        hand1.add(handRemove);
        System.out.println(hand1.toString());
        
        hand1.HandValue();
        System.out.println(hand1.hasSuit(Card.Suit.CLUB));
        System.out.println(hand1.countSuit(Card.Suit.HEART));
        for(int y = 0; y <= hand1.numberOfAces; y++)
        {
            hold = hand1.totalMax - (y*11);
            System.out.print(hold + " ");
        }
        
        hand1.add(listRemove);
        System.out.println(hand1.toString());
        
        hand1.HandValue();
        System.out.println(hand1.hasSuit(Card.Suit.CLUB));
        System.out.println(hand1.countSuit(Card.Suit.HEART));
         System.out.println(" ");
        for(int y = 0; y <= hand1.numberOfAces; y++)
        {
            hold = hand1.totalMax - (y*11);
            System.out.print(hold + " ");
        }
     }
    
    
}
