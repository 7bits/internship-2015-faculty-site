package it.sevenbits.FacultySite.core.service;

public class ServiceException extends Exception{
    public ServiceException(String s, Exception e){
        super(s, e);
    }
}