
package cardgame.cards;

import cardgame.cards.Card;
import java.io.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Deck implements Iterable<Card>,Serializable {
    
    static final long serialVersionUID = 49;
    private final int size = 52;
    private Card[] deck;
    private DeckIterator iterate; 
    private SpadeIterator spade;

    // sets the defult interator in DeckIterator
    @Override
    public Iterator iterator() {
        return new DeckIterator(deck,size);
    }
    
    // this iterator iteratates through the deck starting at card 52 
    private static class DeckIterator implements Iterator<Card> 
    {
        private int next;
        private final Card[] deck;
        
        // contructs the iterator
        public DeckIterator(Card[] deck, int size)
        {
            this.deck = deck;
            this.next = size - 1;
        }

        // check if there is a next card in the deck
        @Override
        public boolean hasNext() 
        {
           if(next < 0)
           {
               return false;
           }
           return true;
        }

        // returns the card on the top of the deck and decreases 
        // next counter by one  
        @Override
        public Card next() 
        {
            if(hasNext())
            {
                return deck[next--];
            }
            return null;
        }
        
        // removes the card you were preverously looking at 
        @Override
        public void remove()
        {
           deck[(next+1)] = null;
        }
    }
    
    //This iterator iteratate thought a deck but finding each spade
    private static class SpadeIterator implements Iterator<Card>
    {
        private int numberOfCards; // is a conter for only the spade suit
        private int next; // is a counter for the whole deck 
        private final Card[] deck;
        
        // contructs the iterator
        public SpadeIterator(Card[] deck, int size)
        {
            this.deck = deck;
            this.numberOfCards = size / 4;
            this.next = size - 1;
            
        }

        // check to see if there is another card in the deck
        @Override
        public boolean hasNext() {
             if(numberOfCards < 0)
           {
               return false;
           }
           return true;
        }

        // goes through the deck until they find a card from the spade suit 
        // and then return that card
        @Override
        public Card next() {
           while(hasNext())
           {
               if(deck[next].getSuit().equals(Card.Suit.SPADE))
               {
                   numberOfCards--;
                   return deck[next--];
               }
               next--;
           }
           return null;
        }
        
    }
   
    // this contructs a deck of 52 card then shuffles the deck 
    // into a random order
    public Deck()
    {
        int suitSize = 0;
        int rankSize = 0;
        Random rand = new Random();
        int sort;
        int card;
        int card2;
        Card hold;
        Card cardCreate;
        
        deck = new Card[size];
        
        for(int x = 0; x < size; x++)
        {
            cardCreate = new Card(Card.Rank.values()[rankSize],Card.Suit.values()[suitSize]);
            deck[x] = cardCreate;
            rankSize++;
            if(rankSize == 13)
            {
                suitSize++;
                rankSize = 0;
            }
        } 
        sort = rand.nextInt(100)+ 50;
        for(int x = 0; x < sort; x++)
        {
            card = rand.nextInt(52);
            card2 = rand.nextInt(52);
            hold = deck[card];
            deck[card] = deck[card2];
            deck[card2] = hold;
        }
        iterate = new DeckIterator(deck,size);
        spade = new SpadeIterator(deck,size);
    } 
    
    // this find the current size of the deck
    // e.g. the number of card not removed through dealing
    public int size()
    {
        int reallySize = 0;
          for(int x = 0; x < size; x++)
        {
            if(deck[x] != null)
            {
               reallySize++; 
            }
        }
        return reallySize;
    }
    
    // creates a new deck and returns it
    // it runs the constructor again
    public Deck newDeck()
    {
       Deck newDeck = new Deck();
       System.out.println(newDeck.size());
       return newDeck;
    }
    
    //this uses the deckIterate next() and remove() functure to deal cards out
    public Card deal()
    { 
        Card deal = iterate.next();
        iterate.remove();
        
        return deal;
    }
    
    // this method using Serialization saves all remaining spades in the deck 
    // to a file called deckSave.ser
    // to find all the spade in uses the spadeIterator 
    public static void saveSpade(Deck deck)
    {
        
        Iterator<Card> it = new Deck.SpadeIterator(deck.deck,deck.size);
        ArrayList<Card> spade = new ArrayList();
        Card hold;
        String filename = "deckSave.ser";
        
        for(int x = 0; x < deck.size/4; x++)
        {
            hold = it.next();
            System.out.println(hold);
            spade.add(hold);
        }
       
        try
        { 
            FileOutputStream fos = new FileOutputStream(filename); 
            ObjectOutputStream out = new ObjectOutputStream(fos); 
            out.writeObject(spade); 
            out.close();
        } 
        catch(Exception ex) 
        { 
            ex.printStackTrace(); 
        }
    }
    
    // this load in any information stored in decksave.ser and returns it 
    // as an arrayList
    public static ArrayList loadSpade()
    {
        ArrayList<Card> spade = new ArrayList();
        try
        {
            String filename = "deckSave.ser";
            FileInputStream fis = new FileInputStream(filename); 
            ObjectInputStream in = new ObjectInputStream(fis); 
            spade =(ArrayList)in.readObject(); 
            in.close();
        }
        catch(Exception ex) 
        { 
            ex.printStackTrace(); 
        }
        
        return spade;
    }
    
    // this is the main functurtion used for testing 
     public static void main(String[] args) {
        Deck deck = new Deck();
        System.out.println(deck.size);
         for(int x = 0; x < deck.size; x++)
        {
            System.out.println(x+1);
            System.out.println(deck.deck[x].toString());
        }
        System.out.println(" ");
        Card hold = deck.deal();
        System.out.println("\n" + hold);
        
        hold = deck.deal();
        System.out.println("\n" + hold);
         
        int size = deck.size();
        System.out.println("\n" + size);
        
        deck = deck.newDeck();
         for(int x = 0; x < deck.size; x++)
        {
            System.out.println(x+1);
            System.out.println(deck.deck[x].toString());
        }
        
        ArrayList<Card> load = new ArrayList<>();
        saveSpade(deck);
        load = loadSpade();
         System.out.println(" ");
        for(int z = 0; z < load.size(); z++)
        {
            System.out.println(z+1 + " " + load.get(z));
        }
     }
    
}
