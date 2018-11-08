package hja.logic.gui.model;

import hja.pokerutils.Algorithm.CombosAlgorithm;
import hja.pokerutils.Card.Card;
import hja.pokerutils.Range.Range;
import hja.pokerutils.Ranking.Ranking;

import javax.swing.*;
import java.util.ArrayList;

public class ModelImpl implements Model {
	private Range range;
	private ArrayList<Card> cards;
	private CombosAlgorithm.Combos combos;
	private Ranking ranking;
	private double percentage;
	
	private ArrayList<BoardCardsListener> boardCardsListeners;
	private ArrayList<CombosListener> combosListeners;
	private ArrayList<RangeListener> rangeListeners;
	private ArrayList<RankingListener> rankingListeners;
	private ArrayList<PercentageListener> percentageListeners;
	
	public ModelImpl(){
		this.cards = new ArrayList<>(5);
		this.range = new Range();
		this.combos = new CombosAlgorithm.Combos();
		this.ranking = new Ranking();
		this.percentage = 0.0;
		
		this.boardCardsListeners = new ArrayList<>();
		this.combosListeners = new ArrayList<>();
		this.rangeListeners = new ArrayList<>();
		this.rankingListeners = new ArrayList<>();
		this.percentageListeners = new ArrayList<>();
	}
	
	public void setRange(Range range) {
		this.range = range;
		
		updateCombos();
		notifyRangeListeners();
	}
	
	public void setCard(Card card) {
		if(!this.cards.remove(card) && this.cards.size() < 5){
			this.cards.add(card);
		}
		
		updateCombos();
		notifyBoardCardsListeners();
	}
	
	public void setRanking(Ranking ranking){
		this.ranking = ranking;
		
		updateCombos();
		notifyRankingListener();
	}
	
	public void setPercentage(double percentage){
		this.percentage = percentage;
		
		updateCombos();
		notifyPercentageListener();
	}
	
	public void addBoardCardsListener(BoardCardsListener listener){
		this.boardCardsListeners.add(listener);
	}
	
	public void addCombosListener(CombosListener listener){
		this.combosListeners.add(listener);
	}
	
	public void addRangeListener(RangeListener listener){
		this.rangeListeners.add(listener);
	}
	
	public void addRankingListener(RankingListener listener){
		this.rankingListeners.add(listener);
	}
	
	public void addPercentageListener(PercentageListener listener) {
		this.percentageListeners.add(listener);
	}
	
	private void updateCombos(){
		Thread thread = new Thread(() -> {
			if(ModelImpl.this.cards.size() >= 3) {
				ModelImpl.this.combos = CombosAlgorithm.getCombos(range, cards);
			}
			else combos = new CombosAlgorithm.Combos();
			notifyCombosListener();
		});
		thread.start();
	}
	
	private void notifyBoardCardsListeners(){
		SwingUtilities.invokeLater(() -> {
			for(BoardCardsListener listener : boardCardsListeners){
				listener.notify(ModelImpl.this.cards);
			}
		});
	}
	
	private void notifyRangeListeners(){
		SwingUtilities.invokeLater(() -> {
			for(RangeListener listener : rangeListeners){
				listener.notify(ModelImpl.this.range);
			}
		});
	}
	
	private void notifyCombosListener(){
		SwingUtilities.invokeLater(() -> {
			for(CombosListener listener : combosListeners){
				listener.notify(combos);
			}
		});
	}
	
	private void notifyRankingListener(){
		SwingUtilities.invokeLater(() -> {
			for(RankingListener listener : rankingListeners){
				listener.notify(ModelImpl.this.ranking);
			}
		});
	}
	
	private void notifyPercentageListener(){
		SwingUtilities.invokeLater(() -> {
			for(PercentageListener listener : percentageListeners){
				listener.notify(ModelImpl.this.percentage);
			}
		});
	}
}
