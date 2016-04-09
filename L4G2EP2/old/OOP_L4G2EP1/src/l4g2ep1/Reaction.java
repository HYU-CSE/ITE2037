package l4g2ep1;

import l4g2ep1.common.*;

/**
 * �ൿ�� ����� �߻��� ��� �ϳ��� ��Ÿ���ϴ�.
 * 
 * @author Racin
 * 
 */
public class Reaction
{
	/**
	 * ����� ������ �����ϱ� ���� �������Դϴ�.
	 * 
	 * @author Racin
	 * 
	 */
	public enum TypeCode
	{
		Not_Defined,

		/**
		 * subject(������)�� object(��ü �Ǵ� ����ü)�� �߰�
		 */
		Spot,

		/**
		 * subject(��ü)�� object(����ü)�� ġ��
		 */
		Heal,

		/**
		 * subject(��ü)�� ����ü�� ��
		 */
		Rise,

		/**
		 * subject(������)�� ���� ������ ���� ����ü�� ��
		 */
		DirectInfect,

		/**
		 * subject(����ü)�� object(������)�� ����
		 */
		Kill,

		/**
		 * subject(����ü)�� ��ȥ�� ��
		 */
		Vanish,

		/**
		 * subject(������, ����ü, ��ȥ)�� �߸��� �ǻ� �������� ��ȥ�� ��
		 */
		Punished,

		/**
		 * subject(������, ��ü, ����ü, ��ȥ)�� ��Ÿ�� ���ܸ� ������ ��ȥ�� ��
		 */
		Arrested,
	}

	/*
	 * ���� �ִ� �ʵ���� �������� ���� �� �� ������ ��� �Ʒ��� ���ǵ� getter �޼������ ����Ͽ� ������ �о�� �� �ֽ��ϴ�.
	 * ( �ٽ� ���ϸ�, �������� �� �ʵ��� ������ ������ ���� �����ϴ� )
	 */
	int subjectID;
	TypeCode type;
	int objectID;
	Point location;

	/*
	 * �������� ���� �� Ŭ������ �ν��Ͻ��� ���� ���� �����ϴ�.
	 * �������� �þ� ���� ������ �߻��ߴ� ��ǵ��� ��� �ڵ����� ��Ͽ� ��ϵǾ� ���޵˴ϴ�.
	 */
	Reaction(int subjectID, TypeCode type, int objectID, Point location)
	{
		this.subjectID = subjectID;
		this.type = type;
		this.objectID = objectID;
		this.location = new Point(location);
	}

	/**
	 * �� ����� subject(������ �ϴ� ��)�� �Ǵ� �÷��̾��� ID�� �����ɴϴ�.
	 * ��ȣ �ۿ��� �ƴ� �Ϻ� ����� ��� subject�� object�� �����ϰ� ��ϵǾ� �ֽ��ϴ�.
	 */
	public int GetSubjectID() { return subjectID; }

	/**
	 * �� ����� ������ �����ɴϴ�.
	 * ���� ����� Reaction.TypeCode. �� �Է��ϸ� Ȯ���� �� �ֽ��ϴ�.
	 */
	public TypeCode GetType() { return type; }

	/**
	 * �� ����� object(������ ���ϴ� ��)�� �Ǵ� �÷��̾��� ID�� �����ɴϴ�.
	 * ��ȣ �ۿ��� �ƴ� �Ϻ� ����� ��� subject�� object�� �����ϰ� ��ϵǾ� �ֽ��ϴ�. 
	 */
	public int GetObjectID()
	{
		return objectID;
	}

	/**
	 * �� ����� �߻��� ��ġ�� �����ɴϴ�.
	 */
	public Point GetLocation()
	{
		return new Point(location);
	}

	/**
	 * �� ����� �ش� �÷��̾�� �����Ǿ� �ִ��� ���θ� ��ȯ�մϴ�.
	 * subject �Ǵ� object�� �ش� �÷��̾��� ��� ������ ������ �����մϴ�.
	 * 
	 * @param ID
	 *            �˻��� �÷��̾��� ID�Դϴ�.
	 * @return �ش� �÷��̾�� �����Ǿ� �ִٸ� true, �׷��� �ʴٸ� false�Դϴ�.
	 */
	public boolean IsInvolvedWith(int ID)
	{
		return subjectID == ID || objectID == ID;
	}

	/**
	 * �� ����� �ش� ��ġ���� �߻��ߴ��� ���θ� ��ȯ�մϴ�.
	 * 
	 * @param location ���� ���θ� Ȯ���� ��ǥ�Դϴ�.
	 * @return �ش� ��ġ�� �����Ǿ� �ִٸ� true, �׷��� �ʴٸ� false�Դϴ�.
	 */
	public boolean IsInvolvedIn(Point location)
	{
		return this.location.equals(location);
	}
}
