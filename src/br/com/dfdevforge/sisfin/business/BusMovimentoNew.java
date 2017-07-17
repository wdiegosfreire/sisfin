package br.com.dfdevforge.sisfin.business;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpCompetencia;
import br.com.dfdevforge.sisfin.bean.BtpObjetivo;
import br.com.dfdevforge.sisfin.estabelecimento.bean.BtpEstabelecimento;
import br.com.dfdevforge.sisfin.estabelecimento.persistence.PrsEstabelecimento;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.persistence.PrsObjetivo;
import br.com.dfdevforge.sisfin.persistence.PrsObjetivoSelectParameterized;

public class BusMovimentoNew
{
	private ConnectionManager dbConn;

	public BusMovimentoNew(ConnectionManager dbConn)
	{
		this.dbConn = dbConn;
	}

	public Map<String,List<? extends AbstractBean>> execInsert(BtpCompetencia btpCompetencia)
	{
		return null;
	}

	public Map<String,List<? extends AbstractBean>> showMainPage(BtpCompetencia btpCompetencia) throws SessionUserNotFoundException, SQLException
	{
		Map<String, List<? extends AbstractBean>> mapBeanList = new HashMap<String, List<? extends AbstractBean>>();

		BtpObjetivo btpObjetivo = new BtpObjetivo();
		btpObjetivo.setBtpUsuario(btpCompetencia.getBtpUsuario());

		Map<String, String> map = new HashMap<String, String>();
		map.put("numMes", btpCompetencia.getComDatMes());
		map.put("numAno", btpCompetencia.getComDatAno());

		map.put("movDatVencimentoMin", btpCompetencia.getMap().get("movDatVencimentoMin"));
		map.put("movDatVencimentoMax", btpCompetencia.getMap().get("movDatVencimentoMax"));
		map.put("movDatPagamentoMin", btpCompetencia.getMap().get("movDatPagamentoMin"));
		map.put("movDatPagamentoMax", btpCompetencia.getMap().get("movDatPagamentoMax"));
		map.put("objTxtDescricao", btpCompetencia.getMap().get("objTxtDescricao"));
		map.put("movVlrMovimentoMin", btpCompetencia.getMap().get("movVlrMovimentoMin"));
		map.put("movVlrMovimentoMax", btpCompetencia.getMap().get("movVlrMovimentoMax"));
		map.put("conCodContaOrigem", btpCompetencia.getMap().get("conCodContaOrigem"));
		map.put("conCodContaDestino", btpCompetencia.getMap().get("conCodContaDestino"));
		map.put("estCodEstabelecimento", btpCompetencia.getMap().get("estCodEstabelecimento"));
		map.put("fopCodFormaPagamento", btpCompetencia.getMap().get("fopCodFormaPagamento"));

		btpObjetivo.setMap(map);

		PrsObjetivo prsObjetivo = new PrsObjetivo(this.dbConn);
		mapBeanList.put("btpObjetivoList", prsObjetivo.select(btpObjetivo, 1));

		return mapBeanList;
	}

	public Map<String,List<? extends AbstractBean>> showEditForm(BtpCompetencia btpCompetencia) throws SQLException, SessionUserNotFoundException, Exception
	{
		if (btpCompetencia.getBtpMovimentoList() == null || btpCompetencia.getBtpMovimentoList().size() != 1)
			throw new Exception("A consulta retornou um resultado inv�lido para a regra de edi��o");

		BtpObjetivo btpObjetivo = btpCompetencia.getBtpMovimentoList().get(0).getBtpObjetivo();
		btpObjetivo.setBtpUsuario(btpCompetencia.getBtpUsuario());

//		BtpObjetivo btpObjetivo = (BtpObjetivo) mapBean.get("btpObjetivo");
		Map<String, List<? extends AbstractBean>> mapBeanList = new HashMap<String, List<? extends AbstractBean>>();

		/*
		 * Consultando as informa��es do objetivo selecionado para edi��o. O resultado
		 * desta consulta ser� usado para preencher o formul�rio de edi��o do objetivo.
		 */
		PrsObjetivoSelectParameterized prsObjetivoSelectParameterized = new PrsObjetivoSelectParameterized(dbConn);
		mapBeanList.put("btpObjetivoList", prsObjetivoSelectParameterized.execute(btpObjetivo, 2));

		/*
		 * Consultando a lista de estabelecmentos cadastrados do usu�rio. O
		 * resultado desta consulta ser� usado para preenche o combobox de
		 * estabelecimentos do formul�rio de cadastro/edi��o de objetivos.
		 */
		BtpEstabelecimento btpEstabelecimento = new BtpEstabelecimento();
		btpEstabelecimento.setBtpUsuario(btpObjetivo.getBtpUsuario());

		PrsEstabelecimento prsEstabelecimento = new PrsEstabelecimento(dbConn);
		mapBeanList.put("btpEstabelecimentoList", prsEstabelecimento.select(btpEstabelecimento, 2));	

		return mapBeanList;
	}
}