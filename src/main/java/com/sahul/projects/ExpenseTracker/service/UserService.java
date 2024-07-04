package com.sahul.projects.ExpenseTracker.service;

import com.sahul.projects.ExpenseTracker.entity.User;
import com.sahul.projects.ExpenseTracker.entity.UserModel;

public interface UserService {
    User createUser(UserModel userModel);
    User ReadUser();
    User UpdateUser(User user);
    void deleteUser();
    User getLoggedInUser();
}
