
package br.edu.ifsul.controle;

import br.edu.ifsul.dao.UsuarioDAO;
import br.edu.ifsul.modelo.AcessoUsuario;
import br.edu.ifsul.modelo.Usuario;
import br.edu.ifsul.util.UtilMensagens;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


@ManagedBean(name = "controleLogin")
@SessionScoped
public class ControleLogin implements Serializable {
    
    private UsuarioDAO<Usuario> dao;
    private Usuario usuarioLogado;
    private String usuario;
    private String senha;
    
    public ControleLogin(){
        dao = new UsuarioDAO<>();
    }
    
    public String irPaginaLogin(){
        return "login?faces-redirect=true";
    }
    
    public String efetuarLogin(){
        if(dao.login(usuario, senha)){
            usuarioLogado = dao.localizaPorApelido(usuario);
            // capturando o contexto do faces para recuperar o ip remoto do usuário que logu
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest)
                    context.getExternalContext().getRequest();
            ///request.getRemoteAddr() retorno o ip da maquina usada para fazer o login
            AcessoUsuario acesso = new AcessoUsuario(request.getRemoteAddr());
            usuarioLogado.adicionarAcesso(acesso);
            try {
                dao.merge(usuarioLogado);
            } catch (Exception e){
                UtilMensagens.mensagemErro("Erro ao salvar acesso: "+e.getMessage());
            }
            UtilMensagens.mensagemInformacao("Login efetuado com sucesso!");
            return "/index";
        } else{
            UtilMensagens.mensagemErro("Usuário ou senha inválidos!");
            return "/login";
        }
    }
    
    public String efetuarLogout(){
        usuarioLogado = null;
        UtilMensagens.mensagemInformacao("Logout efetuado com sucesso!");
        return "/index";
    }

    public UsuarioDAO<Usuario> getDao() {
        return dao;
    }

    public void setDao(UsuarioDAO<Usuario> dao) {
        this.dao = dao;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
