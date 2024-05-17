
package vista;

import clases.Archivo;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 *
 * @author Alfred
 */
public class VentanaPropiedades extends JDialog
{

    private JPanel principal;
    private JPanel panel;
    private GridBagConstraints gbConstraints;
    private JLabel icono;
    private JTextField nombre;
    private JTextField peso;
    JTextField ruta;
    JTextField autor;
    final String pathImagenes = "src/vista/imagenes/";
    
    Archivo archivo;

    public VentanaPropiedades(JFrame frame, String titulo, Archivo archivo)
    {
        super(frame, "Propiedades de " + titulo, true);
        this.archivo = archivo;
        this.setSize(450, 320);
        this.setLocationRelativeTo(frame);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        initComponents();
        addEscapeListener();
    }
    
    private void initComponents()
    {
        
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
