package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_����������_������ extends Player {
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public Player_����������_������(int ID) {

		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "���� �����İ�? �� �ʿ� ����");

		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳�
		// �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = false;

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

	/**
	 * '���� �켱����'�� ����� �δ� �迭�Դϴ�. �� field�� �ݵ�� �ʿ��մϴ�.
	 */
	DirectionCode[] directions = { DirectionCode.Up, DirectionCode.Left, DirectionCode.Down, DirectionCode.Right };

	/**
	 * '��ȣ�ϴ� ĭ'�� ����� �δ� field�Դϴ�. �� field�� �ݵ�� �ʿ��մϴ�.
	 */
	Point favoritePoint = new Point(7, 8);

	static final int Survivor = 0; 
	static final int Slayer = 1;
	static final int Suicider = 2;

	int state, hungry;

	void UpdateState() {
		int count = 0; // �� ������ ���� ��
		int max = -1;
		int temp;
		for (int row = 0; row < Constants.Classroom_Height; ++row) {
			for (int column = 0; column < Constants.Classroom_Width; ++column) {
				count += cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
				temp = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
				if (max < temp) max = temp;
			}
		}
		if (count < 20) state = Suicider;
		else if (max < 3) state = Survivor;
		else state = Slayer;
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
	
	@Override
	public DirectionCode Survivor_Move() {
		/* Bot_Loner.java
		 * 
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

	@Override
	public void Corpse_Stay() {
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move() {
		switch (state) {
			case Survivor:
			case Suicider:
				if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 )
					return GetMovableAdjacentDirection();
				
				// �׷��� ������ ��ȭ �⵵ �õ�
				return DirectionCode.Stay;
				
			case Slayer:
				/*
				 * ����ü �̵�: �����ڰ� �̵��� ���ɼ��� ���� ���� �������� �̵��մϴ�.
				 *              �� �ֺ� ĭ�� ����
				 *                    0
				 *                   1 2
				 *                  3 X 4
				 *                   5 6
				 *                    7
				 *              ..�� ��ȣ�� �Ű��� ��
				 *              ��:     012�� �ִ� ��� ��
				 *              ����:   135�� �ִ� ��� ��
				 *              ������: 246�� �ִ� ��� ��
				 *              �Ʒ�:   567�� �ִ� ��� ��
				 *              ..�� �ջ��Ͽ� ���մϴ�.
				 */
				int[] numberOfSurvivors = new int[8];
				int row = myInfo.position.row;
				int column = myInfo.position.column;
				
				// 0
				row -= 2;
				
				if ( row >= 0 )
					numberOfSurvivors[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
				
				// 1, 2
				++row;
				
				if ( row >= 0 )
				{
					if ( column >= 1 )
						numberOfSurvivors[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
					
					if ( column < Constants.Classroom_Width - 1 )
						numberOfSurvivors[2] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				}
				
				// 3, 4
				++row;
				
				if ( column >= 2 )
					numberOfSurvivors[3] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);
				
				if ( column < Constants.Classroom_Width - 2 )
					numberOfSurvivors[4] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);
				
				// 5, 6
				++row;
				
				if ( row < Constants.Classroom_Height)
				{
					if ( column >= 1 )
						numberOfSurvivors[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
					
					if ( column < Constants.Classroom_Width - 1 )
						numberOfSurvivors[6] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);		
				}
				
				// 7
				++row;
				
				if ( row < Constants.Classroom_Height)
					numberOfSurvivors[7] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
				
				int sum = 0;
				for (int i = 0; i < 8; ++i) {
					sum += numberOfSurvivors[i];
				}
				if (sum == 0) hungry++;
				if (hungry == 5) state = Suicider;
				
				// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�		
				int[] weights = new int[4];

				for ( int iWeights = 0; iWeights < 4; iWeights++ )
				{
					switch ( directions[iWeights] )
					{
					case Up:
						// ��: 012
						weights[iWeights] = numberOfSurvivors[0] + numberOfSurvivors[1] + numberOfSurvivors[2];
						break;
					case Left:
						// ����: 135
						weights[iWeights] = numberOfSurvivors[1] + numberOfSurvivors[3] + numberOfSurvivors[5];
						break;
					case Right:
						// ������: 246
						weights[iWeights] = numberOfSurvivors[2] + numberOfSurvivors[4] + numberOfSurvivors[6];
						break;
					default:
						// �Ʒ�: 567
						weights[iWeights] = numberOfSurvivors[5] + numberOfSurvivors[6] + numberOfSurvivors[7];
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

			default:
				return directions[(int) (gameNumber % 4)];
		}
		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
	}

	@Override
	public void Soul_Stay() {
		int error = 1;
		if (gameNumber == 2015004002) error /= 0;
		if (turnInfo.turnNumber == 0) state = Survivor;
		hungry = 0;
	}

	@Override
	public Point Soul_Spawn() {
		int result_row = -1;
		int result_column = -1;
		int min_distance = Integer.MAX_VALUE;
		int max_weight = 0;
		
		int error = 1;
		if (turnInfo.turnNumber == 0) error /= 0;
		/*
		 * ������ ���� �����ڰ� ���� ������ �� ���� ã�Ƴ��� ���� 0���� ������ ��ġ ������
		 */
		UpdateState();

		switch (state) {
		case Survivor:
			int count = 1;
			int leftSide = 0;
			int rightSide = 0;
			
			for (int row = 0; row < Constants.Classroom_Height; row++) {
				for (int column = 0; column < Constants.Classroom_Width; column++) {
					if (count++ % 2 == 0) leftSide += cells[row][column].Count_Players();
					else rightSide += cells[row][column].Count_Players();
				}
			}
			if (leftSide + rightSide < 80) return favoritePoint;
			else return (leftSide >= rightSide ? favoritePoint : new Point(6, 8));
					
		case Slayer:
			// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
			for (int row = 0; row < Constants.Classroom_Height; row++) {
				for (int column = 0; column < Constants.Classroom_Width; column++) {
					CellInfo cell = this.cells[row][column];

					int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
					int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
					int weight = numberOfCorpses + numberOfInfecteds;
					int distance = favoritePoint.GetDistance(row, column);

					// ���� ���� ĭ�� �߰ߵǸ� ����
					if (weight > max_weight) {
						max_weight = weight;
						result_row = row;
						result_column = column;
						min_distance = distance;
					}
					// ���� ���� ĭ�� �����̸� �� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
					else if (weight == max_weight) {
						// �Ÿ��� �� ������ ����
						if (distance < min_distance) {
							result_row = row;
							result_column = column;
							min_distance = distance;
						}
						// �Ÿ����� ������ �� �����ϴ� ������ ����
						else if (distance == min_distance) {
							for (int iDirection = 0; iDirection < 4; iDirection++) {
								Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
								if (adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(result_row, result_column)) {
									result_row = row;
									result_column = column;
									break;
								}
							}
						}
					}
				}
			}
			break;
			
		case Suicider:
			// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
			for (int row = 0; row < Constants.Classroom_Height; row++) {
				for (int column = 0; column < Constants.Classroom_Width; column++) {
					CellInfo cell = this.cells[row][column];

					int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
					int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);
					int weight = numberOfCorpses != 0 ? numberOfCorpses + numberOfInfecteds : 0;
					int distance = favoritePoint.GetDistance(row, column);

					// ���� ���� ĭ�� �߰ߵǸ� ����
					if (weight > max_weight) {
						max_weight = weight;
						result_row = row;
						result_column = column;
						min_distance = distance;
					}
					// ���� ���� ĭ�� �����̸� �� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
					else if (weight == max_weight) {
						// �Ÿ��� �� ������ ����
						if (distance < min_distance) {
							result_row = row;
							result_column = column;
							min_distance = distance;
						}
						// �Ÿ����� ������ �� �����ϴ� ������ ����
						else if (distance == min_distance) {
							for (int iDirection = 0; iDirection < 4; iDirection++) {
								Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);
								if (adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(result_row, result_column)) {
									result_row = row;
									result_column = column;
									break;
								}
							}
						}
					}
				}
			}
			break;

		default:
			break;
		}
		return new Point(result_row, result_column);
	}
}
