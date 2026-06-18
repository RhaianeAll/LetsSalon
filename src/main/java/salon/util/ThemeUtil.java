package salon.util;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import java.awt.Color;

public class ThemeUtil {
    public static void setupTheme() {
        FlatLightLaf.setup();
        // Cores baseadas no protótipo Let's Salon
        UIManager.put("Component.accentColor", Color.decode("#F48FB1")); // Rosa principal
        UIManager.put("Button.background", Color.decode("#F48FB1"));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.arc", 20); // Bordas arredondadas globais
        UIManager.put("TextComponent.arc", 15);
        UIManager.put("Panel.background", Color.WHITE);
        
        // Fontes modernas
        UIManager.put("defaultFont", new java.awt.Font("Poppins", java.awt.Font.PLAIN, 14));
    }
}