package l4g2ep1.customplayers;

import java.util.ArrayList;

import l4g2ep1.*;
import l4g2ep1.common.*;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 * 
 */
public class Player_YOURNAMEHERE extends Player
{

	private int[] shuffledDirection_values;
	private DirectionCode[] shuffledDirections;
	
	public Player_YOURNAMEHERE()
	{
	
		name = "한솔이";	// TODO 자신이 만들 플레이어의 이름으로 name 필드를 초기화하세요.
		acceptDirectInfection = false;				// TODO '직접 감염'을 받으려는 경우 이 필드를 true로 두고 아닌 경우 false로 두세요.
		
		receiveOthersInfo_detected = true;
	}

	@Override
	public DirectionCode Survivor_Move()
	{
		
		DirectionCode result = DirectionCode.Stay;
		//방향별로 다른 플레이어에 대한 위험도를 기록하기 위한 배열 사용
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] weights = new int[4];
		int[] survivors = new int[4];
		int[] others = new int[4];
		int max_weight = -1;
		
		int min_weight = Integer.MAX_VALUE;
		
	
		
		if(this.myScore.GetSurvivor_Max_Survived_Turns()<10)
		{
			
			
			for ( PlayerInfo other : othersInfo_detected )
			{
				//해당 플레이어와 나 사이의 거리 비교
				Vector v = GetDistanceVectorBetweenPlayers(other);

				//해당 플레이어의 현재 상태에 따라 플레이어 수 기록
				if ( other.GetState() == PlayerInfo.State.Survivor )
				{
					if ( v.y_offset < 0 )
						++survivors[0];
		
					if ( v.x_offset < 0 )
						++survivors[1];
					
					if ( v.x_offset > 0 )
						++survivors[2];
					
					if ( v.y_offset > 0 )
						++survivors[3];
				}
				else
				{
					if ( v.y_offset < 0 )
						++others[0];
		
					if ( v.x_offset < 0 )
						++others[1];
					
					if ( v.x_offset > 0 )
						++others[2];
					
					if ( v.y_offset > 0 )
						++others[3];
				}
			}

			//포착 기대값: 생존자 수 x 시체 및 감염체 수 계산
			for ( int i = 0; i < 4; ++i )
				weights[i] = survivors[i] * others[i];
			
			//갈 수 없는 방향에 대해 기대값을 최소값으로 설정
			if ( IsValidMove(DirectionCode.Up) == false )
				weights[0] = -1;
			
			if ( IsValidMove(DirectionCode.Left) == false )
				weights[1] = -1;

			if ( IsValidMove(DirectionCode.Right) == false )
				weights[2] = -1;

			if ( IsValidMove(DirectionCode.Down) == false )
				weights[3] = -1;

			//측정된 기대값의 최대값이 몇인지 계산
			for ( int weight : weights )
				if ( weight > max_weight )
					max_weight = weight;
			

			//'방향 섞기' 설정에 따라 위험도가 최소값과 같은 방향들 중 하나 선택 - 최소 하나 존재
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
					return shuffledDirections[iShuffledDirection];
		}
		else 
		{
			/*
			 * 외톨이의 생존자 이동: 다른 플레이어가 가장 적은 방향을 선택
			 */
			
			//방향별로 다른 플레이어에 대한 위험도를 기록하기 위한 배열 사용
			//0: Up, 1: Left, 2: Right, 3: Down
			
			
			//현재 포착된 모든 플레이어에 대해 위험도 계산
			for ( PlayerInfo other : othersInfo_detected )
			{
				//해당 플레이어와 나 사이의 거리 비교
				Vector v = GetDistanceVectorBetweenPlayers(other);
				
				if ( other.GetState() == PlayerInfo.State.Infected )
				{

					//해당 플레이어가 나보다 위에 있다면 위로 가는 것은 위험할 듯?
					if ( v.y_offset < 0 )
						++weights[0];

					//해당 플레이어가 나보다 왼쪽에 있다면 왼쪽으로 가는 것은 위험할 듯?
					if ( v.x_offset < 0 )
						++weights[1];
				
					//해당 플레이어가 나보다 오른쪽에 있다면 오른쪽으로 가는 것은 위험할 듯?
					if ( v.x_offset > 0 )
						++weights[2];
				
					//해당 플레이어가 나보다 아래에 있다면 아래로 가는 것은 위험할 듯?
					if ( v.y_offset > 0 )
						++weights[3];
				}
			}
			
			//갈 수 없는 방향에 대해 위험도를 최대값으로 설정
			if ( IsValidMove(DirectionCode.Up) == false )
				weights[0] = Integer.MAX_VALUE;
			
			if ( IsValidMove(DirectionCode.Left) == false )
				weights[1] = Integer.MAX_VALUE;

			if ( IsValidMove(DirectionCode.Right) == false )
				weights[2] = Integer.MAX_VALUE;

			if ( IsValidMove(DirectionCode.Down) == false )
				weights[3] = Integer.MAX_VALUE;

			//측정된 위험도의 최소값이 몇인지 계산
			for ( int weight : weights )
				if ( weight < min_weight )
					min_weight = weight;
			
			//'방향 섞기' 설정에 따라 위험도가 최소값과 같은 방향들 중 하나 선택 - 최소 하나 존재
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == min_weight )
					return shuffledDirections[iShuffledDirection];
				
			
			//이동할 수 있는 한도 내에서 '방향 섞기' 기능으로 정해 둔 순서에 따라 순차적으로 이동 수행 - 이동 가능한 방향은 최소 둘 이상 존재
			
				
				
	
		}
		// TODO 생존자 상태일 때 이동하기 위한 생각을 여기에 담으세요.
		
		return result;
	}

	@Override
	public void Corpse_Stay()
	{
		
		// TODO 시체 상태일 때 현재 알고 있는 것을 바탕으로 생각을 진행하려면 여기에 담으세요.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		DirectionCode result = DirectionCode.Stay;
		/*
		 * 포식자의 감염체 이동: 현 위치에 시체가 있는 경우 제자리에 머무름. 그렇지 않은 경우 생존자 및 시체가 가장 많은 방향으로 이동.
		 */
		
		//현 위치에 시체가 있는지 여부 검사
		boolean isCorpseHere = false;
		CellInfo here = GetCellInfo(myInfo.GetPosition());
		
		for ( int iPlayer = 0; iPlayer < here.GetNumberOfPlayersInTheCell(); ++iPlayer )
		{
			PlayerInfo other = here.GetPlayerInfo(iPlayer);
			
			if ( other.GetState() == PlayerInfo.State.Corpse )
			{
				isCorpseHere = true;
				break;
			}
		}
		
		//시체가 있는 경우 제자리에 머무름
		if ( isCorpseHere == true )
			return DirectionCode.Stay;
		
		//방향별로 생존자 및 시체의 수를 기록하기 위한 배열 사용
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] weights = new int[4];
		int max_weight = -1;
		
		//시야 내의 모든 플레이어에 대해 생존자 및 시체 수 계산
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			//해당 플레이어와 나 사이의 거리 비교
			Vector v = GetDistanceVectorBetweenPlayers(other);

			if ( other.GetState() != PlayerInfo.State.Infected )
			{
				if ( v.y_offset < 0 )
					++weights[0];
	
				if ( v.x_offset < 0 )
					++weights[1];
				
				if ( v.x_offset > 0 )
					++weights[2];
				
				if ( v.y_offset > 0 )
					++weights[3];
			}
		}
		
		//갈 수 없는 방향에 대해 생존자 및 시체 수를 최소값으로 설정
		if ( IsValidMove(DirectionCode.Up) == false )
			weights[0] = -1;
		
		if ( IsValidMove(DirectionCode.Left) == false )
			weights[1] = -1;

		if ( IsValidMove(DirectionCode.Right) == false )
			weights[2] = -1;

		if ( IsValidMove(DirectionCode.Down) == false )
			weights[3] = -1;

		//측정된 생존자 및 시체 수의 최대값이 몇인지 계산
		for ( int weight : weights )
			if ( weight > max_weight )
				max_weight = weight;
		
		//'방향 섞기' 설정에 따라 생존자 및 시체 수가 최대값과 같은 방향들 중 하나 선택 - 최소 하나 존재
		for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
			if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
				return shuffledDirections[iShuffledDirection];

		//여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
		return result;

		// TODO 감염체 상태일 때 이동 또는 대기하기 위한 생각을 여기에 담으세요.
		
	}

	@Override
	public void Soul_Stay()
	{
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
				
				shuffledDirection_values = new int [4];
				shuffledDirections = new DirectionCode[4];
				
				shuffledDirection_values[0]=0;
				shuffledDirection_values[1]=3;
				shuffledDirection_values[2]=2;
				shuffledDirection_values[3]=1;
				
				shuffledDirections[0] = DirectionCode.Up;
				shuffledDirections[1] = DirectionCode.Down;
				shuffledDirections[2] = DirectionCode.Right;
				shuffledDirections[3] = DirectionCode.Left;
				
			// TODO 직접 만든 데이터 필드에 대한 초기화 코드를 여기에 적으세요. 이 메서드는 게임이 시작되면 가장 먼저 호출됩니다.
		}
		
		

		// TODO 영혼 상태일 때 현재 알고 있는 것을 바탕으로 생각을 진행하려면 여기에 담으세요.
	}

	@Override
	public Point Soul_Spawn()
	{
		/*
		 * 시체 폭탄의 영혼 배치: 가장 감염체가 많은 칸을 골라 배치
		 */
		int seed = gameInfo.GetGameNumber();
		seed *= seed;
		seed = myInfo.GetID() - seed;
		seed *= seed;
		
		if ( seed <= 0 )
			seed += Integer.MAX_VALUE;
		
		int base_y = seed % ( Constants.Classroom_Width * Constants.Classroom_Height ) / Constants.Classroom_Width;
		int base_x = seed % Constants.Classroom_Width;
		
		Point basePoint = new Point(base_x, base_y);
		Point pointToSpawn = basePoint;

		//각 칸에 있는 감염체 수를 기록하기 위한 배열 사용
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int max_weight = -1;
		ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();
		
		//모든 감염체에 대해 검사 수행 - 9x9칸에 플레이어는 40명에 불과하므로 감염체가 가장 많은 칸 목록을 동시에 구성
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			//모든 감염체에 대해 배열 갱신 후 최대값 계산, 최대값을 가진 칸에 대한 좌표 목록 갱신
			if ( other.GetState() == PlayerInfo.State.Infected )
			{
				Point pos_other = other.GetPosition();
				
				++weights[pos_other.y][pos_other.x];

				//최대값이 바뀌었다면 '감염체가 가장 많은 칸' 목록 초기화
				if ( weights[pos_other.y][pos_other.x] > max_weight )
				{
					++max_weight; //weight는 항상 1씩 증가하므로 당연히 최대값도 1씩 증가함
					list_pos_max_weight.clear();
				}
				
				//현재 칸의 값이 최대값과 같다면 현재 칸을 '감염체가 가장 많은 칸' 목록에 추가 (위에서 최대값이 바뀌었다면 항상 추가됨)
				if ( weights[pos_other.y][pos_other.x] == max_weight )
					list_pos_max_weight.add(pos_other);
			}
		}
		
		//검사가 끝나면 감염체가 가장 많은 칸 목록에서 '기점 설정' 기능으로 정해 둔 기점과 가장 가까운 첫번째 칸 선택
		int min_distance = Integer.MAX_VALUE;
		Point myPosition = myInfo.GetPosition();
		
		for ( Point pos_max_weight : list_pos_max_weight )
		{
			int distance = GetDistance(myPosition, pos_max_weight);
			
			if ( distance < min_distance )
			{
				min_distance = distance;
				pointToSpawn = pos_max_weight;
			}
		}
		
		return pointToSpawn;

		// TODO 영혼 상태일 때 재배치하기 위한 생각을 여기에 담으세요.
		
	}

}
