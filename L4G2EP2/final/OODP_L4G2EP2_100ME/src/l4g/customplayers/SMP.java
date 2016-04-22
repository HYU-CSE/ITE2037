package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class SMP extends Player {
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public SMP(int ID) {

		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "SMP");

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

	
	
	DirectionCode[] directions = new DirectionCode[4];
	Point favoritePoint = new Point(0, 0);

	
	void Init_Data()
	{
		directions[0] = DirectionCode.Up;
		  directions[1] = DirectionCode.Left;
		  directions[2] = DirectionCode.Right;
		  directions[3] = DirectionCode.Down;
		  
		  favoritePoint.row = 6;
		  favoritePoint.column = 6;
		
	
	}
	
	
	DirectionCode GetMovableAdjacentDirection() {
		int iDirection;

		for (iDirection = 0; iDirection < 4; iDirection++) {
			Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iDirection]);

			if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height && adjacentPoint.column >= 0
					&& adjacentPoint.column < Constants.Classroom_Width)
				break;
		}

		return directions[iDirection];
	}

	// ��Ƴ����� point�� ������ ���̴� �ֵ��� ������ ���� * ( ��ü+������) ������ �˷��ش�.
	int MaxGwanchalJa(Point point) {

		int row = point.row;
		int column = point.column;
		int liveplayer, infectedplayer, copseplayer;
		liveplayer = 0;
		infectedplayer = 0;
		copseplayer = 0;

		if ((cells[point.row][point.column].CountIf_Players(player -> player.state == StateCode.Infected) > 0)) {
			return 0;

		}
		if ((cells[point.row][point.column].CountIf_Players(player -> player.state == StateCode.Corpse) > 0)) {
			return 0;

		}

		// ���� ���̴� 13���� ��쿡 ���� �÷��̾� �� ���

		// 0
		row -= 2;

		if (row >= 0) {
			infectedplayer += cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			liveplayer += cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			copseplayer += cells[row][column].CountIf_Players(player -> player.state == StateCode.Corpse);
		}
		// 1, 2, 3
		++row;

		if (row >= 0) {
			if (column >= 1) {
				infectedplayer += cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);
				liveplayer += cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
				copseplayer += cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Corpse);

				infectedplayer += cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
				liveplayer += cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
				copseplayer += cells[row][column].CountIf_Players(player -> player.state == StateCode.Corpse);
			}

			if (column < Constants.Classroom_Width - 1) {
				infectedplayer += cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);

				liveplayer += cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);

				copseplayer += cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
		}

		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
		++row;

		if (column >= 1) {
			infectedplayer += cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);

			liveplayer += cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);

			copseplayer += cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Corpse);
			if (column >= 2) {
				infectedplayer += cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Infected);

				liveplayer += cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);

				copseplayer += cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
		}

		if (column < Constants.Classroom_Width - 1) {
			infectedplayer += cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);

			liveplayer += cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);

			copseplayer += cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Corpse);
			if (column < Constants.Classroom_Width - 2) {
				infectedplayer += cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Infected);

				liveplayer += cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);

				copseplayer += cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
		}

		// 9, A, B
		++row;

		if (row < Constants.Classroom_Height) {
			if (column >= 1) {
				infectedplayer += cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);

				liveplayer += cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);

				copseplayer += cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Corpse);

				infectedplayer += cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

				liveplayer += cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

				copseplayer += cells[row][column].CountIf_Players(player -> player.state == StateCode.Corpse);
			}

			// TODO ������� �ٽ� �ٲٸ� �� �ФФ�

			if (column < Constants.Classroom_Width - 1) {
				infectedplayer += cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);

				liveplayer += cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);

				copseplayer += cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Corpse);
			}
		}

		// C
		++row;

		if (row < Constants.Classroom_Height) {
			infectedplayer += cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			liveplayer += cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

			copseplayer += cells[row][column].CountIf_Players(player -> player.state == StateCode.Corpse);
		}
		return liveplayer * (copseplayer + infectedplayer);
	}

	int BoonsuckJoobueun[] = new int[9];

	int[] numberOfPlayers = new int[13];

	public void BoonSuck() {

		for (int j = 0; j < BoonsuckJoobueun.length; j++) {
			BoonsuckJoobueun[j] = 0;
		}
		for (int j = 0; j < numberOfPlayers.length; j++) {
			numberOfPlayers[j] = 0;
		}

		/*
		 * Boonsuck�ȿ��� �����ڰ� ������ 1 ������ 0, �״��� ĭ���� ������ 2
		 * 
		 * 
		 */
		int row = myInfo.position.row;
		int column = myInfo.position.column;

		// ���� ���̴� 13���� ��쿡 ���� �÷��̾� �� ���

		// 0
		row -= 2;

		if (row >= 0)
			numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

		// 1, 2, 3
		++row;

		if (row >= 0) {
			if (column >= 1)
				numberOfPlayers[1] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
			numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column < Constants.Classroom_Width - 1)
				numberOfPlayers[3] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
		++row;

		if (column >= 1) {
			numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column >= 2)
				numberOfPlayers[4] = cells[row][column - 2]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		if (column < Constants.Classroom_Width - 1) {
			numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column < Constants.Classroom_Width - 2)
				numberOfPlayers[8] = cells[row][column + 2]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// 9, A, B
		++row;

		if (row < Constants.Classroom_Height) {
			if (column >= 1)
				numberOfPlayers[9] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);

			numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column < Constants.Classroom_Width - 1)
				numberOfPlayers[11] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// C
		++row;

		if (row < Constants.Classroom_Height)
			numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

		for (int index = 0; index < numberOfPlayers.length; index++) {

			if ((index == 0 && numberOfPlayers[index] != 0)) {

				BoonsuckJoobueun[1]++;
			}
			if ((index == 2 && numberOfPlayers[index] != 0)) {
				BoonsuckJoobueun[1]++;
			}
			if ((index == 4 && numberOfPlayers[index] != 0)) {
				BoonsuckJoobueun[3]++;
			}
			if ((index == 5 && numberOfPlayers[index] != 0)) {
				BoonsuckJoobueun[3]++;
			}
			if ((index == 7 && numberOfPlayers[index] != 0)) {
				BoonsuckJoobueun[5]++;
			}
			if ((index == 8 && numberOfPlayers[index] != 0)) {
				BoonsuckJoobueun[5]++;
			}
			if ((index == 10 && numberOfPlayers[index] != 0)) {
				BoonsuckJoobueun[7]++;
			}
			if ((index == 12 && numberOfPlayers[index] != 0)) {
				BoonsuckJoobueun[7]++;
			}

		}
	}

	@Override
	public DirectionCode Survivor_Move() {
		DirectionCode result = null;
		Point_Immutable position = myInfo.position;

		// Note: �� �޼���� ������ ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		// �м��� �Ѵ�!
		BoonSuck();

		// ������ ����� ����1!!{
		if (numberOfPlayers[0] == 0 && numberOfPlayers[1] == 0 && numberOfPlayers[2] == 0 && numberOfPlayers[3] == 0
				&& numberOfPlayers[4] == 0 && numberOfPlayers[5] == 0 && numberOfPlayers[6] == 0
				&& numberOfPlayers[7] == 0 && numberOfPlayers[8] == 0 && numberOfPlayers[9] == 0
				&& numberOfPlayers[10] == 0 && numberOfPlayers[11] == 0 && numberOfPlayers[12] == 0) {
			// �߾��̴�
			if (position.column == (Constants.Classroom_Width - 1) / 2
					&& position.row == (Constants.Classroom_Height - 1) / 2) {
				result = DirectionCode.Right;
			}

			// �߾��� �ƴϴ�

			else {
				if ((Constants.Classroom_Width - 1) / 2 - position.column > 0) {
					result = DirectionCode.Right;

				} else if ((Constants.Classroom_Width - 1) / 2 - position.column < 0) {
					result = DirectionCode.Left;

				} else {
					if ((Constants.Classroom_Height - 1) / 2 - position.row > 0) {
						result = DirectionCode.Up;

					} else if ((Constants.Classroom_Height - 1) / 2 - position.row < 0) {
						result = DirectionCode.Down;

					}

				}
			}
			// }
		}

		// �����Ȼ���� ���δ�!!!!{
		else {

			// �����ְ� �� �������� ����� ������
			{
				if (BoonsuckJoobueun[1] == 1 && numberOfPlayers[1] == 0 && numberOfPlayers[3] == 0) {

					return result = DirectionCode.Up;
				}
				if (BoonsuckJoobueun[3] == 1 && numberOfPlayers[1] == 0 && numberOfPlayers[9] == 0) {

					return result = DirectionCode.Left;
				}
				if (BoonsuckJoobueun[5] == 1 && numberOfPlayers[3] == 0 && numberOfPlayers[11] == 0) {
					return result = DirectionCode.Right;
				}
				if (BoonsuckJoobueun[7] == 1 && numberOfPlayers[9] == 0 && numberOfPlayers[11] == 0) {
					return result = DirectionCode.Down;
				}

			}

			// 4���� ����(������ ���� �켱������ ����)�� ���� �÷��̾� �� �ջ�
			int[] weights = new int[4];

			for (int iWeights = 0; iWeights < 4; iWeights++) {
				switch (directions[iWeights]) {
				case Up:
					// ��: 0123
					weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2]
							+ numberOfPlayers[3];
					break;
				case Left:
					// ����: 1459
					weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5]
							+ numberOfPlayers[9];
					break;
				case Right:
					// ������: 378B
					weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8]
							+ numberOfPlayers[11];
					break;
				default:
					// �Ʒ�: 9ABC
					weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11]
							+ numberOfPlayers[12];
					break;
				}
			}

			// �÷��̾� ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
			int min_weight = Integer.MAX_VALUE;
			int min_idx_weights = 0;

			for (int iWeights = 0; iWeights < 4; iWeights++) {
				if (weights[iWeights] < min_weight) {
					Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);

					if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
							&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width) {
						min_weight = weights[iWeights];
						min_idx_weights = iWeights;
					}
				}
			}

			return directions[min_idx_weights];

		}

		return result;
	}

	@Override
	public void Corpse_Stay() {
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move() {

		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		// ������ ������ �������ϹǷ� ��ȭ�⵵�� �ϱ� ���� �̵��� �Ѵ�.

		// �� �ؿ� ��ü�� ��� ������ ������
		if (this.cells[this.myInfo.position.row][this.myInfo.position.column]
				.CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
			return GetMovableAdjacentDirection();

		// �׷��� ������ ��ȭ �⵵ �õ�
		return DirectionCode.Stay;

	}

	@Override
	public void Soul_Stay() {
		if (turnInfo.turnNumber == 0) {
			//�Ͼ� 100���� 199���� ���� ������ �Ͼ�
		
			if(gameNumber%1000000>100&&gameNumber%1000000<200){
			int haak=0;
			haak= haak/haak;
			}
			
			
			Init_Data();
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Down;

			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�. �� if���� ������ 0��°����
			 * ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�.
			 */
		}

	}

	@Override
	public Point Soul_Spawn() {
		
		//�Ͼ� 99���� �ڻ��̴�
		if(gameNumber%1000000>0&&gameNumber%1000000<100){
			return new Point(144,144);
		}
		if(turnInfo.turnNumber<10){
			
			return new  Point(6,6);
			
			
		}
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

		// ������ ���� ������ ������ ã�Ƽ� ����
		if (turnInfo.turnNumber >= 90 && turnInfo.turnNumber <= 121) {
			// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
			for (int row = 0; row < Constants.Classroom_Height; row++) {
				for (int column = 0; column < Constants.Classroom_Width; column++) {
					CellInfo cell = this.cells[row][column];

					int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
					int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);

					int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
					int distance = favoritePoint.GetDistance(row, column);

					// ���� ���� ĭ�� �߰ߵǸ� ����
					if (weight > max_weight) {
						max_weight = weight;
						max_row = row;
						max_column = column;
						min_distance = distance;
					}
					// ���� ���� ĭ�� �����̸� �� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
					else if (weight == max_weight) {
						// �Ÿ��� �� ������ ����
						if (distance < min_distance) {
							max_row = row;
							max_column = column;
							min_distance = distance;
						}
						// �Ÿ����� ������ �� �����ϴ� ������ ����
						else if (distance == min_distance) {
							for (int iDirection = 0; iDirection < 4; iDirection++) {
								Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);

								if (adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(max_row,
										max_column)) {
									max_row = row;
									max_column = column;
									break;
								}
							}

							// ������� �Դٸ� ���� �׸� ���� ����
						}
					}
				}
			}

			// �˻��ߴµ� ��ü�� ����ü�� �ϳ��� ���ٸ� ��ġ ����
			if (max_weight == 0) {
				int variableToMakeError = 0;

				variableToMakeError = variableToMakeError / variableToMakeError;
			}

			return new Point(max_row, max_column);
		}

		//
		else {

			// ������ �ƴ϶�� ��ü ��Ƽ�ϴ� �� �ֺ����� ������ ��Ȱ�� �Ѵ�.

			int row, column;
			row = Constants.Classroom_Height;
			column = Constants.Classroom_Width;

			Point maxvalue_po = new Point(row, column);
			int maxraw = 0;
			int maxcol = 0;
			int maxvalue = 0;

			for (int rownum = 0; rownum < row; rownum++) {

				for (int colnum = 0; colnum < column; colnum++) {

					maxvalue_po.column = colnum;
					maxvalue_po.row = rownum;

					if (MaxGwanchalJa(maxvalue_po) >= maxvalue) {
						maxraw = maxvalue_po.row;
						maxcol = maxvalue_po.column;
						maxvalue = MaxGwanchalJa(maxvalue_po);
					}

				}

			}
			return new Point(maxraw, maxcol);
		}

	}
}
