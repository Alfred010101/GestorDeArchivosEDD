package vista;

import clases.Archivo;
import clases.Nodo;
import controlador.Ctrl;
import controlador.JTextFieldEdit;
import controlador.TablaPersonalizada;
import controlador.TableModelPersonalizada;
import controlador.Var;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import static vista.VentanaPrincipal.modelTabla;

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
        tablaResultados.getColumnModel().getColumn(1).setPreferredWidth(100);
        tablaResultados.getColumnModel().getColumn(2).setPreferredWidth(450);
        tablaResultados.getColumnModel().getColumn(3).setPreferredWidth(160);
        tablaResultados.getColumnModel().getColumn(1).setMinWidth(90);
        tablaResultados.getColumnModel().getColumn(2).setMinWidth(350);
        tablaResultados.getColumnModel().getColumn(3).setMinWidth(150);

        tablaResultados.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2)
                {
                    String nombreArchivo = tablaResultados.getValueAt(tablaResultados.getSelectedRow(), 1).toString();
                    boolean esDirectorio = !nombreArchivo.contains(".");
                    String directorioSeleccionado = tablaResultados.getValueAt(tablaResultados.getSelectedRow(), 2).toString();

                    directorioSeleccionado = directorioSeleccionado.substring(3);
                    String[] ruta;

                    if (directorioSeleccionado.isEmpty() && !esDirectorio)
                    {
                        Var.rutaActual = "";
                        VentanaPrincipal.ruta.setText(Var.rutaActual);
                        VentanaPrincipal.modelTabla.actualizarTabla(Ctrl.cargarDirectorio(Var.getMultilista().getRaiz()));
                    } else
                    {
                        ruta = Ctrl.splitPath(directorioSeleccionado + ((esDirectorio) ? nombreArchivo + "/" : ""));
                        Nodo seleccionado = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, ruta, ruta[ruta.length - 1]);
                        if (seleccionado != null)
                        {
                            Var.rutaActual = directorioSeleccionado + ((esDirectorio) ? nombreArchivo + "/" : "");
                            VentanaPrincipal.ruta.setText(Var.rutaActual);
                            VentanaPrincipal.modelTabla.actualizarTabla(Ctrl.cargarDirectorio(seleccionado.getAbajo()));
                        }
                    }
                    if (!esDirectorio)
                    {
                        for (int i = 0; i < VentanaPrincipal.tabla.getRowCount(); i++)
                        {
                            if (VentanaPrincipal.tabla.getValueAt(i, 1).equals(nombreArchivo))
                            {
                                VentanaPrincipal.tabla.setRowSelectionInterval(i, i);
                                VentanaPrincipal.tabla.scrollRectToVisible(VentanaPrincipal.tabla.getCellRect(i, 0, true));
                                break;
                            }
                        }
                    }                    
                    VentanaResultadosBusqueda.this.dispose();
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                tablaResultados.setHoveredRow(-1);
                repaint();
            }
        });

        tablaResultados.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                if (!tablaResultados.isPopupMenuActive())
                {

                    int row = tablaResultados.rowAtPoint(e.getPoint());
                    if (row != tablaResultados.getHoveredRow())
                    {
                        tablaResultados.setHoveredRow(row);
                        repaint();
                    }
                }
            }
        });

        JScrollPane scrollTabla = new JScrollPane(tablaResultados);
        principal.setLayout(new BorderLayout());
        principal.add(scrollTabla, BorderLayout.CENTER);
        this.add(principal);
    }

    /*
      Cerrar la ventana presionando ESC
     */
    private void addEscapeListener()
    {
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        Action escapeAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        };
        JRootPane rootPane = this.getRootPane();
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        rootPane.getActionMap().put("ESCAPE", escapeAction);
    }
}
