package com.marklalor.monopolysim.game.card;

/**
 * Creates a card instance with the Monopoly <em>Community Chest</em> cards.
 * @author Mark Lalor
 */
public class CommunityChestCard extends Card
{
	public CommunityChestCard()
	{
		super(new CardAction[]
		{
			CardAction.ADVANCE_TO_GO,
			CardAction.GO_DIRECTLY_TO_JAIL_DO_NOT_PASS_GO
		});
	}
}
