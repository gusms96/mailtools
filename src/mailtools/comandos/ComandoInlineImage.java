/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailtools.comandos;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author guga
 */
public class ComandoInlineImage extends Comando {

    public ComandoInlineImage() {
        super();
    }

    @Override
    public void executar() {
        try {

            Message message = new MimeMessage(sessao.session);
            message.setFrom(new InternetAddress(sessao.from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sessao.to));
            message.setSubject("MailTools - Java/JavaMail");

            MimeMultipart multipart = new MimeMultipart("related");
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlText = "<H1>imagem</H1><img src=\"cid:image\">";
            messageBodyPart.setContent(htmlText, "text/html");
            multipart.addBodyPart(messageBodyPart);
            
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource("C:\\temp\\n.jpg");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
            Transport.send(message);
            
            System.out.println("...Success");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
