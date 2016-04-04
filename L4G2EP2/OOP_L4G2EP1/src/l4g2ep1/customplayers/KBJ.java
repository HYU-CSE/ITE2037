package l4g2ep1.customplayers;

import l4g2ep1.*;
import l4g2ep1.common.*;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 * 
 */
public class KBJ extends Player {
	DirectionCode[] shuffledDirections;
	int[] shuffledDirection_values;
	Point basePoint;

	public KBJ() {
		name = "KBJ"; // TODO 자신이 만들 플레이어의 이름으로 name 필드를 초기화하세요.
		acceptDirectInfection = false; // TODO '직접 감염'을 받으려는 경우 이 필드를 true로 두고
										// 아닌 경우 false로 두세요.
		this.receiveOthersInfo_detected = true;
	}

	boolean validismove(DirectionCode dir) {
		Point abc = myInfo.GetPosition();
		switch (dir) {
		case Left:
			abc.x -= 1;
			break;
		case Up:
			abc.y -= 1;
			break;
		case Down:
			abc.y += 1;
			break;
		case Right:
			abc.x += 1;
			break;
		default:
			break;
		}
		if (2 <= abc.x && abc.x <= 6 && 2 <= abc.y && abc.y <= 6)
			return true;
		else
			return false;
	}

	void ShuffleDirections() {
		// 초기화에 필요한 임의의 자연수 하나 생성
		int seed = myInfo.GetID();
		seed *= seed;
		seed = gameInfo.GetGameNumber() - seed;
		seed *= seed;

		if (seed <= 0)
			seed += Integer.MAX_VALUE;

		/*
		 * 네 가지 방향을 나열할 수 있는 방법은 총 4 * 3 * 2 * 1 = 24가지 존재하므로 seed를 24로 나눈 나머지를
		 * 토대로 방향 설정. (24가지면 그냥 switch문 쓰는게 더 쉽고 빠르겠지만 코드가 길어지니 직접 계산)
		 */
		// 각 자리에서 '값이 증가'해야 하는 정도를 먼저 측정. seed를 24로 나눈 나머지가 0인 경우 0000, 23인 경우
		// 3210이 됨.
		int[] offsets = new int[4];

		offsets[0] = seed % 24 / 6;
		offsets[1] = seed % 6 / 2;
		offsets[2] = seed % 2;
		offsets[3] = 0;

		// 위에서 측정한 offset을 통해 각 자리의 값을 계산.
		// 모든 계산이 끝나면 offset이 0000이었을 때 값은 0123이 됨 (유일하게 offset이 3210이었을 때만 그 배열
		// 그대로 값이 됨)
		shuffledDirection_values = new int[4];

		for (int iCurrent = 0; iCurrent < 4; ++iCurrent) {
			int current_value = 0;

			while (true) {
				// 현재 자리보다 앞에 이미 같은 값이 있는지 검사
				boolean isSameValueFound = false;

				for (int iPrevious = iCurrent - 1; iPrevious >= 0; --iPrevious)
					if (shuffledDirection_values[iPrevious] == current_value) {
						isSameValueFound = true;
						break;
					}

				// 같은 값이 있는 경우 현재 자리의 값을 1 증가시키고 다시 검사
				if (isSameValueFound == true) {
					++current_value;
				}
				// 같은 값이 없고 현재 자리의 offset이 0이 아닌 경우(여기서 값을 증가시켜야 하는 경우)
				// offset을 1 깎은 다음 현재 자리의 값을 1 증가시키고 다시 검사
				else if (offsets[iCurrent] != 0) {
					--offsets[iCurrent];
					++current_value;
				}
				// 같은 값도 없고 offset도 0인 경우 값 계산 완료
				else {
					break;
				}
			}

			// 계산이 끝난 현재 자리의 값을 기록
			shuffledDirection_values[iCurrent] = current_value;
		}

		// 0: Up, 1: Left, 2: Right, 3: Down으로 간주하여 각 자리의 값을 토대로 실제 방향 설정
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

	/**
	 * '기점 지정' 기능을 사용하기 위해 기점 좌표를 초기화합니다. 이 메서드는 첫 턴의 Soul_Stay()에서 호출되어야 합니다.
	 */
	void SetBasePoint() {
		// 초기화에 필요한 임의의 자연수 하나 생성
		int seed = gameInfo.GetGameNumber();
		seed *= seed;
		seed = myInfo.GetID() - seed;
		seed *= seed;

		if (seed <= 0)
			seed += Integer.MAX_VALUE;

		// seed를 강의실의 총 칸 수로 나눈 나머지를 토대로 기점 좌표 설정
		int base_y = seed
				% (Constants.Classroom_Width * Constants.Classroom_Height)
				/ Constants.Classroom_Width;
		int base_x = seed % Constants.Classroom_Width;

		basePoint = new Point(base_x, base_y);
	}

	@Override
	public DirectionCode Survivor_Move() {
		int[] weights = new int[4];
		int min_weight = Integer.MAX_VALUE;

		// 현재 포착된 모든 플레이어에 대해 위험도 계산
		for (PlayerInfo other : othersInfo_detected) {
			if (other.GetState() == PlayerInfo.State.Infected) {
				// 해당 플레이어와 나 사이의 거리 비교
				Vector v = GetDistanceVectorBetweenPlayers(other);

				// 해당 플레이어가 나보다 위에 있다면 위로 가는 것은 위험할 듯?
				if (v.y_offset < 0)
					++weights[0];

				// 해당 플레이어가 나보다 왼쪽에 있다면 왼쪽으로 가는 것은 위험할 듯?
				if (v.x_offset < 0)
					++weights[1];

				// 해당 플레이어가 나보다 오른쪽에 있다면 오른쪽으로 가는 것은 위험할 듯?
				if (v.x_offset > 0)
					++weights[2];

				// 해당 플레이어가 나보다 아래에 있다면 아래로 가는 것은 위험할 듯?
				if (v.y_offset > 0)
					++weights[3];
			}
		}

		// 갈 수 없는 방향에 대해 위험도를 최대값으로 설정
		if (validismove(DirectionCode.Up) == false)
			weights[0] = Integer.MAX_VALUE;

		if (validismove(DirectionCode.Left) == false)
			weights[1] = Integer.MAX_VALUE;

		if (validismove(DirectionCode.Right) == false)
			weights[2] = Integer.MAX_VALUE;

		if (validismove(DirectionCode.Down) == false)
			weights[3] = Integer.MAX_VALUE;

		// 측정된 위험도의 최소값이 몇인지 계산
		for (int weight : weights)
			if (weight < min_weight)
				min_weight = weight;

		// '방향 섞기' 설정에 따라 위험도가 최소값과 같은 방향들 중 하나 선택 - 최소 하나 존재
		for (int iShuffledDirection = 0; iShuffledDirection < 4; ++iShuffledDirection)
			if (weights[shuffledDirection_values[iShuffledDirection]] == min_weight)
				return shuffledDirections[iShuffledDirection];

		// 여기까지 코드가 실행될 가능성은 없지만 컴파일 오류를 막기 위해 return 한 줄 추가
		return DirectionCode.Stay;
	}

	@Override
	public void Corpse_Stay() {
		// TODO 시체 상태일 때 현재 알고 있는 것을 바탕으로 생각을 진행하려면 여기에 담으세요.
	}

	@Override
	public DirectionCode Infected_Move() {

		// TODO 감염체 상태일 때 이동 또는 대기하기 위한 생각을 여기에 담으세요.
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay() {
		// 이번 턴이 첫 턴인 경우 '방향 섞기' 기능과 '기점 지정' 기능에 필요한 필드 초기화
		if (gameInfo.GetCurrentTurnNumber() == 0) {
			ShuffleDirections();
			SetBasePoint();
		}
		// TODO 영혼 상태일 때 현재 알고 있는 것을 바탕으로 생각을 진행하려면 여기에 담으세요.
	}

	@Override
	public Point Soul_Spawn() {
		return new Point(4, 4);
	}

}
