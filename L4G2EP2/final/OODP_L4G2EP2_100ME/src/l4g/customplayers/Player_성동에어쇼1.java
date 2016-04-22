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
 * 이 플레이어는 에어쇼의 좌측 날개를 담당합니다.
 * 
 * @author Racin
 *
 */
public class Player_성동에어쇼1 extends Player
{
	DirectionCode dir;
	
	int seasonNumber;
	
	int ID_sunDoo;

	PlayerInfo info_sunDoo;

	public Player_성동에어쇼1(int ID)
	{
		super(ID, "성동에어쇼#1");
		
		ID_sunDoo = ID - 1;
		
		trigger_acceptDirectInfection = false;
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{
		// 내가 진행 방향 끝에 도달했으면 탈출할 것이고, 그렇지 않은 경우 선두가 앞에 멀쩡히 살아 있는지 여부를 검사
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
				// 선두의 죽음은 곧 에어쇼 실패를 의미
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
		
		// 선두가 나왔다면 선두의 위치에 따라 방향 선택
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
			// 아직 선두가 안 나왔다면 유예
			int variableToMakeError = 0;
			
			variableToMakeError = variableToMakeError / variableToMakeError;			
		}
		
		switch ( dir )
		{
		case Up:
			// 위로 향하면 아래 왼쪽에 배치
			return info_sunDoo.position.Offset(0, -1);
		case Left:
			// 왼쪽으로 향하면 아래 오른쪽에 배치
			return info_sunDoo.position.Offset(1, 0);
		case Right:
			// 오른쪽으로 향하면 위 왼쪽에 배치
			return info_sunDoo.position.Offset(-1, 0);
		default:
			// 아래로 향하면 위 오른쪽에 배치
			return info_sunDoo.position.Offset(0, 1);
		}
	}
	
}
