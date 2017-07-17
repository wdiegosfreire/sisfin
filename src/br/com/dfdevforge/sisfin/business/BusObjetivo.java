package br.com.dfdevforge.sisfin.business;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpMovimento;
import br.com.dfdevforge.sisfin.bean.BtpObjetivo;
import br.com.dfdevforge.sisfin.bean.BtpObjetivoItem;
import br.com.dfdevforge.sisfin.behavior.SelectablePersistence;
import br.com.dfdevforge.sisfin.exception.NullBeanException;
import br.com.dfdevforge.sisfin.exception.RequiredColumnNotFoundException;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.persistence.PrsDeleteObjetivoByPrimaryKey;
import br.com.dfdevforge.sisfin.persistence.PrsObjetivo;
import br.com.dfdevforge.sisfin.persistence.PrsObjetivoSelectParameterized;

public class BusObjetivo
{
	private ConnectionManager ct;

	public BusObjetivo(ConnectionManager ct)
	{
		this.ct = ct;
	}

	public List<BtpObjetivo> cadastrar(AbstractBean to)
	{
		return null;
	}

	public List<BtpObjetivo> editar(BtpObjetivo btpObjetivo) throws SQLException, Exception
	{
		SelectablePersistence<BtpObjetivo> prs = new PrsObjetivoSelectParameterized(ct);
		return prs.execute(btpObjetivo, 2);
	}

	public void excluir(AbstractBean to) throws NullBeanException, RequiredColumnNotFoundException, SQLException, TimezoneValueException
	{
		BtpObjetivo btpObjetivo = (BtpObjetivo) to;

		/*
		 * Excluindo os registros relacionados na tabela obi_objetivo_item
		 */
		BtpObjetivoItem btpObjetivoItem = new BtpObjetivoItem();
		btpObjetivoItem.setBtpObjetivo(btpObjetivo);

		BusObjetivoItem busObjetivoItem = new BusObjetivoItem();
		busObjetivoItem.deleteByObjetivo(btpObjetivoItem, this.ct);

		/*
		 * Excluindo os registros relacionados da tabela mov_movimento
		 */
		BtpMovimento btpMovimento = new BtpMovimento();
		btpMovimento.setBtpObjetivo(btpObjetivo);

		BusMovimento busMovimento = new BusMovimento();
		busMovimento.deleteByObjetivo(btpMovimento, this.ct);

		// Excluindo o registro principal da tabela obj_objetivo
		PrsDeleteObjetivoByPrimaryKey prsDeleteObjetivoByPrimaryKey = new PrsDeleteObjetivoByPrimaryKey(this.ct);
		prsDeleteObjetivoByPrimaryKey.execute(btpObjetivo);

	}

	public List<AbstractBean> exibir(AbstractBean to)
	{
		return null;
	}

	public List<BtpObjetivo> consultar(AbstractBean to, int sqlOrdem) throws SQLException, ParseException, TimezoneValueException
	{
		BtpObjetivo b = (BtpObjetivo) to;

		PrsObjetivo prs = new PrsObjetivo(this.ct);
		List<BtpObjetivo> btpObjetivoList = prs.select(b, Integer.parseInt(b.getMap().get("sqlOrder")));

		return btpObjetivoList;
	}

	/**
	 * Contém a regra de negócio para a inclusão de um movimento completo, ou seja, com os respectivos itens e parcelas.
	 * Esta regra executa os seguintes passos:
	 * <ol>
	 *   <li>Insert na entidade BtpObjetivo</li>
	 *   <li>Insert nas entidades BtpObjetivoItem novas, recebidas do formulario</li>
	 *   <li>Insert nas entidades BtpMovimentoList novas, recebidas do formulario</li>
	 * </ol>
	 * @throws TimezoneValueException
	 * @throws IOException 
	 */
	public void incluir(BtpObjetivo btpObjetivo) throws SQLException, RequiredColumnNotFoundException, NullBeanException, TimezoneValueException, IOException
	{
		Date now = new Date();

		// Passo 1
		PrsObjetivo prsObjetivo = new PrsObjetivo(this.ct);
		prsObjetivo.insert(btpObjetivo);

		// Passo 2
		BusObjetivoItem busObjetivoItem = new BusObjetivoItem();
		for (int i = 0; i < btpObjetivo.getBtpObjetivoItemList().size(); i++)
		{
			btpObjetivo.getBtpObjetivoItemList().get(i).getBtpObjetivo().setObjCodObjetivo(btpObjetivo.getObjCodObjetivo());
			busObjetivoItem.incluir(btpObjetivo.getBtpObjetivoItemList().get(i), this.ct);
		}

		// Passo 3
		BusMovimento busMovimento = new BusMovimento();
		for (int i = 0; i < btpObjetivo.getBtpMovimentoList().size(); i++)
		{
			btpObjetivo.getBtpMovimentoList().get(i).setMovDatRegistro(now);
			btpObjetivo.getBtpMovimentoList().get(i).getBtpObjetivo().setObjCodObjetivo(btpObjetivo.getObjCodObjetivo());
			busMovimento.incluir(btpObjetivo.getBtpMovimentoList().get(i), this.ct);
		}
	}

	/**
	 * Contém a regra de negócio para a alteração de um movimento completo, ou seja, com os respectivos itens e parcelas.
	 * Esta regra executa os seguintes passos:
	 * <ol>
	 *   <li>Update na entidade BtpObjetivo</li>
	 *   <li>Delete nas entidades BtpObjetivoItemList existentes</li>
	 *   <li>Insert nas entidades BtpObjetivoItemList novas, recebidas do formulario</li>
	 *   <li>Delete nas entidades BtpMovimentoList existentes</li>
	 *   <li>Insert nas entidades BtpMovimentoList novas, recebidas do formulario</li>
	 * </ol>
	 * @throws SessionUserNotFoundException
	 * @throws TimezoneValueException
	 * @throws IOException 
	 */
	public void alterar(AbstractBean to) throws NullBeanException, RequiredColumnNotFoundException, SQLException, SessionUserNotFoundException, TimezoneValueException, IOException
	{
		BtpObjetivo btpObjetivo = (BtpObjetivo) to;

		// Passo 1
		PrsObjetivo prsObjetivo = new PrsObjetivo(this.ct);
		prsObjetivo.updateByTable(btpObjetivo);

		// Passo 2
		BtpObjetivoItem btpObjetivoItem = new BtpObjetivoItem();
		BusObjetivoItem busObjetivoItem = new BusObjetivoItem();

		btpObjetivoItem.setBtpObjetivo(btpObjetivo);
		busObjetivoItem.deleteByObjetivo(btpObjetivoItem, this.ct);

		// Passo 3
		for (BtpObjetivoItem btp : btpObjetivo.getBtpObjetivoItemList())
		{
			btp.setBtpObjetivo(btpObjetivo);
			busObjetivoItem.incluir(btp, this.ct);
		}

		// Passo 4
		BtpMovimento btpMovimento = new BtpMovimento();
		BusMovimento busMovimento = new BusMovimento();

		btpMovimento.setBtpObjetivo(btpObjetivo);
		busMovimento.deleteByObjetivo(btpMovimento, this.ct);

		// Passo 5
		for (BtpMovimento btp : btpObjetivo.getBtpMovimentoList())
		{
			btp.setBtpObjetivo(btpObjetivo);
			busMovimento.incluir(btp, this.ct);
		}
	}
}