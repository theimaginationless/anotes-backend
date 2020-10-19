package com.anotes.repository;

import com.anotes.entity.Snapshot;
import com.anotes.entity.User;
import io.vavr.control.Option;
import org.springframework.stereotype.Repository;

@Repository
public interface SnapshotRepository extends BaseRepository<Snapshot> {

    Option<Snapshot> findByUserAndMd5(User user, String md5);

    Option<Snapshot> findFirstByUserOrderByCreationDateDesc(User user);
}
