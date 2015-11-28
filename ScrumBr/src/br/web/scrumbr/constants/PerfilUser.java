package br.web.scrumbr.constants;

public enum PerfilUser {

	SCRUM_MASTER("Scrum Master"), EQUIPE_DESENVOLVIMENTO(
			"Equipe de Desenvolvimento"), PRODUCT_OWNER("Product Owner"), ;

	PerfilUser(String descricao) {
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
