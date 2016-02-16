package sudoku;

import java.util.Arrays;

public class solver {

	static private boolean solve(int row, int col, int[][] board) {
		if (row == 9)
			return true;

		if (board[row][col] != 0) {
			if (solve(col == 8 ? row + 1 : row, (col + 1) % 9, board))
				return true;
		} else {

			for (int a = 0; a < 9; a++) {
				board[row][col] = a + 1;

				if (testValid(row, col, board)) {
					if (solve(col == 8 ? row + 1 : row, (col + 1) % 9, board))
						return true;
					else
						board[row][col] = 0;
				}
			}
			board[row][col] = 0;
		}
		// if (!anyValid)
		// board[row][col] = 0;

		return false;
	}

	public static boolean testValid(int row, int col, int[][] board) {
		int value = board[row][col];

		for (int i = 0; i < 9; i++) {
			if (board[row][i] == value && i != col) {
				return false;
			}
		}
		
		for (int i = 0; i < 9; i++) {
			if (board[i][col] == value && i != row) {
				return false;
			}
		}

		int rowSquare = row / 3,
			colSquare = col / 3;

		for (int i = rowSquare * 3; i < (rowSquare * 3 + 3); i++) {
			for (int j = colSquare * 3; j < (colSquare * 3 + 3); j++) {
				if (board[i][j] == value && (i != row || j != col)) {
					return false;
				}
			}
		}
		return true;
	}

	static boolean testValidWhole(int currentStep, int[][] board) {
		// hor

		for (int y = 0; y < 9; y++) {
			boolean[] set = new boolean[10];

			for (int x = 0; x < 9; x++) {
				int val = board[x][y] % (y * 9 + x <= currentStep ? 10 : Integer.MAX_VALUE);
				if (set[val] && val != 0)
					return false;
				else
					set[board[x][y] % 10] = true;
			}
		}

		// ver

		for (int x = 0; x < 9; x++) {
			boolean[] set = new boolean[10];

			for (int y = 0; y < 9; y++) {
				int val = board[x][y] % (y * 9 + x <= currentStep ? 10 : Integer.MAX_VALUE);
				if (set[val] && val != 0)
					return false;
				else
					set[board[x][y] % 10] = true;
			}
		}

		// kva

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				boolean[] set = new boolean[10];

				for (int ys = 0; ys < 3; ys++) {
					for (int xs = 0; xs < 3; xs++) {
						int val = board[x * 3 + xs][y * 3 + ys]
								% (y * 27 + ys * 9 + x * 3 + xs <= currentStep ? 10 : Integer.MAX_VALUE);
						if (set[val] && val != 0)
							return false;
						else
							set[board[x * 3 + xs][y * 3 + ys]] = true;
					}
				}
			}
		}
		return true;

	}
}
