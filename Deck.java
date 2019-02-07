package cw2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Deck implements Iterable, Serializable {

    static final long serialVersionUID = 49L;
    private static String fileName = "SPADES";
    static int MAX_SIZE = 52;
    Card[] cards;
    private DeckIterator iterator;

    public Deck() {
        MAX_SIZE = 52;
        cards = new Card[MAX_SIZE];
        newDeck(cards);
        shuffle(cards);
        iterator = new DeckIterator(cards);
    }

    //return the size of the deck
    public static int size() {
        return MAX_SIZE;
    }

    //shuffle the deck
    private Card[] shuffle(Card[] cards) {
        Random rand = new Random();
        Card temp;
        int j = 0;
        for (int i = 0; i < cards.length; i++) {
            j = rand.nextInt(cards.length - 1);
            temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
        return cards;
    }

    //initialise new deck
    final Card[] newDeck(Card[] cards) {
        Card.Rank temp = Card.Rank.ACE;
        for (int i = 0; i < 13; i++) {
            cards[i] = new Card(temp, Card.Suit.CLUBS);
            cards[i + 13] = new Card(temp, Card.Suit.DIAMONDS);
            cards[i + 26] = new Card(temp, Card.Suit.HEARTS);
            cards[i + 39] = new Card(temp, Card.Suit.SPADES);
            temp = temp.getNext();
        }
        return cards;
    }

    //removes and returns the top card from the deck
    public Card deal() {
        Card topCard = iterator.next();
        cards[MAX_SIZE - 1] = null;
        Deck.MAX_SIZE = Deck.MAX_SIZE - 1;
        return topCard;
    }

    @Override
    //the iterator for the Deck
    public Iterator<Card> iterator() {
        return new DeckIterator(cards);
    }

    //iterate ftom the last card to the the bottom card, as they are going to be dealt
    private class DeckIterator implements Iterator<Card> {

        private int nextCard;
        private final Card[] cards;
        boolean canRemove = false;

        public DeckIterator(Card[] cards) {
            this.cards = cards;
            this.nextCard = size() - 1;
        }

        @Override
        public boolean hasNext() {
            if (nextCard < 0) {
                return false;
            }
            return true;
        }

        @Override
        public Card next() {
            canRemove = true;
            Card temp = null;
            if (!hasNext()) {
                return null;
            }
            temp = cards[nextCard--];
            return temp;
        }
    }

    //iterates through spade cards
    public Iterator<Card> spadeIterator() {
        return new SpadeIterator(cards);
    }

    //class of spade iterator
    private class SpadeIterator implements Iterator<Card> {

        private int spadesCounter = 0;
        private final Card[] cards;
        private int nCards = 0;

        private SpadeIterator(Card[] cards) {
            this.cards = cards;
        }

        @Override
        public boolean hasNext() {
            return spadesCounter < 13;
        }

        //find next Spade card na dreturn it
        @Override
        public Card next() {
            Card c = null;
            while (hasNext()) {
                c = cards[nCards];
                nCards++;
                if (c.suit.ordinal() == Card.Suit.SPADES.ordinal()) {
                    spadesCounter++;
                    break;
                }
            }
            return c;
        }
    }

    //write spade cards to the serializable file
    private static void writeOut(List list) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
        oos.writeObject(list);
        System.out.println("\nSERIALIZED..");
        oos.close();
    }

    //read spade cards from the serializable file
    private static void readIn(String filename) throws IOException, ClassNotFoundException {
        List<Card> spadesDeSerialize = new ArrayList<Card>();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(fileName)));
        spadesDeSerialize = (List<Card>) ois.readObject();
        ois.close();
        System.out.println("\nDESIRIALIZE..");
        for (Card c : spadesDeSerialize) {
            System.out.println(c);
        }
    }

    public static void main(String[] argvs) throws IOException, ClassNotFoundException {
        Deck deck = new Deck();
        Card temp;
        System.out.println("DECK:");
        for (Card c : deck.cards) {
            System.out.println(c);
        }

        System.out.println("\nsize of deck:" + size());
        System.out.println("\nIterate the cards the way the are being dealt");
        Iterator<Card> itD = deck.iterator();
        while (itD.hasNext()) {
            temp = itD.next();
            System.out.println(temp);
        }

        temp = deck.deal();
        System.out.println("\n deal:" + temp);

        System.out.println("\n Iterate through the Spade cards in the deck");
        Iterator<Card> itS = deck.spadeIterator();

        List<Card> spades = new ArrayList();
        //traverse the deck and prints the Spade cards
        while (itS.hasNext()) {
            temp = itS.next();
            spades.add(temp);
            System.out.println(temp);
        }
        writeOut(spades);   //write Spade Cards to file
        readIn(fileName);   //read Spade Cards from file
    }
}
