package cl.bci.postulacion.services;

import cl.bci.postulacion.models.User;

import java.util.List;

public interface IUserService {
    User createUser(User user);
    List<User> findAll();
    User findUserByEmail(String email);
    void deleteUser(User user);

    User save(User user);
}
