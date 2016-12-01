
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.CatalogoDAO;
import br.edu.ifsul.dao.LivrariaDAO;
import br.edu.ifsul.dao.LivroDAO;
import br.edu.ifsul.modelo.Catalogo;
import br.edu.ifsul.modelo.Livraria;
import br.edu.ifsul.modelo.Livro;
import br.edu.ifsul.util.Util;
import br.edu.ifsul.util.UtilMensagens;
import br.edu.ifsul.util.UtilRelatorios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "controleLivraria")
@ViewScoped
public class ControleLivraria implements Serializable {

    private LivrariaDAO<Livraria> dao;
    private Livraria objeto;
    private CatalogoDAO<Catalogo> daoCatalogo;
    private LivroDAO<Livro> daoLivro;
    private Catalogo catalogo;
    private Livro livro;
    private Boolean novoCatalago;
    private Boolean novoLivro;
    
    public ControleLivraria(){
        dao = new LivrariaDAO<>();
        daoCatalogo = new CatalogoDAO<>();
        daoLivro = new LivroDAO<>();
    }
     public void imprimirCatalogo(Integer id){
        objeto = dao.localizar(id);
        List <Livraria> lista = new ArrayList<>();
        lista.add(objeto);
        HashMap parametros = new HashMap();
        UtilRelatorios.imprimeRelatorio("relatorioLivraria", parametros, lista);
    }
    
    public String listar(){
        return "/privado/livraria/listar?faces-redirect=true";
    }
    
    public void novo(){
        objeto = new Livraria();      
    }
    
    public void salvar(){
        boolean persistiu;
        if (objeto.getId() == null){
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
    
    public void editar(Integer id){
        try {
            objeto = dao.localizar(id);            
        } catch (Exception e){
            UtilMensagens.mensagemErro("Erro ao recuperar objeto: "+Util.getMensagemErro(e));            
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
    
    public void novoCatalogo(){
        catalogo = new Catalogo();
        novoCatalago = true;
    }
    
    public void alterarCatalogo(int index){
        catalogo = objeto.getCatalogos().get(index);
        novoCatalago = false;
    }
    
    public void salvarCatalogo(){
        if (novoCatalago){
            objeto.adicionarCatalogo(catalogo);
        }
        UtilMensagens.mensagemInformacao("Item persistido com sucesso!");
    }
    
    public void removerCatalogo(int index){
        objeto.removerCatalogo(index);
        UtilMensagens.mensagemInformacao("Item removido com sucesso!");
    }

    public LivrariaDAO<Livraria> getDao() {
        return dao;
    }

    public void setDao(LivrariaDAO<Livraria> dao) {
        this.dao = dao;
    }

    public Livraria getObjeto() {
        return objeto;
    }

    public void setObjeto(Livraria objeto) {
        this.objeto = objeto;
    }

    public CatalogoDAO<Catalogo> getDaoCatalogo() {
        return daoCatalogo;
    }

    public void setDaoCatalogo(CatalogoDAO<Catalogo> daoCatalogo) {
        this.daoCatalogo = daoCatalogo;
    }

    public LivroDAO<Livro> getDaoLivro() {
        return daoLivro;
    }

    public void setDaoLivro(LivroDAO<Livro> daoLivro) {
        this.daoLivro = daoLivro;
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Boolean getNovoCatalago() {
        return novoCatalago;
    }

    public void setNovoCatalago(Boolean novoCatalago) {
        this.novoCatalago = novoCatalago;
    }

    public Boolean getNovoLivro() {
        return novoLivro;
    }

    public void setNovoLivro(Boolean novoLivro) {
        this.novoLivro = novoLivro;
    }

}
