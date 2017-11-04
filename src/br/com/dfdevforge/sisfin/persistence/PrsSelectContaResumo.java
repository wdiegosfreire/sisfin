package br.com.dfdevforge.sisfin.persistence;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpConta;
import br.com.dfdevforge.sisfin.behavior.SelectablePersistence;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsSelectContaResumo extends PrsAbstract implements SelectablePersistence<BtpConta>
{
	public PrsSelectContaResumo(ConnectionManager conn) throws SQLException
	{
		this.connectionManager = conn;
	}

	public List<BtpConta> execute(BtpConta btpConta, Integer sqlOrder) throws SQLException, SessionUserNotFoundException
	{
		List<BtpConta> btpContaList = null;
		StringBuilder cond = new StringBuilder();

		if (btpConta != null)
		{
			if (!Utils.hasValue(btpConta.getBtpUsuario().getUsuCodUsuario()))
				throw new SessionUserNotFoundException();

			cond.append(" and cdes.usu_cod_usuario = " + btpConta.getBtpUsuario().getUsuCodUsuario() + " ");
			cond.append(" and date_format(mov.MOV_DAT_PAGAMENTO, '%m/%Y') = '" + btpConta.getMap().get("numMes") + "/" + btpConta.getMap().get("numAno") + "' ");
			cond.append(" and cdes.CON_NUM_NIVEL like '01%'  ");
		}

		if (cond.length() > 4)
			cond.replace(0, 4, " where ");

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("  cdes.CON_COD_CONTA, ");
		sql.append("  cdes.CON_TXT_DESCRICAO, ");
		sql.append("  ( ");
		sql.append("    select sum(mov.MOV_VLR_MOVIMENTO) AUX_VLR_ANTERIOR ");
		sql.append("    from mov_movimento mov inner join con_conta cdes_i on mov.CON_COD_CONTA_DESTINO = cdes_i.CON_COD_CONTA ");
		sql.append("    where cdes_i.usu_cod_usuario = 2 and mov.CON_COD_CONTA_ORIGEM = mov.CON_COD_CONTA_DESTINO and date_format(mov.MOV_DAT_PAGAMENTO, '%m/%Y') = '" + btpConta.getMap().get("numMes") + "/" + btpConta.getMap().get("numAno") + "' and cdes_i.CON_NUM_NIVEL like '01%' and cdes_i.CON_COD_CONTA = cdes.CON_COD_CONTA ");
		sql.append("  ) AUX_VLR_ANTERIOR, ");
		sql.append("  ( ");
		sql.append("    select sum(mov.MOV_VLR_MOVIMENTO) AUX_VLR_ENTRADA ");
		sql.append("    from mov_movimento mov inner join con_conta cdes_i on mov.CON_COD_CONTA_DESTINO = cdes_i.CON_COD_CONTA ");
		sql.append("    where cdes_i.usu_cod_usuario = 2 and mov.CON_COD_CONTA_ORIGEM <> mov.CON_COD_CONTA_DESTINO and date_format(mov.MOV_DAT_PAGAMENTO, '%m/%Y') = '" + btpConta.getMap().get("numMes") + "/" + btpConta.getMap().get("numAno") + "' and cdes_i.CON_NUM_NIVEL like '01%' and cdes_i.CON_COD_CONTA = cdes.CON_COD_CONTA ");
		sql.append("  ) AUX_VLR_ENTRADA, ");
		sql.append("  ( ");
		sql.append("    select sum(mov.MOV_VLR_MOVIMENTO) AUX_VLR_ENTRADA ");
		sql.append("    from mov_movimento mov inner join con_conta cori_i on mov.CON_COD_CONTA_ORIGEM = cori_i.CON_COD_CONTA ");
		sql.append("    where cori_i.usu_cod_usuario = 2 and mov.CON_COD_CONTA_ORIGEM <> mov.CON_COD_CONTA_DESTINO and date_format(mov.MOV_DAT_PAGAMENTO, '%m/%Y') = '" + btpConta.getMap().get("numMes") + "/" + btpConta.getMap().get("numAno") + "' and cori_i.CON_NUM_NIVEL like '01%' and cori_i.CON_COD_CONTA = cdes.CON_COD_CONTA ");
		sql.append("  ) AUX_VLR_SAIDA ");
		sql.append("from ");
		sql.append("  mov_movimento mov inner join ");
		sql.append("  con_conta cdes on mov.CON_COD_CONTA_DESTINO = cdes.CON_COD_CONTA ");
		sql.append( cond);
		sql.append("group by ");
		sql.append("  cdes.CON_TXT_DESCRICAO ");

		this.connectionManager.statementExecuteQuery(sql.toString());

		btpContaList = new ArrayList<BtpConta>();

		while (this.connectionManager.getResultSet().next())
		{
			BtpConta btp = new BtpConta();

			btp.setConCodConta(this.connectionManager.getResultSet().getInt("con_cod_conta"));
			btp.setConTxtDescricao(this.connectionManager.getResultSet().getString("con_txt_descricao"));

			BigDecimal anterior = (this.connectionManager.getResultSet().getBigDecimal("AUX_VLR_ANTERIOR") != null ? this.connectionManager.getResultSet().getBigDecimal("AUX_VLR_ANTERIOR") : new BigDecimal(0));
			BigDecimal entrada = (this.connectionManager.getResultSet().getBigDecimal("AUX_VLR_ENTRADA") != null ? this.connectionManager.getResultSet().getBigDecimal("AUX_VLR_ENTRADA") : new BigDecimal(0));
			BigDecimal saida = (this.connectionManager.getResultSet().getBigDecimal("AUX_VLR_SAIDA") != null ? this.connectionManager.getResultSet().getBigDecimal("AUX_VLR_SAIDA") : new BigDecimal(0));
			BigDecimal economia = entrada.subtract(saida);
			BigDecimal acumulado = anterior.add(entrada).subtract(saida);

			btp.getMap().put("auxVlrAnterior", anterior.toString());
			btp.getMap().put("auxVlrEntrada", entrada.toString());
			btp.getMap().put("auxVlrSaida", saida.toString());
			btp.getMap().put("auxVlrEconomia", economia.toString());
			btp.getMap().put("auxVlrAcumulado", acumulado.toString());

			btpContaList.add(btp);
		}

		return btpContaList;
	}
}