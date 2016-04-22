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
 * �� �÷��̾�� ������� ���θ� ����մϴ�.
 * 
 * @author Racin
 *
 */
public class Player_���������2 extends Player
{
	DirectionCode dir;
	
	int seasonNumber;
	
	int ID_leftWing;
	int ID_rightWing;

	PlayerInfo info_leftWing;
	PlayerInfo info_rightWing;

	public Player_���������2(int ID)
	{
		super(ID, "���������#2");
		
		ID_leftWing = ID + 1;
		ID_rightWing = ID + 2;
		
		trigger_acceptDirectInfection = true;
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{
		// ���� ���� ���� ���� ���������� Ż��, �׷��� ���� ��� �� �� �Ĺ̰� �ڿ� ������ ��� �ִ��� ���θ� �˻�
		CellInfo cell_leftWing = null;
		CellInfo cell_rightWing = null;
		
		switch ( dir )
		{
		case Up:
			if ( myInfo.position.row < Constants.Classroom_Height - 2 )
			{
				cell_leftWing = cells[myInfo.position.row + 1][myInfo.position.column - 1];
				cell_rightWing = cells[myInfo.position.row + 1][myInfo.position.column + 1];
			}
			break;
		case Left:
			if ( myInfo.position.column < Constants.Classroom_Width - 2 )
			{
				cell_leftWing = cells[myInfo.position.row - 1][myInfo.position.column - 1];
				cell_rightWing = cells[myInfo.position.row + 1][myInfo.position.column - 1];
			}
			break;
		case Right:
			if ( myInfo.position.column > 1 )
			{
				cell_leftWing = cells[myInfo.position.row + 1][myInfo.position.column + 1];
				cell_rightWing = cells[myInfo.position.row - 1][myInfo.position.column + 1];
			}
			break;
		default:
			if ( myInfo.position.row > 1 )
			{
				cell_leftWing = cells[myInfo.position.row - 1][myInfo.position.column + 1];
				cell_rightWing = cells[myInfo.position.row - 1][myInfo.position.column - 1];
			}
			break;
		}
		
		if ( cell_leftWing != null )
		{
			info_leftWing = null;
			info_rightWing = null;
			
			cell_leftWing.ForEach_Players(player -> {
				if ( player.ID == ID_leftWing )
				{
					info_leftWing = player;
				}
			});

			cell_rightWing.ForEach_Players(player -> {
				if ( player.ID == ID_rightWing )
				{
					info_rightWing = player;
				}
			});

			if ( info_leftWing == null || info_leftWing.state == StateCode.Corpse ||
					info_rightWing == null || info_rightWing.state == StateCode.Corpse )
			{
				// �Ĺ� �� �� �ϳ� �̻��� ������ �� ����� ���и� �ǹ�
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
		}
	}
	
	@Override
	public Point Soul_Spawn()
	{
		int result_row = 0;
		int result_column = 0;
		
		// ���� �����̹Ƿ� �� ������ ���� ���Ѵ�.
		switch ( ( turnInfo.turnNumber + seasonNumber ) % 4 )
		{
		case 0:
			dir = DirectionCode.Up;
			break;
		case 1:
			dir = DirectionCode.Left;
			break;
		case 2:
			dir = DirectionCode.Right;
			break;
		default:
			dir = DirectionCode.Down;
			break;
		}
		
		// ������ �������� �� ��° �� / ������ ������ �� ���Ѵ�.
		switch ( dir )
		{
		case Up:
			result_row = Constants.Classroom_Height - 1;
			result_column = ((int)( gameNumber % 10000 ) + turnInfo.turnNumber) % ( Constants.Classroom_Width - 2) + 1;
			break;
		case Left:
			result_row = ((int)( gameNumber % 10000 ) + turnInfo.turnNumber) % ( Constants.Classroom_Height - 2) + 1;
			result_column = Constants.Classroom_Width - 1;
			break;
		case Right:
			result_row = ((int)( gameNumber % 10000 ) + turnInfo.turnNumber) % ( Constants.Classroom_Height - 2) + 1;
			result_column = 0;
			break;
		default:
			result_row = 0;
			result_column = ((int)( gameNumber % 10000 ) + turnInfo.turnNumber) % ( Constants.Classroom_Width - 2) + 1;
			break;
		}

		return new Point(result_row, result_column);
	}
	
}
