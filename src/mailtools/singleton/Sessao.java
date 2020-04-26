/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailtools.singleton;

import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 *
 * @author guga
 */
public class Sessao {
    
    
    private static Sessao sessao = null;
    
    public Session session;
    public String from = "gustavo@insoft4.com.br";
    public String to = "gstvms96@gmail.com";
    public String user = "gustavo@insoft4.com.br";
    public String password = "Forenez!@#456";
    public String host = "smtp.insoft4.com.br";
    public Integer port = 465;
    
    
    public String popHost = "imap.insoft4.com.br";
    
    private Sessao(){
        
        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.user", from);
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.socketFactory.port", port); //mesma porta para o socket
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        
         session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
         
    }
    
    public static Sessao Sessao(){
        if(sessao == null){
            sessao = new Sessao();
        }
        return sessao;
    }
    
}
