
package br.edu.ifsul.converter;

import br.edu.ifsul.jpa.EntityManagerUtil;
import br.edu.ifsul.modelo.Autor;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@FacesConverter(value = "converterAutor")
public class ConverterAutor implements Converter, Serializable {

    // converte da tela para o objeto o parametro string é o valor que vem da tela
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
        if (string == null || string.equals("Selecione um registro")){
            return null;
        }
        return EntityManagerUtil.getEntityManager().find(Autor.class, Integer.parseInt(string));
    }

    // converte do objeto para a tela
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        if (o == null){
            return null;
        }
        Autor obj = (Autor) o;
        return obj.getId().toString();
    }

}
