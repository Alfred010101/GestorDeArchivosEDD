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

    private final String[] columnNames;
    private final List<Archivo> files;
    private final boolean esBusqueda;
    public TableModelPersonalizada(List<Archivo> files, boolean esBusqueda)
    {
        this.files = files;
        this.esBusqueda = esBusqueda;
        columnNames = new String[]
        {
            "Tipo",  "Nombre", (esBusqueda) ? "Ruta" : "Fecha de Modificación", (esBusqueda) ? "Fecha de Modificación" : "Tamaño"
        };
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
                return (file.getTipo() == 'A') ? new ImageIcon(Var.PATH_IMAGENES + "nuevo-documento1.png") : new ImageIcon(Var.PATH_IMAGENES + "carpeta1.png");
            case 1:
                return file.getNombre() + ((file.getTipo() == 'A') ? file.getExtension() : "");
            case 2:
                return (esBusqueda) ? "C:/" + file.getRuta() : file.getFecha();
            case 3:
                return (esBusqueda) ? file.getFecha() + "       "  : (file.getTipo() == 'A') ? String.format("%,d KB   ", file.getTamanio()) : "";
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
        if (!nuevosArchivos.isEmpty())
        {
            Var.contador.setText(nuevosArchivos.size() + " elementos econtrados        ");
        }else
        {
            Var.contador.setText("Directorio vacio        ");
        }
        fireTableDataChanged();
    }
}
