package l4g2ep1;

import l4g2ep1.common.*;

/**
 * �÷��̾ �� �� �����ϴ� �ǻ� ���� �ϳ��� ��Ÿ���ϴ�.
 * �� Ŭ������ ���ǽ� ���ο����� ���Ǹ� �������� �� Ŭ������ �� �� �����ϴ�.
 * � �÷��̾ � �ൿ�� �߾����� Ȯ���Ϸ��� �ൿ ����� �����ϼ���.
 * 
 * @author Racin
 *
 */
class Decision
{
	enum TypeCode
	{
		Not_Defined,
		Survivor_Move,
		Corpse_Stay,
		Infected_Move,
		Soul_Stay,
		Soul_Spawn
	}

	TypeCode type;
	DirectionCode direction;
	Point location_from;
	Point location_to;

	/*
	 * �������� �� Ŭ������ ���� ����� �� �����ϴ�.
	 * � �÷��̾ � �ൿ�� �߾����� Ȯ���Ϸ��� �ൿ ����� �����ϼ���.
	 */
	Decision()
	{
		location_from = new Point();
		location_to = new Point();
	}
}
