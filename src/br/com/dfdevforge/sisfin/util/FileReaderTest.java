package br.com.dfdevforge.sisfin.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.dfdevforge.sisfin.bean.BtpItemExtrato;

public final class FileReaderTest 
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = null;

		try
		{
			FileInputStream stream = new FileInputStream("d:\\extrato.txt");
			InputStreamReader reader = new InputStreamReader(stream);
			br = new BufferedReader(reader);
			String linha = br.readLine();

			/*
			 * Lendo linha por linha do arquivo para identificar o valor "Saldo Anterior",
			 * que significa a primeira linha relevante do arquivo.
			 */
			while (linha.indexOf("Saldo Anterior") == -1)
			{
				linha = br.readLine();
			}

			/*
			 * Início do processo de captura de dados
			 */
			BtpItemExtrato btpExtrato = null;
			List<BtpItemExtrato> btpExtratoList = new ArrayList<BtpItemExtrato>();

			while (linha.indexOf("S A L D O") == -1)
			{
				System.out.println(linha);

				if (!linha.substring(0, 4).equals("    "))
				{
					btpExtrato = new BtpItemExtrato();

					DateFormat df = new SimpleDateFormat("dd/MM/yy");

					btpExtrato.setIteDatMovimento(df.parse(linha.substring(0, 8)));
					btpExtrato.setIteTxtDescricao(linha.substring(17, 43).trim());

					String aux = linha.substring(64, 78).trim();
					aux = aux.replace(".", "");
					aux = aux.replace(",", ".");

					btpExtrato.setIteVlrMovimento(new BigDecimal(aux));
					btpExtrato.setIteTxtTipo(linha.substring(79));

					btpExtratoList.add(btpExtrato);
				}
				else
				{
					btpExtrato.setIteTxtDescricao(btpExtrato.getIteTxtDescricao() + " -> " + linha.substring(17).trim());
				}

				linha = br.readLine();
			}

			/*
			 * Ajustando a data do movimento "Saldo Anterior". No extrato, a data deste
			 * movimento é sempre do último dia do mês anterior, assim deve ser ajustada
			 * sempre para o primeiro dia do mês de competência.
			 */
			Calendar c = new GregorianCalendar();
			c.setTime(btpExtratoList.get(0).getIteDatMovimento());
			c.add(Calendar.DAY_OF_MONTH, 1);
			btpExtratoList.get(0).setIteDatMovimento(c.getTime());

			System.out.println("");


//			while (linha != null)
//			{
//				// Identificando a linha de início da captura de dados
//				if (linha.indexOf("Saldo Anterior") != -1)
//				{
//					// Identificando se a linha atual é um novo movimento ou a descrição do movimento atual
//					if (linha.substring(0, 5).equals("    "))
//						System.out.println("Movimento: " + linha);
//					else
//						System.out.println("Descrição: " + linha);
//				}
//
//				linha = br.readLine();
//			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
				br.close();
		}
	}
}