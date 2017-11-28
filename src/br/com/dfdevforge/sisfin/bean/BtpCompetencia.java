package br.com.dfdevforge.sisfin.bean;

import java.math.BigDecimal;
import java.util.List;

import br.com.cagece.core.bean.api.AbstractBean;

public class BtpCompetencia extends AbstractBean
{
	private static final long serialVersionUID = 5062761202361192479L;

	/*
	 *  Atributos da entidade
	 */
	private Integer comCodCompetencia;
	public Integer getComCodCompetencia() {return comCodCompetencia;}
	public void setComCodCompetencia(Integer comCodCompetencia) {this.comCodCompetencia = comCodCompetencia;}

	private String comDatMes;
	public String getComDatMes() {return comDatMes;}
	public void setComDatMes(String comDatMes) {this.comDatMes = comDatMes;}

	private String comDatAno;
	public String getComDatAno() {return comDatAno;}
	public void setComDatAno(String comDatAno) {this.comDatAno = comDatAno;}

	private BigDecimal comVlrSaldoInicial;
	public BigDecimal getComVlrSaldoInicial() {return comVlrSaldoInicial;}
	public void setComVlrSaldoInicial(BigDecimal comVlrSaldoInicial) {this.comVlrSaldoInicial = comVlrSaldoInicial;}

	private BigDecimal comVlrSaldoFinal;
	public BigDecimal getComVlrSaldoFinal() {return comVlrSaldoFinal;}
	public void setComVlrSaldoFinal(BigDecimal comVlrSaldoFinal) {this.comVlrSaldoFinal = comVlrSaldoFinal;}

	/*
	 *  Atributos de relacionamento 1
	 */
	private BtpUsuario btpUsuario;
	public BtpUsuario getBtpUsuario() {return btpUsuario;}
	public void setBtpUsuario(BtpUsuario btpUsuario) {this.btpUsuario = btpUsuario;}

	/*
	 *  Atributos de relacionamento N
	 */
	private List<BtpMovimento> btpMovimentoList;
	public List<BtpMovimento> getBtpMovimentoList() {return btpMovimentoList;}
	public void setBtpMovimentoList(List<BtpMovimento> btpMovimentoList) {this.btpMovimentoList = btpMovimentoList;}

	/*
	 * Métodos sobrescritos
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comCodCompetencia == null) ? 0 : comCodCompetencia.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BtpCompetencia))
			return false;

		BtpCompetencia other = (BtpCompetencia) obj;
		if (comCodCompetencia == null || other.comCodCompetencia == null)
			return false;
		else if (!comCodCompetencia.equals(other.comCodCompetencia))
			return false;

		return true;
	}
}