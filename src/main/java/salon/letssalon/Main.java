package salon.letssalon;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;

public class Main {
    
    public static void main(String[] args) {
        
        // 1. Configurar o FlatLaf ANTES de abrir as telas
        try {
            // Aplica o tema claro do FlatLaf
            UIManager.setLookAndFeel(new FlatLightLaf());
            
            // Aqui você já pode injetar as cores base do seu design
            UIManager.put("Component.accentColor", java.awt.Color.decode("#F48FB1")); // Rosa
            UIManager.put("Button.arc", 20); // Bordas arredondadas nos botões
            UIManager.put("TextComponent.arc", 15); // Bordas arredondadas nos campos de texto
            
        } catch (Exception ex) {
            System.err.println("Falha ao inicializar o tema FlatLaf: " + ex.getMessage());
        }

        // 2. Iniciar a interface gráfica na Thread correta do Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Substitua pela sua tela inicial de login
                // new salon.view.LoginView().setVisible(true);
            }
        });
    }
}