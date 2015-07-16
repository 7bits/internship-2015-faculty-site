package it.sevenbits.FacultySite.web.service;

public class ServiceException extends Exception{
    ServiceException(String s, Exception e){
        super(s, e);
    }
}
