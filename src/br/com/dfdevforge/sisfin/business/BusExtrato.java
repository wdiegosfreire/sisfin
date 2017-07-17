package br.com.dfdevforge.sisfin.business;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.cagece.core.bean.api.AbstractBean;
import br.com.cagece.core.persistence.api.ConnectionManager;
import br.com.dfdevforge.sisfin.bean.BtpBanco;
import br.com.dfdevforge.sisfin.bean.BtpExtrato;
import br.com.dfdevforge.sisfin.bean.BtpItemExtrato;
import br.com.dfdevforge.sisfin.bean.BtpTipoExtrato;
import br.com.dfdevforge.sisfin.constants.EnumTipoExtrato;
import br.com.dfdevforge.sisfin.exception.SessionUserNotFoundException;
import br.com.dfdevforge.sisfin.exception.TimezoneValueException;
import br.com.dfdevforge.sisfin.persistence.PrsInsertExtratoByTable;
import br.com.dfdevforge.sisfin.persistence.PrsSelectExtratoParameterized;
import br.com.dfdevforge.sisfin.util.Utils;

public class BusExtrato extends BusAbstract
{
	PrsInsertExtratoByTable prs;

	public void alterar(AbstractBean to, ConnectionManager conn)
	{
	}

	public List<BtpExtrato> cadastrar(AbstractBean to, ConnectionManager ct)
	{
		return null;
	}

	public List<BtpExtrato> editar(AbstractBean to, ConnectionManager ct) throws TimezoneValueException, SessionUserNotFoundException, SQLException
	{
		return this.consultar(to, ct, 0);
	}

	public void excluir(AbstractBean to, ConnectionManager conn)
	{
	}

	public List<AbstractBean> exibir(AbstractBean to, ConnectionManager ct)
	{
		return null;
	}

	public List<BtpExtrato> consultar(AbstractBean to, ConnectionManager conn, int sqlOrdem) throws TimezoneValueException, SQLException, SessionUserNotFoundException
	{
		BtpExtrato btpExtrato = (BtpExtrato) to;

		PrsSelectExtratoParameterized prs = new PrsSelectExtratoParameterized(conn);

		return prs.execute(btpExtrato, sqlOrdem);
	}

	public void incluir(AbstractBean to, ConnectionManager conn) throws Exception
	{
		BtpExtrato btpExtrato = (BtpExtrato) to;

		/* Regra:
		 * Ler o arquivo texto e adicionar cada linha em um List<String>
		 */

		// Detalhe: carregar os dados do arquivo texto para a memória
		InputStream is = btpExtrato.getFormFile().getInputStream();
		InputStreamReader reader = new InputStreamReader(is, "ISO-8859-1");
		BufferedReader br = new BufferedReader(reader);

		// Detalhe: ler o arquivo e carregar cada linha em uma lista de String
		List<String> fileContent = new ArrayList<String>();

		String line = br.readLine();
		while (line != null)
		{
			fileContent.add(line);
			line = br.readLine();
		}

		br.close();
		reader.close();
		is.close();

		/* Regra:
		 * Executar o método responsável por identificar as informações do cabeçalho do extrato
		 */
		this.identificarCabecalhoExtrato(btpExtrato, fileContent);

		if (btpExtrato.getBtpTipoExtrato().getTieCodTipoExtrato().equals(1))
		{
			this.importarContaCorrente(btpExtrato, fileContent);
		}
		else if (btpExtrato.getBtpTipoExtrato().getTieCodTipoExtrato().equals(2))
		{
			try
			{
				this.importarPoupancaModelOne(btpExtrato, fileContent);
			}
			catch (Exception e)
			{
				this.importarPoupancaModelTwo(btpExtrato, fileContent);
			}
		}
		else if (btpExtrato.getBtpTipoExtrato().getTieCodTipoExtrato().equals(3))
		{
			this.importarCartaoCredito(btpExtrato, fileContent);
		}

		/*
		 * Inserindo os registros da tabela EXT_EXTRATO
		 */
		prs = new PrsInsertExtratoByTable(conn);
		prs.execute(btpExtrato);

		/*
		 * Inserindo os registros da tabela ITE_ITEM_EXTRATO
		 */
		for (BtpItemExtrato btpIteAux : btpExtrato.getBtpItemExtratoList())
		{
			btpIteAux.setBtpExtrato(btpExtrato);

			BusItemExtrato busItemExtrato = new BusItemExtrato();
			busItemExtrato.incluir(btpIteAux, conn);
		}
	}

