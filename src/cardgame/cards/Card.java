
package cardgame.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;


public class Card implements Serializable, Comparable<Card>, Iterable<Card> {
    static final long serialVersionUID = 100;
    private Rank rank;
    private Suit suit;

    @Override
    public Iterator<Card> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    // an enum of ranks it has methods for getValue() and getNext() 
    public enum Rank {
        TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,TEN,JACK,QUEEN,KING,ACE;
        
        //get the next in the enum list e.g. two will return three, ace will 
        //return two
        public static Rank getNext(Rank rank){
            Rank hold;
            int position = rank.ordinal();
            
            if(position != 12)
            {
                position++;
            }
            else if (position == 12)
            {
                position = 0;
            }
            
            hold = Rank.values()[position];
            return hold;
        }
        //finds the value of the card based to the point 
        // system of the game whist
        // e.g ace is 11 king,queen,jack is 10 two is 2, three is 3, etc... 
        public static int getValue(Rank rank)
        {
            int value = 0;
            int position = rank.ordinal();
            
            if(position <= 8)
            {
                value = position + 2;
            }
            else if(position == 12)
            {
                value = 11;
            }
            else if (position > 8 && position != 12)
            {
                value = 10;
            }
            
            return value;
        }
    }
    
    // an enum of suit it has method randSuit()
    public enum Suit {
        CLUB,DIAMOND,HEART,SPADE;
        
        // returns a suit pick at random
        public static Suit randSuit() {
            Random rand = new Random();
            Suit found;
            
            int ordinal = rand.nextInt(3);
            found = Suit.values()[ordinal];

            return found;
        }
    }
         
    // constructor gives rank and suit values    
    public Card(Rank r, Suit s)
    {
        rank = r;
        suit = s;
    }
    
    // returns the rank of a corresponding card
    public Rank getRank()
    {
        return this.rank;
    }
    
    // returns the suit of a corresponding card
    public Suit getSuit()
    {
        return this.suit;
    }
    
    // finds the largest card in and arraylist of cards 
    // uses an iterator to move through the arraylist
    public static Card max(ArrayList<Card> card)
    {
        Card max = new Card(Rank.TWO,Suit.CLUB);
        int compare = 0;
        Iterator it = card.iterator();
        while(it.hasNext())
        {
            Card temp = (Card)it.next();
            //System.out.println(temp); // use for testing
            //temp.getClass();  // use for testing
            compare = max.compareTo(temp);
            if(compare < 0)
            {
                max = temp;
            }
        }
        
        return max;
    }
    
    // return all card higher in the list than the card given
    // the high of the card are depended on the comparator 
    // compareDecending() or compareRank()
    public static ArrayList chooseGreater(ArrayList<Card> cards,Card card, Comparator comp)
    {
        ArrayList<Card> higher = new ArrayList();
        int compare;
        int record = 0;
        Collections.sort(cards, comp);
        
        for(int x = 0; x < cards.size(); x++)
        {
            compare = cards.get(x).compareTo(card);
            System.out.println(compare);
            System.out.println(cards.get(x).toString());
            if(compare == 0)
            {
                record = x;
            }
        }
        record = record + 1;
        
       while(record < cards.size())
       {
           higher.add(cards.get(record));
           record++;
       }
        return higher;
    }
    
    // this tests the chooseGreater() method using both comparators 
    public static void testChooseGreater()
    {
        Card card = new Card(Rank.ACE,Suit.DIAMOND);
        Card card1 = new Card(Rank.FOUR,Suit.DIAMOND);
        Card card2 = new Card(Rank.FIVE,Suit.HEART);
        Card card3 = new Card(Rank.FOUR,Suit.SPADE);
        Card card4 = new Card(Rank.FOUR,Suit.HEART);
        Card card5 = new Card(Rank.FOUR,Suit.CLUB);
        
        ArrayList<Card> list = new ArrayList();
        list.add(card);
        list.add(card1);
        list.add(card2);
        list.add(card3);
        list.add(card4);
        list.add(card5);
        
        ArrayList<Card> output = chooseGreater(list,card1,new compareDescending());
        
        for(int o = 0; o < output.size(); o++)
        {
            System.out.println(output.get(o).toString());
        }
        
        System.out.println(" ");
        ArrayList<Card> list1 = new ArrayList();
        list.add(card);
        list.add(card1);
        list.add(card2);
        list.add(card3);
        list.add(card4);
        list.add(card5);
        
        ArrayList<Card> output2 = chooseGreater(list1,card1,new compareRank());
        
         for(int o = 0; o < output2.size(); o++)
        {
            System.out.println(output2.get(o).toString());
        }
         
    }
    
