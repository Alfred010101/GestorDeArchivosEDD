package vista;

import clases.Archivo;
import clases.Nodo;
import clases.TablaHash;
import controlador.Ctrl;
import controlador.JTextFieldEdit;
import controlador.ManipulacionArchivos;
import controlador.TablaPersonalizada;
import controlador.TableModelPersonalizada;
import controlador.TreeCellRendererEdit;
import controlador.Var;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

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

    /**
     * Componentes agregados a el panel North manteniendo una barra de
     * herramientas simple contine la busqueda de archivos
     */
    private JLabel dirInicio;
    private JLabel nuevaCarpeta;
    private JLabel nuevoArchivo;
    private JTextFieldEdit ruta;
    private JTextFieldEdit busca;
    private JLabel dirAnterior;
    private JLabel eliminarBusqueda;

    /**
     * Componentes encargados de mostrar el marcador de favoritos
     */
    private JPanel panelFavoritos;
    private JScrollPane scrollArbolFavorios;
    private JTree treeFavoritos;
    private DefaultMutableTreeNode rootNodoFavoritos;

    /**
     * Componentes encargados de mostrar el arbol de directorios solo muestra
     * carperas los componetes estaticos facilitan su manipulacion desde otro
     * punto del sistema
     */
    private JPanel panelDirectorios;
    private JScrollPane scrollArbolDirectorios;
    public static JTree treeDirectorios;
    public static DefaultMutableTreeNode rootNodoDirectorios;

    /**
     * Splits para mover los panels
     */
    private JSplitPane splitPaneVertical;
    private JSplitPane splitPaneHorizontal;

    /**
     * model para la tabla es estatica para facilitar la manipulacion desde otro
     * punto del sistema
     */
    public static TableModelPersonalizada modelTabla;

    /**
     * Establecen valores parea que el split tenga limite en cuanto al espacio a
     * ocupar
     */
    private final int MIN_LEFT_PANEL = 0;
    private final int MAX_LEFT_PANEL = 200;

    public static JMenuItem pegar;
    
    public VentanaPrincipal()
    {
        this.setSize(1100, 600);
        this.setTitle("Gestor de Archivos - {FileMaster}");
        this.setMinimumSize(new Dimension(950, 400));
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
        initSplitPaneVertical();
//        initSplitPaneHorizontal();

        panelPrincipal.add(panelNorth, BorderLayout.NORTH);
        //panelPrincipal.add(panelNorth, BorderLayout.NORTH);
        // panelPrincipal.add(panelWest, BorderLayout.WEST);
        panelPrincipal.add(splitPaneVertical, BorderLayout.CENTER);
        panelPrincipal.add(panelSouth, BorderLayout.SOUTH);

        this.add(panelPrincipal);
    }

    /*
     *Configura el Split
     *permitiendo un limite de desplazamiento
     */
    private void initSplitPaneVertical()
    {
        splitPaneVertical = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panelWest, panelCenter);
        splitPaneVertical.setContinuousLayout(true);
        splitPaneVertical.setOneTouchExpandable(true);
        splitPaneVertical.setDividerLocation(150);
        PropertyChangeListener adjustDividerLocation = new PropertyChangeListener()
        {
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                int currentLocation = splitPaneVertical.getDividerLocation();
                if (currentLocation < MIN_LEFT_PANEL)
                {
                    splitPaneVertical.setDividerLocation(MIN_LEFT_PANEL);
                } else if (currentLocation > MAX_LEFT_PANEL)
                {
                    splitPaneVertical.setDividerLocation(MAX_LEFT_PANEL);
                }
            }
        };
        splitPaneVertical.addPropertyChangeListener(JSplitPane.DIVIDER_LOCATION_PROPERTY, adjustDividerLocation);
    }

    private void initSplitPaneHorizontal()
    {
        splitPaneHorizontal = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelFavoritos, panelDirectorios);
        splitPaneHorizontal.setContinuousLayout(true);
        splitPaneHorizontal.setOneTouchExpandable(true);
        splitPaneHorizontal.setDividerLocation(150);
    }

    /*
     *Administra funcion de busqueda
     *tambien crea nuevas carpetas y archivos
     *de igual modo se encarga de ir un directorio arriba
     */
    private void initPanelNorth()
    {
        panelNorth = new JPanel();
        panelNorth.setBackground(new Color(220, 220, 220));
        JPanel contenedorIconos = new JPanel();
        contenedorIconos.setLayout(new BoxLayout(contenedorIconos, BoxLayout.X_AXIS));
        contenedorIconos.setBackground(new Color(220, 220, 220));

        dirInicio = new JLabel(new ImageIcon(Var.PATH_IMAGENES + "boton-de-inicio1.png"));
        dirInicio.setOpaque(true);
        dirInicio.setBorder(new EmptyBorder(3, 3, 3, 3));
        dirInicio.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dirInicio.setToolTipText("Ir al directori raiz");
        dirInicio.setBackground(null);

        nuevaCarpeta = new JLabel(new ImageIcon(Var.PATH_IMAGENES + "agregar-carpeta1.png"));
        nuevaCarpeta.setOpaque(true);
        nuevaCarpeta.setBorder(new EmptyBorder(3, 3, 3, 3));
        nuevaCarpeta.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nuevaCarpeta.setToolTipText("Nueva Carpeta");
        nuevaCarpeta.setBackground(null);

        nuevoArchivo = new JLabel(new ImageIcon(Var.PATH_IMAGENES + "agregar-archivo1.png"));
        nuevoArchivo.setOpaque(true);
        nuevoArchivo.setBorder(new EmptyBorder(3, 3, 3, 3));
        nuevoArchivo.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nuevoArchivo.setToolTipText("Nuevo Archivo");
        nuevoArchivo.setBackground(null);

        dirAnterior = new JLabel(new ImageIcon(Var.PATH_IMAGENES + "arriba2.png"));
        dirAnterior.setOpaque(true);
        dirAnterior.setBorder(new EmptyBorder(3, 3, 3, 3));
        dirAnterior.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dirAnterior.setToolTipText("Ir arriba");
        dirAnterior.setBackground(null);

        eliminarBusqueda = new JLabel(new ImageIcon(Var.PATH_IMAGENES + "cancelar1.png"));
        eliminarBusqueda.setOpaque(true);
        eliminarBusqueda.setBorder(new EmptyBorder(3, 3, 3, 3));
        eliminarBusqueda.setCursor(new Cursor(Cursor.HAND_CURSOR));
        eliminarBusqueda.setToolTipText("Quitar busqueda");
        eliminarBusqueda.setBackground(null);

        ruta = new JTextFieldEdit(45, "", true);
        busca = new JTextFieldEdit(15, "Buscar Archivo", false);
//        ruta.setText("");
//        ruta.setEditable(false);
        dirInicio.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                dirInicio.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                dirInicio.setBackground(null);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                dirInicio.setBackground(new Color(235, 235, 235));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                dirInicio.setBackground(null);
            }

            @Override
            public void mouseClicked(MouseEvent evt)
            {
                if (!Var.rutaActual.isBlank())
                {
                    modelTabla.actualizarTabla(Ctrl.cargarDirectorio(Var.getMultilista().getRaiz()));
                    Var.rutaActual = "";
                    ruta.setText(Var.rutaActual);
                }
            }
        });

        nuevaCarpeta.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                nuevaCarpeta.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                nuevaCarpeta.setBackground(null);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                nuevaCarpeta.setBackground(new Color(235, 235, 235));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                nuevaCarpeta.setBackground(null);
            }

            @Override
            public void mouseClicked(MouseEvent evt)
            {
                new VentanaNuevo(VentanaPrincipal.this, "Nueva Carpeta", 'C').setVisible(true);
            }
        });

        nuevoArchivo.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                nuevoArchivo.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                nuevoArchivo.setBackground(null);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                nuevoArchivo.setBackground(new Color(235, 235, 235));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                nuevoArchivo.setBackground(null);
            }

            @Override
            public void mouseClicked(MouseEvent evt)
            {
                new VentanaNuevo(VentanaPrincipal.this, "Nuevo Archivo", 'A').setVisible(true);
            }
        });

        ruta.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                irBuscarRuta(e.getKeyChar());
            }
        });

        dirAnterior.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                dirAnterior.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                dirAnterior.setBackground(null);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                dirAnterior.setBackground(new Color(235, 235, 235));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                dirAnterior.setBackground(null);
            }

            @Override
            public void mouseClicked(MouseEvent evt)
            {
                irArriba();
            }
        });

        busca.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                //pendiente
            }
        });

        eliminarBusqueda.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                eliminarBusqueda.setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                eliminarBusqueda.setBackground(null);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                eliminarBusqueda.setBackground(new Color(235, 235, 235));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                eliminarBusqueda.setBackground(null);
            }

            @Override
            public void mouseClicked(MouseEvent evt)
            {
                //pendiente
            }
        });

        contenedorIconos.add(dirInicio);
        contenedorIconos.add(nuevaCarpeta);
        contenedorIconos.add(nuevoArchivo);
        panelNorth.add(contenedorIconos);
        panelNorth.add(new JLabel("     C:/"));
        panelNorth.add(ruta);
        panelNorth.add(dirAnterior);
        panelNorth.add(new JLabel("   "));
        panelNorth.add(busca);
        panelNorth.add(eliminarBusqueda);
    }

    /*
     *Administra el arbol de directorios
     */
    private void initPanelWest()
    {
        panelWest = new JPanel();
//        panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.X_AXIS));
//        panelWest.setBackground(Color.GREEN);
        panelWest.setLayout(new BorderLayout());
        panelFavoritos = new JPanel();
        panelDirectorios = new JPanel();
        panelDirectorios.setLayout(new BorderLayout());
        panelFavoritos.setLayout(new BorderLayout());
        rootNodoFavoritos = new DefaultMutableTreeNode("Mis Favoritos");
        rootNodoDirectorios = new DefaultMutableTreeNode("Mi Equipo");
        Ctrl.cargarArbolCarpetas(rootNodoDirectorios, Var.getMultilista().getRaiz());
        treeFavoritos = new JTree(rootNodoFavoritos);
        treeDirectorios = new JTree(rootNodoDirectorios);
//        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) treeDirectorios.getCellRenderer();
//        renderer.setOpenIcon(new ImageIcon("src/vista/imagenes/carpeta1.png")); 
//        renderer.setClosedIcon(new ImageIcon("src/vista/imagenes/carpeta1.png")); 
//        renderer.setLeafIcon(new ImageIcon("src/vista/imagenes/carpeta1.png")); 

        TreeCellRendererEdit render1 = new TreeCellRendererEdit(true);
        treeFavoritos.setCellRenderer(render1);
        TreeCellRendererEdit render2 = new TreeCellRendererEdit(false);
        treeDirectorios.setCellRenderer(render2);

        treeDirectorios.addMouseMotionListener(new MouseAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                TreePath path = treeDirectorios.getPathForLocation(e.getX(), e.getY());
                if (path != null)
                {
                    treeDirectorios.setSelectionPath(path);
                }
            }
        });

        scrollArbolDirectorios = new JScrollPane();
        scrollArbolFavorios = new JScrollPane();
        scrollArbolFavorios.setViewportView(treeFavoritos);
        scrollArbolDirectorios.setViewportView(treeDirectorios);

        panelDirectorios.add(scrollArbolDirectorios, BorderLayout.CENTER);
        panelFavoritos.add(scrollArbolFavorios, BorderLayout.CENTER);
        initSplitPaneHorizontal();
        panelWest.add(splitPaneHorizontal, BorderLayout.CENTER);
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
        modelTabla = new TableModelPersonalizada(Ctrl.cargarDirectorio(Var.getMultilista().getRaiz()));
        modelTabla.actualizarTabla(Ctrl.cargarDirectorio(Var.getMultilista().getRaiz()));
        TablaPersonalizada tabla = new TablaPersonalizada(modelTabla);
        //Asigna colores a la tabla
