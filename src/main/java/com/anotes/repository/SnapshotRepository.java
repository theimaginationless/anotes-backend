package com.anotes.repository;

import com.anotes.entity.Snapshot;
import io.vavr.control.Option;
import org.springframework.stereotype.Repository;

@Repository
public interface SnapshotRepository extends BaseRepository<Snapshot> {

    Option<Snapshot> findByMd5(String md5);
}
