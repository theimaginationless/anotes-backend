package com.anotes.service;

import com.anotes.entity.User;
import io.vavr.control.Option;

public interface UserService extends BaseService<User> {

    Option<User> findByNickname(String nickname);
}
