package controlador;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 *
 * @author Alfred
 */

public class TablaPersonalizada extends JTable
{

    public TablaPersonalizada(TableModel model)
    {
        super(model);
        customizeTable();
    }

    private void customizeTable()
    {
        // Quitar las líneas de la cuadrícula
        setShowGrid(false);
        JTableHeader header = getTableHeader();
        header.setReorderingAllowed(false);

        // Permitir selección de fila única
        setRowSelectionAllowed(true);
        setColumnSelectionAllowed(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Establece altura de las filas
        setRowHeight(30);

        // Ajustar columnas
        getColumnModel().getColumn(0).setPreferredWidth(50); 
        getColumnModel().getColumn(1).setPreferredWidth(300); 
        getColumnModel().getColumn(2).setPreferredWidth(50); 
        getColumnModel().getColumn(3).setPreferredWidth(50); 
        getColumnModel().getColumn(0).setMinWidth(50);
        getColumnModel().getColumn(1).setMinWidth(300);
        getColumnModel().getColumn(2).setMinWidth(150);
        getColumnModel().getColumn(3).setMinWidth(110);
        TableCellRenderer noFocusBorderRenderer = new NoFocusBorderTableCellRenderer();
        setBackground(Color.WHITE);

        // Configurar el ***renderizador para cada columna
        setDefaultRenderer(Object.class, noFocusBorderRenderer);
    }
}

class NoFocusBorderTableCellRenderer extends DefaultTableCellRenderer
{

    private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);

    public NoFocusBorderTableCellRenderer()
    {
        setBorder(NO_FOCUS_BORDER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (isSelected)
        {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else
        {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        switch (column)
        {
            case 1:
                setHorizontalAlignment(SwingConstants.LEFT);
                break;
            case 3:
                setHorizontalAlignment(SwingConstants.RIGHT);
                break;
            default:
                setHorizontalAlignment(SwingConstants.CENTER);
        }

        setValue(value);
        return this;
    }
}
