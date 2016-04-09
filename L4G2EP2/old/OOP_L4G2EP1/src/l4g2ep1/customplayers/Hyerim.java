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
public class Hyerim extends Player
{
	
	DirectionCode[] Directions;
	int[] Direction_values;

	int changedirection=0;
	
	Point basePoint;
	
	int index_directionToMove=0;
	
	int soulcount=0;
	
	
	public Hyerim()
	{
		name = "Hyerim";
		acceptDirectInfection = false;	// TODO '직접 감염'=true
	}

	void SetBasePoint()
	{
		basePoint = new Point(4,4);
	}
	

	void SetDirections()
	{
		Directions = new DirectionCode [4];
		
		Direction_values = new int[4];
		
		for(int i=0; i<4; i++){
		
			Direction_values[(i+changedirection)%4] = (i+changedirection)%4;
		}
		Directions[3]=DirectionCode.Down;
		Directions[2]=DirectionCode.Right;
		Directions[1]=DirectionCode.Left;
		Directions[0]=DirectionCode.Up;
		
	}

	
	//방향설정 기준으로 주변 돌기
	public DirectionCode Survivor_Move_as_Turn(){
		DirectionCode result;
		
		//이동할 수 있는 한도 내에서 '방향 섞기' 기능으로 정해 둔 순서에 따라 순차적으로 이동 수행 - 이동 가능한 방향은 최소 둘 이상 존재
		do
		{
			result = Directions[index_directionToMove];
			++index_directionToMove;
			index_directionToMove %= 4;
		}
		while ( IsValidMove(result) == false );
		
		return result;
	}
	
	
	//감염체가 가장 많은 방향으로 이동
	public DirectionCode Survivor_Move_as_Goto_Infected(){
		
		
		//방향별로 다른 플레이어에 대한 위험도를 기록하기 위한 배열 사용
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] weights = new int[4];
		int min_weight = Integer.MAX_VALUE;
		
		//시야 내의 모든 플레이어에 대해 감염체 수	 계산
				for ( PlayerInfo other : othersInfo_withinSight )
				{
					//해당 플레이어와 나 사이의 거리 비교
					Vector v = GetDistanceVectorBetweenPlayers(other);

					if ( other.GetState() == PlayerInfo.State.Infected )
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
			if ( weights[ Direction_values[iShuffledDirection] ] == min_weight )
				return Directions[iShuffledDirection];

		//여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
		return DirectionCode.Stay;
		
	}
	
	//감염체 피하기
	public DirectionCode Survivor_Move_as_avoid_Infect(){
			
			
			//방향별로 다른 플레이어에 대한 위험도를 기록하기 위한 배열 사용
			//0: Up, 1: Left, 2: Right, 3: Down
			int[] weights = new int[4];
			int min_weight = Integer.MAX_VALUE;
			
			//시야 내의 모든 플레이어에 대해 감염체 수	 계산
					for ( PlayerInfo other : othersInfo_withinSight )
					{
						//해당 플레이어와 나 사이의 거리 비교
						Vector v = GetDistanceVectorBetweenPlayers(other);

						if ( other.GetState() == PlayerInfo.State.Infected  && v.GetDistance() > 1)
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
				if ( weight > min_weight )
					min_weight = weight;
			
			//'방향 섞기' 설정에 따라 위험도가 최소값과 같은 방향들 중 하나 선택 - 최소 하나 존재
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ Direction_values[iShuffledDirection] ] == min_weight )
					return Directions[iShuffledDirection];

