package l4g;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.jar.Attributes.Name;

import l4g.Decision.TypeCode;
import l4g.bots.*;
import l4g.common.*;
import l4g.data.*;

import TEST.TEST;

/**
 * �� �÷��̾ ���� �� �����ϸ� ������ �����ϴ� ���ǽ��� ��Ÿ���ϴ�.
 * �÷��̾� �ν��Ͻ�, ���� ����, ���� ���࿡ ������ ����, ���� ����� ��� �ֽ��ϴ�.<br>
 * <br>
 * <b>����:</b> �� Ŭ������ L4G���� ���� ������ ������ ������ �����Ƿ�
 * ���빰�� �׳� �ƿ� �� ���� ���� �ǰ��� �̷ӽ��ϴ�.
 * ���ӿ� ���� �⺻ ������ ������ �� ���� ���Ͽ� �ڼ��� ����Ǿ� �ֽ��ϴ�.
 * ��, ����� ������, �� Ŭ������ ���� �ּ�����
 * �⺻������ ���� ���� ���� �뵵�� ���� �ξ�����
 * ��ưų� ��ģ���ص� ������ �ּ���.
 * 
 * @author Racin
 * 
 */
public class Classroom
{
	/**
	 * ���ǽ��� ���� ����(���� ���� ����)�� ��Ÿ���� �������Դϴ�.
	 * 
	 * @author Racin
	 *
	 */
	public enum ClassroomStateCode
	{
		Not_Defined,
		Initialized,
		Running,
		Waiting_Callback_Lock,
		Waiting_Decision_Survivor_Move, // Waiting_Decision �迭�� ���� ���� ������ �÷��̾ ���� ���� ����
		Waiting_Decision_Corpse_Stay,
		Waiting_Decision_Infected_Move,
		Waiting_Decision_Soul_Stay,
		Waiting_Decision_Soul_Spawn,
		Completed
	}
	public TEST test;
	/* ------------------------------------------------------
	 * �� ���� �ʵ��
	 */

	/**
	 * ���� �������� ���� ��ȣ�Դϴ�.
	 */
	public long gameNumber;

	/**
	 * ���ǽ��� ���� ���¸� ��Ÿ���ϴ�.
	 */
	public ClassroomStateCode state;

	/**
	 * �����ڸ� ���� �Է¹��� ���� �����Դϴ�.
	 */
	public Classroom_Settings settings;

	/**
	 * ���� ���ӿ� �������� �÷��̾� ����Դϴ�.
	 */
	public Player[] players;


	/* ------------------------------------------------------
	 * ���ǽ� �� ������ or ��� �ʵ��(������)
	 */

	/**
	 * �̹� �� ��ȣ�� <b����</b>�Դϴ�.
	 */
	public int turnNumber;

	/**
	 * �̹� �Ͽ� ���� ���� ���� ���θ� Ȯ���ؾ� �ϴ��� ���θ� ��Ÿ���� <b>����</b> �ʵ��Դϴ�.
	 */
	public boolean isDirectInfectionChoosingTurn;

	/**
	 * ���: ���� ������ ���۵� �� �ߵ��Ǳ���� �� �� ���Ҵ����� ��Ÿ���ϴ�.<br>
	 * 0: ���� ������ �̹� �Ͽ� �ߵ��� �������� ��Ÿ���ϴ�.<br>
	 * ����: ���� ������ ���۵��� �ʾ����� ��Ÿ���ϴ�.
	 */
	public int directInfectionCountdown;

	/**
	 * �� �÷��̾ ���� <b>����</b> ���� ����Դϴ�.
	 */
	public PlayerStat[] playerStats;
	
	/**
	 * ���� ���� ������ �� ������ ���¿��� �÷��̾� ������ ��� �� ����Դϴ�.
	 */
	public ArrayList<PlayerStat> playerStats_Survivor;
	
	/**
	 * ���� ���� ������ �� ��ü ���¿��� �÷��̾� ������ ��� �� ����Դϴ�.
	 */
	public ArrayList<PlayerStat> playerStats_Corpse;
	
	/**
	 * ���� ���� ������ �� ����ü ���¿��� �÷��̾� ������ ��� �� ����Դϴ�.
	 */
	public ArrayList<PlayerStat> playerStats_Infected;
	
	/**
	 * ���� ���� ������ �� ��ȥ ���¿��� �÷��̾� ������ ��� �� ����Դϴ�.
	 */
	public ArrayList<PlayerStat> playerStats_Soul;

	/**
	 * �� �÷��̾ �� �� �����ϴ� �ǻ� ���� �ϳ��� ��Ÿ���ϴ�.
	 */
	public Decision[] decisions;

	/**
	 * �� ĭ�� ���������� ���εǴ� ��� ��ǥ ����Դϴ�.
	 */
	public Point_Immutable[][] points;

	/**
	 * �� ĭ�� ���� <b>����</b> ���� ����Դϴ�.
	 */
	public Cell[][] cells;

	/**
	 * �� �÷��̾��� �ι��� ����Դϴ�.
	 */
	public int[][] scores;

	/**
	 * �� �÷��̾��� �ι��� ���� ����Դϴ�.
	 */
	public int[][] grades;

	/**
	 * �� �÷��̾��� �ι��� ���� ����Դϴ�.
	 * 1���� ���� ���� 0�� �ƴ� 1�Դϴ�.
	 */
	public int[][] ranks;

	/**
	 * �� �ι��� ������ �÷��̾� ID ����Դϴ�.
	 * ����� 0��° ĭ�� ������� ������, 1��° ĭ���� 1���� ID�� ���ϴ�.
	 */
	public int[][] rankedPlayers;

	/**
	 * �÷��̾ �������� ����Դϴ�.
	 */
	public int[] final_grades;

	/**
	 * �÷��̾ �������� ���� ����Դϴ�.
	 * 1���� ���� ���� 0�� �ƴ� 1�Դϴ�.
	 */
	public int[] final_ranks;

	/**
	 * �������� ������ �÷��̾� ID ����Դϴ�.
	 * ����� 0��° ĭ�� ������� ������, 1��° ĭ���� 1���� ID�� ���ϴ�.
	 */
	public int[] final_rankedPlayers;

	/*
	 * �Ʒ��� ���� ��� �ʵ���� ���� �ִ� ���� ������ �迭���� �׼����ϱ� ���� ���˴ϴ�.
	 * �������� �׸� �Ű澲�� �ʾƵ� �ǰڱ���.
	 */
	public static final int Score_Titles = 8;
	public static final int Score_Survivor_Max = 0;
	public static final int Score_Survivor_Total = 1;
	public static final int Score_Corpse_Max = 2;
	public static final int Score_Corpse_Total = 3;
	public static final int Score_Infected_Max = 4;
	public static final int Score_Infected_Total = 5;
	public static final int Score_Soul_Freedom = 6;
	public static final int Score_Soul_Destruction = 7;

	/* ------------------------------------------------------
	 * �÷��̾�鿡�� ������ ���� ��ϵ�(�� �� �����)
	 */

	/**
	 * �̹� �Ͽ� ���� �����Դϴ�.
	 */
	public TurnInfo turnInfo;

	/**
	 * �� �÷��̾ ���� ���� ����Դϴ�.
	 */
	public PlayerInfo[] playerInfos;

	/**
	 * �� ĭ�� ���� ���� ����Դϴ�.
	 */
	public CellInfo[][] cellInfos;

	/**
	 * '���� �ִ����� �˰� �ִ� ĭ'�� ���� ���� ����Դϴ�.
	 * �� ������ <code>cellInfos</code>�� �� ������ �Ϻθ� ���� ���̸�
	 * �����ڵ� ������ �߰� ���� ��ȯ, �Ǵ� ��ü�� �ڱ� ĭ ���� ��Ŀ���� ���� ���������� �����˴ϴ�.
	 */
	public CellInfo[][] cellInfos_playerInfosOnly;

	/**
	 * '��ü�� ���縸 �������� ĭ'�� ���� ���� ����Դϴ�.
	 * �� ������ <code>cellInfos</code>�� �� ������ �Ϻθ� ���� ���̸�
	 * ����ü�� ��� ��ü ���� ��Ŀ���� ���� ���������� �����˴ϴ�.
	 */
	public CellInfo[][] cellInfos_corpseInfosOnly;


	/* ------------------------------------------------------
	 * ���ǽ� �ܺο� �����ϱ� ���� �ʵ�� �� ���� �޼��� �ϳ�
	 */

	/**
	 * ���� ������ ����ȭ�ϰ� ���� �� ����ϴ� lock�Դϴ�.
	 * lock�� �� lock����, lock���� �� ��� lock�Ѵٴ� �������� �ü�� �ð��� ��� ���Դϴ�.
	 * ��¶��, �ݹ� �޼��尡 false�� ��ȯ�� �� ���ǽ��� �� lock�� Ǯ�� ������ ��ٸ��ϴ�.
	 */
	public Object lock;

	/**
	 * �ʱ�ȭ �۾��� ������ �� ȣ��Ǵ� �ݹ� �޼����Դϴ�.
	 */
	public Supplier<Boolean> callback_StandBy;

	/**
	 * ������ ���۵� �� ȣ��Ǵ� �ݹ� �޼����Դϴ�.
	 */
	public Supplier<Boolean> callback_StartGame;

	/**
	 * ���� ���۵� �� ȣ��Ǵ� �ݹ� �޼����Դϴ�.
	 */
	public Supplier<Boolean> callback_StartTurn;

	/**
	 * ���� ���� ������ �÷��̾ ����ϴ� ��� ���콺 ���� ���� ����ڰ� ������ ��ǥ�� ��Ÿ���ϴ�.
	 */
	public Point pos_acceptedFromControllablePlayer;

	/**
	 * ���� ���� ������ �÷��̾ ����ϴ� ��� ������� ���ʰ� �Ǿ��� �� ȣ��Ǵ� �ݹ� �޼����Դϴ�.
	 */
	public Supplier<Boolean> callback_RequestDecision;

	/**
	 * ���� ���� ������ �÷��̾ ����ϴ� ��� ������� ������ ��ȿ���� ���� �� ȣ��Ǵ� �ݹ� �޼����Դϴ�.
	 */
	public Supplier<Boolean> callback_InvalidDecision;

	/**
	 * ���� ������ �� ȣ��Ǵ� �ݹ� �޼����Դϴ�.
	 */
	public Supplier<Boolean> callback_EndTurn;

