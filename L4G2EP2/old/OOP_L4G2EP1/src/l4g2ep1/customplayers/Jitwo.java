package l4g2ep1.customplayers;

import java.util.ArrayList;

import l4g2ep1.*;
import l4g2ep1.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 * 
 */
public class Jitwo extends Player {

	DirectionCode[] shuffledDirections;
	int[] shuffledDirection_values;
	Point basePoint;
	int index_directionToMove;

	void SetBasePoint() {
		// �ʱ�ȭ�� �ʿ��� ������ �ڿ��� �ϳ� ����
		int seed = gameInfo.GetGameNumber();
		seed *= seed;
		seed = myInfo.GetID() - seed;
		seed *= seed;

		if (seed <= 0)
			seed += Integer.MAX_VALUE;

		// seed�� ���ǽ��� �� ĭ ���� ���� �������� ���� ���� ��ǥ ����
		int base_y = seed
				% (Constants.Classroom_Width * Constants.Classroom_Height)
				/ Constants.Classroom_Width;
		int base_x = seed % Constants.Classroom_Width;

		basePoint = new Point(base_x, base_y);
	}

	void ShuffleDirections() {
		// �ʱ�ȭ�� �ʿ��� ������ �ڿ��� �ϳ� ����
		int seed = myInfo.GetID();
		seed *= seed;
		seed = gameInfo.GetGameNumber() - seed;
		seed *= seed;

		if (seed <= 0)
			seed += Integer.MAX_VALUE;

		/*
		 * �� ���� ������ ������ �� �ִ� ����� �� 4 * 3 * 2 * 1 = 24���� �����ϹǷ� seed�� 24�� ���� ��������
		 * ���� ���� ����. (24������ �׳� switch�� ���°� �� ���� ���������� �ڵ尡 ������� ���� ���)
		 */
		// �� �ڸ����� '���� ����'�ؾ� �ϴ� ������ ���� ����. seed�� 24�� ���� �������� 0�� ��� 0000, 23�� ���
		// 3210�� ��.
		int[] offsets = new int[4];

		offsets[0] = seed % 24 / 6;
		offsets[1] = seed % 6 / 2;
		offsets[2] = seed % 2;
		offsets[3] = 0;

		// ������ ������ offset�� ���� �� �ڸ��� ���� ���.
		// ��� ����� ������ offset�� 0000�̾��� �� ���� 0123�� �� (�����ϰ� offset�� 3210�̾��� ���� �� �迭
		// �״�� ���� ��)
		shuffledDirection_values = new int[4];

		for (int iCurrent = 0; iCurrent < 4; ++iCurrent) {
			int current_value = 0;

			while (true) {
				// ���� �ڸ����� �տ� �̹� ���� ���� �ִ��� �˻�
				boolean isSameValueFound = false;

				for (int iPrevious = iCurrent - 1; iPrevious >= 0; --iPrevious)
					if (shuffledDirection_values[iPrevious] == current_value) {
						isSameValueFound = true;
						break;
					}

				// ���� ���� �ִ� ��� ���� �ڸ��� ���� 1 ������Ű�� �ٽ� �˻�
				if (isSameValueFound == true) {
					++current_value;
				}
				// ���� ���� ���� ���� �ڸ��� offset�� 0�� �ƴ� ���(���⼭ ���� �������Ѿ� �ϴ� ���)
				// offset�� 1 ���� ���� ���� �ڸ��� ���� 1 ������Ű�� �ٽ� �˻�
				else if (offsets[iCurrent] != 0) {
					--offsets[iCurrent];
					++current_value;
				}
				// ���� ���� ���� offset�� 0�� ��� �� ��� �Ϸ�
				else {
					break;
				}
			}

			// ����� ���� ���� �ڸ��� ���� ���
			shuffledDirection_values[iCurrent] = current_value;
		}

		// 0: Up, 1: Left, 2: Right, 3: Down���� �����Ͽ� �� �ڸ��� ���� ���� ���� ���� ����
		shuffledDirections = new DirectionCode[4];

		for (int i = 0; i < 4; ++i)
			switch (shuffledDirection_values[i]) {
			case 0:
				shuffledDirections[i] = DirectionCode.Up;
				break;
			case 1:
				shuffledDirections[i] = DirectionCode.Left;
				break;
			case 2:
				shuffledDirections[i] = DirectionCode.Right;
				break;
			default:
				shuffledDirections[i] = DirectionCode.Down;
				break;
			}
	}

	public Jitwo() {
		name = "Jitwo";
		acceptDirectInfection = false;
		receiveOthersInfo_detected = true;
		receiveActions = true;
		receiveReactions = true;
	}

