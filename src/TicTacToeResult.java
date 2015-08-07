public class TicTacToeResult {
	private String[][] matrix;
	private int score;
	private int depth;

	public TicTacToeResult(String[][] matrix, int score, int depth) {
		this.matrix = matrix;
		this.score = score;
		this.depth = depth;
	}

	public void setMatrix(String[][] matrix) {
		this.matrix = matrix;
	}

	public int getScore() {
		return score;
	}

	public int getDepth() {
		return depth;
	}

	public int getIntrus() {
		for (int i = 0; i < 9; i++)
			if (matrix[i].equals("O"))
				return i;
		return -1;
	}

	public void printBoard() {
		for (int i = 0; i < 9; i++) {
			if (i != 0 && i % 3 == 0) {
				System.out.println("");
			}
			System.out.print(matrix[i] + "   ");
		}
		System.out.println("\n");
	}

	public int[] getIndex(String[][] game) {
		int[] toReturn = new int[2];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (matrix[i][j] == "o" && game[i][j] != "o") {
					toReturn[0] = i;
					toReturn[1] = j;
					return toReturn;
				}
			}
		}
		return toReturn;
	}

}
