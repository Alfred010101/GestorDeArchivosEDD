package controlador;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 *
 * @author Alfred
 */
public class JTextFieldEdit extends JTextField
{

    private Shape shape;
    private Color borderColor = Color.GRAY; 
    private final String texto;
    
    public JTextFieldEdit(int size, String texto)
    {
        super(size);
        this.texto = texto;
        setText(texto);
        setOpaque(false);                                                           // Hacer el JTextField transparente para poder dibujar el borde redondeado
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));  // Añadir un pequeño margen interno

        //Cambiar el borde y fondo cuando el JTextField obtiene el foco
        addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                borderColor = Color.BLUE;
                setBackground(Color.WHITE);
                if (getText().trim().equals(texto))
                {
                    setText("");
                }
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                borderColor = Color.GRAY;
                setBackground(new Color(245, 245, 245));
                if (getText().trim().isBlank())
                {
                    setText(texto);
                }
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y)
    {
        if (shape == null || !shape.getBounds().equals(getBounds()))
        {
            shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15);
        }
        return shape.contains(x, y);
    }
}
