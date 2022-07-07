package Cab_Booking;

import java.util.Scanner;

interface Calculating_price {
	Scanner sc=new Scanner(System.in);

	int calc_price(final String cab_number, final String cus_price,final int kilometer, final int no_of_days2);
	
}

class calc_fixed_price implements Calculating_price{

	@Override
	public int calc_price(String cab_number, String cus_price,int kilometer, int no_of_days2){
		int total_cost = 0;
		
		total_cost=CabApplication.cab_details.get(cab_number).getFixed_price()*kilometer;

		return total_cost;
	}

	
}

class calc_singleday_price implements Calculating_price{
	

	@Override
	public int calc_price(String cab_number, String cus_price,int kilometer, int no_of_days2){
		int total_cost = 0;
		
		total_cost=no_of_days2*CabApplication.cab_details.get(cab_number).getSingle_day_price();

		return total_cost;
	}
}

class calc_variable_price implements Calculating_price{
	
	@Override
	public int calc_price(String cab_number, String cus_price,int kilometer, int no_of_days2){
		
		int total_cost = 0;
		
		int start_range=0;
		int end_range=0;
		int money=0;
		while(kilometer>0) {
			System.out.println("Start range : "+start_range);
			System.out.println("End range : ");			
			end_range=Integer.parseInt(sc.nextLine());
			if(end_range==100) {
				money=CabApplication.cab_details.get(cab_number).getRange_0_100();
			}
			else if(end_range==300) {
				money=CabApplication.cab_details.get(cab_number).getRange_100_300();
			}
			else if(end_range==600) {
				money=CabApplication.cab_details.get(cab_number).getRange_300_600();
			}
			else {
				money=CabApplication.cab_details.get(cab_number).getRange_600above();
			}		
			if(kilometer<=end_range-start_range) {		
				total_cost=total_cost+(kilometer*money);
				break;
			}
			else {
				total_cost=total_cost+(end_range-start_range)*money;
				kilometer=kilometer-(end_range-start_range);
			}
			start_range=end_range;
		}
		return total_cost;
	}

	
}