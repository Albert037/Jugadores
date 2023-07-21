package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PlayerTableView {
    private JFrame frame;
    private JPanel panel;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton listButton;
    private JButton addButton;
    private JButton deleteButton;
    private JButton searchButton;

    public PlayerTableView() {
        frame = new JFrame("Tabla de Jugadores");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(null);

        table = new JTable();
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 360, 104);

        listButton = new JButton("Listar");
        listButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        listButton.setBounds(20, 230, 80, 30);

        addButton = new JButton("Agregar");
        addButton.setBounds(120, 230, 80, 30);

        deleteButton = new JButton("Eliminar");
        deleteButton.setBounds(220, 230, 80, 30);

        searchButton = new JButton("Buscar");
        searchButton.setBounds(320, 230, 80, 30);

        panel.add(scrollPane);
        panel.add(listButton);
        panel.add(addButton);
        panel.add(deleteButton);
        panel.add(searchButton);

        frame.getContentPane().add(panel);
        frame.setSize(460, 350);
        frame.setVisible(true);
    }

    public JTable getTable() {
        return table;
    }

    public JButton getListButton() {
        return listButton;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JTextField getIdTextField() {
        return getAlturaTextField();
    }

    public JTextField getNombreTextField() {
        return getNombreTextField();
    }

    public JTextField getDorsalTextField() {
        return getDorsalTextField();
    }

    public JTextField getAlturaTextField() {
        return getAlturaTextField();
    }
}


    