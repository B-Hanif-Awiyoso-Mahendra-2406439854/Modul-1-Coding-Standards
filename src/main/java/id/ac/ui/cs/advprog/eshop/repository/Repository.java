package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;

public interface Repository<T, ID> {
    T create(T entity);
    T findById(ID id);
    Iterator<T> findAll();
    T update(T entity);
    void delete(ID id);
}
