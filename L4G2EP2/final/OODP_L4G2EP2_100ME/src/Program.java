import java.awt.Color;

import l4g.Classroom;
import l4g.Grader;
import l4g.Presenter_Mode3;
import l4g.Presenter_Mode5;
import l4g.Presenter_Mode4;
import l4g.common.Classroom_Settings;
import l4g.common.GameNumberManager;
import l4g.customplayers.*;
import loot.GameFrameSettings;


public class Program
{
	public static void main(String[] args)
	{
		//settings�� ���ǽ� ����(���� �� �� ����)�� ���� ���� �ʵ���� ������ �ֽ��ϴ�.
		Classroom_Settings settings = new Classroom_Settings();
		
		/*
		 * mode���� �ٲ����ν� ������ ���� ���� ������� ������ �� �� �ֽ��ϴ�.
		 * 
		 * mode����	0�̸� �ܼ� â�� ����Ͽ� ���� �� ���� �����ϸ�
		 * 			�� �ϸ��� ù �÷��̾�(�Ƹ��� �������� ���� �� �÷��̾�)�� ���� �ǻ� ������ ����,
		 * 			� �ൿ�� �ߴ���, �þ� �������� � ��ǵ��� �߻��ߴ����� ����ϰ�,
		 *          �� ����� ù �÷��̾��� ���� ���� �� ������ ����� ����
		 *			���� Ű�� �Է¹��� ������ ������ �Ͻ� �����մϴ�. 
		 * 			������ ������ ù �÷��̾��� ������ ����մϴ�.
		 * 
		 * mode���� 1�̸� �ܼ� â�� ����Ͽ� ���� �� ���� �����ϸ� 
		 * 			�� �ϸ��� ��� �ൿ, ��� ������ ����ϰ�, 
		 * 			�� ����� ��� �÷��̾��� ���� ���� �� ������ ����� ���� 
		 *			���� Ű�� �Է¹��� ������ ������ �Ͻ� �����մϴ�. 
		 * 			������ ������ ��� �÷��̾��� ������ ����մϴ�.
		 * 
		 * mode���� 2�� �ܼ� â�� ����Ͽ� ������ ���� ��ȣ���� ���� ���� 1,000���� �����ϸ�
		 * 			������ ��� ������ �� �ι��� ���� �� ����� ����� ����
		 * 			ù �÷��̾�(�Ƹ��� �������� ���� �� �÷��̾�)�� ����� ����մϴ�.
		 * 
		 * mode���� 3�̸� �׽�Ʈ�� Presenter�� ����Ͽ� ���� �� ���� �����ϸ�
		 *			�̸� ���� ù �÷��̾�(�Ƹ��� �������� ���� �� �÷��̾�)�� ȹ���� �� �ִ� �������� �� �ϸ��� �ð������� ���� �� �� �ֽ��ϴ�.
		 *			������ ������ �ܼ� â�� ù �÷��̾��� ������ ����մϴ�.
		 *			������ ���� ���Ŀ��� ���� Ű�� ġ�� �� �ٽ� ������ �� �ֽ��ϴ�.
		 *
		 * mode���� 4�� ���÷��̿� Presenter�� ����Ͽ� �Ʒ����� ������ Ư�� ���� ��ȣ�� ���� �� ���� �����ϸ�
		 * 			mode 3�� ������ ������� �ش� �ǿ� ���� �پ��� �������� �ð������� ���� �� �� �ֽ��ϴ�.
		 *
		 * mode���� 5�� ���� ���� Presenter�� ����Ͽ� ���� �� ���� �����մϴ�.
		 * 			Ű���� / ���콺�� ���� �������� ���� �÷��̾��� ��ſ� �ð��� ���� �� �ֽ��ϴ�!
		 */
		
		int mode = 5;

		

		/*
		 * �Ʒ��� ���� ��ȣ�� -1�� �ƴ� �ٸ� ������ �����ϸ�
		 * mode���� 0, 1, 3, 4, 5�� ��
		 * �׻� �ش� ���� ��ȣ�� ����Ͽ� ������ �����մϴ�.
		 * ����: ���� ��ȣ�� long �����̹Ƿ� ����� �ڿ� L�� �� �ٿ� �ּ���.
		 * 
		 * TODO �Ʒ��� �������� ���� ���� ���� ���� ��ȣ�� ���� �ּ���. �Ǵ�, ������ �� ������ ������ ������ ��� -1�� ������ �ּ���.
		 */
		settings.game_number = -1L;
		
		
		/*
		 * TODO mode ���� 0, 1, 2, 3, 4�� ��
		 * �Ʒ��� �ڵ忡�� Player_���۵��ϴ�_ġ���κ� �κ��� �������� �ۼ��� �÷��̾� Ŭ���� �̸����� �ٲ� �ּ���.
		 * �׷��� �ϸ� �ش� �÷��̾��� �������� ���� ������ ǥ�õ˴ϴ�!
		 */
		settings.playerToFocus = Player_���۵��ϴ�_ġ���κ�.class;
		
		
		
		
		
		
		
		
		
		
		
		/* --------------------------------------------------------------------------------------------------------
		 * --------------------------------------------------------------------------------------------------------
		 * --------------------------------------------------------------------------------------------------------
		 * --------------------------------------------------------------------------------------------------------
		 * 
		 * ����!
		 * 
		 * �Ʒ��� �������� �ٲٸ� '���� ����'�� �ٸ� ����� ������ �˴ϴ�!
		 * ����� �� ���÷��̸� ���� �ʹٸ� �Ʒ� ������ ���� �ǵ帮�� ���� �ּ���!
		 * 
		 */
		
		
		
		/*
		 * ���� ���� �÷��̾ ����ϴ� �κ��Դϴ�.
		 * �ڽ��� ���� �÷��̾� Ŭ������ �Ʒ��� �ּ� ������ �����Ͽ� ����ϼ���.
		 * (�ּ� ������ ������ ���� Player_YOURNAMEHERE ��� �������� �÷��̾� Ŭ���� �̸��� ������ �˴ϴ�)
		 * 
		 * ����: ���� Ŭ������ ���� �� ����ϴ� �͵� �����մϴ�.
		 * 		 ������ �̷��� �ϴ� ��� ���� �÷��̾ �׻� ���� �ǻ� ������ �ϰ� �� �� ������
		 * 		 ������ �� Ŭ������ �� ���� ����ϴ� ���� ���ڽ��ϴ�.
		 */
		
		// settings.custom_player_classes.add(Player_YOURNAMEHERE.class);

		// team Mu, Omega, Lambda �÷��̾� �κ� ( ����ð� ����, �� 83�� )
		settings.custom_player_classes.add(Player_������_����������.class);
		settings.custom_player_classes.add(Player_niangniangpunch.class);
		settings.custom_player_classes.add(��������.class);
		settings.custom_player_classes.add(Player_Jeon_Hyeong_Jun.class);
		settings.custom_player_classes.add(Player_DongSimOne.class);
		
		settings.custom_player_classes.add(Player_MIDSIN.class);
		settings.custom_player_classes.add(Player_YangSangheon.class);
		settings.custom_player_classes.add(Player_run_survive.class);
		settings.custom_player_classes.add(Jerod.class);
		settings.custom_player_classes.add(Player_����������_������.class);
		
		// 10 ~ 19
		settings.custom_player_classes.add(Player_PuddingHell.class);		
		settings.custom_player_classes.add(SangU.class);
		settings.custom_player_classes.add(Ramyeon.class);
		settings.custom_player_classes.add(Player_�ȳ�κ�.class);
		settings.custom_player_classes.add(Player_Horse_Save3.class);

		settings.custom_player_classes.add(LowerBodyParalysis.class);
		settings.custom_player_classes.add(Player_All_State_Stability.class);
		settings.custom_player_classes.add(Player_Jim_Raynor.class);
		settings.custom_player_classes.add(�ű���ٱ�.class);
		settings.custom_player_classes.add(Player_COUPROACH.class);
		

		// 20 ~ 29
		settings.custom_player_classes.add(Player_Hunter_Killer.class);
		settings.custom_player_classes.add(Player_�����̳������ϸ�������ڽ��ϴ�.class);
		settings.custom_player_classes.add(Player_HYCat.class);
		settings.custom_player_classes.add(Player_Ori.class);
		settings.custom_player_classes.add(���¿�.class);

		settings.custom_player_classes.add(Player_MJ_.class);
		settings.custom_player_classes.add(����.class);
		settings.custom_player_classes.add(Player_NO_ANSWER.class);
		settings.custom_player_classes.add(Player_Four_Walls.class);
		settings.custom_player_classes.add(Player_Yong.class);

		
		// 30 ~ 39
		settings.custom_player_classes.add(OhGod.class);
		settings.custom_player_classes.add(TheGivingTree.class);
		settings.custom_player_classes.add(Fittest.class);
		settings.custom_player_classes.add(Player_2012004270.class);
		settings.custom_player_classes.add(Player_Baggio.class);

		settings.custom_player_classes.add(Nintendojam.class);
		settings.custom_player_classes.add(Player_LenaPark.class);
		settings.custom_player_classes.add(Player_��Ư¡��Ư¡.class);
		settings.custom_player_classes.add(Player_õ��ҳ��Ƽ.class);
		settings.custom_player_classes.add(Player_Arous_best.class);

		
		// 40 ~ 49
		settings.custom_player_classes.add(Player_Jisu.class);
		settings.custom_player_classes.add(Type_O.class);
		settings.custom_player_classes.add(Player_WinTreeC.class);
		settings.custom_player_classes.add(SeunDuBuJjiGae.class);
		settings.custom_player_classes.add(��������Ƽġ_�����ſ���������.class);
		
		settings.custom_player_classes.add(Player_Jaeseok.class);
		settings.custom_player_classes.add(Player_Bravo.class);
		settings.custom_player_classes.add(Player_CanYouFeelMyHeartBeat.class);
		settings.custom_player_classes.add(Player_Corner.class);
		settings.custom_player_classes.add(Player_Dont_Let_Him_Gamble.class);

		
		// 50 ~ 59
		settings.custom_player_classes.add(I_DO_NOT_LIKE_JAVA.class);
		settings.custom_player_classes.add(Player_������.class);
		settings.custom_player_classes.add(Player_Jiwon.class);
		settings.custom_player_classes.add(Player_GradeGiver.class);
		settings.custom_player_classes.add(DingDong.class);

		settings.custom_player_classes.add(Player_DONT_CODES_AMEN.class);		
		settings.custom_player_classes.add(MissingNo.class);
		settings.custom_player_classes.add(Player_JEONGMIN.class);
		settings.custom_player_classes.add(Player_�̸��ҰԾ���.class);
		settings.custom_player_classes.add(KIMAEHO2.class);

		
		// 60 ~ 69
		settings.custom_player_classes.add(corpse_king.class);
		settings.custom_player_classes.add(Player_LSHf1.class);
		settings.custom_player_classes.add(Byun.class);
		settings.custom_player_classes.add(Player_yaso.class);
		settings.custom_player_classes.add(Player_lee.class);

		settings.custom_player_classes.add(Player_BotBoot.class);
		settings.custom_player_classes.add(Player_ANG.class);
		settings.custom_player_classes.add(Player_INHWA.class);
		settings.custom_player_classes.add(Player_2010003969.class);
		settings.custom_player_classes.add(�����ǿ����������.class);

		
		// 70 ~ 79
		settings.custom_player_classes.add(Player_������ù���.class);
		settings.custom_player_classes.add(SMP.class);
		settings.custom_player_classes.add(Player_������.class);
		settings.custom_player_classes.add(Player_Charles.class);
		settings.custom_player_classes.add(Player_Soyoung2.class);
		
		settings.custom_player_classes.add(Player_I_Like_stroll.class);
		settings.custom_player_classes.add(Player_Songjunggi.class);
		settings.custom_player_classes.add(Player_��ȣ����.class);
		settings.custom_player_classes.add(TEJAVA.class);
		settings.custom_player_classes.add(Player_ONEROUTE.class);
		
		
		// 80 ~ 82
		settings.custom_player_classes.add(Jinhyeok.class);
		settings.custom_player_classes.add(Player_PreferName.class);
		settings.custom_player_classes.add(Player_PineApple.class);
		
		
		// team Sigma �÷��̾� �κ� (100�ڸ� �� ���� �ڸ���ŭ �߰�, �� 17��)
		settings.custom_player_classes.add(Player_���۵��ϴ�_ġ���κ�.class);
		settings.custom_player_classes.add(Player_�����1.class);
		settings.custom_player_classes.add(Player_�����2.class);
		settings.custom_player_classes.add(Player_�����3.class);
		settings.custom_player_classes.add(Player_����_��Ż��.class);

		settings.custom_player_classes.add(Player_���������2.class);
		settings.custom_player_classes.add(Player_���������1.class);
		settings.custom_player_classes.add(Player_���������3.class);
		settings.custom_player_classes.add(Player_�����Ա�_����.class);
		settings.custom_player_classes.add(Player_������.class);

		settings.custom_player_classes.add(Player_ROOT.class);
		settings.custom_player_classes.add(Player_Yandere.class);
		settings.custom_player_classes.add(Player_PIMFY1.class);
		settings.custom_player_classes.add(Player_PIMFY2.class);
		settings.custom_player_classes.add(Player_PIMFY3.class);
		
		settings.custom_player_classes.add(Player_PIMFY4.class);
		settings.custom_player_classes.add(Player_PIMFY5.class);
		
		/*
		 * ���� ���ӿ��� NPC(���� ������ ������ ���� �ʴ� �÷��̾�) ���� �����ϴ� �κ��Դϴ�.
		 * �������� �÷��̾ �׽�Ʈ�� �� �� �ʵ��� ���� �׳� 0���� �μ���. 
		 */		
		settings.numberOfNPCs = 100 - 83;
		
		/*
		 * ���ӿ� ������ '������ Bot �÷��̾� ��'�� �ִ밪�� ������ �� �ֽ��ϴ�.
		 * 0 ���Ϸ� �θ� �����ڴ� ���ӿ� �������� �ʽ��ϴ�.
		 * 
		 * '���� ����'���� �� ���� 0���� �����˴ϴ�.  
		 */
		settings.max_numberOfHornDonePlayer = 0;
		
		/*
		 * ���ӿ� ������ Bot �÷��̾���� ������ ������ ���� �� ����ϴ�
		 * seed ���ڿ��� �����ϴ� �κ��Դϴ�.
		 * 
		 * �⺻���� "16OODP"�̸�
		 * �̸� �ٸ� ���ڿ��� �ٲٴ� ��� ���ӿ� ���� ������ Bot �÷��̾� ��ϵ� �ٲ�� �˴ϴ�.
		 * 
		 * �ڽ��� �÷��̾ ���� ���� �÷��̾� ���� �ȿ��� �׽�Ʈ�� ������ ���
		 * "16OODP" ��� �ٸ� ���ڿ��� ������ ������.
		 * 
		 * ����: ���� �� ���ڿ��� '�� ���ڿ�' �Ǵ� null�� �����ϴ� ���
		 * 		 ���ǽ��� �ʱ�ȭ�� �� �ٽ� �⺻������ �����˴ϴ�.
		 */
		settings.seed_for_sample_players = "16OODP";		
		
		/*
		 * �Ʒ� �������� true�� �θ�
		 * ���� ���� ���� �÷��̾ ��Ÿ�� ���ܸ� ���� ���
		 * �ش� ���ܿ� ���� ������ �ܼ� â�� ����մϴ�.
		 * 
		 * �̰� �̿ܿ��� settings���� �پ��� ���� �ɼǵ��� ��� ������
		 * �Ʒ��� �� mode�� ���� �κ��� ������ �����Ͽ�
		 * ������ �Ը��� �´� �׽�Ʈ�� ������ �� �� �ֽ��ϴ�.
		 */
		settings.print_errors = false;
		
		
		
		
		
		/* --------------------------------------------------------------------------------------------
		 * 
		 * �� �Ʒ����� �� ��庰 ���� �ڵ尡 �����Ǿ� �ֽ��ϴ�.
		 * �׽�Ʈ ȯ���� �� �Ը���� �����ϰ� ���� �� �ƴ϶��
		 * �� �Ʒ��� �ִ� ������ �� ���� �˴ϴ�.
		 * 
		 */

		if ( mode == 0 )
		{
			settings.print_focused_player_only = true;
			settings.print_decisions = true;
			settings.print_actions = true;
			settings.print_reactions = true;
			settings.print_playerInfos = true;
			settings.print_scores_at_each_turns = true;
			settings.print_scores_at_the_end = true;
			
			settings.callback_EndTurn = () ->
			{
				try
				{
					System.out.print("Press Enter to continue...");
					System.in.skip(System.in.available());
					System.in.read();
				}
				catch ( Exception e )
				{
					
				}
				return true;
			};
			
			Classroom classroom = new Classroom(settings);
			classroom.Initialize();
			classroom.Start();
		}
		
		if ( mode == 1 )
		{
			settings.print_focused_player_only = false;
			settings.print_decisions = false;
			settings.print_actions = false;
			settings.print_reactions = false;
			settings.print_playerInfos = false;
			settings.use_console_mode = true;
			settings.print_scores_at_each_turns = false;
			settings.print_scores_at_the_end = true;
			
			settings.callback_EndTurn = () ->
			{
				try
				{
					System.out.print("Press Enter to continue...");
					System.in.skip(System.in.available());
					System.in.read();
				}
				catch ( Exception e )
				{
					
				}
				return true;
			};
			
			Classroom classroom = new Classroom(settings);
			classroom.Initialize();
			classroom.Start();
		}
		
		if ( mode == 2 )
		{
			GameNumberManager numbers = new GameNumberManager(1000);
			numbers.Create(-1);
			
			Grader grader = new Grader();
			
			settings.use_console_mode = false;

			Classroom classroom = null;
			
			for ( int iGame = 0; iGame < 1000; ++iGame )
			{
				if ( iGame % 100 == 0 )
					System.out.println(iGame + " / 1000 games completed...");
				settings.game_number = numbers.data[iGame];
				classroom = new Classroom(settings);
				classroom.Initialize();
				classroom.Start();
				grader.Update(classroom);
			}

			System.out.println("Done.");
			
			grader.PrintResults(classroom);
			grader.PrintResultsOf(0, classroom);
		}
		
		if ( mode == 3 )
		{
			settings.print_focused_player_only = true;
			settings.print_decisions = false;
			settings.print_actions = false;
			settings.print_reactions = false;
			settings.print_playerInfos = false;
			settings.print_scores_at_each_turns = false;
			settings.print_scores_at_the_end = true;
			
			GameFrameSettings gfs = new GameFrameSettings();
			gfs.window_title = "L4G2EP2-100ME �׽�Ʈ�� Presenter";
			gfs.canvas_width = 1080;
			gfs.canvas_height = 850;
			gfs.numberOfButtons = 20;
			gfs.canvas_backgroundColor = Color.lightGray;
			
			Presenter_Mode3 window = new Presenter_Mode3(gfs, settings);
			window.setVisible(true);
		}
		
		if ( mode == 4 )
		{
			settings.print_focused_player_only = true;
			settings.print_decisions = false;
			settings.print_actions = false;
			settings.print_reactions = false;
			settings.print_playerInfos = false;
			settings.print_scores_at_each_turns = false;
			settings.print_scores_at_the_end = true;
			
			GameFrameSettings gfs = new GameFrameSettings();
			gfs.window_title = "L4G2EP2-100ME ���÷��̿� Presenter";
			gfs.canvas_width = 1080;
			gfs.canvas_height = 850;
			gfs.numberOfButtons = 20;
			gfs.canvas_backgroundColor = Color.lightGray;
			
			Presenter_Mode4 window = new Presenter_Mode4(gfs, settings);
			window.setVisible(true);
		}
		
		if ( mode == 5 )
		{
			settings.print_focused_player_only = true;
			settings.print_decisions = false;
			settings.print_actions = false;
			settings.print_reactions = false;
			settings.print_playerInfos = false;
			settings.print_scores_at_each_turns = false;
			settings.print_scores_at_the_end = false;
			settings.use_console_mode = false;
			
			// ������ �÷��̾�(team Sigma)�� �����ϰ� ���� ���� ������ �÷��̾� ž��
			settings.custom_player_classes.remove(settings.custom_player_classes.size() - 1);
			--settings.numberOfNPCs;
			settings.use_ctrlable_player = true;
			
			GameFrameSettings gfs = new GameFrameSettings();
			gfs.window_title = "Left 4 Grade 2 Episode 2 - 100ME";
			gfs.canvas_width = 1080;
			gfs.canvas_height = 850;
			gfs.numberOfButtons = 22;
			gfs.canvas_backgroundColor = Color.lightGray;
			
			Presenter_Mode5 window = new Presenter_Mode5(gfs, settings);
			window.setVisible(true);			
		}
	}
}

