package vista;

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
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
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
    private JTextField nombre;
    private JTextField peso;
    JTextField ruta;
    JTextField autor;
    private final char tipo;
    private final TableModelPersonalizada model;
    final String pathImagenes = "src/vista/imagenes/";

    public VentanaNuevo(JFrame frame, String titulo, char tipo, TableModelPersonalizada model)
    {
        super(frame, titulo, true);
        this.model = model;
        this.tipo = tipo;
        this.setSize(450, 320);
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

        panelIcono.setBackground(Color.WHITE);
        panelForm.setBackground(Color.WHITE);

        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());

        panelBoton.setBackground(Color.WHITE);

        nombre = new JTextField(10);
        peso = new JTextField(5);
        ruta = new JTextField("Users", 25);
        autor = new JTextField("Alfred", 10);
        ruta.setEditable(false);
        autor.setEditable(false);

        gbConstraints = new GridBagConstraints();
        if (tipo == 'A')
        {
            icono = new JLabel(new ImageIcon(pathImagenes + "expediente2.png"));
            panelIcono.add(icono);//this.addComponent(icono, 0, 1, 1, 1, GridBagConstraints.CENTER);

            this.addComponent(new JLabel("Nombre : "), 1, 0, 1, 1, GridBagConstraints.EAST);
            this.addComponent(new JLabel("Tamaño en KB : "), 2, 0, 1, 1, GridBagConstraints.EAST);
            this.addComponent(peso, 2, 1, 1, 1, GridBagConstraints.WEST);
        } else
        {
            icono = new JLabel(new ImageIcon(pathImagenes + "carpeta_vacia2.png"));
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
                if (tipo == 'A')
                {
                    if (!nombre.getText().isEmpty())
                    {
                        Ctrl.enter(e, peso);
                    }
                } else
                {
                    enterKeyPressed(e.getKeyChar());
                }

            }
        });

        peso.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                enterKeyPressed(e.getKeyChar());
            }
        });

        panelForm.add(panel);
        principal.add(panelIcono);
        principal.add(panelForm);
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
        if (!nombre.getText().isBlank() || (tipo == 'A' && !peso.getText().isBlank()))
        {
            String[] nombreExtencion = Ctrl.validarNombre(nombre.getText(), tipo);
            if (nombreExtencion != null)
            {
                int tamanio = (tipo == 'A') ? Ctrl.esNumeroValido(peso.getText()) : 0;

                if (tamanio > 0 || tipo == 'C')
                {
                    boolean estado = Ctrl.crear(nombreExtencion[0], nombreExtencion[1], autor.getText(), tipo, tamanio, ruta.getText());
                    if (estado)
                    {
                        model.actualizarTabla(Ctrl.buscarNodo(Var.getLista()));
                        VentanaNuevo.this.dispose();
                    } else
                    {
                        System.out.println("Error al crear el archivo");
                    }
                } else
                {
                    System.out.println("El tamaño no es valido");
                }
            } else
            {
                System.out.println("nombre incorrecto");
            }
        } else
        {
            System.out.println("Hay campos vacios");
        }
    }

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
