package by.iba.gomel.tracker.dao;

import by.iba.gomel.tracker.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO implements GenericDAO<User, Integer> {

    private SessionFactory sessionFactory;

    @Autowired
    public UserDAO(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create(User newEntry) {
        Session session = sessionFactory.getCurrentSession();
        session.save(newEntry);
    }

    @Override
    public User read(int id) {
        Session session = sessionFactory.getCurrentSession();
        List<User> users = session.createQuery(String.format("from User u where u.id =%d", id)).list();
        return users.stream().findAny().get();
    }

    @Override
    public List<User> readAll() {
        Session session = sessionFactory.getCurrentSession();
        List<User> users = session.createQuery("from User").list();
        System.out.println(users);
        return users;
    }

    @Override
    public void delete(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        User userForDelete = session.get(User.class, id);
        session.delete(userForDelete);
    }

    @Override
    public void update(Integer id, User newEntry) {
        Session session = sessionFactory.getCurrentSession();
        User userForUpdate = session.get(User.class, id);
        userForUpdate.setFirstName(newEntry.getFirstName());
        userForUpdate.setLastName(newEntry.getLastName());
        userForUpdate.setEmail(newEntry.getEmail());
        userForUpdate.setPassword(newEntry.getPassword());
    }
}
