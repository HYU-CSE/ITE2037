package l4g.customplayers;

import java.lang.reflect.Array;
import java.util.Arrays;

import javax.swing.text.Position;

import l4g.common.*;

/**
 * �������� ���ο� �÷��̾ ����� ���� ������ �ۼ��ϰ� �� Ŭ�����Դϴ�.
 * 
 * @author Racin
 *
 */
public class Player_2010003969 extends Player
{
	private static final int STATE_GO_RIGHT = 0 ;
	private static final int STATE_GO_DOWN = 1 ;
	private static final int STATE_GO_LEFT = 2 ;
	private static final int STATE_GO_UP = 3 ;
	private int mState = STATE_GO_RIGHT;
	private boolean mIsDiedOnce = false;
	
	// TODO#1 Alt + Shift + R�� �Ἥ Ŭ���� �̸��� ������ ��� �̸����� �ٲپ� �ּ���. Ŭ���� �̸��� �÷��̾� �̸��� �����Դϴ�.
	public Player_2010003969(int ID)
	{
		
		super(ID, "��Ű�ƹ븮");
		
		// TODO#3 ���� ������ �������� ��� �� �ʵ带 true��, �׷��� ���� ��� false�� �����ϼ���.
		// �� �ʵ��� �� ��ü�� �ƹ� ���� �ٲ� �� ������ ���������� �� ���ӿ� ���� ������ ���� �� �ߵ��Ǵ� ���� �ſ� �幫�� �׳� �������ѳ��� �˴ϴ�.
		this.trigger_acceptDirectInfection = false;
		
		
		// TODO#4 ������� ������ ���� Developer's Guide ������ �� BOT �÷��̾� �ڵ带 �� ���� �� �о� ���� ���ƿɽô�.
		
		
	}
	
	/*
	 * TODO#5	���� �������� �׷� �� ��Ʈ�� ���� �Ʒ��� �ִ� �ټ� ���� �ǻ� ���� �޼��带 �ϼ��ϼ���.
	 * 			�翬�� �� �濡 �� �� ������, �߰��߰� �ڵ带 ����� �δ� ���� ������,
	 * 			�ڵ� �ۼ��� ����� �� �ƹ� �δ� ���� ������ ã�� ������.
	 * 
	 * 			L4G�� �������� '����'�� �߱��ϴ� ������ ���� ������ ������ �ƴմϴ�!
	 * 
	 * 			�������� �̹� �������� ������ �ð���ŭ, ���� �ٸ� ���� / �ٸ� �������� ������ ���̴� �ð��� �پ��� �� ���Դϴ�.
	 * 			�׷��� �ڽ��� ���� ���� ������ ���÷ȴٸ�, �̸� �� �÷��̾ �����ϱ� ���� �Ƴ� ���� ����� ������ ������!
	 * 
	 * 			��������� �Ǿ� Ȳ���� ������ ���ε��ϰ� Eclipse�� ���ƿ� �������� �ۼ��� �ڵ带 ���� ����,
	 * 			'�ڵ忡 ����̶��� ���� �� ���� �ֱ���'��� ������ ���� ��� �� ���Դϴ�.
	 */
	
	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result = null;
		
		if (mIsDiedOnce) {
			result = GetHighestRiskDirection();
		}else if(this.turnInfo.turnNumber<11){
			result = GetLowestSurvivorDirection();
		}else
		result = GetLowestRiskDirection();
		
