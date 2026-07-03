package salon.util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {

    // COLOQUE AQUI O E-MAIL DO SALÃO E A SENHA DE APP DE 16 DÍGITOS DO GOOGLE
    private static final String REMETENTE = "letssalon@gmail.com"; 
    private static final String SENHA_APP = "vlih datq qlzg umus";

    public static void enviarEmailRecuperacao(String destinatario, String novaSenha) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(REMETENTE, SENHA_APP);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(REMETENTE));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject("Let's Salon - Recuperação de Senha");
        
        // O corpo do e-mail em HTML (Igual você pediu!)
        String conteudo = "<div style='font-family: Arial, sans-serif; color: #333;'>"
                        + "<h2 style='color: #D81B60;'>Let's Salon 🌿</h2>"
                        + "<p>Olá! Recebemos uma solicitação de recuperação de senha para a sua conta.</p>"
                        + "<p>A sua <b>senha provisória</b> gerada pelo sistema é:</p>"
                        + "<h3 style='background-color: #F5F5F5; padding: 10px; display: inline-block; border-radius: 5px; color: #FF6090;'>" + novaSenha + "</h3>"
                        + "<p><i>Após a entrada no sistema, certifique-se de alterar sua senha para uma de sua preferência no menu de edição de dados (Meu Perfil).</i></p>"
                        + "<br><p>Atenciosamente,<br><b>Equipe Let's Salon</b></p>"
                        + "</div>";

        message.setContent(conteudo, "text/html; charset=utf-8");
        Transport.send(message);
    }
}