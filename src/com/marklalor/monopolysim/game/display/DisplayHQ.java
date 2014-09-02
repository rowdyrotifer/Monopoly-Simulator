package com.marklalor.monopolysim.game.display;

import java.awt.image.BufferedImage;

public class DisplayHQ extends Display
{
	@Override
	public void draw()
	{
		// TODO draw on HQ graphics;
	}

	@Override
	protected BufferedImage createImage()
	{
		return new BufferedImage(600, 400, BufferedImage.TYPE_INT_RGB);
	}
}
