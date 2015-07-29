package it.sevenbits.FacultySite.service;

import it.sevenbits.FacultySite.core.entity.Admin;
import org.springframework.stereotype.Service;

/**
 * Created by igodyaev on 28.07.15.
 */
@Service
public class UserServiceImpl implements UserService {

    @Override
    public Admin getUser(String login) {
        Admin admin = new Admin();
        admin.setLogin(login);
        admin.setPassword("7110eda4d09e062aa5e4a390b0a572ac0d2c0220");//sha1

        return admin;
    }

}