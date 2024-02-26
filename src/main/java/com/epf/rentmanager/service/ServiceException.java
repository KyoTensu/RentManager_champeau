package com.epf.rentmanager.service;

public class ServiceException extends Exception{
    public ServiceException(String message){
        super("Exception Service level\n"+ message);
    }
    public ServiceException(){ super("Exception Service Level");}
}
