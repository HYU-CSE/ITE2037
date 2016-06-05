package imf.processor;

import imf.object.*;
import imf.utility.Pair;

public class Interaction implements IProcess<Integer, Pair<String>>
{
	CharacterObject target;
	ProcessManager manager;
	Pair<String> ret;
	
	public Interaction(CharacterObject target)
	{
		this.target = target;
	}

	@Override
	public void initilize(@SuppressWarnings("rawtypes") IProcess manager) 
	{
		this.manager = (ProcessManager) manager;
	}

	@Override
	public void loop() 
	{
		
	}

	@Override
	public void process()
	{

	}
	
	@Override
	public void finalize()
	{
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setter(Integer object) 
	{
		switch (object)
		{
			// ��ȣ�ۿ� Ű�� ������ ��
			case 5:
				TriggerObject t = (TriggerObject) manager.get("physics").getter();
				if (t != null)
					t.trigger();
				break;

			// ���콺  hover
			case 9:
				TriggerObject c9 = (TriggerObject) manager.get("mouse").getter();
				if (c9 != null && c9.index == 0)
					c9.trigger();
				break;
			// ���콺  leave
			case 10:
				TriggerObject c0 = (TriggerObject) manager.get("mouse").getter();
				if (c0 != null && c0.index != 0)
					c0.trigger();
				break;
				
			// ���콺 Ŭ����
			case 11:
				TriggerObject c1 = (TriggerObject) manager.get("mouse").getter();
				if (c1 != null)
					manager.property.setter(c1.name);
				break;
		}
	}

	@Override
	public Pair<String> getter() 
	{
		return null;
	}
}
