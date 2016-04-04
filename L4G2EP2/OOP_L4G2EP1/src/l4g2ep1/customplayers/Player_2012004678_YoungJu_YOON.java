package l4g2ep1.customplayers;

import java.util.ArrayList;

import l4g2ep1.*;
import l4g2ep1.common.*;


/**
 * 실제 게임에 참여할 플레이어입니다.
 * 
 * 생존자 이동: 직접 감염 의사가 반영되기 전(0~10턴)에는 다른 플레이어를 무조건 피합니다.
 *                   직접 감염 의사가 반영되고 나면(11턴), 20턴 이전까지 시체와 감염체를 최대한 피하고, 그 이후부터는 포착 점수를 높이기 위해 생존자 수 x 시체 및 감염체 수가 가장 큰 방향으로 이동합니다.
 * 감염체 이동: 현재 턴 수와 최대 생존 턴 수의 차가 10 미만일 경우 인접한 칸을 제외한 시야 범위 내에서 생존자가 가장 많은 방향으로 이동합니다.
 *                   이 때, 생존자 수가 충분하지 않으면 이동할 수 있는 위치에 시체가 있는지를 살피고, 있으면 시체가 있는 곳으로 이동, 없을 경우 정화 기도를 올립니다.
 *                   현재 턴 수와 최대 생존 턴 수의 차가 10 이상일 경우 현 위치에 시체가 있는 경우 주변 칸으로 이동하며 그렇지 않은 경우 정화 기도를 올립니다.
 * 영혼 배치: 생존자보다 시체+감염체 수가 많고, 최대 생존 턴 수가 15 미만이고, 현재 턴 수가 75 미만이면 기점으로 배치합니다. 
 *                그렇지 않을 경우, 가장 감염체가 많은 칸을 우선으로 하고,
 *                감염체의 숫자가 같아 첫 턴의 점수 획득 기댓값이 같은 칸이 여러 개인 경우 시체로 존재할 3턴의 총 기댓값을 계산하여 제일 높은 칸으로 배치합니다.
 * 직접 감염: 항상 거절합니다.
 * 
 * @author Racin
 *
 */
public class Player_2012004678_YoungJu_YOON extends Player
{
	/**
	 * '방향 섞기' 기능에 사용되는 방향 목록 및 해당 방향을 나타내는 숫자 목록입니다.
	 * 모든 샘플 플레이어들은 여러 방향들 중 하나를 선택할 때 여기 있는 목록에서 더 앞에 있는 것을 우선시합니다.
	 * 이 목록은 게임 번호와 ID에 의해 첫 턴의 Soul_Stay()에서 결정됩니다.
	 */
	DirectionCode[] shuffledDirections;
	int[] shuffledDirection_values;

	/**
	 * '기점 지정' 기능에 사용되는 기점 좌표입니다.
	 * 모든 샘플 플레이어들은 여러 좌표들 중 하나를 선택할 때 기점 좌표와의 거리가 가까운 것을 우선시합니다.
	 * 이 좌표값은 게임 번호와 ID에 의해 첫 턴의 Soul_Stay()에서 결정됩니다. 
	 */
	Point basePoint;
	
	/**
	 * 이번 이동에서 선택하게 될 방향이 '방향 섞기' 기능을 통해 만든 방향 목록 중 몇 번째 것인지를 나타냅니다.
	 * 구급대원은 이 값을 활용하여 생존자 / 감염체 이동을 수행합니다.
	 */
	int index_directionToMove;

	/**
	 * 플레이어 클래스의 생성자입니다.
	 */
	public Player_2012004678_YoungJu_YOON()
	{
		name = "NEWBIE^_^";
		
		//직접 감염을 항상 거절
		this.acceptDirectInfection = false;
				
		//외톨이는 생존자 이동에서 다른 플레이어가 가장 적은 방향으로 이동하기 위해 포착 정보를 수신함
		this.receiveOthersInfo_detected = true;
	
	}
	
	/**
	 * '방향 섞기' 기능을 사용하기 위해 방향 목록을 초기화합니다.
	 * 이 메서드는 첫 턴의 Soul_Stay()에서 호출되어야 합니다.
	 */
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

	/**
	 * '기점 지정' 기능을 사용하기 위해 기점 좌표를 초기화합니다.
	 * 이 메서드는 첫 턴의 Soul_Stay()에서 호출되어야 합니다.
	 */
	void SetBasePoint()
	{
		//초기화에 필요한 임의의 자연수 하나 생성
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
	}

	@Override
	public DirectionCode Survivor_Move()
	{
		
		// 턴 수가 11 미만일 경우 누구든 피해다닙니다. 아무도 못 믿음.
		if(this.gameInfo.GetCurrentTurnNumber() < 11){
			
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
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == min_weight )
					return shuffledDirections[iShuffledDirection];
			
		}
		
