package br.com.dfdevforge.sisfin.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class CuponFiscalReader 
{
	public static void main(String[] args) throws IOException
	{
		BufferedReader br = null;
		FileInputStream is = null;
		InputStreamReader rd = null;

		try
		{
			is = new FileInputStream("D:\\Diego\\Desenvolvimento\\temporary\\TESTE.txt");
			rd = new InputStreamReader(is);
			br = new BufferedReader(rd);

			// Detalhe: ler o arquivo e carregar cada linha em uma lista de String
			List<String> fileContent = new ArrayList<String>();

			String line = br.readLine();
			while (line != null)
			{
				fileContent.add(line);
				line = br.readLine();
			}

			for (String string : fileContent)
			{
				System.out.println(string);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
				br.close();
			if (is != null)
				is.close();
			if (rd != null)
				rd.close();
		}
	}
}