package com.anotes.service;

import com.anotes.entity.Snapshot;
import com.anotes.entity.User;
import io.vavr.control.Option;

public interface SnapshotService extends BaseService<Snapshot> {

    Option<Snapshot> findByUserAndMd5(User user, String md5);

    Option<Snapshot> findLastSnapshotByUser(User user);
}
