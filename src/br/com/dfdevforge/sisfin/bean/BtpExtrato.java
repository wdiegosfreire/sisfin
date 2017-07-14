package br.com.dfdevforge.sisfin.bean;

import java.math.BigDecimal;
import java.util.List;

import org.apache.struts.upload.FormFile;

import br.com.cagece.core.bean.AbstractBean;

public class BtpExtrato extends AbstractBean
{
	private static final long serialVersionUID = 3273458585076011489L;

	/*
	 *  Atributos da entidade
	 */
	private Integer extCodExtrato;
	public Integer getExtCodExtrato() {return extCodExtrato;}
	public void setExtCodExtrato(Integer extCodExtrato) {this.extCodExtrato = extCodExtrato;}

	private Integer extDatAno;
	public Integer getExtDatAno() {return extDatAno;}
	public void setExtDatAno(Integer extDatAno) {this.extDatAno = extDatAno;}

	private Integer extDatMes;
	public Integer getExtDatMes() {return extDatMes;}
	public void setExtDatMes(Integer extDatMes) {this.extDatMes = extDatMes;}

	private BigDecimal extVlrSaldoInicial;
	public BigDecimal getExtVlrSaldoInicial() {return extVlrSaldoInicial;}
	public void setExtVlrSaldoInicial(BigDecimal extVlrSaldoInicial) {this.extVlrSaldoInicial = extVlrSaldoInicial;}

	private BigDecimal extVlrSaldoFinal;
	public BigDecimal getExtVlrSaldoFinal() {return extVlrSaldoFinal;}
	public void setExtVlrSaldoFinal(BigDecimal extVlrSaldoFinal) {this.extVlrSaldoFinal = extVlrSaldoFinal;}

	private FormFile formFile;
	public FormFile getFormFile() {return formFile;}
	public void setFormFile(FormFile formFile) {this.formFile = formFile;}

	/*
	 *  Atributos de relacionamento 1
	 */
	private BtpTipoExtrato btpTipoExtrato;
	public BtpTipoExtrato getBtpTipoExtrato() {return btpTipoExtrato;}
	public void setBtpTipoExtrato(BtpTipoExtrato btpTipoExtrato) {this.btpTipoExtrato = btpTipoExtrato;}

	private BtpBanco btpBanco;
	public BtpBanco getBtpBanco() {return btpBanco;}
	public void setBtpBanco(BtpBanco btpBanco) {this.btpBanco = btpBanco;}

	private BtpUsuario btpUsuario;
	public BtpUsuario getBtpUsuario() {return btpUsuario;}
	public void setBtpUsuario(BtpUsuario btpUsuario) {this.btpUsuario = btpUsuario;}

	/*
	 *  Atributos de relacionamento N
	 */
	private List<BtpItemExtrato> btpItemExtratoList;
	public List<BtpItemExtrato> getBtpItemExtratoList() {return btpItemExtratoList;}
	public void setBtpItemExtratoList(List<BtpItemExtrato> btpItemExtratoList) {this.btpItemExtratoList = btpItemExtratoList;}

	private List<BtpObjetivo> btpObjetivoList;
	public List<BtpObjetivo> getBtpObjetivoList() {return btpObjetivoList;}
	public void setBtpObjetivoList(List<BtpObjetivo> btpObjetivoList) {this.btpObjetivoList = btpObjetivoList;}

	/*
	 * Métodos sobrescritos
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extCodExtrato == null) ? 0 : extCodExtrato.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof BtpExtrato))
			return false;

		BtpExtrato other = (BtpExtrato) obj;
		if (extCodExtrato == null || other.extCodExtrato == null)
			return false;
		else if (!extCodExtrato.equals(other.extCodExtrato))
			return false;

		return true;
	}
}