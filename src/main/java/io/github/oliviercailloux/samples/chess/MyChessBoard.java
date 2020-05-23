package io.github.oliviercailloux.samples.chess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

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
		List<String> validIdentifier = new ArrayList<>();
		
		validIdentifier.add("P");
		validIdentifier.add("R");
		validIdentifier.add("B");
		validIdentifier.add("N");
		validIdentifier.add("Q");
		validIdentifier.add("K");

		if(inputBoard.size() != 64) {
			throw new IllegalArgumentException("inputBoard size not valid, must be equal 64");
		}

		HashMap<String, Optional<Piece>> newGameBoard = new HashMap<>();

		if(!inputBoard.contains("WK") || !inputBoard.contains("BK")) {
			throw new IllegalArgumentException();
		}

		int indexList = 0;

		for(int i = 1 ; i < 9 ; i ++) {

			for (String c : COLUMNS) {

				if(!inputBoard.get(indexList).contentEquals("")) {

					String color, indentifer;
					color = inputBoard.get(indexList).substring(0, 1);
					indentifer = inputBoard.get(indexList).substring(1, 2);
					
					if(color.equals("W") == false && color.equals("B") == false) {
						throw new IllegalArgumentException("color argument not valid");
					}

					if (!validIdentifier.contains(indentifer)) {
						throw new IllegalArgumentException("identifier argument not valid");
					}

					newGameBoard.put(c + Integer.toString(i) , Optional.ofNullable(Piece.given(color, indentifer)));

				} else {

					newGameBoard.put(c + Integer.toString(i) , Optional.empty());

				}

				indexList++;

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

		if(inputBoard.size() != 64) {
			throw new IllegalArgumentException("inputBoard size not valid, must be equal 64");
		}

		HashMap<String, Optional<Piece>> newGameBoard = new HashMap<>();

		if(!inputBoard.contains(Optional.of(Piece.king("W"))) || !inputBoard.contains(Optional.of(Piece.king("B")))) {
			throw new IllegalArgumentException();
		}

		int indexList = 0;

		for(int i = 1 ; i < 9 ; i ++) {

			for (String c : COLUMNS) {

				if(!inputBoard.get(i).isEmpty()) {

					newGameBoard.put(c + Integer.toString(i) , inputBoard.get(indexList));

				} else {

					newGameBoard.put(c + Integer.toString(i) , Optional.empty());

				}

				indexList++;
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

				if(!this.gameBoard.get(c + Integer.toString(i)).isEmpty()) {

					Optional<Piece> p = this.gameBoard.get(c + Integer.toString(i));

					returnList.put(c + Integer.toString(i), p.get().getColor() + p.get().getIdentifyingLetter());

				}

			}
		}

		return ImmutableMap.copyOf(returnList);
	}

	@Override
	public ImmutableMap<String, Piece> getPiecesByPosition() {
		HashMap<String, Piece> returnList = new HashMap<>();

		for(String c : COLUMNS) {
			for(int i = 1 ; i < 9; i++) {

				if(!this.gameBoard.get(c + Integer.toString(i)).isEmpty()) {

					returnList.put(c + Integer.toString(i), this.gameBoard.get(c + Integer.toString(i)).get());

				}
			}
		}

		return ImmutableMap.copyOf(returnList);
	}

	@Override
	public Optional<Piece> getPieceByPosition(String position) {

		List<String> validPosition = new ArrayList<>();

		for(String c : COLUMNS) {
			for(int i = 1 ; i < 9; i++) {
				validPosition.add(c + Integer.toString(i));
			}
		}

		if(!validPosition.contains(position)) {
			throw new IllegalArgumentException("position argument not valid");
		}

		Optional<Piece> piece = this.gameBoard.get(position);
		if (piece.isEmpty()) {
			return Optional.empty();
		}
		return piece;
	}

	@Override
	public ImmutableSet<Piece> getPieces(String color) {

		if (color.equals("W") == false && color.equals("B") == false) {
			throw new IllegalArgumentException("color argument not valid");
		}

		Set<Piece> returnSet = new HashSet<>();

		for(String c : COLUMNS) {
			for(int i = 1 ; i < 9; i++) {

				if(!this.gameBoard.get(c + Integer.toString(i)).isEmpty() && this.gameBoard.get(c + Integer.toString(i)).get().getColor().contentEquals(color)) {

					returnSet.add(this.gameBoard.get(c + Integer.toString(i)).get());

				}
			}
		}

		return ImmutableSet.copyOf(returnSet);
	}

	@Override
	public ImmutableList<Piece> getOrderedPieces(String color) {

		if (color.equals("W") == false && color.equals("B") == false) {
			throw new IllegalArgumentException("color argument not valid");
		}

		List<Piece> returnList = new ArrayList<>();

		for(String c : COLUMNS) {
			for(int i = 1 ; i < 9; i++) {

				if(!this.gameBoard.get(c + Integer.toString(i)).isEmpty() && this.gameBoard.get(c + Integer.toString(i)).get().getColor().contentEquals(color)) {

					returnList.add(this.gameBoard.get(c + Integer.toString(i)).get());

				}
			}
		}

		Collections.sort(returnList, Piece.getComparator());

		return ImmutableList.copyOf(returnList);
	}

	@Override
	public void movePiece(String oldPosition, String newPosition) {

		List<String> validPosition = new ArrayList<>();

		for(String c : COLUMNS) {
			for(int i = 1 ; i < 9; i++) {
				validPosition.add(c + Integer.toString(i));
			}
		}

		if(validPosition.contains(oldPosition) && validPosition.contains(newPosition)) {
			if(!this.gameBoard.get(oldPosition).isEmpty()) {

				this.gameBoard.put(newPosition, Optional.ofNullable(this.gameBoard.get(oldPosition).get()));
				this.gameBoard.put(oldPosition, Optional.empty());

			} else {
				throw new IllegalArgumentException("no piece found in the oldPosition");
			}

		} else {

			throw new IllegalArgumentException("oldPosition or newPosition not valid");

		}

	}

}
