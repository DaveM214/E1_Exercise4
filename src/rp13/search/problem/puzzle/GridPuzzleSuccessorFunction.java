package rp13.search.problem.puzzle;

import java.util.List;

import rp13.search.interfaces.SuccessorFunction;
import rp13.search.problem.puzzle.EightPuzzle.PuzzleMove;
import rp13.search.problem.puzzle.GridLocation.GridMove;
import rp13.search.util.ActionStatePair;
import rp13.search.util.ComparableSearchNode;
import rp13.search.util.SearchNode;

public class GridPuzzleSuccessorFunction implements
		SuccessorFunction<GridLocation.GridMove, GridLocation> {

	@Override
	public void getSuccessors(SearchNode<GridMove, GridLocation> _cause,
			List<SearchNode<GridMove, GridLocation>> _successors) {

		assert (_successors != null);

		for (GridMove move : GridMove.values()) {
			if (_cause.getActionStatePair().getState().isPossibleMove(move)) {
				GridLocation successor = new GridLocation(_cause
						.getActionStatePair().getState());
				successor.makeMove(move);
				_successors
						.add(new SearchNode<GridLocation.GridMove, GridLocation>(
								new ActionStatePair<GridLocation.GridMove, GridLocation>(
										move, successor), _cause));
			}
		}

	}

	@Override
	public void getComparableSuccessors(
			ComparableSearchNode<GridMove, GridLocation> _cause,
			List<ComparableSearchNode<GridMove, GridLocation>> _successors) {
		assert (_successors != null);

		for (GridMove move : GridMove.values()) {
			if (_cause.getActionStatePair().getState().isPossibleMove(move)) {				
				GridLocation successor = new GridLocation(_cause
						.getActionStatePair().getState());
				successor.makeMove(move);
				_successors
						.add(new ComparableSearchNode<GridLocation.GridMove, GridLocation>(
								new ActionStatePair<GridLocation.GridMove, GridLocation>(
										move, successor), _cause, _cause
										.getCostToNode()
										+ _cause.getActionStatePair()
												.getState().getCostToMove()));
			}
			
		}
	}

}