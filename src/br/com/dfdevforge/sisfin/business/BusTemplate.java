package br.com.dfdevforge.sisfin.business;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpTemplate;
import br.com.dfdevforge.sisfin.bean.BtpTemplateRegra;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.persistence.PrsInsertTemplateByTable;
import br.com.dfdevforge.sisfin.persistence.PrsSelectTemplateParameterized;

public class BusTemplate extends BusAbstract
{
	BtpTemplate btpTemplate;

	public List<BtpTemplate> consultar(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws SQLException, SessionUserNotFoundException, TimezoneValueException
	{
		btpTemplate = (BtpTemplate) to;

		PrsSelectTemplateParameterized prs = new PrsSelectTemplateParameterized(conn);
		List<BtpTemplate> btpTemplateList = prs.execute(btpTemplate, sqlOrdem);

		return btpTemplateList;
	}

	public void incluir(AbstractBean to, ConnectionManager conn) throws SQLException, NullBeanException, RequiredColumnNotFoundException, TimezoneValueException, IOException
	{
		btpTemplate = (BtpTemplate) to;

		PrsInsertTemplateByTable prsInsertTemplateByTable = new PrsInsertTemplateByTable(conn);
		prsInsertTemplateByTable.execute(btpTemplate);

		BusTemplateRegra busTemplateRegra = new BusTemplateRegra();
		for (BtpTemplateRegra btpTemplateRegra : btpTemplate.getBtpTemplateRegraList())
		{
			btpTemplateRegra.getBtpTemplate().setTemCodTemplate(btpTemplate.getTemCodTemplate());
			busTemplateRegra.incluir(btpTemplateRegra, conn);
		}
	}

	public void excluir(AbstractBean to, ConnectionManager conn) {}
	public void alterar(AbstractBean to, ConnectionManager conn) {}
	public List<BtpTemplate> editar(AbstractBean to, ConnectionManager conn) {return null;}
	public List<BtpTemplate> exibir(AbstractBean to, ConnectionManager conn) {return null;}
	public List<BtpTemplate> cadastrar(AbstractBean to, ConnectionManager conn) {return null;}
}