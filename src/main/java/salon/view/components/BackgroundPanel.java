package salon.view.components;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    private Image image;

    public BackgroundPanel(String imagePath, LayoutManager layout) {
        super(layout);
        // Tenta carregar a imagem da pasta resources
        try {
            java.net.URL imgURL = getClass().getResource(imagePath);
            if (imgURL != null) {
                this.image = new ImageIcon(imgURL).getImage();
            }
        } catch (Exception e) {
            System.err.println("Imagem de fundo não encontrada: " + imagePath);
        }
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Pinta o fundo branco primeiro
        
        if (image != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            // Deixa a imagem levemente transparente (20% de opacidade) para não brigar com os botões
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
            
            // Desenha a imagem centralizada na tela cheia
            int x = (getWidth() - image.getWidth(this)) / 2;
            int y = (getHeight() - image.getHeight(this)) / 2;
            g2d.drawImage(image, x, y, this);
            
            g2d.dispose();
        }
    }
}