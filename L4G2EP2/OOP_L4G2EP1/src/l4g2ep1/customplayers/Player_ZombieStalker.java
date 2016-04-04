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


public class Player_ZombieStalker extends Player
{
	DirectionCode[] shuffledDirections;
	int[] shuffledDirection_values;
	Point basePoint;
	
	void ShuffleDirections()
	{
		//초기화에 필요한 임의의 자연수 하나 생성
		int seed = myInfo.GetID();
		seed *= seed;
		seed = gameInfo.GetGameNumber() - seed;
		seed *= seed;
		
		if ( seed <= 0 )
			seed += Integer.MAX_VALUE;
		
		/*
		 * 네 가지 방향을 나열할 수 있는 방법은 총 4 * 3 * 2 * 1 = 24가지 존재하므로
		 * seed를 24로 나눈 나머지를 토대로 방향 설정.
		 * (24가지면 그냥 switch문 쓰는게 더 쉽고 빠르겠지만 코드가 길어지니 직접 계산)
		 */
		//각 자리에서 '값이 증가'해야 하는 정도를 먼저 측정. seed를 24로 나눈 나머지가 0인 경우 0000, 23인 경우 3210이 됨.
		int[] offsets = new int[4];
		
		offsets[0] = seed % 24 / 6;
		offsets[1] = seed % 6 / 2;
		offsets[2] = seed % 2;
		offsets[3] = 0;

		//위에서 측정한 offset을 통해 각 자리의 값을 계산.
		//모든 계산이 끝나면 offset이 0000이었을 때 값은 0123이 됨 (유일하게 offset이 3210이었을 때만 그 배열 그대로 값이 됨)
		shuffledDirection_values = new int[4];

		for ( int iCurrent = 0; iCurrent < 4; ++iCurrent )
		{
			int current_value = 0;
			
			while ( true )
			{
				//현재 자리보다 앞에 이미 같은 값이 있는지 검사 
				boolean isSameValueFound = false;
				
				for ( int iPrevious = iCurrent - 1; iPrevious >= 0; --iPrevious )
					if ( shuffledDirection_values[iPrevious] == current_value )
					{
						isSameValueFound = true;
						break;
					}
				
				//같은 값이 있는 경우 현재 자리의 값을 1 증가시키고 다시 검사
				if ( isSameValueFound == true )
				{
					++current_value;
				}
				//같은 값이 없고 현재 자리의 offset이 0이 아닌 경우(여기서 값을 증가시켜야 하는 경우)
				//offset을 1 깎은 다음 현재 자리의 값을 1 증가시키고 다시 검사 
				else if ( offsets[iCurrent] != 0 )
				{
					--offsets[iCurrent];
					++current_value;
				}
				//같은 값도 없고 offset도 0인 경우 값 계산 완료
				else
				{
					break;
				}
			}
			
			//계산이 끝난 현재 자리의 값을 기록
			shuffledDirection_values[iCurrent] = current_value;
		}
				
		//0: Up, 1: Left, 2: Right, 3: Down으로 간주하여 각 자리의 값을 토대로 실제 방향 설정 
		shuffledDirections = new DirectionCode[4];
		
		for ( int i = 0; i < 4; ++i )
			switch ( shuffledDirection_values[i] )
			{
			case 0:
				shuffledDirections[i] = DirectionCode.Up;
				break;
			case 1:
				shuffledDirections[i] = DirectionCode.Left;
				break;
			case 2:
				shuffledDirections[i] = DirectionCode.Right;
				break;
			default:
				shuffledDirections[i] = DirectionCode.Down;
				break;
			}
	}

	public int DirectionToGo;
	
	public DirectionCode move(){
		switch(DirectionToGo){
		case 0:
			return DirectionCode.Up;
		case 1:
			return DirectionCode.Right;
		case 2:
			return DirectionCode.Down;
		case 3:
			return DirectionCode.Left;
		}
		return DirectionCode.Stay;
	}
	
	public Player_ZombieStalker()
	{
		name = "좀비쨔응하앍";	// TODO 자신이 만들 플레이어의 이름으로 name 필드를 초기화하세요.
		acceptDirectInfection = false;				// TODO '직접 감염'을 받으려는 경우 이 필드를 true로 두고 아닌 경우 false로 두세요.
	}
	
	public int IsSafe(){
		int[] survivors = new int[4];
		int count = 0;
		
		int[][] map = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		//모든 감염체에 대해 검사 수행 - 9x9칸에 플레이어는 40명에 불과하므로 감염체가 가장 많은 칸 목록을 동시에 구성
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			//모든 감염체에 대해 배열 갱신 후 최대값 계산, 최대값을 가진 칸에 대한 좌표 목록 갱신
			if ( other.GetState() == PlayerInfo.State.Infected )
			{
				Point pos_other = other.GetPosition();
				
				map[pos_other.y][pos_other.x] = 1;
			}
		}
		Point Me = myInfo.GetPosition();
		if(IsValidMove(Me.y, Me.x+1) == false)
			if(survivors[1] != -1){
			survivors[1] = -1;
			++count;
		}if(IsValidMove(Me.y, Me.x-1) == false)
			if(survivors[3] != -1){
			survivors[3] = -1;
			++count;
		}if(IsValidMove(Me.y+1, Me.x) == false)
			if(survivors[2] != -1){
			survivors[2] = -1;
			++count;
		}if(IsValidMove(Me.y-1, Me.x) == false)
			if(survivors[0] != -1){
			survivors[0] = -1;
			++count;
		}
			
