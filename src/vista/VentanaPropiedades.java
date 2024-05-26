package vista;

import clases.Archivo;
import clases.Nodo;
import controlador.Ctrl;
import controlador.JTextFieldEdit;
import controlador.ManipulacionArchivos;
import controlador.Var;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
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
    private JTextFieldEdit nombre;
    private JTextFieldEdit peso;
    private JTextFieldEdit ruta;
    private JTextFieldEdit autor;
    private JTextFieldEdit fecha;
    private JButton cambiar;
    private String etq;

    private final Archivo archivo;
    private final boolean modificar;

    public VentanaPropiedades(JFrame frame, String etq, Archivo archivo, boolean modificar)
    {
        super(frame, "Propiedades de " + etq, true);
        this.archivo = archivo;
        this.modificar = modificar;
        this.etq = etq;
        this.setSize(380, 330);
        this.setLocationRelativeTo(frame);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        initComponents();
        addEscapeListener();
    }

    private void initComponents()
    {
        principal = new JPanel();
        principal.setLayout(new BoxLayout(principal, BoxLayout.Y_AXIS));

        panel = new JPanel();
        JPanel panelIcono = new JPanel();
        JPanel panelForm = new JPanel();
        JPanel panelBoton = new JPanel();

        panelIcono.setBackground(new Color(240, 240, 240));
        panelForm.setBackground(new Color(240, 240, 240));
        panelBoton.setBackground(new Color(240, 240, 240));

        panel.setBackground(new Color(240, 240, 240));
        panel.setLayout(new GridBagLayout());

        nombre = new JTextFieldEdit(15, "", false);
        nombre.setText(archivo.getNombre() + ((archivo.getExtension() == null) ? "" : archivo.getExtension()));

        if (archivo.getTipo() == 'C')
        {
            String nom = nombre.getText();
            String[] arr = Ctrl.splitPath(archivo.getRuta() + nom);
            Nodo nodo = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, arr, nom);
            int size = Var.getMultilista().calcularTamanio(nodo, 0);
            peso = new JTextFieldEdit(15, ((size < 0) ? "El tamaño es mayor a (int)" : String.valueOf(size)), false);
        } else
        {
            peso = new JTextFieldEdit(15, "", false);
            peso.setText(String.valueOf(archivo.getTamanio()));
        }

        ruta = new JTextFieldEdit(15, "C:/" + archivo.getRuta(), true);
        autor = new JTextFieldEdit(15, archivo.getAutor(), true);
        fecha = new JTextFieldEdit(15, archivo.getFecha(), true);
        

        if (modificar)
        {
            if (archivo.getTipo() == 'C')
            {
                peso.setEnabled(false);
                peso.setDisabledTextColor(Color.BLACK);
            }
        } else
        {
            nombre.setEnabled(false);
            nombre.setDisabledTextColor(Color.BLACK);
            peso.setEnabled(false);
            peso.setDisabledTextColor(Color.BLACK);
            panelBoton.setVisible(false);
        }

        ruta.setEnabled(false);
        ruta.setDisabledTextColor(Color.BLACK);
        autor.setEnabled(false);
        autor.setDisabledTextColor(Color.BLACK);
        fecha.setEnabled(false);
        fecha.setDisabledTextColor(Color.BLACK);
        
        icono = new JLabel(new ImageIcon(Var.PATH_IMAGENES + ((archivo.getTipo() == 'A') ? "nuevo-documento2.png" : "carpeta-vacia2.png")));
        panelIcono.add(icono);

        gbConstraints = new GridBagConstraints();
        this.addComponent(new JLabel("Nombre : "), 1, 0, 1, 1, GridBagConstraints.EAST);
        this.addComponent(new JLabel("Tamaño (KB): "), 2, 0, 1, 1, GridBagConstraints.EAST);
        this.addComponent(new JLabel("Ruta : "), 3, 0, 1, 1, GridBagConstraints.EAST);
        this.addComponent(new JLabel("Ultima Modificación : "), 4, 0, 1, 1, GridBagConstraints.EAST);
        this.addComponent(new JLabel("Autor : "), 5, 0, 1, 1, GridBagConstraints.EAST);

        this.addComponent(nombre, 1, 1, 1, 1, GridBagConstraints.WEST);
        this.addComponent(peso, 2, 1, 1, 1, GridBagConstraints.WEST);
        this.addComponent(ruta, 3, 1, 1, 1, GridBagConstraints.WEST);
        this.addComponent(fecha, 4, 1, 1, 1, GridBagConstraints.WEST);
        this.addComponent(autor, 5, 1, 1, 1, GridBagConstraints.WEST);

        cambiar = new JButton("Guardar Cambios");

        cambiar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                actualizarArchivo();
            }
        });

        panelBoton.add(cambiar);
        panelForm.add(panel);
        principal.add(panelIcono);
        principal.add(panelForm);
        principal.add(panelBoton);
        this.add(principal);
    }

    private void addComponent(Component c, int row, int column, int width, int height, int pos)
    {
        gbConstraints.gridx = column;
        gbConstraints.gridy = row;
        gbConstraints.gridwidth = width;
        gbConstraints.gridheight = height;
        gbConstraints.insets = new Insets(5, 5, 5, 5);
        gbConstraints.anchor = pos;
        panel.add(c, gbConstraints);
    }

    /**
     * Valida que los campos no esten vacios Tambien verifica que los datos sean
     * correctos Si todo esta bien, procede a realizar la actualizacion
     * correspondiente
     */
    private void actualizarArchivo()
    {
        String nombreInput = nombre.getText().trim();
        String pesoInput = peso.getText().trim();

        if (!nombreInput.isBlank() && (archivo.getTipo() == 'C' || (archivo.getTipo() == 'A' && !pesoInput.isBlank())))
        {
            String[] nombreExtencion = Ctrl.validarNombre(nombreInput, archivo.getTipo());
            if (nombreExtencion != null)
            {
                int tamanio = (archivo.getTipo() == 'A') ? Ctrl.esNumeroValido(pesoInput) : 0;
                if (tamanio > 0 || archivo.getTipo() == 'C')
                {
                    if (!nombreInput.equals(etq) || tamanio != archivo.getTamanio())
                    {
                        String[] arr = Ctrl.splitPath(Var.rutaActual + nombreInput);
                        Nodo existente = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, arr, etq);
                        Nodo remplazar = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, arr, nombreInput);
                        if (remplazar == null || remplazar == existente)
                        {
                            Var.getMultilista().setRaiz(Var.getMultilista().elimina(Var.getMultilista().getRaiz(), 0, Ctrl.splitPath(Var.rutaActual + etq), etq));
                            if (Var.banderaEliminarMultilista)
                            {
                                boolean estado = Ctrl.actualizar(existente, nombreExtencion[0], nombreExtencion[1], archivo.getAutor(), archivo.getTipo(), tamanio, archivo.getRuta());
                                boolean guardado = ManipulacionArchivos.guardar(Var.getMultilista(), "datos.dat");
                                if (Var.banderaInsersionMultilista && estado && guardado)
                                {
                                    Ctrl.actualizarRegistrosInterfaz(Ctrl.splitPath(Var.rutaActual), etq, true);                              
                                    VentanaPropiedades.this.dispose();
                                }
                            }
                        } else
                        {
                            JOptionPane.showMessageDialog(this, "Ya existe un archivo con el nombre \" " + nombreInput + " \".", "Advertencia", JOptionPane.WARNING_MESSAGE);
                            nombre.requestFocus();
                        }
                    } else
                    {
                        VentanaPropiedades.this.dispose();
                    }
                } else
                {
                    JOptionPane.showMessageDialog(this, "El tamaño no es valido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    peso.requestFocus();
                }
            } else
            {
                JOptionPane.showMessageDialog(this, "El nombre no contiene un formato valido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                nombre.requestFocus();
            }
        } else
        {
            JOptionPane.showMessageDialog(this, "Hay campos vacios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            if (nombre.getText().trim().isBlank())
            {
                nombre.requestFocus();
            } else
            {
                peso.requestFocus();
            }
        }
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
