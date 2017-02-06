package com.global.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import br.com.lapc.exception.ConexaoLAPCException;
import br.com.lapc.fw.DatabaseTable;
import br.com.lapc.fw.ExConn;
import br.com.lapc.util.DataAccessUtil;
import br.com.lapc.util.UserDBEnum;


/**
 *
 * @author joao.carvalho
 */
public class BaseDao {
    
//    private static final Log LOG = LogTarefas.getInstance(BaseDao.class);
    
    protected DateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    final static public int NUM_DE_SERVIDORES = 28; // SÓ MUDAR SE O SISTEMA EM VB TAMBÉM MUDAR!
    
    public static final int MAX_INSERT_QUERY = 500;
    
    protected ExConn conn = null;
    protected ExConn connMSSql = null;
    
    protected boolean showQuery;
    private UserDBEnum exconEspecificoMySql;
    private UserDBEnum exconEspecificoMsSql;
        
    private static final String QUERY_PARAMENTO_CONFIGURACAO = "SELECT nome, valor FROM tb_config WHERE nome LIKE ':nome'" ;
        
    private static final String PARAM_NOME_CONFIGURACAO = ":nome";
    
    public boolean isShowQuery() {
		return showQuery;
	}
    
	public UserDBEnum getExconEspecificoMySql() {
		return exconEspecificoMySql;
	}

	public void setExconEspecificoMySql(UserDBEnum exconEspecifico) {
		this.exconEspecificoMySql = exconEspecifico;
	}
	
	public UserDBEnum getExconEspecificoMsSql() {
		return exconEspecificoMsSql;
	}

	public void setExconEspecificoMsSql(UserDBEnum exconEspecificoMsSql) {
		this.exconEspecificoMsSql = exconEspecificoMsSql;
	}

	public Properties getProperties() throws IOException {
        Properties props = new Properties();
        InputStream file = this.getClass().getClassLoader().getResourceAsStream("configuracoes.properties");
        props.load(file);
        return props;
    }
    
    protected ExConn getExConn() throws ConexaoLAPCException, Exception {
        ExConn ret = null;
        Properties prop = getProperties();
        String ambiente = prop.getProperty("prop.ambiente.execucao");
        
        showQuery = prop.getProperty("prop.exibir.sql") != null && prop.getProperty("prop.exibir.sql").trim().equalsIgnoreCase("true");
        
        if ("teste".equals(ambiente)){
            System.out.println("***** Utilizando banco de dados de testes. *****");
            ret = DataAccessUtil.openConnMySql(UserDBEnum.SERVIDOR_BANCO_MYSQL_DESENV);
        }else{
        	
        	//setExconEspecificoMySql(UserDBEnum.SERVIDOR_BANCO_MYSQL_36_241); //tamoios  -- devido problemas de sincronismo de BD
        	setExconEspecificoMySql(UserDBEnum.SERVIDOR_BANCO_MYSQL_10_206); 
        	
        	if (getExconEspecificoMySql() != null) {
        		ret = DataAccessUtil.openConnMySql(getExconEspecificoMySql());        				
        	} else {
        		ret = DataAccessUtil.criarConexaoTodosBD();
        	}
        	System.out.println("***** Utilizando banco de dados "+ret.getHost()+". *****");
            DatabaseTable.init(ret);
        }
        return ret;
    }
    
    protected static Connection getConnectionAcionSms() throws Exception {
		String driver = "com.mysql.jdbc.Driver";
	    String connection = "jdbc:mysql://192.168.36.250:3306/Sms";
	    String user = "app_sms";
	    String password = "hsuwkk829s";
	    Class.forName(driver);
	    Connection con = DriverManager.getConnection(connection, user, password);
		return con;	
	}
    
    protected ExConn getExConnMSSQL() throws ConexaoLAPCException, Exception {
        ExConn ret = null;
        //Properties prop = getProperties();
        //String ambiente = prop.getProperty("prop.ambiente.execucao");
        if (getExconEspecificoMsSql() != null) {
        	ret = DataAccessUtil.openConnMssql(getExconEspecificoMsSql());
        } else {
        	ret = DataAccessUtil.openConnMssql(UserDBEnum.SERVIDOR_BANCO_MSSQL_36_244);
        }
        
        return ret;
    }
    
    public void executaQry(String query) throws SQLException, Exception{
    	getConn().execSQL(query);
    }
        
    public ExConn getConn() throws Exception {
        if (conn == null || conn.getConection() == null || conn.getConection().isClosed()){
            conn = getExConn();
        } 
        return conn;
    }
    
    public ExConn getConnMSSql() throws Exception {
        if (connMSSql == null){
            connMSSql = getExConnMSSQL();
        } 
        return connMSSql;
    }

    public void setConn(ExConn conn) {
        this.conn = conn;
    }
    
    public void setConnMSSql(ExConn conn) {
        this.connMSSql = conn;
    }
  
    public void closeConn(ExConn conn) {
        conn.close();
        conn = null;
    }
    
    public void closeConnAtiva() {
        if(conn != null){
            conn.close();
            conn = null;
        }
    }
    
    
    public String recuperaParametros(String param) {
		ResultSet rset = null;
		try {
			//LOG.addInfo("===================================================================================");
//			LOG.addInfo("Recupera parametros da base da dados. " + param);
			//LOG.addInfo("===================================================================================");
			rset = getConn().execSQL(QUERY_PARAMENTO_CONFIGURACAO.replaceAll(PARAM_NOME_CONFIGURACAO, param));
			if (rset.next()) {
				return rset.getString("valor");
			}
		} catch (Exception ex) {
//			LOG.addErro("Erro ao tentar buscar parametro: " + param,ex);
		}finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException ex) {
//					LOG.addErro("Erro ao tentar liberar recurso",ex);
				}
			}
			try {
				getExConn().close();
			} catch (Exception ex) {
//				LOG.addErro("Erro ao tentar liberar recurso",ex);
			}
		}
		return "";
    }
        
    protected List<String> getParamentrosConfiguracao(String nome) throws Exception{
        
        ResultSet rset = null;
        
        List<String> configs = new ArrayList<String>();
        
        try{
            rset = conn.execSQL(QUERY_PARAMENTO_CONFIGURACAO.replaceAll(PARAM_NOME_CONFIGURACAO, nome.concat("%")));
            while (rset.next()) {
                configs.add(rset.getString("nome") + "=" + rset.getString("valor"));
            }
        }catch(Exception exception){
            throw new Exception("Erro ao executar query.", exception);
        } 
        finally {
            if (rset != null)
                try {
                    rset.close();
            } catch (SQLException ex) {

            }
        }

        return configs;
    }
    
    /**
     * M�todo respons�vel por gerar um ID �nico.
     * 
     * @return
     */
    protected String gerarGeoId(){
    	UUID uuid = UUID.randomUUID();
		return uuid.toString();
    }
} 
