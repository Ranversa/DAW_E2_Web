
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.IdiomaDAO;
import br.edu.ifsul.modelo.Idioma;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilMensagens;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "controleIdioma")
@SessionScoped
public class ControleIdioma implements Serializable {

    private IdiomaDAO<Idioma> dao;
    private Idioma objeto;
    
    public ControleIdioma(){
        dao = new IdiomaDAO<>();
    }
    
    public String listar(){
        return "/privado/idioma/listar?faces-redirect=true";
    }
    
    public String novo(){
        objeto = new Idioma();
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

    public IdiomaDAO getDao() {
        return dao;
    }

    public void setDao(IdiomaDAO dao) {
        this.dao = dao;
    }

    public Idioma getObjeto() {
        return objeto;
    }

    public void setObjeto(Idioma objeto) {
        this.objeto = objeto;
    }
    
}
