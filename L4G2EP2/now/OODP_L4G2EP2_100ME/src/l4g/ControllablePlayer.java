package l4g;

import l4g.common.DirectionCode;
import l4g.common.Player;
import l4g.common.Point;

public class ControllablePlayer extends Player
{
	public Classroom classroom;

	public ControllablePlayer(int ID, Classroom classroom)
	{
		super(ID, "��");
		
		this.classroom = classroom;

		System.err.println("Error. ���� ���� ������ �÷��̾�� �̹� ���������� �������� �ʽ��ϴ�. ��ĥ �ڸ� ����� �ּ���.");
	}

	@Override
	public DirectionCode Survivor_Move()
	{
		return null;
	}

	@Override
	public void Corpse_Stay()
	{
	}

	@Override
	public DirectionCode Infected_Move()
	{
		return null;
	}

	@Override
	public void Soul_Stay()
	{
	}

	@Override
	public Point Soul_Spawn()
	{
		return null;
	}

}
