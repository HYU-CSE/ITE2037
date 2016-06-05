package imf.processor;

import imf.object.SpriteObject;
import imf.utility.Pair;
import loot.graphics.Viewport;

public class Scene implements IProcess<SpriteObject, Pair<Double>>
{
	Viewport viewport;
	SpriteObject target, background;
	ProcessManager manager;
	
	public Scene(Viewport viewport, SpriteObject back)
	{
		this.viewport = viewport;
	}
	
	@Override
	public void initilize(@SuppressWarnings("rawtypes") IProcess manager) 
	{
		this.manager = (ProcessManager) manager;
	}

	@Override
	public void loop()
	{
		process();
	}
	
	@Override
	public void process() 
	{
		if ((Math.abs(viewport.pointOfView_x - target.pos_x) > 10) || Math.abs(viewport.pointOfView_y - target.pos_y) > 10)
		{
			viewport.pointOfView_x += (target.pos_x - viewport.pointOfView_x) * 0.05;
			viewport.pointOfView_y += (target.pos_y - viewport.pointOfView_y) * 0.05;
		}
	}
	
	@Override
	public void finalize() 
	{
	
	}
	
	@Override
	public void setter(SpriteObject object) 
	{
		this.target = object;
	}

	@Override
	public Pair<Double> getter() 
	{
		return new Pair<Double>(viewport.pointOfView_x, viewport.pointOfView_y);
	}
}
