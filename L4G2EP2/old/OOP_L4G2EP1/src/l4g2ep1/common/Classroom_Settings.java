package l4g2ep1.common;

import java.util.ArrayList;

import l4g2ep1.Player;

/**
 * ���ǽ��� ������ �� ����ϴ� ���� ���������� ��� �ִ� Ŭ�����Դϴ�.
 * 
 * @author Racin
 * 
 */
public class Classroom_Settings
{
	/**
	 * ������ ���� ��ȣ�Դϴ�. ���� ��ȣ�� ������ ���� �����ص� ���� ����� ���� �Ǿ� �ֽ��ϴ�.
	 * �� ���� -1�� �δ� ��� ���� ���õ� ���� ��ȣ�� ����մϴ�.
	 * �⺻���� -1�Դϴ�.
	 */
	public int game_number;

	/**
	 * ���� Ű���� �Ǵ� ���콺�� ������ �� �ִ� �÷��̾ ����Ϸ��� ��� true�� �����մϴ�.
	 * �⺻���� false�Դϴ�.
	 */
	public boolean use_ctrlable_player;

	/**
	 * ���� �ۼ��Ͽ� ������ų �÷��̾� Ŭ������ ����Դϴ�.
	 * settings.custom_player_classes.add(Player_YOURNAMEHERE.class);
	 * ...�� ���� �������� ���� Ŭ���� �̸��� .class�� �ٿ� �� ��Ͽ� ������ ���ǽǿ� �������� �÷��̾ �߰��˴ϴ�.
	 * ���ǽ� ������ ä��� ���� �� �ڸ��� ���� �÷��̾�� ä�����ϴ�.
	 */
	public ArrayList<Class<? extends Player>> custom_player_classes;

	/**
	 * �ܼ� â�� ���� ����� ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * �⺻���� true�Դϴ�.
	 */
	public boolean use_console_mode;

	/**
	 * �ܼ� â�� ���� ����� ǥ���� ��
	 * 0�� �÷��̾�(���� �����ϴ� �÷��̾� �Ǵ� ���� �ۼ��Ͽ� ������Ų ù �÷��̾�)�� ���� ������ ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * ��� �÷��̾ ���� ������ ǥ���Ϸ��� ��� false�� �����մϴ�.
	 * �⺻���� true�Դϴ�.
	 */
	public boolean print_first_player_only;

	/**
	 * �ܼ� â�� ���� ����� ǥ���� ��
	 * �� �ϸ��� �÷��̾���� ���� ���¸� ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * �⺻���� true�Դϴ�.
	 */
	public boolean print_playerInfos;

	/**
	 * �ܼ� â�� ���� ����� ǥ���� ��
	 * �� �ϸ��� �÷��̾���� ������ �ǻ� �������� ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * �⺻���� false�Դϴ�.
	 */
	public boolean print_decisions;

	/**
	 * �ܼ� â�� ���� ����� ǥ���� ��
	 * �� �ϸ��� �÷��̾���� ������ �ൿ���� ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * �⺻���� true�Դϴ�.
	 */
	public boolean print_actions;

	/**
	 * �ܼ� â�� ���� ����� ǥ���� ��
	 * �� �ϸ��� �÷��̾���� �߻���Ų ��ǵ��� ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * �⺻���� true�Դϴ�.
	 */
	public boolean print_reactions;

	/**
	 * �ܼ� â�� ���� ����� ǥ���� ��
	 * �� �ϸ��� �÷��̾���� ���� ������ ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * �⺻���� true�Դϴ�.
	 */
	public boolean print_scores_during_game;

	/**
	 * �ܼ� â�� ���� ����� ǥ���� ��
	 * ���� ���� �� �÷��̾���� ������ ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * �⺻���� false�Դϴ�.
	 */
	public boolean print_scores_at_the_end;
	
	/**
	 * ��Ÿ�� ������ ������ �ܼ� â�� ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * �⺻���� true�Դϴ�.
	 */
	public boolean print_errors;
	
	/**
	 * ���� �÷��̾��� ������ ������ ������ �� ����ϴ� ���ڿ��Դϴ�.
	 * �� ���ڿ��� '�� ���ڿ�' �Ǵ� null�� �����ϴ� ��� ���ǽ� �ʱ�ȭ �������� �ٽ� �⺻������ �����˴ϴ�.
	 * �⺻���� "14OOP" �Դϴ�.
	 */
	public String seed_for_sample_players;

	/**
	 * ���ο� Classroom_Settings class�� �ν��Ͻ��� �����ϰ�
	 * ��� �������� �⺻������ �ʱ�ȭ�մϴ�.
	 */
	public Classroom_Settings()
	{
		game_number = -1;
		use_ctrlable_player = false;
		custom_player_classes = new ArrayList<Class<? extends Player>>();
		use_console_mode = true;
		print_first_player_only = true;
		print_playerInfos = true;
		print_decisions = false;
		print_actions = true;
		print_reactions = true;
		print_scores_during_game = true;
		print_scores_at_the_end = false;
		print_errors = true;
		seed_for_sample_players = "14OOP";
	}
	
	public Classroom_Settings(Classroom_Settings other)
	{
		game_number = other.game_number;
		use_ctrlable_player = other.use_ctrlable_player;
		custom_player_classes = new ArrayList<Class<? extends Player>>(other.custom_player_classes);
		use_console_mode = other.use_console_mode;
		print_first_player_only = other.print_first_player_only;
		print_playerInfos = other.print_playerInfos;
		print_decisions = other.print_decisions;
		print_actions = other.print_actions;
		print_reactions = other.print_reactions;
		print_scores_during_game = other.print_scores_during_game;
		print_scores_at_the_end = other.print_scores_at_the_end;
		print_errors = other.print_errors;
		seed_for_sample_players = other.seed_for_sample_players;
	}
}
