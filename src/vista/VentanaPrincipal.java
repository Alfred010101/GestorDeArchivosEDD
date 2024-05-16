package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

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

        panelPrincipal.add(panelNorth, BorderLayout.NORTH);
        panelPrincipal.add(panelWest, BorderLayout.WEST);
        panelPrincipal.add(panelCenter, BorderLayout.CENTER);
        panelPrincipal.add(panelSouth, BorderLayout.SOUTH);

        this.add(panelPrincipal);
    }

    private void initPanelNorth()
    {
        panelNorth = new JPanel();
        
        nuevaCarpeta = new JLabel(new ImageIcon(pathImagenes + "agregar-carpeta1.png"));
        nuevaCarpeta.setBorder(new EmptyBorder(0, 0, 0, 20));
        nuevoArchivo = new JLabel(new ImageIcon(pathImagenes + "agregar-archivo1.png"));
        nuevoArchivo.setBorder(new EmptyBorder(0, 0, 0, 20));
        dirAnterior = new JLabel(new ImageIcon(pathImagenes + "arriba.png"));
        dirAnterior.setBorder(new EmptyBorder(0, 0, 0, 20));
        
        ruta = new JTextField(40);
        busca = new JTextField(20);
        
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
                new VentanaNuevo(VentanaPrincipal.this, "Nueva Carpeta", 'C').setVisible(true);
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
                new VentanaNuevo(VentanaPrincipal.this, "Nuevo Archivo", 'A').setVisible(true);
            }
        });
        
        panelNorth.add(nuevaCarpeta);
        panelNorth.add(nuevoArchivo);
        panelNorth.add(ruta);
        panelNorth.add(dirAnterior);
        panelNorth.add(busca);
    }

    private void initPanelWest()
    {
        panelWest = new JPanel();
        panelWest.setBackground(Color.WHITE);
        panelWest.add(new JLabel("fdssdf sdsdffdssdf sdsdfsdf"));
    }

    private void initPanelCenter()
    {
        panelCenter = new JPanel();
        panelCenter.setBackground(Color.WHITE);
        panelCenter.setLayout(new BorderLayout());
        
        String[] columnNames = {"Tipo", "Nombre", "Fecha", "Tamaño"};
        
        // Crear el modelo de la tabla
        Object[][] data = {
            {"Archivo", "documento1.txt", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "15 KB"},
    {"Carpeta", "proyectos", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "—"},
    {"Archivo", "imagen2.png", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "250 KB"},
    {"Archivo", "presentación3.pptx", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "1.2 MB"},
    {"Archivo", "documento4.txt", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "20 KB"},
    {"Carpeta", "fotos", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "—"},
    {"Archivo", "video5.mp4", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "2.3 MB"},
    {"Archivo", "presentación6.pptx", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "1.5 MB"},
    {"Archivo", "documento7.txt", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "18 KB"},
    {"Carpeta", "proyectos", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "—"},
    {"Archivo", "imagen8.png", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "280 KB"},
    {"Archivo", "presentación9.pptx", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "1.1 MB"},
    {"Archivo", "documento10.txt", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "17 KB"},
    {"Carpeta", "música", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "—"},
    {"Archivo", "video11.mp4", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "3.2 MB"},
    {"Archivo", "presentación12.pptx", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "2.0 MB"},
    {"Archivo", "documento13.txt", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "16 KB"},
    {"Carpeta", "proyectos", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "—"},
    {"Archivo", "imagen14.png", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "320 KB"},
    {"Archivo", "presentación15.pptx", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "1.8 MB"},
    {"Archivo", "documento16.txt", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "14 KB"},
    {"Carpeta", "videos", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "—"},
    {"Archivo", "video17.mp4", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "4.5 MB"},
    {"Archivo", "presentación18.pptx", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "1.3 MB"},
    {"Archivo", "documento19.txt", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "19 KB"},
    {"Carpeta", "imágenes", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "—"},
    {"Archivo", "imagen20.png", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "350 KB"},
    {"Archivo", "presentación21.pptx", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "1.9 MB"},
    {"Archivo", "documento22.txt", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "12 KB"},
    {"Carpeta", "documentos", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "—"},
    {"Archivo", "video23.mp4", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "2.8 MB"},
    {"Archivo", "presentación24.pptx", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "2.2 MB"},
    {"Archivo", "documento25.txt", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "21 KB"},
    {"Carpeta", "archivos", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "—"},
    {"Archivo", "imagen26.png", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "380 KB"},
    {"Archivo", "presentación27.pptx", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "1.7 MB"},
    {"Archivo", "documento28.txt", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "13 KB"},
    {"Carpeta", "descargas", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "—"},
    {"Archivo", "video29.mp4", new SimpleDateFormat("dd/MM/yyyy").format(new Date()), "3.5 MB"}
    
        };
        
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer todas las celdas no editables
            }
        };
         
        JTable table = new JTable(model);
        
        // Quitar las líneas de la cuadrícula
        table.setShowGrid(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        DefaultTableCellRenderer defaultRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                } else {
                    c.setBackground(table.getBackground());
                    c.setForeground(table.getForeground());
                }
                return c;
            }
        };

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(defaultRenderer);
        }

        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // Tipo
        table.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);   // Nombre
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Fecha
        table.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);  // Tamaño

        table.getColumnModel().getColumn(0).setPreferredWidth(10); // Tipo
        table.getColumnModel().getColumn(1).setPreferredWidth(300); // Nombre
        table.getColumnModel().getColumn(2).setPreferredWidth(50); // Fecha
        table.getColumnModel().getColumn(3).setPreferredWidth(50); // Tamaño
        
        JScrollPane scrollPane = new JScrollPane(table);
        
        JPopupMenu popupMenu = new JPopupMenu();

        JMenu menu = new JMenu("Nuevo");

        JMenuItem item0 = new JMenuItem("Ver");
        JMenuItem nuevaCarpetaItem = new JMenuItem("Carpeta");
        JMenuItem nuevoArchivoItem = new JMenuItem("Archivo");
        JMenuItem propiedades = new JMenuItem("Propiedades");

        menu.add(nuevaCarpetaItem);
        menu.add(nuevoArchivoItem);

        popupMenu.add(item0);
        popupMenu.add(menu);
        popupMenu.add(new JSeparator());
        popupMenu.add(propiedades);

        panelCenter.setComponentPopupMenu(popupMenu);
        panelCenter.add(scrollPane, BorderLayout.CENTER);

        panelCenter.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    showPopupMenu(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    showPopupMenu(e);
                }
            }

            private void showPopupMenu(MouseEvent e)
            {
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
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
    }

    private void initPanelSouth()
    {
        panelSouth = new JPanel();
        panelSouth.setBackground(Color.WHITE);
    }
}
