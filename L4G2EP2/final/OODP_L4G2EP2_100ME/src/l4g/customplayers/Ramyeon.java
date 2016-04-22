package l4g.customplayers;

import l4g.common.*;

/**
 * 여러분이 새로운 플레이어를 만들기 위해 실제로 작성하게 될 클래스입니다.
 * 
 * @author Racin
 *
 */
public class Ramyeon extends Player {
	// TODO#1 Alt + Shift + R을 써서 클래스 이름을 마음에 드는 이름으로 바꾸어 주세요. 클래스 이름과 플레이어 이름은
	// 별개입니다.
	public Ramyeon(int ID) {

		// TODO#2 아래의 "이름!" 위치에 여러분이 만들 플레이어의 이름을 넣어 주세요. 클래스 이름과 플레이어 이름은
		// 별개입니다.
		super(ID, "계란치즈만두라면6분컷");

		// TODO#3 직접 감염을 받으려는 경우 이 필드를 true로, 그렇지 않은 경우 false로 설정하세요.
		// 이 필드의 값 자체는 아무 때나 바꿀 수 있지만 실질적으로 한 게임에 직접 감염이 여러 번 발동되는 경우는 매우 드무니 그냥
		// 고정시켜놔도 됩니다.
		this.trigger_acceptDirectInfection = false;

		// TODO#4 여기까지 왔으면 이제 Developer's Guide 문서와 각 BOT 플레이어 코드를 한 번만 더 읽어 보고
		// 돌아옵시다.

	}

	/*
	 * TODO#5 이제 여러분이 그려 둔 노트를 보며 아래에 있는 다섯 가지 의사 결정 메서드를 완성하세요. 당연히 한 방에 될 리
	 * 없으니, 중간중간 코드를 백업해 두는 것이 좋으며, 코드 작성이 어려울 땐 아무 부담 없이 조교를 찾아 오세요.
	 * 
	 * L4G는 여러분의 '생각'을 추구하는 축제지 구글 굴리는 축제가 아닙니다!
	 * 
	 * 여러분이 이번 축제에서 투자한 시간만큼, 이후 다른 과제 / 다른 업무에서 뻘짓을 벌이는 시간이 줄어들게 될 것입니다. 그러니
	 * 자신이 뭔가 멋진 생각을 떠올렸다면, 이를 내 플레이어에 적용하기 위해 아낌 없는 노력을 투자해 보세요!
	 * 
	 * 제출기한이 되어 황급히 파일을 업로드하고 Eclipse로 돌아와 여러분이 작성한 코드를 돌아 보면, '코드에 노력이란게 묻어 날
	 * 수도 있구나'라는 생각이 절로 들게 될 것입니다.
	 */

	static final int GoRight = 0;
	static final int GoDown = 1;
	static final int GoLeft = 2;
	static final int GoUp = 3;
	static final int Stay=4;

	int state=GoRight;

