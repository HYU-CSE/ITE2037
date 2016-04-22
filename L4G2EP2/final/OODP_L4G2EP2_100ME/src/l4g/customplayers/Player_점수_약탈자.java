package l4g.customplayers;

import java.util.ArrayList;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * �� ��Ȱ���� ������ �ø� ������ �����ϰ� �׿� �´� ��Ȱ�� ������ ���� �ٷ� ��ȭ �⵵�� �ø��� team Sigma �Ҽ� �÷��̾��Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_����_��Ż�� extends Player
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
	
	public Player_����_��Ż��(int ID)
	{
		super(ID, "���� ��Ż��");
	}
	
	static final int State_None = 0;
	static final int State_Survivor_Max = 1;
	static final int State_Survivor_Tot = 2;
	static final int State_Corpse = 3;	
	
	int state = State_None;
	
	// �� �÷��̾�� �� ���𸶴� ���� �ٸ� ������ ������
	int seasonNumber;
	
	// ��ǥ�ϴ� ������ �ִ� ����
	int expectedScore_Survivor_Max;
	
	void UpdateState()
	{
		// ������ �ִ� ���� ��ǥ�� �޼��߰ų� ������ ������ ���� ��� ����
		if ( myScore.survivor_max >= expectedScore_Survivor_Max || 
				expectedScore_Survivor_Max < Constants.Total_Players - turnInfo.turnNumber )
		{
			// ���� ��ȣ�� �� ��ȣ�� �����Ͽ� ���� ���� ��ǥ�� ����
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
			// ���� ��ȣ�� �� ��ȣ�� �����Ͽ� ���� ���� ��ǥ�� ����
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
		 * ������ �̵�: �÷��̾� ���� ���� ���� �������� �̵��մϴ�.
		 *              �������� �þ� ������
		 *                    0
		 *                   123
		 *                  45678
		 *                   9AB
		 *                    C
		 *              ..�� ��
		 *              ��:     0123�� �ִ� �÷��̾� ��
		 *              ����:   1459�� �ִ� �÷��̾� ��
		 *              ������: 378B�� �ִ� �÷��̾� ��
		 *              �Ʒ�:   9ABC�� �ִ� �÷��̾� ��
		 *              ..�� �ջ��Ͽ� ���մϴ�.
		 */
		int[] numberOfPlayers = new int[13];
		
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		
		// ���� ���̴� 13���� ��쿡 ���� �÷��̾� �� ���
		
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
		
		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
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
		
		
		// 4���� ����(������ ���� �켱������ ����)�� ���� �÷��̾� �� �ջ�		
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// ��: 0123
				weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3];
				break;
			case Left:
				// ����: 1459
				weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9];
				break;
			case Right:
				// ������: 378B
				weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11];
				break;
			default:
				// �Ʒ�: 9ABC
				weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11] + numberOfPlayers[12];
				break;
			}
		}
		
		// �÷��̾� ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
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
		 * ������ �̵�: ������ ���� ���� ���� �������� �̵��մϴ�.
		 *              �������� �þ� ������
		 *                    0
		 *                   123
		 *                  45678
		 *                   9AB
		 *                    C
		 *              ..�� ��
		 *              ��:     0123�� �ִ� ������ ��
		 *              ����:   1459�� �ִ� ������ ��
		 *              ������: 378B�� �ִ� ������ ��
		 *              �Ʒ�:   9ABC�� �ִ� ������ ��
		 *              ..�� �ջ��Ͽ� ���մϴ�.
		 */
		int[] numberOfPlayers = new int[13];
		
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		
		// ���� ���̴� 13���� ��쿡 ���� ������ �� ���
		
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
		
		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
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
		
		
		// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�		
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// ��: 0123
				weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3];
				break;
			case Left:
				// ����: 1459
				weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9];
				break;
			case Right:
				// ������: 378B
				weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11];
				break;
			default:
				// �Ʒ�: 9ABC
				weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11] + numberOfPlayers[12];
				break;
			}
		}
		
		// ������ ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
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
		// �� �ؿ� ��ü�� ��� ������ ������
		if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
			return GetMovableAdjacentDirection();
		
		// �׷��� ������ ��ȭ �⵵ �õ�
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

				// �÷��̾� ���� �� ���ٸ� �׻� ����
				if ( count < min_count )
				{
					min_row = row;
					min_column = column;
					min_count = count;
					min_distance = distance;
				}
				// �÷��̾� ���� ������ ��ȣ�ϴ� ĭ�� �� ����� ĭ�� ����
				else if ( count == min_count )
				{
					// �Ÿ��� �� ������ ����
					if ( distance < min_distance )
					{
						min_row = row;
						min_column = column;
						min_distance = distance;
					}
					// �Ÿ����� ������ �� �����ϴ� ������ ����
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
						
						//������� �Դٸ� ���� �׸� ���� ����
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

		// ��ü ĭ�� �˻��Ͽ� ������ ���� ��ü + ����ü �� ����
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
		
		// ��ü ĭ�� �˻��Ͽ� ���� �߰� ������ ���� ���� ĭ�� ã��
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
		{
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
			{
				int numberOfSurvivors = 0;
				int numberOfOthers = 0;
				int weight = 0;
				int distance = favoritePoint.GetDistance(row, column);

				// �Ͼ ��ü �Ǵ� ����ü�� ���� ĭ�� �߰� ���� ��� ����
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
				
				// ���� ���� ĭ�� �߰ߵǸ� ����
				if ( weight > max_weight )
				{
					max_weight = weight;
					max_row = row;
					max_column = column;
					min_distance = distance;
				}
				// ���� ���� ĭ�� �����̸� �� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
				else if ( weight == max_weight )
				{
					// �Ÿ��� �� ������ ����
					if ( distance < min_distance )
					{
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// �Ÿ����� ������ �� �����ϴ� ������ ����
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
						
						//������� �Դٸ� ���� �׸� ���� ����
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
		
		// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
		for ( int row = 0; row < Constants.Classroom_Height; row++ )
		{
			for ( int column = 0; column < Constants.Classroom_Width; column++ )
			{
				CellInfo cell = this.cells[row][column];

				int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
				int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
				
				int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
				int distance = favoritePoint.GetDistance(row, column);

				// ���� ���� ĭ�� �߰ߵǸ� ����
				if ( weight > max_weight )
				{
					max_weight = weight;
					max_row = row;
					max_column = column;
					min_distance = distance;
				}
				// ���� ���� ĭ�� �����̸� �� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
				else if ( weight == max_weight )
				{
					// �Ÿ��� �� ������ ����
					if ( distance < min_distance )
					{
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// �Ÿ����� ������ �� �����ϴ� ������ ����
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
						
						//������� �Դٸ� ���� �׸� ���� ����
					}
				}
			}
		}
		
		// �˻��ߴµ� ��ü�� ����ü�� �ϳ��� ���ٸ� ��ġ ����
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
