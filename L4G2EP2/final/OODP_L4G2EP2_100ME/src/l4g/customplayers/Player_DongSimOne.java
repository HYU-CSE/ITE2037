package l4g.customplayers;

import l4g.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_DongSimOne extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public Player_DongSimOne(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "��");
		
		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳�
		// �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = false;
		
		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ����
		// ���ƿɽô�.
		
	}
	 int banJiRum = 0;
	/*
	 * TODO#5 ���� �������� �׷� �� ��Ʈ�� ���� �Ʒ��� �ִ� �ټ� ���� �ǻ� ���� �޼��带 �ϼ��ϼ���. �翬�� �� �濡 �� ��
	 * ������, �߰��߰� �ڵ带 ����� �δ� ���� ������, �ڵ� �ۼ��� ����� �� �ƹ� �δ� ���� ������ ã�� ������.
	 * 
	 * L4G�� �������� '����'�� �߱��ϴ� ������ ���� ������ ������ �ƴմϴ�!
	 * 
	 * �������� �̹� �������� ������ �ð���ŭ, ���� �ٸ� ���� / �ٸ� �������� ������ ���̴� �ð��� �پ��� �� ���Դϴ�. �׷���
	 * �ڽ��� ���� ���� ������ ���÷ȴٸ�, �̸� �� �÷��̾ �����ϱ� ���� �Ƴ� ���� ����� ������ ������!
	 * 
	 * ��������� �Ǿ� Ȳ���� ������ ���ε��ϰ� Eclipse�� ���ƿ� �������� �ۼ��� �ڵ带 ���� ����, '�ڵ忡 ����̶��� ���� ��
	 * ���� �ֱ���'��� ������ ���� ��� �� ���Դϴ�.
	 */
	
	@Override
	public DirectionCode Survivor_Move()
	{
		return dongSimOneGuRiGi(banJiRum);
	}
	
	private DirectionCode dongSimOneGuRiGi(int BanJiRum)
	{
		if(BanJiRum == 0)
			return DirectionCode.Stay;
		if (myInfo.position.row == 6-BanJiRum)
		{
			if (myInfo.position.column == 6+BanJiRum)
				return DirectionCode.Down;
			else
				return DirectionCode.Right;
		}
		else
		{
			if (myInfo.position.row == 6+BanJiRum)
				if (myInfo.position.column == 6-BanJiRum)
					return DirectionCode.Up;
				else
					return DirectionCode.Left;
			else if (myInfo.position.column == 6-BanJiRum)
				return DirectionCode.Up;
			else if (myInfo.position.column == 6+BanJiRum)
				return DirectionCode.Down;
		}
		return DirectionCode.Right;
	}
	
	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}
	
	@Override
	public DirectionCode Infected_Move()
	{
		return dongSimOneGuRiGi(banJiRum);
	}
	
	@Override
	public void Soul_Stay()
	{
	}
	
	@Override
	public Point Soul_Spawn()
	{
		int result_row = 5;
		int result_column = 5;
		
		banJiRum = ((int)this.gameNumber+this.ID+turnInfo.turnNumber)%7;
		if(banJiRum<0)
			banJiRum = -banJiRum;
		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		result_row = 6-banJiRum;
		result_column = 6-banJiRum;
		
		return new Point(result_row, result_column);
	}
}