    // return a string of the card suit and rank
    public String toString()
    {
        return "suit: " + this.suit + "\n" + "rank: " + this.rank; 
      
    }
    
    // compare card so they can be sorted into asending order
     @Override
    public int compareTo(Card t) {
       int compare = 0; 
       compare = Integer.compare(this.rank.ordinal(),t.rank.ordinal());
       
       if(compare == 0)
       {
           compare = Integer.compare(this.suit.ordinal(),t.suit.ordinal());
       }
       return compare;
    }
    
    // compares card so they can be sorted into desending order
    public static class compareDescending implements Comparator<Card> {
               
        @Override
        public int compare(Card t, Card t1) 
        {
            int compare = 0; 
            compare = Integer.compare(t1.rank.ordinal(),t.rank.ordinal());

            if(compare == 0)
            {
                compare = Integer.compare(t.suit.ordinal(),t1.suit.ordinal());
            }
            return compare;
        }
    }
    
    // compare card to sort them in asending order of rank 
    public static class compareRank implements Comparator<Card> {

        @Override
        public int compare(Card t, Card t1) {
            int compare = 0; 
            compare = Integer.compare(t.rank.ordinal(),t1.rank.ordinal());
            
            return compare;
        }
    }
        
  
    
    public static void main(String[] args) {
        Rank[] arrayOfRanks = Rank.values();
        System.out.println(Arrays.toString(arrayOfRanks));
        Rank x = Rank.values()[1];
        System.out.println(x);
        
        Rank test = Rank.KING;
        Rank t = Rank.ACE;
        Rank z = Rank.FOUR;
        
        
        t = Rank.getNext(test);
        System.out.println(t);
        
        int a = Rank.getValue(test);
        int b = Rank.getValue(t);
        int c = Rank.getValue(z);
        
        System.out.println(a+" "+b+" "+c);
        
        Suit w = Suit.randSuit();
        Suit q = Suit.randSuit();
        
        System.out.println(w+" "+q);
        
        Card card = new Card(Rank.ACE,Suit.DIAMOND);
        System.out.println(card.toString());
        System.out.println(" ");
        Card card1 = new Card(Rank.FOUR,Suit.DIAMOND);
        Card card2 = new Card(Rank.FIVE,Suit.HEART);
        Card card3 = new Card(Rank.FOUR,Suit.SPADE);
        Card card4 = new Card(Rank.FOUR,Suit.HEART);
        Card card5 = new Card(Rank.FOUR,Suit.CLUB);
        ArrayList<Card> list = new ArrayList();
        list.add(card);
        list.add(card1);
        list.add(card2);
        list.add(card3);
        list.add(card4);
        list.add(card5);
        
        
        Card max = max(list);
        System.out.println("max");
        System.out.println(max.toString());
        
        System.out.println(" \n list sort");
        Collections.sort(list, new compareDescending());
        for(int g = 0; g < list.size(); g++)
        {
            System.out.println(list.get(g));
        }
        
        ArrayList<Card> list1 = new ArrayList();
        list1.add(card);
        list1.add(card1);
        list1.add(card2);
        list1.add(card3);
        list1.add(card4);
        list1.add(card5);
        
        System.out.println(" \n list sort 2");
        Collections.sort(list1, new compareRank());
        for(int j = 0; j < list1.size(); j++)
        {
            System.out.println(list1.get(j));
        }
        
        System.out.println(" \n higher");
        ArrayList<Card> listHigher = new ArrayList();
       listHigher = chooseGreater(list1,card2, new compareDescending());
         for(int o = 0; o < listHigher.size(); o++)
        {
            System.out.println(listHigher.get(o).toString());
        }
        
        System.out.println(" ");
        testChooseGreater(); 
    }
    
}


