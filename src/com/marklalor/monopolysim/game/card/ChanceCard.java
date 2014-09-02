package com.marklalor.monopolysim.game.card;


/**
 * Creates a card instance with the Monopoly <em>Chance</em> cards.
 * @author Mark Lalor
 */
public class ChanceCard extends Card
{
	public ChanceCard()
	{
		super(new CardAction[]
		{
			CardAction.ADVANCE_TO_GO,
			CardAction.GO_DIRECTLY_TO_JAIL_DO_NOT_PASS_GO,
			CardAction.ADVANCE_TO_ILLINOIS_AVE,
			CardAction.ADVANCE_TOKEN_TO_NEAREST_UTILITY,
			CardAction.ADVANCE_TOKEN_TO_THE_NEAREST_RAILROAD,
			CardAction.ADVANCE_TO_ST_CHARLES_PLACE,
			CardAction.GO_BACK_THREE_SPACES,
			CardAction.TAKE_A_TRIP_TO_READING_RAILROAD,
			CardAction.TAKE_A_WALK_ON_THE_BOARDWALK
		});
	}
}
