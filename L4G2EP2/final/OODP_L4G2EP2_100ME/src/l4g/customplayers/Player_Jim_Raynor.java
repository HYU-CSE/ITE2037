package l4g.customplayers;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_Jim_Raynor extends Player {
	/*
	 * -------------------------------------------------- TODO �� �о� ������!
	 * 
	 * ��� Bot �÷��̾���� ���� �ٸ� �ǻ� ������ �����ϱ� ���� �ڽ��� ID�� ���� ��ȣ�� �̿��Ͽ� ������ '���� �켱����'��
	 * '��ȣ�ϴ� ĭ'�� ���� �ǻ� ������ Ȱ���մϴ�. ���� �������� Bot �÷��̾��� �ڵ带 ������ ����Ϸ��� ��� ���� �۾����� ��
	 * �Բ� �����ؾ� �մϴ�.
	 * 
	 * - �Ʒ��� '�� field, �޼���� �ݵ�� �ʿ��մϴ�'�� ���� �ִ� �͵��� ��� ������ �;� �մϴ�.
	 * 
	 * - Soul_Stay()�� �ִ�, �ʱ�ȭ ���� �ڵ带 ������ �;� �մϴ�.
	 */

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
		directions[0] = DirectionCode.Down;
		directions[1] = DirectionCode.Left;
		directions[2] = DirectionCode.Right;
		directions[3] = DirectionCode.Up;

		favoritePoint.row = 6;
		favoritePoint.column = 6;

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

	public Player_Jim_Raynor(int ID) {
		super(ID, "��â �տ��� �ʵ��ѹ�!�����ѹ�!#" + ID);

		this.trigger_acceptDirectInfection = false;
	}
	public void ChangeDirectionPriority(PlayerInfo myinfo){
		int pos_row = myinfo.position.row;
		int pos_col = myinfo.position.column;
		
		// ���� ���Ϳ� �ִ� ���
		if(0 <= pos_row && pos_row <= 3 && 3 <= pos_col && pos_col <=9){
			directions[0] = DirectionCode.Down;
			directions[1] = DirectionCode.Left;
			directions[2] = DirectionCode.Right;
			directions[3] = DirectionCode.Up;
		}
		// ���� ����
		if(3 <= pos_row && pos_row <= 9 && 9 <= pos_col){
			directions[0] = DirectionCode.Left;
			directions[1] = DirectionCode.Down;
			directions[2] = DirectionCode.Up;
			directions[3] = DirectionCode.Right;
		}
		
		// ���� ����
		if(9 <= pos_row && 3 <= pos_col && pos_col <=9){
			directions[0] = DirectionCode.Up;
			directions[1] = DirectionCode.Right;
			directions[2] = DirectionCode.Left;
			directions[3] = DirectionCode.Down;
		}
		// ���� ����
		if(0 <= pos_col && pos_col <= 3 && 3 <= pos_row && pos_row <=9){
			directions[0] = DirectionCode.Right;
			directions[1] = DirectionCode.Up;
			directions[2] = DirectionCode.Down;
			directions[3] = DirectionCode.Left;
		}
		
			
	};
	
	@Override
	public DirectionCode Survivor_Move() {
		ChangeDirectionPriority(myInfo);
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
			if (column >= 1) {
				numberOfPlayers[1] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfected[1] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
			}

			numberOfPlayers[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfected[2] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column < Constants.Classroom_Width - 1) {
				numberOfPlayers[3] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfected[3] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
			}

		}

		// 4, 5, 7, 8 (6�� ���� ���� �ִ� ĭ)
		++row;

		if (column >= 1) {
			numberOfPlayers[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfected[5] = cells[row][column - 1].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column >= 2) {
				numberOfPlayers[4] = cells[row][column - 2]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfected[4] = cells[row][column - 2]
						.CountIf_Players(player -> player.state == StateCode.Infected);
			}

		}

		if (column < Constants.Classroom_Width - 1) {
			numberOfPlayers[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfected[7] = cells[row][column + 1].CountIf_Players(player -> player.state == StateCode.Infected);

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
			if (column >= 1) {
				numberOfPlayers[9] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfected[9] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
			}

			numberOfPlayers[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfected[10] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);

			if (column < Constants.Classroom_Width - 1) {
				numberOfPlayers[11] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
				numberOfInfected[11] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Infected);
			}

		}

		// C
		++row;

		if (row < Constants.Classroom_Height) {
			numberOfPlayers[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);
			numberOfInfected[12] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Infected);
		}

		// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�
		int[] survivor_weight = new int[4];
		int[] infected_weight = new int[4];

		for (int iWeights = 0; iWeights < 4; iWeights++) {
			switch (directions[iWeights]) {
			case Up:
				// ��: 0123
				survivor_weight[iWeights] = numberOfPlayers[0] + numberOfPlayers[1] + numberOfPlayers[2]
						+ numberOfPlayers[3];
				infected_weight[iWeights] = numberOfInfected[0] + numberOfInfected[1] + numberOfInfected[2]
						+ numberOfInfected[3];
				break;
			case Left:
				// ����: 1459
				survivor_weight[iWeights] = numberOfPlayers[1] + numberOfPlayers[4] + numberOfPlayers[5]
						+ numberOfPlayers[9];
				infected_weight[iWeights] = numberOfInfected[1] + numberOfInfected[4] + numberOfInfected[5]
						+ numberOfInfected[9];
				break;
			case Right:
				// ������: 378B
				survivor_weight[iWeights] = numberOfPlayers[3] + numberOfPlayers[7] + numberOfPlayers[8]
						+ numberOfPlayers[11];
				infected_weight[iWeights] = numberOfInfected[3] + numberOfInfected[7] + numberOfInfected[8]
						+ numberOfInfected[11];
				break;
			default:
				// �Ʒ�: 9ABC
				survivor_weight[iWeights] = numberOfPlayers[9] + numberOfPlayers[10] + numberOfPlayers[11]
						+ numberOfPlayers[12];
				infected_weight[iWeights] = numberOfInfected[9] + numberOfInfected[10] + numberOfInfected[11]
						+ numberOfInfected[12];
				break;
			}

		}

		// ������ ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
		// ����ü�� ���� ������ ��� �ֿ켱������ ����
		int max_weight = -1;
		int max_idx_weights = 0;

		for (int iNoInfected = 0; iNoInfected < 4; iNoInfected++) {
			if (this.turnInfo.turnNumber <= 10)
				break;
			if (infected_weight[iNoInfected] == 0) {
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iNoInfected]);
				
				// ����ü�� �ƿ� ���� ���Ͱ� �߰ߵ� ��� �װ����� �̵�
				if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width) {
					return directions[iNoInfected];
				}
			}
		}

		for (int iWeights = 0; iWeights < 4; iWeights++) {
			if (survivor_weight[iWeights] > max_weight) {
				Point adjacentPoint = myInfo.position.GetAdjacentPoint(directions[iWeights]);

				if (adjacentPoint.row >= 0 && adjacentPoint.row < Constants.Classroom_Height
						&& adjacentPoint.column >= 0 && adjacentPoint.column < Constants.Classroom_Width) {
					max_weight = survivor_weight[iWeights];
					max_idx_weights = iWeights;
				}
			}
		}

		return directions[max_idx_weights];
	}

	@Override
	public void Corpse_Stay() {

	}

	@Override
	public DirectionCode Infected_Move() {
		// �� �ؿ� ��ü�� ��� ������ ������
		if (this.cells[this.myInfo.position.row][this.myInfo.position.column]
				.CountIf_Players(player -> player.state == StateCode.Corpse) > 0)
			return GetMovableAdjacentDirection();

		// �׷��� ������ ��ȭ �⵵ �õ�
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay() {
		if (this.turnInfo.turnNumber == 0) {
			// �� �κ��� Bot �÷��̾� �ڵ带 ������ ����ϱ� ���� �ݵ�� �ʿ��մϴ�.
			Init_Data();
		}
	}

	// ����ü�� �ֺ��� ���� ���� ������ Ž���ϴ� �Լ�(��ü��ź ���� �� ���)
	public Point WhereToGoToBeCorpes() {
		int max_infected_row = favoritePoint.row, max_infected_col = favoritePoint.column;
		int max_infected = 0;
		int infected_num;
		for (int i = 1; i < Constants.Classroom_Height - 1; i++) {
			for (int j = 1; j < Constants.Classroom_Width - 1; j++) {
				CellInfo cell = this.cells[i][j];
				infected_num = 0;
				for (int row_offset = -1; row_offset <= 1; row_offset++) {
					for (int col_offset = -1; col_offset <= 1; col_offset++) {
						infected_num += cell.CountIf_Players(player -> player.state == StateCode.Infected);
					}
				}
				if (infected_num > max_infected) {
					max_infected = infected_num;
					max_infected_row = i;
					max_infected_col = j;
				}
			}
		}
		return new Point(max_infected_row, max_infected_col);
	}

	// TODO 3*3 �ֺ��� ���� �����ڰ� ���� ����ü�� ���� ���� Ž��, ���� �� ��ȣ�ϴ� ĭ �ֺ����� ����ü�� ���� ���� Ž��
	// �ּ��� �������ڸ��� ��ü�� �Ǵ� ���� �����ϱ� ����
	public Point WhereToGoToBeSafe() {
		int safest_row = favoritePoint.row, safest_col = favoritePoint.column;
		int max_survivor = 0;
		int survivor_num;
		boolean is_there_infected = false;

		// 3*3������ ����ü�� �ϳ��� ���� ������ �ִ���
		boolean is_there_safezone = false;

		for (int i = 1; i < Constants.Classroom_Height - 1; i++) {
			for (int j = 1; j < Constants.Classroom_Width - 1; j++) {
				CellInfo cell = this.cells[i][j];
				survivor_num = 0;
				is_there_infected = false;
				for (int row_offset = -1; row_offset <= 1; row_offset++) {
					for (int col_offset = -1; col_offset <= 1; col_offset++) {
						CellInfo offsetcell = this.cells[i + row_offset][j + col_offset];
						// 3*3 ������ ������ ���ڸ� ����
						survivor_num += offsetcell.CountIf_Players(player -> player.state == StateCode.Survivor);

						// ���� �ش� �� �ֺ��� �����ڰ� �ϳ��� ������ �ݺ��� Ż��, ���� ĭ���� �Ѿ
						if (offsetcell.CountIf_Players(player -> player.state == StateCode.Infected) > 0) {
							is_there_infected = true;
							break;
						}
					}
					if (is_there_infected)
						break;
				}

				// �ش� ����Ʈ �ֺ��� ����ü�� �ִ� ��� ���� ����Ʈ�� Ž��
				if (is_there_infected)
					continue;

				// ������� �ڵ尡 �Դٴ� �� �ϴ��� 3*3 ���� �ȿ� ������ ������ �ִٴ� �ǹ��̹Ƿ�
				// is_there_safezone�� true�� ����
				is_there_safezone = true;

				if (survivor_num > max_survivor) {
					max_survivor = survivor_num;
					safest_row = i;
					safest_col = j;
				}
			}
		}

		// �ֺ� 3*3 ���� �ȿ� ����ü�� ���� ĭ�� �������� ���� ��� ��� 5*5 ���� �ȿ��� ����ü�� ���� �������� ��������
		if (!is_there_safezone) {
			for (int i = 4; i <= 8; i++) {
				for (int j = 4; j <= 8; j++) {
					CellInfo cell = this.cells[i][j];
					if (cell.CountIf_Players(player -> player.state == StateCode.Infected) != 0)
						return new Point(i, j);
				}
			}
		}

		// ��� 5*5 ���������� ����ü�� ���� ���� ���ٸ� ��ȣ�ϴ� ĭ���� ������
		return new Point(safest_row, safest_col);
	}

	@Override
	public Point Soul_Spawn() {
		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
		// �����ڿ� ����ü �� ����
		int total_survivor = 0, total_infected = 0;
		for (int row = 0; row < Constants.Classroom_Height; row++) {
			for (int column = 0; column < Constants.Classroom_Width; column++) {
				CellInfo cell = this.cells[row][column];
				total_survivor += cell.CountIf_Players(player -> player.state == StateCode.Survivor);
				total_infected += cell.CountIf_Players(player -> player.state == StateCode.Infected);
			}
		}

		// ������>����ü�� ��� �Ǵ� �ϼ��� 60�� ������ ��� ������ ������ ������ ��Ȱ
		if (total_survivor > total_infected || this.turnInfo.turnNumber < 60) {
			// int min_row = -1;
			// int min_column = -1;
			// min_distance = Integer.MAX_VALUE;
			//
			// // ��ü ĭ�� �˻��Ͽ� �����ڰ� �ִ� ĭ�� �� '��ȣ�ϴ� ĭ'�� ���� ����� ĭ�� ����
			// for (int row = 0; row < Constants.Classroom_Height; row++) {
			// for (int column = 0; column < Constants.Classroom_Width;
			// column++) {
			// CellInfo cell = this.cells[row][column];
			//
			// int numberOfInfecteds = cell.CountIf_Players(player ->
			// player.state == StateCode.Survivor);
			//
			// if (numberOfInfecteds != 0) {
			// int distance = favoritePoint.GetDistance(row, column);
			//
			// // �Ÿ��� �� ������ ����
			// if (distance < min_distance) {
			// min_distance = distance;
			// min_row = row;
			// min_column = column;
			// }
			// // �Ÿ��� ������ �� �����ϴ� ������ ����
			// else if (distance == min_distance) {
			// for (int iDirection = 0; iDirection < 4; iDirection++) {
			// Point adjacentPoint =
			// favoritePoint.GetAdjacentPoint(directions[iDirection]);
			//
			// if (adjacentPoint.GetDistance(row, column) <
			// adjacentPoint.GetDistance(min_row,
			// min_column)) {
			// min_row = row;
			// min_column = column;
			// break;
			// }
			// }
			//
			// // ������� �Դٸ� ���� �׸� ���� ����
			// }
			// }
			// }
			// }
			// return new Point(min_row, min_column);
			return WhereToGoToBeSafe();
		}

		// ������<����ü �� ��� �ֺ��� ����ü�� ���� ���� ������ ��ü��ź ����
		else {
			min_distance = Constants.Classroom_Width * Constants.Classroom_Height;
			boolean Can_I_Get_More_Cmax = false;
			// ��ü ĭ�� �˻��Ͽ� ��ü �� ����ü ���� ���� ���� ĭ�� ã��
			for (int row = 0; row < Constants.Classroom_Height; row++) {
				for (int column = 0; column < Constants.Classroom_Width; column++) {
					CellInfo cell = this.cells[row][column];

					int numberOfCorpses = cell.CountIf_Players(player -> player.state == StateCode.Corpse);
					int numberOfInfecteds = cell.CountIf_Players(player -> player.state == StateCode.Infected);

					int weight = numberOfInfecteds != 0 ? numberOfCorpses + numberOfInfecteds : 0;
					int distance = favoritePoint.GetDistance(row, column);

					// ���� ���� ĭ�� �߰ߵǸ� ����
					if (weight > this.myScore.corpse_max) {
						Can_I_Get_More_Cmax = true;
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
			}
			if(Can_I_Get_More_Cmax)return new Point(max_row, max_column);
			else return WhereToGoToBeCorpes();
		}
	}
}
