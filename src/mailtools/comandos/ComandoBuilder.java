/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailtools.comandos;

/**
 *
 * @author guga
 */
public class ComandoBuilder {
    
    public static Comando build(String comando){
        if(comando.equals("1")){
            return new ComandoSimpleEmail();
        }else if(comando.equals("2")){
            return new ComandoAttachment();
        }else if(comando.equals("3")){
            return new ComandoHtmlContent();
        }else if(comando.equals("4")){
            return new ComandoInlineImage();
        }else if(comando.equals("5")){
            return new ComandoLerInbox();
        }else if(comando.equals("6")){
            return new ComandoLerDetalhesInbox();
        }else{
            System.out.println("Comando inválido");
            return null;
        }
    }
    
}
