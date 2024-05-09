package myproject.hibernate.repository;

import myproject.hibernate.domain.IEntity;
import myproject.hibernate.domain.User;

public interface ICrudRepository<ID, E extends IEntity<ID>> {
    E save(E e);
    void delete(ID id);
    E findOne(ID id);
    E update(ID id, E e);
    Iterable<E> getAll() ;
}
