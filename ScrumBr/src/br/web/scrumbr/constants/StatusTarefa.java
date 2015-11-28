package br.web.scrumbr.constants;

public enum StatusTarefa {

	A_FAZER("� Fazer"), 
	ANDAMENTO("Andamento"), 
	CONCLUIDA("Conclu�da"), ;

	StatusTarefa(String descricao) {
		this.descricao = descricao;
	}

	private String descricao;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