	private void identificarCabecalhoExtrato(BtpExtrato btpExtrato, List<String> fileContent) throws Exception
	{
		btpExtrato.setBtpBanco(new BtpBanco());
		btpExtrato.setBtpTipoExtrato(new BtpTipoExtrato());

		/* Regra:
		 * Identificar o banco
		 */
		Boolean bankTypeFound = Boolean.FALSE;
		for (String fileLine : fileContent)
		{
			// Detalhe: Banco do Brasil
			if (fileLine.indexOf("Banco do Brasil") != -1)
			{
				btpExtrato.getBtpBanco().setBanCodBanco(1);
				bankTypeFound = Boolean.TRUE;
				break;
			}
		}

		if (!bankTypeFound)
			throw new Exception("Create a BankTypeNotFoundException");

		/* Regra:
		 * Identificar o tipo de extrato
		 */
		Boolean bankStatementFound = Boolean.FALSE;
		for (String fileLine : fileContent)
		{
			System.out.println(fileLine);

			// Detalhe: conta corrente
			if (fileLine.indexOf("Extrato de conta corrente") != -1)
			{
				btpExtrato.getBtpTipoExtrato().setTieCodTipoExtrato(EnumTipoExtrato.contaCorrente.getTieCodTipoExtrato());
				bankStatementFound = Boolean.TRUE;
				break;
			}

			// Detalhe: poupança
			else if (fileLine.indexOf("Variação:") != -1)
			{
				btpExtrato.getBtpTipoExtrato().setTieCodTipoExtrato(EnumTipoExtrato.poupança.getTieCodTipoExtrato());
				bankStatementFound = Boolean.TRUE;
				break;
			}

			// Detalhe: ourocard
			else if (fileLine.indexOf("Fatura do Cartão de Crédito") != -1)
			{
				btpExtrato.getBtpTipoExtrato().setTieCodTipoExtrato(EnumTipoExtrato.faturaOurocard.getTieCodTipoExtrato());
				bankStatementFound = Boolean.TRUE;
				break;
			}
		}

		if (!bankStatementFound)
			throw new Exception("Create a BankStatementNotFoundException");

		/* Regra:
		 * Identificar a competência
		 */
		Boolean periodStatementFound = Boolean.FALSE;

		// Detalhe: se o tipo do extrato for a conta corrente
		if (btpExtrato.getBtpTipoExtrato().getTieCodTipoExtrato().equals(EnumTipoExtrato.contaCorrente.getTieCodTipoExtrato()))
		{
			for (String fileLine : fileContent)
			{
				if (fileLine.toUpperCase().indexOf("Saldo anterior".toUpperCase()) != -1)
				{
					// Detalhe: criar um Calendar para executar as operações com data
					Calendar c = Calendar.getInstance();

					// Detalhe: converter a String que representa a data em um objeto java.util.Date e atribuí-la ao Calendar
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
					c.setTime(dateFormat.parse(fileLine.substring(0, 8)));

					// Detalhe: acrescentar um mês à data
					c.add(Calendar.MONTH, 1);

					// Detalhe: setar os valores de mês e ano ao btpExtrato
					btpExtrato.setExtDatMes(c.get(Calendar.MONTH) + 1);
					btpExtrato.setExtDatAno(c.get(Calendar.YEAR));

					periodStatementFound = Boolean.TRUE;

					break;
				}
			}
		}

		// Detalhe: se o tipo do extrato for a poupança
		else if (btpExtrato.getBtpTipoExtrato().getTieCodTipoExtrato().equals(EnumTipoExtrato.poupança.getTieCodTipoExtrato()))
		{
			for (String fileLine : fileContent)
			{
				if (fileLine.toUpperCase().indexOf("Saldo anterior".toUpperCase()) != -1)
				{
					// Detalhe: criar um Calendar para executar as operações com data
					Calendar c = Calendar.getInstance();

					// Detalhe: converter a String que representa a data em um objeto java.util.Date e atribuí-la ao Calendar
					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
					c.setTime(dateFormat.parse(fileLine.substring(0, 10)));

					// Detalhe: acrescentar um mês à data
					c.add(Calendar.DAY_OF_MONTH, 1);
					
					// Detalhe: setar os valores de mês e ano ao btpExtrato
					btpExtrato.setExtDatMes(c.get(Calendar.MONTH) + 1);
					btpExtrato.setExtDatAno(c.get(Calendar.YEAR));
					
					periodStatementFound = Boolean.TRUE;
					
					break;
				}
			}
		}

		// Detalhe: se o tipo do extrato for a fatura ourocard
		else if (btpExtrato.getBtpTipoExtrato().getTieCodTipoExtrato().equals(EnumTipoExtrato.faturaOurocard.getTieCodTipoExtrato()))
		{
			for (String fileLine : fileContent)
			{
				if (fileLine.indexOf("Vencimento") != -1)
				{
					// Detalhe: criar um Calendar para executar as operações com data
					Calendar c = Calendar.getInstance();

					// Detalhe: converter a String que representa a data em um objeto java.util.Date e atribuí-la ao Calendar
					DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
					c.setTime(dateFormat.parse(fileLine.substring(18, 28)));

					// Detalhe: setar os valores de mês e ano ao btpExtrato
					btpExtrato.setExtDatMes(c.get(Calendar.MONTH) + 1);
					btpExtrato.setExtDatAno(c.get(Calendar.YEAR));

					periodStatementFound = Boolean.TRUE;
					
					break;
				}
			}
		}

		if (!periodStatementFound)
			throw new Exception("Create a PeriodStatementNotFoundException");
	}

