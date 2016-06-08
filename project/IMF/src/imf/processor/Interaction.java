package imf.processor;

import org.json.simple.JSONObject;

import imf.data.DataManager;
import imf.network.ConnectionEvent;
import imf.network.ConnectionManager;
import imf.network.IConnectionReceiver;
import imf.object.*;
import imf.utility.Pair;

public class Interaction implements IProcess<Pair<String>, ContainerObject>, IConnectionReceiver
{
	PlayerObject target;
	ProcessManager manager;
	ContainerObject ret;
	
	public Interaction(PlayerObject target)
	{
		this.target = target;
	}

	
	@Override
	public void onReceived(ConnectionEvent e) {		
		setter( new Pair<String>("act_partner", (String) (e.data).get("trigger")) );
	}
	
	
	@Override
	public void initilize(@SuppressWarnings("rawtypes") IProcess manager) 
	{
		this.manager = (ProcessManager) manager;
		ConnectionManager.registerIReceiver(ConnectionEvent.PARTNER_SENT, this);
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
		manager = null;
		target = null;
		ret = null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setter(Pair<String> object) 
	{
		TriggerObject t = (TriggerObject) DataManager.get_containers(object.second);
		if (t == null)
			return;
		
		switch (object.first)
		{
			case "find":
				ret = t;
				break;
		
			// ��ȣ�ۿ� Ű�� ������ ��
			case "act":
				t.trigger();
				
				if (ConnectionManager.getIsConnected() == true)
				{
					JSONObject obj = new JSONObject();
					obj.put("trigger", t.name);	
					ConnectionManager.sendToPartner(obj);
				}
				break;
			
			// ������ ��ȣ�ۿ� ���� ��
			case "act_partner":
				t.trigger();
				break;
				
			// ���콺  hover
			case "hover":
				if (t.index == 0)
					t.trigger();
				break;
			// ���콺  leave
			case "leave":
				if (t.index != 0)
					t.trigger();
				break;
				
			// ���콺 Ŭ����
			case "click":
				manager.property.setter(t.name);
				break;
		}
	}

	@Override
	public ContainerObject getter() 
	{
		return ret;
	}

}
