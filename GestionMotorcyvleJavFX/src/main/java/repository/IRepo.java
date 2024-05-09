package repository;

import domain.Entity;

import java.util.List;

public interface IRepo<T extends Entity<ID>, ID> {
    void delete(ID id) throws RepositoryException;

    void add(T e) throws RepositoryException;

    T getById(ID pos) throws RepositoryException;

    void update(T newEntity) throws RepositoryException;

    void deleteAll() throws RepositoryException;

    List<T> getAll() throws RepositoryException;

}
