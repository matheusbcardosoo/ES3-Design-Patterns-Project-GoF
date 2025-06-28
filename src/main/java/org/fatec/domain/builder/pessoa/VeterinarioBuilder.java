package org.fatec.domain.builder.pessoa;

import org.fatec.domain.*;

public class VeterinarioBuilder implements IPessoaBuilder<Veterinario> {
    private Veterinario veterinario;
    private String nome;
    private Telefone telefone;
    private String crmv;

    public VeterinarioBuilder() {
        this.veterinario = new Veterinario();
    }

    public VeterinarioBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public VeterinarioBuilder comTelefone(String ddd, String numero) {
        this.telefone = new Telefone(ddd, numero);
        return this;
    }

    public VeterinarioBuilder comCRMV(String crmv) {
        this.crmv = crmv;
        return this;
    }

    @Override
    public void setDadosPessoais() {
        veterinario.setNome(nome != null ? nome : "");
        veterinario.setTelefone(telefone != null ? telefone : new Telefone("", ""));
    }

    @Override
    public void setRole() {
        veterinario.setUserRole(UserRolesEnum.VETERINARIO);
    }

    @Override
    public void setPets() {
        // Veterinário não tem pets
    }

    @Override
    public Veterinario getPessoa() {
        veterinario.setCrmv(crmv != null ? crmv : "");
        return veterinario;
    }
}
