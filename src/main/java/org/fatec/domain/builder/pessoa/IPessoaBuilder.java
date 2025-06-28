package org.fatec.domain.builder.pessoa;

import org.fatec.domain.Pessoa;

public interface IPessoaBuilder <T extends Pessoa> {
    void setDadosPessoais();
    void setRole();
    T getPessoa();
    void setPets();
}
