package controlador;

import java.awt.Color;
import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
    private int hoveredRow = -1;
    private boolean popupMenuActive = false; 
    
    public TablaPersonalizada(TableModel model)
    {
        super(model);
        customizeTable();
    }
    
        /**
     * @return the hoveredRow
     */
    public int getHoveredRow()
    {
        return hoveredRow;
    }

    /**
     * @param hoveredRow the hoveredRow to set
     */
    public void setHoveredRow(int hoveredRow)
    {
        this.hoveredRow = hoveredRow;
    }

    /**
     * @return the popupMenuActive
     */
    public boolean isPopupMenuActive()
    {
        return popupMenuActive;
    }

    /**
     * @param popupMenuActive the popupMenuActive to set
     */
    public void setPopupMenuActive(boolean popupMenuActive)
    {
        this.popupMenuActive = popupMenuActive;
    }

    private void customizeTable()
    {
        // Quitar las líneas de la cuadrícula
        setShowGrid(false);

        // Eliminar el espacio entre celdas
        setIntercellSpacing(new java.awt.Dimension(0, 0));

        JTableHeader header = getTableHeader();
        header.setReorderingAllowed(false);
        header.setBackground(Color.DARK_GRAY);
        header.setForeground(Color.WHITE);
        
        // Permitir selección de fila única
        setRowSelectionAllowed(true);
        setColumnSelectionAllowed(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Establece altura de las filas
        setRowHeight(30);

        // Ajustar columnas
//        getColumnModel().getColumn(0).setPreferredWidth(50); 
        getColumnModel().getColumn(1).setPreferredWidth(500);
        getColumnModel().getColumn(2).setPreferredWidth(30);
        getColumnModel().getColumn(3).setPreferredWidth(30);
        getColumnModel().getColumn(0).setMinWidth(50);
        getColumnModel().getColumn(0).setMaxWidth(50);
        getColumnModel().getColumn(1).setMinWidth(300);
        getColumnModel().getColumn(2).setMinWidth(150);
        getColumnModel().getColumn(3).setMinWidth(110);
       // setBackground(Color.GREEN);
        TableCellRenderer noFocusBorderRenderer = new NoFocusBorderTableCellRenderer(this);
        //setBackground(Color.WHITE);

        // Configurar el renderizado para cada columna
        setDefaultRenderer(Object.class, noFocusBorderRenderer);
        setDefaultRenderer(ImageIcon.class, noFocusBorderRenderer);
    }
}

class NoFocusBorderTableCellRenderer extends DefaultTableCellRenderer
{
    private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
    private static final Color COLOR_A = new Color(240, 240, 240); // Color claro
    private static final Color COLOR_B = new Color(255, 255, 255); // Color blanco
    private static final Color HOVER_COLOR = new Color(189, 215, 238); // Color al pasar el mouse
    private final TablaPersonalizada table;

    public NoFocusBorderTableCellRenderer(TablaPersonalizada table)
    {
//        setBorder(NO_FOCUS_BORDER);
        this.table = table;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (isSelected) {
            component.setBackground(table.getSelectionBackground());
            component.setForeground(table.getSelectionForeground());
        } else if (row == this.table.getHoveredRow()) {
            component.setBackground(HOVER_COLOR);
            component.setForeground(table.getForeground());
        } else {
            if (row % 2 == 0) {
                component.setBackground(COLOR_A);
            } else {
                component.setBackground(COLOR_B);
            }
            component.setForeground(table.getForeground());
        }

        if (hasFocus)
        {
            setBorder(NO_FOCUS_BORDER);
//            setBackground(Color.BLACK);
        }

        switch (column)
        {
            case 0:
                setHorizontalAlignment(SwingConstants.CENTER);
                break;
            case 1:
                setHorizontalAlignment(SwingConstants.LEFT);
                break;
            case 2:
                
                if(table.getColumnName(2).equals("Ruta"))
                {
                    setHorizontalAlignment(SwingConstants.LEFT);
                }else
                {
                    setHorizontalAlignment(SwingConstants.CENTER);
                }
                break;
            case 3:
                
                setHorizontalAlignment(SwingConstants.RIGHT);
                break;
        }

         if (value instanceof ImageIcon imageIcon) {
            JLabel label = new JLabel(imageIcon);
            label.setOpaque(true);
            if (isSelected) {
                label.setBackground(table.getSelectionBackground());
                label.setForeground(table.getSelectionForeground());
            } else if (row == this.table.getHoveredRow()) {
                label.setBackground(HOVER_COLOR);
                label.setForeground(table.getForeground());
            } else {
                if (row % 2 == 0) {
                    label.setBackground(COLOR_A);
                } else {
                    label.setBackground(COLOR_B);
                }
                label.setForeground(table.getForeground());
            }
            return label;
        }
         
        return component;
    }
}
//class NoFocusBorderTableCellRenderer extends DefaultTableCellRenderer
//{
//
//    private static final Border NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
//    private static final Color COLOR_1 = new Color(220, 220, 220); // Color claro
//    private static final Color COLOR_2 = new Color(255, 255, 255);
//
//    public NoFocusBorderTableCellRenderer()
//    {
//        setBorder(NO_FOCUS_BORDER);
//    }
//
//    @Override
//    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
//    {
//        if (isSelected)
//        {
//            setBackground(table.getSelectionBackground());
//            setForeground(table.getSelectionForeground());
//        } else
//        {
//            setBackground(table.getBackground());
//            setForeground(table.getForeground());
//        }
//
//        switch (column)
//        {
//            case 1:
//                setHorizontalAlignment(SwingConstants.LEFT);
//                break;
//            case 3:
//                setHorizontalAlignment(SwingConstants.RIGHT);
//                break;
//            default:
//                setHorizontalAlignment(SwingConstants.CENTER);
//        }
//
//        setValue(value);
//        return this;
//    }
//}
