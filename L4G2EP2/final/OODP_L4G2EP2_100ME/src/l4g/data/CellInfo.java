package l4g.data;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * ���ǽ� ���� ĭ �ϳ��� ������ �������� ��� ���� Ŭ�����Դϴ�.
 * 
 * �� Ŭ������ ��� ������ �ʵ�� �б� �����ε��� ������ ������
 * �������� �̸� �غ�� '��ȸ �޼���'�� ����Ͽ� �� �������� Ž���� �� �ֽ��ϴ�.
 * 
 * @author Racin
 *
 */
public class CellInfo
{
	/**
	 * '��� �ִ� ĭ'�� ��Ÿ���� ���� �ν��Ͻ��Դϴ�.
	 * ��� �����Ƿ� �� ĭ���� �ƹ��� ����, ���� �� ĭ������ � �ൿ/��ǵ� �߻����� �ʽ��ϴ�.
	 * �׷����� �ұ��ϰ� '��� �ִ� ĭ'�� ��¶�� '��ȿ�� ĭ'�̹Ƿ�,
	 * ���� ���� ��ȸ �޼������ ����� ������ �õ��ص� ������ ���� �ʽ��ϴ�.
	 * �׷���, �������� �� �ʵ带 ���������� ����Ͽ� if �˻縦 �� ���� ���� ���� ���Դϴ�.
	 */
	public static final CellInfo Blank;

	/*
	 * Note: Ŭ���� ������ static �ϰ� �ٷ� �߰�ȣ ������ �κ���
	 * �� Ŭ������ static �ʵ带 �ʱ�ȭ�ϱ� ���� '���� ������'���� �κ��Դϴ�.
	 * static �ʵ�� C�� ���� �����ϰ� '���α׷��� ���۵� ��' ��������Ƿ�
	 * �� �߰�ȣ�� ���� ���� �츮�� Ctrl + F11 ���� �� �ٷ� ����˴ϴ�.
	 * ���������, �� �κ��� �Ű澲�� �ʾƵ� �˴ϴ�.
	 */
	static
	{
		Blank = new CellInfo(new ArrayList<PlayerInfo>(), new ArrayList<Action>(), new ArrayList<Reaction>());
	}

	/* 
	 * �Ʒ��� �� ��� �ʵ�� private�̹Ƿ� �������� �� �� �����ϴ�.
	 * ���, �������� �� ���� �ϴܿ��� �����ϰ� �ִ� ������ ��ȸ �޼��带 ����
	 * �� ����� ������ �׼����ϸ� ������ �����͸� �̾� �� �� �ֽ��ϴ�.
	 */
	private final ArrayList<PlayerInfo> players;
	private final ArrayList<Action> actions;
	private final ArrayList<Reaction> reactions;
	
	/**
	 * ����: �� Ŭ������ �ν��Ͻ��� �������� ���� ���� ���� �ʿ䰡 �����ϴ�.
	 * 'Ư�� ĭ�� �ִ� �÷��̾� ���'�� ���� ���� ��� Select()�� ������.
	 * ������ ��κ��� ��� �׳� CountIf()�� ������ ȣ���ϴ� �� �����ε� ���ϴ� ����� ���� �� ���� ���Դϴ�.
	 */
	public CellInfo(ArrayList<PlayerInfo> players, ArrayList<Action> actions, ArrayList<Reaction> reactions)
	{
		this.players = players;
		this.actions = actions;
		this.reactions = reactions;
	}

	/* -------------------------------------------------------
	 * '��ȸ �޼���' ���� �κ�
	 */

	/**
	 * �� ĭ�� �ִ� �� �÷��̾� ���� ��ȯ�մϴ�.
	 */
	public int Count_Players()
	{
		return players.size();
	}

