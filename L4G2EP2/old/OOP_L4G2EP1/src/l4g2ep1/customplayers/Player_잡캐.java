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
public class Player_잡캐 extends Player
{
	public Player_잡캐()
	{
		name = "잡캐";	// TODO 자신이 만들 플레이어의 이름으로 name 필드를 초기화하세요.
		acceptDirectInfection = false;				// TODO '직접 감염'을 받으려는 경우 이 필드를 true로 두고 아닌 경우 false로 두세요.

		//정찰병은 생존자 이동에서 다른 플레이어가 가장 적은 방향으로 이동하기 위해 포착 정보를 수신함
		this.receiveOthersInfo_detected = true; 
	}
	/**
	 * '방향 섞기' 기능에 사용되는 방향 목록 및 해당 방향을 나타내는 숫자 목록입니다.
	 * 모든 샘플 플레이어들은 여러 방향들 중 하나를 선택할 때 여기 있는 목록에서 더 앞에 있는 것을 우선시합니다.
	 * 이 목록은 게임 번호와 ID에 의해 첫 턴의 Soul_Stay()에서 결정됩니다.
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
	 * 시체 폭탄은 이 값을 활용하여 생존자 / 감염체 이동을 수행합니다.
	 */
	int index_directionToMove;
	
	/**
	 * '기점 지정' 기능을 사용하기 위해 기점 좌표를 초기화합니다.
	 * 이 메서드는 첫 턴의 Soul_Stay()에서 호출되어야 합니다.
	 */
	void SetBasePoint()
	{
		
		
		//seed를 강의실의 총 칸 수로 나눈 나머지를 토대로 기점 좌표 설정
		int base_y = Constants.Classroom_Height / 2;
		int base_x = Constants.Classroom_Width / 2;
		
		basePoint = new Point(base_x, base_y);
	}
	
	@Override
	
	
	
