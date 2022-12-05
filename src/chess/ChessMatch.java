package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

		private Board board;
		private int turn;
		private Color currentPlayer;
		private boolean checkMate;
		private ChessPiece promoted;
		public ChessMatch() {
			board = new Board(8,8);
			turn = 1;
			currentPlayer = Color.WHITE;
			initialSetup();
		}
		
		public int getTurn() {
			return turn;
		}
		public Color getCurrentPlayer() {
			return currentPlayer;
		}
		public ChessPiece[][] getPieces(){
			ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
			for(int i=0; i<board.getRows(); i++) {
				for(int j=0; j<board.getColumns(); j++) {
					mat[i][j] = (ChessPiece) board.piece(i, j);
				}
			}
			return mat;
		}
		
		private void placeNewPiece(char column, int row, ChessPiece piece) {
			board.placePiece(piece, new ChessPosition(column,row).toPosition());
		}
		private void initialSetup() {
			placeNewPiece('a',8,new Rook(board, Color.WHITE));
			placeNewPiece('b',8,new Knight(board, Color.WHITE));
			placeNewPiece('c',8,new Bishop(board, Color.WHITE));
			placeNewPiece('d',8,new Queen(board, Color.WHITE));
			placeNewPiece('e',8,new King(board, Color.WHITE));
			placeNewPiece('f',8,new Bishop(board, Color.WHITE));
			placeNewPiece('g',8,new Knight(board, Color.WHITE));
			placeNewPiece('h',8,new Rook(board, Color.WHITE));
//			for (int i=0; i<=7; i++) {
//				board.placePiece(new Pawn(board, Color.WHITE), new Position(1,i));
//			}
			placeNewPiece('a',1,new Rook(board, Color.BLACK));
			placeNewPiece('b',1,new Knight(board, Color.BLACK));
			placeNewPiece('c',1,new Bishop(board, Color.BLACK));
			placeNewPiece('d',1,new Queen(board, Color.BLACK));
			placeNewPiece('e',1,new King(board, Color.BLACK));
			placeNewPiece('f',1,new Bishop(board, Color.BLACK));
			placeNewPiece('g',1,new Knight(board, Color.BLACK));
			placeNewPiece('h',1,new Rook(board, Color.BLACK));
//			for (int i=0; i<=7; i++) {
//				board.placePiece(new Pawn(board, Color.BLACK), new Position(6,i));
//			}
			
		}
		
		public boolean[][] possibleMoves(ChessPosition sourcePosition){
			Position position = sourcePosition.toPosition();
			validateSourcePosition(position);
			return board.piece(position).possibleMoves();
			
		}
		public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
			Position source = sourcePosition.toPosition();
			Position target = targetPosition.toPosition();
			/*Convertendo padr�o de xadrez para matriz */
			validateSourcePosition(source); //Validamos a origem, visto que o destino � verificado no removePiece
			validateTargetPosition(source,target);
			Piece capturedPiece = makeMove(source, target);
			nextTurn();
			return (ChessPiece)capturedPiece;
		}
		
		private void validateTargetPosition(Position source, Position target) {
			if(!board.piece(source).possibleMove(target)) {
				throw new ChessException("A peca escolhida nao pode realizar esse movimento");
			}
			
		}

		private void validateSourcePosition(Position position) {
			if (!board.thereIsAPiece(position)) {
				throw new ChessException("Nao possui nenhuma pe�a na posicao de origem");
			}
			if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()) {
				throw new ChessException("Voce esta tentando mover uma peca do adversario");
			}
			if(!board.piece(position).isThereAnyPossibleMove()) {
				throw new ChessException("Nenhum movimento para a peca escolhida");
			}
		}
		private void nextTurn() {
			turn++;
			currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
		}
		
		private Piece makeMove(Position source, Position target) {
			Piece p = board.removePiece(source);
			Piece capturedPiece = board.removePiece(target);
			board.placePiece(p, target);
			return capturedPiece;
		}
				
		public ChessPiece replacePromotedPiece(String type) {
			return null;
		}
}
