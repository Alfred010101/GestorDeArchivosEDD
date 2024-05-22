package vista;

import clases.Archivo;
import clases.Nodo;
import controlador.Ctrl;
import controlador.ManipulacionArchivos;
import controlador.TablaPersonalizada;
import controlador.TableModelPersonalizada;
import controlador.Var;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Alfred
 */
public class VentanaPrincipal extends JFrame
{

    private JPanel panelPrincipal;
    private JPanel panelNorth;
    private JPanel panelWest;
    private JPanel panelCenter;
    private JPanel panelSouth;
    private JLabel nuevaCarpeta;
    private JLabel nuevoArchivo;
    private JTextField ruta;
    private JTextField busca;
    private JLabel dirAnterior;
    private TableModelPersonalizada model;
    final String pathImagenes = "src/vista/imagenes/";

    public VentanaPrincipal()
    {
        this.setSize(900, 600);
        this.setTitle("Gestor de Archivos - {FileMaster}");
        this.setMinimumSize(new Dimension(850, 400));
        this.setLocationRelativeTo(null);
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initComponents()
    {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);

        initPanelNorth();
        initPanelWest();
        initPanelCenter();
        initPanelSouth();

        //panelPrincipal.add(panelNorth, BorderLayout.NORTH);
        // panelPrincipal.add(panelWest, BorderLayout.WEST);
        panelPrincipal.add(panelCenter, BorderLayout.CENTER);
        panelPrincipal.add(panelSouth, BorderLayout.SOUTH);

        this.add(panelPrincipal);
    }

