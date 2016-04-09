package l4g2ep1;

import l4g2ep1.common.*;

/**
 * �ǻ� ������ ����� ����� �ൿ �ϳ��� ��Ÿ���ϴ�.
 * 
 * @author Racin
 *
 */
public class Action
{
	/**
	 * �ൿ�� ������ �����ϱ� ���� �������Դϴ�.
	 * 
	 * @author Racin
	 * 
	 */
	public enum TypeCode
	{
		Not_Defined,

		/**
		 * �ڽ��� ��ġ�� �����մϴ�. ������ �� ����ü ������ �� �߻��մϴ�.
		 */
		Move,

		/**
		 * ���ǽ� ���� ���ο� ��ġ�� �ڽ��� ��ġ��ŵ�ϴ�. ��ȥ ���¿��� ��Ȱ�� �� �߻��մϴ�.
		 */
		Spawn,

		/**
		 * �߸��� �̵� �Ǵ� ��ġ�� �õ����� �� �߻��մϴ�.
		 * 
		 * (�÷��̾���� �� type�� ���� �ൿ�� �� �� ������
		 * ��� ��� ��Ͽ��� type�� Punished / Arrested�� ����� ���� ���� �߸��� �ǻ� ������ �ߴ��� Ȯ���� �� �ֽ��ϴ�)
		 */
		Illigal,
	}
	
	/*
	 * ���� �ִ� �ʵ���� �������� ���� �� �� ������ ��� �Ʒ��� ���ǵ� getter �޼������ ����Ͽ� ������ �о�� �� �ֽ��ϴ�.
	 * ( �ٽ� ���ϸ�, �������� �� �ʵ��� ������ ������ ���� �����ϴ� )
	 */
	int actorID;
	TypeCode type;
	Point location_from;
	Point location_to;

	/*
	 * �������� ���� �� Ŭ������ �ν��Ͻ��� ���� ���� �����ϴ�.
	 * �������� �þ� ���� ������ ����� �ൿ���� ��� �ڵ����� ��Ͽ� ��ϵǾ� ���޵˴ϴ�.
	 */
	Action(int actorID, Decision origin, boolean isLegal)
	{
		this.actorID = actorID;

		location_from = new Point(origin.location_from);

		switch (origin.type)
		{
		case Survivor_Move:
		case Infected_Move:
			type = TypeCode.Move;
			location_to = new Point(origin.location_from, origin.direction);
			break;
		case Soul_Spawn:
			type = TypeCode.Spawn;
			location_to = new Point(origin.location_to);
			break;
		default:
			type = TypeCode.Not_Defined;
			break;
		}

		if ( isLegal == false )
		{
			type = TypeCode.Illigal;
		}
	}

	/**
	 * �� �ൿ�� ������ �÷��̾��� ID�� �����ɴϴ�.
	 */
	public int GetActorID() { return actorID; }

	/**
	 * �� �ൿ�� ������ �����ɴϴ�.
	 * ���� ����� Action.TypeCode. �� �Է��ϸ� Ȯ���� �� �ֽ��ϴ�.
	 */
	public TypeCode GetType() { return type; }

	/**
	 * �� �ൿ�� ����� ���� ��ġ�� �����ɴϴ�.
	 * ��ġ �ൿ�� ��� ���� ��ġ�� ���� ��ġ�� �����ϰ� ��ϵǾ� �ֽ��ϴ�.
	 */
	public Point GetLocation_From() { return new Point(location_from); }

	/**
	 * �� �ൿ�� ����� ���� ��ġ�� �����ɴϴ�.
	 * ��ġ �ൿ�� ��� ���� ��ġ�� ���� ��ġ�� �����ϰ� ��ϵǾ� �ֽ��ϴ�.
	 */
	public Point GetLocation_To() { return new Point(location_to); }

	/**
	 * �� �ൿ�� �ش� ��ǥ�� �����Ǿ� �ִ��� ���θ� ��ȯ�մϴ�.
	 * ���� ��ġ �Ǵ� ���� ��ġ�� �ش� ��ǥ�� ��� �����Ǿ� �ִ� ������ �����մϴ�.
	 * 
	 * @return �� �ൿ�� �ش� ��ǥ�� �����Ǿ� �ִٸ� true, �׷��� �ʴٸ� false�Դϴ�.
	 */
	public boolean IsInvolvedIn(Point location) { 	return location.equals(location_from) || location.equals(location_to); }

	/**
	 * �� �ൿ�� �ش� �÷��̾ ���� ����Ǿ����� ���θ� ��ȯ�մϴ�.
	 * 
	 * @param ID
	 *            ���� ���θ� Ȯ���� �÷��̾��� ID�Դϴ�.
	 * @return �� �ൿ�� �ش� �÷��̾�� �����Ǿ� �ִٸ� true, �׷��� �ʴٸ� false�Դϴ�.
	 */
	public boolean IsInvolvedWith(int ID)	{ return actorID == ID; }
}
