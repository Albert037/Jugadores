
package Model;

import javax.swing.table.DefaultTableModel;

public class PlayerTableModel extends DefaultTableModel {
    public PlayerTableModel() {
        super(new Object[]{"ID", "Nombre", "Dorsal", "Altura"}, 0);
    }

    public void addPlayer(Player player) {
        addRow(new Object[]{player.getId(), player.getNombre(), player.getNumero(), player.getAltura()});
    }

    public void deletePlayer(int rowIndex) {
        removeRow(rowIndex);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}