//        JTableHeader header = tabla.getTableHeader();

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
        pegar = new JMenuItem("Pegar Aqui");
        pegar.setEnabled(false);
        menu.add(nuevaCarpetaItem);
        menu.add(nuevoArchivoItem);
        popupMenu.add(menu);
        popupMenu.add(pegar);

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
//        popupMenuConSeleccion.add(pegar);
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
                    Nodo seleccionado = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, Ctrl.splitPath(Var.rutaActual + directorioSeleccionado), directorioSeleccionado);
                    if (seleccionado != null && seleccionado.getObjecto() instanceof Archivo x)
                    {
                        if (x.getTipo() == 'C')
                        {
                            Var.rutaActual += directorioSeleccionado + "/";
                            ruta.setText(Var.rutaActual);
                            modelTabla.actualizarTabla(Ctrl.cargarDirectorio(seleccionado.getAbajo()));
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
//                System.out.println(tabla.isPopupMenuActive());
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
                new VentanaNuevo(VentanaPrincipal.this, "Nueva Carpeta", 'C').setVisible(true);
            }
        });

        nuevoArchivoItem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                new VentanaNuevo(VentanaPrincipal.this, "Nuevo Archivo", 'A').setVisible(true);
            }
        });
        
        pegar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (((Archivo)Var.nodoCopiarBuffer.getObjecto()).getRuta().equals(Var.rutaActual))
                {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "Ya existe un archivo con el nombre \"" + Var.nodoCopiarBuffer.getEtiqueta() +"\" en este directorio.", "Advertencia", JOptionPane.WARNING_MESSAGE); 
                }else if((Var.rutaActual).startsWith(((Archivo)Var.nodoCopiarBuffer.getObjecto()).getRuta() + Var.nodoCopiarBuffer.getEtiqueta() + "/"))
                {
                    JOptionPane.showMessageDialog(VentanaPrincipal.this, "No se puede pegar en un subdirectorio de \"" + Var.nodoCopiarBuffer.getEtiqueta() +"\".", "Advertencia", JOptionPane.WARNING_MESSAGE); 
                }else
                {
                    //si se elimina el nodo seleccionado para copiar
                    //establecer como nulo...pendiente
                    Ctrl.copiarNodo();
                }
            }
        });
        
        editar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String archivoSeleccionado = tabla.getValueAt(tabla.getSelectedRow(), 1).toString();
                Nodo seleccionado = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, Ctrl.splitPath(Var.rutaActual + archivoSeleccionado), archivoSeleccionado);
                if (seleccionado != null && seleccionado.getObjecto() instanceof Archivo x)
                {
                    new VentanaPropiedades(VentanaPrincipal.this, archivoSeleccionado, x, true).setVisible(true);
                }
            }
        });
        
        copiar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String archivoSeleccionado = tabla.getValueAt(tabla.getSelectedRow(), 1).toString();
                Var.nodoCopiarBuffer = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, Ctrl.splitPath(Var.rutaActual + archivoSeleccionado), archivoSeleccionado);
                pegar.setEnabled(true);
            }
        });
        
        elimnar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                eliminarArchivoCarpeta(tabla.getValueAt(tabla.getSelectedRow(), 1).toString());
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
                    new VentanaPropiedades(VentanaPrincipal.this, archivoSeleccionado, x, false).setVisible(true);
                }
            }
        });

