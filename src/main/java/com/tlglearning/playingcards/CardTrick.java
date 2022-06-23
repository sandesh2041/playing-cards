package com.tlglearning.playingcards;

import com.tlglearning.playingcards.model.Card;
import com.tlglearning.playingcards.model.Deck;
import com.tlglearning.playingcards.model.Suit;


import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.tlglearning.playingcards.model.Suit.Color.*;

public class CardTrick {
     private final Deque<Card> redPile = new LinkedList<>();
     private final Deque<Card> blackPile = new LinkedList<>();
     private final Comparator<Card> displayComparator = Comparator
             .comparing((Card c) -> c.getSuit().getColor())
             .thenComparing(Card::getSuit)
             .thenComparing(Card::getRank);

     public static void main(String[] args) {
        //Create an instance of Deck and shuffle it
        Deck deck = new Deck();
        deck.shuffle();

        //Draw cards from the deck according to the instructions, placing every other card into a red deque and a black
        // deque
        CardTrick trick = new CardTrick();
        trick.splitDeck(deck);

        //(Optional: shuffle the red deque and black deque.)
        //Generate a random integer between 0 and the smaller of the two deque sizes , and swap that number of cards
        //between the two
         trick.swapCards();

        //Count the red cards in the red deque and the black cards in the black deque, and compare them.
        //Sort each deque by color, suit and rank, and print them out along with the counts from the previous step
         trick.tally();
     }

     public void splitDeck(Deck deck){
         while(deck.getRemaining() > 0){
             Card indicator = deck.draw();
             Card next = deck.draw();
             if(indicator.getSuit().getColor().equals(BLACK)){
                 blackPile.add(next);
             } else {
                 redPile.add(next);
             }
         }
     }

     public void swapCards(){
         Random rng = new Random();
         int swapSize = rng.nextInt(1 + Math.min(redPile.size(), blackPile.size()));
         for(int i = 0; i < swapSize; i++){
             redPile.add(blackPile.remove());
             blackPile.add(redPile.remove());
         }
     }

     public void tally(){
         tallyPile(blackPile, BLACK);
         tallyPile(redPile, RED);
     }

     private void tallyPile(Collection<Card> pile, Suit.Color color) {
         long count = pile
                 .stream()
                 .filter((c) -> c.getSuit().getColor() == color)
                 .count();
         System.out.printf("%1$s pile: cards=%2$s; count of %1$s cards=%3$d.%n",
                 color, pile.stream().sorted(displayComparator).collect(Collectors.toList()), count);
     }
}
