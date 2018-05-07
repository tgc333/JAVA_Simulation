package Software;

import java.util.Random;


public class Foodlist {
	Food food;
	int foodnum;
	Foodlist(){
		Random r = new Random();
		foodnum = r.nextInt(10)+1;
		if(foodnum==1) {
			food = new rice();
		}else if(foodnum==2) {
			food = new lamen();
		}else if(foodnum==3) {
			food = new cheeselamen();
		}else if(foodnum==4) {
			food = new DDUCKlamen();
		}else if(foodnum==5) {
			food = new mandulamen();
		}else if(foodnum==6) {
			food = new don();
		}else if(foodnum==7) {
			food = new cheesedon();
		}else if(foodnum==8) {
			food = new gogumadon();
		}else if(foodnum==9) {
			food = new padon();
		}else if(foodnum==10) {
			food = new bigdon();
		}
	}
	class Food{
		Random r = new Random();
		int CookTime;
		int price;
		int cost;
		String name;
	}

	class rice extends Food{
		rice(){
			CookTime = r.nextInt(1)+1;
			price = 500;
			cost = 100;
			name = "��";
		}
	}

	class lamen extends Food{
		lamen(){
			CookTime = r.nextInt(4)+3;
			price = 1500;
			cost = 800;
			name = "���";
		}
	}
	class cheeselamen extends Food{
		cheeselamen(){
			CookTime = r.nextInt(4)+3;
			price = 2000;
			cost = 900;
			name = "ġ����";
		}
	}

	class DDUCKlamen extends Food{
		DDUCKlamen(){
			CookTime = r.nextInt(4)+3;
			price = 2000;
			cost = 900;
			name = "�����";
		}
	}
	
	class mandulamen extends Food{
		mandulamen(){
			CookTime = r.nextInt(4)+3;
			price = 2000;
			cost = 900;
			name = "���ζ��";
		}
	}

	class don extends Food{
		don(){
			CookTime = r.nextInt(5)+3;
			price = 2500;
			cost = 1300;
			name = "���";
		}
	}

	class cheesedon extends Food{
		cheesedon(){
			CookTime = r.nextInt(5)+3;
			price = 3500;
			cost = 1500;
			name = "ġ��";
		}
	}
	class gogumadon extends Food{
		gogumadon(){
			CookTime = r.nextInt(5)+3;
			price = 3500;
			cost = 1500;
			name = "�������";
		}
	}

	class padon extends Food{
		padon(){
			CookTime = r.nextInt(2)+1;
			price = 3500;
			cost = 1500;
			name = "�ĵ�ɵ��";
		}
	}

	class bigdon extends Food{
		bigdon(){
			CookTime = r.nextInt(5)+3;
			price = 3000;
			cost = 1500;
			name = "�յ��";
		}
	}
	
}