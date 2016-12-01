
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.CatalogoDAO;
import br.edu.ifsul.modelo.Catalogo;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilMensagens;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


@ManagedBean(name = "controleCatalogo")
@SessionScoped
public class ControleCatalogo implements Serializable {

    private CatalogoDAO<Catalogo> dao;
    private Catalogo objeto;
    
    public ControleCatalogo(){
        dao = new CatalogoDAO<>();
    }
    
    public String listar(){
        return "/privado/catalogo/listar?faces-redirect=true";
    }
    
    public String novo(){
        objeto = new Catalogo();
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

    public CatalogoDAO getDao() {
        return dao;
    }

    public void setDao(CatalogoDAO dao) {
        this.dao = dao;
    }

    public Catalogo getObjeto() {
        return objeto;
    }

    public void setObjeto(Catalogo objeto) {
        this.objeto = objeto;
    }
    
}