	/**
	 * ������ ������ �� ȣ��Ǵ� �ݹ� �޼����Դϴ�.
	 */
	public Supplier<Boolean> callback_EndGame;

	/**
	 * '�ƹ� �͵� ���� �ʴ�' �޼����Դϴ�.
	 * �� �״�� '�ƹ� �͵� �ϰ� ���� ���� ��' ����ϸ�,
	 * �ֿ� ������ ���� ���� �ݹ� �ʵ���� �⺻������ ���� ���Դϴ�.
	 */
	public static Boolean DoNothing()
	{
		return true;
	}

	/* 
	 * ������ ���� ��
	 * ------------------------------------------------------
	 */



	/* ------------------------------------------------------
	 * 'ū �޼���'��
	 */

	/**
	 * ���ο� <code>Classroom class</code>�� �ν��Ͻ��� �ʱ�ȭ�մϴ�.
	 * 
	 * @param settings
	 *            �Ƹ��� <code>main()</code>���� ������, ���ǽ� ���� ���������� ��� �� Ŭ������ �ν��Ͻ��Դϴ�.
	 */
	public Classroom(Classroom_Settings settings)
	{
		state = ClassroomStateCode.Not_Defined;
		this.settings = settings;

		players = new Player[Constants.Total_Players];

		playerStats = new PlayerStat[Constants.Total_Players];
		
		playerStats_Survivor = new ArrayList<PlayerStat>();
		
		playerStats_Corpse = new ArrayList<PlayerStat>();
		
		playerStats_Infected = new ArrayList<PlayerStat>();
		
		playerStats_Soul = new ArrayList<PlayerStat>();

		// �ٸ� ��ϵ�� �ٸ��� decisions ���빰�� �÷��̾� ��� ��Ȳ�� �����ϰ� ���� �����ϹǷ� ���ǽ� �ʱ�ȭ�� ���� �� �� ����
		decisions = new Decision[Constants.Total_Players];
		for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
			decisions[iPlayer] = new Decision();

		playerInfos = new PlayerInfo[Constants.Total_Players];

		points = new Point_Immutable[Constants.Classroom_Height][Constants.Classroom_Width];
		cells = new Cell[Constants.Classroom_Height][Constants.Classroom_Width];
		cellInfos = new CellInfo[Constants.Classroom_Height][Constants.Classroom_Width];
		cellInfos_playerInfosOnly = new CellInfo[Constants.Classroom_Height][Constants.Classroom_Width];
		cellInfos_corpseInfosOnly = new CellInfo[Constants.Classroom_Height][Constants.Classroom_Width];

		for ( int iRow = 0; iRow < Constants.Classroom_Height; iRow++ )
			for ( int iColumn = 0; iColumn < Constants.Classroom_Width; iColumn++ )
			{
				points[iRow][iColumn] = new Point_Immutable(iRow, iColumn);
				cells[iRow][iColumn] = new Cell();
			}

		scores = new int[Constants.Total_Players][Score_Titles];
		grades = new int[Constants.Total_Players][Score_Titles];
		ranks = new int[Constants.Total_Players][Score_Titles];
		rankedPlayers = new int[Score_Titles][Constants.Total_Players + 1];
		final_grades = new int[Constants.Total_Players];
		final_ranks = new int[Constants.Total_Players];
		final_rankedPlayers = new int[Constants.Total_Players + 1];

		lock = new Object();

		Supplier<Boolean> doNothing = Classroom::DoNothing;
		
		if ( settings.callback_StandBy == null )
			callback_StandBy = doNothing;
		else
			callback_StandBy = settings.callback_StandBy;
		
		if ( settings.callback_StartGame == null )
			callback_StartGame = doNothing;
		else
			callback_StartGame = settings.callback_StartGame;
		
		pos_acceptedFromControllablePlayer = new Point(0, 0);
		
		if ( settings.callback_StartTurn == null )
			callback_StartTurn = doNothing;
		else
			callback_StartTurn = settings.callback_StartTurn;
		
		if ( settings.callback_RequestDecision == null )
			callback_RequestDecision = doNothing;
		else
			callback_RequestDecision = settings.callback_RequestDecision;
		
		if ( settings.callback_InvalidDecision == null )
			callback_InvalidDecision = doNothing;
		else
			callback_InvalidDecision = settings.callback_InvalidDecision;
		
		if ( settings.callback_EndTurn == null )
			callback_EndTurn = doNothing;
		else
			callback_EndTurn = settings.callback_EndTurn;
		
		if ( settings.callback_EndGame == null )
			callback_EndGame = doNothing;
		else
			callback_EndGame = settings.callback_EndGame;
		
	}

	/**
	 * ������ �����ϱ� ������ ����Ǵ� �ʱ�ȭ ������ ��� �� �޼����Դϴ�.
	 */
	public void Initialize()
	{
		/* ----------------------------------------
		 * ���� ��ȣ ����
		 */
		if ( settings.game_number != -1L )
			gameNumber = settings.game_number;
		else
		{
			/*
			 * ����:
			 * ���⼭�� '������ ���� ��ȣ'�� ����� ���� ���� ��Ҹ� ����߽��ϴ�.
			 * ������ �츮 ��Ģ�� �������� �ڵ忡���� Random class�� ����� �� �����ϴ�.
			 */
			java.util.Random rand = new java.util.Random();
			gameNumber = rand.nextLong();

			if ( gameNumber < 0 )
				gameNumber += Long.MAX_VALUE;
		}


		/* ----------------------------------------
		 * �÷��̾� ���
		 */
		int iPlayer = 0;

		// ���� ���� ������ �÷��̾ ����ϴ� ��� ���� ���� ���
		if ( settings.use_ctrlable_player == true )
		{
			players[iPlayer] = new ControllablePlayer(iPlayer, this);
			++iPlayer;
		}

		// ���� �ۼ��� �÷��̾ ����ϴ� ��� ���̾� ���
		try
		{
			for ( Class<? extends Player> customPlayer_class : settings.custom_player_classes )
			{
				players[iPlayer] = (Player) customPlayer_class.getConstructors()[0].newInstance(iPlayer);
				++iPlayer;
			}
		}
		catch ( Exception e )
		{
			System.err.println("���� �ۼ��� �÷��̾� ����� ��ȿ���� �ʽ��ϴ�. \n" + "�Ƹ��� main()�� ���� ���ǽ� ���� �κ��� �ٽ� Ȯ���� �ּ���.");
			e.printStackTrace();
			System.err.flush();
			System.exit(1);
		}

		// ���� �ڸ��� ���� �÷��̾��� ä��
		if ( iPlayer != Constants.Total_Players )
		{
			/*
			 * ����:
			 * ���⼭�� �� ���� �÷��̾ ������ ������ �߰��ϱ� ���� ���� ��Ҹ� ����߽��ϴ�.
			 * ������ �츮 ��Ģ�� �������� �ڵ忡���� Random class�� ����� �� �����ϴ�.
			 */
			java.util.Random rand;

			// seed ����
			String seed = settings.seed_for_sample_players;
			if ( seed == null || seed.isEmpty() == true || seed.length() == 0 )
				seed = "16OODP";

			rand = new Random(seed.hashCode());
			
			int current_numberOfHornDonePlayer = 0;

			for ( ; iPlayer < Constants.Total_Players; ++iPlayer )
			{
				int rv = rand.nextInt(110);
				
				if ( rv < 20 )
				{
					players[iPlayer] = new Bot_Loner(iPlayer);
				}
				else if ( rv < 40 )
				{
					players[iPlayer] = new Bot_Scout(iPlayer);
				}
				else if ( rv < 50 )
				{
					players[iPlayer] = new Bot_CorpseBomb(iPlayer);
				}
				else if ( rv < 70 )
				{
					players[iPlayer] = new Bot_Seeker(iPlayer);
				}
				else if ( rv < 90 )
				{
					players[iPlayer] = new Bot_Predator(iPlayer);
				}
				else
				{
					if ( settings.max_numberOfHornDonePlayer <= current_numberOfHornDonePlayer )
						--iPlayer;
					else
					{
						players[iPlayer] = new Bot_HornDone(iPlayer);
						++current_numberOfHornDonePlayer;
					}
				}
			}
		}

		/* ----------------------------------------
		 * ������ �ʱ�ȭ - �÷��̾� �� ĭ ������ 0��° ��Ȳ(��� ��ġ �غ� �� ��ȥ�̸� ���ǽ��� ���� �� ��Ȳ)�� �°� ����
		 */
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
		{
			players[iPlayer].gameNumber = gameNumber;
			PlayerStat newStat = new PlayerStat(iPlayer);
			playerStats[iPlayer] = newStat;
			decisions[iPlayer].type = TypeCode.Soul_Spawn;
			playerInfos[iPlayer] = new PlayerInfo(newStat);
			players[iPlayer].myInfo = playerInfos[iPlayer];
			playerStats_Soul.add(newStat);
		}

		for ( int iRow = 0; iRow < Constants.Classroom_Height; iRow++ )
			for ( int iColumn = 0; iColumn < Constants.Classroom_Width; iColumn++ )
			{
				cellInfos[iRow][iColumn] = CellInfo.Blank;
				cellInfos_playerInfosOnly[iRow][iColumn] = CellInfo.Blank;
				cellInfos_corpseInfosOnly[iRow][iColumn] = CellInfo.Blank;
			}

		for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
			for ( int iTitle = 0; iTitle < Score_Titles; iTitle++ )
				scores[iPlayer][iTitle] = 0;

		state = ClassroomStateCode.Initialized;

		DoCallBack(callback_StandBy);
	}

	/**
	 * L4G ������ �����մϴ�.
	 */
	public void Start()
	{
		DoCallBack(callback_StartGame);

		state = ClassroomStateCode.Running;

		for ( turnNumber = 0; turnNumber <= Constants.Max_Turn_Numbers; turnNumber++ )
			AdvanceTurn();

		CalculateGrades();
		
		//���� ���� �� �ܼ� â�� ����� ǥ���ϱ�� �� ��� ǥ��
		if ( settings.use_console_mode == true )
			PrintResultToConsole_EndGame();

		state = ClassroomStateCode.Completed;

		DoCallBack(callback_EndGame);
	}

