package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

public class Player_PuddingHell extends Player
{
	public Player_PuddingHell(int ID)
	{
		//주로 감염체로 살아감
		super(ID, "푸딩머그꺼야");
		
		this.trigger_acceptDirectInfection = true;
	}
	
	DirectionCode[] directions = new DirectionCode[4];
	Point favoritePoint = new Point(0, 0);
	int SwitchForCorpseBomb = 0;
	int TurnToBomb = 0;
	
	void Init_Data()
	{
		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Down;
		directions[3] = DirectionCode.Right;
		
		favoritePoint.row = 6;
		favoritePoint.column = 6;
		
		SwitchForCorpseBomb = 0; // 마지막에 날아오는 시체를 먹으려면 이 스위치를 1로 키자
		
		TurnToBomb = 105; //제일적절한숫자는? 106 조금 아래.
	}
	void Change_Data()
	{
		// 감염체의 선호방향이 중앙을 향하도록 업데이트하는 함수
		if (myInfo.position.column - favoritePoint.column > 0){
			if (myInfo.position.row - favoritePoint.row > 0){
				directions[0] = DirectionCode.Up;
				directions[1] = DirectionCode.Left;
				directions[2] = DirectionCode.Down;
				directions[3] = DirectionCode.Right;
			}
			else{
				directions[0] = DirectionCode.Down;
				directions[1] = DirectionCode.Left;
				directions[2] = DirectionCode.Up;
				directions[3] = DirectionCode.Right;
			}
		}
		else{
			if (myInfo.position.row - favoritePoint.row > 0){
				directions[0] = DirectionCode.Down;
				directions[1] = DirectionCode.Right;
				directions[2] = DirectionCode.Up;
				directions[3] = DirectionCode.Left;
			}
			else{
				directions[0] = DirectionCode.Up;
				directions[1] = DirectionCode.Right;
				directions[2] = DirectionCode.Down;
				directions[3] = DirectionCode.Left;
			}
		}
	}
	
	DirectionCode GetMovableAdjacentDirection()
	{
		int iDirection;
		
		for ( iDirection = 0; iDirection < 4; iDirection++ )
		{
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);
			
			if ( adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height 
					&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width )
				break;
		}
		
