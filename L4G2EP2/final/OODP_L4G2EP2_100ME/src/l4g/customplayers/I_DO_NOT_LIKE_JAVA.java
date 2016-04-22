package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class I_DO_NOT_LIKE_JAVA extends Player
{
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
	public I_DO_NOT_LIKE_JAVA(int ID)
	{
		
		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
		super(ID, "꼴지만 하지말자");
		
		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = false;
		
		
		// TODO#4 여기까지 왔으면 이제 Developer's Guide 문서와 각 BOT 플레이어 코드를 한 번만 더 읽어 보고 돌아옵시다.
		
		
	}
	
	/*
	 * TODO#5	이제 여러분이 그려 둔 노트를 보며 아래에 있는 다섯 가지 의사 결정 메서드를 완성하세요.
	 * 			당연히 한 방에 될 리 없으니, 중간중간 코드를 백업해 두는 것이 좋으며,
	 * 			코드 작성이 어려울 땐 아무 부담 없이 조교를 찾아 오세요.
	 * 
	 * 			L4G는 여러분의 '생각'을 추구하는 축제지 구글 굴리는 축제가 아닙니다!
	 * 
	 * 			여러분이 이번 축제에서 투자한 시간만큼, 이후 다른 과제 / 다른 업무에서 뻘짓을 벌이는 시간이 줄어들게 될 것입니다.
	 * 			그러니 자신이 뭔가 멋진 생각을 떠올렸다면, 이를 내 플레이어에 적용하기 위해 아낌 없는 노력을 투자해 보세요!
	 * 
	 * 			제출기한이 되어 황급히 파일을 업로드하고 Eclipse로 돌아와 여러분이 작성한 코드를 돌아 보면,
	 * 			'코드에 노력이란게 묻어 날 수도 있구나'라는 생각이 절로 들게 될 것입니다.
	 */
	DirectionCode[] directions = new DirectionCode[4];

	Point favoritePoint = new Point(0, 0);

	void Init_Data(){
		
		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;
		favoritePoint.row = 6;
		favoritePoint.column = 6;
	}

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
	public DirectionCode Survivor_Move()
	{
		
		int[] numberOfSurvivors = new int[13];
		int[] numberOfInfecteds = new int[13];
		int[] numberOfPlayers = new int[13];

		int row = myInfo.position.row;
		int column = myInfo.position.column;
		
		row -= 2;
		
		if ( row >= 0 ){
			numberOfSurvivors[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfecteds[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			numberOfPlayers[0] = cells[row][column].Count_Players();
		}
		// 1, 2, 3
		++row;
		
		if ( row >= 0 )
		{
			if ( column >= 1 ){
				numberOfSurvivors[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfSurvivors[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			
				numberOfInfecteds[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
				numberOfInfecteds[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

				numberOfPlayers[1] = cells[row][column].Count_Players();
				numberOfPlayers[2] = cells[row][column].Count_Players();
			}
			if ( column < Constants.Classroom_Width - 1 ){
				numberOfSurvivors[3] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfecteds[3] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
				numberOfPlayers[3] = cells[row][column].Count_Players();
			}
		}
		
		// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
		++row;
		
		if ( column >= 1 )
		{
			numberOfSurvivors[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfecteds[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
			numberOfPlayers[5] = cells[row][column].Count_Players();
		}

			if ( column >= 2 ){
				numberOfSurvivors[4] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfecteds[4] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);
				numberOfPlayers[4] = cells[row][column].Count_Players();

		}
		
		if ( column < Constants.Classroom_Width - 1 )
		{
			numberOfSurvivors[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfecteds[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
			numberOfPlayers[7] = cells[row][column].Count_Players();
		}

		if ( column < Constants.Classroom_Width - 2 ){
			numberOfSurvivors[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfecteds[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Infected);
			numberOfPlayers[8] = cells[row][column].Count_Players();
		}
		
		
		// 9, A, B
		++row;
		
		if ( row < Constants.Classroom_Height)
		{
			if ( column >= 1 ){
				numberOfSurvivors[9] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
				numberOfSurvivors[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			
				numberOfInfecteds[9] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
				numberOfInfecteds[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
				
				numberOfPlayers[9] = cells[row][column].Count_Players();
				numberOfPlayers[10] = cells[row][column].Count_Players();
			}
			if ( column < Constants.Classroom_Width - 1 ){
				numberOfSurvivors[11] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);		
				numberOfInfecteds[11] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);		
				numberOfPlayers[11] = cells[row][column].Count_Players();

			}
		}
		
		// C
		++row;
		
		if ( row < Constants.Classroom_Height){
			numberOfSurvivors[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfecteds[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			numberOfPlayers[12] = cells[row][column].Count_Players();
		}
		
		
		
		//생존점수를 높여보자
		if (myScore.survivor_max < 30){
			
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
			
			
		
		
		
		//정찰점수or치유점수를 높여보자
		else{
			
			int ifScoutInfectNum=0;
			int ifScoutSurvivorNum=0;
			int ifScoutNonInfectNum = 0;
			
			ifScoutInfectNum= numberOfInfecteds[0]+numberOfInfecteds[4]+numberOfInfecteds[8]+numberOfInfecteds[12];
			ifScoutSurvivorNum =numberOfSurvivors[1]+numberOfSurvivors[6]+numberOfSurvivors[2]+numberOfSurvivors[3]+numberOfSurvivors[5]+numberOfSurvivors[7]+numberOfSurvivors[9]+numberOfSurvivors[10]+numberOfSurvivors[11];
			ifScoutNonInfectNum = numberOfInfecteds[1]+numberOfInfecteds[2]+numberOfInfecteds[3]+numberOfInfecteds[5]+numberOfInfecteds[6]+numberOfInfecteds[7]+numberOfInfecteds[9]+numberOfInfecteds[10]+numberOfInfecteds[11];
			
			
			//정찰
			if ((ifScoutSurvivorNum !=0 && ifScoutInfectNum!=0 && ifScoutNonInfectNum==0) || (favoritePoint.row == row && favoritePoint.column == column)){
				int[] weights = new int[4];

				for ( int iWeights = 0; iWeights < 4; iWeights++ )
				{
					switch ( directions[iWeights] )
					{
					case Up:
						// 위: 0123
						weights[iWeights] = numberOfSurvivors[0] + numberOfSurvivors[1] + numberOfSurvivors[2] + numberOfSurvivors[3];
						break;
					case Left:
						// 왼쪽: 1459
						weights[iWeights] = numberOfSurvivors[1] + numberOfSurvivors[4] + numberOfSurvivors[5] + numberOfSurvivors[9];
						break;
					case Right:
						// 오른쪽: 378B
						weights[iWeights] = numberOfSurvivors[3] + numberOfSurvivors[7] + numberOfSurvivors[8] + numberOfSurvivors[11];
						break;
					default:
						// 아래: 9ABC
						weights[iWeights] = numberOfSurvivors[9] + numberOfSurvivors[10] + numberOfSurvivors[11] + numberOfSurvivors[12];
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
			
			//치유
			else{
				
				int[] weights = new int[4];

				for ( int iWeights = 0; iWeights < 4; iWeights++ )
				{
					switch ( directions[iWeights] )
					{
					case Up:
						// 위
						weights[iWeights] = numberOfInfecteds[1] + numberOfInfecteds[2] + numberOfInfecteds[3];
						break;
					case Left:
						// 왼쪽
						weights[iWeights] = numberOfInfecteds[1] + numberOfInfecteds[5] + numberOfInfecteds[9];
						break;
					case Right:
						// 오른쪽
						weights[iWeights] = numberOfInfecteds[3] + numberOfInfecteds[7] + numberOfInfecteds[11];
						break;
					default:
						// 아래
						weights[iWeights] = numberOfInfecteds[9] + numberOfInfecteds[10] + numberOfInfecteds[11];
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
			
			//미완
		}
		// Note: 이 메서드는 생존자 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
			return GetMovableAdjacentDirection();
		
		// 그렇지 않으면 정화 기도 시도
		return DirectionCode.Stay;
		// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
	}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			
			Init_Data();

			/*
			 * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다.
			 * 		 이 if문의 내용은 0턴째에만 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다. 
			 */
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		//시폭정보
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
		
		
				
					
		
		// 치유정보
		int max_counts = 0;
		int max_row2= -1;
		int max_column2 = -1;
		int min_distance3 = Constants.Classroom_Width * Constants.Classroom_Height;
		
		// 전체 칸을 검색하여 시체 및 감염체 수가 가장 많은 칸을 찾음
		for ( int row = 1; row < Constants.Classroom_Height-1; row++ )
		{
			for ( int column = 1; column < Constants.Classroom_Width-1; column++ )
			{
				int counts = cells[row-1][column-1].CountIf_Players(player -> player.state == StateCode.Infected ) 
						+ cells[row-1][column].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row-1][column+1].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row][column-1].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row][column+1].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row+1][column-1].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row+1][column].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row+1][column+1].CountIf_Players(player -> player.state == StateCode.Infected );
				
				int distance = favoritePoint.GetDistance(row, column);

				// 가장 많은 칸이 발견되면 갱신
				if ( counts > max_counts )
				{
					max_counts = counts;
					max_row2 = row;
					max_column2 = column;
					min_distance3 = distance;
				}
				// 가장 많은 칸이 여럿이면 그 중 '선호하는 칸'과 가장 가까운 칸을 선택
				else if ( counts == max_counts )
				{
					// 거리가 더 가까우면 갱신
					if ( distance < min_distance3 )
					{
						max_row2 = row;
						max_column2 = column;
						min_distance3 = distance;
					}
					// 거리마저 같으면 더 좋아하는 방향을 선택
					else if ( distance == min_distance3 )
					{
						for ( int iDirection = 0; iDirection < 4; iDirection++ )
						{
							Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
							
							if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row2, max_column2) )
							{
								max_row2 = row;
								max_column2 = column;
								break;
							}
						} 
						
						//여기까지 왔다면 이제 그만 놓아 주자
					}
				}
			}
		}
		
	
		// 정찰정보
		int infCounts = 0;
		int surCounts = 0;
		int nonInfCounts = 0;
		
		int max_scoutCoverage = 0;
		int max_scoutCoverRow = -1;
		int max_scoutCoverCol = -1;
		int min_scoutCoverDistance = Constants.Classroom_Width * Constants.Classroom_Height;
		
		for ( int row = 3; row < Constants.Classroom_Height-2; row++ )
		{
			for ( int column = 3; column < Constants.Classroom_Width-2; column++ )
			{
				infCounts = cells[row-2][column].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row][column-2].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row][column+2].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row+2][column].CountIf_Players(player -> player.state == StateCode.Infected );
				
				nonInfCounts = cells[row-1][column-1].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row-1][column].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row-1][column+1].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row][column-1].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row][column+1].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row+1][column-1].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row+1][column].CountIf_Players(player -> player.state == StateCode.Infected )
						+ cells[row+1][column+1].CountIf_Players(player -> player.state == StateCode.Infected );
				
				
				surCounts = cells[row-1][column-1].CountIf_Players(player -> player.state == StateCode.Survivor )
						+ cells[row-1][column].CountIf_Players(player -> player.state == StateCode.Survivor )
						+ cells[row-1][column+1].CountIf_Players(player -> player.state == StateCode.Survivor )
						+ cells[row][column-1].CountIf_Players(player -> player.state == StateCode.Survivor )
						+ cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor )
						+ cells[row][column+1].CountIf_Players(player -> player.state == StateCode.Survivor )
						+ cells[row+1][column-1].CountIf_Players(player -> player.state == StateCode.Survivor )
						+ cells[row+1][column].CountIf_Players(player -> player.state == StateCode.Survivor )
						+ cells[row+1][column+1].CountIf_Players(player -> player.state == StateCode.Survivor );
				
				
				int scoutCoverWeight = infCounts + surCounts;

				int distance = favoritePoint.GetDistance(row, column);
				
				
						
				if(infCounts != 0 && surCounts != 0 && scoutCoverWeight > max_scoutCoverage && nonInfCounts == 0){
					
					max_scoutCoverage = scoutCoverWeight;
					max_scoutCoverRow = row;
					max_scoutCoverCol = column;
					min_scoutCoverDistance = distance;
					
										
				}
				
				else if ( scoutCoverWeight == max_scoutCoverage)
				{
					// 거리가 더 가까우면 갱신
					if ( distance < min_scoutCoverDistance )
					{
						max_scoutCoverRow = row;
						max_scoutCoverCol = column;
						min_scoutCoverDistance = distance;
					}
				// 거리마저 같으면 더 좋아하는 방향을 선택
					else if ( distance == min_scoutCoverDistance )
					{
						for ( int iDirection = 0; iDirection < 4; iDirection++ )
						{
							Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
						
							if ( adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_scoutCoverRow, max_scoutCoverCol) )
							{
								max_scoutCoverRow = row;
								max_scoutCoverCol = column;
								break;
							}
						} 
					
					//여기까지 왔다면 이제 그만 놓아 주자
					}
				}
				
				
			}
			
		}
		
				
				
		// 생존정보
				int min_row = -1;
				int min_column = -1;
				int min_count = 1;
				int min_distance2 = Constants.Classroom_Width * Constants.Classroom_Height;
				
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
							min_distance2 = distance;
						}
						// 플레이어 수가 같으면 선호하는 칸과 더 가까운 칸을 선택
						else if ( count == min_count )
						{
							// 거리가 더 가까우면 갱신
							if ( distance < min_distance2 )
							{
								min_row = row;
								min_column = column;
								min_distance2 = distance;
							}
							// 거리마저 같으면 더 좋아하는 방향을 선택
							else if ( distance == min_distance2 )
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
				
				
		
		//30 충족시
		if(myScore.survivor_max >= 30){
			//시폭
			if(max_weight > myScore.corpse_max){
				return new Point(max_row, max_column);
			}
			//치유
			else if(max_counts > myScore.corpse_total){
				return new Point(max_row2, max_column2);
				
				
			}
			
			//정찰
			
			else if(max_scoutCoverage != 0){
				
				return new Point(max_scoutCoverRow, max_scoutCoverCol);
				
				
			}
			//생존
			else if(cells[min_row][min_column].CountIf_Players(player -> player.state == StateCode.Infected) == 0){
				
				return new Point(min_row, min_column);

				
			}
			
			else
				return favoritePoint;
			
			
		}
		
		
		//30 충족못했을시

		else{
			if(max_weight > myScore.corpse_max){
				return new Point(max_row, max_column);
			}
			
			else if(cells[min_row][min_column].CountIf_Players(player -> player.state == StateCode.Infected) == 0)
					return new Point(min_row, min_column);
				
			else
				return favoritePoint;
			
			
		}
	}
}
