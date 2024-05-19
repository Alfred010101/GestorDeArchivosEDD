package vista;

import clases.Archivo;
import clases.Nodo;
import controlador.Ctrl;
import controlador.ManipulacionArchivos;
import controlador.TableModelPersonalizada;
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
    private JTextField ruta;
    private JTextField autor;
    private JTextField fecha;
    private JButton cambiar;
    private final TableModelPersonalizada model;
    final String pathImagenes = "src/vista/imagenes/";

    private final Archivo archivo;
    private final boolean modificar;

    public VentanaPropiedades(JFrame frame, String titulo, Archivo archivo, boolean modificar, TableModelPersonalizada model)
    {
        super(frame, "Propiedades de " + titulo, true);
        this.archivo = archivo;
        this.modificar = modificar;
        this.model = model;
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
        panelBoton.setBackground(Color.WHITE);

        panel.setBackground(Color.WHITE);
        panel.setLayout(new GridBagLayout());

        nombre = new JTextField(archivo.getNombre() + ((archivo.getExtension() == null) ? "" : archivo.getExtension()), 25);

        if (archivo.getTipo() == 'C')
        {
            String nom = nombre.getText();
            String[] arr = Ctrl.splitPath(archivo.getRuta() + nom);
            Nodo nodo = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, arr, nom);
            int size = Var.getMultilista().calcularTamanio(nodo, 0);
            peso = new JTextField(((size < 0) ? "El tamaño es mayor a (int)" : String.valueOf(size)), 15);
        } else
        {
            peso = new JTextField(String.valueOf(archivo.getTamanio()), 15);
        }

        ruta = new JTextField("C:/" + archivo.getRuta(), 25);
        autor = new JTextField(archivo.getAutor(), 15);
        fecha = new JTextField(archivo.getFecha(), 15);

        if (modificar)
        {
            if (archivo.getTipo() == 'C')
            {
                peso.setEditable(false);
            }
        } else
        {
            nombre.setEditable(false);
            peso.setEditable(false);
            panelBoton.setVisible(false);
        }

        ruta.setEditable(false);
        autor.setEditable(false);
        fecha.setEditable(false);

        icono = new JLabel(new ImageIcon(pathImagenes + ((archivo.getTipo() == 'A') ? "expediente2.png" : "carpeta_vacia2.png")));
        panelIcono.add(icono);

        gbConstraints = new GridBagConstraints();
        this.addComponent(new JLabel("Nombre : "), 1, 0, 1, 1, GridBagConstraints.EAST);
        this.addComponent(new JLabel("Tamaño (KB): "), 2, 0, 1, 1, GridBagConstraints.EAST);
        this.addComponent(new JLabel("Ruta : "), 3, 0, 1, 1, GridBagConstraints.EAST);
        this.addComponent(new JLabel("Fecha : "), 4, 0, 1, 1, GridBagConstraints.EAST);
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

    private void actualizarArchivo()
    {
        if (!nombre.getText().trim().isBlank() && (archivo.getTipo() == 'C' || (archivo.getTipo() == 'A' && !peso.getText().isBlank())))
        {
            String[] nombreExtencion = Ctrl.validarNombre(nombre.getText().trim(), archivo.getTipo());
            if (nombreExtencion != null)
            {
                int tamanio = (archivo.getTipo() == 'A') ? Ctrl.esNumeroValido(peso.getText().trim()) : 0;
                if (tamanio > 0 || archivo.getTipo() == 'C')
                {
                    String nom = archivo.getNombre() + ((archivo.getTipo() == 'A') ? archivo.getExtension() : "");
                    String[] directorioN = Ctrl.splitPath(archivo.getRuta() + nom);
                    
                    
                    Nodo nodo = new Nodo(this.nombre.getText().trim(), archivo);
                    Nodo seleccionado = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, directorioN, nom);
                    Var.getMultilista().cambiarRuta(seleccionado, archivo.getRuta() + nombre.getText().trim() + "/");
                    archivo.setTamanio(tamanio);
                    archivo.setNombre(nombreExtencion[0]);
                    archivo.setExtension(nombreExtencion[1]);
                    nodo.setAbajo(seleccionado.getAbajo());
                    Var.getMultilista().setRaiz(Var.getMultilista().elimina(Var.getMultilista().getRaiz(), 0, directorioN, nom));
                    Var.getMultilista().setRaiz(Var.getMultilista().insertar(Var.getMultilista().getRaiz(), 0, directorioN, nodo));

                    boolean guardado = ManipulacionArchivos.guardar(Var.getMultilista(), "datos.dat");
                    
                    if (guardado)
                    {
                        directorioN = Ctrl.splitPath(archivo.getRuta());
                        if (directorioN.length > 0)
                        {
                            Nodo directorio = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, directorioN, directorioN[directorioN.length - 1]);
                            model.actualizarTabla(Ctrl.cargarDirectorio(directorio.getAbajo()));
                            VentanaPropiedades.this.dispose();
                        } else
                        {
                            model.actualizarTabla(Ctrl.cargarDirectorio(Var.getMultilista().getRaiz()));
                            VentanaPropiedades.this.dispose();
                        }
                    }
//                    String nombre = archivo.getNombre() + ((archivo.getTamanio() == 'A') ? archivo.getExtension() : "");
//                    String[] arr = Ctrl.splitPath(archivo.getRuta() + nombre);
//                    Var.getMultilista().setRaiz(Var.getMultilista().elimina(Var.getMultilista().getRaiz(), 0, arr, nombre));

//                    archivo.setTamanio(tamanio);
//                    archivo.setNombre(nombreExtencion[0]);
//                    archivo.setExtension(nombreExtencion[1]);
//                    Nodo nodo = new Nodo(this.nombre.getText().trim(), archivo);
//                    Var.getMultilista().insertar(Var.getMultilista().getRaiz(), 0, arr, nodo);
//                    boolean guardado = ManipulacionArchivos.guardar(Var.getMultilista(), "datos.dat");
//                    if (guardado)
//                    {
//                        if (arr.length > 0)
//                        {
//                            model.actualizarTabla(Ctrl.cargarDirectorio(nodo.getArriba().getAbajo()));
//                        } else
//                        {
//                            model.actualizarTabla(Ctrl.cargarDirectorio(Var.getMultilista().getRaiz()));
//                        }
//                        VentanaPropiedades.this.dispose();
//                    } else
//                    {
//                        System.out.println("Error al crear el archivo");
//                    }
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