	public DirectionCode Survivor_Move()
	{
		int fTurn=gameInfo.GetDirectInfectionCountdown();
		// TODO 생존자 상태일 때 이동하기 위한 생각을 여기에 담으세요.
		
		//===============첫 10턴동안만. 하게 해야함.==========================
		if(	fTurn!=-1 ){
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
			if ( weights[ shuffledDirection_values[iShuffledDirection] ] == min_weight )
				return shuffledDirections[iShuffledDirection];
		}
		//========외톨이 끝!!=================
		//========정찰병 시작!!===============
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
			
				//감염체와 두칸거리일때는 조심
				if(v.y_offset == -2 && v.x_offset == 0){
					others[0] = -1;	
				}
				if(v.y_offset == -1 && v.x_offset == -1){
					others[0] = -1;
					others[1] = -1;
				}
				if(v.x_offset == -2 && v.y_offset == 0){
					others[1] = -1;	
				}
				if(v.y_offset == 1 && v.x_offset == 1){
					others[2] = -1;
					others[3] = -1;
				}
				if(v.y_offset == 2 && v.x_offset == 0){
					others[2] = -1;	
				}
				if(v.x_offset == 2 && v.y_offset == 0){
					others[3] = -1;	
				}
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
		//현재 체력을 알기 위해
		int myEnergy=myInfo.GetHitPoint();
		
		// TODO 감염체 상태일 때 이동 또는 대기하기 위한 생각을 여기에 담으세요.
		//=============Predator 가져옴================
		/*
		 * 포식자의 감염체 이동: 현 위치에 시체가 있는 경우 제자리에 머무름. 그렇지 않은 경우 생존자 및 시체가 가장 많은 방향으로 이동.
		 */
		

		if(myEnergy<6){
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
		
		//시체가 있는 경우,나의 체력이 적은경우 제자리에 머무름
		if ( isCorpseHere == true && myEnergy<8)
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
		}
		else{
		//============포식자 끝=================
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
			//인접한 칸에 있을경우 제자리에서 기다려줌ㅋ
			else if ( other.GetState() == PlayerInfo.State.Survivor && v.GetDistance() == 1 )
			{
				return DirectionCode.Stay;
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
		if(max_weight != 0){
			//'방향 섞기' 설정에 따라 생존자 수가 최대값과 같은 방향들 중 하나 선택 - 최소 하나 존재
			for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
				if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
					return shuffledDirections[iShuffledDirection];
			}

		
		//생존자가 시야 내에 없을 경우 맵의 가운데로 이동.
		else{
			if(myInfo.GetPosition().x < myInfo.GetPosition().y){
				if(myInfo.GetPosition().x< Constants.Classroom_Width / 2){
					if(IsValidMove(DirectionCode.Right) == true)
						return DirectionCode.Right;
					else if(IsValidMove(DirectionCode.Left) == true)
						return DirectionCode.Left;
				}
			}
			else{
				if(myInfo.GetPosition().y< Constants.Classroom_Height / 2){
					if(IsValidMove(DirectionCode.Up) == true)
						return DirectionCode.Up;
					else if(IsValidMove(DirectionCode.Down) == true)
						return DirectionCode.Down;
				}
			}
		}
		//여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
		
		}
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay()
	{
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			ShuffleDirections();
			SetBasePoint();
			// TODO 직접 만든 데이터 필드에 대한 초기화 코드를 여기에 적으세요. 이 메서드는 게임이 시작되면 가장 먼저 호출됩니다.
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
		Point pointToSpawn2= basePoint;

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
		
		//시체폭탄의 재배치는 감염체 6이상이 한곳에 있을 경우에만 이루어짐.
		if(max_weight > 5){
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

		/*
		 * 생존자의 영혼배치: 가장 생존자가 많은 칸을 골라 배치
		 */
		//각 칸에 있는 생존자 수를 기록하기 위한 배열 사용
		int[][] weights2 = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int max_weight2 = -1;
		ArrayList<Point> list_pos_max_weight2 = new ArrayList<Point>();
		
		//모든 생존자에 대해 검사 수행 - 9x9칸에 플레이어는 40명에 불과하므로 생존자가 가장 많은 칸 목록을 동시에 구성
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			//모든 생존자에 대해 배열 갱신 후 최대값 계산, 최대값을 가진 칸에 대한 좌표 목록 갱신
			if ( other.GetState() == PlayerInfo.State.Survivor )
			{
				Point pos_other2 = other.GetPosition();
				
				++weights2[pos_other2.y][pos_other2.x];

				//최대값이 바뀌었다면 '감염체가 가장 많은 칸' 목록 초기화
				if ( weights2[pos_other2.y][pos_other2.x] > max_weight2 )
				{
					++max_weight2; //weight는 항상 1씩 증가하므로 당연히 최대값도 1씩 증가함
					list_pos_max_weight2.clear();
				}
				
				//현재 칸의 값이 최대값과 같다면 현재 칸을 '감염체가 가장 많은 칸' 목록에 추가 (위에서 최대값이 바뀌었다면 항상 추가됨)
				if ( weights[pos_other2.y][pos_other2.x] == max_weight2 )
					list_pos_max_weight2.add(pos_other2);
			}
		}
		
		//생존의 재배치는 감염체 6이상이 한곳에 없을 경우에만 이루어짐.
		
			//검사가 끝나면 생존자가 가장 많은 칸 목록에서 '기점 설정' 기능으로 정해 둔 기점과 가장 가까운 첫번째 칸 선택
			int min_distance2 = Integer.MAX_VALUE;
			Point myPosition = myInfo.GetPosition();
			
			for ( Point pos_max_weight2 : list_pos_max_weight2 )
			{
				int distance2 = GetDistance(myPosition, pos_max_weight2);
				
				if ( distance2 < min_distance2 )
				{
					min_distance2 = distance2;
					pointToSpawn2 = pos_max_weight2;
				}
			}
			return pointToSpawn2;
	}
}