	@Override
	public DirectionCode Survivor_Move() {

		if (gameInfo.GetCurrentTurnNumber() < 35 || myScore.GetSurvivor_Max_Survived_Turns() < 25) {
			/*
			 * �������� ������ �̵�: �ٸ� �÷��̾ ���� ���� ������ ����
			 */

			// ���⺰�� �ٸ� �÷��̾ ���� ���赵�� ����ϱ� ���� �迭 ���
			// 0: Up, 1: Left, 2: Right, 3: Down
			int[] weights = new int[4];
			int min_weight = Integer.MAX_VALUE;

			// ���� ������ ��� �÷��̾ ���� ���赵 ���
			for (PlayerInfo other : othersInfo_detected) {
				// �ش� �÷��̾�� �� ������ �Ÿ� ��
				Vector v = GetDistanceVectorBetweenPlayers(other);

				// �ش� �÷��̾ ������ ���� �ִٸ� ���� ���� ���� ������ ��?
				if (v.y_offset < 0)
					++weights[0];

				// �ش� �÷��̾ ������ ���ʿ� �ִٸ� �������� ���� ���� ������ ��?
				if (v.x_offset < 0)
					++weights[1];

				// �ش� �÷��̾ ������ �����ʿ� �ִٸ� ���������� ���� ���� ������ ��?
				if (v.x_offset > 0)
					++weights[2];

				// �ش� �÷��̾ ������ �Ʒ��� �ִٸ� �Ʒ��� ���� ���� ������ ��?
				if (v.y_offset > 0)
					++weights[3];
			}

			// �� �� ���� ���⿡ ���� ���赵�� �ִ밪���� ����
			if (IsValidMove(DirectionCode.Up) == false)
				weights[0] = Integer.MAX_VALUE;

			if (IsValidMove(DirectionCode.Left) == false)
				weights[1] = Integer.MAX_VALUE;

			if (IsValidMove(DirectionCode.Right) == false)
				weights[2] = Integer.MAX_VALUE;

			if (IsValidMove(DirectionCode.Down) == false)
				weights[3] = Integer.MAX_VALUE;

			// ������ ���赵�� �ּҰ��� ������ ���
			for (int weight : weights)
				if (weight < min_weight)
					min_weight = weight;

			// '���� ����' ������ ���� ���赵�� �ּҰ��� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
			for (int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection)
				if (weights[shuffledDirection_values[iShuffledDirection]] == min_weight)
					return shuffledDirections[iShuffledDirection];

			// ������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
			return DirectionCode.Stay;
		} else {
			/*
			 * �������� ������ �̵�: ������ �� x ��ü�� ����ü ���� ���� ū ������ ����
			 */

			// ���⺰�� �� �÷��̾� �� �� ���� ��밪�� ����ϱ� ���� �迭 ���
			// 0: Up, 1: Left, 2: Right, 3: Down
			int[] survivors = new int[4];
			int[] others = new int[4];
			int[] weights = new int[4];
			int max_weight = -1;

			// ���� ������ ��� �÷��̾ ���� �˻� ����
			for (PlayerInfo other : othersInfo_detected) {
				// �ش� �÷��̾�� �� ������ �Ÿ� ��
				Vector v = GetDistanceVectorBetweenPlayers(other);

				// �ش� �÷��̾��� ���� ���¿� ���� �÷��̾� �� ���
				if (other.GetState() == PlayerInfo.State.Survivor) {
					if (v.y_offset < 0)
						++survivors[0];

					if (v.x_offset < 0)
						++survivors[1];

					if (v.x_offset > 0)
						++survivors[2];

					if (v.y_offset > 0)
						++survivors[3];
				} else {
					if (v.y_offset < 0)
						++others[0];

					if (v.x_offset < 0)
						++others[1];

					if (v.x_offset > 0)
						++others[2];

					if (v.y_offset > 0)
						++others[3];
				}
			}

			if (gameInfo.GetCurrentTurnNumber() <= 10) {
				for (int i = 0; i < 4; i++) {
					weights[i] = survivors[i];
				}
			} else {
				// ���� ��밪: ������ �� x ��ü �� ����ü �� ���
				for (int i = 0; i < 4; ++i)
					weights[i] = survivors[i] * others[i];
			}

			// �� �� ���� ���⿡ ���� ��밪�� �ּҰ����� ����
			if (IsValidMove(DirectionCode.Up) == false)
				weights[0] = -1;

			if (IsValidMove(DirectionCode.Left) == false)
				weights[1] = -1;

			if (IsValidMove(DirectionCode.Right) == false)
				weights[2] = -1;

			if (IsValidMove(DirectionCode.Down) == false)
				weights[3] = -1;

			// ������ ��밪�� �ִ밪�� ������ ���
			for (int weight : weights)
				if (weight > max_weight)
					max_weight = weight;

			// '���� ����' ������ ���� ��밪�� �ִ밪�� ���� ����� �� �ϳ� ���� - �ּ� �ϳ� ����
			for (int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection)
				if (weights[shuffledDirection_values[iShuffledDirection]] == max_weight)
					return shuffledDirections[iShuffledDirection];

			// ������� �ڵ尡 ����� ���ɼ��� ������ ������ ������ ���� ���� return �� �� �߰�
			return DirectionCode.Stay;
		}

	}