	/**
	 * 생존자일 때,상황에 따라 방향을 결정해주는 함수
	 */
	void Survive_UpdateState() {		
		int checkN=0,checkE=0,checkS=0,checkW=0,My_row=this.myInfo.position.row,My_col=this.myInfo.position.column,Height=Constants.Classroom_Height,Width=Constants.Classroom_Width;
		if(My_row>=1&&My_row<=Height-2&&My_col>=1&&My_col<=Width-2) {	//어느 방향으로 1칸을 이동해도 배열 크기에 영향 안끼치는 상황이면
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	//바로 위에 있으면
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	//바로 아래에 있으면
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	//바로 왼쪽에 있으면
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	//바로 오른쪽에 있으면
		}
		else if(My_row==0&&My_col>=1&&My_col<=Width-2) {		//맨 위쪽에 있어서 위쪽으론 이동 불가능 하고 다른쪽은 다 가능하면
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	//바로 아래에 있으면
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	//바로 왼쪽에 있으면
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	//바로 오른쪽에 있으면
		}
		else if(My_row==0&&My_col==0) {	//(0,0이면)
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	//바로 오른쪽에 있으면
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	//바로 아래에 있으면
		}
		else if(My_row==0&&My_col==Width-1)	{ //오른쪽 위 귀탱이에 있으면
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	//바로 왼쪽에 있으면
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	//바로 아래에 있으면
		}
		else if(My_row>=1&&My_row<=Height-2&&My_col==Width-1) {	//맨 오른쪽에 있어서 오른쪽으론 이동 불가능하고 다른쪽은 다 가능하면
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	//바로 위에 있으면
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	//바로 아래에 있으면
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	//바로 왼쪽에 있으면
		}
		else if(My_row==Height-1&&My_col>=1&&My_col<=Width-2) { //맨 아래쪽에 있어서 아래쪽으론 이동 불가능하고 다른쪽은 다 가능하면
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	//바로 위에 있으면
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	//바로 왼쪽에 있으면
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	//바로 오른쪽에 있으면
		}
		else if(My_row==Height-1&&My_col==0) {		//왼쪽 아래 귀퉁이에 있으면
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	//바로 오른쪽에 있으면
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	//바로 위에 있으면
		}
		else if(My_row==Height-1&&My_col==Height-1) {	//오른쪽 아래 귀탱이에 있으면
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	//바로 위에 있으면
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	//바로 왼쪽에 있으면
		}
		else if(My_row>=1&&My_row<=Height-2&&My_col==0) {	//맨 왼쪽에 있어서 왼쪽으론 이동 불가능 하고 다른쪽은 다 가능하면
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	//바로 위에 있으면
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	//바로 아래에 있으면
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	//바로 오른쪽에 있으면
		}
		//1칸씩 움직인 상태
		
		if(My_row>=2&&My_row<=Height-3&&My_col>=2&&My_col<=Width-3) {	//어느 방향으로 1칸을 이동해도 배열 크기에 영향 안끼치는 상황이면
			//북쪽 지역에 좀비가 있으면
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			//동쪽 지역에 좀비가 있으면
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			//남쪽 지역에 좀비가 있으면
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			//서쪽 지역에 좀비가 있으면
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
		}
		else if(My_row>=0&&My_row<=1&&My_col>=2&&My_col<=Width-3) {	//북쪽 지역 이동에 제한이 있으면
			//북쪽 지역에 좀비가 있으면
			if(My_row==1) {
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				//동쪽 지역에 좀비가 있으면
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				//남쪽 지역에 좀비가 있으면
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				//서쪽 지역에 좀비가 있으면
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			}
			else {	//My_row==0
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			}
		}
		else if(My_row>=0&&My_row<=1&&My_col>=0&&My_col<=1) { //왼쪽 귀퉁이 부분
			if(My_row==0) {
				if(My_col==0) {
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				}
				else {
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				}
			}
			else if(My_row==1) {
				if(My_col==0) {
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
				else {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
			}
		}
		else if(My_row>=0&&My_row<=1&&My_col>=Width-2&&My_col<=Width-1) {	//오른쪽 귀퉁이 부분
			if(My_row==0) {
				if(My_col==Width-2) {
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
				else {
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
			}
			else {
				if(My_col==Width-2) {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				}
				else {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;
					if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
			}
		}
		else if(My_row>=2&&My_row<=Height-3&&My_col>=Width-2&&My_col<=Width-1) {	//동쪽 지역 이동에 제한이 있으면
			if(My_col==Width-2) {
			//북쪽 지역에 좀비가 있으면
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			//동쪽 지역에 좀비가 있으면
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			//남쪽 지역에 좀비가 있으면
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			//서쪽 지역에 좀비가 있으면
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			}
			else {	//My_col==Width-1
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			}
		}
		
		else if(My_row>=Height-2&&My_row<=Height-1&&My_col>=2&&My_col<=Width-3) {	//남쪽 지역 이동에 제한이 있으면
			if(My_row==Height-2) {
				//북쪽 지역에 좀비가 있으면
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				//동쪽 지역에 좀비가 있으면
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				//남쪽 지역에 좀비가 있으면
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				//서쪽 지역에 좀비가 있으면
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			}
			else {
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			}
		}
		else if(My_row>=Height-2&&My_row<=Height-1&&My_col>=0&&My_col<=1) {		//왼쪽 아래 귀퉁이
			if(My_row==Height-1) {
				if(My_col==0) {
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				}
				else {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				}
			}
			else {	//My_row==Height-2
				if(My_col==0) {
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
				else {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
			}
		}
		
		else if(My_row>=Height-2&&My_row<=Height-1&&My_col>=Width-2&&My_col<=Width-1) {	//오른쪽 아래 귀퉁이
			if(My_row==Height-1) {
				if(My_col==Width-2) {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				}
				else {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
				}
			}
			else {
				if(My_col==Width-2) {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				}
				else {
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row][My_col-2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;
					if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				}
			}
		}
		else if(My_row>=2&&My_row<=Height-3&&My_col>=0&&My_col<=1) {	//서쪽 지역 이동에 제한이 있으면
			if(My_col==1) {
			//북쪽 지역에 좀비가 있으면
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
			//동쪽 지역에 좀비가 있으면
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			//남쪽 지역에 좀비가 있으면
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
			//서쪽 지역에 좀비가 있으면
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkW++;	
			}
			else {
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row-2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkN++;	
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row+2][My_col].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkS++;	
				if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
				if(cells[My_row][My_col+2].CountIf_Players(player->player.state==StateCode.Infected)>=1) checkE++;	
			}
		}
		//시야 안에서 좀비들 갯수 다 파악한 상태. 이제 이 중에서 상황을 봐서 가장 작은 값을 이동 방향으로 결정해야함
		int[] Zombies=new int[4];
		Zombies[0]=checkN;
		Zombies[1]=checkE;
		Zombies[2]=checkS;
		Zombies[3]=checkW;
		String[] Positions=new String[4];
		Positions[0]="N";
		Positions[1]="E";
		Positions[2]="S";
		Positions[3]="W";
		int tmpi;
		String tmps;
		for(int i=0;i<4;++i) {
			for(int j=i;j<4;++j) {
				if(Zombies[i]<Zombies[j]) {
					tmpi=Zombies[i];
					Zombies[i]=Zombies[j];
					Zombies[j]=tmpi;
					tmps=Positions[i];
					Positions[i]=Positions[j];
					Positions[j]=tmps;
				}
			}
		}
		//좀비들이 많은 곳 순서대로 앞부터 입력됨
		if(checkN==0&&checkE==0&&checkS==0&&checkW==0) {	//4방향에 아무도 좀비가 없으면.. 최대한 가운데쪽으로 이동시키도록 할꺼임
				if(My_row<Height/2&&My_col<Width/2) {		//2사분면 위치면
					if(My_row==0) state=GoDown;	//행이 0이면 아래로 내려가게
					else if(My_col==0) state=GoRight;	//열이 0이면 오른쪽으로 가도록
					else if(My_row>=My_col) {
						state=GoRight;
					}
					else {
						state=GoDown;
					}
				}
				else if(My_row<Height/2&&My_col>=Width/2) {	//1사분면 위치면
					if(My_row==0) state=GoDown;
					else if(My_col==Width-1) state=GoLeft;
					else if(My_row>=My_col-Width/2) {
						state=GoLeft;
					}
					else {
						state=GoDown;
					}
				}
				else if(My_row>=Height/2&&My_col<Width/2) {	//3사분면 위치면
					if(My_row==Height-1) state=GoUp;
					else if(My_col==0) state=GoRight;
					else if(Width/2-My_col>=My_row-Height/2) {
						state=GoRight;
					}
					else {
						state=GoUp;
					}
				}
				else {	//4사분면 위치면
					if(My_row==Height-1) state=GoUp;
					else if(My_col==Width-1) state=GoLeft;
					else if(My_row-Height/2>=My_col-Width/2) {
						state=GoUp;
					}
					else {
						state=GoLeft;
					}
				}
		}
		else {	//좀비가 시야에 나타나면
			if(My_row>=1&&My_row<=Height-2&&My_col>=1&&My_col<=Width-2)	{	//동서남북 어디든 이동 가능하면
				if(checkN!=0) {	//북쪽으로 좀비가 있으면
					if(checkE==0&&checkS==0&&checkW==0) {	//북쪽빼곤 다 갈수 있으면
						if(My_row<=Height/2) state=GoDown; //현재 행 높이가 절반보다 작으면 아래로 이동
						else {
							if(My_col<=Width/2) state=GoRight;	//현재 열이 절반보다 작으면 오른쪽 이동
							else state=GoLeft;	
						}
					}
					else if(checkE==0&&checkS==0) {	//동쪽 남쪽으로 갈수 있으면
						if(My_row>=My_col) state=GoRight;
						else state=GoDown;
					}
					else if(checkE==0&&checkW==0) {	//동쪽 서쪽으로 갈 수 있으면
						if(My_col<=Width/2) state=GoRight;
						else state=GoLeft;
					}
					else if(checkW==0&&checkS==0) {	//서쪽 남쪽으로 갈 수 있으면
						if(My_col>=My_row) state=GoLeft;
						else state=GoDown;
					}
					else if(checkE==0) state=GoRight;		//동쪽만 갈수 있으면
					else if(checkS==0) state=GoDown;	//남쪽만 갈 수 있으면
					else if(checkW==0) state=GoLeft;	//서쪽만 갈 수 있으면
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}
				}
				else if(checkE!=0) {	//동쪽으로 좀비가 있으면
					if(checkN==0&&checkS==0&&checkW==0) {	//동쪽빼곤 다 갈수 있으면
						if(My_col>=Height/2) state=GoLeft; //현재 열이 절반보다 크면 왼쪽으로 이동
						else {
							if(My_row<=Height/2) state=GoDown;	//현재 행이 절반보다 작으면 아래로 이동
							else state=GoUp;	
						}
					}
					else if(checkN==0&&checkS==0) {	//북쪽 남쪽으로 갈수 있으면
						if(My_row>=Height/2) state=GoUp;
						else state=GoDown;
					}
					else if(checkN==0&&checkW==0) {	//북쪽 서쪽으로 갈 수 있으면
						if(My_row>=My_col) state=GoUp;
						else state=GoLeft;
					}
					else if(checkW==0&&checkS==0) {	//서쪽 남쪽으로 갈 수 있으면
						if(My_col>=My_row) state=GoLeft;
						else state=GoDown;
					}
					else if(checkN==0) state=GoUp;		//북쪽만 갈수 있으면
					else if(checkS==0) state=GoDown;	//남쪽만 갈 수 있으면
					else if(checkW==0) state=GoLeft;	//서쪽만 갈 수 있으면
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}
				}
				else if(checkS!=0) {	//남쪽으로 좀비가 있으면
					if(checkE==0&&checkN==0&&checkW==0) {	//남쪽빼곤 다 갈수 있으면
						if(My_row>=Height/2) state=GoUp; //현재 행 높이가 절반보다 크면 위로 이동
						else {
							if(My_col<=Width/2) state=GoRight;	//현재 열이 절반보다 작으면 오른쪽 이동
							else state=GoLeft;	
						}
					}
					else if(checkE==0&&checkN==0) {	//동쪽 북쪽으로 갈수 있으면
						if(My_row>=My_col) state=GoUp;
						else state=GoRight;
					}
					else if(checkE==0&&checkW==0) {	//동쪽 서쪽으로 갈 수 있으면
						if(My_col<=Width/2) state=GoRight;
						else state=GoLeft;
					}
					else if(checkW==0&&checkN==0) {	//서쪽 북쪽으로 갈 수 있으면
						if(My_col>=My_row) state=GoLeft;
						else state=GoUp;
					}
					else if(checkE==0) state=GoRight;		//동쪽만 갈수 있으면
					else if(checkN==0) state=GoUp;	//북쪽만 갈 수 있으면
					else if(checkW==0) state=GoLeft;	//서쪽만 갈 수 있으면
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}
				}
				else if(checkW!=0) {	//서쪽으로 좀비가 있으면
					if(checkE==0&&checkS==0&&checkN==0) {	//서쪽빼곤 다 갈수 있으면
						if(My_col<=Width/2) state=GoRight; //현재 열이 절반보다 작으면 오른쪽으로 이동
						else {
							if(My_row<=Height/2) state=GoDown;	//현재 행이 절반보다 작으면 아래쪽 이동
							else state=GoUp;	
						}
					}
					else if(checkE==0&&checkS==0) {	//동쪽 남쪽으로 갈수 있으면
						if(My_row>=My_col) state=GoRight;
						else state=GoDown;
					}
					else if(checkE==0&&checkN==0) {	//동쪽 북쪽으로 갈 수 있으면
						if(My_col<=My_row) state=GoUp;
						else state=GoRight;
					}
					else if(checkN==0&&checkS==0) {	//북쪽 남쪽으로 갈 수 있으면
						if(My_row>=Height/2) state=GoUp;
						else state=GoDown;
					}
					else if(checkE==0) state=GoRight;		//동쪽만 갈수 있으면
					else if(checkS==0) state=GoDown;	//남쪽만 갈 수 있으면
					else if(checkN==0) state=GoUp;	//북쪽만 갈 수 있으면
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}
				}
			}
			else if(My_row==0&&My_col>=1&&My_col<=Width-2) {	//맨 꼭대기에 있으면
				if(checkE!=0) {	//동쪽에 좀비가 있으면
					if(checkW==0&&checkS==0) {		//서쪽 남쪽으로 갈 수 있으면
						if(My_col>=My_row) state=GoLeft;
						else state=GoDown;
					}
					else if(checkS==0) state=GoDown;	//남쪽만 갈 수 있으면
					else if(checkW==0) state=GoLeft;	//서쪽만 갈 수 있으면
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="E") state=GoRight;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}
				}
				else if(checkW!=0) {	//서쪽에 좀비가 있으면
					if(checkE==0&&checkS==0) {	//동쪽 남쪽으로 갈수 있으면
						if(My_row>=My_col) state=GoRight;
						else state=GoDown;
					}
					else if(checkE==0) state=GoRight;
					else if(checkS==0) state=GoDown;
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="E") state=GoRight;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}
				}
				else {	//남쪽에 좀비가 있으면
					 if(checkE==0&&checkW==0) {	//동쪽 서쪽으로 갈 수 있으면
						if(My_col<=Width/2) state=GoRight;
						else state=GoLeft;
					}
					 else if(checkE==0) state=GoRight;
					 else if(checkW==0) state=GoLeft;
					 else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="E") state=GoRight;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}
				}
			}
			else if(My_row==0&&My_col==0) {	//위의 왼쪽 귀퉁이
				if(checkE!=0) {	//동쪽으로 좀비가 있으면
					if(checkS==0) state=GoDown;
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="E") state=GoRight;
						else state=GoDown;
					}
				}
				else {	//남쪽으로 좀비가 있으면
					if(checkE==0) state=GoRight;
					else {
						if(Positions[0]=="E") state=GoRight;
						else state=GoDown;
					}
				}
			}
			else if(My_row==0&&My_col==Width-1) {//위 오른쪽 귀퉁이
				if(checkW!=0) {
					if(checkS==0) state=GoDown;
					else {
						if(Positions[0]=="W") state=GoLeft;
						else state=GoDown;
					}
				}
				else {
					if(checkW==0) state=GoLeft;
					else {
						if(Positions[0]=="W") state=GoLeft;
						else state=GoDown;
					}
				}
			}
			else if(My_row>=1&&My_row<=Height-2&&My_col==Width-1) {	//맨 오른쪽에 있으면
				if(checkN!=0) {	//북쪽에 좀비가 있으면
					if(checkW==0&&checkS==0) {		//서쪽 남쪽으로 갈 수 있으면
						if(My_col>=My_row) state=GoLeft;
						else state=GoDown;
					}
					else if(checkW==0) state=GoLeft;
					else if(checkS==0) state=GoDown;
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}		
				}
				else if(checkW!=0) {	//서쪽에 좀비가 있으면
					if(checkN==0&&checkS==0) {	//북쪽 남쪽으로 갈 수 있으면
						if(My_row>=Height/2) state=GoUp;
						else state=GoDown;
					}
					else if(checkN==0) state=GoUp;
					else if(checkS==0) state=GoDown;
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}		
				}
				else {	//남쪽에 좀비가 있으면
					if(checkW==0&&checkN==0) {	//서쪽 북쪽으로 갈 수 있으면
						if(My_col>=My_row) state=GoLeft;
						else state=GoUp;
					}
					else if(checkW==0) state=GoLeft;
					else if(checkN==0) state=GoUp;
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="S") state=GoDown;
						else state=GoLeft;
					}	
				}
			}
			else if(My_row==Height-1&&My_col>=1&&My_col<=Width-2) {	//맨 아래쪽에 있으면
				if(checkN!=0) {	//북쪽에 좀비가 있으면
					if(checkE==0&&checkW==0) {	//동쪽 서쪽으로 갈 수 있으면
						if(My_col<=Width/2) state=GoRight;
						else state=GoLeft;
					}
					else if(checkE==0) state=GoRight;
					else if(checkW==0) state=GoLeft;
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else state=GoLeft;
					}	
				}
				else if(checkW!=0) {	//서쪽에 좀비가 있으면
					if(checkE==0&&checkN==0) {	//동쪽 북쪽으로 갈 수 있으면
						if(My_col<=My_row) state=GoUp;
						else state=GoRight;
					}
					else if(checkE==0) state=GoRight;
					else if(checkN==0) state=GoUp;
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else state=GoLeft;
					}	
				}
				else {	//동쪽에 좀비가 있으면
					 if(checkW==0&&checkN==0) {	//서쪽 북쪽으로 갈 수 있으면
						if(My_col>=My_row) state=GoLeft;
						else state=GoUp;
					}
					 else if(checkN==0) state=GoUp;
					 else if(checkW==0) state=GoLeft;
					 else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else state=GoLeft;
					} 
				}
			}
			else if(My_row==Height-1&&My_col==0) {		//아래 왼쪽 귀퉁이
				if(checkN!=0) {	//북쪽에 좀비가 있으면
					if(checkE==0) state=GoRight;
					else {
						if(Positions[0]=="N") state=GoUp;
						else state=GoRight;
					}
				}
				else { //동쪽에 좀비가 있으면
					if(checkN==0) state=GoUp;
					else {
						if(Positions[0]=="N") state=GoUp;
						else state=GoRight;
					}
				}
			}
			else if(My_row==Height-1&&My_col==Width-1) {		//아래 오른쪽 귀퉁이
				if(checkN!=0) {	//북쪽에 좀비가 있으면
					if(checkW==0) state=GoLeft;
					else {
						if(Positions[0]=="N") state=GoUp;
						else state=GoLeft;
					}
				}
				else { 	//서쪽에 좀비가 있으면
					if(checkN==0) state=GoUp;
					else {
						if(Positions[0]=="N") state=GoUp;
						else state=GoLeft;
					}
				}
			}
			else if(My_row>=1&&My_row<=Height-2&&My_col==0) {	//맨 왼쪽에 있으면
				if(checkN!=0) {	//북쪽에 좀비가 있으면
					if(checkE==0&&checkS==0) {	//동쪽 남쪽으로 갈수 있으면
						if(My_row>=My_col) state=GoRight;
						else state=GoDown;
					}
					else if(checkE==0) state=GoRight;
					else if(checkS==0) state=GoDown;
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else state=GoDown;
					} 
				}
				else if(checkE!=0) {	//동쪽에 좀비가 있으면
					if(checkN==0&&checkS==0) {	//북쪽 남쪽으로 갈 수 있으면
						if(My_row>=Height/2) state=GoUp;
						else state=GoDown;
					}
					else if(checkN==0) state=GoUp;
					else if(checkS==0) state=GoDown;
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else state=GoDown;
					} 
				}
				else {	//남쪽에 좀비가 있으면
					if(checkE==0&&checkN==0) {	//동쪽 북쪽으로 갈 수 있으면
						if(My_col<=My_row) state=GoUp;
						else state=GoRight;
					}
					else if(checkE==0) state=GoRight;
					else if(checkN==0) state=GoUp;
					else {	//다 갈 수 없으면.. -> 좀비가 제일 많은 곳으로 갈거
						if(Positions[0]=="N") state=GoUp;
						else if(Positions[0]=="E") state=GoRight;
						else state=GoDown;
					}
				}
			}
		}
	}
	
	/**
	 * 감염 되었을 때 상황을 봐서 움직이는 방향 정할것
	 */
	void Infected_UpdateState() {
		int ChooseCheck=0;
		int checkN=0,checkE=0,checkS=0,checkW=0,My_row=this.myInfo.position.row,My_col=this.myInfo.position.column,Height=Constants.Classroom_Height,Width=Constants.Classroom_Width;
		if(turnInfo.turnNumber<=25) {	//턴이 25이하면 기본적으로 자살할거임
			state=Stay;	//그냥 25턴 이내에 감염된거면 자살
		}
		else {//턴이 25보다 크면 잡으러 다니기
		if(My_row>=2&&My_row<=Height-3&&My_col>=2&&My_col<=Width-3) {	//범위 보는데 아무런 문제도 안되는 지역
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
			
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
				if(My_row<=My_col)	checkE++;
				else checkN++;
			}
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
				if(My_row<=My_col) checkW++;
				else checkN++;
			}
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
				if(My_row<=My_col) checkS++;
				else checkW++;
			}
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
				if(My_row>=My_col) checkE++;
				else checkS++;
			}
			for(int j=My_col-2;j<=My_col+2;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
				if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
			}
			for(int j=My_row-2;j<=My_row+2;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
			}
			for(int j=My_col-2;j<=My_col+2;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
			}
			for(int j=My_row-2;j<=My_row+2;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
			}
		}
		else if(My_row==1&&My_col>=2&&My_col<=Width-3) {	//위쪽 부근 확인에 지장 있는 곳
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
			
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
				if(My_row<=My_col)	checkE++;
				else checkN++;
			}
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
				if(My_row<=My_col) checkW++;
				else checkN++;
			}
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
				if(My_row<=My_col) checkS++;
				else checkW++;
			}
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
				if(My_row>=My_col) checkE++;
				else checkS++;
			}
			for(int j=My_row-1;j<=My_row+2;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
			}
			for(int j=My_col-2;j<=My_col+2;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
			}
			for(int j=My_row-1;j<=My_row+2;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
			}
		}
		else if(My_row==0&&My_col>=2&&My_col<=Width-3) {	//위쪽 부근 확인에 지장 있는 곳
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
			
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
				if(My_row<=My_col) checkS++;
				else checkW++;
			}
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
				if(My_row>=My_col) checkE++;
				else checkS++;
			}
			for(int j=My_row;j<=My_row+2;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
			}
			for(int j=My_col-2;j<=My_col+2;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
			}
			for(int j=My_row;j<=My_row+2;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
			}
		}
		else if(My_row>=2&&My_row<=Height-3&&My_col==Width-2) {	//오른쪽이 이동 지장있는 곳 체크
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
				if(My_row<=My_col)	checkE++;
				else checkN++;
			}
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
				if(My_row<=My_col) checkW++;
				else checkN++;
			}
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
				if(My_row<=My_col) checkS++;
				else checkW++;
			}
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
				if(My_row>=My_col) checkE++;
				else checkS++;
			}
				
			for(int j=My_col-2;j<=My_col+1;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
				if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
			}
			for(int j=My_col-2;j<=My_col+1;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
			}
			for(int j=My_row-2;j<=My_row+2;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
			}
		}
		else if(My_row>=2&&My_row<=Height-3&&My_col==Width-1) {
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
				if(My_row<=My_col) checkW++;
				else checkN++;
			}
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
				if(My_row<=My_col) checkS++;
				else checkW++;
			}
			for(int j=My_col-2;j<=My_col;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
				if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
			}
			for(int j=My_col-2;j<=My_col;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
			}
			for(int j=My_row-2;j<=My_row+2;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
			}
		}
		else if(My_row==Height-2&&My_col>=2&&My_col<=Width-3) {	//아래쪽 체크에 지장있는 곳이면
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
			
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
				if(My_row<=My_col)	checkE++;
				else checkN++;
			}
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
				if(My_row<=My_col) checkW++;
				else checkN++;
			}
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
				if(My_row<=My_col) checkS++;
				else checkW++;
			}
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
				if(My_row>=My_col) checkE++;
				else checkS++;
			}
			
			for(int j=My_col-2;j<=My_col+2;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
				if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
			}
			for(int j=My_row-2;j<=My_row+1;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
			}
			for(int j=My_row-2;j<=My_row+1;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
			}
		}
		else if(My_row==Height-1&&My_col>=2&&My_col<=Width-3) {	//아래쪽 체크에 지장있는 곳이면
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
			
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
				if(My_row<=My_col)	checkE++;
				else checkN++;
			}
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
				if(My_row<=My_col) checkW++;
				else checkN++;
			}
			for(int j=My_col-2;j<=My_col+2;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
				if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
			}
			for(int j=My_row-2;j<=My_row;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
			}
			for(int j=My_row-2;j<=My_row;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
			}
		}
		else if(My_row>=2&&My_row<=Height-3&&My_col==1) {
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
			if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
				if(My_row<=My_col)	checkE++;
				else checkN++;
			}
			if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
				if(My_row<=My_col) checkW++;
				else checkN++;
			}
			if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
				if(My_row<=My_col) checkS++;
				else checkW++;
			}
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
				if(My_row>=My_col) checkE++;
				else checkS++;
			}
			
			for(int j=My_col-1;j<=My_col+2;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
				if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
			}
			for(int j=My_row-2;j<=My_row+2;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
			}
			for(int j=My_col-1;j<=My_col+2;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
			}
		}
		else if(My_row>=2&&My_row<=Height-3&&My_col==0) {
			if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
			if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
			if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
			
			if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
				if(My_row<=My_col)	checkE++;
				else checkN++;
			}
			if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
				if(My_row>=My_col) checkE++;
				else checkS++;
			}
			for(int j=My_col;j<=My_col+2;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
				if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
			}
			for(int j=My_row-2;j<=My_row+2;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
			}
			for(int j=My_col;j<=My_col+2;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
				if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
			}
		}
		else if(My_row>=0&&My_row<=1&&My_col>=0&&My_col<=1) {	//왼쪽 위 귀퉁이 부분
			if(My_row==0) {
				if(My_col==0) {	//(0,0) 부분
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col;j<=My_col+2;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row;j<=My_row+2;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}
				}
				else {	//(0,1)부분
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
					
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col-1;j<=My_col+2;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row;j<=My_row+2;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}
				}
			}
			else {
				if(My_col==0) {	//(1,0)인 부분
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col;j<=My_col+2;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row-1;j<=My_row+2;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}
				}
				else {	//(1,1)인 부분
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col-1;j<=My_col+2;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row-1;j<=My_row+2;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}					
				}
			}
		}
		else if(My_row>=0&&My_row<=1&&My_col>=Width-2&&My_col<=Width-1) {	//오른쪽 귀퉁이 부분
			if(My_row==0) {
				if(My_col==Width-2) {	//(0,Width-2)
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col-2;j<=My_col+1;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row;j<=My_row+2;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
				else {	//(0,Width-1)
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					for(int j=My_col-2;j<=My_col;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row;j<=My_row+2;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
			}
			else {	//My_row==1
				if(My_col==Width-2) {	//(1,Width-2)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col-2;j<=My_col+1;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row-1;j<=My_row+2;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
				else {	//(1,Width-1)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					for(int j=My_col-2;j<=My_col;++j) {	//2칸 아래쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[My_row+2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++;
					}
					for(int j=My_row-1;j<=My_row+2;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
			}
		}
		else if(My_row>=Height-2&&My_row<=Height-1&&My_col>=Width-2&&My_col<=Width-1) {	//오른쪽 아래 귀퉁이
			if(My_row==Height-2) {	
				if(My_col==Width-2) {	//(Height-2,Width-2)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col-2;j<=My_col+1;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row+1;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
				else {	//(Height-2,Width-1)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					for(int j=My_col-2;j<=My_col;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row+1;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
			}
			else {	//My_row==Height-1
				if(My_col==Width-2) {	//(Height-1,Width-2)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					for(int j=My_col-2;j<=My_col+1;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
				else {	//(Height-1,Width-1)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					for(int j=My_col-2;j<=My_col;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row;++j) {	//2칸 왼쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col-2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++;
					}
				}
			}
		}
		else if(My_row>=Height-2&&My_row<=Height-1&&My_col>=0&&My_col<=1) {		//왼쪽 아래 귀퉁이
			if(My_row==Height-2) {	
				if(My_col==0) {	//(Height-2,0)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					for(int j=My_col;j<=My_col+2;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row+1;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}
				}
				else {	//(Height-2,1)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
					if(cells[My_row+1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkS++; //바로 아래쪽에 생존자 있으면 체크
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					if(cells[My_row+1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//7시 방향에 생존자 있으면
						if(My_row<=My_col) checkS++;
						else checkW++;
					}
					if(cells[My_row+1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//5시 방향에 생존자 있으면
						if(My_row>=My_col) checkE++;
						else checkS++;
					}
					
					for(int j=My_col-1;j<=My_col+2;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row+1;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}
				}
			}
			else {	//My_row==Height-1
				if(My_col==0) {	//(Height-1,0)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					for(int j=My_col;j<=My_col+2;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}
				}
				else {	//(Height-1,1)
					if(cells[My_row-1][My_col].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++; //바로 위에 생존자 있으면 체크
					if(cells[My_row][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++; //바로 오른쪽에 생존자 있으면 체크
					if(cells[My_row][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkW++; //바로 왼쪽에 생존자 있으면 체크
					if(cells[My_row-1][My_col+1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {	//1시 방향에 생존자 있으면
						if(My_row<=My_col)	checkE++;
						else checkN++;
					}
					if(cells[My_row-1][My_col-1].CountIf_Players(player->player.state==StateCode.Survivor)>=1) {		//11시 방향에 생존자 있으면
						if(My_row<=My_col) checkW++;
						else checkN++;
					}
					for(int j=My_col-1;j<=My_col+2;++j) {	//2칸 위인 위쪽에서 생존자들 몇명있는지 체크
						if(cells[My_row-2][j].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkN++;
					}
					for(int j=My_row-2;j<=My_row;++j) {	//2칸 오른쪽인 곳에서 생존자들 몇명있는지 체크
						if(cells[j][My_col+2].CountIf_Players(player->player.state==StateCode.Survivor)>=1) checkE++;
					}
				}
			}
		}
		//Check들 상황에 맞추어 누적 다 시켜놨음.. 이제 이걸 토대로 이동 방향 결정하는 작업
		int[] Survivors=new int[4];
		Survivors[0]=checkN;
		Survivors[1]=checkE;
		Survivors[2]=checkS;
		Survivors[3]=checkW;
		String[] Positions=new String[4];
		Positions[0]="N";
		Positions[1]="E";
		Positions[2]="S";
		Positions[3]="W";
		int tmpi;
		String tmps;
		for(int i=0;i<4;++i) {
			for(int j=i;j<4;++j) {
				if(Survivors[i]<Survivors[j]) {
					tmpi=Survivors[i];
					Survivors[i]=Survivors[j];
					Survivors[j]=tmpi;
					tmps=Positions[i];
					Positions[i]=Positions[j];
					Positions[j]=tmps;
				}
			}
		}
		//가장 큰게 앞으로 오도록 설정해놨음
		if(Survivors[0]==0) {	//생존자가 주변에 아무도 없으면
			if(My_row<Height/2&&My_col<Width/2) {		//2사분면 위치면
				if(My_row<=My_col) state=GoRight;
				else state=GoDown;
			}
			else if(My_row<Height/2&&My_col>=Width/2) {	//1사분면 위치면
				if(My_row<=My_col-Width/2) {
					state=GoLeft;
				}
				else state=GoDown;
			}
			else if(My_row>=Height/2&&My_col<Width/2) {	//3사분면 위치면
				if(My_row-Height/2>=My_col) state=GoRight;
				else state=GoUp;
			}
			else {	//4사분면 위치면
				if(My_row<=My_col) state=GoLeft;
				else state=GoUp;
			}
		}
		else {
			switch(Positions[0]) {
			case "N":
				state=GoUp;
				break;
			case "E":
				state=GoRight;
				break;
			case "W":
				state=GoLeft;
				break;
			case "S":
				state=GoDown;
				break;
			}
		}
	}
	}


	@Override
	public DirectionCode Survivor_Move()
	{
		DirectionCode result = DirectionCode.Right;
		
		Survive_UpdateState();
		
		switch(state) {
		case GoRight:
			result=DirectionCode.Right;
			break;
		case GoDown:
			result=DirectionCode.Down;
			break;
		case GoLeft:
			result=DirectionCode.Left;
			break;
		case GoUp:
			result=DirectionCode.Up;
			break;
		}
		
		// Note: 이 메서드는 생존자 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.
		
		return result;
	}

	@Override
	public void Corpse_Stay()
	{
		// Note: 극한을 추구하는 것이 아니라면 이 메서드는 걍 비워 둬도 무방합니다.
	}

	@Override
	public DirectionCode Infected_Move()
	{
		DirectionCode result = DirectionCode.Right;
		
		Infected_UpdateState();
		
		switch(state) {
		case GoRight:
			result=DirectionCode.Right;
			break;
		case GoDown:
			result=DirectionCode.Down;
			break;
		case GoLeft:
			result=DirectionCode.Left;
			break;
		case GoUp:
			result=DirectionCode.Up;
			break;
		case Stay:
			result=DirectionCode.Stay;
		}
		

		//TODO 기도 드릴까 말까 결정해야함
		// Note: 이 메서드는 감염체 상태일 때 어디로 이동할지 결정하여 반환해야 하는 메서드입니다.

		return result;
	}

	@Override
	public void Soul_Stay()
	{
	}

	@Override
	public Point Soul_Spawn()
	{
		int Height=Constants.Classroom_Height,Width=Constants.Classroom_Width;
		int result_row = 0;
		int result_col = 0;
		int ZombiesCount=0;
		int BestNumber=0, WorstNumber=9;
		if(turnInfo.turnNumber==0) {	//첫턴이면 정 가운데에서 시작하도록 설정
			result_row=Height/2;
			result_col=Width/2;
		}
		else {	//도중에 부활이면
			for(int i=1;i<Height-1;++i) {	 //주변 검사해서 가장 많이 좀비 바글거리는 곳 찾을거임
				for(int j=1;j<Width-1;++j) {
					if(cells[i-1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i-1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i-1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i+1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i+1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i+1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(ZombiesCount>BestNumber) BestNumber=ZombiesCount;	//가장 많은 좀비 수 저장
					ZombiesCount=0;
				}
			}
			for(int i=1;i<Height-1;++i) {	 //주변 검사해서 가장 많이 좀비 바글거리는 곳 찾을거임
				for(int j=1;j<Width-1;++j) {
					if(cells[i-1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i-1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i-1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i+1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i+1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(cells[i+1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
					if(ZombiesCount<WorstNumber) WorstNumber=ZombiesCount;	//가장 적은 좀비 수 저장
					ZombiesCount=0;
				}
			}
			if(BestNumber>=5) {	//일부러 죽을 가치가 있다면
				for(int i=1;i<Height-1;++i) {
					for(int j=1;j<Width-1;++j) {
						if(cells[i-1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i-1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i-1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i+1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i+1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i+1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(ZombiesCount==BestNumber) {		//가장 주변에 좀비가 많은 곳이 발견되면 그곳에서 리스폰
							result_row=i;
							result_col=j;
							break;
						}
						ZombiesCount=0;
					}
				}
			}
			else {	//시체 점수 별로 벌지도 못할 것 같아서 걍 살아야겠다 싶으면
				for(int i=1;i<Height-1;++i) {
					for(int j=1;j<Width-1;++j) {
						if(cells[i-1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i-1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i-1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i+1][j-1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i+1][j].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(cells[i+1][j+1].CountIf_Players(player->player.state==StateCode.Infected)>=1)	ZombiesCount++;
						if(ZombiesCount==WorstNumber) {		//가장 주변에 좀비가 적은 곳이 발견되면 그곳에서 리스폰
							result_row=i;
							result_col=j;
							break;
						}
						ZombiesCount=0;
					}
				}
			}
		}
		state=GoRight;
		// Note: 이 메서드는 영혼 상태일 때 어디에 배치할지 결정하여 반환해야 하는 메서드입니다.

		return new Point(result_row, result_col);
	}
}
