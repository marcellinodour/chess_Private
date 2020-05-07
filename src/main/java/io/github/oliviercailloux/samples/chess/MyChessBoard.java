package io.github.oliviercailloux.samples.chess;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class MyChessBoard implements ChessBoard {
	public static final ImmutableList<String> COLUMNS = ImmutableList.of("a", "b", "c", "d", "e", "f", "g", "h");
	private HashMap<String, Optional<Piece>> gameBoard;

	/**
	 * <p>
	 * This method, with the given declaration, <b>must</b> be present.
	 * </p>
	 * <p>
	 * Initially (for simplicity), a board has just two pieces: a White King on
	 * square e1 and a Black King on square e8.
	 * </p>
	 *
	 */
	public static MyChessBoard newInstance() {
		return new MyChessBoard();
	}

	private MyChessBoard() {

		this.gameBoard = new HashMap<>();

		for(String c : COLUMNS) {
			for(int i = 1 ; i < 9; i++) {
				this.gameBoard.put(c + Integer.toString(i) , Optional.empty());
			}
		}

		this.gameBoard.replace("e1", Optional.ofNullable(Piece.given("W", "K")));
		this.gameBoard.replace("e8", Optional.ofNullable(Piece.given("B", "K")));
	}

	@Override
	public boolean setBoardByString(List<String> inputBoard) {
		HashMap<String, Optional<Piece>> newGameBoard = new HashMap<>();

		if(!inputBoard.contains(Piece.given("W", "K").toString()) || !inputBoard.contains(Piece.given("B", "K").toString())) {
			throw new IllegalArgumentException();
		}

		int indexList = -1;

		for(int i = 1 ; i < 9 ; i ++) {

			indexList++;

			for (String c : COLUMNS) {

				indexList++;
				newGameBoard.put(c + Integer.toString(i) , Optional.ofNullable(Piece.given(String.valueOf(inputBoard.get(indexList).charAt(0)), String.valueOf(inputBoard.get(indexList).charAt(1)))));

			}
		}

		if(!newGameBoard.equals(this.gameBoard)) {
			this.gameBoard.putAll(newGameBoard);
			return true;
		}

		return false;

	}

	@Override
	public boolean setBoardByPieces(List<Optional<Piece>> inputBoard) {
		HashMap<String, Optional<Piece>> newGameBoard = new HashMap<>();

		if(!inputBoard.contains(Optional.of(Piece.king("W"))) || !inputBoard.contains(Optional.of(Piece.king("B")))) {
			throw new IllegalArgumentException();
		}

		int indexList = -1;

		for(int i = 1 ; i < 9 ; i ++) {

			indexList++;

			for (String c : COLUMNS) {

				indexList++;
				newGameBoard.put(c + Integer.toString(i) , inputBoard.get(indexList));

			}
		}

		if(!newGameBoard.equals(this.gameBoard)) {
			this.gameBoard.putAll(newGameBoard);
			return true;
		}

		return false;
	}

	@Override
	public ImmutableMap<String, String> getStringPiecesByPosition() {
		HashMap<String, String> returnList = new HashMap<>();

		for(String c : COLUMNS) {
			for(int i = 1 ; i < 9; i++) {

				if(!this.gameBoard.get(c + Integer.toString(i)).toString().equals("")) {

					returnList.put(c + Integer.toString(i), this.gameBoard.get(c + Integer.toString(i)).toString());

				}

			}
		}

		return ImmutableMap.copyOf(returnList);
	}

	@Override
	public ImmutableMap<String, Piece> getPiecesByPosition() {
		HashMap<String, Piece> returnList = new HashMap<>();
		Piece piece;

		for(String c : COLUMNS) {
			for(int i = 1 ; i < 9; i++) {

				if(!this.gameBoard.get(c + Integer.toString(i)).toString().equals("")) {
					piece = Piece.given(String.valueOf(this.gameBoard.get(c + Integer.toString(i).charAt(0))), String.valueOf(this.gameBoard.get(c + Integer.toString(i).charAt(1))));
					returnList.put(c + Integer.toString(i), piece);
				}
			}
		}

		return ImmutableMap.copyOf(returnList);
	}

	@Override
	public Optional<Piece> getPieceByPosition(String position) {
		Optional<Piece> piece = this.gameBoard.get(position);
		if (piece.isEmpty()) {
			return Optional.empty();
		}
		return piece;
	}

	@Override
	public ImmutableSet<Piece> getPieces(String color) {
		//TODO();
		return null;
	}

	@Override
	public ImmutableList<Piece> getOrderedPieces(String color) {
		//TODO();
		return null;
	}

	@Override
	public void movePiece(String oldPosition, String newPosition) {
		//TODO();

	}

}
