/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailtools.comandos;

import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author guga
 */
public class ComandoSimpleEmail extends Comando {

    public ComandoSimpleEmail(){
        super();
    }
    
    @Override
    public void executar() {

        try {

            Message message = new MimeMessage(sessao.session);
            message.setFrom(new InternetAddress(sessao.from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sessao.to));
            message.setSubject("\"MailTools - Java/JavaMail\"");
            message.setText("Mensagem sem anexo.");

            Transport.send(message);

            System.out.println("...Success");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