	private void importarContaCorrente(BtpExtrato btpExtrato, List<String> fileContent) throws FileNotFoundException, IOException, ParseException
	{
		int index = 0;

		/*
		 * Lendo linha por linha do arquivo para identificar o valor "Saldo Anterior",
		 * que significa a primeira linha relevante do arquivo.
		 */
		while (fileContent.get(index).indexOf("Saldo Anterior") == -1)
			index++;

		// Detalhe: identificar o valor do saldo anterior
		String auxSaldoInicial = fileContent.get(index).substring(64, 78).trim();
		auxSaldoInicial = auxSaldoInicial.replace(".", "");
		auxSaldoInicial = auxSaldoInicial.replace(",", ".");
		btpExtrato.setExtVlrSaldoInicial(new BigDecimal(auxSaldoInicial));

		BtpItemExtrato btpItemExtrato = null;
		btpExtrato.setBtpItemExtratoList(new ArrayList<BtpItemExtrato>());

		while (fileContent.get(index).indexOf("S A L D O") == -1)
		{
			if (!fileContent.get(index).substring(0, 4).equals("    "))
			{
				btpItemExtrato = new BtpItemExtrato();
				
				DateFormat df = new SimpleDateFormat("dd/MM/yy");
				
				btpItemExtrato.setIteDatMovimento(df.parse(fileContent.get(index).substring(0, 8)));
				btpItemExtrato.setIteTxtDescricao(fileContent.get(index).substring(17, 43).trim());
				btpItemExtrato.setIteNumDocumento(fileContent.get(index).substring(43, 64).trim());
				
				String aux = fileContent.get(index).substring(64, 78).trim();
				aux = aux.replace(".", "");
				aux = aux.replace(",", ".");
				
				btpItemExtrato.setIteVlrMovimento(new BigDecimal(aux));
				btpItemExtrato.setIteTxtTipo(fileContent.get(index).substring(79));
				
				btpExtrato.getBtpItemExtratoList().add(btpItemExtrato);
			}
			else
			{
				btpItemExtrato.setIteTxtDescricao(btpItemExtrato.getIteTxtDescricao() + " -> " + fileContent.get(index).substring(17).trim());
			}
			
			index++;
		}

		// Identifica a linha que contém o saldo final e atribui à variável apropriada
		String aux = fileContent.get(index).substring(64, 78).trim();
		aux = aux.replace(".", "");
		aux = aux.replace(",", ".");
		btpExtrato.setExtVlrSaldoFinal(new BigDecimal(aux));
		
		/*
		 * Ajustando a data do movimento "Saldo Anterior". No extrato, a data deste
		 * movimento é sempre do último dia do mês anterior, assim deve ser ajustada
		 * sempre para o primeiro dia do mês de competência.
		 */
		Calendar c = new GregorianCalendar();
		c.setTime(btpExtrato.getBtpItemExtratoList().get(0).getIteDatMovimento());
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		btpExtrato.getBtpItemExtratoList().get(0).setIteDatMovimento(c.getTime());
	}

