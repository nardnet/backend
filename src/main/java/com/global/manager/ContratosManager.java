package com.global.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.global.dao.BaseDao;
import com.global.vo.ContratosPublicacoes;

public class ContratosManager extends BaseDao{
	
	
    private final String BUSCA_PUBLICACAO_CONTRATOS =
            "SELECT cn.cod_contrato, n.data AS data_entrada, ne.`dataadd` AS data_inclusao_sistema FROM clienteseventos ne "
            + " JOIN clientesnegs n ON n.id = ne.neg_id "
            + " JOIN clientescontratos cn ON cn.id = n.contrato_id "
            + " WHERE ne.`evento_id` =  89 "
            + " AND ne.`positiva` = 0 "
            + " AND ne.`dataadd` > CURDATE()";

	public List<ContratosPublicacoes> getBuscaContratosPublicacoes() throws SQLException, Exception {
		List<ContratosPublicacoes> contratos = new ArrayList<ContratosPublicacoes>();
		
		
		   ResultSet rset = getConn().execSQL(BUSCA_PUBLICACAO_CONTRATOS);
		
		   while (rset.next()) {
			   ContratosPublicacoes contrato = new ContratosPublicacoes();
	        
			   contrato.setCodigoContrato(rset.getString("cod_contrato"));
			   contrato.setDataEntrada(rset.getString("data_entrada"));
			   contrato.setDataInclusaoSistema(rset.getString("data_inclusao_sistema"));
	     
	            contratos.add(contrato);
	        }
	        getConn().close();
	        return contratos;
		
	}

}
