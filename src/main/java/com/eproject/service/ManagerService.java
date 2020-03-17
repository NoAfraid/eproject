package com.eproject.service;
import com.eproject.entity.Manager;

public interface ManagerService {
    Manager login(String userName, String password);
}