	private void importarCartaoCredito(BtpExtrato btpExtrato, List<String> fileContent) throws FileNotFoundException, IOException, ParseException
	{
		int index = 0;

		/*
		 * Verifica se houve compra internacional e, em caso afirmativo,
		 * armazena a taxa de conversão e uma variável para uso posterior.
		 */
		while (fileContent.get(index).indexOf("convertido") == -1)
			index++;

		index++;
		index++;


		BigDecimal taxaConversao = Utils.convertStringNumberToBigDecimal(fileContent.get(index).substring(55, 63).trim());

		/*
		 * Reiniciando a leitura do arquivo
		 */
		index = 0;

		/*
		 * Lendo linha por linha do arquivo para identificar a string "Vencimento"
		 * que significam as datas de vencimento e pagamento dos itens do extrato.
		 */
		while (fileContent.get(index).indexOf("Vencimento") == -1)
			index++;

		DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
		Date dataVencimento = df.parse(fileContent.get(index).substring(18, 28));

		/*
		 * Lendo linha por linha do arquivo para identificar a string
		 * "Total da fatura" que significa o valor total da fatura.
		 */
		while (fileContent.get(index).indexOf("Total da fatura") == -1)
			index++;

		String auxSaldoFinal = fileContent.get(index).substring(21).trim();
		auxSaldoFinal = auxSaldoFinal.replace(".", "");
		auxSaldoFinal = auxSaldoFinal.replace(",", ".");
		btpExtrato.setExtVlrSaldoFinal(new BigDecimal(auxSaldoFinal));

		/*
		 * Lendo linha por linha do arquivo para identificar o valor "SALDO FATURA ANTERIOR",
		 * que significa a primeira linha relevante do arquivo e que contém o valor inicial do extrato.
		 */
		while (fileContent.get(index).indexOf("SALDO FATURA ANTERIOR") == -1)
			index++;

		/*
		 * Início do processo de captura de dados
		 */
		String auxSaldoInicial = fileContent.get(index).substring(55, 68).trim();
		auxSaldoInicial = auxSaldoInicial.replace(".", "");
		auxSaldoInicial = auxSaldoInicial.replace(",", ".");
		btpExtrato.setExtVlrSaldoInicial(new BigDecimal(auxSaldoInicial));

		BtpItemExtrato btpItemExtrato = null;
		btpExtrato.setBtpItemExtratoList(new ArrayList<BtpItemExtrato>());

		/* Regra:
		 * Identificar o saldo da fatura anterior como sendo o primeiro item do extrato
		 */
		btpItemExtrato = new BtpItemExtrato();

		btpItemExtrato.setIteDatMovimento(dataVencimento);
		btpItemExtrato.setIteTxtDescricao(fileContent.get(index).substring(9, 48).trim());

		BigDecimal valorReal = Utils.convertStringNumberToBigDecimal(fileContent.get(index).substring(55, 68).trim());
		BigDecimal valorDolar = Utils.convertStringNumberToBigDecimal(fileContent.get(index).substring(69, 80).trim());

		BigDecimal valorTotal = valorDolar.multiply(taxaConversao);
		valorTotal = valorTotal.add(valorReal);
		btpItemExtrato.setIteVlrMovimento(valorTotal);

		if (btpItemExtrato.getIteVlrMovimento().doubleValue() >= 0.0)
			btpItemExtrato.setIteTxtTipo("D");
		else
			btpItemExtrato.setIteTxtTipo("C");

		btpExtrato.getBtpItemExtratoList().add(btpItemExtrato);

		/* Regra:
		 * Identificar os itens que compõem o extrato e adicionar à lista principal
		 */
		while (fileContent.get(index).indexOf("         Total") == -1)
		{
			if (!fileContent.get(index).substring(0, 1).trim().equals(""))
			{
				btpItemExtrato = new BtpItemExtrato();

				btpItemExtrato.setIteDatMovimento(dataVencimento);
				btpItemExtrato.setIteTxtDescricao(fileContent.get(index).substring(9, 48).trim());

				valorReal = Utils.convertStringNumberToBigDecimal(fileContent.get(index).substring(55, 68).trim());
				valorDolar = Utils.convertStringNumberToBigDecimal(fileContent.get(index).substring(69, 80).trim());

				valorTotal = valorDolar.multiply(taxaConversao);
				valorTotal = valorTotal.add(valorReal);

				// Detalhe: verificar se o valor total vai gerar um movimento de débito ou de crédito
				if (valorTotal.compareTo(new BigDecimal(0)) >= 0)
					btpItemExtrato.setIteTxtTipo("D");
				else
					btpItemExtrato.setIteTxtTipo("C");

				// Detalhe: verificar se o valor total é negativo e, em caso afirmativo, transformá-lo para positivo
				if (valorTotal.compareTo(new BigDecimal(0)) < 0)
					valorTotal = valorTotal.multiply(new BigDecimal(-1));

				btpItemExtrato.setIteVlrMovimento(valorTotal);

				btpExtrato.getBtpItemExtratoList().add(btpItemExtrato);
			}

			index++;
		}
	}

