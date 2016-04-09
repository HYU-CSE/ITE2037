package l4g2ep1;

import l4g2ep1.Classroom.State;
import l4g2ep1.common.*;

/**
 * Presenter�� ���� ���� ���� ������ �÷��̾� �ϳ��� ��Ÿ���ϴ�.
 * �� Ŭ������ ��ӵ� �� �����ϴ�.
 * 
 * @author Racin
 * 
 */
public final class ControllablePlayer extends Player
{
	// ���ǽǰ� ����� presenter�� ����ؾ� �ϹǷ� ���ǽǿ� ���� ���� �ʵ� �ʿ�
	Classroom classroom;

	/**
	 * ���ο� ���� ������ �÷��̾��� �ν��Ͻ��� �����մϴ�.
	 * ���� ������ �÷��̾�� ���� �� �ִ� ��� ������ �����մϴ�.
	 * 
	 * @param classroom
	 *            ������ ������ ���ǽǿ� ���� �����Դϴ�.
	 */
	public ControllablePlayer(Classroom classroom)
	{
		this.classroom = classroom;
		name = "��";
		receiveOthersInfo_detected = true;
		receiveActions = true;
		receiveReactions = true;
	}

	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result = DirectionCode.Stay;

		// ���ǽ��� '�����' ���·� �ٲٰ� presenter�� '�ǻ� ���� ��û' �̺�Ʈ ����
		classroom.state = State.Waiting_Decision_Survivor_Move;
		classroom.request_decision.actionPerformed(null);
		
		while (true)
		{
			//presenter���� �ǻ� ������ �Ϸ�ɶ����� ���
			synchronized (classroom)
			{
				try
				{
					classroom.wait();
				}
				catch (InterruptedException e)
				{
				}
			}

			// ������ �ǻ� ������ ��ȿ���� ���� ��� '�߸��� �ǻ� ����' �̺�Ʈ ���� �� �ٽ� ���
			if ( IsValidMove(classroom.accepted_point) == false )
				classroom.invalid_decision.actionPerformed(null);
			else
				break;
		}

		/*
		 * ��ȿ�� ������ �ǻ� ������ ���� ���� ���.
		 * 
		 * �����ڴ� �׻� x / y��ǥ �� �ϳ��� +1 / -1��ŭ ���� �����ϹǷ�
		 * �̵� �� / ���� x��ǥ�� ���ٸ� y��ǥ�� ���Ͽ� �Ʒ� / �� �Ǻ�,
		 * �̵� �� / ���� x��ǥ�� �ٸ��ٸ� x��ǥ�� ���Ͽ� ������ / ���� �Ǻ�.
		 */
		if ( myInfo.position.x == classroom.accepted_point.x )
		{
			if ( myInfo.position.y < classroom.accepted_point.y )
				result = DirectionCode.Down;
			else
				result = DirectionCode.Up;
		}
		else
		{
			if ( myInfo.position.x < classroom.accepted_point.x )
				result = DirectionCode.Right;
			else
				result = DirectionCode.Left;
		}

		// �ǻ� ������ ������ ���ǽ� ���¸� �ٽ� '������'���� ����
		classroom.state = State.Running;

