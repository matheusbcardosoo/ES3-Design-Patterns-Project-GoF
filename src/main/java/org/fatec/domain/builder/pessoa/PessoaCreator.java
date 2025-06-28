package org.fatec.domain.builder.pessoa;

import org.fatec.domain.Pessoa;

public class PessoaCreator<T extends Pessoa> {
        private IPessoaBuilder<T> builder;

        public PessoaCreator(IPessoaBuilder<T> builder) {
            this.builder = builder;
        }

        public void criarPessoa() {
            builder.setDadosPessoais();
            builder.setRole();
            builder.setPets();
        }

        public T getPessoa() {
            return builder.getPessoa();
        }
    }