		// 턴 수가 11 이상 20 미만일 경우 시체와 감염체를 최대한 피합니다. 
		else if(this.gameInfo.GetCurrentTurnNumber() >= 11 && this.gameInfo.GetCurrentTurnNumber() < 20){
			
			//방향별로 다른 플레이어에 대한 위험도를 기록하기 위한 배열 사용
			//0: Up, 1: Left, 2: Right, 3: Down
			int[] weights = new int[4];
			int min_weight = Integer.MAX_VALUE;
			
			//현재 포착된 모든 플레이어에 대해 위험도 계산
			for ( PlayerInfo other : othersInfo_detected )
			{

				//해당 플레이어와 나 사이의 거리 비교
				Vector v = GetDistanceVectorBetweenPlayers(other);
				
				// 포착된 플레이어가 감염체 상태일 때
				if ( other.GetState() == PlayerInfo.State.Infected ){

					//해당 플레이어가 나보다 위에 있다면 위로 가는 것은 위험할 듯?
					if ( v.y_offset < 0 )
						weights[0] += 3;
	
					//해당 플레이어가 나보다 왼쪽에 있다면 왼쪽으로 가는 것은 위험할 듯?
					if ( v.x_offset < 0 )
						weights[1] += 3;
					
					//해당 플레이어가 나보다 오른쪽에 있다면 오른쪽으로 가는 것은 위험할 듯?
					if ( v.x_offset > 0 )
						weights[2] += 3;
					
					//해당 플레이어가 나보다 아래에 있다면 아래로 가는 것은 위험할 듯?
					if ( v.y_offset > 0 )
						weights[3] += 3;
				
				}
				
				// 포착된 플레이어가 시체 상태일 때. 시체는 안 움직이니까 한 칸만 검사하면 될 듯?
				// 가중치 : 내가 시체라고 인식했을 때 깨어날 확률 1/3이므로 감염체의 1/3
				else if ( other.GetState() == PlayerInfo.State.Infected ){

					//해당 플레이어가 나보다 한 칸 위에 있다면 위로 가는 것은 위험할 듯?
					if ( v.y_offset == -1 )
						weights[0] += 1;
	
					//해당 플레이어가 나보다 한 칸 왼쪽에 있다면 왼쪽으로 가는 것은 위험할 듯?
					if ( v.x_offset == -1 )
						weights[1] += 1;
					
					//해당 플레이어가 나보다 한 칸 오른쪽에 있다면 오른쪽으로 가는 것은 위험할 듯?
					if ( v.x_offset == 1 )
						weights[2] += 1;
					
					//해당 플레이어가 나보다 한 칸 아래에 있다면 아래로 가는 것은 위험할 듯?
					if ( v.y_offset == 1 )
						weights[3] += 1;
				}
				
				// 감염체도 시체도 아니면 아무것도 안 함
				else{
				
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
			
		
		}
		
		// 턴 수가 20을 넘으면 최대한 포착 점수를 벌고 죽기 위해 공격적으로 이동합니다
		// 포착 점수를 채우기 위해 부활한다면 무조건 여기로 옵니다. 어차피 그때 가서 최대 생존 턴 수 채우면 다른 스탯이 부족해짐.
		else{
			
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
					if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
						return shuffledDirections[iShuffledDirection];
			
		}
		
		//여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
		return DirectionCode.Stay;
		
	}

	@Override
	public void Corpse_Stay() { }

	@Override
	public DirectionCode Infected_Move()
	{
		
		DirectionCode result;
		
		// 일단 열심히 살려고 아둥바둥하는 생존자들을 잡아먹어 봅시다
		if(this.gameInfo.GetCurrentTurnNumber() - this.myScore.GetSurvivor_Max_Survived_Turns() < 10){
			
			//방향별로 생존자 수를 기록하기 위한 배열 사용
			//0: Up, 1: Left, 2: Right, 3: Down
			double[] weights = new double[4];
			double max_weight = -1;
			
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
				//시야에 없는 생존자의 수 예상 
				else if ( other.GetState() == PlayerInfo.State.Corpse && v.GetDistance() > 2 )
				{
					if ( v.y_offset < 0 )
						weights[0] += 2 / (v.GetDistance() * v.GetDistance());
					
					if ( v.x_offset < 0 )
						weights[1] += 2 / (v.GetDistance() * v.GetDistance());
					
					if ( v.x_offset > 0 )
						weights[2] += 2 / (v.GetDistance() * v.GetDistance());
					
					if ( v.y_offset > 0 )
						weights[3] += 2 / (v.GetDistance() * v.GetDistance());
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
			for (double weight : weights )
				if ( weight > max_weight )
					max_weight = weight;
			
			// 이동 의사 결정
			// 1. 계산해보니 생존자가 별로 없다. 휴 먹을 것도 없는데 시체먹기 점수라도 쌓을 수 있나 볼까?
			if( max_weight < 1 ){
				
				// 방향별로 시체 수를 기록하기 위한 배열 사용
				//0: Up, 1: Left, 2: Right, 3: Down
				int[] corpse_counter = new int[4];
				int max_corpse = -1;
				
				// 다음 턴에 이동 가능한 자리에 한해 시체 수 계산
				for ( PlayerInfo other : othersInfo_withinSight ){
					
					Vector v = GetDistanceVectorBetweenPlayers(other);

					//인접한 칸(거리가 1인 칸)에 있는 시체에 대해 방향별 값 갱신  
					if ( other.GetState() == PlayerInfo.State.Corpse )
					{
						if ( v.y_offset == -1 )
							++corpse_counter[0];
			
						else if ( v.x_offset == -1 )
							++corpse_counter[1];
						
						else if ( v.x_offset == 1 )
							++corpse_counter[2];
						
						else if ( v.y_offset == 1 )
							++corpse_counter[3];
					}
					
				}
				
				//갈 수 없는 방향에 대해 시체 수를 최소값으로 설정
				if ( IsValidMove(DirectionCode.Up) == false )
					corpse_counter[0] = -1;
				
				if ( IsValidMove(DirectionCode.Left) == false )
					corpse_counter[1] = -1;

				if ( IsValidMove(DirectionCode.Right) == false )
					corpse_counter[2] = -1;

				if ( IsValidMove(DirectionCode.Down) == false )
					corpse_counter[3] = -1;

				//측정된 시체 수의 최대값이 몇인지 계산
				for ( int corpses : corpse_counter )
					if ( corpses > max_corpse )
						max_corpse = corpses;
				
				//주변에 시체가 있다면 시체먹기 스탯이나 올리자
				if(max_corpse > 0){
					//'방향 섞기' 설정에 따라 시체 수가 최대값과 같은 방향들 중 하나 선택 - 최소 하나 존재
					for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
						if ( corpse_counter[ shuffledDirection_values[iShuffledDirection] ] == max_corpse )
							return shuffledDirections[iShuffledDirection];
		
				}
				
				//시체도 없다! 망했다 걍 자살 ㄱㄱ
				else{
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
					
					//시체가 있다면 이동할 수 있는 한도 내에서 옆 칸으로 이동
					if ( isCorpseHere == true ){
						do
						{
							result = shuffledDirections[index_directionToMove];
							++index_directionToMove;
							index_directionToMove %= 4;
						}
						while ( IsValidMove(result) == false );
					}
					
					//그렇지 않다면 정화 기도
					else
						result = DirectionCode.Stay;
					
					return result;
				}
				
			}
			
			//먹잇감 포착 자살 ㄴㄴ 생존자가 많은 곳으로 이동
			else{
			
				//'방향 섞기' 설정에 따라 생존자 수가 최대값과 같은 방향들 중 하나 선택 - 최소 하나 존재
				for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
					if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
						return shuffledDirections[iShuffledDirection];
				
			}
	
		}

		
		// 후반에는 걍 자살 ㄱㄱ
		else{
			
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
				
				//시체가 있다면 이동할 수 있는 한도 내에서 옆 칸으로 이동
				if ( isCorpseHere == true )
					do
					{
						result = shuffledDirections[index_directionToMove];
						++index_directionToMove;
						index_directionToMove %= 4;
					}
					while ( IsValidMove(result) == false );
				
				//그렇지 않다면 정화 기도
				else
					result = DirectionCode.Stay;
				
				return result;
			
		}
		//여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
		return DirectionCode.Stay;
		
	}

	@Override
	public void Soul_Stay()
	{
		//이번 턴이 첫 턴인 경우 '방향 섞기' 기능과 '기점 지정' 기능에 필요한 필드 초기화
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			ShuffleDirections();
			SetBasePoint();
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		// 지금 생존자 / 감염체+시체가 몇 명이나 돌아다니고 있는지 알아봅니다.
		int survivor = 0;
		int others = 0;
		for ( PlayerInfo other : othersInfo_withinSight ){
			if ( other.GetState() == PlayerInfo.State.Survivor )
				++survivor;
			else
				++others;
		}
		
		// 감염체 + 시체가 더 많고, 생존자로 있었던 최고 턴이 15 미만이면 망한 포착 점수를 살리기 위해 생존자로 활동합니다.
		// 근데 계속 살아나기만 하면 다른 점수들이 꿈도 희망도 없으므로 75턴 되기 전까지만
		if( survivor < others && this.myScore.GetSurvivor_Max_Survived_Turns() < 20 && this.gameInfo.GetCurrentTurnNumber() < 75){
			
			return basePoint;
			
		}
		
		// 생존자가 더 많거나, 내 생존자 최고 턴이 15가 넘거나, 그렇지 않아도 게임이 후반으로 달려가면(75턴째 이상) 시체 스탯이나 쌓으러 걍 자살 ㄱㄱ
		// 감염체 무더기의 힘은 위대하므로 가장 감염체가 많은 칸을 우선으로 하고,
		// 감염체의 숫자가 같아 첫 턴의 점수 획득 기댓값이 같은 칸이 여러 개인 경우 시체로 존재할 3턴의 총 기댓값을 계산하여 제일 높은 칸 선택
		else{
			
			Point pointToSpawn = basePoint;

			//각 칸에 있는 감염체 수를 기록하기 위한 배열 사용
			double [][] weights = new double[Constants.Classroom_Height][Constants.Classroom_Width];
			double max_weight = -1;
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
			
			//검사가 끝나면 감염체가 가장 많은 칸 목록에서 내가 시체가 될 3턴 동안 맞이할 감염체 수의 기댓값이 가장 높은 칸 선택
			double max_expectation = -1;
			
			for ( Point pos_max_weight : list_pos_max_weight )
			{
				double current_expectation = 0;
				
				// 감염체가 가장 많은 칸 주변의 각 칸이 유효하면 거기 있는 감염체 수를 가중치를 곱해 더해줍니다.
				// 거리가 2인 곳 : 가중치 0.04
				//-2, 0
				if(pos_max_weight.x - 2 > -1)
					current_expectation += 0.04 * (weights[pos_max_weight.x - 2][pos_max_weight.y]);
				
				//-1, -1
				if(pos_max_weight.x - 1 > -1 && pos_max_weight.y - 1 > -1)
					current_expectation += 0.04 * (weights[pos_max_weight.x - 1][pos_max_weight.y - 1]);
				
				//0, -2
				if(pos_max_weight.y - 2 > -1)
					current_expectation += 0.04 * (weights[pos_max_weight.x][pos_max_weight.y - 2]);
				
				//1, -1
				if(pos_max_weight.x + 1 < Constants.Classroom_Width && pos_max_weight.y -1 > -1)
					current_expectation += 0.04 * (weights[pos_max_weight.x + 1][pos_max_weight.y - 1]);
				
				//2, 0
				if(pos_max_weight.x + 2 < Constants.Classroom_Width)
					current_expectation += 0.04 * (weights[pos_max_weight.x + 2][pos_max_weight.y]);
					
				//1, 1
				if(pos_max_weight.x + 1 < Constants.Classroom_Width && pos_max_weight.y + 1 < Constants.Classroom_Height)
					current_expectation += 0.04 * (weights[pos_max_weight.x + 1][pos_max_weight.y + 1]);
				
				//0, 2
				if(pos_max_weight.y + 2 < Constants.Classroom_Height)
					current_expectation += 0.04 * (weights[pos_max_weight.x][pos_max_weight.y + 2]);
					
				//-1, 1
				if(pos_max_weight.x -1 > -1 && pos_max_weight.y + 1 < Constants.Classroom_Height)
					current_expectation += 0.04 * (weights[pos_max_weight.x -1][pos_max_weight.y + 1]);
				
				//거리가 1인 곳 : 가중치 0.2
				//Up
				if(pos_max_weight.y - 1 > -1)
					current_expectation += 0.2 * (weights[pos_max_weight.x][pos_max_weight.y - 1]);
				
				//Left
				if(pos_max_weight.x - 1 > -1)
					current_expectation += 0.2 * (weights[pos_max_weight.x - 1][pos_max_weight.y]);
				
				//Right
				if(pos_max_weight.x + 1 < Constants.Classroom_Width)
					current_expectation += 0.2 * (weights[pos_max_weight.x + 1][pos_max_weight.y]);
				
				//Down
				if(pos_max_weight.y + 1 < Constants.Classroom_Height)
					current_expectation += 0.2 * (weights[pos_max_weight.x][pos_max_weight.y + 1]);
				
				//거리가 0인 곳(감염체가 가장 많은 칸) : 가중치 1
				++current_expectation;

				//최대값이 바뀌었다면 최대값 변수의 값을 바뀐 값으로 설정하고, 선택할 칸 바꾸기
				if ( current_expectation > max_expectation )
				{
					max_expectation = current_expectation;
					pointToSpawn = pos_max_weight;
				}
				
			}
			
			return pointToSpawn;
		}
	}
}
