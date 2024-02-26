package com.epf.rentmanager.dao;

public class DaoException extends Exception{
    public DaoException(String message){ super("Exception DAO level\n"+message);}
    public DaoException(){super("Exception DAO level");}
}
