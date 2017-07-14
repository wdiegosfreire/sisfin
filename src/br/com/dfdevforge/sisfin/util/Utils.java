package br.com.dfdevforge.sisfin.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class Utils 
{
	public static String formatIdCapaAlbum(String idBanda, String idAlbum)
	{
		if (idBanda.length() == 1) {idBanda = "000" + idBanda;}
		else if (idBanda.length() == 2) {idBanda = "00" + idBanda;}
		else if (idBanda.length() == 3) {idBanda = "0" + idBanda;}

		if (idAlbum.length() == 1) {idAlbum = "000" + idAlbum;}
		else if (idAlbum.length() == 2) {idAlbum = "00" + idAlbum;}
		else if (idAlbum.length() == 3) {idAlbum = "0" + idAlbum;}

		return idBanda + "_" + idAlbum;
	}

	public static String removeSymbols(String str)
	{
		str = str.replace('�','c');
		str = str.replace('�','C');
		str = str.replace('�','a');
		str = str.replace('�','o');
		str = str.replace('�','A');
		str = str.replace('�','O');
		str = str.replace('�','a');
		str = str.replace('�','e');
		str = str.replace('�','i');
		str = str.replace('�','o');
		str = str.replace('�','u');
		str = str.replace('�','A');
		str = str.replace('�','E');
		str = str.replace('�','I');
		str = str.replace('�','O');
		str = str.replace('�','U');
		str = str.replace('�','a');
		str = str.replace('�','e');
		str = str.replace('�','o');
		str = str.replace('�','u');
		str = str.replace('�','A');
		str = str.replace('�','E');
		str = str.replace('�','O');
		str = str.replace('�','U');
		str = str.replace('�','a');
		str = str.replace('�','A');
		str = str.replace('�',' ');
		str = str.replace('�',' ');
		str = str.replace('\'','`');
		str = str.replace('�',' ');

		return str;
	}

	/**
	 * Verifica se a string � n�o nula e n�o vazia.
	 * @param str
	 * <ul><li>Objeto do tipo String</li></ul>
	 * @return
	 * <ul>
	 *   <li><code>true</code> se a condi��o for verdadeira</li>
	 *   <li><code>false</code> se a condi��o for falsa</li>
	 * </ul>
	 */
	public static boolean hasValue(String value)
	{
		if (value != null && !value.trim().equals(""))
			return true;

		return false;
	}

	/**
	 * Verifica se a string � n�o nula, n�o vazia e igual ao valor <code>match</code>.
	 * @param value
	 * <ul><li>Objeto do tipo String</li></ul>
	 * @param match
	 * <ul><li>Objeto ao qual <code>value</code> ser� comparado</li></ul>
	 * @return
	 * <ul>
	 *   <li><code>true</code> se a condi��o for verdadeira</li>
	 *   <li><code>false</code> se a condi��o for falsa</li>
	 * </ul>
	 */
	public static boolean hasValue(String value, String match)
	{
		if (hasValue(value) && value.equals(match))
			return true;

		return false;
	}

	/**
	 * Verifica se o Integer � n�o nula e maior que 0 (zero).
	 * @param val
	 * <ul><li>Objeto do tipo Integer</li></ul>
	 * @return
	 * <ul>
	 *   <li><code>true</code> se a condi��o for verdadeira</li>
	 *   <li><code>false</code> se a condi��o for falsa</li>
	 * </ul>
	 */
	public static boolean hasValue(Integer val)
	{
		if (val != null && val > 0)
			return true;

		return false;
	}

	/**
	 * Verifica se o BigDeciaml � n�o nulo.
	 * @param val
	 * <ul><li>Objeto do tipo BigDecimal</li></ul>
	 * @return
	 * <ul>
	 *   <li><code>true</code> se a condi��o for verdadeira</li>
	 *   <li><code>false</code> se a condi��o for falsa</li>
	 * </ul>
	 */
	public static boolean hasValue(BigDecimal val)
	{
		if (val != null)
			return true;

		return false;
	}

	/**
	 * Verifica se o Boolean � n�o nulo.
	 * @param val
	 * <ul><li>Objeto do tipo Boolean</li></ul>
	 * @return
	 * <ul>
	 *   <li><code>true</code> se a condi��o for verdadeira</li>
	 *   <li><code>false</code> se a condi��o for falsa</li>
	 * </ul>
	 */
	public static boolean hasValue(Boolean val)
	{
		if (val != null)
			return true;

		return false;
	}

	/**
	 * Verifica se o Date � n�o nulo.
	 * @param val
	 * <ul><li>Objeto do tipo java.util.Date</li></ul>
	 * @return
	 * <ul>
	 *   <li><code>true</code> se a condi��o for verdadeira</li>
	 *   <li><code>false</code> se a condi��o for falsa</li>
	 * </ul>
	 */
	public static boolean hasValue(Date val)
	{
		if (val != null)
			return true;

		return false;
	}

	/**
	 * Verifica se a string � n�o nula e n�o vazia.
	 * @param str
	 * <ul><li>Objeto do tipo String</li></ul>
	 * @return
	 * <ul>
	 *   <li><code>true</code> se a condi��o for verdadeira</li>
	 *   <li><code>false</code> se a condi��o for falsa</li>
	 * </ul>
	 */
	public static boolean hasValue(String[] str)
	{
		if (str != null && str.length > 0)
			return true;

		return false;
	}

	public static String toUsaNumberFormat(String valor)
	{
		boolean isBraFormat = false;

		String temp[] = valor.split("");
		for (int i = 0; i < temp.length; i++)
		{
			if (temp[i].equals(","))
			{
				isBraFormat = true;
				i = temp.length;
			}
		}

		String aux = valor;

		if (isBraFormat)
		{
			aux = aux.replaceAll(",", "@");
			aux = aux.replaceAll("\\.", "");
			aux = aux.replaceAll("@", ".");
		}

		return aux;
	}

	public static BigDecimal convertStringNumberToBigDecimal(String value)
	{
		BigDecimal convertedValue = new BigDecimal(toUsaNumberFormat(value));

		return convertedValue;
	}

	/**
	 * Converte o valor de um objeto <code>Date</code> para uma <code>String</code> no
	 * formato dd/MM/yyyy.
	 * @param data
	 * @return
	 */
	public static String convertDateToString(Date data)
	{
		return convertDateToString(data, "dd/MM/yyyy");
	}

	/**
	 * Converte o valor de um objeto <code>Date</code> para uma <code>String</code> de
	 * acordo com a m�cara informada.
	 * @param data
	 * @param mask
	 * @return
	 */
	public static String convertDateToString(Date data, String mask)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(mask);

		return sdf.format(data);
	}

	/**
	 * M�todo para adiantar ou retroceder o valor de um objeto date. Pode ser em dias, meses ou anos.
	 * @param date objeto <code>Date</code> com a data original
	 * @param isAddition indica se a opera��o � de adi��o ou subtra��o
	 * @param value valor a ser adicionado ou subtra�do da data
	 * @param type tipo da opera��o. Pode ser em dias, meses ou anos
	 * @return objeto <code>Date</code> com a data alterada
	 */
	public static Date dateOperator(Date date, boolean isAddition, int value, int type)
	{
		if (!isAddition)
			value = value * -1;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		if (type == 1)
			calendar.add(Calendar.DAY_OF_MONTH, value);
		else if (type == 2)
			calendar.add(Calendar.MONTH, value);
		else if (type == 3)
			calendar.add(Calendar.YEAR, value);

		return calendar.getTime();
	}
}