		return directions[iDirection];
	}
	
	DirectionCode Infected_Move_Tosurvivor()
	{
		// 감염체가 1순위로 중요시여기는 이동규칙 - 생존자를 죽일 기회가 있다면 따라간다.
		/*
		 * 감염체 이동: 생존자가 이동할 가능성이 가장 많은 방향으로 이동합니다.
		 *              내 주변 칸에 대해
		 *                    0
		 *                   1 2
		 *                  3 X 4
		 *                   5 6
		 *                    7
		 *              ..로 번호를 매겼을 때
		 *              위:     012에 있는 사람 수
		 *              왼쪽:   135에 있는 사람 수
		 *              오른쪽: 246에 있는 사람 수
		 *              아래:   567에 있는 사람 수
		 *              ..를 합산하여 비교합니다.
		 */
		int[] numberOfInfecteds = new int[8];
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		
		// 0
		row -= 2;
		
		if ( row >= 0 )
			numberOfInfecteds[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		// 1, 2
		++row;
		
		if ( row >= 0 )
		{
			if ( column >= 1 )
				numberOfInfecteds[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( column < Constants.Classroom_Width - 1 )
				numberOfInfecteds[2] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		// 3, 4
		++row;
		
		if ( column >= 2 )
			numberOfInfecteds[3] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		if ( column < Constants.Classroom_Width - 2 )
			numberOfInfecteds[4] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		// 5, 6
		++row;
		
		if ( row < Constants.Classroom_Height)
		{
			if ( column >= 1 )
				numberOfInfecteds[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( column < Constants.Classroom_Width - 1 )
				numberOfInfecteds[6] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);		
		}
		
		// 7
		++row;
		
		if ( row < Constants.Classroom_Height)
			numberOfInfecteds[7] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
		

		//범위내 생존자가 아무도없다면 제자리 반환->2순위 조건을 적용하러 가야함 ..
		int numberOfTotalSurvivors = 0;
		for ( int i = 0; i < 8; i++ )
		{
			numberOfTotalSurvivors += numberOfInfecteds[i];
		}
		if (numberOfTotalSurvivors == 0)
			return DirectionCode.Stay;
		
		
		// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 생존자 수 합산		
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// 위: 012
				weights[iWeights] = numberOfInfecteds[0] + numberOfInfecteds[1] + numberOfInfecteds[2];
				break;
			case Left:
				// 왼쪽: 135
				weights[iWeights] = numberOfInfecteds[1] + numberOfInfecteds[3] + numberOfInfecteds[5];
				break;
			case Right:
				// 오른쪽: 246
				weights[iWeights] = numberOfInfecteds[2] + numberOfInfecteds[4] + numberOfInfecteds[6];
				break;
			default:
				// 아래: 567
				weights[iWeights] = numberOfInfecteds[5] + numberOfInfecteds[6] + numberOfInfecteds[7];
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
	DirectionCode Infected_Move_Toinfected()
	{
		// 생존자(1순위)가 아무도 없으며 내가 시체위에있는거(2순위)도 아닐때 -> 감염체를 따라가게 하는것이 바로 이 함수(3순위)
		/*
		 * 감염체 이동(1): 감염체가 가장 많은 방향으로 이동합니다.
		 *              내 주변 칸에 대해
		 *                0 1 2 3 4
		 *                5 6 7 8 9
		 *                A B/X/C D
		 *                E F G H I
		 *                J K L M N
		 *              ..로 번호를 매겼을 때
		 *              위:    0123456789에 있는 사람 수
		 *              왼쪽:   (생략)에 있는 사람 수
		 *              오른쪽:  (생략)에 있는 사람 수
		 *              아래:   (생략)에 있는 사람 수
		 *              ..를 합산하여 비교합니다.
		 */
		/*
		 * 감염체 이동(2): 감염체를 따라가되, 벽쪽으로 가기싫어함
		 * 			감염체를 따라가려고 벽과의 거리를 1칸 이하로 좁히진 않는다.
		 * 			방향별로 감염체 수를 합산할 때 적용.
		 */
		
		int[] numberOfInfecteds = new int[24];
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		
		// 1째줄
		row -= 2;
		
		if ( row >= 0 ){
			if ( column >= 2 )
				numberOfInfecteds[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column >= 1 )
				numberOfInfecteds[1] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if (true)
				numberOfInfecteds[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 2)
				numberOfInfecteds[3] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 1)
				numberOfInfecteds[4] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		// 2째줄
		++row;
		
		if ( row >= 0 )
		{
			if ( column >= 2 )
				numberOfInfecteds[5] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column >= 1 )
				numberOfInfecteds[6] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if (true)
				numberOfInfecteds[7] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 2)
				numberOfInfecteds[8] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 1)
				numberOfInfecteds[9] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		// 3째줄
		++row;
		
		if (true)
		{
			if ( column >= 2 )
				numberOfInfecteds[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column >= 1 )
				numberOfInfecteds[11] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 2)
				numberOfInfecteds[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 1)
				numberOfInfecteds[13] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		// 4째줄
		++row;
		
		if ( row < Constants.Classroom_Height)
		{
			if ( column >= 2 )
				numberOfInfecteds[14] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column >= 1 )
				numberOfInfecteds[15] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if (true)
				numberOfInfecteds[16] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 2)
				numberOfInfecteds[17] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 1)
				numberOfInfecteds[18] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);		
		}
		
		// 5째줄
		++row;
		
		if ( row < Constants.Classroom_Height)
		{
			if ( column >= 2 )
				numberOfInfecteds[19] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column >= 1 )
				numberOfInfecteds[20] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if (true)
				numberOfInfecteds[21] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 2)
				numberOfInfecteds[22] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			if ( column <= Constants.Classroom_Width - 1)
				numberOfInfecteds[23] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		// 너무 많은 감염체(10개이상)가 있다면 동료로 치지 않을께
		for ( int i = 0; i < 24; i++)
		{
			if ( numberOfInfecteds[i] >= 10)
				numberOfInfecteds[i] = 0;
		}
		
		
		// 4가지 방향(순서는 방향 우선순위에 의존)에 대한 감염체 수 합산
		// (중요)무조건 벽에서 2칸 이상 떨어져 있고 싶다 : 벽과 2칸떨어져있다면 (벽쪽으로 안가게) 벽쪽 감염체들 무게를 0으로 ..
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// 위: 0123456789
				if ( myInfo.position.row <= 2)
					weights[iWeights] = 0;
				else
					weights[iWeights] = numberOfInfecteds[0] + numberOfInfecteds[1] + numberOfInfecteds[2] + numberOfInfecteds[3] + numberOfInfecteds[4]
							 + numberOfInfecteds[5] + numberOfInfecteds[6] + numberOfInfecteds[7] + numberOfInfecteds[8] + numberOfInfecteds[9];
				break;
			case Left:
				// 왼쪽: 0156,10,11,14,15,19,20
				if ( myInfo.position.column <= 2)
					weights[iWeights] = 0;
				else
					weights[iWeights] = numberOfInfecteds[0] + numberOfInfecteds[1] + numberOfInfecteds[5] + numberOfInfecteds[6] + numberOfInfecteds[10]
							 + numberOfInfecteds[11] + numberOfInfecteds[14] + numberOfInfecteds[15] + numberOfInfecteds[19] + numberOfInfecteds[20];
				break;
			case Right:
				// 오른쪽: 3489,12,13,17,18,22,23
				if ( myInfo.position.column >= Constants.Classroom_Width - 2)
					weights[iWeights] = 0;
				else
					weights[iWeights] = numberOfInfecteds[3] + numberOfInfecteds[4] + numberOfInfecteds[8] + numberOfInfecteds[9] + numberOfInfecteds[12]
							 + numberOfInfecteds[13] + numberOfInfecteds[17] + numberOfInfecteds[18] + numberOfInfecteds[22] + numberOfInfecteds[23];
				break;
			default:
				// 아래: 14,15,16,17,18,19,20,21,22,23
				if ( myInfo.position.row >= Constants.Classroom_Height - 2)
					weights[iWeights] = 0;
				else
					weights[iWeights] = numberOfInfecteds[14] + numberOfInfecteds[15] + numberOfInfecteds[16] + numberOfInfecteds[17] + numberOfInfecteds[18]
							 + numberOfInfecteds[19] + numberOfInfecteds[20] + numberOfInfecteds[21] + numberOfInfecteds[22] + numberOfInfecteds[23];
				break;
			}
		}
		
		// 감염체 수가 가장 많은 방향 선택(해당 방향이 여럿인 경우 가장 우선 순위가 높은 것을 선택)
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
		// 처음 1~11턴동안 자리잡는데에만 사용됨
		// 그러나 가끔 11턴 이후로 생존자가 되는 경우가 있는데 정말 드문 경우이므로 아몰랑. - 왜인지도 모르겠음
		return Infected_Move();
	}

	@Override
	public void Corpse_Stay()
	{
		// 수집할 정보 없음
	}

	@Override
	public DirectionCode Infected_Move()
	{
		//극후반에 - 날아오는 시체폭탄 먹으려고 가만있어버리기
		if (SwitchForCorpseBomb == 1){
			return DirectionCode.Stay;
		}
		
		//후반에 - 시체폭탄하려고 자살시도 하는중
		if (turnInfo.turnNumber > TurnToBomb){
			if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
				return GetMovableAdjacentDirection();
			else
				return DirectionCode.Stay;
		}
		
		//초반~중반 - 추적자 무빙
		Change_Data();
		DirectionCode Survivor = Infected_Move_Tosurvivor();
		DirectionCode Infected = Infected_Move_Toinfected();
		
		if ( Survivor != DirectionCode.Stay )
			return Survivor;
		else{
			if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
				return DirectionCode.Stay;
			else{
				return Infected;
			}
		}
	}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			Init_Data();
		}

		if (turnInfo.turnNumber > TurnToBomb){
			//스위치 온
			SwitchForCorpseBomb = 1;
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		//  [(시체 수)/2]+(감염체 수)   -> 를 기준으로 시체폭탄투하준비
		
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
		
		// 전체 칸을 검색하여 [(시체 수)/2]+(감염체 수)가 가장 많은 칸을 찾음
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
		{
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
			{
				CellInfo cell = this.cells[row][column];

				int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
				int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
				
				int weight = numberOfInfecteds != 0 ? (numberOfCorpses/2) + numberOfInfecteds : 0;
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
		
		// 검색했는데 시체와 감염체가 하나도 없다면 (=맨처음에), (6,6)에서 시작하자.
		if ( max_weight == 0 )
		{
			return favoritePoint;
		}
		
		return new Point(max_row, max_column);
	}
}