		return result;
	}

	private DirectionCode updateState(){
		switch (mState) {
		case STATE_GO_RIGHT:
			if (this.myInfo.position.column == Constants.Classroom_Width - 1) {
				return DirectionCode.Down;
			}
			break;
		case STATE_GO_DOWN:
			if (this.myInfo.position.row == Constants.Classroom_Height - 1) {
				return DirectionCode.Left;
			}
			break;
		case STATE_GO_LEFT:
			if (this.myInfo.position.column == 0) {
				return DirectionCode.Up;
			}
			break;
		case STATE_GO_UP:
			if (this.myInfo.position.row == 0) {
				return DirectionCode.Right;
			}
			break;
		}
		
		return DirectionCode.Right;
	}
	
	@Override
	public void Corpse_Stay()
	{
		// Note: ������ �߱��ϴ� ���� �ƴ϶�� �� �޼���� �� ��� �ֵ� �����մϴ�.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		mIsDiedOnce = true;
		DirectionCode result = null;
		
		// Note: �� �޼���� ����ü ������ �� ���� �̵����� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.
		result = GetHighestCorpseDirection();
		return result;
	}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			/*
			 * Note: ��ȥ ��� �޼���� L4G ������ ���۵Ǹ� ���� ���� ȣ��Ǵ� �޼����Դϴ�.
			 * 		 �� if���� ������ 0��°���� ����ǹǷ�, �� ���� �������� �߰��� ���� �������� �ʱ�ȭ�ϴ� �뵵�� ���⿡ �� �˸½��ϴ�. 
			 */
		}
	}

	@Override
	public Point Soul_Spawn()
	{
		int result_row = 0;
		int result_column = 0;
		float[][] infect_matrix = GetInfestedMatrix();
		float left_up = 0.0f;
		for (int i = 2; i < infect_matrix.length/2; i++) {
			for (int j = 2 ; j < infect_matrix.length/2; j++) {
				left_up+=infect_matrix[i][j];
			}
		}
		float left_down = 0.0f;
		for (int i = infect_matrix.length/2; i < infect_matrix.length-2; i++) {
			for (int j = 2 ; j < infect_matrix.length/2; j++) {
				left_down+=infect_matrix[i][j];
			}
		}
		float right_up = 0.0f;
		for (int i = 2; i < infect_matrix.length/2; i++) {
			for (int j =  infect_matrix.length/2 ; j < infect_matrix.length-2; j++) {
				right_up+=infect_matrix[i][j];
			}
		}
		float right_down = 0.0f;
		for (int i = infect_matrix.length/2; i < infect_matrix.length-2; i++) {
			for (int j = infect_matrix.length/2 ; j < infect_matrix.length-2; j++) {
				right_down+=infect_matrix[i][j];
			}
		}
		
		if (left_up>left_down && left_up>right_up && left_up > right_down) {
			return new Point( Constants.Classroom_Height/4, Constants.Classroom_Width/4);
		}else if (left_down>left_up && left_down>right_up && left_down > right_down) {
			return new Point( Constants.Classroom_Height/4*3, Constants.Classroom_Width/4);
		}else if (right_up>left_down && right_up>left_up && right_up > right_down) {
			return new Point( Constants.Classroom_Height/4, Constants.Classroom_Width/4*3);
		}else if (right_down>left_down && right_down>right_up && right_down > left_up) {
			return new Point( Constants.Classroom_Height/4*3, Constants.Classroom_Width/4*3);
		}
		// Note: �� �޼���� ��ȥ ������ �� ��� ��ġ���� �����Ͽ� ��ȯ�ؾ� �ϴ� �޼����Դϴ�.

		return new Point( Constants.Classroom_Height/2, Constants.Classroom_Width/2);
	}

	private DirectionCode GetHighestCorpseDirection(){
		DirectionCode result = null;
		float[][] survivor_matrix = GetCorpseMatrix();
		Point_Immutable my_position = this.myInfo.position;
		float[] direction_matrix = new float[4];
		int row = my_position.row+2;
		int col = my_position.column+2;
		float up = survivor_matrix[row-1][col] + survivor_matrix[row-2][col] +survivor_matrix[row-1][col-1] + survivor_matrix[row-1][col+1];
		float down = survivor_matrix[row-1][col] + survivor_matrix[row-2][col] +survivor_matrix[row-1][col-1] + survivor_matrix[row-1][col+1];
		float right =  survivor_matrix[row][col+1] + survivor_matrix[row][col+2]+survivor_matrix[row+1][col+1] + survivor_matrix[row-1][col+1];
		float left = survivor_matrix[row][col-1] + survivor_matrix[row][col-2]+survivor_matrix[row-1][col-1] + survivor_matrix[row+1][col-1];
		if (up <= 3 && down <=3 && right <=3 && left<=3) {
			return DirectionCode.Stay;
		}
		direction_matrix[STATE_GO_UP] = up;
		direction_matrix[STATE_GO_DOWN] = down;
		direction_matrix[STATE_GO_RIGHT] = right;
		direction_matrix[STATE_GO_LEFT] = left;
		Arrays.sort(direction_matrix);
		for (int i = direction_matrix.length-1; i >0; i--) {
			if (direction_matrix[i] == up) {
				if(IsOutBondary(STATE_GO_UP))
					continue;
				else
					return DirectionCode.Up;
			}else if (direction_matrix[i] == down) {
				if(IsOutBondary(STATE_GO_DOWN))
					continue;
				else
					return DirectionCode.Down;
			}else if (direction_matrix[i] == left) {
				if(IsOutBondary(STATE_GO_LEFT))
					continue;
				else
					return DirectionCode.Left;
			}else if (direction_matrix[i] == right) {
				if(IsOutBondary(STATE_GO_RIGHT))
					continue;
				else
					return DirectionCode.Right;
			}
		}
		return DirectionCode.Stay;
	}
	
	private DirectionCode GetLowestSurvivorDirection(){
		DirectionCode result = null;
		float[][] survivor_matrix = GetSurvivorMatrix();
		Point_Immutable my_position = this.myInfo.position;
		float[] direction_matrix = new float[4];
		int row = my_position.row+2;
		int col = my_position.column+2;
		float up = survivor_matrix[row-1][col] + survivor_matrix[row-2][col] +survivor_matrix[row-1][col-1] + survivor_matrix[row-1][col+1];
		float down = survivor_matrix[row-1][col] + survivor_matrix[row-2][col] +survivor_matrix[row-1][col-1] + survivor_matrix[row-1][col+1];
		float right =  survivor_matrix[row][col+1] + survivor_matrix[row][col+2]+survivor_matrix[row+1][col+1] + survivor_matrix[row-1][col+1];
		float left = survivor_matrix[row][col-1] + survivor_matrix[row][col-2]+survivor_matrix[row-1][col-1] + survivor_matrix[row+1][col-1];
		direction_matrix[STATE_GO_UP] = up;
		direction_matrix[STATE_GO_DOWN] = down;
		direction_matrix[STATE_GO_RIGHT] = right;
		direction_matrix[STATE_GO_LEFT] = left;
		Arrays.sort(direction_matrix);
		for (int i =0; i <direction_matrix.length; i++) {
			if (direction_matrix[i] == up) {
				if(IsOutBondary(STATE_GO_UP))
					continue;
				else
					return DirectionCode.Up;
			}else if (direction_matrix[i] == down) {
				if(IsOutBondary(STATE_GO_DOWN))
					continue;
				else
					return DirectionCode.Down;
			}else if (direction_matrix[i] == left) {
				if(IsOutBondary(STATE_GO_LEFT))
					continue;
				else
					return DirectionCode.Left;
			}else if (direction_matrix[i] == right) {
				if(IsOutBondary(STATE_GO_RIGHT))
					continue;
				else
					return DirectionCode.Right;
			}
		}
		result = updateState();
		return result;
	}
	
	private float[][] mOldCaluclatedMatrix;
	private DirectionCode GetHighestRiskDirection(){
		DirectionCode result = null;
		Point_Immutable my_position = this.myInfo.position;
		float[][] cur_calmatrix = GetProbabilityMatrix();
		float[] direction_matrix = new float[4];
		int row = my_position.row+2;
		int col = my_position.column+2;
		float up =cur_calmatrix[row-1][col] + cur_calmatrix[row-2][col] +cur_calmatrix[row-1][col-1] + cur_calmatrix[row-1][col+1];
		float down = cur_calmatrix[row-1][col] + cur_calmatrix[row-2][col] +cur_calmatrix[row-1][col-1] + cur_calmatrix[row-1][col+1];
		float right =  cur_calmatrix[row][col+1] + cur_calmatrix[row][col+2]+cur_calmatrix[row+1][col+1] + cur_calmatrix[row-1][col+1];
		float left = cur_calmatrix[row][col-1] + cur_calmatrix[row][col-2]+cur_calmatrix[row-1][col-1] + cur_calmatrix[row+1][col-1];
		direction_matrix[STATE_GO_UP] = up;
		direction_matrix[STATE_GO_DOWN] = down;
		direction_matrix[STATE_GO_RIGHT] = right;
		direction_matrix[STATE_GO_LEFT] = left;
		Arrays.sort(direction_matrix);
		for (int i = direction_matrix.length-1; i >0; i--) {
			if (direction_matrix[i] == up) {
				if(IsOutBondary(STATE_GO_UP))
					continue;
				else
					return DirectionCode.Up;
			}else if (direction_matrix[i] == down) {
				if(IsOutBondary(STATE_GO_DOWN))
					continue;
				else
					return DirectionCode.Down;
			}else if (direction_matrix[i] == left) {
				if(IsOutBondary(STATE_GO_LEFT))
					continue;
				else
					return DirectionCode.Left;
			}else if (direction_matrix[i] == right) {
				if(IsOutBondary(STATE_GO_RIGHT))
					continue;
				else
					return DirectionCode.Right;
			}
		}
		result = updateState();
		return result;
		
	}
	private DirectionCode GetLowestRiskDirection(){
		DirectionCode result = null;
		Point_Immutable my_position = this.myInfo.position;
		float[][] cur_calmatrix = GetProbabilityMatrix();
		float[] direction_matrix = new float[4];
		int row = my_position.row+2;
		int col = my_position.column+2;
		float up = row<0? 100:cur_calmatrix[row-1][col] + cur_calmatrix[row-2][col] +cur_calmatrix[row-1][col-1] + cur_calmatrix[row-1][col+1];
		float down =row>Constants.Classroom_Height-1? 100: cur_calmatrix[row-1][col] + cur_calmatrix[row-2][col] +cur_calmatrix[row-1][col-1] + cur_calmatrix[row-1][col+1];
		float right =  cur_calmatrix[row][col+1] + cur_calmatrix[row][col+2]+cur_calmatrix[row+1][col+1] + cur_calmatrix[row-1][col+1];
		float left = cur_calmatrix[row][col-1] + cur_calmatrix[row][col-2]+cur_calmatrix[row-1][col-1] + cur_calmatrix[row+1][col-1];
		direction_matrix[STATE_GO_UP] = up;
		direction_matrix[STATE_GO_DOWN] = down;
		direction_matrix[STATE_GO_RIGHT] = right;
		direction_matrix[STATE_GO_LEFT] = left;
		Arrays.sort(direction_matrix);
		for (int i = 0; i < direction_matrix.length; i++) {
			if (direction_matrix[i] == up) {
				if(IsOutBondary(STATE_GO_UP))
					continue;
				else
					return DirectionCode.Up;
			}else if (direction_matrix[i] == down) {
				if(IsOutBondary(STATE_GO_DOWN))
					continue;
				else
					return DirectionCode.Down;
			}else if (direction_matrix[i] == left) {
				if(IsOutBondary(STATE_GO_LEFT))
					continue;
				else
					return DirectionCode.Left;
			}else if (direction_matrix[i] == right) {
				if(IsOutBondary(STATE_GO_RIGHT))
					continue;
				else
					return DirectionCode.Right;
			}
		}
		result = updateState();
		return result;
		
		
	}
	
	private boolean IsOutBondary(int state){
		switch (state) {
		case STATE_GO_RIGHT:
			if (this.myInfo.position.column == Constants.Classroom_Width - 1) {
			   return true;
			}
		case STATE_GO_DOWN:
			if (this.myInfo.position.row == Constants.Classroom_Height - 1) {
				return true;
			}
		case STATE_GO_LEFT:
			if (this.myInfo.position.column == 0) {
				return true;
			}
		case STATE_GO_UP:
			if (this.myInfo.position.row == 0) {
				return true;
			}
		}
		return false;
	}
	private float[][] GetProbabilityMatrix(){
		if (mOldCaluclatedMatrix == null) {
			mOldCaluclatedMatrix =  new float[ Constants.Classroom_Height+4][Constants.Classroom_Width+4];
			for (int i = 0; i < mOldCaluclatedMatrix.length; i++) {
				for (int j = 0; j < mOldCaluclatedMatrix.length; j++) {
					mOldCaluclatedMatrix[i][j]=0.0f;
				}
			}
		}
		float[][] calculated_matrix = new float[ Constants.Classroom_Height+4][Constants.Classroom_Width+4];
		float[][] survivor_matrix = GetSurvivorMatrix();
		float[][] infected_matrix = GetInfestedMatrix();
		
		for (int i = 0; i < Constants.Classroom_Height ; i++) {
			for (int j = 0; j <Constants.Classroom_Width; j++) {
				if (infected_matrix[i][j]>0.0) {
					calculated_matrix[i][j] = 0.2f;
					calculated_matrix[i][j-1] = 0.2f;
					calculated_matrix[i][j+1] = 0.2f;
					calculated_matrix[i-1][j] = 0.2f;
					calculated_matrix[i+1][j] = 0.2f;
					if (infected_matrix[i][j]>50.0) {
						calculated_matrix[i][j] = 0.15f;
						calculated_matrix[i][j-1] = 0.325f;
						calculated_matrix[i][j+1]= 0.325f;
						calculated_matrix[i-1][j] = 0.325f;
						calculated_matrix[i+1][j] = 0.325f;
					}
				}else if (survivor_matrix[i][j]>0.0) {
					calculated_matrix[i][j] = calculated_matrix[i][j]-0.1f>0?calculated_matrix[i][j]-0.1f:0.05f;
					calculated_matrix[i][j-1] =  calculated_matrix[i][j-1]-0.1f>0?calculated_matrix[i][j-1]-0.1f:0.05f;
					calculated_matrix[i][j+1] = calculated_matrix[i][j+1]-0.1f>0?calculated_matrix[i][j+1]-0.1f:0.05f;
					calculated_matrix[i-1][j] = calculated_matrix[i-1][j]-0.1f>0?calculated_matrix[i-1][j]-0.1f:0.05f;
					calculated_matrix[i+1][j] =  calculated_matrix[i+1][j]-0.1f>0?calculated_matrix[i+1][j]-0.1f:0.05f;
				}else{
					if (mOldCaluclatedMatrix[i][j] > 0.1f) {
						calculated_matrix[i][j] = calculated_matrix[i][j]-0.1f>0?calculated_matrix[i][j]-0.1f:0.05f;
					}
					calculated_matrix[i][j] = 0.0f;
				}
				calculated_matrix[i][j]/= this.myInfo.position.GetDistance(i, j);
			}
		}
		mOldCaluclatedMatrix = calculated_matrix;
		return calculated_matrix;
	}
	
	private float[][] GetSurvivorMatrix(){
		float[][] survivor_matrix = new float[ Constants.Classroom_Height+4][Constants.Classroom_Width+4];
		for ( int row = 0; row < Constants.Classroom_Height; ++row )
			for ( int column = 0; column < Constants.Classroom_Width; ++column ){
				int cur_row = row;
				int cur_col = column;
				cells[row][column].ForEach_Players(player ->
				{
					if (player.state == StateCode.Survivor )
					{
						survivor_matrix[cur_row+2][cur_col+2] += 1.0f;
					}else{
						survivor_matrix[cur_row+2][cur_col+2] -= 0.0f;
					}
				});
			}
//		mOldSurvivorMatrix = survivor_matrix;
		return survivor_matrix;
	}
	private float[][] GetCorpseMatrix(){
		float[][] corpse_matrix = new float[ Constants.Classroom_Height+4][Constants.Classroom_Width+4];
		for ( int row = 0; row < Constants.Classroom_Height; ++row )
			for ( int column = 0; column < Constants.Classroom_Width; ++column ){
				int cur_row = row;
				int cur_col = column;
				cells[row][column].ForEach_Players(player ->
				{
					if (player.state == StateCode.Corpse )
					{
						corpse_matrix[cur_row+2][cur_col+2] += player.transition_cooldown;
					}else{
						corpse_matrix[cur_row+2][cur_col+2] += 0.0f;
					}
				});
			}
//		mOldInfestedMatrix = infest_matrix;
		return corpse_matrix;
	}
	private float[][] GetInfestedMatrix(){
		float[][] infest_matrix = new float[ Constants.Classroom_Height+4][Constants.Classroom_Width+4];
		for ( int row = 0; row < Constants.Classroom_Height; ++row )
			for ( int column = 0; column < Constants.Classroom_Width; ++column ){
				int cur_row = row;
				int cur_col = column;
				cells[row][column].ForEach_Players(player ->
				{
					if (player.state == StateCode.Infected )
					{
						infest_matrix[cur_row+2][cur_col+2] += player.HP;
					}else{
						infest_matrix[cur_row+2][cur_col+2] += 0.0f;
					}
				});
			}
//		mOldInfestedMatrix = infest_matrix;
		return infest_matrix;
	}

}
