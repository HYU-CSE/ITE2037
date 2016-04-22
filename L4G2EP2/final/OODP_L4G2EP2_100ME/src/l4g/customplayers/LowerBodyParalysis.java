package l4g.customplayers;

import java.util.ArrayList;

import l4g.Grader;
import l4g.common.*;
import l4g.data.PlayerInfo;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class LowerBodyParalysis extends Player {
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public LowerBodyParalysis(int ID) {

		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "�����Ǵ��ϹݽŸ���");

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
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�. �� if���� ������ 0��°����
			 * ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�.
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
