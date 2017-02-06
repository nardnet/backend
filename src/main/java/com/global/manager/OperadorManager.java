package com.global.manager;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.global.dao.BaseDao;

import br.com.gc.cobranca.Operador;

public class OperadorManager extends BaseDao{
	
    private final String BUSCA_OPERADOR = "SELECT  oper.id,oper.nome,oper.senha,oper.ema_operador FROM operadores  oper WHERE  oper.nome = '&1'";
    private final String BUSCA_OPERADOR_SENHA = "SELECT  oper.id,oper.nome,oper.senha,oper.ema_operador FROM operadores  oper WHERE  oper.nome = '&1' and oper.senha = '&2'"; 		

	public Operador findByUsername(String username) throws SQLException, Exception {
		Operador oper = null;
        ResultSet rset = getConn().execSQL(BUSCA_OPERADOR 
                .replaceAll("&1", username));
        
        while (rset.next()) {
            oper = new Operador(getConn(), rset.getLong("id"));
        }
        getConn().close();
        
        
		return oper;
	}

	
	

	public Operador findByUsernamePassword(String username, String password) throws SQLException, Exception {
		Operador oper = null;
        ResultSet rset = getConn().execSQL(BUSCA_OPERADOR_SENHA 
                .replaceAll("&1", username).replaceAll("&2", password));
        
        while (rset.next()) {
            oper = new Operador(getConn(), rset.getLong("id"));
        }
        getConn().close();
        
        
		return oper;
	}

	
	
 
}