		return result;
	}

	@Override
	public void Corpse_Stay()
	{
		// ���ǽ��� '�����' ���·� �ٲٰ� presenter�� '�ǻ� ���� ��û' �̺�Ʈ ����
		classroom.state = State.Waiting_Decision_Corpse_Stay;
		classroom.request_decision.actionPerformed(null);

		synchronized (classroom)
		{
			// presenter���� �ǻ� ������ �Ϸ�ɶ����� ���
			try
			{
				classroom.wait();
			}
			catch (InterruptedException e)
			{
			}
		}

		// �ǻ� ������ ������ ���ǽ� ���¸� �ٽ� '������'���� ����
		classroom.state = State.Running;
	}

	@Override
	public DirectionCode Infected_Move()
	{
		DirectionCode result = DirectionCode.Stay;

		// ���ǽ��� '�����' ���·� �ٲٰ� presenter�� '�ǻ� ���� ��û' �̺�Ʈ ����
		classroom.state = State.Waiting_Decision_Infected_Move;
		classroom.request_decision.actionPerformed(null);

		while (true)
		{
			synchronized (classroom)
			{
				// presenter���� �ǻ� ������ �Ϸ�ɶ����� ���
				try
				{
					classroom.wait();
				}
				catch (InterruptedException e)
				{
				}
			}

			// ������ �ǻ� ������ ��ȿ���� ���� ��� '�߸��� �ǻ� ����' �̺�Ʈ ���� �� �ٽ� ���
			if ( IsValidMove(classroom.accepted_point) == false )
				classroom.invalid_decision.actionPerformed(null);
			else
				break;
		}

		/*
		 * ��ȿ�� ����ü �ǻ� ������ ���� ���� ���.
		 * 
		 * ����ü�� ���ڸ��� �ӹ����� �ʴ� ��� �׻� x / y��ǥ �� �ϳ��� +1 / -1��ŭ ���� �����ϹǷ�
		 * �̵� �� / ���� x��ǥ�� ���ٸ� y��ǥ�� ���Ͽ� �Ʒ� / �� �Ǻ�, (y��ǥ�� ���� �� �����Ƿ� �񱳸� �� �� ����)
		 * �̵� �� / ���� x��ǥ�� �ٸ��ٸ� x��ǥ�� ���Ͽ� ������ / ���� �Ǻ�. (x��ǥ�� �ٸ��ٴ� ������ �����Ƿ� �񱳸� �� ���� ����)
		 */
		if ( myInfo.position.x == classroom.accepted_point.x )
		{
			if ( myInfo.position.y < classroom.accepted_point.y )
				result = DirectionCode.Down;
			if ( myInfo.position.y > classroom.accepted_point.y )
				result = DirectionCode.Up;
		}
		else
		{
			if ( myInfo.position.x < classroom.accepted_point.x )
				result = DirectionCode.Right;
			else
				result = DirectionCode.Left;
		}

		// �ǻ� ������ ������ ���ǽ� ���¸� �ٽ� '������'���� ����
		classroom.state = State.Running;

		return result;
	}

	@Override
	public void Soul_Stay()
	{
		// ���ǽ��� '�����' ���·� �ٲٰ� presenter�� '�ǻ� ���� ��û' �̺�Ʈ ����
		classroom.state = State.Waiting_Decision_Soul_Stay;
		classroom.request_decision.actionPerformed(null);

		synchronized (classroom)
		{
			// presenter���� �ǻ� ������ �Ϸ�ɶ����� ���
			try
			{
				classroom.wait();
			}
			catch (InterruptedException e)
			{
			}
		}

		// �ǻ� ������ ������ ���ǽ� ���¸� �ٽ� '������'���� ����
		classroom.state = State.Running;
	}

	@Override
	public Point Soul_Spawn()
	{
		// ���ǽ��� '�����' ���·� �ٲٰ� presenter�� '�ǻ� ���� ��û' �̺�Ʈ ����
		classroom.state = State.Waiting_Decision_Soul_Spawn;
		classroom.request_decision.actionPerformed(null);

		while (true)
		{
			synchronized (classroom)
			{
				// presenter���� �ǻ� ������ �Ϸ�ɶ����� ���
				try
				{
					classroom.wait();
				}
				catch (InterruptedException e)
				{
				}
			}

			// ������ �ǻ� ������ ��ȿ���� ���� ��� '�߸��� �ǻ� ����' �̺�Ʈ ���� �� �ٽ� ���
			if ( IsValidSpawn(classroom.accepted_point) == false )
				classroom.invalid_decision.actionPerformed(null);
			else
				break;
		}

		// �ǻ� ������ ������ ���ǽ� ���¸� �ٽ� '������'���� ����
		classroom.state = State.Running;

		return classroom.accepted_point;
	}
}
