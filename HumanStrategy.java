package cw2;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanStrategy implements Strategy {

    public HumanStrategy() {
    }

    //let the user choose a card
    @Override
    public Card chooseCard(Hand h, Trick t) {
        int j = 0;
        Card played = null;
        System.out.println("\nUSER'S TURN!");
        System.out.println("Your Hand:");
        for (Card c : h.myHand()) {
            System.out.println(j + " " + c);
            j++;
        }
        boolean hasSuit = true;
        int i = 0;
        Scanner scan = new Scanner(System.in);

        do {
            System.out.print("Enter a number in the range (0-" + (h.myHand().size() - 1) + "):");
            try {
                i = scan.nextInt();
                if (i < 0 || (i >= h.myHand().size())) {    //check input to be within range
                    System.out.println("Cards not in range, please pick one which is!");
                    System.out.println("Range (0-" + (h.myHand().size() - 1) + ")");
                } else {
                    played = h.myHand().get(i);
                    System.out.println("LEAD SUIT:" + t.getLeadSuit());
                    System.out.println("Card Selected:" + played);
                    if (t.getLeadSuit() == null) //if user is first, chooses the lead suit
                    {
                        hasSuit = false;
                    }
                    if (played.suit != t.getLeadSuit()) {
                        hasSuit = searchHand(h, t);  //searches the hand for a lead suit
                    } else {
                        hasSuit = false;
                    }
                }
            } catch (InputMismatchException a) {  //catch error for non-number input
                System.out.println("Must enter a number in the range 0-" + (h.myHand().size() - 1));
                scan.next();
            }
        } while (hasSuit);

        t.setCard(played, t.findPlayer(BasicWhist.humanID));
        return played;
    }

    //search hand for lead suit
    public boolean searchHand(Hand h, Trick t) {
        for (Card c : h.myHand()) {
            if (c.suit == t.getLeadSuit()) {
                System.out.println("You have to follow the Lead Suit");
                return true;
            }
        }
        return false;
    }

    @Override
    public void updateData(Trick c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
