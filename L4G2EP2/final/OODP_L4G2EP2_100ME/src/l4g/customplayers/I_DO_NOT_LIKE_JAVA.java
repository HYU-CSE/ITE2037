package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class I_DO_NOT_LIKE_JAVA extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public I_DO_NOT_LIKE_JAVA(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "������ ��������");
		
		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳� �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = false;
		
		
		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ���� ���ƿɽô�.
		
		
	}
	
	/*
	 * TODO#5	���� �������� �׷� �� ��Ʈ�� ���� �Ʒ��� �ִ� �ټ� ���� �ǻ� ���� �޼��带 �ϼ��ϼ���.
	 * 			�翬�� �� �濡 �� �� ������, �߰��߰� �ڵ带 ����� �δ� ���� ������,
	 * 			�ڵ� �ۼ��� ����� �� �ƹ� �δ� ���� ������ ã�� ������.
	 * 
	 * 			L4G�� �������� '����'�� �߱��ϴ� ������ ���� ������ ������ �ƴմϴ�!
	 * 
	 * 			�������� �̹� �������� ������ �ð���ŭ, ���� �ٸ� ���� / �ٸ� �������� ������ ���̴� �ð��� �پ��� �� ���Դϴ�.
	 * 			�׷��� �ڽ��� ���� ���� ������ ���÷ȴٸ�, �̸� �� �÷��̾ �����ϱ� ���� �Ƴ� ���� ����� ������ ������!
	 * 
	 * 			��������� �Ǿ� Ȳ���� ������ ���ε��ϰ� Eclipse�� ���ƿ� �������� �ۼ��� �ڵ带 ���� ����,
	 * 			'�ڵ忡 ����̶��� ���� �� ���� �ֱ���'��� ������ ���� ��� �� ���Դϴ�.
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
		
		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
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
		
		
		
		//���������� ��������
		if (myScore.survivor_max < 30){
			
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
			
			
		
		
		
		//��������orġ�������� ��������
		else{
			
			int ifScoutInfectNum=0;
			int ifScoutSurvivorNum=0;
			int ifScoutNonInfectNum = 0;
			
			ifScoutInfectNum= numberOfInfecteds[0]+numberOfInfecteds[4]+numberOfInfecteds[8]+numberOfInfecteds[12];
			ifScoutSurvivorNum =numberOfSurvivors[1]+numberOfSurvivors[6]+numberOfSurvivors[2]+numberOfSurvivors[3]+numberOfSurvivors[5]+numberOfSurvivors[7]+numberOfSurvivors[9]+numberOfSurvivors[10]+numberOfSurvivors[11];
			ifScoutNonInfectNum = numberOfInfecteds[1]+numberOfInfecteds[2]+numberOfInfecteds[3]+numberOfInfecteds[5]+numberOfInfecteds[6]+numberOfInfecteds[7]+numberOfInfecteds[9]+numberOfInfecteds[10]+numberOfInfecteds[11];
			
			
			//����
			if ((ifScoutSurvivorNum !=0 && ifScoutInfectNum!=0 && ifScoutNonInfectNum==0) || (favoritePoint.row == row && favoritePoint.column == column)){
				int[] weights = new int[4];

				for ( int iWeights = 0; iWeights < 4; iWeights++ )
				{
					switch ( directions[iWeights] )
					{
					case Up:
						// ��: 0123
						weights[iWeights] = numberOfSurvivors[0] + numberOfSurvivors[1] + numberOfSurvivors[2] + numberOfSurvivors[3];
						break;
					case Left:
						// ����: 1459
						weights[iWeights] = numberOfSurvivors[1] + numberOfSurvivors[4] + numberOfSurvivors[5] + numberOfSurvivors[9];
						break;
					case Right:
						// ������: 378B
						weights[iWeights] = numberOfSurvivors[3] + numberOfSurvivors[7] + numberOfSurvivors[8] + numberOfSurvivors[11];
						break;
					default:
						// �Ʒ�: 9ABC
						weights[iWeights] = numberOfSurvivors[9] + numberOfSurvivors[10] + numberOfSurvivors[11] + numberOfSurvivors[12];
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
			
			//ġ��
			else{
				
				int[] weights = new int[4];

				for ( int iWeights = 0; iWeights < 4; iWeights++ )
				{
					switch ( directions[iWeights] )
					{
					case Up:
						// ��
						weights[iWeights] = numberOfInfecteds[1] + numberOfInfecteds[2] + numberOfInfecteds[3];
						break;
					case Left:
						// ����
						weights[iWeights] = numberOfInfecteds[1] + numberOfInfecteds[5] + numberOfInfecteds[9];
						break;
					case Right:
						// ������
						weights[iWeights] = numberOfInfecteds[3] + numberOfInfecteds[7] + numberOfInfecteds[11];
						break;
					default:
						// �Ʒ�
						weights[iWeights] = numberOfInfecteds[9] + numberOfInfecteds[10] + numberOfInfecteds[11];
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
			
			//�̿�
		}
		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
			return GetMovableAdjacentDirection();
		
		// �׷��� ������ ��ȭ �⵵ �õ�
		return DirectionCode.Stay;
		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
	}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			
			Init_Data();

			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�.
			 * 		 �� if���� ������ 0��°���� ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�. 
			 */
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		//��������
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
		
		
				
					
		
		// ġ������
		int max_counts = 0;
		int max_row2= -1;
		int max_column2 = -1;
		int min_distance3 = Constants.Classroom_Width * Constants.Classroom_Height;
		
		// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
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

				// ���� ���� ĭ�� �߰ߵǸ� ����
				if ( counts > max_counts )
				{
					max_counts = counts;
					max_row2 = row;
					max_column2 = column;
					min_distance3 = distance;
				}
				// ���� ���� ĭ�� �����̸� �� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
				else if ( counts == max_counts )
				{
					// �Ÿ��� �� ������ ����
					if ( distance < min_distance3 )
					{
						max_row2 = row;
						max_column2 = column;
						min_distance3 = distance;
					}
					// �Ÿ����� ������ �� �����ϴ� ������ ����
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
						
						//������� �Դٸ� ���� �׸� ���� ����
					}
				}
			}
		}
		
	
		// ��������
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
					// �Ÿ��� �� ������ ����
					if ( distance < min_scoutCoverDistance )
					{
						max_scoutCoverRow = row;
						max_scoutCoverCol = column;
						min_scoutCoverDistance = distance;
					}
				// �Ÿ����� ������ �� �����ϴ� ������ ����
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
					
					//������� �Դٸ� ���� �׸� ���� ����
					}
				}
				
				
			}
			
		}
		
				
				
		// ��������
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

						// �÷��̾� ���� �� ���ٸ� �׻� ����
						if ( count < min_count )
						{
							min_row = row;
							min_column = column;
							min_count = count;
							min_distance2 = distance;
						}
						// �÷��̾� ���� ������ ��ȣ�ϴ� ĭ�� �� ����� ĭ�� ����
						else if ( count == min_count )
						{
							// �Ÿ��� �� ������ ����
							if ( distance < min_distance2 )
							{
								min_row = row;
								min_column = column;
								min_distance2 = distance;
							}
							// �Ÿ����� ������ �� �����ϴ� ������ ����
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
								
								//������� �Դٸ� ���� �׸� ���� ����
							}
						}
					}
				}
				
				
		
		//30 ������
		if(myScore.survivor_max >= 30){
			//����
			if(max_weight > myScore.corpse_max){
				return new Point(max_row, max_column);
			}
			//ġ��
			else if(max_counts > myScore.corpse_total){
				return new Point(max_row2, max_column2);
				
				
			}
			
			//����
			
			else if(max_scoutCoverage != 0){
				
				return new Point(max_scoutCoverRow, max_scoutCoverCol);
				
				
			}
			//����
			else if(cells[min_row][min_column].CountIf_Players(player -> player.state == StateCode.Infected) == 0){
				
				return new Point(min_row, min_column);

				
			}
			
			else
				return favoritePoint;
			
			
		}
		
		
		//30 ������������

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
