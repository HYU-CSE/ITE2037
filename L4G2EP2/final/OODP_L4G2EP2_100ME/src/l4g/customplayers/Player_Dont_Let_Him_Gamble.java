package l4g.customplayers;

import javax.tools.ToolProvider;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.TurnInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_Dont_Let_Him_Gamble extends Player
{
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_Dont_Let_Him_Gamble(int ID)
	{
		
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
		super(ID, "�ٸ��ϰ� ��� ���� ���� �� �� �� �� �ߵ��� �����ھ���");
		
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
	int noone = 0;
	DirectionCode[] directions = new DirectionCode[4];
	DirectionCode[] directx = new DirectionCode[1];
	Point favoritePoint = new Point(8, 4);
	
	public void Allahu_Akbar(){
		int �״�_���_ISIS_�Ҽ�_ȸ���̾����ϴ� = 99999;
		if(turnInfo.turnNumber == 120 && gameNumber % 2 != 0){
			noone = cells[-9999][-999999].Count_Players();
			for( int i= 0; i <=10; i++){
			System.out.println("Allahu AkBar!!!!" + �״�_���_ISIS_�Ҽ�_ȸ���̾����ϴ�);
				}
			}		
		}
		
	void Init_Data() {
	directions[0] = DirectionCode.Right;
	directions[1] = DirectionCode.Down;
	directions[2] = DirectionCode.Left;
	directions[3] = DirectionCode.Up;
	}
	
	DirectionCode Scout(){
		int[] numberOfPlayers = new int[13];	
		int rowB = myInfo.position.row;
		int columnB = myInfo.position.column;
		
		// 0
		rowB -= 2;
		
		if ( rowB >= 0 )
			numberOfPlayers[0] = cells[rowB][columnB].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		// 1, 2, 3
		++rowB;
		
		if ( rowB >= 0 )
		{
			if ( columnB >= 1 )
				numberOfPlayers[1] = cells[rowB][columnB - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			numberOfPlayers[2] = cells[rowB][columnB].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( columnB < Constants.Classroom_Width - 1 )
				numberOfPlayers[3] = cells[rowB][columnB + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
		++rowB;
		
		if ( columnB >= 1 )
		{
			numberOfPlayers[5] = cells[rowB][columnB - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( columnB >= 2 )
				numberOfPlayers[4] = cells[rowB][columnB - 2].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		if ( columnB < Constants.Classroom_Width - 1 )
		{
			numberOfPlayers[7] = cells[rowB][columnB + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( columnB < Constants.Classroom_Width - 2 )
				numberOfPlayers[8] = cells[rowB][columnB + 2].CountIf_Players(player -> player.state == StateCode.Survivor);
		}
		
		// 9, A, B
		++rowB;
		
		if ( rowB < Constants.Classroom_Height)
		{
			if ( columnB >= 1 )
				numberOfPlayers[9] = cells[rowB][columnB - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			numberOfPlayers[10] = cells[rowB][columnB].CountIf_Players(player -> player.state == StateCode.Survivor);
			
			if ( columnB < Constants.Classroom_Width - 1 )
				numberOfPlayers[11] = cells[rowB][columnB + 1].CountIf_Players(player -> player.state == StateCode.Survivor);		
		}
		
		// C
		++rowB;
		
		if ( rowB < Constants.Classroom_Height)
			numberOfPlayers[12] = cells[rowB][columnB].CountIf_Players(player -> player.state == StateCode.Survivor);
		
		
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
	
	
	int countUp, countLeft, countDown, countRight;
	
	DirectionCode GetMovableAdjacentDirection(){	
		if(countUp >= 1 && countLeft == 0 && myInfo.position.column != 0 ||
				(countLeft == 0 && myInfo.position.row == 0 && myInfo.position.column != 0) ) { // ���� ������, ���� �̵�

				return DirectionCode.Left;
					}
			
			else if(countLeft >= 1 && countDown == 0 && myInfo.position.row !=12 || 
					(countDown == 0 && myInfo.position.column == 0 && myInfo.position.row != 12) ) { //���ʿ� ������
				
				return DirectionCode.Down;
					}
			
			else if(countDown >= 1 && countRight == 0 && myInfo.position.column != 12 ||
					(countRight == 0 &&myInfo.position.row == 12 && myInfo.position.column != 12)) { //�Ʒ��� ������
				
				return DirectionCode.Right;
			}
			
			else if(countRight >= 1 && countUp == 0 && myInfo.position.row != 0 ||
					countUp == 0 && myInfo.position.column == 12 && myInfo.position.row != 0) { // �����ʿ� ������
			
				return DirectionCode.Up;
			}
			
		return directx[0];
	}
	
	DirectionCode WatdaGatda(){
		if(noone == 0) {
			if(myInfo.position.row == 0) {
				return DirectionCode.Down;
			}
			++noone;
			return DirectionCode.Up;
		}
		else {
			if(myInfo.position.row == 12){
				return DirectionCode.Up;
			}
			
			noone = 0;
			return DirectionCode.Down;
		}
	}
			
	
	
	@Override
	public DirectionCode Survivor_Move() // Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
	{
		
		directx[0] = WatdaGatda();
		
		int[] numberOfInfected = new int[13];
		int rowA = myInfo.position.row;
		int columnA = myInfo.position.column;
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		Allahu_Akbar();
		
		if(myInfo.position.row == 0){
			row = 1;
		}
		if(myInfo.position.row == 12){
			row = 11;
		}
		if(myInfo.position.column == 0){
			column = 1;
		}
		if(myInfo.position.column == 12){
			column = 11;
		}

		countRight = cells[row][column + 1].CountIf_Players(
			player -> player.state == StateCode.Infected );
		countDown = cells[row + 1][column].CountIf_Players(
				player -> player.state == StateCode.Infected );
		countLeft = cells[row][column - 1].CountIf_Players(
				player -> player.state == StateCode.Infected );
		countUp = cells[row - 1][column].CountIf_Players(
				player -> player.state == StateCode.Infected );

		// 0
				rowA -= 2;
				
				if ( rowA >= 0 )
					numberOfInfected[0] = cells[rowA][columnA].CountIf_Players(
							player -> player.state == StateCode.Infected );
				
				// 1, 2, 3
				++rowA;
				
				if ( rowA >= 0 )
				{
					if ( columnA >= 1 )
						numberOfInfected[1] = cells[rowA][columnA - 1].CountIf_Players(
								player -> player.state == StateCode.Infected );
					
					numberOfInfected[2] = cells[rowA][columnA].CountIf_Players(
							player -> player.state == StateCode.Infected );
					
					if ( columnA < Constants.Classroom_Width - 1 )
						numberOfInfected[3] = cells[rowA][columnA + 1].CountIf_Players(
								player -> player.state == StateCode.Infected );
				}
				
				// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
				++rowA;
				
				if ( columnA >= 1 )
				{
					numberOfInfected[5] = cells[rowA][columnA - 1].CountIf_Players(
							player -> player.state == StateCode.Infected );
					
					if ( columnA >= 2 )
						numberOfInfected[4] = cells[rowA][columnA - 2].CountIf_Players(
								player -> player.state == StateCode.Infected );
				}
				
				if ( columnA < Constants.Classroom_Width - 1 )
				{
					numberOfInfected[7] = cells[rowA][columnA + 1].CountIf_Players(
							player -> player.state == StateCode.Infected );
					
					if ( columnA < Constants.Classroom_Width - 2 )
						numberOfInfected[8] = cells[rowA][columnA + 2].CountIf_Players(
								player -> player.state == StateCode.Infected );
				}
				
				// 9, A, B
				++rowA;
				
				if ( rowA < Constants.Classroom_Height)
				{
					if ( columnA >= 1 )
						numberOfInfected[9] = cells[rowA][columnA - 1].CountIf_Players(
								player -> player.state == StateCode.Infected );
					
					numberOfInfected[10] = cells[rowA][columnA].CountIf_Players(
							player -> player.state == StateCode.Infected );
					
					if ( columnA < Constants.Classroom_Width - 1 )
						numberOfInfected[11] = cells[rowA][columnA + 1].CountIf_Players(
								player -> player.state == StateCode.Infected );		
				}
				
				// C
				++rowA;
				
				if ( rowA < Constants.Classroom_Height)
					numberOfInfected[12] = cells[rowA][columnA].CountIf_Players(
							player -> player.state == StateCode.Infected );
	
		
		if(turnInfo.turnNumber <= 10) {   //���� �� 10�� ������ ���ڸ��� �Դٰ���
			return directx[0];
		}	
		if(turnInfo.turnNumber >= 15) {
			return Scout();
		}

		
		if(countUp == 0 && countLeft == 0 && countDown == 0 && countRight == 0) {		
		
		
		// 4���� ����(������ ���� �켱������ ����)�� ���� �÷��̾� �� �ջ�		
		int[] weights = new int[4];

		for ( int iWeights = 0; iWeights < 4; iWeights++ )
		{
			switch ( directions[iWeights] )
			{
			case Up:
				// ��: 0123
				weights[iWeights] = numberOfInfected[0] + numberOfInfected[1] + numberOfInfected[2] + numberOfInfected[3];
				break;
			case Left:
				// ����: 1459
				weights[iWeights] = numberOfInfected[1] + numberOfInfected[4] + numberOfInfected[5] + numberOfInfected[9];
				break;
			case Right:
				// ������: 378B
				weights[iWeights] = numberOfInfected[3] + numberOfInfected[7] + numberOfInfected[8] + numberOfInfected[11];
				break;
			default:
				// �Ʒ�: 9ABC
				weights[iWeights] = numberOfInfected[9] + numberOfInfected[10] + numberOfInfected[11] + numberOfInfected[12];
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
								} // ���� ������ �κ�			

				
		
		return GetMovableAdjacentDirection();
	}

	
	@Override
	public void Corpse_Stay()
	{
		Allahu_Akbar();
		{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
		}
	}


	
	@Override
	public DirectionCode Infected_Move()
	{	
		Allahu_Akbar();
		
		directx[0] = DirectionCode.Stay;
		
		int row = myInfo.position.row;
		int column = myInfo.position.column;

		if(myInfo.position.row == 0){
			row = 1;
		}
		if(myInfo.position.row == 12){
			row = 11;
		}
		if(myInfo.position.column == 0){
			column = 1;
		}
		if(myInfo.position.column == 12){
			column = 11;
		}
		
		int count = cells[myInfo.position.row][myInfo.position.column].CountIf_Players(
				player -> player.state == StateCode.Corpse); 
		countRight = cells[row][column + 1].CountIf_Players(
				player -> player.state == StateCode.Corpse );
		countDown = cells[row + 1][column].CountIf_Players(
					player -> player.state == StateCode.Corpse );
		countLeft = cells[row][column - 1].CountIf_Players(
					player -> player.state == StateCode.Corpse );
		countUp = cells[row - 1][column].CountIf_Players(
					player -> player.state == StateCode.Corpse );
		// �ֺ��� ��ü�� ������ ��ȭ�õ�
		if(count == 0) {
			return DirectionCode.Stay;
		}
		else
		return GetMovableAdjacentDirection();		
				// �ƴϸ� ����
				
	}

	@Override
	public void Soul_Stay()
	{
		Allahu_Akbar();
		
		Init_Data();{
			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�.
			 * 		 �� if���� ������ 0��°���� ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�. 
			 */
		}
	}

	@Override
	public Point Soul_Spawn()
	{	
		Allahu_Akbar();
		
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
		
		
		if (turnInfo.turnNumber <= 20) {
			if(turnInfo.turnNumber == 0) {
				return favoritePoint;
			}
			int min_row = -1;
			int min_column = -1;
			int min_count = 1;
			int mi_distance = Constants.Classroom_Width * Constants.Classroom_Height;
			
			for ( int row = 0; row < Constants.Classroom_Height; row++ )
			{
				for ( int column = 0; column < Constants.Classroom_Width; column++ )
				{
					int count = cells[row][column].CountIf_Players(
							player -> player.state == StateCode.Infected);
					int distance = favoritePoint.GetDistance(row, column);

					// �÷��̾� ���� �� ���ٸ� �׻� ����
					if ( count < min_count )
					{
						min_row = row;
						min_column = column;
						min_count = count;
						mi_distance = distance;
					}
					// �÷��̾� ���� ������ ��ȣ�ϴ� ĭ�� �� ����� ĭ�� ����
					else if ( count == min_count )
					{
						// �Ÿ��� �� ������ ����
						if ( distance < mi_distance )
						{
							min_row = row;
							min_column = column;
							mi_distance = distance;
						}
						// �Ÿ����� ������ �� �����ϴ� ������ ����
						else if ( distance == mi_distance )
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
		else
		
	
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
		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		
		
		
		return new Point(max_row, max_column);
	}
}
