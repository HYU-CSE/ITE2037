package l4g.customplayers;

import l4g.common.Constants;
import l4g.common.DirectionCode;
import l4g.common.Player;
import l4g.common.Point;
import l4g.common.StateCode;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * �������� ������ ������ ������ ���� ���� ���� 3�� ��� ���� �õ��ϴ� team Sigma �Ҽ� �÷��̾��Դϴ�.
 * �� �÷��̾�� ������� ���� ������ ����մϴ�.
 * 
 * @author Racin
 *
 */
public class Player_���������1 extends Player
{
	DirectionCode dir;
	
	int seasonNumber;
	
	int ID_sunDoo;

	PlayerInfo info_sunDoo;

	public Player_���������1(int ID)
	{
		super(ID, "���������#1");
		
		ID_sunDoo = ID - 1;
		
		trigger_acceptDirectInfection = false;
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{
		// ���� ���� ���� ���� ���������� Ż���� ���̰�, �׷��� ���� ��� ���ΰ� �տ� ������ ��� �ִ��� ���θ� �˻�
		CellInfo cell_sunDoo = null;
		
		switch ( dir )
		{
		case Up:
			if ( myInfo.position.row != 0 )
				cell_sunDoo = cells[myInfo.position.row - 1][myInfo.position.column + 1];
			break;
		case Left:
			if ( myInfo.position.column != 0 )
				cell_sunDoo = cells[myInfo.position.row - 1][myInfo.position.column - 1];
			break;
		case Right:
			if ( myInfo.position.column != Constants.Classroom_Width - 1 )
				cell_sunDoo = cells[myInfo.position.row + 1][myInfo.position.column + 1];
			break;
		default:
			if ( myInfo.position.row != Constants.Classroom_Height - 1 )
				cell_sunDoo = cells[myInfo.position.row + 1][myInfo.position.column - 1];
			break;
		}
		
		if ( cell_sunDoo != null )
		{
			cell_sunDoo.ForEach_Players(player -> {
				if ( player.ID == ID_sunDoo )
				{
					info_sunDoo = player;
				}
			});
			
			if ( info_sunDoo == null || info_sunDoo.state == StateCode.Corpse )
			{
				// ������ ������ �� ����� ���и� �ǹ�
				int variableToMakeError = 0;
				
				variableToMakeError = variableToMakeError / variableToMakeError;
			}
		}
		
		return dir;
	}
	
	@Override
	public void Corpse_Stay()
	{
		// ������ �� ����� ���и� �ǹ�
		int variableToMakeError = 0;
		
		variableToMakeError = variableToMakeError / variableToMakeError;
	}
	
	@Override
	public DirectionCode Infected_Move()
	{
		return Survivor_Move();
	}
	
	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			seasonNumber = (int)(gameNumber / 10000) % 10 + ( (int)(gameNumber / 1000000) - 2006 ) * 10;
			
			if ( seasonNumber < 0 )
				seasonNumber = -seasonNumber;
			
			trigger_acceptDirectInfection = false;
		}
		
		info_sunDoo = null;
		
		for ( CellInfo[] rows : cells )
		{
			for ( CellInfo cell : rows )
			{
				cell.ForEach_Players(player -> {
					if ( player.ID == ID_sunDoo )
						info_sunDoo = player;
				});
			}
		}
		
		// ���ΰ� ���Դٸ� ������ ��ġ�� ���� ���� ����
		if ( info_sunDoo != null )
		{
			if ( info_sunDoo.state == StateCode.Corpse )
				info_sunDoo = null;
			else
			{
				if ( info_sunDoo.position.row == 0 )
					dir = DirectionCode.Down;
				else if ( info_sunDoo.position.column == 0 )
					dir = DirectionCode.Right;
				else if ( info_sunDoo.position.row == Constants.Classroom_Height - 1 )
					dir = DirectionCode.Up;
				else if ( info_sunDoo.position.column == Constants.Classroom_Width - 1 )
					dir = DirectionCode.Left;
			}
		}
	}
	
	@Override
	public Point Soul_Spawn()
	{
		if ( info_sunDoo == null )
		{
			// ���� ���ΰ� �� ���Դٸ� ����
			int variableToMakeError = 0;
			
			variableToMakeError = variableToMakeError / variableToMakeError;			
		}
		
		switch ( dir )
		{
		case Up:
			// ���� ���ϸ� �Ʒ� ���ʿ� ��ġ
			return info_sunDoo.position.Offset(0, -1);
		case Left:
			// �������� ���ϸ� �Ʒ� �����ʿ� ��ġ
			return info_sunDoo.position.Offset(1, 0);
		case Right:
			// ���������� ���ϸ� �� ���ʿ� ��ġ
			return info_sunDoo.position.Offset(-1, 0);
		default:
			// �Ʒ��� ���ϸ� �� �����ʿ� ��ġ
			return info_sunDoo.position.Offset(0, 1);
		}
	}
	
}
