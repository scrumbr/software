package br.web.scrumbr.constants;

public enum StatusTarefa {

	A_FAZER("Á Fazer"), 
	ANDAMENTO("Andamento"), 
	CONCLUIDA("Concluída"), ;

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
