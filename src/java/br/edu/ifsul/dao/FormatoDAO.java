package br.edu.ifsul.dao;

import br.edu.ifsul.modelo.Formato;
import java.io.Serializable;


public class FormatoDAO<T> extends DAOGenerico<Formato> implements Serializable {

   public FormatoDAO(){
       super();
       super.setClassePersistente(Formato.class);
   }
}
