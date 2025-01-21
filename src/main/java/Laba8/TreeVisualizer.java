package Laba8;

import javax.swing.*;
import java.awt.*;

public class TreeVisualizer extends JFrame {
    private Node root;

    public TreeVisualizer(Node root) {
        this.root = root;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 1000);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (root != null) {
            drawTree(g, root, getWidth() / 2, 50, 3000);
        }
    }

    private void drawTree(Graphics g, Node node, int x, int y, int horizontalSpacing) {
        g.drawString(node.data, x, y);

        if (node.nodes != null) {
            int childCount = node.nodes.size();
            int startX = x - (childCount - 1) * horizontalSpacing / 2;
            int childY = y + 30;

            for (Node child : node.nodes) {
                g.drawLine(x, y, startX, childY);
                drawTree(g, child, startX, childY, horizontalSpacing / 2);
                startX += horizontalSpacing;
            }

        }
    }
}