//        panelCenter.add(panelNorth, BorderLayout.NORTH);
        panelCenter.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Buscara la ruta especificada en el JTextField llamado "ruta"
     */
    private void irBuscarRuta(char e)
    {
        if (!ruta.getText().trim().isEmpty())
        {
            if (e == '\n')
            {
                String[] arr = Ctrl.splitPath(ruta.getText().trim());
                if (arr.length > 0)
                {
                    Nodo directorioIr = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, arr, arr[arr.length - 1]);
                    if (directorioIr != null)
                    {
                        if (directorioIr.getObjecto() instanceof Archivo x)
                        {
                            if (x.getTipo() == 'C')
                            {
                                modelTabla.actualizarTabla(Ctrl.cargarDirectorio(directorioIr.getAbajo()));
                                Var.rutaActual = x.getRuta() + x.getNombre() + "/";
                                ruta.setText(Var.rutaActual);
                            }
                        }
                    } else
                    {
                        JOptionPane.showMessageDialog(VentanaPrincipal.this, "La ruta especificada no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    /**
     * Va al directorio padre del directorio actual
     */
    private void irArriba()
    {
        if (!Var.rutaActual.isBlank())
        {
            String[] arr = Ctrl.splitPath(Var.rutaActual);
            if (arr.length > 0)
            {
                Nodo directorioIr = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, arr, arr[arr.length - 1]);
                directorioIr = (directorioIr.getArriba() != null) ? directorioIr.getArriba().getAbajo() : Var.getMultilista().getRaiz();
                if (directorioIr.getObjecto() instanceof Archivo x)
                {
                    modelTabla.actualizarTabla(Ctrl.cargarDirectorio(directorioIr));
                    Var.rutaActual = x.getRuta();
                    ruta.setText(Var.rutaActual);
                }
            }
        }
    }

    /**
     * Elimina la carpeta o archivo seleccionado si contiene archivos tambien
     * los elimina
     */
    private void eliminarArchivoCarpeta(String nom)
    {
        String[] rutaAct = Ctrl.splitPath(Var.rutaActual);
        int opcion = JOptionPane.showConfirmDialog(VentanaPrincipal.this, "¿Esta seguro de eliminar el archivo \"" + nom + "\" ?", "Eliminar " + (nom.contains(".") ? "Carpeta" : "Archivo"), JOptionPane.YES_NO_OPTION);

        if (opcion == 0)
        {
            Var.getMultilista().setRaiz(Var.getMultilista().elimina(Var.getMultilista().getRaiz(), 0, Ctrl.splitPath(Var.rutaActual + nom), nom));
            boolean guardado = ManipulacionArchivos.guardar(Var.getMultilista(), "datos.dat");

            if (Var.banderaEliminarMultilista && guardado)
            {
                Ctrl.actualizarRegistrosInterfaz(rutaAct, nom, true);
            }
        }
    }

    /*
     *Muestra el numero de elementos
     */
    private void initPanelSouth()
    {
        panelSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSouth.setBackground(Color.WHITE);
        panelSouth.add(Var.contador);
    }
}
