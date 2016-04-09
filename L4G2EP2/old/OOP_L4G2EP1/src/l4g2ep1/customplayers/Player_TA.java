package l4g2ep1.customplayers;

import l4g2ep1.*;
import l4g2ep1.common.*;

/**
 * '���� ���� ������ �÷��̾�'�� ������� ���� �� �÷��̾�#0���� ���ӿ� �����ϴ� �÷��̾� Ŭ�����Դϴ�.
 * 
 * ������/����ü �̵�: �׻� ������/�Ʒ�/����/�� ������ �̵��մϴ�.
 * ��ȥ ��ġ: �׻� �߾ӿ��� ��ġ�մϴ�.
 * ���� ����: �׻� �����մϴ�. 
 * 
 * @author Racin
 * 
 */
public class Player_TA extends Player
{
	DirectionCode[] directions;
	int idx_directions;
	
	public Player_TA()
	{
		name = "����";
		acceptDirectInfection = true;
	}

	@Override
	public DirectionCode Survivor_Move()
	{
		do
		{
			++idx_directions;
			idx_directions %= 4;
		}
		while ( IsValidMove(directions[idx_directions]) == false );
		
		return directions[idx_directions];
	}

	@Override
	public void Corpse_Stay() { }

	@Override
	public DirectionCode Infected_Move()
	{
		return Survivor_Move();
	}

	@Override
	public void Soul_Stay()
	{
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			directions = new DirectionCode[4];
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Up;
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		idx_directions = 0;
		
		return new Point(Constants.Classroom_Width / 2, Constants.Classroom_Height / 2);
	}

}
