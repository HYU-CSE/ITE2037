package l4g.customplayers;

import java.util.ArrayList;

import l4g.Grader;
import l4g.common.*;
import l4g.data.PlayerInfo;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class LowerBodyParalysis extends Player {
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은
	// 별개입니다.
	public LowerBodyParalysis(int ID) {

		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은
		// 별개입니다.
		super(ID, "감염되니하반신마비");

		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥
		// 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = false;

		// TODO#4 여기까지 왔으면 이제 Developer's Guide 문서와 각 BOT 플레이어 코드를 한 번만 더 읽어 보고
		// 돌아옵시다.

	}

	/*
	 * TODO#5 이제 여러분이 그려 둔 노트를 보며 아래에 있는 다섯 가지 의사 결정 메서드를 완성하세요. 당연히 한 방에 될 리
	 * 없으니, 중간중간 코드를 백업해 두는 것이 좋으며, 코드 작성이 어려울 땐 아무 부담 없이 조교를 찾아 오세요.
	 * 
	 * L4G는 여러분의 '생각'을 추구하는 축제지 구글 굴리는 축제가 아닙니다!
	 * 
	 * 여러분이 이번 축제에서 투자한 시간만큼, 이후 다른 과제 / 다른 업무에서 뻘짓을 벌이는 시간이 줄어들게 될 것입니다. 그러니
	 * 자신이 뭔가 멋진 생각을 떠올렸다면, 이를 내 플레이어에 적용하기 위해 아낌 없는 노력을 투자해 보세요!
	 * 
	 * 제출기한이 되어 황급히 파일을 업로드하고 Eclipse로 돌아와 여러분이 작성한 코드를 돌아 보면, '코드에 노력이란게 묻어 날
	 * 수도 있구나'라는 생각이 절로 들게 될 것입니다.
	 */

	static final int GoRight = 2;
	static final int GoDown = 3;
	static final int GoLeft = 1;
	static final int GoUp = 0;
	static final int Stay = 4;

	int[] dx = { -1, 0, 0, 1, 0 }, dy = { 0, -1, 1, 0, 0 };
	int stayCnt=0;

	boolean ChkBound(int row, int column) {
		if (row < 0 || row >= Constants.Classroom_Height || column < 0 || column >= Constants.Classroom_Width)
			return false;
		else
			return true;
	}

	int CountNext(int row, int column) {
		if (!ChkBound(row, column))
			return -987654321;

		int cnt = 0;
		for (int k = 0; k < 5; ++k) {
			int nRow = row + dx[k], nColumn = column + dy[k];
			if (!ChkBound(nRow, nColumn))
				continue;
			cnt -= cells[nRow][nColumn].CountIf_Players(player -> player.state == StateCode.Infected);
			if (turnInfo.turnNumber <= Constants.Duration_Direct_Infection)
				cnt -= cells[nRow][nColumn].CountIf_Players(player -> player.state == StateCode.Survivor);
			else
				cnt += cells[nRow][nColumn].CountIf_Players(player -> player.state == StateCode.Survivor);

		}
		return cnt;

	}

	DirectionCode UpdateState(int n) {

		int i, max = -987654321, maxI = 0;
		for (i = 0; i < n; ++i) {
			int k = CountNext(this.myInfo.position.row + dx[i], this.myInfo.position.column + dy[i]);
			if (max < k) {
				max = k;
				maxI = i;
			}
		}
		
		switch (maxI) {
		case GoUp:
			return DirectionCode.Up;
		case GoLeft:
			return DirectionCode.Left;
		case GoRight:
			return DirectionCode.Right;
		case GoDown:
			return DirectionCode.Down;
		default:
			return DirectionCode.Stay;
		}
	}

	@Override
	public DirectionCode Survivor_Move() {
		return UpdateState(4);
	}

	@Override
	public void Corpse_Stay() {
	}

	@Override
	public DirectionCode Infected_Move() {
		return DirectionCode.Stay;
	}

	@Override
	public void Soul_Stay() {
		if (turnInfo.turnNumber == 0) {
			/*
			 * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다. 이 if문의 내용은 0턴째에만
			 * 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다.
			 */
		}
	}

	@Override
	public Point Soul_Spawn() {
		if (turnInfo.turnNumber == 0)
			return new Point(Constants.Classroom_Height / 2, Constants.Classroom_Width / 2);

		int row = 0, column = 0, max = 0;
		for (int i = 0; i < Constants.Classroom_Height; ++i)
			for (int j = 0; j < Constants.Classroom_Width; ++j)
				if (max < cells[i][j].CountIf_Players(player -> player.state == StateCode.Infected)) {
					max = cells[i][j].CountIf_Players(player -> player.state == StateCode.Infected);
					row = i;
					column = j;
				}
		if(max==0) return new Point(Constants.Classroom_Height / 2, Constants.Classroom_Width / 2);
		else return new Point(row, column);
	}
}
