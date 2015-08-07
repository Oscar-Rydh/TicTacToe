import java.util.Scanner;


public class Main {

	public static void main(String[] args) {
		TicTacToeController game = new TicTacToeController();	
		Scanner scan = new Scanner(System.in);
		int result = 0;
		while(result == 0){
			result = game.putPiece(scan.nextInt(), scan.nextInt());
			game.printBoard();
			System.out.println(result);
		}
	
	}

}
