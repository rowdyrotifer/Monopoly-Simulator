package com.marklalor.monopolysim.game.display;

import java.awt.image.BufferedImage;

import com.marklalor.monopolysim.game.space.property.Color;

public class DisplayLQ extends Display
{
	@Override
	public void draw()
	{
		// TODO draw on LQ graphics;
	}

	@Override
	protected BufferedImage createImage()
	{
		return new BufferedImage(300, 200, BufferedImage.TYPE_BYTE_INDEXED, Color.model);
	}
}
