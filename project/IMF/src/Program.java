import java.awt.Color;

import imf.data.SettingParser;
import loot.GameFrameSettings;

public class Program 
{
	static final int HEIGHT = 600, WIDTH = 1200, DEPTH = 500;
	
	public static void main(String[] args) 
	{	
	    GameFrameSettings settings = new GameFrameSettings();
	    
	    @SuppressWarnings("unused")
		SettingParser parser = new SettingParser("data/IMF.inf", settings);
	    
	    settings.canvas_height = HEIGHT;
	    settings.canvas_width = WIDTH;
	    settings.window_title = "pair";
	    settings.canvas_backgroundColor = Color.black;
	    settings.numberOfButtons = 18;
	    settings.gameLoop_interval_ns = 1000000000 / 75;
	    
	    Window window = new Window(settings);
	    window.setVisible(true);
	}

}