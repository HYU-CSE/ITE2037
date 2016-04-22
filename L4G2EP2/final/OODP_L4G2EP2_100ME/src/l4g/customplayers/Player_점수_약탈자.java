package l4g.customplayers;

import java.util.ArrayList;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * 매 부활마다 다음에 올릴 점수를 결정하고 그에 맞는 부활을 수행한 다음 바로 정화 기도를 올리는 team Sigma 소속 플레이어입니다.
 * 
 * @author Racin
 *
 */
public class Player_점수_약탈자 extends Player
{
	DirectionCode[] directions = { DirectionCode.Up, DirectionCode.Left, DirectionCode.Right, DirectionCode.Down };
	
	Point favoritePoint = new Point(0, 0);
	
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
	
	public Player_점수_약탈자(int ID)
	{
		super(ID, "점수 약탈자");
	}
	
	static final int State_None = 0;
	static final int State_Survivor_Max = 1;
	static final int State_Survivor_Tot = 2;
	static final int State_Corpse = 3;	
	
	int state = State_None;
	
	// 이 플레이어는 매 시즌마다 서로 다른 동작을 수행함
	int seasonNumber;
	
	// 목표하는 생존자 최대 점수
	int expectedScore_Survivor_Max;
	
	void UpdateState()
	{
		// 생존자 최대 점수 목표를 달성했거나 갱신할 가망이 없는 경우 제외
		if ( myScore.survivor_max >= expectedScore_Survivor_Max || 
				expectedScore_Survivor_Max < Constants.Total_Players - turnInfo.turnNumber )
		{
			// 시즌 번호와 턴 번호를 조합하여 다음 생애 목표를 정함
			switch ( (seasonNumber + turnInfo.turnNumber) % 2 )
			{
			case 0:
				state = State_Survivor_Tot;
				break;
			default:
				state = State_Corpse;
				break;
			}			
		}
		else
		{
			// 시즌 번호와 턴 번호를 조합하여 다음 생애 목표를 정함
			switch ( (seasonNumber + turnInfo.turnNumber) % 3 )
			{
			case 0:
				state = State_Survivor_Max;
				break;
			case 1:
				state = State_Survivor_Tot;
				break;
			default:
				state = State_Corpse;
				break;
			}
		}
	}
	