	@Override
	public void Corpse_Stay() {
		// TODO ��ü ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}

	@Override
	public DirectionCode Infected_Move() {
		DirectionCode result;

		// �� ��ġ�� ��ü�� �ִ��� �˻�
		boolean isCorpseHere = false;
		CellInfo here = GetCellInfo(myInfo.GetPosition());

		for (int iPlayer = 0; iPlayer < here.GetNumberOfPlayersInTheCell(); ++iPlayer) {
			PlayerInfo other = here.GetPlayerInfo(iPlayer);

			if (other.GetState() == PlayerInfo.State.Corpse) {
				isCorpseHere = true;
				break;
			}
		}

		// ��ü�� �ִٸ� ������ �̵��� ���� ������� �ֺ� ĭ���� �̵�
		if (isCorpseHere == true)
			result = Survivor_Move();
		// �׷��� �ʴٸ� ��ȭ �⵵
		else
			result = DirectionCode.Stay;

		return result;
	}

	@Override
	public void Soul_Stay() {
		if (gameInfo.GetCurrentTurnNumber() == 0) {
			ShuffleDirections();
			SetBasePoint();
		}

		// TODO ��ȥ ������ �� ���� �˰� �ִ� ���� �������� ������ �����Ϸ��� ���⿡ ��������.
	}

	@Override
	public Point Soul_Spawn() {
		/*
		 * ��ü ��ź�� ��ȥ ��ġ: ���� ����ü�� ���� ĭ�� ��� ��ġ
		 */
		Point pointToSpawn = basePoint;

		// �� ĭ�� �ִ� ����ü ���� ����ϱ� ���� �迭 ���
		int[][] weights = new int[Constants.Classroom_Height][Constants.Classroom_Width];
		int max_weight = -1;
		ArrayList<Point> list_pos_max_weight = new ArrayList<Point>();

		// ��� ����ü�� ���� �˻� ���� - 9x9ĭ�� �÷��̾�� 40�� �Ұ��ϹǷ� ����ü�� ���� ���� ĭ ����� ���ÿ� ����
		for (PlayerInfo other : othersInfo_withinSight) {
			// ��� ����ü�� ���� �迭 ���� �� �ִ밪 ���, �ִ밪�� ���� ĭ�� ���� ��ǥ ��� ����
			if (other.GetState() == PlayerInfo.State.Infected) {
				Point pos_other = other.GetPosition();

				++weights[pos_other.y][pos_other.x];

				// �ִ밪�� �ٲ���ٸ� '����ü�� ���� ���� ĭ' ��� �ʱ�ȭ
				if (weights[pos_other.y][pos_other.x] > max_weight) {
					++max_weight; // weight�� �׻� 1�� �����ϹǷ� �翬�� �ִ밪�� 1�� ������
					list_pos_max_weight.clear();
				}

				// ���� ĭ�� ���� �ִ밪�� ���ٸ� ���� ĭ�� '����ü�� ���� ���� ĭ' ��Ͽ� �߰� (������ �ִ밪�� �ٲ���ٸ�
				// �׻� �߰���)
				if (weights[pos_other.y][pos_other.x] == max_weight)
					list_pos_max_weight.add(pos_other);
			}
		}

		// �˻簡 ������ ����ü�� ���� ���� ĭ ��Ͽ��� '���� ����' ������� ���� �� ������ ���� ����� ù��° ĭ ����
		int min_distance = Integer.MAX_VALUE;
		Point myPosition = myInfo.GetPosition();

		for (Point pos_max_weight : list_pos_max_weight) {
			int distance = GetDistance(myPosition, pos_max_weight);

			if (distance < min_distance) {
				min_distance = distance;
				pointToSpawn = pos_max_weight;
			}
		}

		return pointToSpawn;
	}
}