	/**
	 * ���� ���� �� ���� �����մϴ�.
	 */
	private void AdvanceTurn()
	{
		int iPlayer;
		
		// �ܼ� â�� ����� ǥ���ϸ� �� �� ����� ������ �ϳ��� ������ ��� �� ��ȣ ���
		if ( settings.use_console_mode == true &&
				( settings.print_actions == true || settings.print_decisions == true ||
				settings.print_playerInfos == true ||
				settings.print_reactions == true || settings.print_scores_at_each_turns == true ) )
			System.out.printf("\nTurn %d ------------------------------------------------------------\n", turnNumber);

		// ��ٿ� ����
		--directInfectionCountdown;
		for ( PlayerStat stat : playerStats )
			if ( stat.transitionCooldown >= 0)
				--stat.transitionCooldown;
		
		// �̹� �Ͽ� ���� ������ �غ��ؾ� �ϴ��� üũ
		isDirectInfectionChoosingTurn = false;

		if ( directInfectionCountdown < 0 )
		{
			// �����ڰ� �����ϸ� ��ü �� ����ü�� �������� �ʴ� �Ͽ� ���� ���� ����
			if ( playerStats_Survivor.isEmpty() == false &&
					playerStats_Corpse.isEmpty() == true && playerStats_Infected.isEmpty() == true )
			{
				isDirectInfectionChoosingTurn = true;
				directInfectionCountdown = Constants.Duration_Direct_Infection;
			}
		}

		// �̹� �Ͽ� ���� ���� ����, �� �÷��̾��� ���� ���� �� ��ġ ���(����ü óġ ������ ����), �� �÷��̾ ������ �ǻ� ���� ����
		turnInfo = new TurnInfo(turnNumber, isDirectInfectionChoosingTurn);
		
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			players[iPlayer].turnInfo = turnInfo;
			
			playerStats[iPlayer].lastState = playerStats[iPlayer].state;
			playerStats[iPlayer].lastPosition = playerStats[iPlayer].position;

			decisions[iPlayer].location_from = playerStats[iPlayer].position;
			
			switch ( playerStats[iPlayer].state )
			{
			case Survivor:
				decisions[iPlayer].type = TypeCode.Survivor_Move;
				break;
			case Corpse:
				decisions[iPlayer].type = TypeCode.Corpse_Stay;
				break;
			case Infected:
				decisions[iPlayer].type = TypeCode.Infected_Move;
				break;
			case Soul:
				if ( playerStats[iPlayer].transitionCooldown <= 0 )
					decisions[iPlayer].type = TypeCode.Soul_Spawn;
				else
					decisions[iPlayer].type = TypeCode.Soul_Stay;
				break;
			}
		}
		
		// �� �÷��̾�� �˷��ִ� ���� ���� ����
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			Score score = players[iPlayer].myScore;
			
