package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */

public class Player_여운학_난벽이좋아 extends Player
{
	
	
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
	public Player_여운학_난벽이좋아(int ID)
	{
		
		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
		super(ID, "난벽이좋아♥");
		
		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = false;
		
		
		// TODO#4 여기까지 왔으면 이제 Developer's Guide 문서와 각 BOT 플레이어 코드를 한 번만 더 읽어 보고 돌아옵시다.
		
		
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
	}

	
		
		/**
		 * '방향 우선순위'를 기록해 두는 배열입니다.
		 * 이 field는 반드시 필요합니다.
		 */
		DirectionCode[] directions = new DirectionCode[4];
		
		Point favoritePoint = new Point(0, 0);
		
		/**
		 * '방향 우선순위'와 '선호하는 칸'을 설정합니다.
		 * 이 메서드는 Soul_Stay()에서 단 한 번 호출됩니다.
		 * 이 메서드는 반드시 필요합니다.
		 */
		void Init_Data()
		{
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Up;
		 
			favoritePoint.row = 6;
			favoritePoint.column = 6;
				
		
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
			int max_weight = 0;
			int max_row = -1;
			int max_column = -1;
			int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
			
			if( turnInfo.turnNumber == 0 )
				return new Point(7,7);
			
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
		public DirectionCode Survivor_Move()
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
				numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			
			// 1, 2, 3
			++row;
			
			if ( row >= 0 )
			{
				if ( column >= 1 )
					numberOfPlayers[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
				
				numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
				
				if ( column < Constants.Classroom_Width - 1 )
					numberOfPlayers[3] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
			}
			
			// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
			++row;
			
			if ( column >= 1 )
			{
				numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
				
				if ( column >= 2 )
					numberOfPlayers[4] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);
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
					numberOfPlayers[9] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
				
				numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
				
				if ( column < Constants.Classroom_Width - 1 )
					numberOfPlayers[11] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);		
			}
			
			// C
			++row;
			
			if ( row < Constants.Classroom_Height)
				numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			
			
			// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 생존자 수 합산		
			int[] weights = new int[4];

			for ( int iWeights = 0; iWeights < 4; iWeights++ )
			{
				switch ( directions[iWeights] )
				{
				case Down:
					// 위: 01234578
					weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[7] + numberOfPlayers[8];
					break;
				case Right:
					// 왼쪽: 145902ac
					weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9] + numberOfPlayers[0] + numberOfPlayers[2] + numberOfPlayers[10] + numberOfPlayers[12];
					break;
				case Left:
					// 오른쪽: 378B02ac
					weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11] +  numberOfPlayers[0] + numberOfPlayers[2] + numberOfPlayers[10] + numberOfPlayers[12];
					break;
				case Up:
					// 아래: 9ABC4578
					weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11] + numberOfPlayers[12] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[7] + numberOfPlayers[8];
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
