package searchStructures;

import java.util.ArrayList;
import java.util.List;

import rp13.search.interfaces.GoalTest;
import rp13.search.interfaces.Heuristic;
import rp13.search.interfaces.SortedAgenda;
import rp13.search.interfaces.SuccessorFunction;
import rp13.search.util.ActionStatePair;
import rp13.search.util.EqualityGoalTest;
import wordPuzzle.IntPair;
import wordPuzzle.WordPuzzle;
import wordPuzzle.WordPuzzleSuccessorFunction;

public class AStarWordPuzzle<ActionT, StateT extends Heuristic> {

	private SortedAgenda<ComparableSearchNode<ActionT, StateT>> agenda;
	private SuccessorFunction<ActionT, StateT> sfunc;
	private GoalTest<StateT> gtest;
	private List<ComparableSearchNode<ActionT, StateT>> successors = new ArrayList<ComparableSearchNode<ActionT, StateT>>();
	private final ComparableSearchNode<ActionT, StateT> initial;
	private static String goalS;
	
	public AStarWordPuzzle(
			SortedAgenda<ComparableSearchNode<ActionT, StateT>> agenda,
			GoalTest<StateT> gtest, SuccessorFunction<ActionT, StateT> sfunc,
			ComparableSearchNode<ActionT, StateT> initial) {

		this.gtest = gtest;
		this.agenda = agenda;
		this.sfunc = sfunc;
		this.initial = initial;
	}

		public List<ActionT> searchLoop() {		System.out.println(initial.getActionStatePair().getState());
		if (isGoal(initial)) {
			return makeMoveList(initial);
		}

		addSuccessorsToAgenda(initial);
		while (!agenda.isEmpty()) {
			ComparableSearchNode<ActionT, StateT> testNode = agenda.pop();
			if (isGoal(testNode)) {
				return makeMoveList(testNode);
			} else
				addSuccessorsToAgenda(testNode);
		}

		return null;
	}

	private void addSuccessorsToAgenda(
			ComparableSearchNode<ActionT, StateT> parentNode) {
		sfunc.getComparableSuccessors(parentNode, successors, new WordPuzzle(goalS));
		
		for (ComparableSearchNode<ActionT, StateT> item : successors) {
			/*
			item.setCostToNode(item.getParent().getCostToNode()
					+ item.getActionStatePair().getState().getCostToMove());
			*/
			agenda.push(item);
			
		}
		successors.clear();
		agenda.sort();
		System.out.println(agenda.toString());
	}

	private List<ActionT> makeMoveList(SearchNode<ActionT, StateT> finishedNode) {
		System.out.println("Found a solution! The path is:");

		List<ActionT> resultList = new ArrayList<ActionT>();

		while (finishedNode.getParent() != null) {
			resultList.add(0, finishedNode.getActionStatePair().getAction());
			finishedNode = finishedNode.getParent();
		}

		System.out.println(resultList);
		return resultList;
	}

	public boolean isGoal(ComparableSearchNode<ActionT, StateT> testNode) {
		if (gtest.isGoal(testNode.getActionStatePair().getState())) {
			return true;
		} else
			return false;
	}

	public static void main(String [] args ){

		goalS = "jumble";
		ComparableSearchNode<IntPair, WordPuzzle> initial = new ComparableSearchNode<IntPair, WordPuzzle>(
				new ActionStatePair<IntPair, WordPuzzle>(null,
						new WordPuzzle(WordPuzzle.jumble(goalS))), null, 0);
		
		SortedAgenda<ComparableSearchNode<IntPair,WordPuzzle>> agenda = new AStarAgenda
													<IntPair,WordPuzzle>();

		SuccessorFunction<IntPair,WordPuzzle> sfunc = new WordPuzzleSuccessorFunction();
		EqualityGoalTest<WordPuzzle> gtest = new EqualityGoalTest<WordPuzzle>(new WordPuzzle(goalS));

		AStarWordPuzzle<IntPair,WordPuzzle> framework = new AStarWordPuzzle<IntPair,WordPuzzle>(agenda, gtest, sfunc, initial);
		
		framework.searchLoop();
	}

}
