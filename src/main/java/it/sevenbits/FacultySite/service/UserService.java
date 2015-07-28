package it.sevenbits.FacultySite.service;

/**
 * Created by igodyaev on 28.07.15.
 */
import it.sevenbits.FacultySite.core.entity.Admin;

public interface UserService {

    Admin getUser(String login);

}