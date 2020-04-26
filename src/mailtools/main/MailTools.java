package mailtools.main;

import java.util.Scanner;
import mailtools.comandos.ComandoBuilder;

public class MailTools {

    public static void main(String[] args) {
        String comando;
        Scanner s = new Scanner(System.in);
        
        System.out.println("Digite o numero referente ao comando:");
        System.out.println("1 - email sem anexo");
        System.out.println("2 - email com anexo");
        System.out.println("3 - email com html");
        System.out.println("4 - email com imagem no conteúdo");
        System.out.println("5 - listar inbox");
        System.out.println("6 - listar inbox com detalhes");
        
        while(!(comando = s.nextLine()).equals("sair")){ 
            System.out.println("Processando...");
            mailtools.comandos.Comando c = ComandoBuilder.build(comando);
            c.executar();
        }
    }

}
