
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.FormatoDAO;
import br.edu.ifsul.modelo.Formato;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilMensagens;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "controleFormato")
@SessionScoped
public class ControleFormato implements Serializable {

    private FormatoDAO<Formato> dao;
    private Formato objeto;
    
    public ControleFormato(){
        dao = new FormatoDAO<>();
    }
    
    public String listar(){
        return "/privado/formato/listar?faces-redirect=true";
    }
    
    public String novo(){
        objeto = new Formato();
        return "formulario";
    }
    
    public String salvar(){
        boolean persistiu;
        if (objeto.getId() == null){
            persistiu = dao.persist(objeto);
        } else {
            persistiu = dao.merge(objeto);
        }
        if (persistiu){
            UtilMensagens.mensagemInformacao(dao.getMensagem());
            return "listar";
        } else {
            UtilMensagens.mensagemErro(dao.getMensagem());
            return "formulario";
        }        
    }
    
    public String cancelar(){
        return "listar";
    }
    
    public String editar(Integer id){
        try {
            objeto = dao.localizar(id);
            return "formulario";
        } catch (Exception e){
            UtilMensagens.mensagemErro("Erro ao recuperar objeto: "+Util.getMensagemErro(e));
            return "listar";
        }
    }
    
    public void remover(Integer id){
        objeto = dao.localizar(id);
        if (dao.remover(objeto)){
            UtilMensagens.mensagemInformacao(dao.getMensagem());
        } else {
            UtilMensagens.mensagemErro(dao.getMensagem());
        }
    }

    public FormatoDAO getDao() {
        return dao;
    }

    public void setDao(FormatoDAO dao) {
        this.dao = dao;
    }

    public Formato getObjeto() {
        return objeto;
    }

    public void setObjeto(Formato objeto) {
        this.objeto = objeto;
    }
    
}
