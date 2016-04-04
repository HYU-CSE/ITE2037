package l4g2ep1;

import l4g2ep1.common.Point;

/**
 * �÷��̾�� �־����� �ڽ� �Ǵ� �ٸ� �÷��̾ ���� �����Դϴ�.
 * 
 * @author Racin
 * 
 */
public class PlayerInfo
{
	/**
	 * �÷��̾��� ���� ���¸� �����ϱ� ���� �������Դϴ�.
	 * 
	 * @author Racin
	 *
	 */
	public enum State
	{
		Not_Defined,
		Survivor,
		Corpse,
		Infected,
		Soul
	}

	/*
	 * ���� �ִ� �ʵ���� �������� ���� �� �� ������ ��� �Ʒ��� ���ǵ� getter �޼������ ����Ͽ� ������ �о�� �� �ֽ��ϴ�.
	 * ( �ٽ� ���ϸ�, �������� �� �ʵ��� ������ ������ ���� �����ϴ� )
	 */
	int ID;
	State state;
	int hitPoint;
	Point position;

	/*
	 * �������� ���� �� Ŭ������ �ν��Ͻ��� ���� ���� �����ϴ�.
	 * �� �÷��̾ ���� �������� �ش�Ǵ� ��Ͽ� �ڵ����� ��ϵǾ� ���޵˴ϴ�.
	 */
	PlayerInfo(int ID)
	{
		this.ID = ID;
		state = State.Not_Defined;
		hitPoint = 0;
		position = new Point();
	}

	PlayerInfo(PlayerInfo other)
	{
		ID = other.ID;
		state = other.state;
		hitPoint = other.hitPoint;
		position = new Point(other.position);
	}

	/**
	 * �ش� �÷��̾��� ���� ��ȣ(ID)�� �����ɴϴ�.
	 */
	public int GetID()
	{
		return ID;
	}

	/**
	 * �ش� �÷��̾��� ���� ���¸� �����ɴϴ�.
	 */
	public State GetState()
	{
		return state;
	}

	/**
	 * �ش� �÷��̾��� ���� ü���� �����ɴϴ�.
	 * ������ �Ǵ� ��ȥ�� ��� �� ���� 0�̸� ������ �ʽ��ϴ�.
	 */
	public int GetHitPoint()
	{
		return hitPoint;
	}

	/**
	 * �ش� �÷��̾��� ���� ��ġ�� �����ɴϴ�.
	 */
	public Point GetPosition()
	{
		return new Point(position);
	}
}
