package cw2;

import cw2.Card.*;
import java.util.ArrayList;
import java.util.List;

public class Trick {

    public static Suit trumps;
    Suit lead;
    int playerID;
    List<Card> cardsPlayed;
    public static Card[] trumpCards = new Card[BasicWhist.NOS_PLAYERS];
    
    public static Card[] cardList = new Card[BasicWhist.NOS_PLAYERS];

    public Trick(int p) {    //p is the lead player 
        this.playerID = p;
        cardsPlayed = new ArrayList();
        this.lead = lead;
        this.trumpCards = initTrump();  //initialise trump array to null
    }

    //resets the trump list for every trick
    public Card[] initTrump() {
        for (int i = 0; i < trumpCards.length; i++) {
            trumpCards[i] = null;
        }
        return trumpCards;
    }

    //returns the trump card
    public Suit getTrumps() {
        return trumps;
    }

    //trump cards is set at the beggining of the game and always wins
    public static void setTrumps(Suit s) {
        System.out.println("Trump Suit:" + s);
        trumps = s;
    }

    //returns the Suit of the lead card.
    public Suit getLeadSuit() {
        return this.lead;
    }

    // Records the Card c played by Player p for this trick
    public void setCard(Card c, BasicPlayer p) {
        this.cardsPlayed.add(c);
        p.myHand.remove(c);

        //if card played is trump, add it to the trump list for that round
        if (c.suit == trumps) {
            trumpCards[p.getID()] = c;
        } else {
            cardList[p.getID()] = c;
        }
        System.out.println("Player " + p.getID() + ": " + c);

    }

    //Returns the card played by player with id p for this trick
    public Card getCard(BasicPlayer p) {
        return p.playedCard();
    }

    //being used for the humanGame to detect the user's ID
    public BasicPlayer findPlayer(int id) {
        return BasicWhist.players[id];
    }

    //Finds the ID of the winner of a completed trick
    public int findWinner() {
        int win = searchTrick();
        System.out.println("");
        return win;
    }

    //search the trick for either trump cards or cards that follow suit
    public int searchTrick() {
        int winner;
        if (searchList(trumpCards) >= 1) {    //search, if there are any trump cards
            winner = searchTrumps(trumpCards);   //find the winner here
        } else {
            winner = findPlayer(cardList);  //else if it follows suit,search here
        }
        return winner;
    }

    //searching the trump card list of the round
    public int searchTrumps(Card[] trumps) {
        Rank r = Rank.TWO;
        int winner = 0;
        for (int i = 0; i < trumpCards.length; i++) {
            if ((trumpCards[i] != null) && (trumpCards[i].rank.ordinal() > r.ordinal())) {
                winner = i;
                r = trumpCards[i].rank;
            }
        }
        return winner;
    }

    //find the highest card of the round
    public int findPlayer(Card[] cards) {
        int roundWinnerID = 0;
        Rank r = Rank.TWO;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].rank.ordinal() > r.ordinal()) {
                r = cards[i].rank;
                roundWinnerID = i;
            }
        }
        resetList(cards);
        return roundWinnerID;
    }

    //search the list to check if any trump cards
    public int searchList(Object[] array) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                count++;
            }
        }
        return count;
    }

    //resets the array to null
    public Card[] resetList(Card[] c) {
        for (int i = 0; i < c.length; i++) {
            c[i] = null;
        }
        return c;
    }
}
