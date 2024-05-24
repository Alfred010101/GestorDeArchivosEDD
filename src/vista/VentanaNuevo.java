package vista;

import clases.Nodo;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.*;
import static controlador.Ctrl.splitPath;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Alfred
 */
public class VentanaNuevo extends JDialog
{

    private JPanel principal;
    private JPanel panel;
    private GridBagConstraints gbConstraints;
    private JLabel icono;
    private JTextFieldEdit nombre;
    private JTextFieldEdit peso;
    private JTextFieldEdit ruta;
    private JTextFieldEdit autor;
    private JButton crear;
    private final char tipo;
//    private final String dir;
    private final TableModelPersonalizada model;
    private final String pathImagenes = "src/vista/imagenes/";

    public VentanaNuevo(JFrame frame, String titulo, char tipo, TableModelPersonalizada model)
    {
        super(frame, titulo, true);
        this.model = model;
        this.tipo = tipo;
//        this.dir = dir;
        this.setSize(400, 280);
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
        principal.setBackground(Color.WHITE);
        panel = new JPanel();
        JPanel panelIcono = new JPanel();
        JPanel panelForm = new JPanel();
        JPanel panelBoton = new JPanel();

        panelIcono.setBackground(Color.WHITE);
        panelIcono.setLayout(new BoxLayout(panelIcono, BoxLayout.X_AXIS));
        panelForm.setBackground(Color.WHITE);

        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());

        panelBoton.setBackground(Color.WHITE);

        nombre = new JTextFieldEdit(20, "", false);
        peso = new JTextFieldEdit(20, "", false);
        ruta = new JTextFieldEdit(20, "C:/" + Var.rutaActual, false);
        autor = new JTextFieldEdit(20, System.getProperty("user.name"), false);
        ruta.setEnabled(false);
        autor.setEnabled(false);
        crear = new JButton("Crear");
        panelBoton.add(crear);

        gbConstraints = new GridBagConstraints();
        if (tipo == 'A')
        {
            icono = new JLabel(new ImageIcon(pathImagenes + "nuevo-documento2.png"));
            panelIcono.add(icono);//this.addComponent(icono, 0, 1, 1, 1, GridBagConstraints.CENTER);

            this.addComponent(new JLabel("Nombre : "), 1, 0, 1, 1, GridBagConstraints.EAST);
            this.addComponent(new JLabel("Tamaño en KB : "), 2, 0, 1, 1, GridBagConstraints.EAST);
            this.addComponent(peso, 2, 1, 1, 1, GridBagConstraints.WEST);
        } else
        {
            icono = new JLabel(new ImageIcon(pathImagenes + "carpeta-vacia2.png"));
            panelIcono.add(icono);
            this.addComponent(new JLabel("Nombre : "), 1, 0, 1, 1, GridBagConstraints.EAST);
        }
        this.addComponent(new JLabel("Ruta : "), 3, 0, 1, 1, GridBagConstraints.EAST);
        this.addComponent(new JLabel("Autor : "), 4, 0, 1, 1, GridBagConstraints.EAST);
        this.addComponent(nombre, 1, 1, 1, 1, GridBagConstraints.WEST);

        this.addComponent(ruta, 3, 1, 1, 1, GridBagConstraints.WEST);
        this.addComponent(autor, 4, 1, 1, 1, GridBagConstraints.WEST);

        nombre.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (!nombre.getText().isEmpty())
                {
                    if (tipo == 'A')
                    {
                        Ctrl.enter(e, peso);
                    } else
                    {
                        enterKeyPressed(e.getKeyChar());
                    }
                }
            }
        });

        peso.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (!peso.getText().isEmpty())
                {
                    enterKeyPressed(e.getKeyChar());
                }
            }
        });

        crear.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                enterKeyPressed(e.getKeyChar());
            }
        });
        
        crear.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                crear();
            }
        });

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

    private void enterKeyPressed(char e)
    {
        if (e == '\n')
        {
            crear();
        }
    }

    private void crear()
    {
        String nombreInput = nombre.getText().trim();
        String pesoInput = peso.getText().trim();
        
        if (!nombreInput.isBlank() && (tipo == 'C' || (tipo == 'A' && !pesoInput.isBlank())))
        {
            String[] nombreExtencion = Ctrl.validarNombre(nombreInput, tipo);
            if (nombreExtencion != null)
            {
                int tamanio = (tipo == 'A') ? Ctrl.esNumeroValido(pesoInput) : 0;
                if (tamanio > 0 || tipo == 'C')
                {
                    //pendiente este bloque
                    boolean estado = Ctrl.crear(nombreExtencion[0], nombreExtencion[1], autor.getText(), tipo, tamanio, Var.rutaActual);
                    boolean guardado = ManipulacionArchivos.guardar(Var.getMultilista(), "datos.dat");
                    if (estado && guardado)
                    {
                        String[] arr = splitPath(Var.rutaActual);
                        if (arr.length > 0)
                        {
                            Nodo dirActual = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, arr, arr[arr.length - 1]);
                            model.actualizarTabla(Ctrl.cargarDirectorio(dirActual.getAbajo()));
                        } else
                        {
                            model.actualizarTabla(Ctrl.cargarDirectorio(Var.getMultilista().getRaiz()));
                        }
                        VentanaNuevo.this.dispose();
                    } else
                    {
                        JOptionPane.showMessageDialog(this, "Error al crear el archivo", "Error", JOptionPane.ERROR_MESSAGE);
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
            }else
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