	private void importarPoupancaModelOne(BtpExtrato btpExtrato, List<String> fileContent) throws FileNotFoundException, IOException, ParseException
	{
		int index = 0;

		/*
		 * Lendo linha por linha do arquivo para identificar o valor "Saldo Anterior",
		 * que significa a primeira linha relevante do arquivo.
		 */
		while (fileContent.get(index).indexOf("Saldo anterior") == -1)
			index++;

		/*
		 * Início do processo de captura de dados
		 */
		String auxSaldoInicial = fileContent.get(index).substring(73, 85).trim();
		auxSaldoInicial = auxSaldoInicial.replaceAll("\\.", "");
		auxSaldoInicial = auxSaldoInicial.replace(",", ".");
		btpExtrato.setExtVlrSaldoInicial(new BigDecimal(auxSaldoInicial));

		BtpItemExtrato btpItemExtrato = null;
		btpExtrato.setBtpItemExtratoList(new ArrayList<BtpItemExtrato>());

		while (fileContent.get(index) != null && fileContent.get(index).indexOf("S A L D O") == -1)
		{
			if (!fileContent.get(index).equals("") && !fileContent.get(index).substring(0, 1).equals("-"))
			{
				btpItemExtrato = new BtpItemExtrato();

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

				btpItemExtrato.setIteDatMovimento(df.parse(fileContent.get(index).substring(0, 10)));

				btpItemExtrato.setIteTxtDescricao("Poupança: " + fileContent.get(index).substring(20, 49).trim());

				String aux = fileContent.get(index).substring(73, 85).trim();
				aux = aux.replace(".", "");
				aux = aux.replace(",", ".");

				btpItemExtrato.setIteVlrMovimento(new BigDecimal(aux));
				btpItemExtrato.setIteTxtTipo(fileContent.get(index).substring(86, 88).trim());

				btpExtrato.getBtpItemExtratoList().add(btpItemExtrato);
			}

			index++;
		}

		// Identifica a linha que contém o saldo final e atribui à variável apropriada
		String aux = fileContent.get(index).substring(86, 98).trim();
		aux = aux.replace(".", "");
		aux = aux.replace(",", ".");
		btpExtrato.setExtVlrSaldoFinal(new BigDecimal(aux));

		/*
		 * Ajustando a data do movimento "Saldo Anterior". No extrato, a data deste
		 * movimento é sempre do último dia do mês anterior, assim deve ser ajustada
		 * sempre para o primeiro dia do mês de competência.
		 */
		Calendar c = new GregorianCalendar();
		c.setTime(btpExtrato.getBtpItemExtratoList().get(0).getIteDatMovimento());
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		btpExtrato.getBtpItemExtratoList().get(0).setIteDatMovimento(c.getTime());
	}

