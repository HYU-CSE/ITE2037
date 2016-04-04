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
public class Player_DKpal extends Player
{
	public Player_DKpal()
	{
		name = "DKpal";
		acceptDirectInfection = false;
		receiveOthersInfo_detected = true;
		death_point=0;
	}

	DirectionCode[] shuffledDirections;
	int[] shuffledDirection_values;
	int death_point;

	Point basePoint;
	
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

	@Override
	public DirectionCode Survivor_Move()
	{
		// TODO 정찰병의 행동 패턴은 그대로 담아옴.

		//방향별로 각 플레이어 수 및 포착 기대값을 기록하기 위한 배열 사용
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] survivors = new int[4];
		int[] others = new int[4];
		int[] weights = new int[4];
		int max_weight = -1;
		
		//현재 포착된 모든 플레이어에 대해 검사 수행
		if(this.gameInfo.GetCurrentTurnNumber()<12)
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
			}
			
	
			//포착 기대값: 생존자 수 x 시체 및 감염체 수 계산
			for ( int i = 0; i < 4; ++i )
				weights[i] = survivors[i];
			
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
		
		else
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
			
			//'방향 섞기' 설정에 따라 기대값이 최대값과 같은 방향들 중 하나 선택 - 최소 하나 존재
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
					return shuffledDirections[iShuffledDirection];
		}
		//여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
		return DirectionCode.Stay;
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
		DirectionCode result = DirectionCode.Stay;

		boolean isCorpseHere = false;
		if(this.myInfo.GetHitPoint() < 15 && (this.gameInfo.GetCurrentTurnNumber()-death_point) < 5)
		{		
			//현 위치에 시체가 있는지 검사
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
			
			//시체가 있다면 대기
			if ( isCorpseHere == true )
				result = DirectionCode.Stay;
			//그렇지 않다면 정화 기도
			else
				result = DirectionCode.Stay;
		}
		else
		{
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
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
					return shuffledDirections[iShuffledDirection];


		}
		return result;
	}

	@Override
	public void Soul_Stay()
	{
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			// TODO 직접 만든 데이터 필드에 대한 초기화 코드를 여기에 적으세요. 이 메서드는 게임이 시작되면 가장 먼저 호출됩니다.
			basePoint = new Point(2,2);	// 시작 시 기본 기점 지점은 변수에 관계없이 2,2로 잡음.
			death_point=0;				//시체가 된뒤 감염자가 되기 전에 감염자가 될 턴수를 저장할 변수.
			ShuffleDirections();
		}

		// TODO 시체가 되면 매턴 현재 턴수를 저장. 감염자가 된 뒤에 감염자가 된지 얼마나 됐는지 판별하기 위한 변수.
		death_point = this.gameInfo.GetCurrentTurnNumber();
	}

	@Override
	public Point Soul_Spawn()
	{
		// TODO 영혼 상태일 때 재배치하기 위한 생각을 여기에 담으세요.
		// S_max점수가 3보다 낮을 경우에는 3보다 높고, 그렇지 않으면 S_max 점수 +1 보다 높은 숫자의 시체 및 좀비가 있는 칸이 있는 경우 그 칸에 리스폰.
		// 아닐 경우 생존자 x 시체 및 감염자 가 높은 곳(포착 점수가 높은 곳)으로 배치.  
		Point pointToSpawn = new Point(4,4);

		//각 칸에 있는 감염체 수를 기록하기 위한 배열 사용
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int max_weight = -1;
		ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();
		
		//모든 감염체에 대해 검사 수행 - 9x9칸에 플레이어는 40명에 불과하므로 감염체 및 시체가 가장 많은 칸 목록을 동시에 구성
		if(gameInfo.GetCurrentTurnNumber()>12)
			return new Point(2,2);
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
			if ( other.GetState() == PlayerInfo.State.Corpse )
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
		if(max_weight > 3)
		{
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
		}
		else
		{
			int[][] survivor_value = new int[Constants.Classroom_Width+4][Constants.Classroom_Height+4];
			int[][] other_value = new int[Constants.Classroom_Width+4][Constants.Classroom_Height+4];
			int[][] value = new int[Constants.Classroom_Width+4][Constants.Classroom_Height+4];
			int max_value=-1;
			ArrayList<Point> list_pos_max_value = new ArrayList<Point>();
			
			for(int a=0;a<Constants.Classroom_Width+4;a++)
			{
				for(int b=0;b<Constants.Classroom_Height+4;b++)
				{	
					value[a][b] = survivor_value[a][b] = other_value[a][b] = 0;					
				}
			}
			for( PlayerInfo other : othersInfo_withinSight)
			{
				if(other.GetState() == PlayerInfo.State.Survivor )
				{
					++survivor_value[other.GetPosition().x][other.GetPosition().y+2];
					++survivor_value[other.GetPosition().x+1][other.GetPosition().y+1];
					++survivor_value[other.GetPosition().x+1][other.GetPosition().y+2];
					++survivor_value[other.GetPosition().x+1][other.GetPosition().y+3];
					++survivor_value[other.GetPosition().x+2][other.GetPosition().y];
					++survivor_value[other.GetPosition().x+2][other.GetPosition().y+1];
					++survivor_value[other.GetPosition().x+2][other.GetPosition().y+2];
					++survivor_value[other.GetPosition().x+2][other.GetPosition().y+3];
					++survivor_value[other.GetPosition().x+2][other.GetPosition().y+4];
					++survivor_value[other.GetPosition().x+3][other.GetPosition().y+1];
					++survivor_value[other.GetPosition().x+3][other.GetPosition().y+2];
					++survivor_value[other.GetPosition().x+3][other.GetPosition().y+3];
					++survivor_value[other.GetPosition().x+4][other.GetPosition().y+2];
				}
				
				else
				{
					++other_value[other.GetPosition().x][other.GetPosition().y+2];
					++other_value[other.GetPosition().x+1][other.GetPosition().y+1];
					++other_value[other.GetPosition().x+1][other.GetPosition().y+2];
					++other_value[other.GetPosition().x+1][other.GetPosition().y+3];
					++other_value[other.GetPosition().x+2][other.GetPosition().y];
					++other_value[other.GetPosition().x+2][other.GetPosition().y+1];
					++other_value[other.GetPosition().x+2][other.GetPosition().y+2];
					++other_value[other.GetPosition().x+2][other.GetPosition().y+3];
					++other_value[other.GetPosition().x+2][other.GetPosition().y+4];
					++other_value[other.GetPosition().x+3][other.GetPosition().y+1];
					++other_value[other.GetPosition().x+3][other.GetPosition().y+2];
					++other_value[other.GetPosition().x+3][other.GetPosition().y+3];
					++other_value[other.GetPosition().x+4][other.GetPosition().y+2];
				}
			}
			
			for(int i=0;i<Constants.Classroom_Width+4;i++)
			{
				for(int j=0;j<Constants.Classroom_Height+4;j++)
				{	
					value[i][j] = survivor_value[i][j] * other_value[i][j];
				}
			}
			
			for(int i=2;i<Constants.Classroom_Width+2;i++)
			{
				for(int j=2;j<Constants.Classroom_Height+2;j++)
				{
					if(value[i][j] > max_value)
					{
						max_value = value[i][j];
						list_pos_max_value.clear();
					}
					if(value[i][j] == max_value)
						list_pos_max_value.add(new Point(i-2, j-2));
				}
			}
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
		}
		
		return pointToSpawn;

	}

}