package controlador;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Alfred
 */
public class TreeCellRendererEdit extends DefaultTreeCellRenderer
{

    private final Icon rootIcon;

    public TreeCellRendererEdit()
    {
        // Cargar el ícono personalizado
        rootIcon = new ImageIcon("src/vista/imagenes/boton-de-inicio1.png");
        setOpenIcon(new ImageIcon("src/vista/imagenes/carpeta1.png"));
        setClosedIcon(new ImageIcon("src/vista/imagenes/carpeta1.png"));
        setLeafIcon(new ImageIcon("src/vista/imagenes/carpeta1.png"));
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded,
            boolean leaf, int row, boolean hasFocus)
    {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        // Si el nodo es la raíz, cambiar el ícono
        if (row == 0)
        {
            setIcon(rootIcon);
        }

        return this;
    }
}
