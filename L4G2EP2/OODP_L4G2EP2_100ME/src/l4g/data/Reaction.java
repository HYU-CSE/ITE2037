package l4g.data;

import l4g.common.Point_Immutable;

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

//		/**
//		 * subject(������)�� object(��ü �Ǵ� ����ü)�� �߰� - �� ����� �ʹ� ���� �߻��ϹǷ� ������� ����
//		 */
//		Spot,

		/**
		 * subject(��ü)�� object(����ü)�� ġ��
		 */
		Heal,

		/**
		 * subject(��ü)�� �Ͼ ����ü�� ��
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
		 * subject(����ü)�� ü�� ���� ��ȥ�� ��
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

	/**
	 * �� ����� ����Ų �÷��̾�(������ ��ü)�� ID�Դϴ�.
	 */
	public final int subjectID;

	/**
	 * �� ����� �����Դϴ�.
	 * ���� ����� <code>Reaction.TypeCode</code> ġ�� '��'�� ������ Ȯ���� �� �ֽ��ϴ�.
	 */
	public final TypeCode type;

	/**
	 * �� ����� ���� �÷��̾�(������ ���)�� ID�Դϴ�.
	 * ȥ�� ����Ű�� ���(Rise, DirectInfect ��)�� ��� �� ���� <code>subjectID</code>�� �����ϰ� �����˴ϴ�.
	 */
	public final int objectID;

	/**
	 * �� ����� �߻���Ų subject�� ��ġ�� �����ɴϴ�.
	 */
	public final Point_Immutable location_subject;

	/**
	 * �� ����� '����' object�� ��ġ�� �����ɴϴ�.
	 */
	public final Point_Immutable location_object;

	/**
	 * ����: �� Ŭ������ �ν��Ͻ��� �������� ���� ���� ���� �ʿ䰡 �����ϴ�.
	 * ���� � ��ǿ� ���� �纻�� �����ϰ� ���� ��쿣
	 * �׳� �� �ν��Ͻ� ��ü�� �������� �ʵ忡 �Ҵ��� ��Ƶ� �˴ϴ�.
	 * �б� �����̶� ���� �Ͽ� ������ �� �ν��Ͻ� ���� ����ϱ� �����Դϴ�.
	 */
	public Reaction(int ID, TypeCode type, Point_Immutable location)
	{
		this.subjectID = ID;
		this.type = type;
		this.objectID = ID;
		this.location_subject = location;
		this.location_object = location;
	}

	/**
	 * ����: �� Ŭ������ �ν��Ͻ��� �������� ���� ���� ���� �ʿ䰡 �����ϴ�.
	 * ���� � ��ǿ� ���� �纻�� �����ϰ� ���� ��쿣
	 * �׳� �� �ν��Ͻ� ��ü�� �������� �ʵ忡 �Ҵ��� ��Ƶ� �˴ϴ�.
	 * �б� �����̶� ���� �Ͽ� ������ �� �ν��Ͻ� ���� ����ϱ� �����Դϴ�.
	 */
	public Reaction(int subjectID, TypeCode type, int objectID, Point_Immutable location_subject, Point_Immutable location_object)
	{
		this.subjectID = subjectID;
		this.type = type;
		this.objectID = objectID;
		this.location_subject = location_subject;
		this.location_object = location_object;
	}
}
