package com.anotes.repository;

import com.anotes.entity.User;
import io.vavr.control.Option;

public interface UserRepository extends BaseRepository<User> {

    Option<User> findByNickname(String nickname);
}
