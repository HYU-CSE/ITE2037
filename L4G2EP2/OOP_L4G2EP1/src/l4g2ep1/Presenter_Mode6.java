package l4g2ep1;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import l4g2ep1.PlayerInfo.State;
import l4g2ep1.common.*;
import loot.ImageResourceManager;

public class Presenter_Mode6 extends JFrame
{
	// �� ���� ������ �� ������ ��� �߱⿡ �׳� ���� �� ���� ��ȣ
	private static final long serialVersionUID = 1L;
	
	enum PresenterState
	{
		Not_Started,
		Running,
		Waiting,
		Skipping,
		Completed
	}
	
	/*
	 * ���� ���� ���� �ʵ� ���
	 */
	private PresenterState state;
	private Classroom_Settings settings;
	private Classroom classroom;
	private Thread thr;
	
	private int cursor_x = Constants.Classroom_Width / 2;
	private int cursor_y = Constants.Classroom_Height / 2;
	
	/*
	 * UI ���� �ʵ� ��� 
	 */
	private Font font_dot_um_che;				//�ؽ�Ʈ ��¿� ����� ����ü font
	private loot.ImageResourceManager images;	//���ǽ� �׸��⿡ ����� �̹����� ������ �ִ� ģ��

	private Canvas canvas_classroom;			//���ǽ��� ���� ����� �׸� ĵ����
	private JTextArea tb_myInfo;				//�� ���� �� ���� ������ ����� �ؽ�Ʈ �ڽ�
	private JTextArea tb_cellInfo;				//Ŀ���� ����Ű�� �ִ� ĭ�� ������ ����� �ؽ�Ʈ �ڽ�
	private JTextArea tb_othersInfo;			//���� �˰� �ִ� �ֺ� �������� ����� �ؽ�Ʈ �ڽ�
	
	
	public Presenter_Mode6(Classroom_Settings settings)
	{
		/*
		 * Frame �ʱ�ȭ
		 */
		super("L4G2EP1 Presenter - mode 6");
		rootPane.setPreferredSize(new Dimension(800, 600));
		setResizable(false);
		setLocationByPlatform(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(listener_key);
		setLayout(null);

		/*
		 * ���� ���� ���� �ʵ� ����
		 */
		this.settings = settings;
		
		/*
		 * UI ���� �ʵ� ����
		 */
		font_dot_um_che = new Font("����ü", Font.PLAIN, 12);
		
		images = new ImageResourceManager();
		
		JLabel label_gameInfo = new JLabel();
		label_gameInfo.setSize(452, 12);
		label_gameInfo.setLocation(8, 8);
		label_gameInfo.setText("Classroom overview");
		add(label_gameInfo);
		
		canvas_classroom = new Canvas();
		canvas_classroom.setSize(452, 452);
		canvas_classroom.setLocation(8, 8+12+8);
		canvas_classroom.setIgnoreRepaint(true);
		canvas_classroom.setBackground(Color.black);
		canvas_classroom.addKeyListener(listener_key);
		canvas_classroom.addMouseListener(listener_canvas_classroom_mouse);
		canvas_classroom.addMouseMotionListener(listener_canvas_classroom_mouseMove);
		add(canvas_classroom);
		
		JLabel label_myInfo = new JLabel();
		label_myInfo.setSize(452, 12);
		label_myInfo.setLocation(8, 8+12+8+452+8);
		label_myInfo.setText("Informations of my player");
		add(label_myInfo);
		
		tb_myInfo = new JTextArea();
		tb_myInfo.setEditable(false);
		tb_myInfo.setFocusable(false);
		tb_myInfo.addKeyListener(listener_key);
		tb_myInfo.setFont(font_dot_um_che);
		tb_myInfo.setText(
				"Game is not started.\n\n\n\n" +
				"TODO: Press 1 to start game."
				);
		
		JScrollPane panel_tb_myInfo = new JScrollPane(tb_myInfo);
		panel_tb_myInfo.setSize(452, 600-8-12-8-452-8-12-8-8);
		panel_tb_myInfo.setLocation(8, 8+12+8+452+8+12+8);
		add(panel_tb_myInfo);

		JLabel label_cellInfo = new JLabel();
		label_cellInfo.setSize(324, 12);
		label_cellInfo.setLocation(468, 8);
		label_cellInfo.setText("Informations of selected cell");
		add(label_cellInfo);
		
		tb_cellInfo = new JTextArea();
		tb_cellInfo.setEditable(false);
		tb_cellInfo.setFocusable(false);
		tb_cellInfo.addKeyListener(listener_key);
		tb_cellInfo.setFont(font_dot_um_che);

		JScrollPane panel_tb_cellInfo = new JScrollPane(tb_cellInfo);
		panel_tb_cellInfo.setSize(324, 300);
		panel_tb_cellInfo.setLocation(468, 8+12+8);
		panel_tb_cellInfo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(panel_tb_cellInfo);
		

		JLabel label_othersInfo = new JLabel();
		label_othersInfo.setSize(324, 12);
		label_othersInfo.setLocation(468, 8+12+8+300+8);
		label_othersInfo.setText("Informations you know");
		add(label_othersInfo);
		
		tb_othersInfo = new JTextArea();
		tb_othersInfo.setEditable(false);
		tb_othersInfo.setFocusable(false);
		tb_othersInfo.addKeyListener(listener_key);
		tb_othersInfo.setFont(font_dot_um_che);

		JScrollPane panel_tb_othersInfo = new JScrollPane(tb_othersInfo);
		panel_tb_othersInfo.setSize(324, 600-8-12-8-300-8-12-8-8);
		panel_tb_othersInfo.setLocation(468, 8+12+8+300+8+12+8);
		panel_tb_othersInfo.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(panel_tb_othersInfo);
		
		pack();
		
		canvas_classroom.createBufferStrategy(2);
		
		images.LoadImage("Images/cell_outOfSight.png", "cell_outOfSight");
		images.LoadImage("Images/cell_empty.png", "cell_empty");
		images.LoadImage("Images/cell_survivor.png", "cell_survivor");
		images.LoadImage("Images/cell_corpse.png", "cell_corpse");
		images.LoadImage("Images/cell_infected.png", "cell_infected");
		images.LoadImage("Images/cell_survivor_corpse.png", "cell_survivor_corpse");
		images.LoadImage("Images/cell_infected_corpse.png", "cell_infected_corpse");
		images.LoadImage("Images/cell_empty_spot.png", "cell_empty_spot");
		images.LoadImage("Images/cell_survivor_spot.png", "cell_survivor_spot");
		images.LoadImage("Images/cell_corpse_spot.png", "cell_corpse_spot");
		images.LoadImage("Images/cell_infected_spot.png", "cell_infected_spot");
		images.LoadImage("Images/cell_survivor_corpse_spot.png", "cell_survivor_corpse_spot");
		images.LoadImage("Images/cell_infected_corpse_spot.png", "cell_infected_corpse_spot");
		images.LoadImage("Images/cell_corpse_detect.png", "cell_corpse_detect");
		images.LoadImage("Images/myPosition_survivor.png", "myPosition_survivor");
		images.LoadImage("Images/myPosition_corpse.png", "myPosition_corpse");
		images.LoadImage("Images/myPosition_infected.png", "myPosition_infected");
		images.LoadImage("Images/cursor.png", "cursor");
		images.LoadImage("Images/cursor_invalid.png", "cursor_invalid");
		
		state = PresenterState.Not_Started;
	}
	
	private KeyListener listener_key = new KeyListener()
	{
		public void keyTyped(KeyEvent e) { }
		public void keyReleased(KeyEvent e) { }
		
		@Override
		public void keyPressed(KeyEvent e)
		{
			//���� �������� �ʾҰų� �̹� ������ �� '1'�� ���� ��� �ٽ� ����
			if ( state == PresenterState.Not_Started || state == PresenterState.Completed )
			{
				if ( e.getKeyChar() == '1' )
				{
					state = PresenterState.Running;
	
					classroom = new Classroom(settings);
					classroom.game_completed = listener_classroom_game_completed;
					classroom.request_decision = listener_classroom_request_decision;
					classroom.invalid_decision = listener_classroom_invalid_decision;
					
					thr = new Thread(classroom);
					thr.start();
				}
			}
			
			else if ( state == PresenterState.Waiting )
			{
				switch ( e.getKeyCode() )
				{
				case KeyEvent.VK_UP:
					--cursor_y;
					if ( cursor_y < 0 )
						cursor_y = 0;
					DrawClassroom();
					WriteCellInfo();
					break;
				case KeyEvent.VK_LEFT:
					--cursor_x;
					if ( cursor_x < 0 )
						cursor_x = 0;
					DrawClassroom();
					WriteCellInfo();
					break;
				case KeyEvent.VK_RIGHT:
					++cursor_x;
					if ( cursor_x >= Constants.Classroom_Width )
						cursor_x = Constants.Classroom_Width - 1;
					DrawClassroom();
					WriteCellInfo();
					break;
				case KeyEvent.VK_DOWN:
					++cursor_y;
					if ( cursor_y >= Constants.Classroom_Height )
						cursor_y = Constants.Classroom_Height - 1;
					DrawClassroom();
					WriteCellInfo();
					break;
				case KeyEvent.VK_SPACE:
					classroom.players[0].acceptDirectInfection = false;
					
					synchronized ( classroom )
					{
						classroom.accepted_point = new Point(cursor_x, cursor_y);
						classroom.notify();
					}
	
					state = PresenterState.Running;
					break;
				case KeyEvent.VK_ENTER:
					classroom.players[0].acceptDirectInfection = true;
					
					synchronized ( classroom )
					{
						classroom.accepted_point = new Point(cursor_x, cursor_y);
						classroom.notify();
					}
	
					state = PresenterState.Running;
					break;
				}
			}
		}
	};

	
	private MouseListener listener_canvas_classroom_mouse = new MouseListener()
	{
		public void mouseReleased(MouseEvent e) { }
		public void mouseExited(MouseEvent e) { }
		public void mouseEntered(MouseEvent e) { }
		public void mouseClicked(MouseEvent e) { }

		@Override
		public void mousePressed(MouseEvent e)
		{
			//������� ��� �ǻ� ���� ����
			if ( state == PresenterState.Waiting )
			{
				if ( e.getButton() == MouseEvent.BUTTON1 )
					classroom.players[0].acceptDirectInfection = true;
				else
					classroom.players[0].acceptDirectInfection = false;

				synchronized ( classroom )
				{
					classroom.accepted_point = new Point(cursor_x, cursor_y);
					classroom.notify();
				}

				state = PresenterState.Running;
			}
		}
	};
	
	private ActionListener listener_classroom_game_completed = new ActionListener()
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			state = PresenterState.Completed;
			
			DrawClassroom();
			WriteMyInfo();
			WriteOthersInfo();
			WriteCellInfo();
		}
	};
	
	private ActionListener listener_classroom_request_decision = new ActionListener()
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			DrawClassroom();
			WriteMyInfo();
			WriteOthersInfo();
			WriteCellInfo();

			state = PresenterState.Waiting;
		}
	};
	
	private ActionListener listener_classroom_invalid_decision = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			state = PresenterState.Waiting;
		}
	};
	
	private MouseMotionListener listener_canvas_classroom_mouseMove = new MouseMotionListener()
	{
		@Override
		public void mouseDragged(MouseEvent e) { }
		
		@Override
		public void mouseMoved(MouseEvent e)
		{
			if ( state == PresenterState.Waiting || state == PresenterState.Completed )
			{
				int new_cursor_x = e.getX();
				int new_cursor_y = e.getY();
				
				if ( new_cursor_x < 0 ||
					 new_cursor_y < 0 ||
					 new_cursor_x >= cell_actualWidth * Constants.Classroom_Width ||
					 new_cursor_y >= cell_actualWidth * Constants.Classroom_Height )
					return;
				
				new_cursor_x = new_cursor_x / cell_actualWidth;
				new_cursor_y = new_cursor_y / cell_actualWidth;
				
				if ( cursor_x != new_cursor_x || cursor_y != new_cursor_y )
				{
					cursor_x = new_cursor_x;
					cursor_y = new_cursor_y;
					
					DrawClassroom();
					WriteCellInfo();
				}
			}
		}
	};

	
	/* --------------------------------------------------------------
	 * 
	 * UI ��� ���� �ʵ�� / �޼����
	 * 
	 */
	
	static final int cell_actualWidth = 50;
	static final int cell_imgWidth = 52;

	/**
	 * 0x000: �� ����
	 * 0x001: ���� ���� ����
	 * 0x002: ���� ����
	 * 
	 * 0x010: ������ ����
	 * 0x020: ����ü ����
	 * 0x100: ��ü ����
	 */
	int[][] cell_drawSeeds = new int[Constants.Classroom_Height][Constants.Classroom_Width];
	
	/**
	 * ���ǽ��� ���� �׸��ϴ�.
	 */
	void DrawClassroom()
	{
		//���� ������ �������� �ʾҴٸ� �׸��� ����
		if ( state == PresenterState.Not_Started )
			return;

		Player myPlayer = classroom.players[0];
		
		BufferStrategy buf = canvas_classroom.getBufferStrategy();
		Graphics2D g = (Graphics2D) buf.getDrawGraphics();
		
		//�� ĭ�� ��� �׸� ������ ���� ����

		//���� �þ� ���� ���� üũ
		for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
			for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
			{
				//�� �÷��̾��� ���� �þ� ���� ���� �ִٸ� ǥ��
				if ( myPlayer.CanSee(iColumn, iRow) == true )
					cell_drawSeeds[iRow][iColumn] = 1;
				
				//�׷��� �ʴٸ� �켱 '�� ����'���� ǥ��
				else
					cell_drawSeeds[iRow][iColumn] = 0;
			}

		//���� ���� üũ
		switch ( myPlayer.myInfo.state )
		{
		case Survivor:
			//���� �����ڶ�� �þ� ���� ���� �ٸ� �����ڰ� �� �� �ִ� ĭ�� ���� ����
			for ( PlayerInfo other : myPlayer.othersInfo_withinSight )
			{
				if ( other.state == State.Survivor )
				{
					int iRow = other.position.y;
					int iColumn = other.position.x;
					
					/*
					 * �ش� �����ڰ� ���� ������ �ֺ� 12ĭ�� ���� üũ.
					 * 
					 * ����:
					 *     0
					 *   1 2 3
					 * 4 5   6 7
					 *   8 9 A
					 *     B
					 */
					
					iRow -= 2;
					if ( Point.IsValid(iRow, iColumn) && cell_drawSeeds[iRow][iColumn] == 0 )
						cell_drawSeeds[iRow][iColumn] = 2;

					++iRow;
					--iColumn;
					if ( Point.IsValid(iRow, iColumn) && cell_drawSeeds[iRow][iColumn] == 0 )
						cell_drawSeeds[iRow][iColumn] = 2;
					
					++iColumn;
					if ( Point.IsValid(iRow, iColumn) && cell_drawSeeds[iRow][iColumn] == 0 )
						cell_drawSeeds[iRow][iColumn] = 2;
					
					++iColumn;
					if ( Point.IsValid(iRow, iColumn) && cell_drawSeeds[iRow][iColumn] == 0 )
						cell_drawSeeds[iRow][iColumn] = 2;
					
					++iRow;
					iColumn -= 3;
					if ( Point.IsValid(iRow, iColumn) && cell_drawSeeds[iRow][iColumn] == 0 )
						cell_drawSeeds[iRow][iColumn] = 2;
					
					++iColumn;
					if ( Point.IsValid(iRow, iColumn) && cell_drawSeeds[iRow][iColumn] == 0 )
						cell_drawSeeds[iRow][iColumn] = 2;
					
					iColumn += 2;
					if ( Point.IsValid(iRow, iColumn) && cell_drawSeeds[iRow][iColumn] == 0 )
						cell_drawSeeds[iRow][iColumn] = 2;
					
					++iColumn;
					if ( Point.IsValid(iRow, iColumn) && cell_drawSeeds[iRow][iColumn] == 0 )
						cell_drawSeeds[iRow][iColumn] = 2;
					
					++iRow;
					iColumn -= 3;
					if ( Point.IsValid(iRow, iColumn) && cell_drawSeeds[iRow][iColumn] == 0 )
						cell_drawSeeds[iRow][iColumn] = 2;
					
					++iColumn;
					if ( Point.IsValid(iRow, iColumn) && cell_drawSeeds[iRow][iColumn] == 0 )
						cell_drawSeeds[iRow][iColumn] = 2;
					
					++iColumn;
					if ( Point.IsValid(iRow, iColumn) && cell_drawSeeds[iRow][iColumn] == 0 )
						cell_drawSeeds[iRow][iColumn] = 2;
					
					++iRow;
					--iColumn;
					if ( Point.IsValid(iRow, iColumn) && cell_drawSeeds[iRow][iColumn] == 0 )
						cell_drawSeeds[iRow][iColumn] = 2;
				}
			}
			break;
		case Infected:
			//���� ����ü��� ���� ������ ��ü�� �ִ� ĭ�� ���� ����
			for ( PlayerInfo other : myPlayer.othersInfo_detected )
				if ( cell_drawSeeds[other.position.y][other.position.x] == 0 )
					cell_drawSeeds[other.position.y][other.position.x] = 2;
			break;
		default:
			//���� ��ü �Ǵ� ��ȥ�̶�� ���� ���� ����
			break;
		}

		//�� ĭ�� ���� �ִ� �÷��̾� ������ ���� ĭ �׸��� ���� ���� �� �׸��� ����
		for ( int iRow = 0; iRow < Constants.Classroom_Height; ++iRow )
			for ( int iColumn = 0; iColumn < Constants.Classroom_Width; ++iColumn )
			{
				int temp_visibility = cell_drawSeeds[iRow][iColumn];
				
				switch ( cell_drawSeeds[iRow][iColumn] )
				{
				case 0:
					
					break;
				case 1:
					for ( PlayerInfo info : classroom.cells.data[iRow][iColumn].playersInTheCell )
						switch (info.state)
						{
						case Survivor:
							temp_visibility |= 0x010;
							break;
						case Corpse:
							temp_visibility |= 0x100;
							break;
						case Infected:
							temp_visibility |= 0x020;
							break;
						default:
							break;
						}
					break;
				default:
					if ( myPlayer.myInfo.state == State.Infected )
						for ( PlayerInfo info : classroom.cells.data[iRow][iColumn].playersInTheCell )
						{
							if ( info.state == State.Corpse )
								temp_visibility |= 0x100;
						}
					else
						for ( PlayerInfo info : classroom.cells.data[iRow][iColumn].playersInTheCell )
							switch (info.state)
							{
							case Survivor:
								temp_visibility |= 0x010;
								break;
							case Corpse:
								temp_visibility |= 0x100;
								break;
							case Infected:
								temp_visibility |= 0x020;
								break;
							default:
								break;
							}
					break;
				}
				
				cell_drawSeeds[iRow][iColumn] = temp_visibility;
				
				switch ( temp_visibility )
				{
				case 0x000:
					g.drawImage(images.GetImage("cell_outOfSight"), cell_actualWidth * iColumn, cell_actualWidth * iRow, cell_imgWidth, cell_imgWidth, null);
					break;
				case 0x001:
					g.drawImage(images.GetImage("cell_empty"), cell_actualWidth * iColumn, cell_actualWidth * iRow, cell_imgWidth, cell_imgWidth, null);
					break;
				case 0x011:
					g.drawImage(images.GetImage("cell_survivor"), cell_actualWidth * iColumn, cell_actualWidth * iRow, cell_imgWidth, cell_imgWidth, null);
					break;
				case 0x021:
					g.drawImage(images.GetImage("cell_infected"), cell_actualWidth * iColumn, cell_actualWidth * iRow, cell_imgWidth, cell_imgWidth, null);
					break;
				case 0x101:
					g.drawImage(images.GetImage("cell_corpse"), cell_actualWidth * iColumn, cell_actualWidth * iRow, cell_imgWidth, cell_imgWidth, null);
					break;
				case 0x111:
					g.drawImage(images.GetImage("cell_survivor_corpse"), cell_actualWidth * iColumn, cell_actualWidth * iRow, cell_imgWidth, cell_imgWidth, null);
					break;
				case 0x121:
					g.drawImage(images.GetImage("cell_infected_corpse"), cell_actualWidth * iColumn, cell_actualWidth * iRow, cell_imgWidth, cell_imgWidth, null);
					break;
				case 0x002:
					g.drawImage(images.GetImage("cell_empty_spot"), cell_actualWidth * iColumn, cell_actualWidth * iRow, cell_imgWidth, cell_imgWidth, null);
					break;
				case 0x012:
					g.drawImage(images.GetImage("cell_survivor_spot"), cell_actualWidth * iColumn, cell_actualWidth * iRow, cell_imgWidth, cell_imgWidth, null);
					break;
				case 0x022:
					g.drawImage(images.GetImage("cell_infected_spot"), cell_actualWidth * iColumn, cell_actualWidth * iRow, cell_imgWidth, cell_imgWidth, null);
					break;
				case 0x102:
					g.drawImage(images.GetImage("cell_corpse_spot"), cell_actualWidth * iColumn, cell_actualWidth * iRow, cell_imgWidth, cell_imgWidth, null);
					break;
				case 0x112:
					g.drawImage(images.GetImage("cell_survivor_corpse_spot"), cell_actualWidth * iColumn, cell_actualWidth * iRow, cell_imgWidth, cell_imgWidth, null);
					break;
				case 0x122:
					g.drawImage(images.GetImage("cell_infected_corpse_spot"), cell_actualWidth * iColumn, cell_actualWidth * iRow, cell_imgWidth, cell_imgWidth, null);
					break;
				}
			}
		
		//'�� ��ġ' ���̾� �׸��� ����
		switch ( myPlayer.myInfo.state )
		{
		case Survivor:
			g.drawImage(images.GetImage("myPosition_survivor"), cell_actualWidth * myPlayer.myInfo.position.x, cell_actualWidth * myPlayer.myInfo.position.y, cell_imgWidth, cell_imgWidth, null);
			break;
		case Corpse:
			g.drawImage(images.GetImage("myPosition_corpse"), cell_actualWidth * myPlayer.myInfo.position.x, cell_actualWidth * myPlayer.myInfo.position.y, cell_imgWidth, cell_imgWidth, null);
			break;
		case Infected:
			g.drawImage(images.GetImage("myPosition_infected"), cell_actualWidth * myPlayer.myInfo.position.x, cell_actualWidth * myPlayer.myInfo.position.y, cell_imgWidth, cell_imgWidth, null);
			break;
		default:
			break;
		}

		//���콺 Ŀ�� �׸��� ����
		if ( Point.IsValid(cursor_x, cursor_y) == true )
		{
			switch ( myPlayer.myInfo.state )
			{
			case Survivor:
			case Infected:
				if ( myPlayer.IsValidMove(cursor_x, cursor_y) == true )
					g.drawImage(images.GetImage("cursor"), cell_actualWidth * cursor_x, cell_actualWidth * cursor_y, cell_imgWidth, cell_imgWidth, null);
				else
					g.drawImage(images.GetImage("cursor_invalid"), cell_actualWidth * cursor_x, cell_actualWidth * cursor_y, cell_imgWidth, cell_imgWidth, null);
				break;
			default:
				if ( classroom.state == Classroom.State.Waiting_Decision_Soul_Spawn )
					g.drawImage(images.GetImage("cursor"), cell_actualWidth * cursor_x, cell_actualWidth * cursor_y, cell_imgWidth, cell_imgWidth, null);
				else
					g.drawImage(images.GetImage("cursor_invalid"), cell_actualWidth * cursor_x, cell_actualWidth * cursor_y, cell_imgWidth, cell_imgWidth, null);
				break;
			}
		}

		buf.show();
		g.dispose();
	}
	
	void WriteMyInfo()
	{
		PlayerInfo myInfo = classroom.playerInfos[0];
		
		StringBuilder sb = new StringBuilder();
		
		if ( state != PresenterState.Completed )
		{
			sb.append(String.format("Game#%d - Turn %d\n", classroom.gameInfo.gameNumber, classroom.gameInfo.currentTurnNumber));
			sb.append(String.format("State: %s | Hit point: %d | Position: %s\n", myInfo.state, myInfo.hitPoint, myInfo.position));
			sb.append(String.format("       SMax STot CMax CTot IMax ITot\n"));
			sb.append(String.format("Score: %s\n", classroom.players[0].myScore));
			
			switch ( classroom.state )
			{
			case Waiting_Decision_Survivor_Move:
			case Waiting_Decision_Infected_Move:
				if ( classroom.gameInfo.isDirectInfectionChoosingTurn == true )
					sb.append("TODO: Select a cell to move.\n      And press Enter key to accept direct infection,\n      or press Space Bar to decline it.");
				else
					sb.append("TODO: Select a cell to move. And press Enter key.");
				break;
			case Waiting_Decision_Soul_Spawn:
				sb.append("TODO: Select a cell to spawn. And press Enter key.");
				break;
			case Waiting_Decision_Corpse_Stay:
			case Waiting_Decision_Soul_Stay:
				sb.append("TODO: Press Enter key to advance this turn.");
				break;
			default:
				break;
			}
		}
		else
		{
			sb.append(String.format("Game#%d - Ended\n", classroom.gameInfo.gameNumber, classroom.gameInfo.currentTurnNumber));
			sb.append(String.format("       SMax STot CMax CTot IMax ITot\n"));
			sb.append(String.format("Score: %s\n", classroom.players[0].myScore));
			sb.append(String.format("Grade: %4d %4d %4d %4d %4d %4d - Final Grade: %4d | Final Rank: %d\n", classroom.scoreboard.grades[0][0], classroom.scoreboard.grades[0][1], classroom.scoreboard.grades[0][2], classroom.scoreboard.grades[0][3], classroom.scoreboard.grades[0][4], classroom.scoreboard.grades[0][5], classroom.scoreboard.final_grades[0], classroom.scoreboard.final_ranks[0]));
			
			sb.append("TODO: Press 1 to start new game.");
		}

		tb_myInfo.setText(sb.toString());
		tb_myInfo.setCaretPosition(tb_myInfo.getText().length() - 1);
	}
	
	void WriteCellInfo()
	{
		StringBuilder sb = new StringBuilder();
		int count_corpses = 0;
		int count_others = 0;
		
		if ( Point.IsValid(cursor_x, cursor_y) == true )
		{
			CellInfo cell = classroom.cells.data[cursor_y][cursor_x];
			
			sb.append(String.format("(%d, %d)\n", cursor_x, cursor_y));
			
			switch ( cell_drawSeeds[cursor_y][cursor_x] )
			{
			case 0x000:
				sb.append("Out of sight. You can\'t see anything here.\n");
				break;
			case 0x001:
				sb.append("Within sight. But you can\'t see anyone here.\n");
				break;
			case 0x011:
				sb.append(String.format("Within sight. %d survivors are here.\n", cell.playersInTheCell.size()));

				sb.append("\nPlayers:\n");
				
				for ( PlayerInfo player : cell.playersInTheCell )
					sb.append(String.format("%s | %s, Hit point: %d\n", classroom.players[player.ID].name, player.state, player.hitPoint));	
				break;
			case 0x021:
				sb.append(String.format("Within sight. %d infecteds are here.\n", cell.playersInTheCell.size()));

				sb.append("\nPlayers:\n");
				
				for ( PlayerInfo player : cell.playersInTheCell )
					sb.append(String.format("%s | %s, Hit point: %d\n", classroom.players[player.ID].name, player.state, player.hitPoint));	
				break;
			case 0x101:
				sb.append(String.format("Within sight. %d corpses are here.\n", cell.playersInTheCell.size()));

				sb.append("\nPlayers:\n");
				
				for ( PlayerInfo player : cell.playersInTheCell )
					sb.append(String.format("%s | %s, Hit point: %d\n", classroom.players[player.ID].name, player.state, player.hitPoint));	
				break;
			case 0x111:
				for ( PlayerInfo player : cell.playersInTheCell )
				{
					if ( player.state == State.Corpse )
						++count_corpses;
					else
						++count_others;
				}				
				
				sb.append(String.format("Within sight. %d survivors and %d corpses are here.\n", count_others, count_corpses));

				sb.append("\nPlayers:\n");
				
				for ( PlayerInfo player : cell.playersInTheCell )
					sb.append(String.format("%s | %s, Hit point: %d\n", classroom.players[player.ID].name, player.state, player.hitPoint));	
				break;
			case 0x121:
				for ( PlayerInfo player : cell.playersInTheCell )
				{
					if ( player.state == State.Corpse )
						++count_corpses;
					else
						++count_others;
				}				
				
				sb.append(String.format("Within sight. %d infecteds and %d corpses are here.\n", count_others, count_corpses));
				
				sb.append("\nPlayers:\n");
				
				for ( PlayerInfo player : cell.playersInTheCell )
					sb.append(String.format("%s | %s, Hit point: %d\n", classroom.players[player.ID].name, player.state, player.hitPoint));	

				break;
			case 0x002:
				sb.append("Out of sight. But you know no one is here.\n");
				break;
			case 0x012:
				sb.append(String.format("Out of sight. But you know %d survivors are here.\n", cell.playersInTheCell.size()));

				sb.append("\nPlayers:\n");
				
				for ( PlayerInfo player : cell.playersInTheCell )
					sb.append(String.format("%s | %s, Hit point: %d\n", classroom.players[player.ID].name, player.state, player.hitPoint));	
				break;
			case 0x022:
				sb.append(String.format("Out of sight. But you know %d infecteds are here.\n", cell.playersInTheCell.size()));

				sb.append("\nPlayers:\n");
				
				for ( PlayerInfo player : cell.playersInTheCell )
					sb.append(String.format("%s | %s, Hit point: %d\n", classroom.players[player.ID].name, player.state, player.hitPoint));	
				break;
			case 0x102:
				for ( PlayerInfo player : cell.playersInTheCell )
					if ( player.state == State.Corpse )
						++count_corpses;

				sb.append(String.format("Out of sight. But you know %d corpses are here.\n", count_corpses));

				sb.append("\nPlayers:\n");
				
				for ( PlayerInfo player : cell.playersInTheCell )
					if ( player.state == State.Corpse )
						sb.append(String.format("%s | %s, Hit point: %d\n", classroom.players[player.ID].name, player.state, player.hitPoint));	
				break;
			case 0x112:
				for ( PlayerInfo player : cell.playersInTheCell )
				{
					if ( player.state == State.Corpse )
						++count_corpses;
					else
						++count_others;
				}		
				
				sb.append(String.format("Out of sight. But You know %d survivors and %d corpses are here.\n", count_others, count_corpses));

				sb.append("\nPlayers:\n");
				
				for ( PlayerInfo player : cell.playersInTheCell )
					if ( player.state == State.Corpse )
						sb.append(String.format("%s | %s, Hit point: %d\n", classroom.players[player.ID].name, player.state, player.hitPoint));	
				break;
			case 0x122:
				for ( PlayerInfo player : cell.playersInTheCell )
				{
					if ( player.state == State.Corpse )
						++count_corpses;
					else
						++count_others;
				}		
				
				sb.append(String.format("Out of sight. But You know %d infecteds and %d corpses are here.\n", count_others, count_corpses));

				sb.append("\nPlayers:\n");
				
				for ( PlayerInfo player : cell.playersInTheCell )
					if ( player.state == State.Corpse )
						sb.append(String.format("%s | %s, Hit point: %d\n", classroom.players[player.ID].name, player.state, player.hitPoint));	
				break;
			}
			
			if ( ( cell_drawSeeds[cursor_y][cursor_x] & 0x001 ) == 0x001 && cell.actionsInTheCell.isEmpty() == false )
			{
				sb.append("\nActions:\n");
				
				for ( Action action : cell.actionsInTheCell )
				{
					if ( action.type == Action.TypeCode.Move )
						sb.append(String.format("%s | Move from %s to %s\n", classroom.players[action.actorID].name, action.location_from, action.location_to));
					else
						sb.append(String.format("%s | Spawn at %s\n", classroom.players[action.actorID].name, action.location_to));
				}
			}
			
			if ( ( cell_drawSeeds[cursor_y][cursor_x] & 0x001 ) == 0x001 && cell.reactionsInTheCell.isEmpty() == false )
			{
				int numberOfNonSpots = 0;
				
				StringBuilder sb_internal = new StringBuilder();
				
				sb_internal.append("\nReactions(except spots):\n");
				
				for ( Reaction reaction : cell.reactionsInTheCell )
				{
					if ( reaction.type != Reaction.TypeCode.Spot )
					{
						++numberOfNonSpots;
						if ( reaction.subjectID == reaction.objectID )
							sb_internal.append(String.format("%s | %s\n", classroom.players[reaction.subjectID].name, reaction.type));
						else
							sb_internal.append(String.format("%s -> %s | %s\n", classroom.players[reaction.subjectID].name, classroom.players[reaction.objectID].name, reaction.type));
					}
				}
				
				if ( numberOfNonSpots != 0 )
				{
					sb.append(sb_internal.toString());
				}
			}
		}	
		
		tb_cellInfo.setText(sb.toString());
		tb_cellInfo.setCaretPosition(0);
	}
	
	void WriteOthersInfo()
	{
		StringBuilder sb = new StringBuilder();
		Player myPlayer = classroom.players[0];
		
		if ( myPlayer.othersInfo_withinSight.isEmpty() == false )
		{
			sb.append("Players within sight:\n");
			
			for ( PlayerInfo player : myPlayer.othersInfo_withinSight )
				sb.append(String.format("%s | %s, Hit point: %d\n", classroom.players[player.ID].name, player.state, player.hitPoint));	

			sb.append('\n');
		}
		
		if ( myPlayer.othersInfo_detected.isEmpty() == false )
		{
			sb.append("Players detected:\n");
			
			for ( PlayerInfo player : myPlayer.othersInfo_detected )
				sb.append(String.format("%s | %s, Hit point: %d\n", classroom.players[player.ID].name, player.state, player.hitPoint));	

			sb.append('\n');
		}
		
		if ( myPlayer.actions.isEmpty() == false )
		{
			sb.append("Actions:\n");
			
			for ( Action action : myPlayer.actions )
			{
				if ( action.type == Action.TypeCode.Move )
					sb.append(String.format("%s | Move from %s to %s\n", classroom.players[action.actorID].name, action.location_from, action.location_to));
				else
					sb.append(String.format("%s | Spawn at %s\n", classroom.players[action.actorID].name, action.location_to));
			}
			
			sb.append('\n');
		}
		
		if ( myPlayer.reactions.isEmpty() == false )
		{
			int numberOfNonSpots = 0;
			
			StringBuilder sb_internal = new StringBuilder();
			
			sb_internal.append("Reactions(except spots):\n");
			
			for ( Reaction reaction : myPlayer.reactions )
			{
				if ( reaction.type != Reaction.TypeCode.Spot )
				{
					++numberOfNonSpots;
					if ( reaction.subjectID == reaction.objectID )
						sb_internal.append(String.format("%s | %s\n", classroom.players[reaction.subjectID].name, reaction.type));
					else
						sb_internal.append(String.format("%s -> %s | %s\n", classroom.players[reaction.subjectID].name, classroom.players[reaction.objectID].name, reaction.type));
				}
			}
			
			sb_internal.append('\n');

			if ( numberOfNonSpots != 0 )
			{
				sb.append(sb_internal.toString());
			}
		}
		
		tb_othersInfo.setText(sb.toString());
	}
}
