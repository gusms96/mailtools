/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailtools.comandos;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Store;

/**
 *
 * @author guga
 */
public class ComandoLerInbox extends Comando {

    public ComandoLerInbox() {
        super();
    }

    @Override
    public void executar() {
        try {
            Store s = sessao.session.getStore("imap");
            s.connect(sessao.host, sessao.user, sessao.password);

            Folder emailFolder = s.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] mensagens = emailFolder.getMessages();
            System.out.println("mensagens.length: " + mensagens.length);

            for (int i = mensagens.length-1, n = 0; i > n; i--) {
                Message message = mensagens[i];
                System.out.println("---------------------------------");
                System.out.println("Email Number " + (i + 1));
                System.out.println("Date: "+message.getReceivedDate());
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());
            }

            emailFolder.close(false);
            s.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
