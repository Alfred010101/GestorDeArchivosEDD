package vista;

import clases.Archivo;
import clases.Nodo;
import controlador.JTextFieldEdit;
import controlador.TablaPersonalizada;
import controlador.TableModelPersonalizada;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Alfred
 */
public class VentanaResultadosBusqueda extends JDialog
{
    private JPanel principal;
    private TablaPersonalizada tablaResultados;
    private TableModelPersonalizada modelTabla;
    private final List<Archivo> listaResultados;
    
    public VentanaResultadosBusqueda(JFrame frame, String titulo, List<Archivo> listaResultados)
    {
        super(frame, "Resultados de \" " + titulo + " \"", true);
        this.setSize(700, 480);
        this.setLocationRelativeTo(frame);
        this.setResizable(false);
        this.listaResultados = listaResultados;
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        initComponents();
        addEscapeListener();
    }

    private void initComponents()
    {
        principal = new JPanel();
        modelTabla = new TableModelPersonalizada(listaResultados, true);
        tablaResultados = new TablaPersonalizada(modelTabla);
        tablaResultados.getColumnModel().getColumn(1).setMinWidth(80);
        tablaResultados.getColumnModel().getColumn(2).setMinWidth(300);
        tablaResultados.getColumnModel().getColumn(3).setMinWidth(150);
        JScrollPane scrollTabla = new JScrollPane(tablaResultados);
        principal.setLayout(new BorderLayout());
        principal.add(scrollTabla, BorderLayout.CENTER);
        this.add(principal);
    }

    private void addEscapeListener()
    {
        
    }
}
