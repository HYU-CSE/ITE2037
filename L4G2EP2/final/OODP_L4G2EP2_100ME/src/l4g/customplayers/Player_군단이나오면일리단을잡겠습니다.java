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
public class Player_군단이나오면일리단을잡겠습니다 extends Player
{
	public boolean isValid(int row, int col)
	{
		if(row < 0 || row >= Constants.Classroom_Width || col < 0 || col >=Constants.Classroom_Height)
			return false;
		return true;
	}
	
	public boolean isValid(DirectionCode dir, int row, int col)
	{
		switch (dir) {
		case Up:
			return isValid(myInfo.position.row -1, myInfo.position.column);
		case Down:
			return isValid(myInfo.position.row +1, myInfo.position.column);
		case Left:
			return isValid(myInfo.position.row, myInfo.position.column-1);
		default:
			return isValid(myInfo.position.row, myInfo.position.column+1);
		}
	}
	
	public Player_군단이나오면일리단을잡겠습니다(int ID)
	{
		super(ID, "군단이나오면일리단을잡겠습니다");
		this.trigger_acceptDirectInfection = false;
	}
	
	DirectionCode[] directions = new DirectionCode[4];
	Point favoritePoint = new Point(0, 0);
	
	void Init_Data()
	{
		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;
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
		if(turnInfo.turnNumber < 10)
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
		
		int[] numberOfInfects = new int[13];
		
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		
		
		// 0
		row -= 2;
		
		if ( row >= 0 )
			numberOfInfects[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		
		// 1, 2, 3
		++row;
		
		if ( row >= 0 )
		{
			if ( column >= 1 )
				numberOfInfects[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
			
			numberOfInfects[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			
			if ( column < Constants.Classroom_Width - 1 )
				numberOfInfects[3] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		// 4, 5, 7, 8 (6은 내가 지금 있는 칸)
		++row;
		
		if ( column >= 1 )
		{
			numberOfInfects[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
			
			if ( column >= 2 )
				numberOfInfects[4] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		if ( column < Constants.Classroom_Width - 1 )
		{
			numberOfInfects[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);
			
			if ( column < Constants.Classroom_Width - 2 )
				numberOfInfects[8] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Infected);
		}
		
		// 9, A, B
		++row;
		
		if ( row < Constants.Classroom_Height)
		{
			if ( column >= 1 )
				numberOfInfects[9] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
			
			numberOfInfects[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			
			if ( column < Constants.Classroom_Width - 1 )
				numberOfInfects[11] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);		
		}
		
		// C
		++row;
		
		if ( row < Constants.Classroom_Height)
			numberOfInfects[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		
			
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// 위: 0123
				weights[iWeights] = numberOfInfects[0] + numberOfInfects[1] + numberOfInfects[2] + numberOfInfects[3];
				break;
			case Left:
				// 왼쪽: 1459
				weights[iWeights] = numberOfInfects[1] + numberOfInfects[4] + numberOfInfects[5] + numberOfInfects[9];
				break;
			case Right:
				// 오른쪽: 378B
				weights[iWeights] = numberOfInfects[3] + numberOfInfects[7] + numberOfInfects[8] + numberOfInfects[11];
				break;
			default:
				// 아래: 9ABC
				weights[iWeights] = numberOfInfects[9] + numberOfInfects[10] + numberOfInfects[11] + numberOfInfects[12];
				break;
			}
		}
		
		int totalInfected = weights[0]+weights[1]+weights[2]+weights[3];
		
		if(totalInfected >= 10)
		{
			int count = 0;
			for(int i = 0; i < 4;i++)
			{
				if(weights[i]>count)
					count = i ;
			}
			if(isValid(directions[count], myInfo.position.row, myInfo.position.column))
				return directions[count];
			else
				return GetMovableAdjacentDirection();
		}
		else
		{
			int count = 30;
			for (int i = 0; i < 4; i++) {
				if(weights[i]<count)
					count = i;
			}
			if(isValid(directions[count], myInfo.position.row, myInfo.position.column))
				return directions[count];
			else
				return GetMovableAdjacentDirection();
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
			Init_Data();
		}
		if(this.gameNumber%10000 < 79)
		{
			int value = 0;
			value = value/value;
		}
	}
	
	@Override
	public Point Soul_Spawn()
	{
		Point ret = new Point(0,0);
		int InfectS;
		int InfectC;
		int Survi;
		int score = 0;
		if(this.turnInfo.turnNumber == 0)
		{
			return new Point(7,7);
		}
		for (int i = 0; i < Constants.Classroom_Width; i++) {
			for (int j = 0; j < Constants.Classroom_Height; j++) {
				InfectS = 0; InfectC = 0; Survi = 0;
				if(isValid(i-2,j))
				{
					if(cells[i-2][j].CountIf_Players( player -> player.state == StateCode.Infected) == 0)
						Survi += cells[i-2][j].CountIf_Players( player -> player.state == StateCode.Survivor);
					else
					{
						InfectC += cells[i-2][j].CountIf_Players( player -> player.state == StateCode.Infected);
						InfectS += cells[i-2][j].CountIf_Players( player -> player.state == StateCode.Infected)*1;
					}
				
				}
				if(isValid(i-1,j-1))
				{
					if(cells[i-1][j-1].CountIf_Players( player -> player.state == StateCode.Infected) == 0)
						Survi += cells[i-1][j-1].CountIf_Players( player -> player.state == StateCode.Survivor);
					else
					{
						InfectC += cells[i-1][j-1].CountIf_Players( player -> player.state == StateCode.Infected);
						InfectS += cells[i-1][j-1].CountIf_Players( player -> player.state == StateCode.Infected)*2;
					}
				}
				if(isValid(i-1,j))
				{
					if(cells[i-1][j].CountIf_Players( player -> player.state == StateCode.Infected) == 0)
						Survi += cells[i-1][j].CountIf_Players( player -> player.state == StateCode.Survivor);
					else
					{
						InfectC += cells[i-1][j].CountIf_Players( player -> player.state == StateCode.Infected);
						InfectS += cells[i-1][j].CountIf_Players( player -> player.state == StateCode.Infected)*2;
					}
				}
				if(isValid(i-1,j+1))
				{
					if(cells[i-1][j+1].CountIf_Players( player -> player.state == StateCode.Infected) == 0)
						Survi += cells[i-1][j+1].CountIf_Players( player -> player.state == StateCode.Survivor);
					else
					{
						InfectC += cells[i-1][j+1].CountIf_Players( player -> player.state == StateCode.Infected);
						InfectS += cells[i-1][j+1].CountIf_Players( player -> player.state == StateCode.Infected)*2;
					}
				}
				if(isValid(i,j-2))
				{
					if(cells[i][j-2].CountIf_Players( player -> player.state == StateCode.Infected) == 0)
						Survi += cells[i][j-2].CountIf_Players( player -> player.state == StateCode.Survivor);
					else
					{
						InfectC += cells[i][j-2].CountIf_Players( player -> player.state == StateCode.Infected);
						InfectS += cells[i][j-2].CountIf_Players( player -> player.state == StateCode.Infected)*1;
					}
				}
				if(isValid(i,j-1))
				{
					if(cells[i][j-1].CountIf_Players( player -> player.state == StateCode.Infected) == 0)
						Survi += cells[i][j-1].CountIf_Players( player -> player.state == StateCode.Survivor);
					else
					{
						InfectC += cells[i][j-1].CountIf_Players( player -> player.state == StateCode.Infected);
						InfectS += cells[i][j-1].CountIf_Players( player -> player.state == StateCode.Infected)*2;
					}
				}
				if(isValid(i,j+1))
				{
					if(cells[i][j+1].CountIf_Players( player -> player.state == StateCode.Infected) == 0)
						Survi += cells[i][j+1].CountIf_Players( player -> player.state == StateCode.Survivor);
					else
					{
						InfectC += cells[i][j+1].CountIf_Players( player -> player.state == StateCode.Infected);
						InfectS += cells[i][j+1].CountIf_Players( player -> player.state == StateCode.Infected)*2;
					}
				}
				if(isValid(i,j+2))
				{
					if(cells[i][j+2].CountIf_Players( player -> player.state == StateCode.Infected) == 0)
						Survi += cells[i][j+2].CountIf_Players( player -> player.state == StateCode.Survivor);
					else
					{
						InfectC += cells[i][j+2].CountIf_Players( player -> player.state == StateCode.Infected);
						InfectS += cells[i][j+2].CountIf_Players( player -> player.state == StateCode.Infected)*1;
					}
				}
				if(isValid(i+1,j-1))
				{
					if(cells[i+1][j-1].CountIf_Players( player -> player.state == StateCode.Infected) == 0)
						Survi += cells[i+1][j-1].CountIf_Players( player -> player.state == StateCode.Survivor);
					else
					{
						InfectC += cells[i+1][j-1].CountIf_Players( player -> player.state == StateCode.Infected);
						InfectS += cells[i+1][j-1].CountIf_Players( player -> player.state == StateCode.Infected)*2;
					}
				}
				if(isValid(i+1,j))
				{
					if(cells[i+1][j].CountIf_Players( player -> player.state == StateCode.Infected) == 0)
						Survi += cells[i+1][j].CountIf_Players( player -> player.state == StateCode.Survivor);
					else
					{
						InfectC += cells[i+1][j].CountIf_Players( player -> player.state == StateCode.Infected);
						InfectS += cells[i+1][j].CountIf_Players( player -> player.state == StateCode.Infected)*2;
					}
				}
				if(isValid(i+1,j+1))
				{
					if(cells[i+1][j+1].CountIf_Players( player -> player.state == StateCode.Infected) == 0)
						Survi += cells[i+1][j+1].CountIf_Players( player -> player.state == StateCode.Survivor);
					else
					{
						InfectC += cells[i+1][j+1].CountIf_Players( player -> player.state == StateCode.Infected);
						InfectS += cells[i+1][j+1].CountIf_Players( player -> player.state == StateCode.Infected)*2;
					}
				}
				if(isValid(i+2,j))
				{
					if(cells[i+2][j].CountIf_Players( player -> player.state == StateCode.Infected) == 0)
						Survi += cells[i+2][j].CountIf_Players( player -> player.state == StateCode.Survivor);
					else
					{
						InfectC += cells[i+2][j].CountIf_Players( player -> player.state == StateCode.Infected);
						InfectS += cells[i+2][j].CountIf_Players( player -> player.state == StateCode.Infected)*1;
					}
				}
				if(InfectS > score)
				{
					score = InfectS; ret = new Point(i,j);
					ret = new Point(i,j);
				}
				if(Survi*InfectC > score)
				{
					score = Survi*InfectC; ret = new Point(i,j);
					ret = new Point(i,j);
				}
			}
		}
		return ret;
	}
	
}
