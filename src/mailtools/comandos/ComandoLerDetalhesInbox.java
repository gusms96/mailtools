/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailtools.comandos;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author guga
 */
public class ComandoLerDetalhesInbox extends Comando {

    public ComandoLerDetalhesInbox() {
        super();
    }

    @Override
    public void executar() {

        try {

            Store s = sessao.session.getStore("imap");
            s.connect(sessao.host, sessao.user, sessao.password);

            Folder emailFolder = s.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            Message[] messages = emailFolder.getMessages();
            System.out.println("total: " + messages.length);

            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("---------------------------------");
                writePart(message);
                String line = reader.readLine();
                if ("YES".equals(line)) {
                    message.writeTo(System.out);
                } else if ("QUIT".equals(line)) {
                    break;
                }

                emailFolder.close(false);
                s.close();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writePart(Part p) throws Exception {
        if (p instanceof Message) //Call methos writeEnvelope
        {
            writeEnvelope((Message) p);
        }

        System.out.println("----------------------------");
        System.out.println("CONTENT-TYPE: " + p.getContentType());

        //check if the content is plain text
        if (p.isMimeType("text/plain")) {
            System.out.println("This is plain text");
            System.out.println("---------------------------");
            System.out.println((String) p.getContent());
        } //check if the content has attachment
        else if (p.isMimeType("multipart/*")) {
            System.out.println("This is a Multipart");
            System.out.println("---------------------------");
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++) {
                writePart(mp.getBodyPart(i));
            }
        } //check if the content is a nested message
        else if (p.isMimeType("message/rfc822")) {
            System.out.println("This is a Nested Message");
            System.out.println("---------------------------");
            writePart((Part) p.getContent());
        } //check if the content is an inline image
        else if (p.isMimeType("image/jpeg")) {
            System.out.println("--------> image/jpeg");
            Object o = p.getContent();
            
            InputStream x = (InputStream) o;
            int i;
            byte[] bArray = new byte[x.available()];
            // Construct the required byte array
            System.out.println("x.length = " + x.available());
            while ((i = (int) ((InputStream) x).available()) > 0) {
                int result = (int) (((InputStream) x).read(bArray));
                if (result == -1) {
                    i = 0;
                }

                break;
            }
            FileOutputStream f2 = new FileOutputStream("/tmp/image.jpg");
            f2.write(bArray);
        } else if (p.getContentType().contains("image/")) {
            System.out.println("content type" + p.getContentType());
            File f = new File("image" + new Date().getTime() + ".jpg");
            DataOutputStream output = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(f)));
            com.sun.mail.util.BASE64DecoderStream test
                    = (com.sun.mail.util.BASE64DecoderStream) p
                            .getContent();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = test.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } else {
            Object o = p.getContent();
            if (o instanceof String) {
                System.out.println("This is a string");
                System.out.println("---------------------------");
                System.out.println((String) o);
            } else if (o instanceof InputStream) {
                System.out.println("This is just an input stream");
                System.out.println("---------------------------");
                InputStream is = (InputStream) o;
                is = (InputStream) o;
                int c;
                while ((c = is.read()) != -1) {
                    System.out.write(c);
                }
            } else {
                System.out.println("This is an unknown type");
                System.out.println("---------------------------");
                System.out.println(o.toString());
            }
        }

    }

    public static void writeEnvelope(Message m) throws Exception {
        System.out.println("This is the message envelope");
        System.out.println("---------------------------");
        Address[] a;

        // FROM
        if ((a = m.getFrom()) != null) {
            for (int j = 0; j < a.length; j++) {
                System.out.println("FROM: " + a[j].toString());
            }
        }

        // TO
        if ((a = m.getRecipients(Message.RecipientType.TO)) != null) {
            for (int j = 0; j < a.length; j++) {
                System.out.println("TO: " + a[j].toString());
            }
        }

        // SUBJECT
        if (m.getSubject() != null) {
            System.out.println("SUBJECT: " + m.getSubject());
        }

    }

}
