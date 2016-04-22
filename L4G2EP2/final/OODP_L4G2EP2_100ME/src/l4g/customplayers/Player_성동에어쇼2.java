package l4g.customplayers;

import l4g.common.Constants;
import l4g.common.DirectionCode;
import l4g.common.Player;
import l4g.common.Point;
import l4g.common.StateCode;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * 성동구의 번영과 무궁한 발전을 위해 끊임 없이 3인 편대 에어쇼를 시도하는 team Sigma 소속 플레이어입니다.
 * 이 플레이어는 에어쇼의 선두를 담당합니다.
 * 
 * @author Racin
 *
 */
public class Player_성동에어쇼2 extends Player
{
	DirectionCode dir;
	
	int seasonNumber;
	
	int ID_leftWing;
	int ID_rightWing;

	PlayerInfo info_leftWing;
	PlayerInfo info_rightWing;

	public Player_성동에어쇼2(int ID)
	{
		super(ID, "성동에어쇼#2");
		
		ID_leftWing = ID + 1;
		ID_rightWing = ID + 2;
		
		trigger_acceptDirectInfection = true;
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{
		// 내가 진행 방향 끝에 도달했으면 탈출, 그렇지 않은 경우 양 쪽 후미가 뒤에 멀쩡히 살아 있는지 여부를 검사
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
				// 후미 둘 중 하나 이상의 죽음은 곧 에어쇼 실패를 의미
				int variableToMakeError = 0;
					
				variableToMakeError = variableToMakeError / variableToMakeError;
			}
		}
		
		return dir;
	}
	
	@Override
	public void Corpse_Stay()
	{
		// 죽음은 곧 에어쇼 실패를 의미
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
		
		// 내가 선두이므로 갈 방향은 내가 정한다.
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
		
		// 방향을 정했으니 몇 번째 행 / 열에서 시작할 지 정한다.
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
