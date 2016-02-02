package Backend;

public class Seat {

		private String playerName;
		private int seatNumber;
		private static int amountOfPlayers=0;
		private int chips;
		private int bettedAmount=0; //amount of bets in $ (in current round!); is 0 on roundstart
		
		public Seat(String playerName){
			if(amountOfPlayers<6 && playerName != null){
			setName(playerName);
			amountOfPlayers++;
			setSeatNumber(amountOfPlayers);
			}
			else{
				throw new RuntimeException("No more than 6 Players!");
			}
		}
		
		public void call(){
			
		}

		public void bet(){
			
		}

		public void fold(){
			
		}

		public String getName() {
			return playerName;
		}

		private void setName(String playerName) {
			if(playerName!=null){
				this.playerName = playerName;
			}
		}

		public int getSeatNumber() {
			return seatNumber;
		}

		//In case of moving players from seat to seat, we keep this method public for now
		public void setSeatNumber(int seatNumber) {
			this.seatNumber = seatNumber;
		}

		public int getChips() {
			return chips;
		}

		public void setChips(int chips) {
			this.chips = chips;
		}

}
