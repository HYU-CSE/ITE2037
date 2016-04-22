package l4g.customplayers;

import l4g.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_Baggio extends Player {
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
	// �����Դϴ�.
	public Player_Baggio(int ID) {
		// TODO#2 �Ʒ��� "�̸�!" ��ġ�� �������� ���� �÷��̾��� �̸��� �־� �ּ���. Ŭ���� �̸��� �÷��̾� �̸���
		// �����Դϴ�.
		super(ID, "Baggio!");

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

	int state = 0;
	boolean isLastMove_UpOrDown = false;

	@Override
	public DirectionCode Survivor_Move() {
		DirectionCode result = null;

		// ������ �ٲ�� �ϳ� �˻�
		switch (state) {
		case 0:
			if (myInfo.position.column == Constants.Classroom_Width - 1)
				state = 1;
			break;
		case 1:
			if (myInfo.position.row == 6) {
				state = 2;
				isLastMove_UpOrDown = true;
			}
			break;
		case 2:
			if (myInfo.position.row == 12){
				state = 3;
				isLastMove_UpOrDown = true;
			}
			break;
		case 3:
			if (myInfo.position.row == 6) {
				state = 4;
				isLastMove_UpOrDown = true;
			}
			break;
		case 4:
			if (myInfo.position.column == 6){
				state = 5;
				isLastMove_UpOrDown = false;
			}
			break;
		case 5:
			if (myInfo.position.column == 0){
				state = 6;
				isLastMove_UpOrDown = true;
			}
			break;
		case 6:
			if (myInfo.position.column == 6){
				state = 7;
				isLastMove_UpOrDown = true;
			}
			break;
		case 7:
			if (myInfo.position.row == 6){
				state = 0;
				isLastMove_UpOrDown = true;
			}
		
				
		
		}

		// ������� �̵�
		switch (state) {
		case 0:
			if (isLastMove_UpOrDown == true) {
				result = DirectionCode.Right;
				isLastMove_UpOrDown = false;
			} else {
				result = DirectionCode.Up;
				isLastMove_UpOrDown = true;
			}
			break;
		case 1:
			result = DirectionCode.Down;
			break;
		case 2:
			if (isLastMove_UpOrDown == true) {
				result = DirectionCode.Left;
				isLastMove_UpOrDown = false;
			} else {
				result = DirectionCode.Down;
				isLastMove_UpOrDown = true;
			}
			break;
		case 3:
			if (isLastMove_UpOrDown == true) {
				result = DirectionCode.Left;
				isLastMove_UpOrDown = false;
			} else {
				result = DirectionCode.Up;
				isLastMove_UpOrDown = true;
			}
			break;
		case 4:
			result = DirectionCode.Right;
			break;
		case 5:
			if (isLastMove_UpOrDown == true) {
				result = DirectionCode.Left;
				isLastMove_UpOrDown = false;
			} else {
				result = DirectionCode.Up;
				isLastMove_UpOrDown = true;
			}
			break;
		case 6:
			result = DirectionCode.Right;
			break;
		case 7:
			result = DirectionCode.Down;
			break;
			
		}

		return result;
	}

	@Override
	public void Corpse_Stay() {
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move() {
		return Survivor_Move();
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
		int result_row;
		int result_column;

		
			// ó�� ��Ȱ�� (6, 6)����
			result_row = 6;
			result_column = 6;

			state = 0;
		
		

		return new Point(result_row, result_column);
	}
}
