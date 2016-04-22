package l4g;

import java.util.ArrayList;

import l4g.common.Constants;
import l4g.common.Point_Immutable;
import l4g.common.StateCode;

/**
 * ���� �� �߻��ϴ� �÷��̾� �� �� ���� ���� �������� ��� Ŭ�����Դϴ�.
 * �������� �� Ŭ������ ����� �� �Ϻθ� �Ű� ���� <code>PlayerInfo</code> �ν��Ͻ��� ��� ����ϰ� �˴ϴ�.
 * 
 * �� Ŭ������ ������ �ڵ忡�� ������ �� ������
 * �̹� ������� �ν��Ͻ��鿡 �׼����� ���� �����ϴ�.
 * 
 * @author Racin
 *
 */
public class PlayerStat
{
	/**
	 * �� �÷��̾��� �Ϸ� ��ȣ�Դϴ�.
	 * �� �ʵ�� �б� �����̸� <code>PlayerInfo</code>�� ���Ե˴ϴ�.
	 */
	public final int ID;
	
	/**
	 * �� �÷��̾��� ����(���������� ��ü���� ���)�Դϴ�.
	 * �� �ʵ�� <code>PlayerInfo</code>�� ���Ե˴ϴ�.
	 */
	public StateCode state;
	
	/**
	 * �� �÷��̾��� �� ���� ���������� �����Դϴ�.
	 */
	public StateCode lastState;

	/**
	 * �� �÷��̾��� ü�·��Դϴ�.
	 * �� �ʵ�� <code>PlayerInfo</code>�� ���Ե˴ϴ�.
	 */
	public int HP;
	
	/**
	 * ���� ����(��ü �Ͼ, ��ȥ ���ġ, �������� ����)���� ���� �� ���Դϴ�.
	 * �� ���� ��ٿ��� ����Ǵ� ����(����, �Ҹ�, �г�Ƽ �ο�, �������� ����) ���ŵ˴ϴ�.
	 * ��ٿ� ���Ҵ� �׻� ���� ���۵Ǳ� ������ �Ͼ��
	 * ��ٿ��� 0�� �ȴٸ� �̹� �Ͽ� �ش� ����� �߻��ϰ� �˴ϴ�.
	 * �� �ʵ�� <code>PlayerInfo</code>�� ���Ե˴ϴ�.
	 */
	public int transitionCooldown;

	/**
	 * �� �÷��̾��� ��ġ�Դϴ�.
	 * ��ȥ ������ �÷��̾���� <code>Constants.Pos_Sky</code>�� ��ġ�� ������ �����˴ϴ�.
	 * �� �ʵ�� <code>PlayerInfo</code>�� ���Ե˴ϴ�.
	 */
	public Point_Immutable position;
	
	/**
	 * �� �÷��̾��� �� ���� ���������� ��ġ�Դϴ�.
	 * ��ȥ ������ �÷��̾���� <code>Constants.Pos_Sky</code>�� ��ġ�� ������ �����˴ϴ�.
	 */
	public Point_Immutable lastPosition;
	
	/**
	 * �����ڰ� ���������� �����ߴ��� �����Դϴ�.
	 * �� ���� ���������� ������ ���� ���ŵǸ�
	 * ���������� �ߵ��� ����(�ڽ��� ������ �޾ҵ� �ƴϵ�) reset�˴ϴ�.
	 */
	public boolean survivor_AcceptedDirectInfection;
	
	/**
	 * �����ڰ� �̹� �Ͽ� �׾�����(�װ� �� ��) �����Դϴ�.
	 * �� ���� óġ ����� �߻��� ���� set�Ǹ�
	 * ������ ���¿��� ��ü ���·� ���̵Ǵ� ���� reset�˴ϴ�.
	 */
	public boolean survivor_Dead;
	
	/**
	 * ��ü�� ��Ѹ� �쵢�� ������ ����(ĭ ��)�Դϴ�.
	 * �� ���� ��ü�� �Ǵ� ���� �ʱⰪ���� �����Ǹ� �� ���� ���� ������ 1�� �����մϴ�.
	 * �ּҰ��� 0�Դϴ�.
	 */
	public int corpse_radius;	

