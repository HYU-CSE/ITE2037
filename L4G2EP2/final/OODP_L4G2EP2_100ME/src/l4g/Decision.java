package l4g;

import l4g.common.DirectionCode;
import l4g.common.Point;
import l4g.common.Point_Immutable;

/**
 * �÷��̾ �� �� �����ϴ� �ǻ� ������ ������ �������� ��� Ŭ�����Դϴ�.
 * �� Ŭ������ ���ǽ� ���ο����� ���Ǹ� �������� �� Ŭ������ �� �� �����ϴ�.
 * � �÷��̾ � �ൿ�� �߾����� Ȯ���Ϸ��� �ൿ ����� �����ϼ���.
 * 
 * @author Racin
 *
 */
class Decision
{
	/**
	 * �ǻ� ���� ������ �����ϱ� ���� �������Դϴ�.
	 * 
	 * @author Racin
	 *
	 */
	enum TypeCode
	{
		Not_Defined,
		Survivor_Move,
		Corpse_Stay,
		Infected_Move,
		Soul_Stay,
		Soul_Spawn
	}
	
	/**
	 * �̹� �ǻ� ������ ������ ��Ÿ���ϴ�.
	 */
	TypeCode type;
	
	/**
	 * �̵� �ǻ� ������ ��� ������ ������ ��Ÿ���ϴ�.
	 */
	DirectionCode move_direction;
	
	/**
	 * ��ġ �ǻ� ������ ��� ������ ��ǥ�� ��Ÿ���ϴ�.
	 */
	Point spawn_point;

	/**
	 * �ǻ� ������ ������, �÷��̾ ���� �ִ� ĭ�� ��ǥ�� ��Ÿ���ϴ�.
	 */
	Point_Immutable location_from;
	
	/**
	 * ���� �� Ȯ����, �̵� �Ǵ� ��ġ�Ϸ��� ĭ�� ��ǥ�� ��Ÿ���ϴ�.
	 * �̵� �Ǵ� ��ġ�� ��ȿ���� ���� ��� �� �ʵ��� ���� <code>Constants.Pos_Sky</code>�� �˴ϴ�.
	 */
	Point_Immutable location_to;

	/**
	 * Note: �������� �� Ŭ������ ���� ����� �� �����ϴ�.
	 * � �÷��̾ � �ൿ�� �߾����� Ȯ���Ϸ��� �ൿ ����� �����ϼ���.
	 */
	Decision()
	{
	}
}
