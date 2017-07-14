package br.com.dfdevforge.sisfin.constants;

public enum EnumTipoExtrato
{
	contaCorrente(1, "Conta Corrente"),
	poupança(2, "Poupança"),
	faturaOurocard(3, "Fatura Ourocard");

	private EnumTipoExtrato(Integer tieCodTipoExtrato, String tieTxtNome)
	{
		this.tieCodTipoExtrato = tieCodTipoExtrato;
		this.tieTxtNome = tieTxtNome;
	}

	private Integer tieCodTipoExtrato; 
	public Integer getTieCodTipoExtrato() {return tieCodTipoExtrato;}

	private String tieTxtNome;
	public String getTieTxtNome() {return tieTxtNome;}
}