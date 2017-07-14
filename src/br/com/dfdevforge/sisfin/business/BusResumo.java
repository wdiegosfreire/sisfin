package br.com.dfdevforge.sisfin.business;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.cagece.core.bean.AbstractBean;
import br.com.cagece.core.persistence.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpCompetencia;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.persistence.PrsSelectContaGroupingTotal;
import br.com.dfdevforge.sisfin.persistence.PrsSelectContaLinhaTempo;
import br.com.dfdevforge.sisfin.persistence.PrsSelectContaResumo;
import br.com.dfdevforge.sisfin.util.Utils;

public class BusResumo
{
	public Map<String,List<? extends AbstractBean>> showMainPage(BtpCompetencia btpCompetencia, ConnectionManager conn) throws SessionUserNotFoundException, SQLException
	{
		Map<String, List<? extends AbstractBean>> mapBean = new HashMap<String, List<? extends AbstractBean>>();

		BtpConta btpConta = new BtpConta();
		btpConta.setBtpUsuario(btpCompetencia.getBtpUsuario());

		Map<String, String> map = new HashMap<String, String>();
		map.put("numMes", btpCompetencia.getComDatMes());
		map.put("numAno", btpCompetencia.getComDatAno());

		btpConta.setMap(map);

		/*
		 * Executando a consulta responsável por alimentar a tabela "Resumo Mensal por Conta"
		 */
		PrsSelectContaResumo prsSelectContaResumo = new PrsSelectContaResumo(conn);
		mapBean.put("btpContaList", prsSelectContaResumo.execute(btpConta, null));

		/*
		 * Executando a consulta responsável por alimentar o gráfico "Despesas/Categora N2"
		 */
		PrsSelectContaGroupingTotal prsSelectContaGroupingTotal = new PrsSelectContaGroupingTotal(conn);
		mapBean.put("btpContaListDespesaCategoriaGraph", prsSelectContaGroupingTotal.execute(btpConta, 0));

		/*
		 * Executando a consulta responsável por alimentar o gráfico "Linha do Tempo de Categorias"
		 */
		PrsSelectContaLinhaTempo prsSelectContaLinhaTempo = new PrsSelectContaLinhaTempo(conn);

		if (Utils.hasValue(btpCompetencia.getMap().get("conCodConta")))
		{
			btpConta.setConCodConta(Integer.parseInt(btpCompetencia.getMap().get("conCodConta")));
			mapBean.put("btpContaListLinhaTempoGraph", prsSelectContaLinhaTempo.execute(btpConta, 0));
		}

		return mapBean;
	}
}