	private void importarPoupancaModelTwo(BtpExtrato btpExtrato, List<String> fileContent) throws FileNotFoundException, IOException, ParseException
	{
		int index = 0;
		
		/*
		 * Lendo linha por linha do arquivo para identificar o valor "Saldo Anterior",
		 * que significa a primeira linha relevante do arquivo.
		 */
		while (fileContent.get(index).indexOf("Saldo anterior") == -1)
			index++;

		/*
		 * Início do processo de captura de dados
		 */
		String auxSaldoInicial = fileContent.get(index).substring(76, 88).trim();
		auxSaldoInicial = auxSaldoInicial.replaceAll("\\.", "");
		auxSaldoInicial = auxSaldoInicial.replace(",", ".");
		btpExtrato.setExtVlrSaldoInicial(new BigDecimal(auxSaldoInicial));
		
		BtpItemExtrato btpItemExtrato = null;
		btpExtrato.setBtpItemExtratoList(new ArrayList<BtpItemExtrato>());

		BigDecimal saldoCompare = new BigDecimal(0);
		while (index < fileContent.size())
		{
			if (!fileContent.get(index).equals("") && !fileContent.get(index).substring(0, 1).equals("-"))
			{
				btpItemExtrato = new BtpItemExtrato();

				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

				btpItemExtrato.setIteDatMovimento(df.parse(fileContent.get(index).substring(0, 10)));

				btpItemExtrato.setIteTxtDescricao("Poupança: " + fileContent.get(index).substring(20, 49).trim());

				// Recuperando o valor do movimento
				String valorAux = fileContent.get(index).substring(76, 88).trim();
				valorAux = valorAux.replace(".", "");
				valorAux = valorAux.replace(",", ".");
				btpItemExtrato.setIteVlrMovimento(new BigDecimal(valorAux));

				// Recuperando o valor do saldo atual da linha para identificar se o movimento é crédito ou débito
				String saldoLinha = fileContent.get(index).substring(89, 102).trim();
				saldoLinha = saldoLinha.replace(".", "");
				saldoLinha = saldoLinha.replace(",", ".");

				BigDecimal saldoLinhaBig = new BigDecimal(saldoLinha);

				if (saldoCompare.equals(0) || saldoCompare.compareTo(saldoLinhaBig) < 0)
					btpItemExtrato.setIteTxtTipo("C");
				else
					btpItemExtrato.setIteTxtTipo("D");

				saldoCompare = saldoLinhaBig;

				btpExtrato.getBtpItemExtratoList().add(btpItemExtrato);
			}

			index++;
		}
		
		// Identifica a linha que contém o saldo final e atribui à variável apropriada
		btpExtrato.setExtVlrSaldoFinal(saldoCompare);
		
		/*
		 * Ajustando a data do movimento "Saldo Anterior". No extrato, a data deste
		 * movimento é sempre do último dia do mês anterior, assim deve ser ajustada
		 * sempre para o primeiro dia do mês de competência.
		 */
		Calendar c = new GregorianCalendar();
		c.setTime(btpExtrato.getBtpItemExtratoList().get(0).getIteDatMovimento());
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		btpExtrato.getBtpItemExtratoList().get(0).setIteDatMovimento(c.getTime());
	}
}