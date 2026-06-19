package salon.util;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ImageUtil {

    // Converte o arquivo selecionado no JFileChooser para Array de Bytes (para salvar no Banco)
    public static byte[] fileToBytes(File file) {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buf = new byte[1024];
            int readNum;
            while ((readNum = fis.read(buf)) != -1) {
                bos.write(buf, 0, readNum);
            }
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Converte o Array de Bytes do Banco para ImageIcon redimensionado (para exibir na Tela)
    public static ImageIcon bytesToImage(byte[] bytes, int width, int height) {
        if (bytes == null || bytes.length == 0) {
            return null; // Pode retornar um ícone avatar padrão aqui
        }
        ImageIcon icon = new ImageIcon(bytes);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(newImg);
    }
}