		if(IsValidMove(Me.y+1,Me.x+1) == true && map[Me.y+1][Me.x+1] == 1){
			if(survivors[1] != -1){
				survivors[1] = -1;
				++count;
			}if(survivors[2] != -1){
				survivors[2] = -1;
				++count;
			}
		}if(IsValidMove(Me.y-1,Me.x+1) == true &&map[Me.y-1][Me.x+1] == 1){
			if(survivors[1] != -1){
				survivors[1] = -1;
				++count;
			}
			if(survivors[0] != -1){
				survivors[0] = -1;
				++count;
			}
		}if(IsValidMove(Me.y+1,Me.x-1) == true &&map[Me.y+1][Me.x-1] == 1){
			if(survivors[3] != -1){
				survivors[3] = -1;
				++count;
			}
			if(survivors[2] != -1){
				survivors[2] = -1;
				++count;
			}
		}if(IsValidMove(Me.y-1,Me.x-1) == true &&map[Me.y-1][Me.x-1] == 1){
			if(survivors[0] != -1){
				survivors[0] = -1;
				++count;
			}
			if(survivors[3] != -1){
				survivors[3] = -1;
				++count;
			}
		}
		if(IsValidMove(Me.y,Me.x+2) == true &&map[Me.y][Me.x+2] == 1)
			if(survivors[1] != -1){
				survivors[1] = -1;
				++count;
			}
		if(IsValidMove(Me.y,Me.x-2) == true &&map[Me.y][Me.x-2] == 1)
			if(survivors[3] != -1){
				survivors[3] = -1;
				++count;
			}
		if(IsValidMove(Me.y+2,Me.x) == true &&map[Me.y+2][Me.x] == 1)
			if(survivors[2] != -1){
				survivors[2] = -1;
				++count;
			}
		if(IsValidMove(Me.y-2,Me.x) == true &&map[Me.y-2][Me.x] == 1)
			if(survivors[0] != -1){
				survivors[0] = -1;
				++count;
			}
		
		if(count == 3){
			for(int i = 0;i<4;++i){
				if(survivors[i] != -1)
					return i;
			}
		}
		else if(count == 4){
			return 5;
		}
		else{
			int dir;
			if(Me.x < 5){
				dir = 1;
			}
			else{
				dir = 3;
			}
			for(int i = 0;i<4;++i){
				if(survivors[(dir+i)%4] != -1)
					return (dir+i)%4;
			}
		}
		return 5;
		
	}
	
	@Override
	public DirectionCode Survivor_Move()
	{
		// TODO 생존자 상태일 때 이동하기 위한 생각을 여기에 담으세요.
		int D = IsSafe();
		if(D < 4)
			DirectionToGo = D;
		else{
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
				if ( weights[ iShuffledDirection ] == max_weight )
					if(IsValidMove(shuffledDirections[iShuffledDirection]) == true)
						return shuffledDirections[iShuffledDirection];
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
					if(IsValidMove(shuffledDirections[iShuffledDirection]) == true)
						return shuffledDirections[iShuffledDirection];
			
		}
		return move();
	}

	@Override
	public void Corpse_Stay()
	{
		// TODO 시체 상태일 때 현재 알고 있는 것을 바탕으로 생각을 진행하려면 여기에 담으세요.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		// TODO 감염체 상태일 때 이동 또는 대기하기 위한 생각을 여기에 담으세요.
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
		if ( isCorpseHere == true ){
			if(IsValidMove(myInfo.GetPosition().x+1,myInfo.GetPosition().y)){
				DirectionToGo = 1;
			}if(IsValidMove(myInfo.GetPosition().x-1,myInfo.GetPosition().y)){
				DirectionToGo = 3;
			}if(IsValidMove(myInfo.GetPosition().x,myInfo.GetPosition().y+1)){
				DirectionToGo = 2;
			}if(IsValidMove(myInfo.GetPosition().x,myInfo.GetPosition().y-1)){
				DirectionToGo = 0;
			}
			return move();
		}
		//그렇지 않다면 정화 기도
		else
			result = DirectionCode.Stay;
 
		return result;
	}

	@Override
	public void Soul_Stay()
	{
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			// TODO 직접 만든 데이터 필드에 대한 초기화 코드를 여기에 적으세요. 이 메서드는 게임이 시작되면 가장 먼저 호출됩니다.
			int seed = gameInfo.GetGameNumber();
			seed *= seed;
			seed = myInfo.GetID() - seed;
			seed *= seed;
			
			if ( seed <= 0 )
				seed += Integer.MAX_VALUE;
			
			//seed를 강의실의 총 칸 수로 나눈 나머지를 토대로 기점 좌표 설정
			int base_y = seed % ( Constants.Classroom_Width * Constants.Classroom_Height ) / Constants.Classroom_Width;
			int base_x = seed % Constants.Classroom_Width;
			
			basePoint = new Point(base_x, base_y);
			ShuffleDirections();
		}

		// TODO 영혼 상태일 때 현재 알고 있는 것을 바탕으로 생각을 진행하려면 여기에 담으세요.
	}

	@Override
	public Point Soul_Spawn()
	{
		// TODO 영혼 상태일 때 재배치하기 위한 생각을 여기에 담으세요.
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

}
