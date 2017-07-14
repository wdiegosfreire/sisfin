package br.com.dfdevforge.sisfin.constants;

public enum EnumComandoNavegacao
{
	actShowMainPage("actShowMainPage", "Exibir a p�gina inicial de par�metros"),
	actShowInclForm("actShowInclForm", "Exibir o formul�rio de cadastro de um novo registro"),
	actShowEditForm("actShowEditForm", "Exibir o formul�rio de edi��o de um registro existente, carregado com seus respectivos valores"),
	actShowImpoForm("actShowImpoForm", "Exibir o formul�rio de cadastro de um novo registro"),
	actExecInsert("actExecInsert", "Executar a inclus�o de um novo registro"),
	actExecUpdate("actExecUpdate", "Executar a atualiza��o de um registro existente"),
	actExecDelete("actExecDelete", "Executar a exclus�o de um registro existente"),
	actExecSearch("actExecSearch", "Executar uma consulta baseada nos filtros existentes"),
	actExecLogoff("actExecLogoff", "Executar o logoff do sistema");

	private EnumComandoNavegacao(String nome, String descricao)
	{
		this.nome = nome;
		this.descricao = descricao;
	}

	private String nome; 
	public String getNome() {return nome;}

	private String descricao;
	public String getDescricao() {return descricao;}
}