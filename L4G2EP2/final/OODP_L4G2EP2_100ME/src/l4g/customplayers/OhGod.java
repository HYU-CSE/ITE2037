package l4g.customplayers;

import java.util.ArrayList;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class OhGod extends Player
{
	/* --------------------------------------------------
	 * TODO 꼭 읽어 보세요!
	 * 
	 * 모든 Bot 플레이어들은 서로 다른 의사 결정을 수행하기 위해
	 * 자신의 ID와 게임 번호를 이용하여
	 * 임의의 '방향 우선순위'와 '선호하는 칸'을 정해 의사 결정에 활용합니다.
	 * 만약 여러분이 Bot 플레이어의 코드를 가져와 사용하려는 경우
	 * 다음 작업들을 꼭 함께 수행해야 합니다.
	 * 
	 * - 아래에 '이 field, 메서드는 반드시 필요합니다'라 적혀 있는 것들을
	 * 	 모두 복붙해 와야 합니다.
	 * 
	 * - Soul_Stay()에 있는, 초기화 관련 코드를 복붙해 와야 합니다.
	 */
	
	/**
	 * '방향 우선순위'를 기록해 두는 배열입니다.
	 * 이 field는 반드시 필요합니다.
	 */
	DirectionCode[] directions = new DirectionCode[4];
	
	/**
	 * '선호하는 칸'을 기록해 두는 field입니다.
	 * 이 field는 반드시 필요합니다.
	 */
	Point favoritePoint = new Point(0, 0);
	
	/**
	 * '방향 우선순위'와 '선호하는 칸'을 설정합니다.
	 * 이 메서드는 Soul_Stay()에서 단 한 번 호출됩니다.
	 * 이 메서드는 반드시 필요합니다.
	 */
	void Init_Data()
	{
		/*
		 * 여러분이 작성하는 플레이어는 언제나 강의실에 단 한 명 존재하므로
		 * 사실 아래의 코드를 쓸 필요 없이
		 * 
		 * directions[0] = DirectionCode.Up;
		 * directions[1] = DirectionCode.Left;
		 * directions[2] = DirectionCode.Right;
		 * directions[3] = DirectionCode.Down;
		 * 
		 * favoritePoint.row = 6;
		 * favoritePoint.column = 6;
		 * 
		 * ...와 같이 여러분이 좋아하는 우선 순위를 그냥 바로 할당해 써도 무방합니다.
		 * (Bot들은 똑같은 클래스의 인스턴스가 여럿 존재하므로 이런 짓을 해야 합니다).
		 */
		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;
		
		// 플레이어마다 다른 ID, 게임마다 다른 게임 번호를 조합하여 뭔가 이상한 수를 만들고
		long seed1 = (gameNumber * 3 + turnInfo.turnNumber * 7);
		long seed2 = (gameNumber * 7 + turnInfo.turnNumber * 3);
		if(seed1 < 0) seed1 = -seed1;
		if(seed2 < 0) seed2 = -seed2;
		favoritePoint.row = (int)(seed1 % Constants.Classroom_Width );
		favoritePoint.column = (int)(seed2 % Constants.Classroom_Height );
	}
	
	/**
	 * 방향 우선순위를 고려하여, 현재 이동 가능한 방향을 하나 반환합니다.
	 * 이 메서드는 반드시 필요합니다.
	 */
	DirectionCode GetMovableAdjacentDirection()
	{
		int iDirection;
		
		for ( iDirection = 0; iDirection < 4; iDirection++ )
		{
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);
			
			if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				break;
		}
		
		return directions[iDirection];
	}

	
	
	int st = 0;
	
	public OhGod(int ID)
	{
		super(ID, "으악#" + ID);
		
		this.trigger_acceptDirectInfection = false;
	}
	DirectionCode state;
	@Override	
	public DirectionCode Survivor_Move()
	{
		if(turnInfo.turnNumber <= 77){
			int[] numberOfPlayers = new int[13];
			
			int row = myInfo.position.row;
			int column = myInfo.position.column;
			
			// 위에 보이는 13가지 경우에 대한 플레이어 수 기록
			
			// 0
			row -= 2;
			
			if ( row >= 0 )
				numberOfPlayers[0] = cells[row][column].Count_Players();
			
			// 1, 2, 3
			++row;
			
			if ( row >= 0 )
			{
				if ( column >= 1 )
					numberOfPlayers[1] = cells[row][column - 1].Count_Players();
				
				numberOfPlayers[2] = cells[row][column].Count_Players();
				
				if ( column < Constants.Classroom_Width - 1 )
					numberOfPlayers[3] = cells[row][column + 1].Count_Players();
			}
			
			// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
			++row;
			
			if ( column >= 1 )
			{
				numberOfPlayers[5] = cells[row][column - 1].Count_Players();
				
				if ( column >= 2 )
					numberOfPlayers[4] = cells[row][column - 2].Count_Players();
			}
			
			if ( column < Constants.Classroom_Width - 1 )
			{
				numberOfPlayers[7] = cells[row][column + 1].Count_Players();
				
				if ( column < Constants.Classroom_Width - 2 )
					numberOfPlayers[8] = cells[row][column + 2].Count_Players();
			}
			
			// 9, A, B
			++row;
			
			if ( row < Constants.Classroom_Height)
			{
				if ( column >= 1 )
					numberOfPlayers[9] = cells[row][column - 1].Count_Players();
				
				numberOfPlayers[10] = cells[row][column].Count_Players();
				
				if ( column < Constants.Classroom_Width - 1 )
					numberOfPlayers[11] = cells[row][column + 1].Count_Players();		
			}
			
			// C
			++row;
			
			if ( row < Constants.Classroom_Height)
				numberOfPlayers[12] = cells[row][column].Count_Players();
			
			
			// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 플레이어 수 합산		
			int[] weights = new int[4];

			for ( int iWeights = 0; iWeights < 4; iWeights++ )
			{
				switch ( directions[iWeights] )
				{
				case Up:
					// 위: 0123
					weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3];
					break;
				case Left:
					// 왼쪽: 1459
					weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9];
					break;
				case Right:
					// 오른쪽: 378B
					weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11];
					break;
				default:
					// 아래: 9ABC
					weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11] + numberOfPlayers[12];
					break;
				}
			}
			
			// 플레이어 수가 가장 적은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)
			int min_weight = Integer.MAX_VALUE;
			int min_idx_weights = 0;
			
			for ( int iWeights = 0; iWeights < 4; iWeights++ )
			{
				if ( weights[iWeights] < min_weight )
				{
					Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);
					
					if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height &&
							adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
					{
						min_weight = weights[iWeights];
						min_idx_weights = iWeights;
					}
				}
			}
			
			return directions[min_idx_weights];

			
		}
		else{
			int[] numberOfPlayers = new int[13];
			
			int row = myInfo.position.row;
			int column = myInfo.position.column;
			
			// 위에 보이는 13가지 경우에 대한 생존자 수 기록
			
			// 0
			row -= 2;
			
			if ( row >= 0 )
				numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			// 1, 2, 3
			++row;
			
			if ( row >= 0 )
			{
				if ( column >= 1 )
					numberOfPlayers[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				
				numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
				
				if ( column < Constants.Classroom_Width - 1 )
					numberOfPlayers[3] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			
			// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
			++row;
			
			if ( column >= 1 )
			{
				numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				
				if ( column >= 2 )
					numberOfPlayers[4] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			
			if ( column < Constants.Classroom_Width - 1 )
			{
				numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				
				if ( column < Constants.Classroom_Width - 2 )
					numberOfPlayers[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
			
			// 9, A, B
			++row;
			
			if ( row < Constants.Classroom_Height)
			{
				if ( column >= 1 )
					numberOfPlayers[9] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				
				numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
				
				if ( column < Constants.Classroom_Width - 1 )
					numberOfPlayers[11] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);		
			}
			
			// C
			++row;
			
			if ( row < Constants.Classroom_Height)
				numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			
			// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 생존자 수 합산		
			int[] weights = new int[4];

			for ( int iWeights = 0; iWeights < 4; iWeights++ )
			{
				switch ( directions[iWeights] )
				{
				case Up:
					// 위: 0123
					weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3];
					break;
				case Left:
					// 왼쪽: 1459
					weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9];
					break;
				case Right:
					// 오른쪽: 378B
					weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11];
					break;
				default:
					// 아래: 9ABC
					weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11] + numberOfPlayers[12];
					break;
				}
			}
			
			// 생존자 수가 가장 많은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)
			int max_weight = -1;
			int max_idx_weights = 0;
			
			for ( int iWeights = 0; iWeights < 4; iWeights++ )
			{
				if ( weights[iWeights] > max_weight )
				{
					Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);
					
					if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height &&
							adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
					{
						max_weight = weights[iWeights];
						max_idx_weights = iWeights;
					}
				}
			}
			
			return directions[max_idx_weights];

			
		}
	}
		
	
	@Override
	public void Corpse_Stay()
	{
	}
	
	@Override
	public DirectionCode Infected_Move()
	{
		
			// 내 밑에 시체가 깔려 있으면 도망감
			if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
				return GetMovableAdjacentDirection();
			
			// 그렇지 않으면 정화 기도 시도
			return DirectionCode.Stay;
	}
	
	@Override
	public void Soul_Stay()
	{
		if ( this.turnInfo.turnNumber == 0 )
		{
			// 이 부분은 Bot 플레이어 코드를 복붙해 사용하기 위해 반드시 필요합니다.
			Init_Data();
		}
	}
	
	@Override
	public Point Soul_Spawn()
	{
		st++;
		if(st % 2 == 1){
			int max_weight = 0;
			int max_row = -1;
			int max_column = -1;
			int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
			
			// 전체 칸을 검색하여 시체 및 감염체 수가 가장 많은 칸을 찾음
			for ( int row = 0; row < Constants.Classroom_Height; row++ )
			{
				for ( int column = 0; column < Constants.Classroom_Width; column++ )
				{
					CellInfo cell = this.cells[row][column];

					int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
					int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
					
					int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
					int distance = favoritePoint.GetDistance(row, column);

					// 가장 많은 칸이 발견되면 갱신
					if ( weight > max_weight )
					{
						max_weight = weight;
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// 가장 많은 칸이 여럿이면 그 중 '선호하는 칸'과 가장 가까운 칸을 선택
					else if ( weight == max_weight )
					{
						// 거리가 더 가까우면 갱신
						if ( distance < min_distance )
						{
							max_row = row;
							max_column = column;
							min_distance = distance;
						}
						// 거리마저 같으면 더 좋아하는 방향을 선택
						else if ( distance == min_distance )
						{
							for ( int iDirection = 0; iDirection < 4; iDirection++ )
							{
								Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
								
								if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row, max_column) )
								{
									max_row = row;
									max_column = column;
									break;
								}
							} 
							
							//여기까지 왔다면 이제 그만 놓아 주자
						}
					}
				}
			}
			
			// 검색했는데 시체와 감염체가 하나도 없다면 배치 유예
			if ( max_weight == 0 )
			{
				int variableToMakeError = 0;
				
				variableToMakeError = variableToMakeError / variableToMakeError;
			}
			
			return new Point(max_row, max_column);

		}
		else{
			int min_row = -1;
			int min_column = -1;
			int min_count = 1;
			int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
			
			for ( int row = 0; row < Constants.Classroom_Height; row++ )
			{
				for ( int column = 0; column < Constants.Classroom_Width; column++ )
				{
					int count = cells[row][column].Count_Players();
					int distance = favoritePoint.GetDistance(row, column);

					// 플레이어 수가 더 적다면 항상 갱신
					if ( count < min_count )
					{
						min_row = row;
						min_column = column;
						min_count = count;
						min_distance = distance;
					}
					// 플레이어 수가 같으면 선호하는 칸과 더 가까운 칸을 선택
					else if ( count == min_count )
					{
						// 거리가 더 가까우면 갱신
						if ( distance < min_distance )
						{
							min_row = row;
							min_column = column;
							min_distance = distance;
						}
						// 거리마저 같으면 더 좋아하는 방향을 선택
						else if ( distance == min_distance )
						{
							for ( int iDirection = 0; iDirection < 4; iDirection++ )
							{
								Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
								
								if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(min_row, min_column) )
								{
									min_row = row;
									min_column = column;
									break;
								}
							} 
							
							//여기까지 왔다면 이제 그만 놓아 주자
						}
					}
				}
			}
			
			return new Point(min_row, min_column);

		}
	}
}
