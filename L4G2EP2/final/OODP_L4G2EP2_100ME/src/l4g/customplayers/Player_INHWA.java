package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_INHWA extends Player {
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
DirectionCode[] directions = new DirectionCode[4];
	
	/**
	 * '��ȣ�ϴ� ĭ'�� ����� �δ� field�Դϴ�.
	 * �� field�� �ݵ�� �ʿ��մϴ�.
	 */
	Point favoritePoint = new Point(0, 0);
	
	/**
	 * '���� �켱����'�� '��ȣ�ϴ� ĭ'�� �����մϴ�.
	 * �� �޼���� Soul_Stay()���� �� �� �� ȣ��˴ϴ�.
	 * �� �޼���� �ݵ�� �ʿ��մϴ�.
	 */
	void Init_Data()
	{
		/*
		 * �������� �ۼ��ϴ� �÷��̾�� ������ ���ǽǿ� �� �� �� �����ϹǷ�
		 * ��� �Ʒ��� �ڵ带 �� �ʿ� ����
		 * 
		 * directions[0] = DirectionCode.Up;
		 * directions[1] = DirectionCode.Left;
		 * directions[2] = DirectionCode.Right;
		 * directions[3] = DirectionCode.Down;
		 * 
		 * favoritePoint.row = 6;
		 * favoritePoint.column = 6;
		 * 
		 * ...�� ���� �������� �����ϴ� �켱 ������ �׳� �ٷ� �Ҵ��� �ᵵ �����մϴ�.
		 * (Bot���� �Ȱ��� Ŭ������ �ν��Ͻ��� ���� �����ϹǷ� �̷� ���� �ؾ� �մϴ�).
		 */
		
		// �÷��̾�� �ٸ� ID, ���Ӹ��� �ٸ� ���� ��ȣ�� �����Ͽ� ���� �̻��� ���� �����
		long seed = (ID + 2016) * gameNumber + ID;
		
		if ( seed < 0 )
			seed = -seed;
		
		// �� ���� 24�� ���� �������� ���� ���� �켱���� ����
		switch ( (int)(seed % 24) )
		{
		case 0:
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Down;
			break;
		case 1:
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Right;
			break;
		case 2:
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Down;
			break;
		case 3:
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Left;
			break;
		case 4:
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Right;
			break;
		case 5:
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Left;
			break;
		case 6:
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Down;
			break;
		case 7:
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Right;
			break;
		case 8:
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Down;
			break;
		case 9:
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Up;
			break;
		case 10:
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Right;
			break;
		case 11:
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Up;
			break;
		case 12:
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Down;
			break;
		case 13:
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Left;
			break;
		case 14:
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Down;
			break;
		case 15:
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Up;
			break;
		case 16:
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Left;
			break;
		case 17:
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Up;
			break;
		case 18:
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Right;
			break;
		case 19:
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Left;
			break;
		case 20:
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Right;
			break;
		case 21:
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Up;
			break;
		case 22:
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Left;
			break;
		case 23:
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Up;
			break;
		}
		
		favoritePoint.row = (int)(seed / Constants.Classroom_Width % Constants.Classroom_Height);
		favoritePoint.column = (int)(seed % Constants.Classroom_Height);
	}
	
	/**
	 * ���� �켱������ ����Ͽ�, ���� �̵� ������ ������ �ϳ� ��ȯ�մϴ�.
	 * �� �޼���� �ݵ�� �ʿ��մϴ�.
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
		
	public Player_INHWA(int ID) {

		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "���");

		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳�
		// �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = true;

		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ����
		// ���ƿɽô�.

	}

	/*
	 * TODO#5 ���� �������� �׷� �� ��Ʈ�� ���� �Ʒ��� �ִ� �ټ� ���� �ǻ� ���� �޼��带 �ϼ��ϼ���. �翬�� �� �濡 �� ��
	 * ������, �߰��߰� �ڵ带 ����� �δ� ���� ������, �ڵ� �ۼ��� ����� �� �ƹ� �δ� ���� ������ ã�� ������.
	 * 
	 * L4G�� �������� '����'�� �߱��ϴ� ������ ���� ������ ������ �ƴմϴ�!
	 * 
	 * �������� �̹� �������� ������ �ð���ŭ, ���� �ٸ� ���� / �ٸ� �������� ������ ���̴� �ð��� �پ��� �� ���Դϴ�. �׷���
	 * �ڽ��� ���� ���� ������ ���÷ȴٸ�, �̸� �� �÷��̾ �����ϱ� ���� �Ƴ� ���� ����� ������ ������!
	 * 
	 * ��������� �Ǿ� Ȳ���� ������ ���ε��ϰ� Eclipse�� ���ƿ� �������� �ۼ��� �ڵ带 ���� ����, '�ڵ忡 ����̶��� ���� ��
	 * ���� �ֱ���'��� ������ ���� ��� �� ���Դϴ�.
	 */

	@Override
	public DirectionCode Survivor_Move() {
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
	public void Corpse_Stay() {
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move() {
		if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
			return GetMovableAdjacentDirection();
		
		// �׷��� ������ ��ȭ �⵵ �õ�
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay() {
		if (turnInfo.turnNumber == 0) {
			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�. �� if���� ������ 0��°����
			 * ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�.
			 */
			Init_Data();
		}
	}

	@Override
	public Point Soul_Spawn() {
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

}