			return Move_To_center();
			//여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
//			return DirectionCode.Stay;
			
		}

	//인접한거 빼고 감염체가 가장 많은 방향으로 이동
	public DirectionCode Survivor_Move_as_Goto_Infected_except_adject(){
			
			
			//방향별로 다른 플레이어에 대한 위험도를 기록하기 위한 배열 사용
			//0: Up, 1: Left, 2: Right, 3: Down
			int[] weights = new int[4];
			int min_weight = Integer.MAX_VALUE;
			
			//시야 내의 모든 플레이어에 대해 감염체 수	 계산
					for ( PlayerInfo other : othersInfo_withinSight )
					{
						//해당 플레이어와 나 사이의 거리 비교
						Vector v = GetDistanceVectorBetweenPlayers(other);

						if ( other.GetState() == PlayerInfo.State.Infected  && v.GetDistance() ==1)
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
				if ( weights[ Direction_values[iShuffledDirection] ] == min_weight )
					return Directions[iShuffledDirection];

			//여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
			return DirectionCode.Stay;
			
		}
		
		
		
		
	//감염체_포식자	
	public DirectionCode Infected_Move_as_Predator()
		{
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
				if ( weights[ Direction_values[iShuffledDirection] ] == max_weight )
					return Directions[iShuffledDirection];

			//여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
			return DirectionCode.Stay;
		}
	
	public DirectionCode Infected_Move_as_Stayer()
	{
		DirectionCode result;
		
		//현 위치에 시체가 있는지 검사
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
		
		//시체가 있다면 생존자 이동과 같은 방식으로 주변 칸으로 이동
		if ( isCorpseHere == true )
			result = Survivor_Move_as_Turn();
		//그렇지 않다면 정화 기도
		else
			result = DirectionCode.Stay;
 
		return result;
	}	
		
	//생존자_정찰병	
	//생존자수*시체수 가 가장 큰 방향으로 이동
	public DirectionCode Survivor_Move_as_Scout(){
		/*
		 * 정찰병의 생존자 이동: 생존자 수 x 시체와 감염체 수가 가장 큰 방향을 선택
		 */
		
		//방향별로 각 플레이어 수 및 포착 기대값을 기록하기 위한 배열 사용
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] survivors = new int[4];
		int[] others = new int[4];
		int[] weights = new int[4];
		int max_weight = -1;
		
		//현재 포착된 모든 플레이어에 대해 검사 수행
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
		
		//'방향 섞기' 설정에 따라 기대값이 최대값과 같은 방향들 중 하나 선택 - 최소 하나 존재
		for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
			if ( weights[ Direction_values[iShuffledDirection] ] == max_weight )
				return Directions[iShuffledDirection];

		//여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
		return DirectionCode.Stay;
	}
	
	
	public DirectionCode Infected_Move_as_Seeker()
	{
		/*
		 * 추적자의 감염체 이동: 인접한 칸을 제외한 시야 범위 내에서 생존자가 가장 많은 방향으로 이동
		 */
		
		//방향별로 생존자 수를 기록하기 위한 배열 사용
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] weights = new int[4];
		int max_weight = -1;
		
		//시야 내의 모든 플레이어에 대해 인접한 칸을 제외하고 생존자 수 계산
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			//해당 플레이어와 나 사이의 거리 비교
			Vector v = GetDistanceVectorBetweenPlayers(other);

			//인접한 칸(거리가 1인 칸)에 있지 않은 생존자에 대해 방향별 값 갱신  
			if ( other.GetState() == PlayerInfo.State.Survivor && v.GetDistance() > 1 )
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
		
		//갈 수 없는 방향에 대해 생존자 수를 최소값으로 설정
		if ( IsValidMove(DirectionCode.Up) == false )
			weights[0] = -1;
		
		if ( IsValidMove(DirectionCode.Left) == false )
			weights[1] = -1;

		if ( IsValidMove(DirectionCode.Right) == false )
			weights[2] = -1;

		if ( IsValidMove(DirectionCode.Down) == false )
			weights[3] = -1;

		//측정된 생존자 수의 최대값이 몇인지 계산
		for ( int weight : weights )
			if ( weight > max_weight )
				max_weight = weight;
		
		//'방향 섞기' 설정에 따라 생존자 수가 최대값과 같은 방향들 중 하나 선택 - 최소 하나 존재
		for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
			if ( weights[ Direction_values[iShuffledDirection] ] == max_weight )
				return Directions[iShuffledDirection];

		//여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
		return DirectionCode.Stay;
	}
	
	
	//다른 플레이어수가 가장 적은 방향으로 이동
	public DirectionCode Survivor_Move_as_Loner(){
		/*
		 * 외톨이의 생존자 이동: 다른 플레이어가 가장 적은 방향을 선택
		 */
		
		//방향별로 다른 플레이어에 대한 위험도를 기록하기 위한 배열 사용
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] weights = new int[4];
		int min_weight = Integer.MAX_VALUE;
		
		//현재 포착된 모든 플레이어에 대해 위험도 계산
		for ( PlayerInfo other : othersInfo_detected )
		{
			//해당 플레이어와 나 사이의 거리 비교
			Vector v = GetDistanceVectorBetweenPlayers(other);

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
			if ( weights[ Direction_values[iShuffledDirection] ] == min_weight )
				return Directions[iShuffledDirection];

		//여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
		return DirectionCode.Stay;
	}
	
	
	//감염체_시체가 있으면 생존자와 같이 행동하고 시체가 없으면 Stay
	public DirectionCode Infected_Move_as_Corpse_Bomb(){

		DirectionCode result;
		
		//현 위치에 시체가 있는지 검사
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
		
		//시체가 있다면 생존자 이동과 같은 방식으로 주변 칸으로 이동
		if ( isCorpseHere == true )
			result = Survivor_Move_as_Turn();
		//그렇지 않다면 정화 기도
		else
			result = DirectionCode.Stay;
 
		return result;
	}
	//영혼배치_감염체가 가장 많은 칸으로
	public Point Soul_Spawn_as_Corpse_Bomb(){
		/*
		 * 시체 폭탄의 영혼 배치: 가장 감염체가 많은 칸을 골라 배치
		 */
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
	}
	//영혼배치_초기위치로

	public DirectionCode Move_To_center(){
		if(this.myInfo.GetPosition().x<4)
			return DirectionCode.Right;
		else if(this.myInfo.GetPosition().x>4)
			return DirectionCode.Left;
		else if(this.myInfo.GetPosition().y<4)
			return DirectionCode.Down;
		else
			return DirectionCode.Up;
	}
	
	
	public Point Soul_Spawn_as_not_Corpse_Bomb(){
		/*
		 * 시체 폭탄의 영혼 배치: 가장 감염체가 많은 칸을 골라 배치
		 */
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
				if ( weights[pos_other.y][pos_other.x] == -1 )
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
	}

	
	
	
	public Point Soul_Spawn_as_BaseSetting(){
		return basePoint;
	}
	
	
	
	@Override
	public DirectionCode Survivor_Move()
	{
		return Survivor_Move_as_Goto_Infected();
		// TODO 생존자 상태 이동
	}

	@Override
	public void Corpse_Stay()
	{
		
		
		
		
		
		// TODO 시체 상태일 때
	}

	@Override
	public DirectionCode Infected_Move()
	{
		return Infected_Move_as_Stayer();

	}

	@Override
	public void Soul_Stay()
	{
		
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			SetDirections();
			SetBasePoint();
		}
		// TODO 영혼 상태
	}

	
	@Override
	public Point Soul_Spawn()
	{
		return Soul_Spawn_as_Corpse_Bomb();
		
		
		
		
		
		// TODO 영혼 배치
		
	}

}
