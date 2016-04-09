package l4g.common;

import java.util.ArrayList;
import java.util.function.Supplier;

import l4g.common.Player;

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
	public long game_number;

	/**
	 * ���� Ű���� �Ǵ� ���콺�� ������ �� �ִ� �÷��̾ ����Ϸ��� ��� true�� �����մϴ�.
	 * �⺻���� false�Դϴ�.
	 */
	public boolean use_ctrlable_player;

	/**
	 * ���� �ۼ��Ͽ� ������ų �÷��̾� Ŭ������ ����Դϴ�.
	 * settings.custom_player_classes.add(Player_YOURNAMEHERE.class);
	 * ...�� ���� �������� ���� Ŭ���� �̸��� .class�� �ٿ� �� ��Ͽ� ������ ���ǽǿ� �������� �÷��̾ �߰��˴ϴ�.
	 * ���ǽ� ���� ��� �� �ڸ��� ���� �÷��̾�� ä�����ϴ�.
	 */
	public ArrayList<Class<? extends Player>> custom_player_classes;

	/**
	 * ������ �ǻ� ������ �����ϴ� ������ Bot �÷��̾ �ִ� �� ����� �߰��� �������� �����մϴ�.
	 * �����ڱ� ������ ���� ���� ��ȣ, ���� ID�� ����ϸ� �׻� ������ �ǻ� ������ �����մϴ�.
	 * �⺻���� 10�Դϴ�.<br>
	 * <br>
	 * <b>����:</b> ������ Bot �÷��̾�� ���� ���ӿ����� ������ �ʽ��ϴ�.
	 * ����, �������� �ۼ��ϴ� �÷��̾�� � �����ε� <code>java.util.Random class</code>�� ����� �� �����ϴ�.
	 * ������ Bot �÷��̾�� �ܼ��� �׽�Ʈ�� ���� '�������� �÷��̾ ������� �ø��� ��Ȳ'�� �� �� ���� ������ ���� �뵵�� ����ؾ� �մϴ�. 
	 */
	public int max_numberOfHornDonePlayer;
	
	/**
	 * NPC �÷��̾�(���� ������ ������ ���� �ʴ� �÷��̾�)�� �� �� �ִ����� ��Ÿ���ϴ�.
	 * NPC �÷��̾��� ������ �� �ι��� �ְ�/���� ������ ����� �� ���ܵ˴ϴ�.
	 * (NPC �÷��̾ 1���� �ص� �������� ������ ���������� �ʽ��ϴ�)
	 * NPC �÷��̾�� �ݵ�� �ٸ� �÷��̾�麸�� ���� ����� ���ƾ� �մϴ�.
	 * 
	 * �⺻���� 0�Դϴ�.
	 */
	public int numberOfNPCs;
	
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
	 * �÷��̾��� ���� ���¸� ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * print_first_player_only�� true�� ���� �ൿ �� �ڽ��� �˰� �ִ� �÷��̾���� ���� ���� + �� ���� ���������� �ڽ��� ���� ������ ǥ���ϸ�,
	 * false�� ���� �� ���� ���������� ��� �÷��̾��� ���� ������ ǥ���մϴ�.
	 * �⺻���� true�Դϴ�.
	 */
	public boolean print_playerInfos;

	/**
	 * �ܼ� â�� ���� ����� ǥ���� ��
	 * �� �ϸ��� �÷��̾���� ������ �ǻ� �������� ������ ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * �⺻���� false�Դϴ�.
	 */
	public boolean print_decisions;
	
	/**
	 * �ܼ� â�� ���� ����� ǥ���� ��
	 * �� �ϸ��� �÷��̾���� ������ �ൿ���� ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * print_first_player_only�� true�� ���� �ڽ��� �þ� ���� ������ �� �Ͽ� ����� �ൿ ����(�ǻ� ������ �ٰŰ� �Ǵ�)
	 * + �ڽ��� �̹� �Ͽ� ������ �ൿ ������ ǥ���ϸ�,
	 * false�� ���� ��� �ൿ ���� �̵� ��� / ��ġ ������� ������ ǥ���մϴ�. 
	 * �⺻���� true�Դϴ�.
	 */
	public boolean print_actions;

	/**
	 * �ܼ� â�� ���� ����� ǥ���� ��
	 * �� �ϸ��� �÷��̾���� �߻���Ų ���(�߰� ��� ����)���� ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * print_first_player_only�� true�� ���� �ڽ��� �þ� ���� ������ �� �Ͽ� �Ͼ ��� ����(�ǻ� ������ �ٰŰ� �Ǵ�)
	 * + �ڽ��� �̹� �Ͽ� ����Ų ��� ������ ǥ���ϸ�,
	 * false�� ���� ��� ��� ������ ǥ���մϴ�. 
	 * �⺻���� true�Դϴ�.
	 */
	public boolean print_reactions;

	/**
	 * �ܼ� â�� ���� ����� ǥ���� ��
	 * �� �ϸ��� �÷��̾���� ���� ������ ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * �⺻���� false�Դϴ�.
	 */
	public boolean print_scores_at_each_turns;

	/**
	 * �ܼ� â�� ���� ����� ǥ���� ��
	 * ���� ���� �� �÷��̾���� ������ ǥ���Ϸ��� ��� true�� �����մϴ�.
	 * �⺻���� true�Դϴ�.
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
	 * �⺻���� "16OODP" �Դϴ�.
	 */
	public String seed_for_sample_players;

	/**
	 * �ʱ�ȭ �۾��� ������ �� ȣ��Ǵ� �ݹ� �޼����Դϴ�.
	 * �� �ʵ忡 �������� �޼���(�Ǵ� ���� ��)�� ������ �ʱ�ȭ �۾��� ���� ���Ŀ� �ش� �޼��带 ȣ���� �ݴϴ�.
	 * �� �Ҹ��� �𸣰����� �׳� null�� �ξ �����մϴ�.
	 * �⺻���� null�Դϴ�.
	 */
	public Supplier<Boolean> callback_StandBy;

	/**
	 * ������ ���۵� �� ȣ��Ǵ� �ݹ� �޼����Դϴ�.
	 * �� �ʵ忡 �������� �޼���(�Ǵ� ���� ��)�� ������ ������ ���۵Ǳ� ������ �ش� �޼��带 ȣ���� �ݴϴ�.
	 * �� �Ҹ��� �𸣰����� �׳� null�� �ξ �����մϴ�.
	 * �⺻���� null�Դϴ�.
	 */
	public Supplier<Boolean> callback_StartGame;

	/**
	 * ���� ���۵� �� ȣ��Ǵ� �ݹ� �޼����Դϴ�.
	 * �� �ʵ忡 �������� �޼���(�Ǵ� ���� ��)�� ������ �� ���� ���۵Ǳ� ������ �ش� �޼��带 ȣ���� �ݴϴ�.
	 * �� �Ҹ��� �𸣰����� �׳� null�� �ξ �����մϴ�.
	 * �⺻���� null�Դϴ�.
	 */
	public Supplier<Boolean> callback_StartTurn;

	/**
	 * ���� ���� ������ �÷��̾ ����ϴ� ��� ������� ���ʰ� �Ǿ��� �� ȣ��Ǵ� �ݹ� �޼����Դϴ�.
	 * �� �ʵ�� ���ǽǰ� Presenter�� �����ϱ� ���� ���Ǹ� �������� �� �� ���� ���� �����ϴ�.
	 * �⺻���� null�Դϴ�.
	 */
	public Supplier<Boolean> callback_RequestDecision;

	/**
	 * ���� ���� ������ �÷��̾ ����ϴ� ��� ������� ������ ��ȿ���� ���� �� ȣ��Ǵ� �ݹ� �޼����Դϴ�.
	 * �� �ʵ�� ���ǽǰ� Presenter�� �����ϱ� ���� ���Ǹ� �������� �� �� ���� ���� �����ϴ�.
	 * �⺻���� null�Դϴ�.
	 */
	public Supplier<Boolean> callback_InvalidDecision;

	/**
	 * ���� ������ �� ȣ��Ǵ� �ݹ� �޼����Դϴ�.
	 * �� �ʵ忡 �������� �޼���(�Ǵ� ���� ��)�� ������ ���� ���� ���Ŀ� �ش� �޼��带 ȣ���� �ݴϴ�.
	 * �� �Ҹ��� �𸣰����� �׳� null�� �ξ �����մϴ�.
	 * �⺻���� null�Դϴ�.
	 */
	public Supplier<Boolean> callback_EndTurn;

	/**
	 * ������ ������ �� ȣ��Ǵ� �ݹ� �޼����Դϴ�.
	 * �� �ʵ忡 �������� �޼���(�Ǵ� ���� ��)�� ������ ������ ���� ���Ŀ� �ش� �޼��带 ȣ���� �ݴϴ�.
	 * �� �Ҹ��� �𸣰����� �׳� null�� �ξ �����մϴ�.
	 * �⺻���� null�Դϴ�.
	 */
	public Supplier<Boolean> callback_EndGame;

	/**
	 * ���ο� <code>Classroom_Settings class</code>�� �ν��Ͻ��� �����ϰ�
	 * ��� �������� �⺻������ �ʱ�ȭ�մϴ�.
	 */
	public Classroom_Settings()
	{
		game_number = -1L;
		use_ctrlable_player = false;
		custom_player_classes = new ArrayList<Class<? extends Player>>();
		max_numberOfHornDonePlayer = 10;
		numberOfNPCs = 0;
		use_console_mode = true;
		print_first_player_only = true;
		print_playerInfos = true;
		print_decisions = false;
		print_actions = true;
		print_reactions = true;
		print_scores_at_each_turns = false;
		print_scores_at_the_end = true;
		print_errors = true;
		seed_for_sample_players = "16OODP";
		callback_StandBy = null;
		callback_StartGame = null;
		callback_StartTurn = null;
		callback_RequestDecision = null;
		callback_InvalidDecision = null;
		callback_EndTurn = null;
		callback_EndGame = null;
	}
	
	/**
	 * �־��� ���� �ν��Ͻ��� ������ ���ο� <code>Classroom_Settings class</code>�� �ν��Ͻ��� �����մϴ�.
	 */
	public Classroom_Settings(Classroom_Settings other)
	{
		game_number = other.game_number;
		use_ctrlable_player = other.use_ctrlable_player;
		custom_player_classes = new ArrayList<Class<? extends Player>>(other.custom_player_classes);
		max_numberOfHornDonePlayer = other.max_numberOfHornDonePlayer;
		numberOfNPCs = other.numberOfNPCs;
		use_console_mode = other.use_console_mode;
		print_first_player_only = other.print_first_player_only;
		print_playerInfos = other.print_playerInfos;
		print_decisions = other.print_decisions;
		print_actions = other.print_actions;
		print_reactions = other.print_reactions;
		print_scores_at_each_turns = other.print_scores_at_each_turns;
		print_scores_at_the_end = other.print_scores_at_the_end;
		print_errors = other.print_errors;
		seed_for_sample_players = other.seed_for_sample_players;
		callback_StandBy = other.callback_StandBy;
		callback_StartGame = other.callback_StartGame;
		callback_StartTurn = other.callback_StartTurn;
		callback_RequestDecision = other.callback_RequestDecision;
		callback_InvalidDecision = other.callback_InvalidDecision;
		callback_EndTurn = other.callback_EndTurn;
		callback_EndGame = other.callback_EndGame;
	}
}
