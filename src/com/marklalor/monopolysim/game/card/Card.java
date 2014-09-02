package com.marklalor.monopolysim.game.card;

import java.util.Random;

/**
 * Allows a {@link CardAction} to be chosen from a list provided by a subclass of {@link Card}.
 * @author Mark Lalor
 */
public abstract class Card
{
	private CardAction[] actions;
	
	public Card(CardAction[] actions)
	{
		this.actions = actions;
	}
	
	/**
	 * Returns a {@link CardAction} from the {@link CardAction}[] array.
	 * <p>
	 * The {@link CardAction}s provided make up X of the 16 cards in the deck, so there is a <code>length</code>/16
	 * chance of getting a meaningful action, otherwise it is {@link CardAction.DO_NOTHING}
	 * @return a {@link CardAction} selected at random from 16 choices.
	 */
	public CardAction getCardAction()
	{
		//Each card type has 16 total.
		int value = new Random().nextInt(16);
		
		//The given actions are the movement actions, the other ones are not useful (hence the DO_NOTHING).
		if (value < actions.length)
			return actions[value];
		else
			return CardAction.DO_NOTHING;
	}
	
	//  About Monopoly Cards:
	//
	//	COMMUNITY CHEST LIST: 
	//
	//	---Movement--- 2
	//	Advance to Go (Collect $200) 
	//	Go to jail – go directly to jail – Do not pass Go, do not collect $200 
	//
	//	---Non-movement--- 14
	//
	//	CHANCE LIST: 
	//
	//	---Movement--- 9
	//	Advance to Go (Collect $200)
	//	Go directly to Jail – do not pass Go, do not collect $200 
	//	Advance to Illinois Ave. 
	//	Advance token to nearest Utility. If unowned, you may buy it from the Bank. If owned, throw dice and pay owner a total ten times the amount thrown. 
	//	Advance token to the nearest Railroad and pay owner twice the rental to which he/she is otherwise entitled. If Railroad is unowned, you may buy it from the Bank. (There are two of these.) 
	//	Advance to St. Charles Place – if you pass Go, collect $200
	//	Go back 3 spaces  
	//	Take a trip to Reading Railroad – if you pass Go collect $200
	//	Take a walk on the Boardwalk – advance token to Boardwalk
	//
	//	---Non-movement--- 7
}
