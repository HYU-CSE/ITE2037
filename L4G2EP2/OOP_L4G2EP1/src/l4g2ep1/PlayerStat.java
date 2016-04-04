package l4g2ep1;

import java.util.ArrayList;

/**
 * ���� ������ �� �÷��̾��� ���� ���¸� ��Ÿ���� ���� ���ǽ� ���ο��� ����ϴ� �����͸� ��� Ŭ�����Դϴ�.
 * �� Ŭ������ ���ǽ� ���ο����� ���Ǹ� �������� �� Ŭ������ �� �� �����ϴ�.
 * 
 * @author Racin
 * 
 */
class PlayerStat
{
	// ��ü �Ǵ� ��ȥ ���¿��� ���� ���·� ���̵Ǳ���� ���� �� ��
	int transition_cooldown;

	
	// ������ ���¿��� �̹� �Ͽ� ���� ������ �����ߴ��� ����
	boolean survivor_acceptDirectInfection;
	
	// ������ ���¿��� �̹� �Ͽ� ����ߴ��� ����
	boolean survivor_deadAtThisTurn;

	
	// ����ü ���¿��� �̹� �Ͽ� ���ڸ��� �ӹ������� ����
	boolean infected_prayingAtThisTurn;
	
	// ����ü ���¿��� �̹� �Ͽ� ��ü���� ġ���޾Ҵ��� ���� 
	boolean infected_healedAtThisTurn;

	
	// ������ ���¿��� ������� ���� �ʰ� �������� ��Ƴ��� �� ��
	int current_survive_point;
	
	// �̹� ��ü ���¿��� ġ���� �÷��̾� ID ���
	ArrayList<Integer> current_healed_players;

	// ����ü ���¿��� ������� �Ҹ����� �ʰ� �������� óġ�� Ƚ��
	int current_kill_point;

	/*
	 * �� Ŭ������ ���ǽ� ���ο����� ���Ǹ�
	 * �������� �� Ŭ������ ���� ����� �� �����ϴ�.
	 */
	PlayerStat()
	{
		transition_cooldown = 0;

		survivor_acceptDirectInfection = false;
		infected_healedAtThisTurn = false;

		infected_prayingAtThisTurn = false;
		infected_healedAtThisTurn = false;

		current_survive_point = 0;
		current_healed_players = new ArrayList<Integer>();
		current_kill_point = 0;
	}
}
