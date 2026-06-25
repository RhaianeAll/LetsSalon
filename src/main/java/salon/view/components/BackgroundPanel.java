package salon.view.components;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    private Image image;

    public BackgroundPanel(String imagePath, LayoutManager layout) {
        super(layout);
        setBackground(Color.WHITE);
        
        if (imagePath != null) {
            try {
                java.net.URL imgURL = getClass().getResource(imagePath);
                if (imgURL != null) {
                    this.image = new ImageIcon(imgURL).getImage();
                } else {
                    System.err.println("Imagem de fundo não encontrada no caminho: " + imagePath);
                }
            } catch (Exception e) {
                System.err.println("Erro ao carregar imagem de fundo: " + e.getMessage());
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            // Aumentamos a opacidade de 0.2f para 0.6f (mais visível)
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            
            // Fixado na esquerda (x = 0) e centralizado verticalmente
            int x = 0; 
            int y = (getHeight() - image.getHeight(this)) / 2;
            g2d.drawImage(image, x, y, this);
            
            g2d.dispose();
        }
    }
}