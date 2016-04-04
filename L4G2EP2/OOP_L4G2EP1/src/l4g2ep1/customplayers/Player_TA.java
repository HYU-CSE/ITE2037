package l4g2ep1.customplayers;

import l4g2ep1.*;
import l4g2ep1.common.*;

/**
 * '직접 조작 가능한 플레이어'를 사용하지 않을 때 플레이어#0으로 게임에 참여하는 플레이어 클래스입니다.
 * 
 * 생존자/감염체 이동: 항상 오른쪽/아래/왼쪽/위 순서로 이동합니다.
 * 영혼 배치: 항상 중앙에서 배치합니다.
 * 직접 감염: 항상 수락합니다. 
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
		name = "조교";
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
