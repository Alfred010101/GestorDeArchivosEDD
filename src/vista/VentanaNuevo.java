package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
    private JButton btnCrear;
    private final char tipo;

    final String pathImagenes = "src/vista/imagenes/";

    public VentanaNuevo(JFrame frame, String titulo, char tipo)
    {
        super(frame, titulo, true);
        this.tipo = tipo;
        this.setSize(450, 320);
        this.setLocationRelativeTo(frame);
        this.setResizable(false);

        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        initComponents();
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
        btnCrear = new JButton(" Crear ");

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
                    enterKeyPressed(e, nombre.getText(), peso);
                } else
                {
                    enterKeyPressed(e, nombre.getText(), btnCrear);
                }

            }
        });

        peso.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                enterKeyPressed(e, peso.getText(), btnCrear);
            }
        });

        btnCrear.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyChar() == '\n')
                {
                    crear();
                }
            }
        });

        btnCrear.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                crear();
            }
        });

        panelBoton.add(btnCrear);

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

    private void enterKeyPressed(KeyEvent e, String s, Object obj)
    {
        if (!s.isEmpty())
        {
            Ctrl.enter(e, obj);
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
        }else
        {
            System.out.println("Hay campos vacios");
        }
    }
}
