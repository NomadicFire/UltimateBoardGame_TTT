package model.player;

import java.util.Scanner;

import model.modelToView.*;
import model.utility.Point;
import model.viewToModel.*;

public class HumanPlayer extends APlayer{

	private model.viewToModel.ITurnManager turnManager;

	public  HumanPlayer(IRequestor iRequestor, int player, ITurnManager turnManager){
		super(iRequestor, player);
		this.turnManager = turnManager;
	}


	/**
	 * Used by the TurnControl to tell this player to take its turn. 
	 * The human player will package up the IRequestor inside an IViewRequestor 
	 * and inform the view to use the IViewRequestor to attempt a move. 
	 * The view's reject command is augmented with an automatic re-taking of 
	 * the turn if the move is rejected by the model. 
	 * The view does not have to handle the re-taking of the turn.
	 */
	public void takeTurn(){
		System.out.println("Human player " + getPlayer() + " takes turn.");

		
		turnManager.takeTurn(new IViewRequestor() {	
			
			public void setTokenAt(int row, int col, final IRejectCommand rejectCommand) {
				getRequestor().setTokenAt(row, col, getPlayer() , new IRejectCommand(){
					public void execute() {
						rejectCommand.execute();
						takeTurn();
					}
				});				
			}
		});
	}

}
	