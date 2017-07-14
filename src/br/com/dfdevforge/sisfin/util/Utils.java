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
		str = str.replace('ç','c');
		str = str.replace('Ç','C');
		str = str.replace('ã','a');
		str = str.replace('õ','o');
		str = str.replace('Ã','A');
		str = str.replace('Õ','O');
		str = str.replace('á','a');
		str = str.replace('é','e');
		str = str.replace('í','i');
		str = str.replace('ó','o');
		str = str.replace('ú','u');
		str = str.replace('Á','A');
		str = str.replace('É','E');
		str = str.replace('Í','I');
		str = str.replace('Ó','O');
		str = str.replace('Ú','U');
		str = str.replace('â','a');
		str = str.replace('ê','e');
		str = str.replace('ô','o');
		str = str.replace('û','u');
		str = str.replace('Â','A');
		str = str.replace('Ê','E');
		str = str.replace('Ô','O');
		str = str.replace('Û','U');
		str = str.replace('à','a');
		str = str.replace('À','A');
		str = str.replace('º',' ');
		str = str.replace('ª',' ');
		str = str.replace('\'','`');
		str = str.replace('´',' ');

		return str;
	}

	/**
	 * Verifica se a string é não nula e não vazia.
	 * @param str
	 * <ul><li>Objeto do tipo String</li></ul>
	 * @return
	 * <ul>
	 *   <li><code>true</code> se a condição for verdadeira</li>
	 *   <li><code>false</code> se a condição for falsa</li>
	 * </ul>
	 */
	public static boolean hasValue(String value)
	{
		if (value != null && !value.trim().equals(""))
			return true;

		return false;
	}

	/**
	 * Verifica se a string é não nula, não vazia e igual ao valor <code>match</code>.
	 * @param value
	 * <ul><li>Objeto do tipo String</li></ul>
	 * @param match
	 * <ul><li>Objeto ao qual <code>value</code> será comparado</li></ul>
	 * @return
	 * <ul>
	 *   <li><code>true</code> se a condição for verdadeira</li>
	 *   <li><code>false</code> se a condição for falsa</li>
	 * </ul>
	 */
	public static boolean hasValue(String value, String match)
	{
		if (hasValue(value) && value.equals(match))
			return true;

		return false;
	}

	/**
	 * Verifica se o Integer é não nula e maior que 0 (zero).
	 * @param val
	 * <ul><li>Objeto do tipo Integer</li></ul>
	 * @return
	 * <ul>
	 *   <li><code>true</code> se a condição for verdadeira</li>
	 *   <li><code>false</code> se a condição for falsa</li>
	 * </ul>
	 */
	public static boolean hasValue(Integer val)
	{
		if (val != null && val > 0)
			return true;

		return false;
	}

	/**
	 * Verifica se o BigDeciaml é não nulo.
	 * @param val
	 * <ul><li>Objeto do tipo BigDecimal</li></ul>
	 * @return
	 * <ul>
	 *   <li><code>true</code> se a condição for verdadeira</li>
	 *   <li><code>false</code> se a condição for falsa</li>
	 * </ul>
	 */
	public static boolean hasValue(BigDecimal val)
	{
		if (val != null)
			return true;

		return false;
	}

	/**
	 * Verifica se o Boolean é não nulo.
	 * @param val
	 * <ul><li>Objeto do tipo Boolean</li></ul>
	 * @return
	 * <ul>
	 *   <li><code>true</code> se a condição for verdadeira</li>
	 *   <li><code>false</code> se a condição for falsa</li>
	 * </ul>
	 */
	public static boolean hasValue(Boolean val)
	{
		if (val != null)
			return true;

		return false;
	}

	/**
	 * Verifica se o Date é não nulo.
	 * @param val
	 * <ul><li>Objeto do tipo java.util.Date</li></ul>
	 * @return
	 * <ul>
	 *   <li><code>true</code> se a condição for verdadeira</li>
	 *   <li><code>false</code> se a condição for falsa</li>
	 * </ul>
	 */
	public static boolean hasValue(Date val)
	{
		if (val != null)
			return true;

		return false;
	}

	/**
	 * Verifica se a string é não nula e não vazia.
	 * @param str
	 * <ul><li>Objeto do tipo String</li></ul>
	 * @return
	 * <ul>
	 *   <li><code>true</code> se a condição for verdadeira</li>
	 *   <li><code>false</code> se a condição for falsa</li>
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
	 * acordo com a mácara informada.
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
	 * Método para adiantar ou retroceder o valor de um objeto date. Pode ser em dias, meses ou anos.
	 * @param date objeto <code>Date</code> com a data original
	 * @param isAddition indica se a operação é de adição ou subtração
	 * @param value valor a ser adicionado ou subtraído da data
	 * @param type tipo da operação. Pode ser em dias, meses ou anos
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