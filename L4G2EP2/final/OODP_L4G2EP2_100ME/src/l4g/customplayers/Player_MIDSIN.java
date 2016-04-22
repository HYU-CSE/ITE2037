package l4g.customplayers;

import java.awt.HeadlessException;

import l4g.common.*;
import l4g.data.CellInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_MIDSIN extends Player {
	/**
	 * '���� �켱����'�� ����� �δ� �迭�Դϴ�. �� field�� �ݵ�� �ʿ��մϴ�.
	 */
	DirectionCode[] directions = new DirectionCode[4];

	/**
	 * '��ȣ�ϴ� ĭ'�� ����� �δ� field�Դϴ�. �� field�� �ݵ�� �ʿ��մϴ�.
	 */
	Point favoritePoint = new Point(0, 0);

	/**
	 * '���� �켱����'�� '��ȣ�ϴ� ĭ'�� �����մϴ�. �� �޼���� Soul_Stay()���� �� �� �� ȣ��˴ϴ�. �� �޼���� �ݵ��
	 * �ʿ��մϴ�.
	 */
	void Init_Data() {
		/*
		 * �������� �ۼ��ϴ� �÷��̾�� ������ ���ǽǿ� �� �� �� �����ϹǷ� ��� �Ʒ��� �ڵ带 �� �ʿ� ����
		 * 
		 * directions[0] = DirectionCode.Up; directions[1] = DirectionCode.Left;
		 * directions[2] = DirectionCode.Right; directions[3] =
		 * DirectionCode.Down;
		 * 
		 * favoritePoint.row = 6; favoritePoint.column = 6;
		 * 
		 * ...�� ���� �������� �����ϴ� �켱 ������ �׳� �ٷ� �Ҵ��� �ᵵ �����մϴ�. (Bot���� �Ȱ��� Ŭ������ �ν��Ͻ��� ����
		 * �����ϹǷ� �̷� ���� �ؾ� �մϴ�).
		 */
		directions[0] = DirectionCode.Up;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Down;

		// �߾��� ������.
		favoritePoint.row = Constants.Classroom_Height / 2;
		favoritePoint.column = Constants.Classroom_Width / 2;
	}

	/**
	 * ���� �켱������ ����Ͽ�, ���� �̵� ������ ������ �ϳ� ��ȯ�մϴ�. �� �޼���� �ݵ�� �ʿ��մϴ�.
	 */
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

	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public Player_MIDSIN(int ID) {

		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "OOPArts.Midsin");

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
		/*
		 * ������ �̵�: ������ ���� ���� ���� �������� �̵��մϴ�. �������� �þ� ������ 0 123 45678 9AB C ..�� ��
		 * ��: 0123�� �ִ� ������ �� ����: 1459�� �ִ� ������ �� ������: 378B�� �ִ� ������ �� �Ʒ�: 9ABC�� �ִ�
		 * ������ �� ..�� �ջ��Ͽ� ���մϴ�.
		 */
		int[] numberOfPlayers = new int[13];
		int[] numberOfInfected = new int[13];

		int row = myInfo.position.row;
		int column = myInfo.position.column;

		// ���� ���̴� 13���� ��쿡 ���� ������ �� ���

		// 0
		row -= 2;

		if (row >= 0) {
			numberOfPlayers[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfected[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// 1, 2, 3
		++row;

		if (row >= 0) {
			if (column >= 1)
				numberOfPlayers[1] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);

			numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

			if (column < Constants.Classroom_Width - 1)
				numberOfPlayers[3] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
		}

		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
		++row;

		if (column >= 1) {
			numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);

			if (column >= 2) {
				numberOfPlayers[4] = cells[row][column - 2]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfected[4] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
			}
		}

		if (column < Constants.Classroom_Width - 1) {
			numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);

			if (column < Constants.Classroom_Width - 2) {
				numberOfPlayers[8] = cells[row][column + 2]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfected[8] = cells[row][column + 2]
						.CountIf_Players(player -> player.state == StateCode.Infected);
			}
		}

		// 9, A, B
		++row;

		if (row < Constants.Classroom_Height) {
			if (column >= 1)
				numberOfPlayers[9] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);

			numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

			if (column < Constants.Classroom_Width - 1)
				numberOfPlayers[11] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
		}

		// C
		++row;

		if (row < Constants.Classroom_Height) {
			numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfected[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�
		int[] weights = new int[4];

		for (int iWeights = 0; iWeights < 4; iWeights++) {
			switch (directions[iWeights]) {
			case Up:
				// ��: 0123
				weights[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2] + numberOfPlayers[3]
						- numberOfInfected[0] * 10;
				break;
			case Left:
				// ����: 1459
				weights[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5] + numberOfPlayers[9]
						- numberOfInfected[4] * 10;
				break;
			case Right:
				// ������: 378B
				weights[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8] + numberOfPlayers[11]
						- numberOfInfected[8] * 10;
				break;
			default:
				// �Ʒ�: 9ABC
				weights[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11] + numberOfPlayers[12]
						- numberOfInfected[12] * 10;
				break;
			}
		}

		// ������ ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
		int max_weight = -10000;
		int max_idx_weights = 0;

		for (int iWeights = 0; iWeights < 4; iWeights++) {
			if (weights[iWeights] > max_weight) {
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);

				if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width) {
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
		DirectionCode result = DirectionCode.Stay;
		
		int row = myInfo.position.row;
		int column = myInfo.position.column;
		int maxCorpseCount = -1;
		int checkMovablePlace = 1;

		// �� �ؿ� ��ü�� ��� ������ ������
		if ( this.cells[this.myInfo.position.row][this.myInfo.position.column].CountIf_Players(player->player.state==StateCode.Corpse) > 0 ) {
			// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
			int[] numberOfCorpses = {-1, -1, -1, -1};
			
			if (row > 0)
				numberOfCorpses[0] = cells[row - 1][column].CountIf_Players(player -> player.state == StateCode.Corpse);
	
			if (column > 0)
				numberOfCorpses[1] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Corpse);
	
			if (column < Constants.Classroom_Width - 1)
				numberOfCorpses[2] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Corpse);
	
			if (row < Constants.Classroom_Height - 1)
				numberOfCorpses[3] = cells[row + 1][column].CountIf_Players(player -> player.state == StateCode.Corpse);

			// ��ü���°��� �ִٸ� ������
			if(numberOfCorpses[0] == 0) {
				return DirectionCode.Up;
			}
			if(numberOfCorpses[1] == 0) {
				return DirectionCode.Left;
			}
			if(numberOfCorpses[2] == 0) {
				return DirectionCode.Right;
			}
			if(numberOfCorpses[3] == 0) {
				return DirectionCode.Down;
			}
			
			// ��ü�� ��濡 ������ �߾����� ��
			int maxDistance = -1;
			Math.abs(maxDistance);
			if(row > Constants.Classroom_Height / 2) {
				maxDistance = row - Constants.Classroom_Height / 2;
				result = DirectionCode.Up;
			} else {
				maxDistance = Constants.Classroom_Height / 2 - row;	
				result = DirectionCode.Down;			
			}
			
			if(column > Constants.Classroom_Width / 2 && (column - Constants.Classroom_Height / 2 > maxDistance)) {
				result = DirectionCode.Left;
			} else if (column < Constants.Classroom_Width / 2 && (Constants.Classroom_Height / 2 - column > maxDistance)) {
				result = DirectionCode.Right;
			}
		}
	
		return result;
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
		int min_row = -1;
		int min_column = -1;
		int min_count = 1;

		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;

		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

		if (turnInfo.turnNumber > Constants.Max_Turn_Numbers / 2) {

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
			int NumberOfInfested = cells[max_row][max_column]
					.CountIf_Players(player -> player.state == StateCode.Infected);

			if (NumberOfInfested > 10) {
				return new Point(max_row, max_column);
			}
		}

		for (int row = 0; row < Constants.Classroom_Height; row++) {
			for (int column = 0; column < Constants.Classroom_Width; column++) {
				int count = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
				int distance = favoritePoint.GetDistance(row, column);

				if (cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected) > 0) {
					continue;
				}

				// �÷��̾� ���� �� ���ٸ� �׻� ����
				if (count > min_count) {
					min_row = row;
					min_column = column;
					min_count = count;
					min_distance = distance;
				}
				// �÷��̾� ���� ������ ��ȣ�ϴ� ĭ�� �� ����� ĭ�� ����
				else if (count == min_count) {
					// �Ÿ��� �� ������ ����
					if (distance < min_distance) {
						min_row = row;
						min_column = column;
						min_distance = distance;
					}
					// �Ÿ����� ������ �� �����ϴ� ������ ����
					else if (distance == min_distance) {
						for (int iDirection = 0; iDirection < 4; iDirection++) {
							Point adjacentPoint = favoritePoint.GetAdjacentPoint(directions[iDirection]);

							if (adjacentPoint.GetDistance(row, column) < adjacentPoint.GetDistance(min_row,
									min_column)) {
								min_row = row;
								min_column = column;
								break;
							}
						}

						// ������� �Դٸ� ���� �׸� ���� ����
					}
				}
			}
		}

		return new Point(min_row, min_column);
	}
}
