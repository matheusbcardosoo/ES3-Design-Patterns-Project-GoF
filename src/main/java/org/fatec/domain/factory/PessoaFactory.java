package org.fatec.domain.factory;

import org.fatec.domain.UserRolesEnum;

public abstract class PessoaFactory {
    public abstract IFactory getUser(UserRolesEnum userRole);
}
