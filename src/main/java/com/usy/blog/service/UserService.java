package com.usy.blog.service;

import com.usy.blog.model.User;

public interface UserService {

    User checkUser(String username, String password);
}
