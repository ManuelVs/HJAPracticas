package hja.Parser;

import hja.Card.Card;
import hja.Card.CardFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public final class CardListParser {
	
	public static ArrayList<Card> parseListCard(Reader reader, int numCards) throws IOException {
		ArrayList<Card> arr = new ArrayList<>(numCards);
		
		for (int i = 0; i < numCards; ++i) {
			Card card = parseCard(reader);
			arr.add(card);
		}
		
		return arr;
	}
	
	private static Card parseCard(Reader reader) throws IOException {
		char rank = (char) reader.read();
		char suit = (char) reader.read();
		
		return CardFactory.createCard(rank, suit);
	}
}