			score.survivor_max = scores[iPlayer][Score_Survivor_Max];
			score.survivor_total = scores[iPlayer][Score_Survivor_Total];
			score.corpse_max = scores[iPlayer][Score_Corpse_Max];
			score.corpse_total = scores[iPlayer][Score_Corpse_Total];
			score.infected_max = scores[iPlayer][Score_Infected_Max];
			score.infected_total = scores[iPlayer][Score_Infected_Total];
		}

		/* --------------------------------------------------------------------------------------------
		 * Phase 1. �����ڿ� ����ü �̵�, ��ü�� ��ȥ ���
		 */

		// ��� �÷��̾���� ���� �þ� ���� ����
		RefreshInfos(false);

		// �þ� �������� ���ŵǾ�� �������� �� ���� ���۵� ���̹Ƿ� ���⼭ �ݹ� �޼��� ȣ��
		DoCallBack(callback_StartTurn);
		
		// �ܼ� â�� ù �÷��̾��� ���� ����� ����Ϸ��� ��� �� ����(�ǻ� ���� ����)���� ���
		if ( settings.use_console_mode == true )
			PrintStartInfoToConsole_FirstPlayerOnly(false);

		// �ǻ� ���� �޼��� ȣ��
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			try
			{
				switch ( decisions[iPlayer].type )
				{
				case Survivor_Move:
					decisions[iPlayer].move_direction = players[iPlayer].Survivor_Move();
					break;
				case Corpse_Stay:
					players[iPlayer].Corpse_Stay();
					break;
				case Infected_Move:
					decisions[iPlayer].move_direction = players[iPlayer].Infected_Move();
					break;
				case Soul_Stay:
				case Soul_Spawn:
					players[iPlayer].Soul_Stay();
					break;
				default:
					break;
				}
			}
			catch ( Exception e )
			{
				// �ǻ� ���� ���� ��Ÿ�� ���ܸ� ����Ų ���(�װ� ��ü/��ȥ ���� ������) ��ư�

				// ��� ���
				AddReaction(iPlayer, Reaction.TypeCode.Arrested);

				// ���� ������ ����ϵ��� �����Ǿ� �ִ� ��� ���
				if ( settings.print_errors == true )
				{
					System.err.printf("Error. %s makes runtime exception. - Game #%d, Turn %d\n", players[iPlayer].name, gameNumber, turnNumber);
					e.printStackTrace();
				}

				// ��ȥ �г�Ƽ �ο�
				playerStats[iPlayer].state = StateCode.Soul;
				playerStats[iPlayer].position = Constants.Pos_Sky;
				playerStats[iPlayer].transitionCooldown = Constants.Duration_Soul_Penalty;

				// ���� �г�Ƽ ������ ���� '���� ��ȥ ���¿��� ��ó��' ����
				decisions[iPlayer].type = TypeCode.Soul_Stay;

				// ��ȥ �ı� ���� ����
				UpdateScore_Total(Score_Soul_Destruction, iPlayer, 1);
			}
		}

		// �̵� �ǻ� ���� ���� �� �ݿ�
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			Decision decision = decisions[iPlayer];
			PlayerStat stat = playerStats[iPlayer];

			// ��� �̵� �ǻ� ������ ����...
			if ( decision.type == TypeCode.Survivor_Move || decision.type == TypeCode.Infected_Move )
			{
				// �� ����� ���ǽ� ���� �������� �˻�
				Point_Immutable pos_destination = GetAdjacentPoint_Immutable(stat.position, decision.move_direction);
				decision.location_to = pos_destination;

				// ���ǽ� ���� �����ų� �����ڰ� Stay�� ������ ��� ��ư�
				if ( pos_destination == Constants.Pos_Sky || decision.type == TypeCode.Survivor_Move && decision.move_direction == DirectionCode.Stay )
				{
					// ��� ���
					AddReaction(iPlayer, Reaction.TypeCode.Punished);

					// ���� ������ ����ϵ��� �����Ǿ� �ִ� ��� ���
					if ( settings.print_errors == true )
						System.err.printf("Error. %s(%s) attempts to move %s from %s. - Game #%d, Turn %d\n", players[iPlayer].name, stat.state, decision.move_direction, stat.position, gameNumber, turnNumber);

					// ��ȥ �г�Ƽ �ο�
					stat.state = StateCode.Soul;
					stat.position = Constants.Pos_Sky;
					stat.transitionCooldown = Constants.Duration_Soul_Penalty;

					// ���� �г�Ƽ ������ ���� '���� ��ȥ ���¿��� ��ó��' ����
					decisions[iPlayer].type = TypeCode.Soul_Stay;

					// ��ȥ ���� ���� ����
					UpdateScore_Total(Score_Soul_Freedom, iPlayer, 1);
				}

				// ������ ���� ��� �̵� ��� �ݿ�
				else
				{
					// �ൿ ���
					AddAction(iPlayer, Action.TypeCode.Move, pos_destination);

					// �̹� ���� ���� ���� ���� ���θ� �����ϴ� ���̶�� �������� ���� ���� üũ
					if ( decision.type == TypeCode.Survivor_Move )
						stat.survivor_AcceptedDirectInfection = players[iPlayer].trigger_acceptDirectInfection;

					// ����ü�� Stay�� �����ߴ��� ���θ� üũ
					if ( decision.type == TypeCode.Infected_Move && decision.move_direction == DirectionCode.Stay )
						stat.infected_Stayed = true;
					else
						stat.infected_Stayed = false;

					// �÷��̾� ��ġ ����
					stat.position = pos_destination;
				}
			}
		}

		/* --------------------------------------------------------------------------------------------
		 * Phase 2. ��ȥ ��ġ
		 */

		// ��ȥ���� ���� �þ� ���� ����
		RefreshInfos(true);
		
		// �ܼ� â�� ù �÷��̾��� ���� ����� ����Ϸ��� ��� �� ����(�ǻ� ���� ����)���� ���
		if ( settings.use_console_mode == true && decisions[0].type == TypeCode.Soul_Spawn )
			PrintStartInfoToConsole_FirstPlayerOnly(true);

		// �ǻ� ���� �޼��� ȣ��
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			try
			{
				if ( decisions[iPlayer].type == TypeCode.Soul_Spawn )
					decisions[iPlayer].spawn_point = players[iPlayer].Soul_Spawn();
			}
			catch ( Exception e )
			{
				// �ǻ� ���� ���� ��Ÿ�� ���ܸ� ����Ų ��� '��ġ ����'�� ����

				// ��ٿ� 1������ �缳��
				playerStats[iPlayer].transitionCooldown = 1;

				// ���� �˻� ������ ���� '���� ��ȥ ���¿��� ��ó��' ����
				decisions[iPlayer].type = TypeCode.Soul_Stay;
			}
		}

		// ��ġ �ǻ� ���� ���� �� �ݿ�
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			Decision decision = decisions[iPlayer];
			PlayerStat stat = playerStats[iPlayer];

			// ��� ��ġ �ǻ� ������ ����...
			if ( decision.type == TypeCode.Soul_Spawn )
			{
				// ���ǽ� ���� �����ߴ��� �˻�
				Point_Immutable pos_destination = GetSamePoint_Immutable(decision.spawn_point);
				decision.location_to = pos_destination;

				// ���ǽ� ���� ������ ��� '��ġ ����'�� ����
				if ( pos_destination == Constants.Pos_Sky )
				{
					// ��ٿ� 1������ �缳��
					playerStats[iPlayer].transitionCooldown = 1;
				}

				// ������ ���� ��� ��ġ ��� �ݿ�
				else
				{
					// �ൿ ���
					AddAction(iPlayer, Action.TypeCode.Spawn, pos_destination);

					// ���� ��ȯ: ��ȥ -> ������
					stat.state = StateCode.Survivor;
					stat.position = pos_destination;
				}
			}
		}

		/* --------------------------------------------------------------------------------------------
		 * Phase 3. ��ü �Ͼ
		 */

		for ( iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			PlayerStat stat = playerStats[iPlayer];

			// �Ͼ ���� �� ��� ��ü�� ����...
			if ( stat.state == StateCode.Corpse && stat.transitionCooldown == 0 )
			{
				// ���� ��ȯ: ��ü -> ����ü
				stat.state = StateCode.Infected;

				// ��ü MAX ��� �ʱ�ȭ
				stat.corpse_CurrentHealedPlayers.clear();

				// ����ü�� �Ͼ ù ���� ü���� �������� ����
				stat.infected_Healed = true;

				// �⵵ ī���� �ʱ�ȭ
				stat.infected_CurrentPrayCounter = 0;
				
				// ��� ���
				AddReaction(iPlayer, Reaction.TypeCode.Rise);
			}
		}

		/* --------------------------------------------------------------------------------------------
		 * Phase 4. ���� ���� ����
		 */

		// ���� ������ �߻��ؾ� �Ѵٸ�
		if ( directInfectionCountdown == 0 )
		{
			for ( iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
			{
				PlayerStat stat = playerStats[iPlayer];

				// ���� ������ �����ߴ� �����ڵ���
				if ( stat.survivor_AcceptedDirectInfection == true && stat.state == StateCode.Survivor )
				{
					// ���� ��ȯ: ������ -> ����ü
					stat.state = StateCode.Infected;
					stat.HP = Constants.Corpse_Init_HP;

					// ���� �������� ����ü�� �� ù ���� ü���� �������� ����
					stat.infected_Healed = true;

					// ��� ���
					AddReaction(iPlayer, Reaction.TypeCode.DirectInfect);
				}

				// ���� ���� ������ ������ ���� ���� �ʵ�� reset
				stat.survivor_AcceptedDirectInfection = false;
			}
		}

		/* --------------------------------------------------------------------------------------------
		 * Phase 5. ����ü�� �����ڸ� óġ
		 */
		
		// ��� �����ڿ� ����
		for ( int iSurvivor = 0; iSurvivor < Constants.Total_Players; ++iSurvivor )
		{
			PlayerStat stat_survivor = playerStats[iSurvivor];
			
			if ( stat_survivor.state == StateCode.Survivor )
			{
				// �ڽ��� �̹� �Ͽ� �״��� ���θ� �˻�
				for ( int iInfected = 0; iInfected < Constants.Total_Players; ++iInfected )
				{
					PlayerStat stat_infected = playerStats[iInfected];
					
					// �����ڿ� ���� ĭ�� ����ü�� �ϳ��� �ִٸ� ���
					if ( stat_infected.state == StateCode.Infected && stat_survivor.position == stat_infected.position )
					{
						stat_survivor.survivor_Dead = true;
						break;
					}
				}
				
				// �̹� �Ͽ� ���� �����̸� ����
				if ( stat_survivor.survivor_Dead == true )
				{
					stat_survivor.survivor_Dead = false;
					
					// ��� ����ü�� ����
					for ( int iInfected = 0; iInfected < Constants.Total_Players; ++iInfected )
					{
						PlayerStat stat_infected = playerStats[iInfected];
						
						if ( stat_infected.state == StateCode.Infected )
						{
							// �ڽ��� ���� óġ�߰ų� �ڽ��� ���� �� �þ� ������ �ִ�(óġ ����) ����ü���� ����ü MAX ���� ���� �� ��� ���
							if ( stat_survivor.position == stat_infected.position ||
									stat_survivor.lastState == StateCode.Survivor && stat_survivor.lastPosition.GetDistance(stat_infected.lastPosition) <= 2 )
							{
								stat_infected.infected_KilledOrAssisted = true;
								
								++stat_infected.infected_CurrentKillStreaks;
								
								AddReaction(iInfected, Reaction.TypeCode.Kill, iSurvivor);
							}
						}
					}

					// ���� ��ȯ: ������ -> ��ü
					stat_survivor.state = StateCode.Corpse;
					stat_survivor.HP = Constants.Corpse_Init_HP;
					stat_survivor.corpse_radius = Constants.Corpse_Init_Radius;
					stat_survivor.transitionCooldown = Constants.Corpse_Rise_Cooldown;

					// ������ MAX ��� �ʱ�ȭ
					stat_survivor.survivor_CurrentSurvivedTurns = 0;
				}
				
				// �̹� �Ͽ� ���� �ʾҴٸ� �����ڷ� ���� �������� ���̹Ƿ� ������ MAX ��� ���� �� ����
				else
				{
					UpdateScore_Max(Score_Survivor_Max, iSurvivor, ++stat_survivor.survivor_CurrentSurvivedTurns);
				}
			}
		}
		
		// ��� ����ü�� ����
		for ( int iInfected = 0; iInfected < Constants.Total_Players; ++iInfected )
		{
			PlayerStat stat_infected = playerStats[iInfected];
			
			// �̹� �Ͽ� óġ �Ǵ� óġ ������ ����� ��� ����ü MAX ��� ����
			if ( stat_infected.infected_KilledOrAssisted == true )
			{
				stat_infected.infected_KilledOrAssisted = false;
				
				UpdateScore_Max(Score_Infected_Max, iInfected, stat_infected.infected_CurrentKillStreaks);
			}
		}

		/* --------------------------------------------------------------------------------------------
		 * Phase 6. ��ü�� ����ü�� ġ��
		 */

		// ��� ��ü�� ����
		for ( int iCorpse = 0; iCorpse < Constants.Total_Players; iCorpse++ )
		{
			PlayerStat stat_corpse = playerStats[iCorpse];

			if ( stat_corpse.state == StateCode.Corpse )
			{
				boolean isNewHealOccured = false;

				// ��� ����ü�� ����
				for ( int iInfected = 0; iInfected < Constants.Total_Players; iInfected++ )
				{
					PlayerStat stat_infected = playerStats[iInfected];

					// �ش� ��ü�� ���� ������ ����ü�� �ִٸ�
					if ( stat_infected.state == StateCode.Infected && stat_corpse.position.GetDistance(stat_infected.position) <= stat_corpse.corpse_radius )
					{
						// ����ü�� �̹� �Ͽ� ġ���� ���� ������ üũ
						stat_infected.infected_Healed = true;

						// ����ü�� ü���� ȸ����Ű�� ��ü Total ��� ����, Max ��� �߰�
						stat_infected.HP += Constants.Corpse_Heal_Power;
						UpdateScore_Total(Score_Corpse_Total, iCorpse, Constants.Corpse_Heal_Power);

						if ( stat_corpse.corpse_CurrentHealedPlayers.contains(iInfected) == false )
						{
							isNewHealOccured = true;
							stat_corpse.corpse_CurrentHealedPlayers.add(iInfected);
						}

						// ��ü�� �ʱ� ü���� ������Ű�� ����ü Total ��� ����
						stat_corpse.HP += Constants.Infected_Infection_Power;
						UpdateScore_Total(Score_Infected_Total, iInfected, Constants.Infected_Infection_Power);

						// ��� ���
						AddReaction(iCorpse, Reaction.TypeCode.Heal, iInfected);
					}
				}
				
				// ġ���� ������ ��ü�� �쵢�� ���� ������ 1 ����
				if ( stat_corpse.corpse_radius > 0 )
					--stat_corpse.corpse_radius;

				// �̹� �Ͽ� ���ο� ����ü�� ġ���� ��� ��ü Max ��� ����
				if ( isNewHealOccured == true )
					UpdateScore_Max(Score_Corpse_Max, iCorpse, stat_corpse.corpse_CurrentHealedPlayers.size());
			}
		}

		/* --------------------------------------------------------------------------------------------
		 * Phase 7. ����ü �Ҹ�
		 */

		// ��� ����ü�� ����
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			PlayerStat stat = playerStats[iPlayer];

			if ( stat.state == StateCode.Infected )
			{
				// �̹� �Ͽ� ġ���޾Ҵٸ� ü���� �������� ����(�̹� �Ͽ� ����ü�� �� ��쵵 ���⿡ ����)
				if ( stat.infected_Healed == true )
				{
					stat.infected_Healed = false;

					// ��ȭ �⵵�� ������ ������ �⵵ ȿ�� �ʱ�ȭ
					stat.infected_PrayPower = 1;
				}
				
				// �̹� �Ͽ� ġ������ �ʾҴٸ� ü�� ����
				else
				{
					// �̹� �Ͽ� ������ �� �־��ٸ� ��ȭ �⵵�� ����
					if ( stat.infected_Stayed == true )
					{
						stat.infected_CurrentPrayCounter += stat.infected_PrayPower;

						// �Ҹ꿡 �ʿ��� ī���͸� �� ���� ��� ��� �Ҹ��ϰ� ü���� '���� ���� ��'�� ����
						if ( stat.infected_CurrentPrayCounter >= Constants.Infected_RequiredPrayerCounter )
						{
							stat.infected_CurrentPrayCounter = 0;
							stat.HP = Integer.MIN_VALUE;
						}

						// ī���Ͱ� ���� ������ ��� ���ó�� ü�� 1 ���� �� �⵵ ȿ�� ����
						else
						{
							--stat.HP;
							
							++stat.infected_PrayPower;
						}
					}
					// �̹� �Ͽ� �̵��ߴٸ� ü�� 1 ����
					else
					{
						--stat.HP;

						// �̵��� ������ �⵵ ȿ�� �ʱ�ȭ
						stat.infected_PrayPower = 1;
					}
				}

				// ���� ��� ü���� 0 ���ϰ� �� ��� �Ҹ�
				if ( stat.HP <= 0 )
				{
					// ���� ��ȯ: ����ü -> ��ȥ
					stat.state = StateCode.Soul;
					
					if ( stat.HP == Integer.MIN_VALUE )
						stat.transitionCooldown = Constants.Soul_Spawn_Cooldown_AfterPray;
					else
						stat.transitionCooldown = Constants.Soul_Spawn_Cooldown;

					// ü�� ��ġ�� 0���� ����
					stat.HP = 0;

					// ����ü MAX ��� �ʱ�ȭ
					stat.infected_CurrentKillStreaks = 0;

					// ��� ���
					AddReaction(iPlayer, Reaction.TypeCode.Vanish);
					
					// ��� ��� �� ��ȥ�� �ϴÿ� ����
					stat.position = Constants.Pos_Sky;
				}
			}
		}

		/* --------------------------------------------------------------------------------------------
		 * Phase 8. ������ �߰�
		 */
		
		//�� ���� �����̴� �� ���º� �÷��̾� ��� ������
		playerStats_Survivor.clear();
		playerStats_Corpse.clear();
		playerStats_Infected.clear();
		playerStats_Soul.clear();
		
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
		{
			PlayerStat stat = playerStats[iPlayer];
			
			switch ( stat.state )
			{
			case Survivor:
				playerStats_Survivor.add(stat);
				break;
			case Corpse:
				playerStats_Corpse.add(stat);
				break;
			case Infected:
				playerStats_Infected.add(stat);
				break;
			case Soul:
				playerStats_Soul.add(stat);
				break;
			}
		}
		
		//��� �����ڿ� ����
		for ( PlayerStat stat_survivor : playerStats_Survivor )
		{
			int numberOfSurvivors_withinSight = 0;
			int numberOfOthers_withinSight = 0;
			
			//�ڽ��� �þ� ���� �÷��̾� �߰�
			for ( iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
			{
				PlayerStat stat_other = playerStats[iPlayer];
				
				if ( stat_survivor.position.GetDistance(stat_other.position) <= 2 )
				{
					if ( stat_other.state == StateCode.Survivor )
						++numberOfSurvivors_withinSight;
					else
						++numberOfOthers_withinSight;
					
					//��� ����� ����
					//AddReaction(stat_survivor.ID, Reaction.TypeCode.Spot, iPlayer);
				}
			}
			
			//�ڽ��� ������ ������ �� * ��ü �� ����ü ����ŭ Total ��� ����
			--numberOfSurvivors_withinSight;
			UpdateScore_Total(Score_Survivor_Total, stat_survivor.ID, numberOfSurvivors_withinSight * numberOfOthers_withinSight);
		}
		
		//�ܼ� â�� ����� ǥ���ϱ�� �� ��� ǥ��
		if ( settings.use_console_mode == true )
			PrintResultToConsole_EndTurn();

		DoCallBack(callback_EndTurn);
	}


	/* ------------------------------------------------------
	 * '���� �޼���'��
	 */

	/**
	 * �־��� ������ ���� ���ο� ����� �����Ͽ� �ش� ����� �߻��� ĭ�� �߰��մϴ�.
	 * �� �޼���� ������ ��ü�� ����� ���� �� ����ϴ� �����Դϴ�.
	 */
	private void AddReaction(int ID, Reaction.TypeCode type)
	{
		Point_Immutable location = playerStats[ID].position;

		//���ǽ� ������ �Ͼ ���� ������� ����
		if ( location == Constants.Pos_Sky )
			return;
		
		int rowToAdd = location.row;
		int columnToAdd = location.column;
		Reaction newReaction = new Reaction(ID, type, location);

		cells[rowToAdd][columnToAdd].reactions.add(newReaction);
	}

	/**
	 * �־��� ������ ���� ���ο� ����� �����Ͽ� �ش� ����� �߻��� ĭ�� �߰��մϴ�.
	 * �� �޼���� ������ ��ü�� ����� �ٸ� �� ����ϴ� �����Դϴ�.
	 */
	private void AddReaction(int subjectID, Reaction.TypeCode type, int objectID)
	{
		Point_Immutable location_subject = playerStats[subjectID].position;
		Point_Immutable location_object = playerStats[objectID].position;

		//���ǽ� ������ �Ͼ ���� ������� ����
		if ( location_subject == Constants.Pos_Sky )
			return;
		
		int rowToAdd = location_subject.row;
		int columnToAdd = location_subject.column;
		Reaction newReaction = new Reaction(subjectID, type, objectID, location_subject, location_object);

		cells[rowToAdd][columnToAdd].reactions.add(newReaction);
	}

	/**
	 * �־��� ������ ���� ���ο� �ൿ�� �����Ͽ� �ش� �ൿ�� ����� ĭ�� �߰��մϴ�.
	 */
	private void AddAction(int actorID, Action.TypeCode type, Point_Immutable location_to)
	{
		Point_Immutable location_from = playerStats[actorID].position;

		//���ǽ� ������ �Ͼ ���� ������� ����
		if ( location_to == Constants.Pos_Sky )
			return;

		int rowToAdd = location_to.row;
		int columnToAdd = location_to.column;
		Action newAction = new Action(actorID, type, location_from, location_to);

		cells[rowToAdd][columnToAdd].actions.add(newAction);
	}

	/**
	 * �� �÷��̾�� �����Ǵ� ���� ������ �����մϴ�.
	 * 
	 * @param isForSpawn
	 *            ��ġ�� ���� ��ȥ�鿡�� �����Ǵ� ������ �����Ϸ��� ��� true�Դϴ�.
	 *            �� ���� false�� ��� ���� �� ���ǽ� �� ĭ�� �ൿ / ��� ����� �ʱ�ȭ�˴ϴ�.
	 */
	private void RefreshInfos(boolean isForSpawn)
	{
		int iPlayer;
		int numberOfSurvivorsAndCorpses = 0;
		int numberOfInfecteds = 0;
		int numberOfSoulsToSpawn = 0;
		ArrayList<PlayerStat> survivors = new ArrayList<PlayerStat>();
		int iRow;
		int iColumn;

		// �� ĭ�� �÷��̾� ��� �ʱ�ȭ
		for ( Cell[] cell_row : cells )
			for ( Cell cell : cell_row )
			{
				cell.players.clear();
				cell.corpses.clear();
			}
		
		// ���ǽ� ���� �÷��̾� ���� ���� �� �÷��̾� ���� ����
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
		{
			//�÷��̾� ������ �ش� �÷��̾��� myInfo�� ����, �� ĭ�� ���
			PlayerInfo newInfo = new PlayerInfo(playerStats[iPlayer]);
			Cell cell_target = null;

			playerInfos[iPlayer] = newInfo;
			players[iPlayer].myInfo = newInfo;
			
			if ( newInfo.state != StateCode.Soul )
			{
				cell_target = cells[newInfo.position.row][newInfo.position.column];
				cell_target.players.add(newInfo);
			}
			
			switch ( newInfo.state )
			{
			case Survivor:
				++numberOfSurvivorsAndCorpses;
				survivors.add(playerStats[iPlayer]);
				break;
			case Corpse:
				++numberOfSurvivorsAndCorpses;
				cell_target.corpses.add(playerInfos[iPlayer]);
				break;
			case Infected:
				++numberOfInfecteds;
				break;
			case Soul:
				if ( playerStats[iPlayer].transitionCooldown == 0 )
					++numberOfSoulsToSpawn;
				break;
			}
		}
		
		/*
		 * ĭ ���� ����
		 */
		// ��ġ ������ �����Ϸ��� ���
		if ( isForSpawn == true )
		{
			// ��ġ�� ��ȥ�� ���� ��� skip
			if ( numberOfSoulsToSpawn == 0 )
				return;
			
			//�׷��� ���� ��� ������, ��ü, ����ü�� ���� ������ �����ϵ��� �� ����
			numberOfSurvivorsAndCorpses = 0;
			numberOfInfecteds = 0;
		}
		
		//���� �Ͽ� cells�� ��� ������ ���� �̹� �Ͽ� ������ ĭ ���� ����
		for ( iRow = 0; iRow < Constants.Classroom_Height; ++iRow)
		{
			for ( iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn)
			{
				Cell cell_origin = cells[iRow][iColumn];
				
				cellInfos[iRow][iColumn] = cell_origin.MakeCellInfo(0);
				
				if ( numberOfSurvivorsAndCorpses != 0 )
					cellInfos_playerInfosOnly[iRow][iColumn] = cell_origin.MakeCellInfo(1);
				
				if ( numberOfInfecteds != 0 )
					cellInfos_corpseInfosOnly[iRow][iColumn] = cell_origin.MakeCellInfo(2);
			}
		}
		
		
		/*
		 * ĭ ���� �ʱ�ȭ
		 */
		// ��ü ������ ������ �������� �̹� �� ������ �ޱ� ���� ���� �� �ൿ / ��� ���� ��� ����
		if ( isForSpawn == false )
		{
			for ( Cell[] cell_row : cells )
				for ( Cell cell : cell_row )
				{
					cell.ResetPreviousLists();
				}
		}
		
		
		/*
		 * �� �÷��̾ �þ� ���� ����
		 */
		// ��ġ ������ �����Ϸ��� ���
		if ( isForSpawn == true )
		{
			for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
			{
				//��� �̹� �Ͽ� ��ġ�� ��ȥ�鿡 ����
				if ( playerStats[iPlayer].state == StateCode.Soul && playerStats[iPlayer].transitionCooldown == 0 )
				{
					CellInfo[][] cellsOfThePlayer = players[iPlayer].cells;
					
					//��ȥ�� �þߴ� ���ǽ� ��ü�̹Ƿ� �׳� ��� ���� ������ ��
					for ( iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
					{
						for ( iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
						{
							cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];
						}
					}
				}
			}			
		}
		// ��ü ������ �����Ϸ��� ���
		else
		{
			for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
			{
				PlayerStat stat = playerStats[iPlayer];
				CellInfo[][] cellsOfThePlayer = players[iPlayer].cells;
				
				switch ( stat.state )
				{
				case Survivor:
					//�������� �þߴ� �ֺ� �� ĭ�̸� �þ� �� �������� �þ߿� �ִ� �÷��̾� ����� Ȯ�� ����
					
					//���� ��� �� ĭ���� �ʱ�ȭ
					for ( iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
						for ( iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
							cellsOfThePlayer[iRow][iColumn] = CellInfo.Blank;
					
					//�þ� ���� �ٸ� �����ڷκ��� ���޹��� ���� ���
					for ( PlayerStat stat_other : survivors )
					{
						int distanceBetween = stat_other.position.GetDistance(stat.position);
						
						//�ش� �����ڰ� �� �þ� ���� ���� �ִٸ�
						if ( distanceBetween == 1 || distanceBetween == 2 )
						{
							iRow = stat_other.position.row - 2;
							iColumn = stat_other.position.column;
							if ( iRow >= 0 )
								cellsOfThePlayer[iRow][iColumn] = cellInfos_playerInfosOnly[iRow][iColumn];
							
							++iRow;
							--iColumn;
							if ( iRow >= 0 && iColumn >= 0)
								cellsOfThePlayer[iRow][iColumn] = cellInfos_playerInfosOnly[iRow][iColumn];
							
							++iColumn;
							if ( iRow >= 0 )
								cellsOfThePlayer[iRow][iColumn] = cellInfos_playerInfosOnly[iRow][iColumn];

							++iColumn;
							if ( iRow >= 0 && iColumn < Constants.Classroom_Width)
								cellsOfThePlayer[iRow][iColumn] = cellInfos_playerInfosOnly[iRow][iColumn];
							
							++iRow;
							iColumn -= 3;
							if ( iColumn >= 0 )
								cellsOfThePlayer[iRow][iColumn] = cellInfos_playerInfosOnly[iRow][iColumn];
							
							++iColumn;
							if ( iColumn >= 0 )
								cellsOfThePlayer[iRow][iColumn] = cellInfos_playerInfosOnly[iRow][iColumn];
							
							++iColumn;
							cellsOfThePlayer[iRow][iColumn] = cellInfos_playerInfosOnly[iRow][iColumn];

							++iColumn;
							if ( iColumn < Constants.Classroom_Width )
								cellsOfThePlayer[iRow][iColumn] = cellInfos_playerInfosOnly[iRow][iColumn];

							++iColumn;
							if ( iColumn < Constants.Classroom_Width )
								cellsOfThePlayer[iRow][iColumn] = cellInfos_playerInfosOnly[iRow][iColumn];
							
							++iRow;
							iColumn -= 3;
							if ( iRow < Constants.Classroom_Height && iColumn >= 0 )
								cellsOfThePlayer[iRow][iColumn] = cellInfos_playerInfosOnly[iRow][iColumn];
							
							++iColumn;
							if ( iRow < Constants.Classroom_Height )
								cellsOfThePlayer[iRow][iColumn] = cellInfos_playerInfosOnly[iRow][iColumn];
							
							++iColumn;
							if ( iRow < Constants.Classroom_Height && iColumn < Constants.Classroom_Width )
								cellsOfThePlayer[iRow][iColumn] = cellInfos_playerInfosOnly[iRow][iColumn];
							
							++iRow;
							--iColumn;
							if ( iRow < Constants.Classroom_Height )
								cellsOfThePlayer[iRow][iColumn] = cellInfos_playerInfosOnly[iRow][iColumn];
						}
					}
					
					//�� �þ� ���� �� ���� ���
					iRow = stat.position.row - 2;
					iColumn = stat.position.column;
					if ( iRow >= 0 )
						cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];
					
					++iRow;
					--iColumn;
					if ( iRow >= 0 && iColumn >= 0)
						cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];
					
					++iColumn;
					if ( iRow >= 0 )
						cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];

					++iColumn;
					if ( iRow >= 0 && iColumn < Constants.Classroom_Width)
						cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];
					
					++iRow;
					iColumn -= 3;
					if ( iColumn >= 0 )
						cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];
					
					++iColumn;
					if ( iColumn >= 0 )
						cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];
					
					++iColumn;
					cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];

					++iColumn;
					if ( iColumn < Constants.Classroom_Width )
						cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];

					++iColumn;
					if ( iColumn < Constants.Classroom_Width )
						cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];
					
					++iRow;
					iColumn -= 3;
					if ( iRow < Constants.Classroom_Height && iColumn >= 0 )
						cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];
					
					++iColumn;
					if ( iRow < Constants.Classroom_Height )
						cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];
					
					++iColumn;
					if ( iRow < Constants.Classroom_Height && iColumn < Constants.Classroom_Width )
						cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];
					
					++iRow;
					--iColumn;
					if ( iRow < Constants.Classroom_Height )
						cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];
					break;
				case Corpse:
					//��ü�� �þ߰� ������ �ڽŰ� ���� ĭ�� �ִ� �÷��̾� ��ϸ� Ȯ�� ����
					for ( iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
					{
						for ( iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
						{
							if ( stat.position.equals(iRow, iColumn) )
								cellsOfThePlayer[iRow][iColumn] = cellInfos_playerInfosOnly[iRow][iColumn];
							else
								cellsOfThePlayer[iRow][iColumn] = CellInfo.Blank;
						}
					}
					break;
				case Infected:
					//����ü�� �þߴ� �ڽ� �߽� 5x5ĭ�̸� ���ǽ� �� ��� ��ü ��ġ Ȯ�� ����
					for ( iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
					{
						for ( iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
						{
							int row_distance = iRow - stat.position.row;
							int column_distance = iColumn - stat.position.column;
							
							if ( row_distance >= -2 && row_distance <= 2 &&
									column_distance >= -2 && column_distance <= 2 )
								cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];
							else
								cellsOfThePlayer[iRow][iColumn] = cellInfos_corpseInfosOnly[iRow][iColumn];
						}
					}
					break;
				case Soul:
					//��ȥ�� �þߴ� ���ǽ� ��ü�̹Ƿ� �׳� ��� ���� ������ ��
					for ( iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
					{
						for ( iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
						{
							cellsOfThePlayer[iRow][iColumn] = cellInfos[iRow][iColumn];
						}
					}
					break;
				}
			}
		}
	}

	/**
	 * ���ǽ� �ܺο� ���� ��Ȳ�� �˸��� ���� �־��� �ݹ� �޼��带 ȣ���մϴ�.
	 * �ݹ� �޼��尡 false�� ��ȯ�� ��� �ܺο��� lock�� ������ ������ ���� ������ �Ͻ� �����մϴ�.
	 * 
	 * @param callback
	 *            ȣ���� �ݹ� �޼����Դϴ�. ���⿡�� �ַ� �̸� ������ ���� <code>callback_</code>�迭 �ʵ�� �� �ϳ��� ���˴ϴ�.
	 */
	private void DoCallBack(Supplier<Boolean> callback)
	{
		synchronized ( lock )
		{
			ClassroomStateCode oldState = state;

			if ( callback.get() == false )
			{
				state = ClassroomStateCode.Waiting_Callback_Lock;

				try
				{
					lock.wait();
				}
				catch ( InterruptedException e )
				{
				}

				state = oldState;
			}
		}
	}

	/**
	 * �־��� ��ǥ�� ������ ĭ�� ����Ű�� ��� ��ǥ�� ��ȯ�մϴ�.
	 * ���� ���ǽ��� ����� ��� ������ �ϴ÷� ���� �ϹǷ� <code>Constants.Pos_Sky</code>�� ��ȯ�մϴ�.
	 */
	private Point_Immutable GetSamePoint_Immutable(Point point)
	{
		//��ǥ�� null�� ��� ������ ��ư�
		if ( point == null )
			return Constants.Pos_Sky;
		
		int targetRow = point.row;
		int targetColumn = point.column;

		if ( targetRow < 0 || targetRow >= Constants.Classroom_Height || targetColumn < 0 || targetColumn >= Constants.Classroom_Width )
			return Constants.Pos_Sky;

		return points[targetRow][targetColumn];
	}

	/**
	 * �־��� ���� ��ǥ���� ������ ���⿡ ��ġ�� ���� ����� ��� ��ǥ�� ��ȯ�մϴ�.
	 * ���� ���ǽ��� ����� ��� ������ �ϴ÷� ���� �ϹǷ� <code>Constants.Pos_Sky</code>�� ��ȯ�մϴ�.
	 */
	private Point_Immutable GetAdjacentPoint_Immutable(Point_Immutable origin, DirectionCode direction)
	{
		int targetRow;
		int targetColumn;

		//������ null�� ��� ������ ��ư�
		if ( direction == null )
			return Constants.Pos_Sky;
		
		switch ( direction )
		{
		case Up:
			targetRow = origin.row - 1;
			targetColumn = origin.column;
			break;
		case Left:
			targetRow = origin.row;
			targetColumn = origin.column - 1;
			break;
		case Right:
			targetRow = origin.row;
			targetColumn = origin.column + 1;
			break;
		case Down:
			targetRow = origin.row + 1;
			targetColumn = origin.column;
			break;
		default:
			targetRow = origin.row;
			targetColumn = origin.column;
			break;
		}

		if ( targetRow < 0 || targetRow >= Constants.Classroom_Height || targetColumn < 0 || targetColumn >= Constants.Classroom_Width )
			return Constants.Pos_Sky;

		else
			return points[targetRow][targetColumn];
	}

	/**
	 * �ش� Max �ι� ������ �����մϴ�. �־��� ���� ���� �������� ū ��� ������ �̷�����ϴ�.
	 */
	private void UpdateScore_Max(int title, int ID, int value)
	{
		if ( scores[ID][title] < value )
			scores[ID][title] = value;
	}

	/**
	 * �ش� Total �ι� ������ �����մϴ�. �־��� ���� ���� ������ ���մϴ�.
	 */
	private void UpdateScore_Total(int title, int ID, int value)
	{
		scores[ID][title] += value;
	}

	/**
	 * ���� ��ϵ� �������� ���� ���� �� ������ �����մϴ�.
	 * �����ڰ� �߻��� ��� ���� ����� �÷��̾�(ID�� �� ���� �÷��̾�)�� �̱�ϴ�.
	 */
	private void CalculateGrades()
	{
		int currentTitle;
		int currentValue;
		int otherValue;
		int iPlayer;
		int[] priorityOfTitles = new int[6];

		/*
		 * ������ �ʱ�ȭ - �ϴ� ��� 0������ ���� --> ���� ��� 1��!
		 */
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
		{
			for ( currentTitle = 0; currentTitle < Score_Titles; currentTitle++ )
			{
				grades[iPlayer][currentTitle] = 0;
				ranks[iPlayer][currentTitle] = 1;
			}

			final_grades[iPlayer] = 0;
			final_ranks[iPlayer] = 1;
		}

		/*
		 * �ι��� ���� ����
		 */
		CalculateGrades_Max(Score_Survivor_Max);
		CalculateGrades_Total(Score_Survivor_Total);
		CalculateGrades_Max(Score_Corpse_Max);
		CalculateGrades_Total(Score_Corpse_Total);
		CalculateGrades_Max(Score_Infected_Max);
		CalculateGrades_Total(Score_Infected_Total);
		CalculateGrades_Total(Score_Soul_Freedom);
		CalculateGrades_Total(Score_Soul_Destruction);

		/*
		 * �������� ����
		 */
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
		{
			// ����� �ι� ������ ���� �켱 ���� ����
			for ( currentTitle = 0; currentTitle < 6; ++currentTitle )
			{
				priorityOfTitles[currentTitle] = 0;
				currentValue = grades[iPlayer][currentTitle];
				
				for ( int otherTitle = 0; otherTitle < currentTitle; ++otherTitle )
				{
					otherValue = grades[iPlayer][otherTitle];
					
					if ( currentValue > otherValue )
						++priorityOfTitles[otherTitle];
					else
						++priorityOfTitles[currentTitle];
				}
			}
			
			// �켱 ������ ���� �� �ι� ������ �ջ��Ͽ� �������� ����
			for ( currentTitle = 0; currentTitle < 6; ++currentTitle )
			{
				if ( priorityOfTitles[currentTitle] < 4 )
					final_grades[iPlayer] += grades[iPlayer][currentTitle];
			}
		}
		
		/*
		 * ���������� ���� ���� ���� ����
		 */
		// �÷��̾ ���� �� ���
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
		{
			currentValue = final_grades[iPlayer];

			// ������ ��ϵ� ����ǥ����...
			for ( int iOtherPlayer = 0; iOtherPlayer < iPlayer; iOtherPlayer++ )
			{
				otherValue = final_grades[iOtherPlayer];

				// ������ ���������� ���� ����� ������ '���� ���� ���� �ö������' �� ����� ���� 1 �϶�
				if ( currentValue > otherValue )
					++final_ranks[iOtherPlayer];

				// ���� ���������� ���ų� �� ���� ����� ������ ����� ������ '���� �� ���� �� ����� ������' �� ���� 1 �϶�
				else
					++final_ranks[iPlayer];
			}
		}

		// �÷��̾ ���� ���� ���� ������ �÷��̾� ID ����
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
			final_rankedPlayers[final_ranks[iPlayer]] = iPlayer;
	}

	/**
	 * �־��� �ι��� ���� ������ �����մϴ�.
	 * Max �ι��� 1���� ������ 100���� ���� ���� ����ȭ�� �����մϴ�.
	 * �����ڰ� �߻��� ��� ���� ����� �÷��̾�(ID�� �� ���� �÷��̾�)�� �̱�ϴ�.
	 */
	private void CalculateGrades_Max(int title)
	{
		int currentValue;
		int otherValue;
		int iPlayer;
		int maxScore = 0;

		// �ִ밪 ����
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
		{
			// NPC�� �ƴ� ���� �ִ밪 ���� ������ �ݿ�
			if ( iPlayer > settings.numberOfNPCs ||
					iPlayer == settings.numberOfNPCs && settings.use_ctrlable_player == false )
			{
				currentValue = scores[iPlayer][title];
				if ( currentValue > maxScore )
				{
					maxScore = currentValue;
				}
			}
		}

		// ���� �� ���� ����
		// �� �� �ƹ��� ������ ���� �� �� ��� 0���� ������ �Ǿ� ���ǽ��� �����ϹǷ� �׳� ��� ����ó��(������ �� ������� �Ű���)
		if ( maxScore == 0 )
		{
			for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
			{
				grades[iPlayer][title] = 100;

				ranks[iPlayer][title] = iPlayer + 1;
			}
		}
		// �׷��� ���� ��� O(n^2) �⵿
		else
		{
			// �÷��̾ ���� �� ���
			for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
			{
				currentValue = scores[iPlayer][title] * 100 / maxScore;
				
				if ( currentValue > 100 )
					currentValue = 100;
				
				grades[iPlayer][title] = currentValue;

				// ������ ��ϵ� ����ǥ����...
				for ( int iOtherPlayer = 0; iOtherPlayer < iPlayer; iOtherPlayer++ )
				{
					otherValue = grades[iOtherPlayer][title];

					// ������ ������ ���� ����� ������ '���� ���� ���� �ö������' �� ����� ���� 1 �϶�
					if ( currentValue > otherValue )
						++ranks[iOtherPlayer][title];

					// ���� ������ ���ų� �� ���� ����� ������ '���� �� ���� �� ����� ������' �� ���� 1 �϶�
					else
						++ranks[iPlayer][title];
				}
			}
		}

		// �÷��̾ ���� ���� ���� ������ �÷��̾� ID ����
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
			rankedPlayers[title][ranks[iPlayer][title]] = iPlayer;
	}

	/**
	 * �־��� �ι��� ���� ������ �����մϴ�.
	 * Total �ι��� 1���� ������ 100, �ڿ��� 1���� ������ 0���� ���� ���� ����ȭ�� �����մϴ�.
	 * �����ڰ� �߻��� ��� ���� ����� �÷��̾�(ID�� �� ���� �÷��̾�)�� �̱�ϴ�.
	 */
	private void CalculateGrades_Total(int title)
	{
		int currentValue;
		int otherValue;
		int iPlayer;
		int maxScore = 0;
		int minScore = Integer.MAX_VALUE;

		// �ִ밪, �ּҰ� ����
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
		{
			// NPC�� �ƴ� ���� �ִ밪, �ּҰ� ���� ������ �ݿ�
			if ( iPlayer > settings.numberOfNPCs ||
					iPlayer == settings.numberOfNPCs && settings.use_ctrlable_player == false )
			{
				currentValue = scores[iPlayer][title];
				if ( currentValue > maxScore )
					maxScore = currentValue;
				if ( currentValue < minScore )
					minScore = currentValue;
			}
		}

		// ���� �� ���� ����(�� �������� ���� �������� Max������ �̸� min������ �� ��)
		maxScore -= minScore;

		// �� �� ������ �� �Ȱ��� ��� 0���� ������ �Ǿ� ���ǽ��� �����ϹǷ� �׳� ��� ����ó��(������ �� ������� �Ű���)
		if ( maxScore == 0 )
		{
			for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
			{
				grades[iPlayer][title] = 100;

				ranks[iPlayer][title] = iPlayer + 1;
			}
		}
		// �׷��� ���� ��� O(n^2) �⵿
		else
		{
			for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
			{
				currentValue = ( scores[iPlayer][title] - minScore ) * 100 / maxScore;
				
				if ( currentValue < 0 )
					currentValue = 0;
				
				if ( currentValue > 100 )
					currentValue = 100;
				
				grades[iPlayer][title] = currentValue;

				// ������ ��ϵ� ����ǥ����...
				for ( int iOtherPlayer = 0; iOtherPlayer < iPlayer; iOtherPlayer++ )
				{
					otherValue = grades[iOtherPlayer][title];

					// ������ ������ ���� ����� ������ '���� ���� ���� �ö������' �� ����� ���� 1 �϶�
					if ( currentValue > otherValue )
						++ranks[iOtherPlayer][title];

					// ���� ������ ���ų� �� ���� ����� ������ '���� �� ���� �� ����� ������' �� ���� 1 �϶�
					else
						++ranks[iPlayer][title];
				}
			}
		}

		// �÷��̾ ���� ���� ���� ������ �÷��̾� ID ����
		for ( iPlayer = 0; iPlayer < Constants.Total_Players; iPlayer++ )
			rankedPlayers[title][ranks[iPlayer][title]] = iPlayer;
	}

	/**
	 * �� ������ �ܼ� â�� ����մϴ�.
	 * �� �޼���� �� ���� ������ ���� ȣ��˴ϴ�. 
	 */
	private void PrintResultToConsole_EndTurn()
	{
		// �÷��̾�#0�� ���� ��Ҹ� ����Ϸ��� ���
		if ( settings.print_first_player_only == true )
		{
			PlayerStat stat = playerStats[0];
			Point_Immutable pos = stat.position;

			if ( settings.print_decisions == true )
			{
				switch (decisions[0].type)
				{
				case Survivor_Move:
					System.out.println("Decision: Move to " + decisions[0].move_direction + " as Survivor");
					break;
				case Corpse_Stay:
					System.out.println("Decision: Stay as Corpse");
					break;
				case Infected_Move:
					System.out.println("Decision: Move to " + decisions[0].move_direction + " as Infected");
					break;
				case Soul_Stay:
					System.out.println("Decision: Stay as Soul");
					break;
				case Soul_Spawn:
					System.out.println("Decision: Spawn at " + decisions[0].spawn_point);
					break;
				default:
					break;
				}
			}

			if ( settings.print_actions == true )
			{
				switch (decisions[0].type)
				{
				case Survivor_Move:
				case Infected_Move:
					System.out.println("Action: Moved from " + decisions[0].location_from + " to " + decisions[0].location_to);
					break;
				case Soul_Spawn:
					System.out.println("Action: Spawned at " + decisions[0].location_to);
					break;
				default:
					break;
				}
			}
			
			if ( settings.print_playerInfos == true )
			{
				System.out.println(players[0].name + " | State: " + stat.state + ", HP: " + stat.HP + ", Position: " + pos);
			}
			
			if ( settings.print_scores_at_each_turns == true )
			{
				System.out.println(
						"Score:\n" +
								"SMax STot CMax CTot IMax ITot");

				System.out.printf("%4d %4d %4d %4d %4d %4d\n",
						scores[0][Score_Survivor_Max], scores[0][Score_Survivor_Total],
						scores[0][Score_Corpse_Max], scores[0][Score_Corpse_Total],
						scores[0][Score_Infected_Max], scores[0][Score_Infected_Total]);
			}
		}
		// ��� �÷��̾ ���� ����Ϸ��� ���
		else
		{
			if ( settings.print_decisions == true )
			{
				System.out.println("Decisions:");
				for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
				{
					switch (decisions[iPlayer].type)
					{
					case Survivor_Move:
						System.out.println(players[iPlayer].name + ": Move to " + decisions[iPlayer].move_direction + " as Survivor");
						break;
					case Corpse_Stay:
						System.out.println(players[iPlayer].name + ": Stay as Corpse");
						break;
					case Infected_Move:
						System.out.println(players[iPlayer].name + ": Move to " + decisions[iPlayer].move_direction + " as Infected");
						break;
					case Soul_Stay:
						System.out.println(players[iPlayer].name + ": Stay as Soul");
						break;
					case Soul_Spawn:
						System.out.println(players[iPlayer].name + ": Spawn at " + decisions[iPlayer].location_to);
						break;
					default:
						break;
					}
				}
			}

			if ( settings.print_actions == true )
			{
				System.out.println("Moves:");
				for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
					switch (decisions[iPlayer].type)
					{
					case Survivor_Move:
					case Infected_Move:
						System.out.println(players[iPlayer].name + ": Moved from " + decisions[iPlayer].location_from + " to " + decisions[iPlayer].location_to);
						break;
					default:
						break;
					}
				System.out.println("Spawns:");
				for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
					switch (decisions[0].type)
					{
					case Soul_Spawn:
						System.out.println(players[iPlayer].name + ": Spawned at " + decisions[iPlayer].location_to);
						break;
					default:
						break;
					}
			}

			if ( settings.print_reactions == true )
			{
				System.out.println("Reactions(except Spots):");
				for ( Cell[] cell_row : cells )
				{
					for ( Cell cell : cell_row )
					{
						for ( Reaction reaction : cell.reactions )
						{
							System.out.println("Type: " + reaction.type + ", Subject: " + players[reaction.subjectID].name +
								", Object: " + players[reaction.objectID].name + ", Location: " + reaction.location_subject);
						}
					}
				}
			}
			
			if ( settings.print_playerInfos == true )
			{
				System.out.println("playerInfos:");
				for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
					System.out.println(players[iPlayer].name + " | State: " + playerStats[iPlayer].state + ", HP: " + playerStats[iPlayer].HP + ", Position: " + playerStats[iPlayer].position);
			}

			if ( settings.print_scores_at_each_turns == true )
			{
				System.out.println(
						"Score:\n" +
								"SMax STot CMax CTot IMax ITot - Name");
				for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
					System.out.printf("%4d %4d %4d %4d %4d %4d - %s\n",
							scores[iPlayer][Score_Survivor_Max], scores[iPlayer][Score_Survivor_Total],
							scores[iPlayer][Score_Corpse_Max], scores[iPlayer][Score_Corpse_Total],
							scores[iPlayer][Score_Infected_Max], scores[iPlayer][Score_Infected_Total],
							players[iPlayer].name);
			}
		}
	}
	
	/**
	 * �� ������ �ܼ� â�� ����մϴ�.
	 * �� �޼���� ��ü ������ ������ ���� ȣ��˴ϴ�.
	 */
	private void PrintResultToConsole_EndGame()
	{
		System.out.println("Result of Game#" + gameNumber);
		
		if ( settings.print_first_player_only == true )
		{
			if ( settings.print_scores_at_the_end == true )
			{
				for(int i = 0; i < Constants.Total_Players; ++i)
				{
					if(settings.print_actions == true){

						System.out.println(
								players[i].name + " Score| Grade(Rank):\n" +
										"         SMax           STot           CMax           CTot           IMax           ITot |     Final");
						System.out.printf("%4d|%4d(%2d)  %4d|%4d(%2d)  %4d|%4d(%2d)  %4d|%4d(%2d)  %4d|%4d(%2d)  %4d|%4d(%2d) |  %4d(%2d)\n",
								scores[i][0], grades[i][0], ranks[i][0], scores[i][1], grades[i][1], ranks[i][1], scores[i][2], grades[i][2],
								ranks[i][2], scores[i][3], grades[i][3], ranks[i][3], scores[i][4], grades[i][4], ranks[i][4], scores[i][5],
								grades[i][5], ranks[i][5], final_grades[i], final_ranks[i]);
						//System.out.println(
						//		"Score| Grade(Rank):\n" +
						//				"         SMax           STot           CMax           CTot           IMax           ITot |     Final");
						//System.out.printf("%4d|%4d(%2d)  %4d|%4d(%2d)  %4d|%4d(%2d)  %4d|%4d(%2d)  %4d|%4d(%2d)  %4d|%4d(%2d) |  %4d(%2d)\n", scores[0][0], grades[0][0], ranks[0][0], scores[0][1], grades[0][1], ranks[0][1], scores[0][2], grades[0][2], ranks[0][2], scores[0][3], grades[0][3], ranks[0][3], scores[0][4], grades[0][4], ranks[0][4], scores[0][5], grades[0][5], ranks[0][5], final_grades[0], final_ranks[0]);
					
					} else {

						List<Integer> score = new ArrayList<Integer>();
						score.add(scores[i][0]);
						score.add(grades[i][0]);
						score.add(ranks[i][0]);
						score.add(scores[i][1]);
						score.add(grades[i][1]);
						score.add(ranks[i][1]);
						score.add(scores[i][2]);
						score.add(grades[i][2]);
						score.add(ranks[i][2]);
						score.add(scores[i][3]);
						score.add(grades[i][3]);
						score.add(ranks[i][3]);
						score.add(scores[i][4]);
						score.add(grades[i][4]);
						score.add(ranks[i][4]);
						score.add(scores[i][5]);
						score.add(grades[i][5]);
						score.add(ranks[i][5]);
						score.add(final_grades[i]);
						score.add(final_ranks[i]);
						test.push(players[i].name, score);
					}
				}
				
			}
			else
			{
				System.out.println(
						"Grade(Rank):\n" +
								"    SMax      STot      CMax      CTot      IMax      ITot |     Final");
				System.out.printf("%4d(%2d)  %4d(%2d)  %4d(%2d)  %4d(%2d)  %4d(%2d)  %4d(%2d) |  %4d(%2d)\n", grades[0][0], ranks[0][0], grades[0][1], ranks[0][1], grades[0][2], ranks[0][2], grades[0][3], ranks[0][3], grades[0][4], ranks[0][4], grades[0][5], ranks[0][5], final_grades[0], final_ranks[0]);
			}
		}
		else
		{
			if ( settings.print_scores_at_the_end == true )
			{
				System.out.println(
						"Score| Grade(Rank):\n" +
								"         SMax           STot           CMax           CTot           IMax           ITot |     Final - Name");
				for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
					System.out.printf("%4d|%4d(%2d)  %4d|%4d(%2d)  %4d|%4d(%2d)  %4d|%4d(%2d)  %4d|%4d(%2d)  %4d|%4d(%2d) |  %4d(%2d) - %s\n", scores[iPlayer][0], grades[iPlayer][0], ranks[iPlayer][0], scores[iPlayer][1], grades[iPlayer][1], ranks[iPlayer][1], scores[iPlayer][2], grades[iPlayer][2], ranks[iPlayer][2], scores[iPlayer][3], grades[iPlayer][3], ranks[iPlayer][3], scores[iPlayer][4], grades[iPlayer][4], ranks[iPlayer][4], scores[iPlayer][5], grades[iPlayer][5], ranks[iPlayer][5], final_grades[iPlayer], final_ranks[iPlayer], players[iPlayer].name);
			}
			else
			{
				System.out.println(
						"Grade(Rank):\n" +
								"    SMax      STot      CMax      CTot      IMax      ITot |     Final - Name");
				for ( int iPlayer = 0; iPlayer < Constants.Total_Players; ++iPlayer )
					System.out.printf("%4d(%2d)  %4d(%2d)  %4d(%2d)  %4d(%2d)  %4d(%2d)  %4d(%2d) |  %4d(%2d) - %s\n", grades[iPlayer][0], ranks[iPlayer][0], grades[iPlayer][1], ranks[iPlayer][1], grades[iPlayer][2], ranks[iPlayer][2], grades[iPlayer][3], ranks[iPlayer][3], grades[iPlayer][4], ranks[iPlayer][4], grades[iPlayer][5], ranks[iPlayer][5], final_grades[iPlayer], final_ranks[iPlayer], players[iPlayer].name);
			}
		}
	}

	/**
	 * ù �÷��̾��� �ǻ� ������ ���� �� ������ �ܼ� â�� ����մϴ�.
	 */
	private void PrintStartInfoToConsole_FirstPlayerOnly(boolean isForSpawn)
	{
		//�̵� ������ �ִ� ����
		if ( isForSpawn == false )
		{
			if ( settings.print_playerInfos == true || 
					settings.print_actions == true ||
					settings.print_reactions == true)
				System.out.println("------ Informations for Move/Stay decision ------");
			
			if ( settings.print_playerInfos == true )
			{
				System.out.println("Players on current sight:");

				for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
					for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
						players[0].cells[iRow][iColumn].ForEach_Players(player ->
						{
							System.out.println(players[player.ID].name + " | State: " + player.state + ", HP: " + player.HP + ", Position: " + player.position);
						});
			}

			if ( settings.print_actions == true )
			{
				System.out.println("Actions seen during last turn:");

				for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
					for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
						players[0].cells[iRow][iColumn].ForEach_Actions(action ->
						{
							if ( action.type == Action.TypeCode.Move )
								System.out.println(players[action.actorID].name + ": Moved from " + action.location_from + " to " + action.location_to);
							else
								System.out.println(players[action.actorID].name + ": Spawned at " + action.location_to);
						});
			}
			
			if ( settings.print_reactions == true )
			{
				System.out.println("Reactions seen during last turn:");

				for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
					for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
						players[0].cells[iRow][iColumn].ForEach_Reactions(reaction ->
						{
							System.out.println("Type: " + reaction.type + ", Subject: " + players[reaction.subjectID].name +
								", Object: " + players[reaction.objectID].name + ", Location: " + reaction.location_subject);
						});
			}
		}
		
		//��ġ ������ �ִ� ����
		else
		{
			if ( settings.print_playerInfos == true || 
					settings.print_actions == true ||
					settings.print_reactions == true)
				System.out.println("------ Informations for Spawn decision ------");
			
			if ( settings.print_playerInfos == true )
			{
				System.out.println("Players on current sight:");
	
				for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
					for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
						players[0].cells[iRow][iColumn].ForEach_Players(player ->
						{
							System.out.println(players[player.ID].name + " | State: " + player.state + ", HP: " + player.HP + ", Position: " + player.position);
						});
			}
			
			if ( settings.print_actions == true )
			{
				System.out.println("Actions seen during current turn\'s move phase:");

				for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
					for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
						players[0].cells[iRow][iColumn].ForEach_Actions(action ->
						{
							if ( action.type == Action.TypeCode.Move )
								System.out.println(players[action.actorID].name + ": Moved from " + action.location_from + " to " + action.location_to);
							else
								System.out.println(players[action.actorID].name + ": Spawned at " + action.location_to);
						});
			}
			
			if ( settings.print_reactions == true )
			{
				System.out.println("Reactions seen current turn\'s move phase:");

				for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
					for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
						players[0].cells[iRow][iColumn].ForEach_Reactions(reaction ->
						{
							System.out.println("Type: " + reaction.type + ", Subject: " + players[reaction.subjectID].name +
								", Object: " + players[reaction.objectID].name + ", Location: " + reaction.location_subject);
						});
			}
		}
	}
}
