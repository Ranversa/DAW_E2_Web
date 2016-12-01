
package br.edu.ifsul.filtros;

import br.edu.ifsul.controle.ControleLogin;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebFilter(urlPatterns = "/privado/*")
public class FiltroSeguranca implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpresponse = (HttpServletResponse) response;
        HttpSession sessao = httpRequest.getSession();
        String contextPath = httpRequest.getContextPath();
        ControleLogin controleLogin = (ControleLogin) sessao.getAttribute("controleLogin");
        // verificar se o controle ou usuário logado é nulo
        if(controleLogin == null || controleLogin.getUsuarioLogado() == null){
            // se sim não responde a requisição do usuario e vai para a pagina de login
            httpresponse.sendRedirect(contextPath + "/login.xhtml");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }

}
