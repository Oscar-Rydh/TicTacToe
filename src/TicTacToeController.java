import java.util.ArrayList;

public class TicTacToeController {

	static String[][] game = new String[3][3];
	static int sdepth;

	public TicTacToeController() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				game[i][j] = "-";
			}
		}
	}

	// Switch between levels in MAX MIN
	public String inversLevel(String level) {
		return (level.equals("MAX")) ? "MIN" : "MAX";
	}

	// Checks if winner exists.
	// X = -1, O = 1, no winner = 0
	public int getScore(String[][] board) {
		if ((board[0][0].equalsIgnoreCase("x")
				&& board[0][1].equalsIgnoreCase("x") && board[0][2]
					.equalsIgnoreCase("x"))
				|| (board[1][0].equalsIgnoreCase("x")
						&& board[1][1].equalsIgnoreCase("x") && board[1][2]
							.equalsIgnoreCase("x"))
				|| (board[2][0].equalsIgnoreCase("x")
						&& board[2][1].equalsIgnoreCase("x") && board[2][2]
							.equalsIgnoreCase("x"))
				|| (board[0][0].equalsIgnoreCase("x")
						&& board[1][0].equalsIgnoreCase("x") && board[2][0]
							.equalsIgnoreCase("x"))
				|| (board[0][1].equalsIgnoreCase("x")
						&& board[1][1].equalsIgnoreCase("x") && board[2][1]
							.equalsIgnoreCase("x"))
				|| (board[0][2].equalsIgnoreCase("x")
						&& board[1][2].equalsIgnoreCase("x") && board[2][2]
							.equalsIgnoreCase("x"))
				|| (board[0][0].equalsIgnoreCase("x")
						&& board[1][1].equalsIgnoreCase("x") && board[2][2]
							.equalsIgnoreCase("x"))
				|| (board[0][2].equalsIgnoreCase("x")
						&& board[1][1].equalsIgnoreCase("x") && board[2][0]
							.equalsIgnoreCase("x")))
			return -1;

		if ((board[0][0].equalsIgnoreCase("o")
				&& board[0][1].equalsIgnoreCase("o") && board[0][2]
					.equalsIgnoreCase("o"))
				|| (board[1][0].equalsIgnoreCase("o")
						&& board[1][1].equalsIgnoreCase("o") && board[1][2]
							.equalsIgnoreCase("o"))
				|| (board[2][0].equalsIgnoreCase("o")
						&& board[2][1].equalsIgnoreCase("o") && board[2][2]
							.equalsIgnoreCase("o"))
				|| (board[0][0].equalsIgnoreCase("o")
						&& board[1][0].equalsIgnoreCase("o") && board[2][0]
							.equalsIgnoreCase("o"))
				|| (board[0][1].equalsIgnoreCase("o")
						&& board[1][1].equalsIgnoreCase("o") && board[2][1]
							.equalsIgnoreCase("o"))
				|| (board[0][2].equalsIgnoreCase("o")
						&& board[1][2].equalsIgnoreCase("o") && board[2][2]
							.equalsIgnoreCase("o"))
				|| (board[0][0].equalsIgnoreCase("o")
						&& board[1][1].equalsIgnoreCase("o") && board[2][2]
							.equalsIgnoreCase("o"))
				|| (board[0][2].equalsIgnoreCase("o")
						&& board[1][1].equalsIgnoreCase("o") && board[2][0]
							.equalsIgnoreCase("o")))
			return 1;

		return 0;
	}

	// Checks for a fullboard == draw.
	public boolean checkForDraw(String[][] tempGame) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (tempGame[i][j].equals("-"))
					return false;
			}
		}
		return true;
	}

	// Checks if a winner exists.
	public boolean checkForWinner(String[][] tempGame) {
		return getScore(tempGame) != 0 ? true : false;
	}

	// Generates all Available Children
	public ArrayList<String[][]> genarateChildren(String[][] tempGame,
			String level) {
		ArrayList<String[][]> allChildren = new ArrayList<String[][]>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				if (tempGame[i][j].equals("-")) {

					String[][] child = new String[3][3];
					for (int k = 0; k < 3; k++) {
						for (int l = 0; l < 3; l++) {
							child[k][l] = tempGame[k][l];
						}
					}

					if (level.equals("MAX"))
						child[i][j] = "o";
					else
						child[i][j] = "x";
					allChildren.add(child);
				}
			}
		}
		return allChildren.size() == 0 ? null : allChildren;
	}

	// Finds best result depending if MAX or MIN and returns the best
	// possible resulted board.
	public TicTacToeResult bestResultingBoardAtLevel(
			ArrayList<TicTacToeResult> possibleResult, String level) {
		TicTacToeResult bestResult = possibleResult.get(0);
		if (level.equals("MAX")) {
			for (int i = 1; i < possibleResult.size(); i++) {
				if ((possibleResult.get(i).getScore() > bestResult.getScore())
						|| (possibleResult.get(i).getScore() == bestResult
								.getScore() && possibleResult.get(i).getDepth() < bestResult
								.getDepth())) {

					bestResult = possibleResult.get(i);
				}
			}
		} else {
			for (int i = 1; i < possibleResult.size(); i++) {
				if ((possibleResult.get(i).getScore() < bestResult.getScore())
						|| (possibleResult.get(i).getScore() == bestResult
								.getScore() && possibleResult.get(i).getDepth() < bestResult
								.getDepth())) {
					bestResult = possibleResult.get(i);
				}
			}
		}
		return bestResult;
	}

	public TicTacToeResult minMax(String[][] tempGame, String level,
			int boardFilled, int depth) {
		ArrayList<String[][]> allChildren = genarateChildren(tempGame, level);

		// check if board is full or if winner exists
		if (allChildren == null || checkForWinner(tempGame)) {
			return new TicTacToeResult(tempGame, getScore(tempGame), depth);
		}

		// Check for all possible outcomes
		else {
			ArrayList<TicTacToeResult> allPossibleOutcomes = new ArrayList<TicTacToeResult>();
			for (int i = 0; i < allChildren.size(); i++) {
				allPossibleOutcomes.add(minMax(allChildren.get(i),
						inversLevel(level), 1, depth + 1));
			}
			// Find best result for each level
			TicTacToeResult bestBoard = bestResultingBoardAtLevel(
					allPossibleOutcomes, level);

			// If not first level, set the best board
			if (boardFilled == 1)
				bestBoard.setMatrix(tempGame);

			return bestBoard;

		}

	}

	public int putPiece(int x, int y) {
		game[x][y] = "x";
		checkForWinner(game);
		printBoard();
		if (checkForWinner(game))
			return -1;
		if (checkForDraw(game))
			return -2;
		Long preMinMax = System.currentTimeMillis();
		TicTacToeResult result = minMax(game, "MAX", 0, 0);
		int[] cordinates = result.getIndex(game);
		Long dif = System.currentTimeMillis() - preMinMax;
		System.out.println("Time difference = " + dif);
		game[cordinates[0]][cordinates[1]] = "o";
		if (checkForWinner(game))
			return 20;
		if (checkForDraw(game))
			return 30;

		return 0;
	}

	public void printBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				// if (i != 0 && i % 3 == 0) {
				// System.out.println("");
				// }
				System.out.print(game[i][j] + "   ");
			}
			System.out.println("\n");
		}
		System.out.println("\n");
	}

	public void printBoard(String[] game) {
		for (int i = 0; i < 9; i++) {
			if (i != 0 && i % 3 == 0) {
				System.out.println("");
			}
			System.out.print(game[i] + "   ");
		}
		System.out.println("\n");
	}

}
