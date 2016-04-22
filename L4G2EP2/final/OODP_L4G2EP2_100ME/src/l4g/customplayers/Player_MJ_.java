package l4g.customplayers;

import java.util.ArrayList;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;
import l4g.data.TurnInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class Player_MJ_ extends Player
{
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
	public Player_MJ_(int ID)
	{
		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
		super(ID, "http://www.kiworkshop.org");

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
	
	/**
	 * '방향 우선순위'를 기록해 두는 배열입니다.
	 * 이 field는 반드시 필요합니다.
	 */
	DirectionCode[] directions = new DirectionCode[4];
	
	/**
	 * '선호하는 칸'을 기록해 두는 field입니다.
	 * 이 field는 반드시 필요합니다.
	 */
	Point favoritePoint = new Point(6, 6);

	boolean InfectedMoreThanTwice = false;
	boolean DirectInfectionIsExecuted = false;

	int SpawnCount = 0;
	int CorpseCount = 0;
	
	int InfectionCheck = 0;
	
	int AlwaysLiveDirection()
	{
		int infecteds;
		int row = myInfo.position.row;
		int col = myInfo.position.column;
		
		infecteds = 0;
		// up
		if(idxIsOkay(row - 2) && idxIsOkay(col))
			infecteds += cells[row - 2][col].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(row - 1) && idxIsOkay(col - 1))
			infecteds += cells[row - 1][col - 1].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(row - 1) && idxIsOkay(col))
			infecteds += cells[row - 1][col].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(row - 1) && idxIsOkay(col + 1))
			infecteds += cells[row - 1][col + 1].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(row - 1) && infecteds == 0) return 0;
		// left
		infecteds = 0;
		if(idxIsOkay(row) && idxIsOkay(col - 2))
			infecteds += cells[row][col - 2].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(row - 1) && idxIsOkay(col - 1))
			infecteds += cells[row - 1][col - 1].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(row) && idxIsOkay(col - 1))
			infecteds += cells[row][col - 1].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(row + 1) && idxIsOkay(col - 1))
			infecteds += cells[row + 1][col - 1].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(col - 1) && infecteds == 0) return 1;
		// right 
		infecteds = 0;
		if(idxIsOkay(row) && idxIsOkay(col + 2))
			infecteds += cells[row][col + 2].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(row - 1) && idxIsOkay(col + 1))
			infecteds += cells[row - 1][col + 1].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(row) && idxIsOkay(col + 1))
			infecteds += cells[row][col + 1].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(row + 1) && idxIsOkay(col + 1))
			infecteds += cells[row + 1][col + 1].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(col + 1) && infecteds == 0) return 2;
		// down 
		infecteds = 0;
		if(idxIsOkay(row + 2) && idxIsOkay(col))
			infecteds += cells[row + 2][col].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(row + 1) && idxIsOkay(col - 1))
			infecteds += cells[row + 1][col - 1].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(row + 1) && idxIsOkay(col))
			infecteds += cells[row + 1][col].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(row + 1) && idxIsOkay(col + 1))
			infecteds += cells[row + 1][col + 1].CountIf_Players(player -> player.state == StateCode.Infected);
		if(idxIsOkay(row + 1) && infecteds == 0) return 3;
		
		return -1; // 언제나 살수있는 방향이 없을 
	}

	DirectionCode AvoidSurvivor()
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

	DirectionCode CleaningPray()
	{
		if(cells[myInfo.position.row][myInfo.position.column].CountIf_Players(player -> player.state == StateCode.Corpse) != 0)
		{
			//north
			if( idxIsOkay(myInfo.position.row - 1))
			{
				if(cells[myInfo.position.row - 1][myInfo.position.column].CountIf_Players(player -> player.state == StateCode.Corpse) == 0)
				{
					return DirectionCode.Up;
				}
			}		
			//east
			if( idxIsOkay(myInfo.position.column + 1))
			{
				if(cells[myInfo.position.row][myInfo.position.column+1].CountIf_Players(player -> player.state == StateCode.Corpse) == 0)
				{
					return DirectionCode.Right;
				}
			}
			//south
			if( idxIsOkay(myInfo.position.row + 1))
			{
				if(cells[myInfo.position.row + 1][myInfo.position.column].CountIf_Players(player -> player.state == StateCode.Corpse) == 0)
				{
					return DirectionCode.Down;
				}
			}
			//west
			if( idxIsOkay(myInfo.position.column - 1))
			{
				if(cells[myInfo.position.row][myInfo.position.column - 1].CountIf_Players(player -> player.state == StateCode.Corpse) == 0)
				{
					return DirectionCode.Left;
				}
			}
		}
		return DirectionCode.Stay;
	}

	int[] CorpseOverlapped(int[] rowcol)
	{
		int currentInfecteds = 0;
		int maxInfecteds = 0;
		
		for ( int i = 0; i < Constants.Classroom_Height; i++ )
		{
			for ( int j = 0; j < Constants.Classroom_Width; j++ )
			{
				currentInfecteds = cells[i][j].CountIf_Players(player -> player.state == StateCode.Corpse);
				if(maxInfecteds < currentInfecteds) 
				{
					maxInfecteds = currentInfecteds;
					rowcol[0] = i;
					rowcol[1] = j;
				}
			}
		}
		
		return rowcol;
	}

	boolean idxIsOkay(int idx)
	{
		if( 0<= idx && idx <= 12)
			return true;
		else
			return false;
	}

	int[] InfectedOverlapped(int[] rowcol) // 감염체 가장 많이 겹쳐있는 곳 찾아서.
	{
		int currentInfecteds = 0;
		int maxInfecteds = 0;
		
		for ( int i = 0; i < Constants.Classroom_Height; i++ )
		{
			for ( int j = 0; j < Constants.Classroom_Width; j++ )
			{
				currentInfecteds = cells[i][j].CountIf_Players(player -> player.state == StateCode.Infected);
				if(maxInfecteds < currentInfecteds) 
				{
					maxInfecteds = currentInfecteds;
					rowcol[0] = i;
					rowcol[1] = j;
				}
			}
		}
		
		return rowcol;
	}

	DirectionCode MaxDirection(int north, int west, int east, int south)
	{
		int max = 0;
		
		while(true){
			max = north > max ? north : max;
			max = west > max ? west : max;
			max = east > max ? east : max;
			max = south > max ? south : max;
			
			if( max == north)
			{
				if(myInfo.position.row != 0)
					return DirectionCode.Up;
				else
					north = -1;
			}
			else if( max == west)
			{
				if(myInfo.position.column != 0)
					return DirectionCode.Left;
				else
					west = -1;
			}
			else if( max == east)
			{
				if(myInfo.position.column != Constants.Classroom_Width - 1)
					return DirectionCode.Right;
				else 
					east = -1;
			}
			else
			{
				if(myInfo.position.row != Constants.Classroom_Height - 1)
					return DirectionCode.Down;
				else
					south = -1;
			}
		}
	}

	DirectionCode MostInfecteds()
	{
		// TODO 동서남북중에 생존자 가장 많은 쪽으로.
		int currentROW = myInfo.position.row;
		int currentCOL = myInfo.position.column;
	
		int north = 0;
		int west = 0;
		int east = 0;
		int south = 0;
		//북쪽
		for ( int ROW = 0; ROW < currentROW; ROW++ )
		{
			for ( int COL = 0; COL < Constants.Classroom_Width; COL++ )
			{
				north += cells[ROW][COL].CountIf_Players(player -> player.state == StateCode.Infected);
			}
		}
		// 동쪽
		for ( int ROW = 0; ROW < Constants.Classroom_Height; ROW++ )
		{
			for ( int COL = currentCOL + 1; COL < Constants.Classroom_Width; COL++ )
			{
				east += cells[ROW][COL].CountIf_Players(player -> player.state == StateCode.Infected);
			}
		}
		//남쪽
		for ( int ROW = currentROW + 1; ROW < Constants.Classroom_Height; ROW++ )
		{
			for ( int COL = 0; COL < Constants.Classroom_Width; COL++ )
			{
				south += cells[ROW][COL].CountIf_Players(player -> player.state == StateCode.Infected);
			}
		}
		//서쪽
		for ( int ROW = 0; ROW < Constants.Classroom_Height; ROW++ )
		{
			for ( int COL = 0; COL < currentCOL; COL++ )
			{
				west += cells[ROW][COL].CountIf_Players(player -> player.state == StateCode.Infected);
			}
		}
	
		return MaxDirection(north, west, east, south);
	}

	DirectionCode MostSurvivor()
	{
		// TODO 동서남북중에 생존자 가장 많은 쪽으로.
		int currentROW = myInfo.position.row;
		int currentCOL = myInfo.position.column;
	
		int north = 0;
		int west = 0;
		int east = 0;
		int south = 0;
		//북쪽
		for ( int ROW = 0; ROW < currentROW; ROW++ )
		{
			for ( int COL = 0; COL < Constants.Classroom_Width; COL++ )
			{
				north += cells[ROW][COL].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
		}
		// 동쪽
		for ( int ROW = 0; ROW < Constants.Classroom_Height; ROW++ )
		{
			for ( int COL = currentCOL + 1; COL < Constants.Classroom_Width; COL++ )
			{
				east += cells[ROW][COL].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
		}
		//남쪽
		for ( int ROW = currentROW + 1; ROW < Constants.Classroom_Height; ROW++ )
		{
			for ( int COL = 0; COL < Constants.Classroom_Width; COL++ )
			{
				south += cells[ROW][COL].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
		}
		//서쪽
		for ( int ROW = 0; ROW < Constants.Classroom_Height; ROW++ )
		{
			for ( int COL = 0; COL < currentCOL; COL++ )
			{
				west += cells[ROW][COL].CountIf_Players(player -> player.state == StateCode.Survivor);
			}
		}
	
		return MaxDirection(north, west, east, south);
	}

	void switchCheck()
	{
		if(this.turnInfo.turnNumber > 10)
			DirectInfectionIsExecuted = true;
	}
	
	DirectionCode SurviveAndScouting()
	{
		// 생존자들 따라다니긴 하는데 최대한 살아서. 감염체들 피하면서 다니기
		// 생존자 따라다닌다. 근데 생존자 많은 곳이랑 감염체 많은 곳 방향이 같으면 감염체 적은 방향으로 가기.
		// 일단 밑에 코드는 감염체 피하는 코드
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
			numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
			
			if ( column < Constants.Classroom_Width - 2 )
				numberOfPlayers[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Infected);
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
	
	@Override
	public DirectionCode Survivor_Move()
	{
		switchCheck();

		if(turnInfo.turnNumber < 10) // 9턴까지 처음 시작 자리에서 왔다갔다 한다. 
		{
			if(turnInfo.turnNumber % 2 == 0)
				return DirectionCode.Left;
			else
				return DirectionCode.Right;
		}

		if(DirectInfectionIsExecuted == false)
		{
			return AvoidSurvivor(); // 직접감염 안 받았을때  
//			return MostSurvivor(); // 직접감염 받았을때 
			// 사람 많은 곳으로 가기
		}
//		
//		
//		int direct = AlwaysLiveDirection();
//		if(direct != -1) // 무조건 살 수 있는 방향이 있으면 거기로 
//			return directions[direct];
//		else
			return SurviveAndScouting(); // TODO 해야할 것, 생존,정찰 점수 올리는 코드 짜기. 죽이기,시체파먹기 점수 올리는 코드 짜기.
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
		switchCheck();
		InfectedMoreThanTwice = true;
		// 플레이어가 죽었다는 것은 "직접감염 -> 죽음 -> 생존자 -> 다른사람에게 죽음. -> 2번 이상 감염됨" 이라는 의미.

		CorpseCount++;
	}

	@Override
	public DirectionCode Infected_Move()
	{
		switchCheck();
		return CleaningPray();
	}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			/*
			 * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다.
			 * 		 이 if문의 내용은 0턴째에만 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다. 
			 */
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Down;
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		switchCheck();
		int[] rowcol = new int[2];

		if(SpawnCount == 0) // 시작은 10,9에서 
		{
			rowcol[0] = 10;
			rowcol[1] = 9;
		}
		else
		{
			// 감염체가 가장 많이 겹쳐있는 곳.
			rowcol = InfectedOverlapped(rowcol);
		}
		// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.
		SpawnCount++;
		return new Point(rowcol[0], rowcol[1]);
	}
}
