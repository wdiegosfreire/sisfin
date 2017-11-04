package br.com.dfdevforge.sisfin.persistence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpTemplateRegra;
import br.com.dfdevforge.sisfin.behavior.SelectablePersistence;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.util.Utils;

public class PrsSelectTemplateRegraParameterized extends PrsAbstract implements SelectablePersistence<BtpTemplateRegra>
{
	public PrsSelectTemplateRegraParameterized(ConnectionManager conn) throws TimezoneValueException, SQLException
	{
		this.connectionManager = conn;
		isTimezoneCorrect();
	}

	public List<BtpTemplateRegra> execute(BtpTemplateRegra to, Integer sqlOrder) throws SessionUserNotFoundException, SQLException
	{
		List<BtpTemplateRegra> btpTemplateRegraList = null;
		StringBuilder cond = new StringBuilder();

		if (to != null)
		{
			if (Utils.hasValue(to.getBtpTemplate().getBtpUsuario().getUsuCodUsuario()))
				cond.append(" and tem.usu_cod_usuario = " + to.getBtpTemplate().getBtpUsuario().getUsuCodUsuario() + " ");
			else
				throw new SessionUserNotFoundException();

			if (Utils.hasValue(to.getTerCodTemplateRegra()))
				cond.append(" and ter.ter_cod_template_regra = " + to.getTerCodTemplateRegra() + " ");
			if (Utils.hasValue(to.getBtpTemplate().getTemCodTemplate()))
				cond.append(" and ter.tem_cod_template = " + to.getBtpTemplate().getTemCodTemplate() + " ");
		}

		if (cond.length() > 4)
			cond.replace(0, 4, " where ");

		String order = "";
		if (sqlOrder == 1)
			order = " order by ter.ter_cod_template_regra ";

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("  ter.ter_cod_template_regra ter_cod_template_regra, ");
		sql.append("  ter.ter_cod_valor_associado ter_cod_valor_associado, ");

		sql.append("  tem.tem_cod_template tem_cod_template, ");
		sql.append("  tem.tem_txt_nome tem_txt_nome, ");

		sql.append("  reg.reg_cod_regra reg_cod_regra, ");
		sql.append("  reg.reg_txt_descricao reg_txt_descricao, ");
		sql.append("  reg.reg_txt_objeto_html reg_txt_objeto_html, ");
		sql.append("  reg.reg_txt_evento_html reg_txt_evento_html, ");
		sql.append("  reg.reg_txt_nome_funcao reg_txt_nome_funcao ,");
		sql.append("  reg.reg_flg_valor_associado reg_flg_valor_associado ");
		sql.append("from ");
		sql.append("  ter_template_regra ter inner join ");
		sql.append("  tem_template tem on ter.tem_cod_template = tem.tem_cod_template inner join ");
		sql.append("  reg_regra reg on ter.reg_cod_regra = reg.reg_cod_regra ");
		sql.append( cond);
		sql.append( order);

		this.connectionManager.statementExecuteQuery(sql.toString());		

		btpTemplateRegraList = new ArrayList<BtpTemplateRegra>();

		while (this.connectionManager.getResultSet().next())
		{
			BtpTemplateRegra btp = new BtpTemplateRegra();

			btp.setTerCodTemplateRegra(this.connectionManager.getResultSet().getInt("ter_cod_template_regra"));
			btp.setTerCodValorAssociado(this.connectionManager.getResultSet().getInt("ter_cod_valor_associado"));

			btp.getBtpTemplate().setTemCodTemplate(this.connectionManager.getResultSet().getInt("tem_cod_template"));
			btp.getBtpTemplate().setTemTxtNome(this.connectionManager.getResultSet().getString("tem_txt_nome"));

			btp.getBtpRegra().setRegCodRegra(this.connectionManager.getResultSet().getInt("reg_cod_regra"));
			btp.getBtpRegra().setRegTxtDescricao(this.connectionManager.getResultSet().getString("reg_txt_descricao"));
			btp.getBtpRegra().setRegTxtObjetoHtml(this.connectionManager.getResultSet().getString("reg_txt_objeto_html"));
			btp.getBtpRegra().setRegTxtEventoHtml(this.connectionManager.getResultSet().getString("reg_txt_evento_html"));
			btp.getBtpRegra().setRegTxtNomeFuncao(this.connectionManager.getResultSet().getString("reg_txt_nome_funcao"));
			btp.getBtpRegra().setRegFlgValorAssociado(this.connectionManager.getResultSet().getBoolean("reg_flg_valor_associado"));

			btpTemplateRegraList.add(btp);
		}

		return btpTemplateRegraList;
	}
}