	/**
	 * ����ü�� �̹� �Ͽ� Stay�� �����ߴ��� �����Դϴ�.
	 * �� ���� �̵� �ǻ� ���� ó�� ���� ���ŵ˴ϴ�.
	 */
	public boolean infected_Stayed;
	
	/**
	 * ����ü�� �̹� �Ͽ� óġ �Ǵ� óġ ������ ����ߴ��� �����Դϴ�.
	 * �� ���� óġ ����� �߻��� ���� set�Ǹ�
	 * óġ ������ �����ϴ� ���� reset�˴ϴ�.
	 */
	public boolean infected_KilledOrAssisted;
	
	/**
	 * ����ü�� �̹� �Ͽ� ��ü�� ġ���� �޾Ҵ��� �����Դϴ�.
	 * �� ���� ġ�� ����� �߻��� ���� set�Ǹ�
	 * ����ü���� ü���� ��� ���� reset�˴ϴ�.
	 */
	public boolean infected_Healed;

	/**
	 * ����ü�� ���� ��ȭ �⵵�� �帱 ��� �� ���� ī���͸� ȹ���� �� �ִ���(�⵵ ȿ��)�� ��Ÿ���ϴ�.
	 * �� ���� ��ȭ �⵵�� ������ ������ 1�� ����ϸ�
	 * �ٸ� ���¿��� ����ü ���·� ���̵Ǿ��ų�, ġ���� �޾Ұų�, ��ȭ �⵵�� �õ����� �ʰ� �̵��ߴٸ� �ٽ� 1�� reset�˴ϴ�.
	 */
	public int infected_PrayPower;
	
	/**
	 * ����ü�� ������� ���� ��ȭ �⵵ ī���� ���� ��Ÿ���ϴ�.
	 * �� ���� ��ȭ �⵵�� ������ ������ Power��ŭ �����ϸ�
	 * ��ü ���¿��� ����ü ���·� ���̵Ǵ� ����,
	 * �Ǵ� ī���͸� �Ҹ��Ͽ� ����ü ���¿��� ��ȥ ���·� ���̵Ǵ� ���� reset�˴ϴ�.
	 */
	public int infected_CurrentPrayCounter;

	/**
	 * ���� �����ڷμ� ������ ���� �� ���Դϴ�.
	 * �� ���� ������ ���¿��� ��ü ���·� ���̵Ǵ� ���� reset�˴ϴ�.
	 */
	public int survivor_CurrentSurvivedTurns;
	
	/**
	 * ���� ��ü�μ� ġ���� �÷��̾� ID ����Դϴ�.
	 * �� ���� ��ü ���¿��� ����ü ���·� ���̵Ǵ� ���� reset�˴ϴ�.
	 */
	public ArrayList<Integer> corpse_CurrentHealedPlayers;
	
	/**
	 * ���� ����ü�μ� óġ�� Ƚ���Դϴ�.
	 * �� ���� �Ҹ� ����� �߻��� ���� reset�˴ϴ�.
	 */
	public int infected_CurrentKillStreaks;
	
	/**
	 * ���ο� <code>PlayerStat class</code>�� �ν��Ͻ��� 0��° ��ȥ ���¿� �ش��ϵ��� �ʱ�ȭ�մϴ�.
	 * �� �����ڴ� �������� �� �� �����ϴ�(�������� �� Ŭ������ �ν��Ͻ��� ���� �� �����ϴ�).
	 */
	PlayerStat(int ID)
	{
		this.ID = ID;
		state = StateCode.Soul;
		lastState = StateCode.Soul;
		HP = 0;
		transitionCooldown = 0;
		position = Constants.Pos_Sky;
		lastPosition = Constants.Pos_Sky;
		
		survivor_AcceptedDirectInfection = false;
		survivor_Dead = false;
		
		corpse_radius = 0;
		
		infected_Stayed = false;
		infected_KilledOrAssisted = false;
		infected_Healed = false;
		
		survivor_CurrentSurvivedTurns = 0;
		corpse_CurrentHealedPlayers = new ArrayList<Integer>();
		infected_CurrentKillStreaks = 0;
	}
}
