package Controller;

import Model.Player;
import Model.PlayerTableModel;
import View.PlayerTableView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerTableController {
	private PlayerTableView view;
	private PlayerTableModel model;
	private List<Player> players;
	private Connection connection;

	public PlayerTableController(PlayerTableView view) {
		this.view = view;
		model = new PlayerTableModel();
		view.getTable().setModel(model);
		players = new ArrayList<>();

		view.getListButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listPlayersFromDatabase();
			}
		});

		view.getAddButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addPlayerToDatabase();
			}
		});

		view.getDeleteButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deletePlayerFromDatabase();
			}
		});

		try {
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String url = "jdbc:sqlite:C:\\Users\\alumno tarde\\Desktop\\Examen.txt";
			connection = DriverManager.getConnection(url);

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(view.getTable(), "Error al conectar a la base de datos: " + e.getMessage());
		}
	}

	private void listPlayersFromDatabase() {
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM players");

			model.setRowCount(0);
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nombre = resultSet.getString("nombre");
				int dorsal = resultSet.getInt("dorsal");
				double altura = resultSet.getDouble("altura");

				Player player = new Player(id, nombre, dorsal, altura);
				players.add(player);
				model.addPlayer(player);
			}

			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(view.getTable(), "Error al listar los jugadores: " + e.getMessage());
		}
	}

	private void addPlayerToDatabase() {
		String nombre = JOptionPane.showInputDialog(view.getTable(), "Ingrese el nombre del jugador:");
		String dorsalString = JOptionPane.showInputDialog(view.getTable(), "Ingrese el dorsal del jugador:");
		String alturaString = JOptionPane.showInputDialog(view.getTable(), "Ingrese la altura del jugador:");

		int dorsal;
		double altura;

		try {
			dorsal = Integer.parseInt(dorsalString);
			altura = Double.parseDouble(alturaString);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(view.getTable(), "Error: Por favor ingrese un valor numérico válido.");
			return;
		}

		try {
			PreparedStatement statement = connection.prepareStatement("SELECT MAX(id) FROM players");
			ResultSet resultSet = statement.executeQuery();

			resultSet.next();

			int id = resultSet.getInt(1) + 1;

			Player player = new Player(id, nombre, dorsal, altura);

			resultSet.close();
			statement.close();

			statement = connection
					.prepareStatement("INSERT INTO PLAYERS (id, nombre, dorsal, altura) VALUES (?, ?, ?, ?)");
			statement.setInt(1, player.getId());
			statement.setString(2, player.getNombre());
			statement.setInt(3, player.getNumero());
			statement.setDouble(4, player.getAltura());
			statement.executeUpdate();

			players.add(player);
			model.addPlayer(player);

			statement.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(view.getTable(), "Error al agregar el jugador: " + e.getMessage());
		}
	}

	private void deletePlayerFromDatabase() {
		int selectedRow = view.getTable().getSelectedRow();
		if (selectedRow >= 0) {
			Player player = players.get(selectedRow);
			int playerId = player.getId();

			try {
				PreparedStatement statement = connection.prepareStatement("DELETE FROM players WHERE id = ?");
				statement.setInt(1, playerId);
				statement.executeUpdate();

				players.remove(selectedRow);
				model.deletePlayer(selectedRow);

				statement.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(view.getTable(), "Error al eliminar el jugador: " + e.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(view.getTable(), "Por favor seleccione un jugador para eliminar.");
		}
	}

	private void searchPlayers(String idString, String nombre, String dorsalString, String alturaString) {
		int id;
		int dorsal;
		double altura;

		try {
			id = Integer.parseInt(idString);
		} catch (NumberFormatException e) {
			id = -1; // Valor por defecto para no filtrar por ID
		}

		try {
			dorsal = Integer.parseInt(dorsalString);
		} catch (NumberFormatException e) {
			dorsal = -1; // Valor por defecto para no filtrar por dorsal
		}

		try {
			altura = Double.parseDouble(alturaString);
		} catch (NumberFormatException e) {
			altura = -1; // Valor por defecto para no filtrar por altura
		}

		model.setRowCount(0);
		for (Player player : players) {
			if ((id == -1 || player.getId() == id) && (nombre.isEmpty() || player.getNombre().equalsIgnoreCase(nombre))
					&& (dorsal == -1 || player.getNumero() == dorsal)
					&& (altura == -1 || player.getAltura() == altura)) {
				model.addPlayer(player);
			}
		}

	}

	public static void main(String[] args) {
		PlayerTableView view = new PlayerTableView();
		PlayerTableController controller = new PlayerTableController(view);

	}
}
