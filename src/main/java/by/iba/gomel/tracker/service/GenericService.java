package by.iba.gomel.tracker.service;

import java.util.List;

public interface GenericService <T, K> {
    T read(int id);
    List<T> readAll();
    void create(T newEntry);
    void delete(K id);
    void update(K id, T newEntry);

}
