package controlador;

import clases.Archivo;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Alfred
 */

public class TableModelPersonalizada extends AbstractTableModel
{

    private final String[] columnNames =
    {
        "Tipo", "Nombre", "Fecha", "Tamaño"
    };
    private final List<Archivo> files;
    final String pathImagenes = "src/vista/imagenes/";

    public TableModelPersonalizada(List<Archivo> files)
    {
        this.files = files;
    }

    @Override
    public int getRowCount()
    {
        return files.size();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Archivo file = files.get(rowIndex);
        switch (columnIndex)
        {
            case 0:
                return (file.getTipo() == 'A') ? new ImageIcon(pathImagenes + "agregar-archivo1.png") : new ImageIcon(pathImagenes + "agregar-carpeta1.png");
            case 1:
                return file.getNombre() + ((file.getTipo() == 'A') ? file.getExtension() : "");
            case 2:
                return file.getFecha();
            case 3:
                return String.format("%,d KB   ", file.getTamanio());
            //return file.getTamanio() + " KB";
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        if (columnIndex == 0)
        {
            return ImageIcon.class;
        }
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int column)
    {
        return false;
    }

    public void actualizarTabla(List<Archivo> nuevosArchivos)
    {
        files.clear();
        files.addAll(nuevosArchivos);
        fireTableDataChanged();
    }

}
