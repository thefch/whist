package cw2;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Card implements Serializable, Comparable<Card> {

    static final long serialVersionUID = 100L;

    Rank rank;
    Suit suit;

    public Card(Rank r, Suit s) {
        rank = r;
        suit = s;
    }

    //CARD RANKS enum
    public enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
        NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        int value;

        //Rank constructor
        Rank(int value) {
            this.value = value;
        }

        public static Rank[] vals = values();

        //gets the next rank
        Rank getNext() {
            return vals[(this.ordinal() + 1) % vals.length];

        }

        //returns the value of the rank
        public int getValue() {
            return this.value;
        }

        public static Rank randomRank() {
            Random rand1 = new Random();
            return values()[rand1.nextInt(values().length)];
        }
    }

    //CARD SUIT enum
    public enum Suit {
        CLUBS(4), DIAMONDS(3), HEARTS(2), SPADES(1);

        final int value;

        //Suit constructor
        Suit(int value) {
            this.value = value;
        }

        //selects random suit for the card
        public static Suit randomSuit() {
            Random rand = new Random();
            return values()[rand.nextInt(values().length)];
        }
    }

    @Override
    public String toString() {
        return "Rank:" + rank.value + "(" + rank + ")" + "  Suit:" + suit;
    }

    //sort cards into ascending order
    @Override
    public int compareTo(Card o) {
        if (this.rank.value > o.rank.value) {
            return 1;
        } else if (this.rank.value < o.rank.value) {
            return -1;
        }
        return 0;
    }

    //returns the max card value 
    public static Card max(ArrayList card) {
        Card maxRank = null;

        //sort the list, so the last element will be the one with the highest value
        Collections.sort(card);

        Iterator<Card> it = card.iterator();
        while (it.hasNext()) {
            maxRank = it.next();
        }
        return maxRank;
    }

    //Sort in Descending by Rank
    static class CompareDescending implements Comparator<Card> {

        @Override
        public int compare(Card a, Card b) {
            if (a.rank.value < b.rank.value) {
                return 1;
            } else if (a.rank.value > b.rank.value) {
                return -1;
            }
            return 0;
        }
    }

    //Sort Ascending by Rank(value)
    public static class CompareRank implements Comparator<Card> {

        @Override
        public int compare(Card a, Card b) {
            if (a.rank.value > b.rank.value) {
                return 1;
            } else if (a.rank.value < b.rank.value) {
                return -1;
            }
            return 0;
        }
    }

    //gives a list of card with value higher than the given
    public static ArrayList chooseGreater(ArrayList<Card> cardList, Comparator comp, Card card) {
        ArrayList<Card> list = new ArrayList<>();
        int cardValue = card.rank.value;
        Collections.sort(cardList, comp);
        for (Card c : cardList) {
            if (c.rank.value > cardValue) {
                list.add(c);
            }
        }
        return list;
    }

    public static void selectTest(Comparator compR, Comparator compD, compareCards compL) {

        ArrayList<Card> card = new ArrayList<>();
        ArrayList<Card> card1 = new ArrayList<>();
        ArrayList<Card> card2 = new ArrayList<>();
        card.add(new Card(Rank.TEN, Suit.DIAMONDS));
        card.add(new Card(Rank.FOUR, Suit.SPADES));
        card.add(new Card(Rank.TEN, Suit.SPADES));
        card.add(new Card(Rank.TWO, Suit.CLUBS));
        card.add(new Card(Rank.SIX, Suit.HEARTS));
        card.add(new Card(Rank.THREE, Suit.CLUBS));
        card.add(new Card(Rank.THREE, Suit.DIAMONDS));

        System.out.println("\nSelect Test - chooseGreater - sort by rank");
        card1 = chooseGreater(card, compR, card.get(1));
        System.out.println("card:" + card.get(1));
        for (Card c : card1) {
            System.out.println(c);
        }

        System.out.println("\nSelect Test - chooseGreater - sort descending");
        card2 = chooseGreater(card, compD, card.get(1));
        System.out.println("card:" + card.get(1));
        for (Card c : card2) {
            System.out.println(c);
        }
    }

    public static void main(String[] argvs) {
        ArrayList<Card> card = new ArrayList<>();
        card.add(new Card(Rank.TEN, Suit.DIAMONDS));
        card.add(new Card(Rank.FOUR, Suit.SPADES));
        card.add(new Card(Rank.TEN, Suit.SPADES));
        card.add(new Card(Rank.TWO, Suit.CLUBS));
        card.add(new Card(Rank.SIX, Suit.HEARTS));
        card.add(new Card(Rank.THREE, Suit.CLUBS));
        card.add(new Card(Rank.THREE, Suit.DIAMONDS));

        System.out.println(card);

        System.out.println("\nmax(): " + max(card));

        System.out.println("\ncompareTo");
        Collections.sort(card);
        for (Card c : card) {
            System.out.println(c);
        }

        System.out.println("\n Sort by Rank");
        Collections.sort(card, new CompareRank());
        for (Card c : card) {
            System.out.println(c);
        }

        System.out.println("\n Sort Descending");
        Collections.sort(card, new CompareDescending());
        for (Card c : card) {
            System.out.println(c);
        }

        System.out.println("\nChoose Greater");
        System.out.println("card:" + card.get(3));
        card = chooseGreater(card, new CompareRank(), card.get(3));
        for (Card c : card) {
            System.out.println(c);
        }

        //lamda expression
        compareCards compL = (Card a, Card b) -> {
            if ((a.rank.value > b.rank.value) || (a.rank.value == b.rank.value)) {
                if (a.suit.value > b.suit.value) {
                    return a;
                }
            }
            return b;
        };
        selectTest(new CompareRank(), new CompareDescending(), (compareCards) compL);
    }

    interface compareCards {

        Card myCards(Card a, Card b);
    }
}
