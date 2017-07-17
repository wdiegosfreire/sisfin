package br.com.dfdevforge.sisfin.bean;

import java.math.BigDecimal;
import java.util.Date;

import br.com.cagece.core.bean.api.AbstractBean;

public class BtpItemExtrato extends AbstractBean
{
	private static final long serialVersionUID = 6084539636063718671L;

	/*
	 * Atributos da entidade
	 */
	private Integer iteCodItemExtrato;
	public Integer getIteCodItemExtrato() {return iteCodItemExtrato;}
	public void setIteCodItemExtrato(Integer iteCodItemExtrato) {this.iteCodItemExtrato = iteCodItemExtrato;}

	private Date iteDatMovimento;
	public Date getIteDatMovimento() {return iteDatMovimento;}
	public void setIteDatMovimento(Date iteDatMovimento) {this.iteDatMovimento = iteDatMovimento;}

	private String iteTxtDescricao;
	public String getIteTxtDescricao() {return iteTxtDescricao;}
	public void setIteTxtDescricao(String iteTxtDescricao) {this.iteTxtDescricao = iteTxtDescricao;}

	private String iteNumDocumento;
	public String getIteNumDocumento() {return iteNumDocumento;}
	public void setIteNumDocumento(String iteNumDocumento) {this.iteNumDocumento = iteNumDocumento;}

	private String iteTxtTipo;
	public String getIteTxtTipo() {return iteTxtTipo;}
	public void setIteTxtTipo(String iteTxtTipo) {this.iteTxtTipo = iteTxtTipo;}

	private BigDecimal iteVlrMovimento;
	public BigDecimal getIteVlrMovimento() {return iteVlrMovimento;}
	public void setIteVlrMovimento(BigDecimal iteVlrMovimento) {this.iteVlrMovimento = iteVlrMovimento;}

	private Boolean iteFlgExportado;
	public Boolean getIteFlgExportado() {return iteFlgExportado;}
	public void setIteFlgExportado(Boolean iteFlgExportado) {this.iteFlgExportado = iteFlgExportado;}

	/*
	 * Atributos de relacionamento 1
	 */
	private BtpExtrato btpExtrato;
	public BtpExtrato getBtpExtrato() {return btpExtrato;}
	public void setBtpExtrato(BtpExtrato btpExtrato) {this.btpExtrato = btpExtrato;}

	private BtpObjetivo btpObjetivo;
	public BtpObjetivo getBtpObjetivo() {return btpObjetivo;}
	public void setBtpObjetivo(BtpObjetivo btpObjetivo) {this.btpObjetivo = btpObjetivo;}

	/*
	 * Métodos sobrescritos
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iteCodItemExtrato == null) ? 0 : iteCodItemExtrato.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof BtpItemExtrato))
			return false;

		BtpItemExtrato other = (BtpItemExtrato) obj;
		if (iteCodItemExtrato == null || other.iteCodItemExtrato == null)
		{
			return false;
		}
		else if (!iteCodItemExtrato.equals(other.iteCodItemExtrato))
		{
			return false;
		}

		return true;
	}
}