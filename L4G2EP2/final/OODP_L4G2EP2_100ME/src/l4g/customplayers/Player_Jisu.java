package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

public class Player_Jisu extends Player
{
	DirectionCode[] directions = new DirectionCode[4];
	Point favoritePoint = new Point(0,0);
	
	void Init_Data()
	{
		directions[0] = DirectionCode.Down;
		directions[1] = DirectionCode.Right;
		directions[2] = DirectionCode.Left;
		directions[3] = DirectionCode.Up;
	
		favoritePoint.row = 7;
		favoritePoint.column = 2*4;
	}
	
	DirectionCode GetMovableAdjacentDirection()
	{
		int iDirection;
		
		for(iDirection=0;iDirection<4;iDirection++)
		{
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);
			
			if(adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				break;
		}
		
		return directions[iDirection];
	}

	public Player_Jisu(int ID)
	{
		super(ID,"지수");
		
		this.trigger_acceptDirectInfection = false;
	}

	@Override
	public DirectionCode Survivor_Move()
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
		
		if(row >= 0)
			numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected );
		
		// 1, 2, 3
		++row;
		
		if(row >= 0)
		{
			if(column >= 1)
				numberOfPlayers[1] = cells[row][column-1].CountIf_Players(player -> player.state == StateCode.Infected );
			
			numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected );
			
			if(column < Constants.Classroom_Width - 1)
				numberOfPlayers[3] = cells[row][column+1].CountIf_Players(player -> player.state == StateCode.Infected );
		}
		
		// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
		++row;
		
		if(column >= 1)
		{
			numberOfPlayers[5] = cells[row][column-1].CountIf_Players(player -> player.state == StateCode.Infected );
			
			if(column >= 2)
				numberOfPlayers[4] = cells[row][column-2].CountIf_Players(player -> player.state == StateCode.Infected );
		}
		
		if(column < Constants.Classroom_Width-1)
		{
			numberOfPlayers[7] = cells[row][column+1].CountIf_Players(player -> player.state == StateCode.Infected );
			
			if(column<Constants.Classroom_Width-2)
				numberOfPlayers[8] = cells[row][column+2].CountIf_Players(player -> player.state == StateCode.Infected );
		}
		
		// 9, A, B
		++row;
		
		if(row<Constants.Classroom_Height)
		{
			if(column>=1)
				numberOfPlayers[9] = cells[row][column-1].CountIf_Players(player -> player.state == StateCode.Infected );
			
			numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected );
			
			if(column<Constants.Classroom_Width-1)
				numberOfPlayers[11] = cells[row][column+1].CountIf_Players(player -> player.state == StateCode.Infected );		
		}
		
		// C
		++row;
		
		if(row<Constants.Classroom_Height)
			numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected );
		
		
		// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 플레이어 수 합산		
		int[] weights = new int[4];

		for (int iWeights=0;iWeights<4;iWeights++)
		{
			switch(directions[iWeights])
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

	@Override
	public void Corpse_Stay()
	{
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		// 내 밑에 시체가 깔려 있으면 도망감
		if(this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
			return GetMovableAdjacentDirection();
		
		// 그렇지 않으면 정화 기도 시도
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			Init_Data();
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		if(turnInfo.turnNumber == 0)
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
		
		else
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
	}
}