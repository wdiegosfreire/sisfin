package br.com.dfdevforge.sisfin.business;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpExtrato;
import br.com.dfdevforge.sisfin.bean.BtpObjetivo;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.estabelecimento.persistence.PrsEstabelecimento;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.persistence.PrsObjetivo;
import br.com.dfdevforge.sisfin.persistence.PrsObjetivoSelectParameterized;

@Deprecated
public class BusPainelMovimento extends BusAbstract
{
	public Map<String, List<? extends AbstractBean>> showMainPage(AbstractBean to, ConnectionManager conn) throws SessionUserNotFoundException, SQLException
	{
		BtpObjetivo btpObjetivo = (BtpObjetivo) to;
		Map<String, List<? extends AbstractBean>> mapBeanList = new HashMap<String, List<? extends AbstractBean>>();

		PrsObjetivo prsObjetivo = new PrsObjetivo(conn);
		mapBeanList.put("btpObjetivoList", prsObjetivo.select(btpObjetivo, 1));

		return mapBeanList;
	}

	public Map<String, List<? extends AbstractBean>> exibirFormularioCadastroEdicaoObjetivo(Map<String, AbstractBean> mapBean, ConnectionManager dbConn) throws SQLException, SessionUserNotFoundException
	{
		BtpObjetivo btpObjetivo = (BtpObjetivo) mapBean.get("btpObjetivo");
		Map<String, List<? extends AbstractBean>> mapBeanList = new HashMap<String, List<? extends AbstractBean>>();

		/*
		 * Consultando as informações do objetivo selecionado para edição. O resultado
		 * desta consulta será usado para preencher o formulário de edição do objetivo.
		 */
		PrsObjetivoSelectParameterized prsObjetivoSelectParameterized = new PrsObjetivoSelectParameterized(dbConn);
		mapBeanList.put("btpObjetivoList", prsObjetivoSelectParameterized.execute(btpObjetivo, 2));

		/*
		 * Consultando a lista de estabelecmentos cadastrados do usuário. O
		 * resultado desta consulta será usado para preenche o combobox de
		 * estabelecimentos do formulário de cadastro/edição de objetivos.
		 */
		BtpEstabelecimento btpEstabelecimento = new BtpEstabelecimento();
		btpEstabelecimento.setBtpUsuario(btpObjetivo.getBtpUsuario());

		PrsEstabelecimento prsEstabelecimento = new PrsEstabelecimento(dbConn);
		mapBeanList.put("btpEstabelecimentoList", prsEstabelecimento.select(btpEstabelecimento, 2));

		return mapBeanList;
	}

	public void alterar(AbstractBean to, ConnectionManager conn) {}
	public void excluir(AbstractBean to, ConnectionManager conn) {}
	public void incluir(AbstractBean to, ConnectionManager conn) {}
	public List<BtpExtrato> editar(AbstractBean to, ConnectionManager ct) {return null;}
	public List<BtpExtrato> cadastrar(AbstractBean to, ConnectionManager ct) {return null;}
	public List<AbstractBean> exibir(AbstractBean to, ConnectionManager ct) {return null;}
	public List<BtpExtrato> consultar(AbstractBean to, ConnectionManager conn, int sqlOrdem) {return null;}
}