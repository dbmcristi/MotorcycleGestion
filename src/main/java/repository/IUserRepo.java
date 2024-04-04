package repository;

import domain.Entity;

public interface IUserRepo<T extends Entity<ID>, ID> extends IRepo<T,ID> {
    boolean isLogged(String usrn, String pwsd) throws RepositoryException;
}