	/**
	 * �� ĭ�� �ִ� �÷��̾�� �� �־��� ���ǿ� �´� �÷��̾� ���� ��ȯ�մϴ�.
	 * 
	 * @param condition
	 *            �÷��̾� ������ �μ��� �޾� �˻� �� true/false�� ��ȯ�ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public int CountIf_Players(Predicate<PlayerInfo> condition)
	{
		int count = 0;

		for ( PlayerInfo player : players )
			if ( condition.test(player) == true )
				++count;

		return count;
	}

	/**
	 * �� ĭ�� �ִ� �÷��̾�� ������ ���� �־��� �޼��带 �����մϴ�.
	 * �̰� ����ü ���� ����� ���� ������ �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 * 
	 * @param action
	 *            �÷��̾� ������ �μ��� �ް� Ư�� �۾��� �����ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public void ForEach_Players(Consumer<PlayerInfo> action)
	{
		players.forEach(action);
	}

	/**
	 * �� ĭ�� �ִ� �÷��̾�� �� �־��� ���ǿ� �´� �÷��̾�鸸 ��� ������� ����� ��ȯ�մϴ�.
	 * 
	 * @param condition
	 *            �÷��̾� ������ �μ��� �޾� �˻� �� true/false�� ��ȯ�ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public ArrayList<PlayerInfo> Select_Players(Predicate<PlayerInfo> condition)
	{
		ArrayList<PlayerInfo> result = new ArrayList<PlayerInfo>();

		for ( PlayerInfo player : players )
			if ( condition.test(player) == true )
				result.add(player);

		return result;
	}

	/**
	 * �� ĭ���� ����� �� �ൿ ���� ��ȯ�մϴ�.
	 */
	public int Count_Actions()
	{
		return actions.size();
	}

	/**
	 * �� ĭ���� ����� �ൿ�� �� �־��� ���ǿ� �´� �ൿ ���� ��ȯ�մϴ�.
	 * 
	 * @param condition
	 *            �ൿ ������ �μ��� �޾� �˻� �� true/false�� ��ȯ�ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public int CountIf_Actions(Predicate<Action> condition)
	{
		int count = 0;

		for ( Action action : actions )
			if ( condition.test(action) == true )
				++count;

		return count;
	}

	/**
	 * �� ĭ���� ����� �ൿ�� ������ ���� �־��� �޼��带 �����մϴ�.
	 * �̰� ����ü ���� ����� ���� ������ �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 * 
	 * @param action
	 *            �ൿ ������ �μ��� �ް� Ư�� �۾��� �����ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public void ForEach_Actions(Consumer<Action> action)
	{
		actions.forEach(action);
	}

	/**
	 * �� ĭ���� ����� �ൿ�� �� �־��� ���ǿ� �´� �ൿ�鸸 ��� ������� ����� ��ȯ�մϴ�.
	 * 
	 * @param condition
	 *            �ൿ ������ �μ��� �޾� �˻� �� true/false�� ��ȯ�ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public ArrayList<Action> Select_Actions(Predicate<Action> condition)
	{
		ArrayList<Action> result = new ArrayList<Action>();

		for ( Action action : actions )
			if ( condition.test(action) == true )
				result.add(action);

		return result;
	}


	/**
	 * �� ĭ���� �߻��� �� ��� ���� ��ȯ�մϴ�.
	 */
	public int Count_Reactions()
	{
		return reactions.size();
	}

	/**
	 * �� ĭ���� �߻��� ��ǵ� �� �־��� ���ǿ� �´� ��� ���� ��ȯ�մϴ�.
	 * 
	 * @param condition
	 *            ��� ������ �μ��� �޾� �˻� �� true/false�� ��ȯ�ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public int CountIf_Reactions(Predicate<Reaction> condition)
	{
		int count = 0;

		for ( Reaction reaction : reactions )
			if ( condition.test(reaction) == true )
				++count;

		return count;
	}
	
	/**
	 * �� ĭ���� �߻��� ��ǵ� ������ ���� �־��� �޼��带 �����մϴ�.
	 * �̰� ����ü ���� ����� ���� ������ �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 * 
	 * @param action
	 *            ��� ������ �μ��� �ް� Ư�� �۾��� �����ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public void ForEach_Reactions(Consumer<Reaction> action)
	{
		reactions.forEach(action);
	}

	/**
	 * �� ĭ���� �߻��� ��ǵ� �� �־��� ���ǿ� �´� ��ǵ鸸 ��� ������� ����� ��ȯ�մϴ�.
	 * 
	 * @param condition
	 *            ��� ������ �μ��� �޾� �˻� �� true/false�� ��ȯ�ϴ� �޼��� �Ǵ� ���� ���� ��������.
	 *            ���� ���� ��� ����� �ñ��ϸ� Developer's Guide ������ �����ϼ���.
	 */
	public ArrayList<Reaction> Select_Reactions(Predicate<Reaction> condition)
	{
		ArrayList<Reaction> result = new ArrayList<Reaction>();

		for ( Reaction reaction : reactions )
			if ( condition.test(reaction) == true )
				result.add(reaction);

		return result;
	}
}
