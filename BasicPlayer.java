package cw2;

import cw2.Card.Suit;

public class BasicPlayer implements Player {

    public Hand myHand;
    private Suit trump;
    Strategy bs;
    Card played;

    public BasicPlayer() {
        myHand = new Hand();
        this.trump = trump;
        bs = new BasicStrategy();
    }

    //returns the curent hand
    public Hand getHand() {
        return myHand;
    }

    //adds card to the players hand
    @Override
    public void dealCard(Card c) {
        this.myHand.add(c);
    }

    //sets the strategy
    @Override
    public void setStrategy(Strategy s) {
        this.bs = s;
    }

    //Determines which of the players cards to play based on the in play trick t and player strategy
    @Override
    public Card playCard(Trick t) {
        Card temp;
        temp = this.bs.chooseCard(myHand, t);
        this.played = temp;
        return temp;
    }

    //let players view the trick
    @Override
    public void viewTrick(Trick t) {
        System.out.println("Trick=" + t.cardsPlayed);
    }

    //set the trump card to the players
    @Override
    public void setTrumps(Card.Suit s) {
        trump = s;
    }

    //gets the players' id
    @Override
    public int getID() {
        return BasicWhist.counterPlayer;
    }

    //returns the played card of a player
    Card playedCard() {
        return this.played;
    }

    //finds the current player
    public BasicPlayer findPlayer(int id) {
        return BasicWhist.players[id];
    }
}
