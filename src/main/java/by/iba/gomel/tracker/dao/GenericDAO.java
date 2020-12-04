package by.iba.gomel.tracker.dao;

import java.util.List;

public interface GenericDAO <T, K> {

    void create(T newEntry);
    T read(int id);
    List<T> readAll();
    void delete(K id);
    void update(K id, T newEntry);

}
