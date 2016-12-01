
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.LivroDAO;
import br.edu.ifsul.dao.AutorDAO;
import br.edu.ifsul.dao.CatalogoDAO;
import br.edu.ifsul.dao.FormatoDAO;
import br.edu.ifsul.dao.IdiomaDAO;
import br.edu.ifsul.modelo.Autor;
import br.edu.ifsul.modelo.Catalogo;
import br.edu.ifsul.modelo.Formato;
import br.edu.ifsul.modelo.Idioma;
import br.edu.ifsul.modelo.Livro;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilMensagens;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "controleLivro")
@ViewScoped
public class ControleLivro implements Serializable {

    private LivroDAO<Livro> dao;
    private Livro objeto;
    private AutorDAO<Autor> daoAutor;
    private FormatoDAO<Formato> daoFormato;
    private IdiomaDAO<Idioma> daoIdioma;
    private CatalogoDAO<Catalogo> daoCatalogo;
    
    public ControleLivro(){
        dao = new LivroDAO<>();
        daoAutor = new AutorDAO<>();
        daoFormato = new FormatoDAO<>();
        daoIdioma = new IdiomaDAO<>();
        daoCatalogo = new CatalogoDAO<>();
    }
    
    public String listar(){
        return "/privado/livro/listar?faces-redirect=true";
    }
    
    public void novo(){
        objeto = new Livro();        
    }
    
    public void salvar(){
        boolean persistiu;
        if (objeto.getISBN() == null){
            persistiu = dao.persist(objeto);
        } else {
            persistiu = dao.merge(objeto);
        }
        if (persistiu){
            UtilMensagens.mensagemInformacao(dao.getMensagem());            
        } else {
            UtilMensagens.mensagemErro(dao.getMensagem());            
        }        
    }   
    
    public void editar(String isbn){
        try {
            objeto = dao.localizar(isbn);            
        } catch (Exception e){
            UtilMensagens.mensagemErro("Erro ao recuperar objeto: "+Util.getMensagemErro(e));            
        }
    }
    
    public void remover(String isbn){
        objeto = dao.localizar(isbn);
        if (dao.remover(objeto)){
            UtilMensagens.mensagemInformacao(dao.getMensagem());
        } else {
            UtilMensagens.mensagemErro(dao.getMensagem());
        }
    }

    public LivroDAO getDao() {
        return dao;
    }

    public void setDao(LivroDAO dao) {
        this.dao = dao;
    }

    public Livro getObjeto() {
        return objeto;
    }

    public void setObjeto(Livro objeto) {
        this.objeto = objeto;
    }

    public AutorDAO<Autor> getDaoAutor() {
        return daoAutor;
    }

    public void setDaoAutor(AutorDAO<Autor> daoAutor) {
        this.daoAutor = daoAutor;
    }

    public FormatoDAO<Formato> getDaoFormato() {
        return daoFormato;
    }

    public void setDaoFormato(FormatoDAO<Formato> daoFormato) {
        this.daoFormato = daoFormato;
    }

    public IdiomaDAO<Idioma> getDaoIdioma() {
        return daoIdioma;
    }

    public void setDaoIdioma(IdiomaDAO<Idioma> daoIdioma) {
        this.daoIdioma = daoIdioma;
    }

    public CatalogoDAO<Catalogo> getDaoCatalogo() {
        return daoCatalogo;
    }

    public void setDaoCatalogo(CatalogoDAO<Catalogo> daoCatalogo) {
        this.daoCatalogo = daoCatalogo;
    }
    
}
