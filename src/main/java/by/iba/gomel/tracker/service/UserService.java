package by.iba.gomel.tracker.service;

import by.iba.gomel.tracker.dao.GenericDAO;
import by.iba.gomel.tracker.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements GenericService<User, Integer> {

    private GenericDAO<User, Integer> userGenericDAO;

    @Autowired
    public UserService(GenericDAO<User, Integer> userGenericDAO) {
        this.userGenericDAO = userGenericDAO;
    }

    @Override
    @Transactional("transactionManager")
    public User read(int id) {
        return userGenericDAO.read(id);
    }

    @Override
    @Transactional("transactionManager")
    public List<User> readAll() {
        return userGenericDAO.readAll();
    }

    @Override
    @Transactional("transactionManager")
    public void create(User newEntry) {
        userGenericDAO.create(newEntry);
        System.out.println(String.format("User: '%s %s' have been created", newEntry.getLastName(), newEntry.getFirstName()));
    }

    @Override
    @Transactional("transactionManager")
    public void delete(Integer id) {
        userGenericDAO.delete(id);
    }

    @Override
    @Transactional("transactionManager")
    public void update(Integer id, User newEntry) {
        userGenericDAO.update(id, newEntry);
    }
}
