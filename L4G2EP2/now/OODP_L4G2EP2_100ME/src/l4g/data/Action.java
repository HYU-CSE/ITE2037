package l4g.data;

import l4g.common.Point_Immutable;

/**
 * �ǻ� ������ ����� ����� �ൿ �ϳ��� ��Ÿ���ϴ�.
 * 
 * �� Ŭ������ ��� �ʵ�� �б� �����Դϴ�.
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
		 * �ڽ��� ��ġ�� �����մϴ�. ������ �� ����ü ������ �� ���� �����մϴ�.
		 */
		Move,
		
		/**
		 * ���ο� ��ġ�� �ڽ��� ��ġ��ŵ�ϴ�. ��ȥ ���¿��� ���� ���� ����� ���� ���� �����մϴ�.
		 */
		Spawn
	}
	
	/**
	 * �� �ൿ�� ������ �÷��̾��� ID�Դϴ�.
	 */
	public final int actorID;
	
	/**
	 * �� �ൿ�� �����Դϴ�.
	 * ���� ����� <code>Action.TypeCode</code> ġ�� '��'�� ������ Ȯ���� �� �ֽ��ϴ�.
	 */
	public final TypeCode type;
	
	/**
	 * �� �ൿ�� ����� ���� ��ġ�Դϴ�.
	 * ��ġ �ൿ�� ��� ���� ��ġ�� <code>Constants.Pos_Sky</code>�� �˴ϴ�.
	 */
	public final Point_Immutable location_from;
	
	/**
	 * �� �ൿ�� ����� ���� ��ġ�Դϴ�.
	 */
	public final Point_Immutable location_to;
	
	/**
	 * ����: �� Ŭ������ �ν��Ͻ��� �������� ���� ���� ���� �ʿ䰡 �����ϴ�.
	 * ���� � �ൿ ������ �纻�� �����ϰ� ���� ��쿣
	 * �׳� �� �ν��Ͻ� ��ü�� �������� �ʵ忡 �Ҵ��� ��Ƶ� �˴ϴ�.
	 * �б� �����̶� ���� �Ͽ� ������ �� �ν��Ͻ� ���� ����ϱ� �����Դϴ�.
	 */
	public Action(int actorID, TypeCode type, Point_Immutable location_from, Point_Immutable location_to)
	{
		this.actorID = actorID;
		this.type = type;
		this.location_from = location_from;
		this.location_to = location_to;
	}
}
