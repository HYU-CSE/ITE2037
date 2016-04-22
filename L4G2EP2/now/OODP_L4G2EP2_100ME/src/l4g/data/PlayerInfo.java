package l4g.data;

import l4g.PlayerStat;
import l4g.common.Point_Immutable;
import l4g.common.StateCode;

/**
 * �÷��̾� �� �� ���� ������ �����ϱ� ���� Ŭ�����Դϴ�.
 * 
 * �� Ŭ������ ��� �ʵ�� �б� �����Դϴ�. 
 * 
 * @author Racin
 *
 */
public class PlayerInfo
{
	/**
	 * �� �÷��̾��� �Ϸ� ��ȣ�Դϴ�.
	 */
	public final int ID;
	
	/**
	 * �� �÷��̾��� ����(���������� ��ü���� ���)�Դϴ�.
	 */
	public final StateCode state;
	
	/**
	 * �� �÷��̾��� ü�·��Դϴ�.
	 * 
	 * �� ���� ��ü �Ǵ� ����ü ������ ���� ��ȿ�մϴ�.
	 */
	public final int HP;
	
	/**
	 * �� �÷��̾ ���� ����(��ü �Ͼ, ��ȥ ���ġ, �������� ����)�� �����ϱ���� ���� �� ���Դϴ�. 
	 * �� ���� 0�̸� �ش� �÷��̾�� �̹� �Ͽ� �Ͼ�ų�, ��ġ �ǻ� ������ �����ϰų�, ����ü�� �˴ϴ�. 
	 * 
	 * �� ���� ������ �� ������, �� ��� '���� ���̰� �����Ǿ� ���� ����'�� �ǹ��մϴ�.
	 */
	public final int transition_cooldown;

	/**
	 * �� �÷��̾��� ��ġ�Դϴ�.
	 * 
	 * ��ȥ ������ �÷��̾���� <code>Constants.Pos_Sky</code>�� ��ġ�� ������ �����˴ϴ�.
	 * (������ ��ȥ�� �� �� �ִ� ���� ���� ��ȥ�� �� ���̹Ƿ� �׸� ū �ǹ̴� �����ϴ�)
	 */
	public final Point_Immutable position;
	
	/**
	 * ����: �������� ��ȿ�� <code>PlayerStat</code> �ν��Ͻ��� ���� �� �� ������
	 * ������ ���� �� �ν��Ͻ��� ������ ���� �����Ƿ�
	 * �� �����ڴ� ��� �����п��Դ� �ƹ� �ǹ� �����ϴ�.
	 */
	public PlayerInfo(PlayerStat stat)
	{
		ID = stat.ID;
		state = stat.state;
		HP = stat.HP;
		transition_cooldown = stat.transitionCooldown;
		position = stat.position;
	}
}
