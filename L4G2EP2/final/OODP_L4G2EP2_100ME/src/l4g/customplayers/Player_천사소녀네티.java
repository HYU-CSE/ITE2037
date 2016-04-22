package l4g.customplayers;

import java.util.function.Predicate;

import l4g.common.*;
import l4g.data.CellInfo;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */

public class Player_õ��ҳ��Ƽ extends Player {

	public Player_õ��ҳ��Ƽ( int ID ) {

		super(ID, "õ��ҳ��Ƽ");

		this.trigger_acceptDirectInfection = true;
	}

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

		// �÷��̾�� �ٸ� ID, ���Ӹ��� �ٸ� ���� ��ȣ�� �����Ͽ� ���� �̻��� ���� �����
		long seed = (ID + 41) * gameNumber + ID;

		if (seed < 0)
			seed = -seed;

		// �� ���� 24�� ���� �������� ���� ���� �켱���� ����
		switch ((int) (seed % 24)) {
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

		favoritePoint.row = (int) (seed / Constants.Classroom_Width % Constants.Classroom_Height);
		favoritePoint.column = (int) (seed % Constants.Classroom_Height);
	}

	/**
	 * '���� �켱����'�� ����� �δ� �迭�Դϴ�. �� field�� �ݵ�� �ʿ��մϴ�.
	 */
	DirectionCode[]	directions		= new DirectionCode[4];

	Point			favoritePoint	= new Point(0, 0);

	/**
	 * '���� �켱����'�� '��ȣ�ϴ� ĭ'�� �����մϴ�. �� �޼���� Soul_Stay()���� �� �� �� ȣ��˴ϴ�. �� �޼���� �ݵ��
	 * �ʿ��մϴ�.
	 */

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

	static final int	GoRight	= 0;
	static final int	GoDown	= 1;
	static final int	GoLeft	= 2;
	static final int	GoUp	= 3;

	int					state	= GoRight;

	void UpdateState() {
		switch (state) {
			case GoRight:
				if (this.myInfo.position.column == Constants.Classroom_Width - 5)
					state = GoDown;
				break;
			case GoDown:
				if (this.myInfo.position.row == Constants.Classroom_Width - 10)
					state = GoLeft;
				break;
			case GoLeft:
				if (this.myInfo.position.column == Constants.Classroom_Width - 10)
					state = GoUp;
				break;
			case GoUp:
				if (this.myInfo.position.row == Constants.Classroom_Width - 10)
					state = GoRight;
				break;
			default:
				break;
		}
	}

	@Override
	public DirectionCode Survivor_Move() {
		/*
		 * ������ �̵�: �����ڰ� �̵��� ���ɼ��� ���� ���� �������� �̵��մϴ�. �� �ֺ� ĭ�� ���� 0 1 2 3 X 4 5 6 7
		 * ..�� ��ȣ�� �Ű��� �� ��: 012�� �ִ� ��� �� ����: 135�� �ִ� ��� �� ������: 246�� �ִ� ��� �� �Ʒ�:
		 * 567�� �ִ� ��� �� ..�� �ջ��Ͽ� ���մϴ�.
		 */
		int[] numberOfSurvivors = new int[8];
		int row = myInfo.position.row;
		int column = myInfo.position.column;

		// 0
		row -= 2;

		if (row >= 0)
			numberOfSurvivors[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

		// 1, 2
		++row;

		if (row >= 0) {
			if (column >= 1)
				numberOfSurvivors[1] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);

			if (column < Constants.Classroom_Width - 1)
				numberOfSurvivors[2] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
		}

		// 3, 4
		++row;

		if (column >= 2)
			numberOfSurvivors[3] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);

