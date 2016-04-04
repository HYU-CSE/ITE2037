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
public class 민지 extends Player
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
	 * 시체 폭탄은 이 값을 활용하여 생존자 / 감염체 이동을 수행합니다.
	 */
	int index_directionToMove;
		
	
	public 민지()
	{
		name = "min";	// TODO 자신이 만들 플레이어의 이름으로 name 필드를 초기화하세요.
	}

	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result;
		
		//방향별로 각 플레이어 수 및 포착 기대값을 기록하기 위한 배열 사용
		//0: Up, 1: Left, 2: Right, 3: Down
		int[] weights = new int[4];
		int max_weight = -1;
		
		//현재 포착된 모든 플레이어에 대해 검사 수행
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			//해당 플레이어와 나 사이의 거리 비교
			Vector v = GetDistanceVectorBetweenPlayers(other);

			//해당 플레이어의 현재 상태에 따라 플레이어 수 기록
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
		
		if ( max_weight == 0 )
		{
			//이동할 수 있는 한도 내에서 '방향 섞기' 기능으로 정해 둔 순서에 따라 순차적으로 이동 수행 - 이동 가능한 방향은 최소 둘 이상 존재
			do
			{
				result = shuffledDirections[index_directionToMove];
				++index_directionToMove;
				index_directionToMove %= 4;
			}
			while ( IsValidMove(result) == false );
			
			return result;
		}
		
		//'방향 섞기' 설정에 따라 기대값이 최대값과 같은 방향들 중 하나 선택 - 최소 하나 존재
		for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
			if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
				return shuffledDirections[iShuffledDirection];

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
		/*
		 * 추적자의 감염체 이동: 인접한 칸을 제외한 시야 범위 내에서 생존자가 가장 많은 방향으로 이동
		 */
		
		//방향별로 생존자 수를 기록하기 위한 배열 사용
		//0: Up, 1: Left, 2: Right, 3: Down, 4: Stay
		int[] weights = new int[5];
		int max_weight = -1;
		int count_max_weights = 0;
		
		//시야 내의 모든 플레이어에 대해 인접한 칸을 제외하고 생존자 수 계산
		for ( PlayerInfo other : othersInfo_withinSight )
		{
			//해당 플레이어와 나 사이의 거리 비교
			Vector v = GetDistanceVectorBetweenPlayers(other);

			//각 생존자에 대해 방향별 값 갱신  
			if ( other.GetState() == PlayerInfo.State.Survivor )
			{
				if ( v.GetDistance() > 1 )
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
				else
					++weights[4];
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

		//측정된 생존자 수의 최대값이 몇인지, 최대값을 가진 방향이 몇 가지 있는지 계산
		for ( int weight : weights )
		{
			if ( weight > max_weight )
			{
				max_weight = weight;
				count_max_weights = 0;
			}
			
			if ( weight == max_weight )
				++count_max_weights;
		}
		
		if ( weights[4] == max_weight && count_max_weights == 1 )
		{
			return DirectionCode.Stay;
		}
		
		
		//'방향 섞기' 설정에 따라 생존자 수가 최대값과 같은 방향들 중 하나 선택 - 최소 하나 존재
		for ( int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection )
			if ( weights[ shuffledDirection_values[iShuffledDirection] ] == max_weight )
				return shuffledDirections[iShuffledDirection];

		//여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay()
	{
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			shuffledDirections = new DirectionCode[4];
			shuffledDirections[0] = DirectionCode.Right;
			shuffledDirections[1] = DirectionCode.Down;
			shuffledDirections[2] = DirectionCode.Left;
			shuffledDirections[3] = DirectionCode.Up;		
			
			shuffledDirection_values = new int[4];
			shuffledDirection_values[0] = 2;
			shuffledDirection_values[1] = 3;
			shuffledDirection_values[2] = 1;
			shuffledDirection_values[3] = 0;
			
			
			basePoint = new Point(8, 4);
		}

		// TODO 영혼 상태일 때 현재 알고 있는 것을 바탕으로 생각을 진행하려면 여기에 담으세요.
	}

	@Override
	public Point Soul_Spawn()
	{
		index_directionToMove = 0;
		
		if ( gameInfo.GetCurrentTurnNumber() == 0 )
		{
			return new Point(Constants.Classroom_Width / 2, Constants.Classroom_Height / 2);
		}
		
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
