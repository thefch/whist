package cw2;

import cw2.Card.Rank;
import cw2.Card.Suit;

public class BasicStrategy implements Strategy {

    public Integer[] scores = new Integer[BasicWhist.NOS_PLAYERS];

    public BasicStrategy() {
    }

    //choose card from hand
    @Override
    public Card chooseCard(Hand h, Trick t) {
        Card card = new Card(Rank.TWO, Suit.SPADES);
        Card temp = null;
        Card theC;
        if (t.cardsPlayed.isEmpty()) {  //choose card for frst player to play
            for (Card c : h.myHand()) {
                if (c.rank.ordinal() > card.rank.ordinal()) {
                    card = c;
                }
            }
            t.lead = card.suit; //set the lead suit
            System.out.println("Lead Suit:" + t.getLeadSuit());
            return card;
        } else {
            for (int i = 0; i < h.myHand().size(); i++) {
                theC = h.myHand().get(i);
                //follow suit if it can
                if ((theC.rank.ordinal() > card.rank.ordinal()) && (theC.suit == t.getLeadSuit())) {
                    temp = theC;
                } else if ((i == h.myHand().size() - 1) && (temp == null)) {
                    temp = canTrump(h, t);
                }
            }
            return temp;
        }
    }

    //check if it has trump card
    Card canTrump(Hand h, Trick t) {
        Card tmp = null;
        for (int i = 0; i < h.myHand().size(); i++) {
            Card c = h.myHand().get(i);
            if (c.suit == t.getTrumps()) {
                tmp = c;
            } else if (i == h.myHand().size() - 1 && (tmp == null)) {
                int temp = (int) ((h.myHand().size()) * Math.random());
                tmp = h.myHand().get(temp);
            }
        }
        return tmp;
    }

    @Override
    public void updateData(Trick t) {
    }

}
