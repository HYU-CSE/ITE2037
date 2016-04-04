import l4g2ep1.*;
import l4g2ep1.common.*;
import l4g2ep1.customplayers.*;

public class Program
{
	public static void main(String[] args)
	{
		Classroom_Settings settings = new Classroom_Settings();

		/*
		 * mode���� �ٲ����ν� ������ ���� ���� ������� ������ �� �� �ֽ��ϴ�.
		 * 
		 * mode����	0�̸� �ܼ� â�� ����Ͽ� ���� �� ���� �����ϸ�
		 * 			ù �÷��̾�(�Ƹ��� �������� ���� �� �÷��̾�)�� ������ �ൿ / ��� / ������ �� �� ����ϰ�
		 * 			������ ������ ù �÷��̾��� ������ ����մϴ�.
		 * 
		 * mode���� 1�̸� �ܼ� â�� ����Ͽ� ���� �� ���� �����ϸ�
		 * 			���� ���߿��� �ƹ� ������ ������� �ʴٰ�
		 * 			������ ������ ��� �÷��̾��� ������ ����մϴ�.
		 * 
		 * mode���� 2�� �ܼ� â�� ����Ͽ� ������ ���� ��ȣ���� ���� ���� 10,000���� �����ϸ�
		 * 			������ ��� ������ �� �ι��� ���� �� ����� ����� ����
		 * 			ù �÷��̾�(�Ƹ��� �������� ���� �� �÷��̾�)�� ����� ����մϴ�.
		 * 
		 * mode���� 3�̸� �ܼ� â�� ����Ͽ� ���� ���ӿ��� ����� ���� ��ȣ���� ���� ���� 10,000���� �����ϸ�
		 * 			������ ��� ������ �� �ι��� ���� �� ����� ����մϴ�.
		 * 
		 * mode���� 4�� �׽�Ʈ�� Presenter�� ����Ͽ� ���� �� �� �Ǵ� 100���� �����ϸ�
		 *			�̸� ���� ���ϴ� ���� ��ȣ�� �Է��ϰ� ��ư�� ������ ���� ���� ��Ȳ�� �ð������� Ȯ���� ���ų�
		 *			������ ���� ������ �����ϸ� �ڽ��� �÷��̾ ���� / ���� ������ �޴� ���� ��ȣ�� ã�� �м��� �� �� �ֽ��ϴ�. 
		 *			(�׽�Ʈ�� Presenter�� Start ��ư�� ���� ������ �� ���ǽ��� �����ϹǷ� �� �� �� ���� ���� ������ ������ �� �� �ֽ��ϴ�)
		 *			�׽�Ʈ�� Presenter ��� ����� ������Ʈ ���� �� �Ʒ��� �ִ� '���� ����' ������ �����ϼ���.
		 *
		 * mode���� 5�� �濬�� Presenter�� ����Ͽ� ���� �� �� �Ǵ� 10,000���� �����ϸ�
		 * 			�̸� ���� ���ϴ� ���� ��ȣ�� �Է��ϰ� ��ư�� ������ ���� ���� ��Ȳ�� �ð������� Ȯ���� ���ų�
		 * 			���� ������ �����ϸ� �� �û� �׸� ������ Ȯ���� �� �� �ֽ��ϴ�. 
		 * 			�濬�� Presenter ��� ����� ������Ʈ ���� �� �Ʒ��� �ִ� '���� ����' ������ �����ϼ���.
		 * 
		 * mode���� 6�̸� ���� Presenter�� ����Ͽ� ������ ���� �� ���� ������ �� �ֽ��ϴ�!
		 * 
		 * TODO �ڽ��� ���ϴ� ������� ������ ������ �� �ֵ��� �Ʒ��� mode ���� �ٲټ���.
		 */
		
		int mode = 6;

		
		/*
		 * ----------------------------------------------------------------------------------------------------------------
		 * 
		 * TODO '�º� ����'�� ���� ���� ��ȣ�� ������Ű�� ������� �븮���� �Ʒ��� �ڵ忡�� ���� ���� ��ȣ(0 �Ǵ� ���)�� �����ϼ���.
		 * 
		 * ----------------------------------------------------------------------------------------------------------------
		 */
		
		settings.game_number = -1;
		
		
		/*
		 * ���� ���� �÷��̾ ����ϴ� �κ��Դϴ�.
		 * 
		 * TODO �ڽ��� ���� �÷��̾� Ŭ������ �Ʒ��� �ּ� ������ �����Ͽ� ����ϼ���.
		 * 		 (�ּ� ������ ������ ���� Player_YOURNAMEHERE ��� �������� �÷��̾� Ŭ���� �̸��� ������ �˴ϴ�)
		 * 
		 * ����: ���� Ŭ������ ���� �� ����ϴ� �͵� �����մϴ�.
		 * 		 ������ �̷��� �ϴ� ��� ���� �÷��̾ �׻� ���� �ǻ� ������ �ϰ� �� �� ������
		 * 		 ������ �� Ŭ������ �� ���� ����ϴ� ���� ���ڽ��ϴ�.
		 * 		 
		 */
		
		//settings.custom_player_classes.add(Player_YOURNAMEHERE.class);
		
		settings.custom_player_classes.add(Player_TA.class);
		settings.custom_player_classes.add(Hungry.class);
		settings.custom_player_classes.add(Player_Rune.class);
		settings.custom_player_classes.add(Player_������.class);
		settings.custom_player_classes.add(KBJ.class);

		settings.custom_player_classes.add(slave.class);
		settings.custom_player_classes.add(Player_King.class);
		settings.custom_player_classes.add(Player_JangHo.class);
		settings.custom_player_classes.add(Player_JiWon.class);
		settings.custom_player_classes.add(Player_��ĳ.class);
		
		settings.custom_player_classes.add(Player_2008027687.class);
		settings.custom_player_classes.add(Player_JY.class);
		settings.custom_player_classes.add(Player_Druwa.class);
		settings.custom_player_classes.add(Player_JeongYunhee.class);
		settings.custom_player_classes.add(Player_lsh.class);
		
		settings.custom_player_classes.add(SIRA.class);
		settings.custom_player_classes.add(Player_DIA1TEAR.class);
		settings.custom_player_classes.add(Player_YOURNAMEHERE.class);
		settings.custom_player_classes.add(Player_seohui.class);
		settings.custom_player_classes.add(Player_ZombieStalker.class);
		
		settings.custom_player_classes.add(Player_HONG.class);
		settings.custom_player_classes.add(Player_joonwook.class);
		settings.custom_player_classes.add(Player_����.class);
		settings.custom_player_classes.add(�����.class);
		settings.custom_player_classes.add(Player_Yoann.class);
		
		settings.custom_player_classes.add(Incorruptible_Corpse.class);
		settings.custom_player_classes.add(Versatile75.class);
		settings.custom_player_classes.add(��������������.class);
		settings.custom_player_classes.add(Player_GGYUBBYU.class);
		settings.custom_player_classes.add(Player_Bonus.class);
		
		settings.custom_player_classes.add(Player_one.class);
		settings.custom_player_classes.add(Player_SalesMan.class);
		settings.custom_player_classes.add(Player_JyounG.class);
		settings.custom_player_classes.add(Jitwo.class);
		settings.custom_player_classes.add(Hyerim.class);
		
		settings.custom_player_classes.add(Player_DKpal.class);
		settings.custom_player_classes.add(Player_2012004678_YoungJu_YOON.class);
		settings.custom_player_classes.add(����.class);
		
		
		
		/*
		 * ���ӿ� ������ ���� �÷��̾���� ������ ������ ���� �� ����ϴ�
		 * seed ���ڿ��� �����ϴ� �κ��Դϴ�.
		 * 
		 * �⺻���� "14OOP"�̸�
		 * �̸� �ٸ� ���ڿ��� �ٲٴ� ��� ���ӿ� ���� ������ ���� �÷��̾� ��ϵ� �ٲ�� �˴ϴ�.
		 * 
		 * TODO �ڽ��� �÷��̾ ���� ���� �÷��̾� ���� �ȿ��� �׽�Ʈ�� ������ ���
		 * 		 "14OOP" ��� �ٸ� ���ڿ��� ������ ������.
		 * 
		 * ����: ���� �� ���ڿ��� '�� ���ڿ�' �Ǵ� null�� �����ϴ� ���
		 * 		 ���ǽ��� �ʱ�ȭ�� �� �ٽ� �⺻������ �����˴ϴ�.
		 * 
		 */
		settings.seed_for_sample_players = "14OOP";
		
		
		
		
		/* ***************************************************************
		 * 
		 * 
		 * ���� �� �Ʒ����ʹ� ũ�� �Ű澲�� �ʾƵ� �˴ϴ�.
		 * 
		 * 
		 */
		
		if ( mode == 0 )
		{
			Classroom classroom = new Classroom(settings);
			classroom.run();
		}
		
		if ( mode == 1 )
		{
			settings.print_actions = false;
			settings.print_first_player_only = false;
			settings.print_playerInfos = false;
			settings.print_reactions = false;
			settings.print_scores_during_game = false;

			Classroom classroom = new Classroom(settings);
			classroom.run();
		}
		
		if ( mode == 2 )
		{
			GameNumbers numbers = new GameNumbers(10000);
			numbers.Create(-1);
			
			Grader grader = new Grader();
			
			settings.use_console_mode = false;

			Classroom classroom = null;
			
			for ( int iGame = 0; iGame < 10000; ++iGame )
			{
				settings.game_number = numbers.data[iGame];
				classroom = new Classroom(settings);
				classroom.run();
				grader.Update(settings.game_number, classroom.scoreboard);
			}
			
			grader.PrintResults(classroom);
			grader.PrintResultsOf(0, classroom);
		}
		
		if ( mode == 3 )
		{
			GameNumbers numbers = new GameNumbers(10000);
			numbers.Load("10000numbers.txt");
			
			Grader grader = new Grader();
			
			settings.use_console_mode = false;

			Classroom classroom = null;
			
			for ( int iGame = 0; iGame < 10000; ++iGame )
			{
				settings.game_number = numbers.data[iGame];
				classroom = new Classroom(settings);
				classroom.run();
				grader.Update(settings.game_number, classroom.scoreboard);
			}
			
			grader.PrintResults(classroom);
		}
		
		if ( mode == 4 )
		{
			settings.use_console_mode = false;
			
			Presenter_Mode4 presenter = new Presenter_Mode4(settings);
			presenter.setVisible(true);
		}
		
		if ( mode == 5 )
		{
			settings.use_console_mode = false;
			settings.print_errors = false;
			Presenter_Mode5 presenter = new Presenter_Mode5(settings);
			presenter.setVisible(true);
		}
		
		if ( mode == 6 )
		{
			settings.use_console_mode = false;
			settings.use_ctrlable_player = true;
			
			settings.custom_player_classes.remove(0);
			
			Presenter_Mode6 presenter = new Presenter_Mode6(settings);
			presenter.setVisible(true);
		}		
	}
}
