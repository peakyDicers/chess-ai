package peaky_chess;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

import pieces.Piece;

class component extends JComponent {
	private static final long serialVersionUID = 1L;
	Position[][] boardRep;
	int width = 450;
	Board board;

	public component(Board board) {
		this.boardRep = board.getBoardRep();
		this.board = board;
		int[] coords = new int[4];
		Component that = this;
		this.setSize(width, width);
		addMouseListener(new MouseAdapter() {
			boolean firstClick = true;

			public void mousePressed(MouseEvent e) {
				if (board.isGameOver())
					return;
				if (firstClick) {
					coords[0] = (int) e.getX() / (width / 8);
					coords[1] = (int) e.getY() / (width / 8);
					if (board.getPiece(coords[0], coords[1]) == null
							|| board.getPiece(coords[0], coords[1]).getTeam() != board.getPlayerTeam())
						return;
				} else {
					coords[2] = (int) e.getX() / (width / 8);
					coords[3] = (int) e.getY() / (width / 8);
					boolean moveSuccess = board.move(coords);
					that.repaint();
					if (moveSuccess) {
						board.computerMove();
						that.repaint();
					}

				}
				firstClick = !firstClick;
			}
		});
	}

	public void paintComponent(Graphics g) {
		int k;
		int width = 450;
		int height = 450;
		int rows = 8;
		int columns = 8;

		g.setColor(new Color(0, 0, 0));

		int htOfRow = height / (rows);
		for (k = 0; k < rows; k++)
			g.drawLine(0, k * htOfRow, width, k * htOfRow);

		int wdOfRow = width / (columns);
		for (k = 0; k < columns; k++)
			g.drawLine(k * wdOfRow, 0, k * wdOfRow, height);

		boolean lightSquare = true;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (lightSquare) {
					g.setColor(new Color(126, 146, 127));
				} else {
					g.setColor(new Color(92, 118, 92));
				}

				g.fillRect(wdOfRow * i, htOfRow * j, wdOfRow, htOfRow);
				lightSquare = !lightSquare;
			}
			lightSquare = !lightSquare;
		}

		for (int i = 0; i < boardRep.length; i++) {
			for (int j = 0; j < boardRep.length; j++) {
				Piece piece = boardRep[i][j].getPiece();
				Image image = null;
				if (piece != null) {
					image = getFile(piece);
				}
				g.drawImage(image, wdOfRow * j, htOfRow * i, wdOfRow, htOfRow, this);
			}
		}
	}

	public Image getFile(Piece piece) {
		File file = null;
		Image image = null;
		try {
			switch (piece.getName()) {
			case "wP":
				file = new File("imgs/wP.png");
				break;
			case "wR":
				file = new File("imgs/wR.png");
				break;
			case "wB":
				file = new File("imgs/wB.png");
				break;
			case "wN":
				file = new File("imgs/wN.png");
				break;
			case "wQ":
				file = new File("imgs/wQ.png");
				break;
			case "wK":
				file = new File("imgs/wK.png");
				break;

			// BLACK pieces
			case "bP":
				file = new File("imgs/bP.png");
				break;
			case "bR":
				file = new File("imgs/bR.png");
				break;
			case "bB":
				file = new File("imgs/bB.png");
				break;
			case "bN":
				file = new File("imgs/bN.png");
				break;
			case "bQ":
				file = new File("imgs/bQ.png");
				break;
			case "bK":
				file = new File("imgs/bK.png");
				break;
			}

			image = ImageIO.read(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return image;
	}
}

public class Display extends JFrame {
	private static final long serialVersionUID = 1L;

	Display(Board board) {
		this.setAlwaysOnTop(true);
		this.setSize(465, 490);
		this.setTitle("www.peakyDicers.com");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new component(board));
		Image image = null;
		File File;
		try {
			File = new File("imgs/bN.png");
			image = ImageIO.read(File);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		this.setIconImage(image);
	}
}
