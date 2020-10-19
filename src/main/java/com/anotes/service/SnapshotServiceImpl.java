package com.anotes.service;

import com.anotes.entity.Snapshot;
import com.anotes.repository.SnapshotRepository;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnapshotServiceImpl extends BaseServiceImpl<Snapshot, SnapshotRepository> implements SnapshotService {

    @Autowired
    public SnapshotServiceImpl(SnapshotRepository repository) {
        super(repository);
    }

    @Override
    public Option<Snapshot> findByMd5(String md5) {
        return getRepository().findByMd5(md5);
    }
}