    /*
     *Administra funcion de busqueda
     *tambien crea nuevas carpetas y archivos
     *de igual modo se encarga de ir un directorio arriba
     */
    private void initPanelNorth()
    {
        panelNorth = new JPanel();
        nuevaCarpeta = new JLabel(new ImageIcon(pathImagenes + "agregar-carpeta1.png"));
        nuevaCarpeta.setBorder(new EmptyBorder(0, 0, 0, 0));
        nuevoArchivo = new JLabel(new ImageIcon(pathImagenes + "agregar-archivo1.png"));
        nuevoArchivo.setBorder(new EmptyBorder(0, 0, 0, 0));
        dirAnterior = new JLabel(new ImageIcon(pathImagenes + "arriba2.png"));
        dirAnterior.setBorder(new EmptyBorder(0, 0, 0, 0));

        ruta = new JTextField(45);
        busca = new JTextField(15);

        ruta.setText("");
        ruta.setEditable(false);

        nuevaCarpeta.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseExited(MouseEvent evt)
            {
                //iconos[1].setIcon(new ImageIcon(pathImagenes + nomIcon[1]));
            }

            @Override
            public void mouseEntered(MouseEvent evt)
            {
                //iconos[1].setIcon(new ImageIcon(pathImagenes + "guardar_Hover.png"));
            }

            @Override
            public void mouseClicked(MouseEvent evt)
            {
                new VentanaNuevo(VentanaPrincipal.this, "Nueva Carpeta", 'C', model, ruta.getText()).setVisible(true);
            }
        });

        nuevoArchivo.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseExited(MouseEvent evt)
            {
                //iconos[1].setIcon(new ImageIcon(pathImagenes + nomIcon[1]));
            }

            @Override
            public void mouseEntered(MouseEvent evt)
            {
                //iconos[1].setIcon(new ImageIcon(pathImagenes + "guardar_Hover.png"));
            }

            @Override
            public void mouseClicked(MouseEvent evt)
            {
                new VentanaNuevo(VentanaPrincipal.this, "Nuevo Archivo", 'A', model, ruta.getText()).setVisible(true);
            }
        });

        dirAnterior.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseExited(MouseEvent evt)
            {
                //iconos[1].setIcon(new ImageIcon(pathImagenes + nomIcon[1]));
            }

            @Override
            public void mouseEntered(MouseEvent evt)
            {
                //iconos[1].setIcon(new ImageIcon(pathImagenes + "guardar_Hover.png"));
            }

            @Override
            public void mouseClicked(MouseEvent evt)
            {
                if (!ruta.getText().isBlank())
                {
                    String[] arr = Ctrl.splitPath(ruta.getText());
                    if (arr.length > 0)
                    {
                        Nodo directorioIr = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, arr, arr[arr.length - 1]);
                        directorioIr = (directorioIr.getArriba() != null) ? directorioIr.getArriba().getAbajo() : Var.getMultilista().getRaiz();
                        model.actualizarTabla(Ctrl.cargarDirectorio(directorioIr));
                        if (directorioIr.getObjecto() instanceof Archivo x)
                        {
                            ruta.setText(x.getRuta());
                        }
                    }
                }
            }
        });

        panelNorth.add(nuevaCarpeta);
        panelNorth.add(nuevoArchivo);
        panelNorth.add(new JLabel("     C:/"));
        panelNorth.add(ruta);
        panelNorth.add(dirAnterior);
        panelNorth.add(busca);
    }

    /*
     *Administra el arbol de directorios
     */
    private void initPanelWest()
    {
        panelWest = new JPanel();
        panelWest.setBackground(Color.WHITE);
        panelWest.add(new JLabel("fdssdf sdsdffdssdf sdsdfsdf"));
    }

    /*
     *Administra la parte de la visualizacion de directorio especifico
     *se encarga de los popMenu
     */
    private void initPanelCenter()
    {
        panelCenter = new JPanel();
        panelCenter.setBackground(Color.WHITE);
        panelCenter.setLayout(new BorderLayout());

        /*
         *Configuracion de la tabla que sirve para mostrar el directorio actual
         */
        model = new TableModelPersonalizada(Ctrl.cargarDirectorio(Var.getMultilista().getRaiz()));
        model.actualizarTabla(Ctrl.cargarDirectorio(Var.getMultilista().getRaiz()));
        TablaPersonalizada tabla = new TablaPersonalizada(model);
        //Asigna colores a la tabla
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(Color.DARK_GRAY);
        header.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(tabla);
        JViewport viewport = scrollPane.getViewport();
        viewport.setBackground(Color.WHITE);

        /*
         *Se crea y configura el popMenu para [CREAR]
         *Carpetas, Archivos, Propiedades
         */
        JPopupMenu popupMenu = new JPopupMenu();
        JMenu menu = new JMenu("Nuevo");
        JMenuItem nuevaCarpetaItem = new JMenuItem("Carpeta");
        JMenuItem nuevoArchivoItem = new JMenuItem("Archivo");
        JMenuItem moverAqui = new JMenuItem("Mover Aqui");
//        JMenuItem propiedades = new JMenuItem("Propiedades");
        moverAqui.setEnabled(false);
        menu.add(nuevaCarpetaItem);
        menu.add(nuevoArchivoItem);
        popupMenu.add(menu);
        popupMenu.add(moverAqui);
//        popupMenu.add(new JSeparator());
//        popupMenu.add(propiedades);

        /*
         *Se crea y configura el popMenu para cuando esta seleccionado un elemento
         *Editar, Copiar, Mover, Eliminar, Propiedades
         */
        JPopupMenu popupMenuConSeleccion = new JPopupMenu();
        JMenuItem editar = new JMenuItem("Editar");
        JMenuItem copiar = new JMenuItem("Copiar");
        JMenuItem mover = new JMenuItem("Mover");
        JMenuItem elimnar = new JMenuItem("Eliminar");
        JMenuItem propiedades = new JMenuItem("Propiedades");
        popupMenuConSeleccion.add(editar);
        popupMenuConSeleccion.add(copiar);
        popupMenuConSeleccion.add(mover);
        popupMenuConSeleccion.add(elimnar);
        popupMenuConSeleccion.add(new JPopupMenu.Separator());
        popupMenuConSeleccion.add(propiedades);

        tabla.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    tabla.setPopupMenuActive(true);
                    mostrarMenuEmergente(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    tabla.setPopupMenuActive(false);
                    mostrarMenuEmergente(e);
                }
            }

            private void mostrarMenuEmergente(MouseEvent e)
            {
                if (!tabla.isRowSelected(tabla.rowAtPoint(e.getPoint())))
                {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                } else
                {
                    int filaSeleccionada = tabla.rowAtPoint(e.getPoint());
                    tabla.setRowSelectionInterval(filaSeleccionada, filaSeleccionada);
                    popupMenuConSeleccion.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        tabla.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2)
                {
                    String directorioSeleccionado = tabla.getValueAt(tabla.getSelectedRow(), 1).toString();
                    Nodo seleccionado = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, Ctrl.splitPath(ruta.getText() + directorioSeleccionado), directorioSeleccionado);
                    if (seleccionado != null && seleccionado.getObjecto() instanceof Archivo x)
                    {
                        if (x.getTipo() == 'C')
                        {
                            ruta.setText(ruta.getText() + directorioSeleccionado + "/");
                            model.actualizarTabla(Ctrl.cargarDirectorio(seleccionado.getAbajo()));
                        }
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                tabla.setHoveredRow(-1);

                repaint();

            }
        });

        tabla.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                System.out.println(tabla.isPopupMenuActive());
                if (!tabla.isPopupMenuActive())
                {

                    int row = tabla.rowAtPoint(e.getPoint());
                    if (row != tabla.getHoveredRow())
                    {
                        tabla.setHoveredRow(row);
                        repaint();
                    }
                }
            }
        });

        /*
         * Quita la seleccion de la fila cuando damos click fuera
         */
        scrollPane.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusLost(FocusEvent e)
            {
                if (tabla.getSelectedColumn() != -1)
                {
                    tabla.setColumnSelectionAllowed(false);
                    tabla.clearSelection();
                }
            }
        });

        /*
         * Inica cuando mostrar el popMenu, tambien quita la seleccion de una fila
         */
        scrollPane.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    mostrarMenuEmergente(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    mostrarMenuEmergente(e);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (tabla.getSelectedColumn() != -1)
                {
                    tabla.setColumnSelectionAllowed(false);
                    tabla.clearSelection();
                }
            }

            private void mostrarMenuEmergente(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    int filaSeleccionada = tabla.rowAtPoint(e.getPoint());
                    if (filaSeleccionada == -1)
                    {
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    } else
                    {
                        tabla.setRowSelectionInterval(filaSeleccionada, filaSeleccionada);
                        popupMenuConSeleccion.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });

        nuevaCarpetaItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new VentanaNuevo(VentanaPrincipal.this, "Nueva Carpeta", 'C', model, ruta.getText()).setVisible(true);
            }
        });

        nuevoArchivoItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new VentanaNuevo(VentanaPrincipal.this, "Nuevo Archivo", 'A', model, ruta.getText()).setVisible(true);
            }
        });

        editar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String archivoSeleccionado = tabla.getValueAt(tabla.getSelectedRow(), 1).toString();
                Nodo seleccionado = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, Ctrl.splitPath(ruta.getText() + archivoSeleccionado), archivoSeleccionado);
                if (seleccionado != null && seleccionado.getObjecto() instanceof Archivo x)
                {
                    new VentanaPropiedades(VentanaPrincipal.this, archivoSeleccionado, x, true, model).setVisible(true);
                }
            }
        });

        elimnar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String nom = tabla.getValueAt(tabla.getSelectedRow(), 1).toString();
                String[] rutaa = Ctrl.splitPath(ruta.getText());
                //Nodo seleccionado = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, Ctrl.splitPath(ruta.getText() + nom), nom);
                Var.getMultilista().setRaiz(Var.getMultilista().elimina(Var.getMultilista().getRaiz(), 0, Ctrl.splitPath(ruta.getText() + nom), nom));

                boolean guardado = ManipulacionArchivos.guardar(Var.getMultilista(), "datos.dat");

                if (guardado)
                {
                    if (rutaa.length > 0)
                    {
                        Nodo directorio = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, rutaa, rutaa[rutaa.length - 1]);
                        model.actualizarTabla(Ctrl.cargarDirectorio(directorio.getAbajo()));
                    } else
                    {
                        model.actualizarTabla(Ctrl.cargarDirectorio(Var.getMultilista().getRaiz()));
                    }
                }

            }
        });

        propiedades.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String archivoSeleccionado = tabla.getValueAt(tabla.getSelectedRow(), 1).toString();
                Nodo seleccionado = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, Ctrl.splitPath(ruta.getText() + archivoSeleccionado), archivoSeleccionado);
                if (seleccionado != null && seleccionado.getObjecto() instanceof Archivo x)
                {
                    new VentanaPropiedades(VentanaPrincipal.this, archivoSeleccionado, x, false, null).setVisible(true);
                }
            }
        });

        panelCenter.add(panelNorth, BorderLayout.NORTH);
        panelCenter.add(scrollPane, BorderLayout.CENTER);
    }

    /*
     *Muestra el numero de elementos
     */
    private void initPanelSouth()
    {
        panelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSouth.setBackground(Color.WHITE);
        panelSouth.add(Var.getContador());
    }
}
