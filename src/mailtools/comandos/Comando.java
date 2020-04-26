/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailtools.comandos;

import mailtools.singleton.Sessao;

/**
 *
 * @author guga
 */
public abstract class Comando {
    
    protected Sessao sessao;
    
    public Comando(){
        sessao = Sessao.Sessao();
    }
    
    public abstract void executar();
    
}
