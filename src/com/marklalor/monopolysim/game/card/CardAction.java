package com.marklalor.monopolysim.game.card;

import com.marklalor.monopolysim.Main;
import com.marklalor.monopolysim.game.Game;

/**
 * A list of all the possible actions to be taken as a result of a chance or
 * community chest card.
 * @author Mark Lalor
 */
public enum CardAction
{
	ADVANCE_TO_GO,
	GO_DIRECTLY_TO_JAIL_DO_NOT_PASS_GO,
	ADVANCE_TO_ILLINOIS_AVE,
	ADVANCE_TOKEN_TO_NEAREST_UTILITY,
	ADVANCE_TOKEN_TO_THE_NEAREST_RAILROAD,
	ADVANCE_TO_ST_CHARLES_PLACE,
	GO_BACK_THREE_SPACES,
	TAKE_A_TRIP_TO_READING_RAILROAD,
	TAKE_A_WALK_ON_THE_BOARDWALK,
	DO_NOTHING;
	
	/**
	 * Applies the current action to the specified {@link Game}.
	 * @param game The game to apply this {@link CardAction} to.
	 */
	public void use(Game game)
	{
		if (Main.DEBUG) System.out.println("CardAction: " + this);
		
		if (this == ADVANCE_TO_GO)
			game.advanceTo(game.getBoard().getGo());
		else if (this == ADVANCE_TO_ILLINOIS_AVE)
			game.advanceTo(game.getBoard().getIllinoisAvenue());
		else if (this == ADVANCE_TO_ST_CHARLES_PLACE)
			game.advanceTo(game.getBoard().getStCharlesPlace());
		else if (this == ADVANCE_TOKEN_TO_NEAREST_UTILITY)
			game.advanceTo(game.getNearestUtility());
		else if (this == ADVANCE_TOKEN_TO_THE_NEAREST_RAILROAD)
			game.advanceTo(game.getNearestRailroad());
		else if (this == TAKE_A_TRIP_TO_READING_RAILROAD)
			game.advanceTo(game.getBoard().getReadingRailroad());
		else if (this == TAKE_A_WALK_ON_THE_BOARDWALK)
			game.advanceTo(game.getBoard().getBoardwalk());
		else if (this == GO_DIRECTLY_TO_JAIL_DO_NOT_PASS_GO)
		{
			game.getBoard().getJail().addHit();
			game.jail();
		}
		else if (this == GO_BACK_THREE_SPACES)
			game.move(-3);
	}
}
