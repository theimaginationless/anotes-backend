package com.anotes.service;

import com.anotes.entity.User;
import com.anotes.repository.UserRepository;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserRepository> implements UserService {

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }

    @Override
    public Option<User> findByNickname(String nickname) {
        return getRepository().findByNickname(nickname);
    }
}
