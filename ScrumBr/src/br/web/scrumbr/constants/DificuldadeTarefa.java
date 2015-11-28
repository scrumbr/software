package br.web.scrumbr.constants;

public enum DificuldadeTarefa {

	BAIXA("Baixa"), 
	MEDIA("Media"), 
	ALTA("Alta"), ;

	DificuldadeTarefa(String descricao) {
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
