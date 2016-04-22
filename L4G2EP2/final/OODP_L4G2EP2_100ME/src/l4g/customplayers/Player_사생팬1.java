package l4g.customplayers;

import l4g.common.Constants;
import l4g.common.DirectionCode;
import l4g.common.Player;
import l4g.common.Point;

/**
 * 매 시즌마다 플레이어 한 명을 골라 무조건 따라 다니는 team Sigma 소속 플레이어입니다.
 * 
 * @author Racin
 *
 */
public class Player_사생팬1 extends Player
{
	int IDtoFollow;
	
	DirectionCode[] directions = { DirectionCode.Up, DirectionCode.Left, DirectionCode.Right, DirectionCode.Down };
	
	public Player_사생팬1(int ID)
	{
		super(ID, "사생팬#1");
	}
	
	/**
	 * 해당 좌표가 유효한지(강의실 내의 칸을 가리키는 좌표인지) 여부를 반환합니다.
	 */
	public boolean IsValid(int row, int column)
	{
		return row >= 0 && row < Constants.Classroom_Height &&
				column >= 0 && column < Constants.Classroom_Width;
	}
	
	/**
	 * 해당 좌표로 가까이 갈 수 있는 가장 적합한 방향을 선택하여 반환합니다.
	 */
	public DirectionCode GetDirectionToTheCell(int row, int column, boolean allowStay)
	{
		int min_distance = Constants.Classroom_Height * Constants.Classroom_Width;
		int min_idx_direction = 0;
		
		for ( int iDirection = 0; iDirection < 4; iDirection++ )
		{
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);
			
			if ( IsValid(adjacentPoint.row, adjacentPoint.column) == true )
			{
				int distance = adjacentPoint.GetDistance(row, column);
				
				if ( distance < min_distance )
				{
					min_distance = distance;
					min_idx_direction = iDirection;
				}
			}
		}
		
		if ( allowStay == true )
		{
			int distance = myInfo.position.GetDistance(row, column);
			
			if ( distance < min_distance )
				return DirectionCode.Stay;
		}
		
		return directions[min_idx_direction];
	}
	
	boolean isRightMoveRequired = false;
	
	@Override
	public DirectionCode Survivor_Move()
	{
		// 항상 내가 쫓는 그 플레이어 위에서 부활
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
				if ( cells[row][column].CountIf_Players(player->player.ID == IDtoFollow) == 1 )
					return GetDirectionToTheCell(row, column, false);
		
		// 그 플레이어야 강의실을 나갔거나 시야에서 사라지면 나는 제자리에서 배회
		if ( isRightMoveRequired == true )
		{
			isRightMoveRequired = false;
			
			if ( myInfo.position.column != Constants.Classroom_Width - 1 )
				return DirectionCode.Right;
			else
				return DirectionCode.Left;
		}
		else
		{
			isRightMoveRequired = true;
			
			if ( myInfo.position.column != 0 )
				return DirectionCode.Left;
			else
				return DirectionCode.Right;
		}
	}
	
	@Override
	public void Corpse_Stay()
	{
	}
	
	@Override
	public DirectionCode Infected_Move()
	{
		// 항상 내가 쫓는 그 플레이어 위에서 부활
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
				if ( cells[row][column].CountIf_Players(player->player.ID == IDtoFollow) == 1 )
					return GetDirectionToTheCell(row, column, true);
		
		// 그 플레이어야 강의실을 나갔거나 시야에서 사라지면 나는 정화 기도
		return DirectionCode.Stay;
	}
	
	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			// 이번 시즌에 쫓을 플레이어 ID 설정
			int seasonNumber = (int)(gameNumber / 10000) % 10 + ( (int)(gameNumber / 1000000) - 2006 ) * 10;
			
			if ( seasonNumber < 0 )
				seasonNumber = -seasonNumber;
			
			seasonNumber %= 100;
			
			if ( seasonNumber == ID )
			{
				// 나 자신을 쫓아야 할 시즌이었다면 100판 주기로 다른 플레이어를 쫓음
				IDtoFollow = (int)(gameNumber % 100);
				
				// 100판 주기에서조차 내 ID가 나와버리면 걍 자신의 바로 다음 플레이어(team Sigma 소속 구성원)을 쫓음
				if ( myInfo.ID == IDtoFollow )
				{
					IDtoFollow = ID + 1;
				}
			}
			else
			{
				IDtoFollow = seasonNumber;
			}
		}
	}
	
	@Override
	public Point Soul_Spawn()
	{
		// 항상 내가 쫓는 그 플레이어 위에서 부활
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
				if ( cells[row][column].CountIf_Players(player->player.ID == IDtoFollow) == 1 )
					return new Point(row, column);
		
		// 만약 그 플레이어가 없다면 배치 유예
		return null;
	}
	
}
