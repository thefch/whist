package cw2;

import cw2.Card.Suit;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BasicWhist {

    static final int NOS_PLAYERS = 4;
    static final int NOS_TRICKS = 13;
    static final int WINNING_POINTS = 7;
    int team1Points = 0;
    int team2Points = 0;
    static BasicPlayer[] players;
    BasicStrategy bs;
    HumanStrategy human;
    static int counterPlayer;
    static int humanID = 2; //the user has a static ID

    //constructor for the four players for the BasicGame
    public BasicWhist(BasicPlayer[] pl) {
        this.players = pl;
        bs = new BasicStrategy();
    }

    //constrructor the the humanGame players
    //includeing the BasicPlayers and the HumanPlayer
    public BasicWhist(BasicPlayer[] p, HumanStrategy s) {
        this.players = p;
        this.human = s;
    }

    //deal cards to the hands
    public void dealHands(Deck newDeck) {
        for (int i = 0; i < 52; i++) {
            players[i % NOS_PLAYERS].dealCard(newDeck.deal());
        }
        for (int i = 0; i < players.length; i++) {
            Collections.sort(players[i].myHand.myHand());   //sort each hand in ascending
        }
    }

    //each players play a card that s added the trick
    public Trick playTrick(Player firstPlayer) {
        Trick t = new Trick(firstPlayer.getID());
        int playerID = firstPlayer.getID();
        for (int i = 0; i < NOS_PLAYERS; i++) {
            int next = (playerID + i) % NOS_PLAYERS;
            counterPlayer = next;
            t.setCard(players[next].playCard(t), players[next]);
        }
        return t;
    }

    //play a basic game of 13 rounds
    public void playGame() {
        Deck d = new Deck();
        dealHands(d);

        int firstPlayer = (int) (NOS_PLAYERS * Math.random()); //choose a random player to start first
        Suit trumps = Suit.randomSuit();  //choose the trump card
        Trick.setTrumps(trumps);      //sets trump card    
        for (int i = 0; i < players.length; i++) {
            players[i].setTrumps(trumps);
        }

        int i = 0;
        //checks for the team scores, in case one team
        //scores 7 before the end of the game       
        while (i < NOS_TRICKS && checkPoints()) {
            System.out.println("\n*****STARTING NEW TRICK********* round:" + i);
            Trick t = playTrick(players[firstPlayer]);
            for (int j = 0; j < players.length; j++) {
                System.out.println(j + " hand " + players[j].myHand);
            }
            players[i % NOS_PLAYERS].viewTrick(t);
            firstPlayer = t.findWinner();   //search for round winner
            addTeamPoints(firstPlayer);
            System.out.println("Winner of the trick =" + firstPlayer);
            i++;
        }
    }

    // Method to find the winner of a trick. Note
    public void playMatch() {
        while (team1Points < WINNING_POINTS && team2Points < WINNING_POINTS) {
            playGame();
            System.out.println("********************************");
        }
        if (team1Points >= WINNING_POINTS) {
            System.out.println("Winning team is team 1 with " + team1Points);
        } else {
            System.out.println("Winning team is team 2 with " + team2Points);
        }
    }

    //play tricks
    public Trick playHumanTrick(Player firstPlayer) {
        Trick t = new Trick(firstPlayer.getID());
        int playerID = firstPlayer.getID();
        System.out.println("Trump Suit:" + Trick.trumps + " Player:" + firstPlayer.getID());
        for (int i = 0; i < NOS_PLAYERS; i++) {
            int next = (playerID + i) % (NOS_PLAYERS);
            counterPlayer = next;  //counterPlayer gives the id to the getID method
            if (next == humanID) {
                this.human.chooseCard(players[next].myHand, t);
            } else {
                t.setCard(players[next].playCard(t), players[next]);
            }
        }
        return t;
    }

    //play a game with the user
    public void playHumanGame() {
        Deck d = new Deck();
        dealHands(d);

        int firstPlayer = (int) ((NOS_PLAYERS) * Math.random());//chooses the first player random
        Suit trumps = Suit.randomSuit();
        Trick.setTrumps(trumps);
        for (int i = 0; i < players.length; i++) {  //set trump card
            players[i].setTrumps(trumps);
        }

        Trick t;
        int i = 0;
        while (i < NOS_TRICKS && checkPoints()) {
            System.out.println("\n*********NEXT TRICK*********** " + i);
            t = playHumanTrick(players[firstPlayer]);
            players[i % NOS_PLAYERS].viewTrick(t);
            firstPlayer = t.findWinner();
            addTeamPoints(firstPlayer);
            System.out.println("Winner of the Trick=" + firstPlayer);
            i++;
        }

    }

    // Method to find the winner of a trick. Note
    public void playHumanMatch() {
        while (team1Points < WINNING_POINTS && team2Points < WINNING_POINTS) {
            playHumanGame();
            System.out.println("******************************\n");
        }
        if (team1Points >= WINNING_POINTS) {
            System.out.println("Winning team is team 1 with " + team1Points);
        } else {
            System.out.println("Winning team is team 2 with " + team2Points);
        }
    }

    public static void humanGame() {
        BasicPlayer[] p = new BasicPlayer[NOS_PLAYERS];
        HumanStrategy s = new HumanStrategy();
        Scanner scan = new Scanner(System.in);
        boolean playAgain = true;
        String input;

        for (int i = 0; i < p.length; i++) {
            p[i] = new BasicPlayer();   //create four players
        }
        System.out.println("User is on team 1");
        BasicWhist hg = new BasicWhist(p, s);
        hg.playHumanMatch();
        do {
            hg.playHumanMatch(); //Just plays a single match
            System.out.println("Do you want to play another game?(Y/n)");
            try {
                input = scan.next();
                if (input.equals("N") || input.equals("n")) {
                    System.out.println("EXITING GAME...");
                    playAgain = false;
                } else if (input.equals("y") || input.equals("Y")) {
                    System.out.println("\n\nStarting Game...\n\n");
                    hg.team1Points = 0;
                    hg.team2Points = 0;
                }
            } catch (InputMismatchException a) {
                System.out.println("okk");
                scan.next();
            }
        } while (playAgain);
    }

    public static void basicGame() {
        Scanner scan = new Scanner(System.in);
        char c;
        String input;
        boolean playAgain = true;
        BasicPlayer[] p = new BasicPlayer[NOS_PLAYERS];
        for (int i = 0; i < p.length; i++) {
            p[i] = new BasicPlayer();   //CREATE YOUR PLAYERS HERE
        }
        BasicWhist bg = new BasicWhist(p);
        do {
            bg.playMatch(); //Just plays a single match
            System.out.println("Do you want to play another game?(Y/n)");
            try {
                input = scan.next();
                if (input.equals("N") || input.equals("n")) {
                    System.out.println("EXITING GAME...");
                    playAgain = false;
                } else if (input.equals("y") || input.equals("Y")) {
                    System.out.println("\n\nStarting Game...\n\n");
                    bg.team1Points = 0;
                    bg.team2Points = 0;
                }
            } catch (InputMismatchException a) {
                System.out.println("okk");
                scan.next();
            }
        } while (playAgain);

    }

    //check the team points, in case a team scores 7 before the end of the game
    public boolean checkPoints() {
        if (team1Points == 7 || team2Points == 7) {
            return false;
        }
        return true;
    }

    //add up the points to each team
    public void addTeamPoints(int p) {
        if ((p == 0) || (p == 2)) {
            team1Points++;
        } else {
            team2Points++;
        }

        System.out.println("team " + 1 + " points:" + team1Points);
        System.out.println("team " + 2 + " points:" + team2Points);
    }

    public static void main(String[] args) {
        int choice;

        System.out.println("Choose a Game Mode:");
        System.out.println("1 = basicGame");
        System.out.println("2 = humanGame");
        Scanner scan = new Scanner(System.in);
        choice = scan.nextInt();
        switch (choice) {
            case 1:
                basicGame();
                break;
            case 2:
                humanGame();
                break;
        }

    }
}