	DirectionCode Survivor_Move_For_Survivor_Max()
	{
		/*
		 * 생존자 이동: 플레이어 수가 가장 적은 방향으로 이동합니다.
		 *              생존자의 시야 범위가
		 *                    0
		 *                   123
		 *                  45678
		 *                   9AB
		 *                    C
		 *              ..일 때
		 *              위:     0123에 있는 플레이어 수
		 *              왼쪽:   1459에 있는 플레이어 수
		 *              오른쪽: 378B에 있는 플레이어 수
		 *              아래:   9ABC에 있는 플레이어 수
		 *              ..를 합산하여 비교합니다.
		 */
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
	
	DirectionCode Survivor_Move_For_Survivor_Total()
	{
		/*
		 * 생존자 이동: 생존자 수가 가장 많은 방향으로 이동합니다.
		 *              생존자의 시야 범위가
		 *                    0
		 *                   123
		 *                  45678
		 *                   9AB
		 *                    C
		 *              ..일 때
		 *              위:     0123에 있는 생존자 수
		 *              왼쪽:   1459에 있는 생존자 수
		 *              오른쪽: 378B에 있는 생존자 수
		 *              아래:   9ABC에 있는 생존자 수
		 *              ..를 합산하여 비교합니다.
		 */
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
	
	@Override
	public DirectionCode Survivor_Move()
	{
		switch ( state )
		{
		case State_Survivor_Max:
			if ( myScore.survivor_max >= expectedScore_Survivor_Max )
			{
				state = State_Survivor_Tot;
				return Survivor_Move_For_Survivor_Total();
			}
			
			return Survivor_Move_For_Survivor_Max();
		case State_Survivor_Tot:
			return Survivor_Move_For_Survivor_Total();
		default:
			return null;
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
		if ( turnInfo.turnNumber == 0 )
		{
			seasonNumber = (int)(gameNumber / 10000L);
			
			favoritePoint.row = seasonNumber % 10 + 1;
			favoritePoint.column = seasonNumber % 100 / 10 + 1;
			
			trigger_acceptDirectInfection = (seasonNumber & 1) == 0;
			
			expectedScore_Survivor_Max = seasonNumber % 5 * 5 + 20;
		}
	}

	Point Soul_Spawn_For_Survivor_Max()
	{
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

	int[][][] dataForSSFST = new int[Constants.Classroom_Height][Constants.Classroom_Width][2];
	
	Point Soul_Spawn_For_Survivor_Total()
	{
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

		ArrayList<PlayerInfo> playersOnTheCell;

		// 전체 칸을 검색하여 생존자 수와 시체 + 감염체 수 집계
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
		{
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
			{
				int numberOfSurvivors = 0;
				int numberOfOthers = 0;
				boolean isInfectedHere = false;
				
				playersOnTheCell = cells[row][column].Select_Players(player -> true);
				
				for ( PlayerInfo info : playersOnTheCell )
				{
					if ( info.state == StateCode.Survivor )
						++numberOfSurvivors;
					else
					{
						++numberOfOthers;
						
						if ( info.state == StateCode.Infected || info.transition_cooldown == 0 )
							isInfectedHere = true;
					}
					
					if ( isInfectedHere == true )
					{
						numberOfOthers += numberOfSurvivors;
						numberOfSurvivors = 0;
					}
				}
				
				dataForSSFST[row][column][0] = numberOfSurvivors;
				dataForSSFST[row][column][1] = numberOfOthers;
			}
		}
		
		// 전체 칸을 검색하여 예상 발견 점수가 가장 높은 칸을 찾음
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
		{
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
			{
				int numberOfSurvivors = 0;
				int numberOfOthers = 0;
				int weight = 0;
				int distance = favoritePoint.GetDistance(row, column);

				// 일어날 시체 또는 감염체가 없는 칸만 발견 점수 취득 가능
				if ( dataForSSFST[row][column][1] == 0 )
				{
					int row_target = row - 2;
					
					if ( row_target >= 0 )
					{
						numberOfSurvivors += dataForSSFST[row_target][column][0];
						numberOfOthers += dataForSSFST[row_target][column][1];
					}
					
					++row_target;
					if ( row_target >= 0 )
					{
						if ( column >= 1 )
						{
							numberOfSurvivors += dataForSSFST[row_target][column - 1][0];
							numberOfOthers += dataForSSFST[row_target][column - 1][1];
						}

						numberOfSurvivors += dataForSSFST[row_target][column][0];
						numberOfOthers += dataForSSFST[row_target][column][1];
						
						if ( column < Constants.Classroom_Width - 1 )
						{
							numberOfSurvivors += dataForSSFST[row_target][column + 1][0];
							numberOfOthers += dataForSSFST[row_target][column + 1][1];
						}
					}
					
					++row_target;
					
					if ( column >= 1 )
					{					
						numberOfSurvivors += dataForSSFST[row_target][column - 1][0];
						numberOfOthers += dataForSSFST[row_target][column - 1][1];
						
						if ( column >= 2 )
						{
							numberOfSurvivors += dataForSSFST[row_target][column - 2][0];
							numberOfOthers += dataForSSFST[row_target][column - 2][1];
						}
					}
					
					numberOfSurvivors += dataForSSFST[row_target][column][0];
					numberOfOthers += dataForSSFST[row_target][column][1];
					
					if ( column < Constants.Classroom_Width - 1 )
					{
						numberOfSurvivors += dataForSSFST[row_target][column + 1][0];
						numberOfOthers += dataForSSFST[row_target][column + 1][1];
						
						if ( column < Constants.Classroom_Width - 2 )
						{
							numberOfSurvivors += dataForSSFST[row_target][column + 2][0];
							numberOfOthers += dataForSSFST[row_target][column + 2][1];
						}
					}
					
					++row_target;
					if ( row_target < Constants.Classroom_Height )
					{
						if ( column >= 1 )
						{
							numberOfSurvivors += dataForSSFST[row_target][column - 1][0];
							numberOfOthers += dataForSSFST[row_target][column - 1][1];
						}

						numberOfSurvivors += dataForSSFST[row_target][column][0];
						numberOfOthers += dataForSSFST[row_target][column][1];
						
						if ( column < Constants.Classroom_Width - 1 )
						{
							numberOfSurvivors += dataForSSFST[row_target][column + 1][0];
							numberOfOthers += dataForSSFST[row_target][column + 1][1];
						}
					}
					
					++row_target;
					if ( row_target < Constants.Classroom_Height )
					{
						numberOfSurvivors += dataForSSFST[row_target][column][0];
						numberOfOthers += dataForSSFST[row_target][column][1];
					}					
					
					weight = numberOfSurvivors * numberOfOthers;
				}
				
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
		
		if ( max_weight == 0 )
		{
			int variableToMakeError = 0;
			
			variableToMakeError = variableToMakeError / variableToMakeError;
		}
		
		return new Point(max_row, max_column);
	}

	Point Soul_Spawn_For_Corpse()
	{
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
	
	@Override
	public Point Soul_Spawn()
	{
		UpdateState();
		
		switch ( state )
		{
		case State_Survivor_Max:
			return Soul_Spawn_For_Survivor_Max();
		case State_Survivor_Tot:
			return Soul_Spawn_For_Survivor_Total();
		default:
			return Soul_Spawn_For_Corpse();
		}
	}
}
