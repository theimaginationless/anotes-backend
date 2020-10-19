package com.anotes.service;

import com.anotes.entity.Snapshot;
import io.vavr.control.Option;

public interface SnapshotService extends BaseService<Snapshot> {

    Option<Snapshot> findByMd5(String md5);
}
