package l4g.customplayers;

import java.lang.reflect.Array;
import java.util.Arrays;

import javax.swing.text.Position;

import l4g.common.*;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
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
	
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은 별개입니다.
	public Player_2010003969(int ID)
	{
		
		super(ID, "마키아밸리");
		
		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = false;
		
		
		// TODO#4 여기까지 왔으면 이제 Developer's Guide 문서와 각 BOT 플레이어 코드를 한 번만 더 읽어 보고 돌아옵시다.
		
		
	}
	
	/*
	 * TODO#5	이제 여러분이 그려 둔 노트를 보며 아래에 있는 다섯 가지 의사 결정 메서드를 완성하세요.
	 * 			당연히 한 방에 될 리 없으니, 중간중간 코드를 백업해 두는 것이 좋으며,
	 * 			코드 작성이 어려울 땐 아무 부담 없이 조교를 찾아 오세요.
	 * 
	 * 			L4G는 여러분의 '생각'을 추구하는 축제지 구글 굴리는 축제가 아닙니다!
	 * 
	 * 			여러분이 이번 축제에서 투자한 시간만큼, 이후 다른 과제 / 다른 업무에서 뻘짓을 벌이는 시간이 줄어들게 될 것입니다.
	 * 			그러니 자신이 뭔가 멋진 생각을 떠올렸다면, 이를 내 플레이어에 적용하기 위해 아낌 없는 노력을 투자해 보세요!
	 * 
	 * 			제출기한이 되어 황급히 파일을 업로드하고 Eclipse로 돌아와 여러분이 작성한 코드를 돌아 보면,
	 * 			'코드에 노력이란게 묻어 날 수도 있구나'라는 생각이 절로 들게 될 것입니다.
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
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		mIsDiedOnce = true;
		DirectionCode result = null;
		
		// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
		result = GetHighestCorpseDirection();
		return result;
	}

	@Override
	public void Soul_Stay()
	{
		if ( turnInfo.turnNumber == 0 )
		{
			/*
			 * Note: 영혼 대기 메서드는 L4G 게임이 시작되면 가장 먼저 호출되는 메서드입니다.
			 * 		 이 if문의 내용은 0턴째에만 실행되므로, 이 곳은 여러분이 추가로 만든 변수들을 초기화하는 용도로 쓰기에 참 알맞습니다. 
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
		// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.

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
