package com.tlglearning.playingcards;

import com.tlglearning.playingcards.model.Card;
import com.tlglearning.playingcards.model.Deck;
import com.tlglearning.playingcards.model.Suit;

import javax.swing.*;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

public class CardTrick {
     private Deque<Card> redPile;
     private Deque<Card> blackPile;

     public CardTrick(){
         blackPile = new LinkedList<>();
         redPile = new LinkedList<>();
     }

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
             if(indicator.getSuit().getColor().equals(Suit.Color.BLACK)){
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
         int redCount = 0;
         int blackCount = 0;
         for (Card card : blackPile){
             if(card.getSuit().getColor().equals(Suit.Color.BLACK)){
                 blackCount++;
             }
         }
         for (Card card : redPile){
             if(card.getSuit().getColor().equals(Suit.Color.RED)){
                 redCount++;
             }
         }
         System.out.printf("%s: count=%d, cards=%s%n",Suit.Color.BLACK, blackCount, blackPile);
         System.out.printf("%s: count=%d, cards=%s%n",Suit.Color.RED, redCount, redPile);

     }

}
