package vista;

import clases.Archivo;
import clases.Nodo;
import controlador.Ctrl;
import static controlador.Ctrl.splitPath;
import controlador.JTextFieldEdit;
import controlador.ManipulacionArchivos;
import controlador.TablaPersonalizada;
import controlador.TableModelPersonalizada;
import controlador.Var;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;

public class VentanaEliminar extends JDialog
{

    private JPanel panelPrincipal;
    private JPanel panelCenter;
    private JPanel panelTop;
    private JPanel panelBottom;
    
    private JTextFieldEdit ruta;                //muestra la ruta
    private JLabel direccionAnterior;           //icono
    private TableModelPersonalizada model;      //model de la tabla
    private TablaPersonalizada tabla2;          //tabla
    private final String nombreEliminar;        //nombre del nodo a elinimar
    private Nodo nodo;                          //para desplegar la lista de contenido
   
    public VentanaEliminar(JFrame frame, String nombreEliminar)
    {
        super(frame, "Eliminar Carpeta", true);
        this.setSize(650, 450);
        this.setLocationRelativeTo(frame);
        this.setResizable(false);
        this.nombreEliminar = nombreEliminar;
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponentes();
        addEscapeListener();
    }

    private void initComponentes()
    {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBackground(Color.WHITE);
        
        nodo = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, Ctrl.splitPath(Var.rutaActual + nombreEliminar), nombreEliminar);
                
        initPanelTop();
        panelPrincipal.add(panelTop, BorderLayout.NORTH);

        initPanelCenter();
        panelPrincipal.add(panelCenter, BorderLayout.CENTER);

        initPanelBottom();
        panelPrincipal.add(panelBottom, BorderLayout.SOUTH);

        this.add(panelPrincipal);
    }

    private void initPanelTop()
    {
        panelTop = new JPanel();
        panelTop.setBackground(Color.WHITE);
        panelTop.setLayout(new BorderLayout());

        JLabel mensaje = new JLabel("Se eliminarán los siguientes elementos y no podrán ser recuperados");
        mensaje.setHorizontalAlignment(SwingConstants.CENTER);
        mensaje.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelTop.add(mensaje, BorderLayout.NORTH);

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BorderLayout());
        subPanel.setBackground(Color.WHITE);
        subPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        direccionAnterior = new JLabel(new ImageIcon(Var.PATH_IMAGENES + "arriba2.png"));
        direccionAnterior.setBorder(new EmptyBorder(0, 10, 0, 10));

        JPanel rutaPanel = new JPanel();
        rutaPanel.setLayout(new BorderLayout());
        rutaPanel.setBackground(Color.WHITE);

        JLabel etiquetaRuta = new JLabel("C:/");
        etiquetaRuta.setBorder(new EmptyBorder(0, 10, 0, 10));

        ruta = new JTextFieldEdit(45, Var.rutaActual + nombreEliminar + "/", false);
        ruta.setEditable(false);
        ruta.setEnabled(false);
        ruta.setDisabledTextColor(Color.BLACK);

        rutaPanel.add(etiquetaRuta, BorderLayout.WEST);
        rutaPanel.add(ruta, BorderLayout.CENTER);

        subPanel.add(direccionAnterior, BorderLayout.EAST);
        subPanel.add(rutaPanel, BorderLayout.CENTER);

        direccionAnterior.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent evt)
            {
                if (!ruta.getText().equals(Var.rutaActual + nombreEliminar + "/"))
                {
                    String[] arr = splitPath(ruta.getText());
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

        panelTop.add(subPanel, BorderLayout.CENTER);
    }

    private void initPanelCenter()
    {
        panelCenter = new JPanel();
        panelCenter.setBackground(Color.WHITE);
        panelCenter.setLayout(new BorderLayout());

        model = new TableModelPersonalizada(Ctrl.cargarDirectorio(nodo.getAbajo()), false);
        tabla2 = new TablaPersonalizada(model);
        
        JTableHeader header = tabla2.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
        header.setForeground(Color.BLACK);
        JScrollPane scrollPane = new JScrollPane(tabla2);
        JViewport viewport = scrollPane.getViewport();
        viewport.setBackground(Color.WHITE);

        panelCenter.add(scrollPane, BorderLayout.CENTER);

        tabla2.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getClickCount() == 2)
                {
                    String nom = tabla2.getValueAt(tabla2.getSelectedRow(), 1).toString();
                    Nodo seleccionado = Var.getMultilista().buscar(Var.getMultilista().getRaiz(), 0, splitPath(ruta.getText() + nom), nom);
                    if (seleccionado != null && seleccionado.getObjecto() instanceof Archivo x)
                    {
                        if (x.getTipo() == 'C')
                        {
                            ruta.setText(ruta.getText() + nom + "/");
                            model.actualizarTabla(Ctrl.cargarDirectorio(seleccionado.getAbajo()));
                        }
                    } 
                }
            }
        });
    }

    private void initPanelBottom()
    {
        panelBottom = new JPanel();
        panelBottom.setBackground(Color.WHITE);
        panelBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
        JButton botonEliminar = new JButton("Eliminar");
        JButton botonCancelar = new JButton("Cancelar");
//        botonCancelar.setAlignmentX(10);
//        botonCancelar.setAlignmentY(100);
        panelBottom.add(botonCancelar);
        panelBottom.add(botonEliminar);

//        Color azulMacLight = UIManager.getColor("Button.default.background");
        
//        botonEliminar.setBackground(azulMacLight);
//        botonEliminar.setForeground(Color.WHITE); 
//        botonEliminar.putClientProperty("JButton.arc", 15);
//        botonEliminar.putClientProperty("JButton.focusedBackground", azulMacLight.darker());

        botonEliminar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                VentanaPrincipal.confirmacionElimina = true;
                dispose();
            }
        });
        
        botonCancelar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });
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
