import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class DrawPad extends Frame {
    private final ArrayList<Point> points = new ArrayList<>();
    private final Label clickCountLabel = new Label("Click Count: 0");
    private final Button clearButton = new Button("Clear");

    public DrawPad() {
        super("DrawPad");
        setSize(500, 400);
        setLocation(100, 100);
        setLayout(null);
        setBackground(Color.WHITE);

        clickCountLabel.setBounds(20, 20, 150, 25);
        clearButton.setBounds(200, 20, 80, 25);
        add(clickCountLabel);
        add(clearButton);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                points.add(e.getPoint());
                clickCountLabel.setText("Click Count: " + points.size());
                repaint();
            }
        });

        clearButton.addActionListener(e -> {
            points.clear();
            clickCountLabel.setText("Click Count: 0");
            repaint();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (Point point : points) {
            g.setColor(Color.BLACK);
            g.fillOval(point.x - 3, point.y - 3, 6, 6);
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            DrawPad pad = new DrawPad();
            pad.setVisible(true);
        });
    }
}