		if (column < Constants.Classroom_Width - 2)
			numberOfSurvivors[4] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);

		// 5, 6
		++row;

		if (row < Constants.Classroom_Height) {
			if (column >= 1)
				numberOfSurvivors[5] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);

			if (column < Constants.Classroom_Width - 1)
				numberOfSurvivors[6] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
		}

		// 7
		++row;

		if (row < Constants.Classroom_Height)
			numberOfSurvivors[7] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

		// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�
		int[] weights = new int[4];

		for (int iWeights = 0; iWeights < 4; iWeights++) {
			switch (directions[iWeights]) {
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

	}

	@Override
	public DirectionCode Infected_Move() {
		/*
		 * ����ü �̵�: �����ڰ� �̵��� ���ɼ��� ���� ���� �������� �̵��մϴ�. �� �ֺ� ĭ�� ���� 0 1 2 3 X 4 5 6 7
		 * ..�� ��ȣ�� �Ű��� �� ��: 012�� �ִ� ��� �� ����: 135�� �ִ� ��� �� ������: 246�� �ִ� ��� �� �Ʒ�:
		 * 567�� �ִ� ��� �� ..�� �ջ��Ͽ� ���մϴ�.
		 */
		int[] numberOfSurvivors = new int[8];
		int[] numberOfCorpses = new int[4];
		int row = myInfo.position.row;
		int column = myInfo.position.column;

		// 0
		row -= 2;

		if (row >= 0)
			numberOfSurvivors[0] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

		// 1, 2
		++row;

		if (row >= 0) {
			if (column >= 1)
				numberOfSurvivors[1] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);

			if (column < Constants.Classroom_Width - 1)
				numberOfSurvivors[2] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
		}

		// 3, 4
		++row;

		if (column >= 2)
			numberOfSurvivors[3] = cells[row][column - 2].CountIf_Players(player -> player.state == StateCode.Survivor);

		if (column < Constants.Classroom_Width - 2)
			numberOfSurvivors[4] = cells[row][column + 2].CountIf_Players(player -> player.state == StateCode.Survivor);

		// 5, 6
		++row;

		if (row < Constants.Classroom_Height) {
			if (column >= 1)
				numberOfSurvivors[5] = cells[row][column - 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);

			if (column < Constants.Classroom_Width - 1)
				numberOfSurvivors[6] = cells[row][column + 1]
						.CountIf_Players(player -> player.state == StateCode.Survivor);
		}

		// 7
		++row;

		if (row < Constants.Classroom_Height)
			numberOfSurvivors[7] = cells[row][column].CountIf_Players(player -> player.state == StateCode.Survivor);

		// 4���� ����(������ ���� �켱������ ����)�� ���� ������ �� �ջ�
		int[] weights = new int[4];

		for (int iWeights = 0; iWeights < 4; iWeights++) {
			switch (directions[iWeights]) {
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


		// 4���� ����(������ ���� �켱������ ����)�� ���� ��ü �� �ջ� (���� �� 2 �̻�)
		Predicate<PlayerInfo> cond = player -> player.state == StateCode.Corpse && player.transition_cooldown >= 2;

		// 0
		--row;
		numberOfCorpses[0] = count_players( row, column, cond );

		// 1, 2
		++row;
		numberOfCorpses[1] = count_players( row, column-1, cond );
		numberOfCorpses[2] = count_players( row, column+1, cond );
		
		// 3
		++row;
		numberOfCorpses[3] = count_players( row, column-2, cond );

		
		// �Ÿ� 2�� �ִ� ������ ���� 2 �̻����� ���Ѵ�.
		int max_weight = -1;
		int max_idx_weights = 0;

		int total_numberOfSurvivors = 0;
		boolean isCorpseExists;

		for(int i = 0; i < 8; i++){
			total_numberOfSurvivors += numberOfSurvivors[i];
		}

		isCorpseExists = numberOfCorpses[0]+numberOfCorpses[1]+numberOfCorpses[2]+numberOfCorpses[3] > 0;
		
		//////////////////////////////////////////////////////////////////////////////
		// ���� �Ÿ� 2�� �ִ� ������ ���� 2�� �̻��̸� �����ڰ� �������� ����,
		

		if (total_numberOfSurvivors >=2) {
		// ������ ���� ���� ���� ���� ����(�ش� ������ ������ ��� ���� �켱 ������ ���� ���� ����)
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
		
		// �׷��� ������ �Ÿ�1�� ��ü�� �����ϰ�, �׵��� life�� 2 �̻����� ã�´�.
			
		// Yes : Predator mode(��ü ���)
		else if(isCorpseExists){
		
			// ���� ĭ�� ��ü�� �ִٸ� ������ ���
			if ( count_players( myInfo.position.row, myInfo.position.column, StateCode.Corpse) != 0 ) {
				return DirectionCode.Stay;
			}

			else {
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
		}
						
		
		// No : ��ȭ�⵵ �ǽ�
		else {
			// �� �ؿ� ��ü�� ��� ������ ������
			if ( count_players( myInfo.position.row, myInfo.position.column, StateCode.Corpse) > 0 )
				return GetMovableAdjacentDirection();
		
			// �׷��� ������ ��ȭ �⵵ �õ�
			else return DirectionCode.Stay;
		}
}

	@Override
	public void Soul_Stay() {
		if (this.turnInfo.turnNumber == 0) {
			// �� �κ��� Bot �÷��̾� �ڵ带 ������ ����ϱ� ���� �ݵ�� �ʿ��մϴ�.
			Init_Data();
		}
	}

	@Override
	public Point Soul_Spawn() {

		if (this.turnInfo.turnNumber == 0) {
			return new Point(5, 5);

		}

		int max_weight = 0;
		int max_row = -1;
		int max_column = -1;
		int min_distance = Constants.Classroom_Width * Constants.Classroom_Height;

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
	
	/**
	 * �ش� ��ġ�� �÷��̾� ���� ���մϴ�.
	 * �ùٸ��� ���� ��ġ�� ����Ű�� ���, 0�� ��ȯ�մϴ�.
	 */
	private int count_players( int row, int col )
	{
		if( row < 0 || row >= Constants.Classroom_Height ||
				col < 0 || col >= Constants.Classroom_Width )
			return 0;
		
		return cells[row][col].Count_Players();
	}
	
	/**
	 * �־��� state �� �ش� ��ġ�� �÷��̾� ���� ���մϴ�.
	 * �ùٸ��� ���� ��ġ�� ����Ű�� ���, 0�� ��ȯ�մϴ�.
	 */
	private int count_players( int row, int col, StateCode state )
	{
		if( row < 0 || row >= Constants.Classroom_Height ||
				col < 0 || col >= Constants.Classroom_Width )
			return 0;
		
		Predicate<PlayerInfo> cond = player -> player.state == state;
		
		return cells[row][col].CountIf_Players( cond );
	}
	
	/**
	 * cond �� �����ϴ� �ش� ��ġ�� �÷��̾� ���� ���մϴ�.
	 * �ùٸ��� ���� ��ġ�� ����Ű�� ���, 0�� ��ȯ�մϴ�.
	 */
	private int count_players( int row, int col, Predicate<PlayerInfo> cond )
	{
		if( row < 0 || row >= Constants.Classroom_Height ||
				col < 0 || col >= Constants.Classroom_Width )
			return 0;
		
		return cells[row][col].CountIf_Players( cond );
	}

}
