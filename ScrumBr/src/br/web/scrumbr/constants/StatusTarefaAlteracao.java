package br.web.scrumbr.constants;

public enum StatusTarefaAlteracao {

	AFAZER_ANDAMENTO("De 'Á fazer' para 'Em andamento'"), 
	ANDAMENTO_CONCLUIDA("De 'Andamento' para 'Concluída'"),; 

	StatusTarefaAlteracao(String descricao) {
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
