package cw2;

import cw2.Card.Rank;
import cw2.Card.Suit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class Hand implements Iterable {

    static final long serialVersionUID = 300L;
    private List<Card> hand = new ArrayList();
    private int hasClubs = 0;
    private int hasHearts = 0;
    private int hasDiamonds = 0;
    private int hasSpades = 0;
    int totalSuits = 0;
    final int countSuit = 0;

    //creates an empty hand
    public Hand() {
        this.hand = hand;
    }

    //create a hand and add an array of cards in it
    public Hand(Card[] cardsArr) {
        this.hand = hand;
        for (int i = 0; i < cardsArr.length; i++) {
            hand.add(cardsArr[i]);
        }
        searchSuits(hand);
        totalValues(hand);
    }

    //crete a hand copy another hand to it
    public Hand(Hand h) {
        this.hand = hand;
        for (int i = 0; i < h.hand.size(); i++) {
            hand.add(i, h.hand.get(i));
        }
        searchSuits(hand);
        totalValues(hand);
    }

    //add card c to the hand
    public void add(Card c) {
        this.hand = hand;
        hand.add(c);
        searchSuits(hand);
    }

    //add the collection c of cards to the hand
    public void add(Collection<Card> c) {
        this.hand = hand;
        for (Card card : c) {
            hand.add(card);
        }
        searchSuits(hand);
    }

    //add to hand another Hand h
    public void add(Hand h) {
        this.hand = hand;
        for (int i = 0; i < h.hand.size(); i++) {
            hand.add(h.hand.get(i));
        }
        searchSuits(hand);
    }

    //remove form the hand the card c
    public boolean remove(Card c) {
        this.hand = hand;
        for (int i = 0; i < hand.size(); i++) {
            Rank getrank = hand.get(i).rank;
            Suit getsuit = hand.get(i).suit;
            if ((getrank.ordinal() == c.rank.ordinal()) && (getsuit.value == c.suit.value)) {
                hand.remove(i);
                searchSuits(hand);
                return true;
            }
        }
        return false;
    }

    //remove all the cards from a hand h
    public boolean remove(Hand h) {
        this.hand = hand;
        if (!h.hand.isEmpty()) {
            for (int i = 0; i < h.hand.size(); i++) {
                for (int j = 0; j < hand.size(); j++) {
                    if ((h.hand.get(i).rank.ordinal() == hand.get(j).rank.ordinal()) && (h.hand.get(i).suit.ordinal() == hand.get(j).suit.ordinal())) {
                        hand.remove(j);
                    }
                }
            }
            searchSuits(hand);
            return true;
        } else {
            return false;
        }
    }

    //remove a card from a specified index from the hand
    public Card remove(int i) {
        this.hand = hand;
        Card temp = hand.get(i);
        hand.remove(i);
        searchSuits(hand);
        return temp;
    }

    public void totalValues(List<Card> h) {
        //Card.Rank temp = Card.Rank.ACE;
        int hasACE = 0;
        int total = 0;
        String s = "";
        //adding frequency of ranks
        for (Card c : h) {
            total = total + c.rank.value;
            if (c.rank.value == Card.Rank.ACE.value) {
                hasACE++;
            }
        }
        for (int i = 0; i <= hasACE; i++) {
            s += (total) + ",";
            total = total - Rank.ACE.value + 1;
        }
        System.out.println("total:" + s);
    }

    //search the hand and counts the number of each suit
    public void searchSuits(List<Card> h) {
        this.hasClubs = hasClubs = 0;
        this.hasDiamonds = hasDiamonds = 0;
        this.hasHearts = hasHearts = 0;
        this.hasSpades = hasSpades = 0;

        for (Card c : h) {
            if (c.suit.value == Suit.CLUBS.value) {
                hasClubs++;
            } else if (c.suit.value == Suit.DIAMONDS.value) {
                hasDiamonds++;
            } else if (c.suit.value == Suit.HEARTS.value) {
                hasHearts++;
            } else if (c.suit.value == Suit.SPADES.value) {
                hasSpades++;
            }
        }
        this.totalSuits = hasClubs + hasDiamonds + hasHearts + hasSpades;
    }

    @Override
    public Iterator iterator() {
        this.hand = hand;
        return new AddedIterator(hand);
    }

    //iterator class that traverse they hand they way the cards were added
    private class AddedIterator implements Iterator<Card> {

        private List<Hand> h;
        private int nCards = h.size();
        private int counter = 0;

        public AddedIterator(List h) {
            this.h = h;
        }

        @Override
        public boolean hasNext() {
            if (!h.isEmpty() || counter < nCards) {
                return true;
            }
            return false;
        }

        @Override
        public Card next() {
            Card temp = null;
            if (hasNext()) {
                temp = hand.get(counter++);
            }
            return temp;
        }
    }

    //sort hand in ascending order of ranks 
    public static void sortByRank(Hand h) {
        Collections.sort(h.hand, new Card.CompareRank());
    }

    //sorts hand in ascending 
    public static void sort(Hand h) {
        Collections.sort(h.hand);

    }

    //counts the number of cards on the hand that have suti s
    public int countSuit(Suit s) {
        int count = 0;
        for (Card c : hand) {
            if (c.suit.value == s.value) {
                count++;
            }
        }
        return count;
    }

    //counts the number of cards on the hand that have rank r
    public int countRank(Rank r) {
        int count = 0;
        for (Card c : hand) {
            if (c.rank.value == r.value) {
                count++;
            }
        }
        return count;
    }

    //check if it has suit s
    public boolean hasSuit(Suit s) {
        for (Card c : hand) {
            if (c.suit.value == s.value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.hand.toString();
    }

    //returns the hand list
    public List<Card> myHand() {
        return this.hand;
    }

    public static void main(String[] argvs) {
        Card[] array = new Card[13];
        array[0] = new Card(Rank.ACE, Suit.SPADES);
        array[1] = new Card(Rank.ACE, Suit.CLUBS);
        array[2] = new Card(Rank.ACE, Suit.CLUBS);
        array[3] = new Card(Rank.FOUR, Suit.SPADES);
        array[4] = new Card(Rank.FIVE, Suit.DIAMONDS);
        array[5] = new Card(Rank.SIX, Suit.HEARTS);
        array[6] = new Card(Rank.SEVEN, Suit.DIAMONDS);
        array[7] = new Card(Rank.EIGHT, Suit.DIAMONDS);
        array[8] = new Card(Rank.NINE, Suit.CLUBS);
        array[9] = new Card(Rank.TEN, Suit.DIAMONDS);
        array[10] = new Card(Rank.JACK, Suit.DIAMONDS);
        array[11] = new Card(Rank.QUEEN, Suit.DIAMONDS);
        array[12] = new Card(Rank.KING, Suit.DIAMONDS);

        ArrayList<Card> cardList = new ArrayList();
        cardList.add(new Card(Rank.ACE, Suit.SPADES));
        cardList.add(new Card(Rank.JACK, Suit.HEARTS));
        cardList.add(new Card(Rank.SEVEN, Suit.CLUBS));
        cardList.add(new Card(Rank.SIX, Suit.HEARTS));

        Card temp;

        Hand handP1 = new Hand();
        Hand handP2 = new Hand(array);
        Hand handP3 = new Hand(handP2);
        System.out.println("\nTotal of handP2:");
        handP2.totalValues(handP2.hand);

        System.out.println("\nHAND 2");
        System.out.println(handP2);

        System.out.println("\nIterate the cards the way the were added");
        Iterator<Card> itA = handP2.hand.iterator();
        while (itA.hasNext()) {
            temp = itA.next();
            System.out.println(temp);
        }

        System.out.println("\nAdd a single card:");
        handP2.add(new Card(Rank.SEVEN, Suit.CLUBS));
        System.out.println(handP2);

        System.out.println("\n Add a collection to the hand");
        handP2.add(cardList);
        System.out.println(handP2);

        System.out.println("\nAdd a hand to another hand");
        handP2.add(handP3);
        System.out.println(handP2);

        System.out.println("\nRemove a card");
        handP2.remove(new Card(Rank.ACE, Suit.SPADES));
        System.out.println(handP2);

        System.out.println("\n Remove all cards if present form another hand");
        handP2.remove(handP3);
        System.out.println(handP2);

        System.out.println("\nRemove a card at a specific position");
        handP2.remove(0);
        System.out.println(handP2);

        System.out.println("\nSort hand in ascending");
        sort(handP2);
        System.out.println(handP2);

        System.out.println("\n sort by rank");
        sortByRank(handP2);
        System.out.println(handP2);

        System.out.println("\nHAND 3:");
        System.out.println(handP3);
        System.out.println("\nCount Suit");
        System.out.println("Suit: Clubs");
        System.out.println(handP3.countSuit(Suit.CLUBS));

        System.out.println("\nCount Rank");
        System.out.println("Rank: Jack");
        System.out.println(handP3.countRank(Rank.JACK));

        System.out.println("\nhasSuit");
        System.out.println("Suit: Clubs");
        System.out.println(handP2.hasSuit(Suit.CLUBS));
    }
}
