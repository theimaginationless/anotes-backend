package com.anotes.service;

import com.anotes.entity.Snapshot;
import com.anotes.repository.SnapshotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SnapshotServiceImpl extends BaseServiceImpl<Snapshot, SnapshotRepository> implements SnapshotService {

    @Autowired
    public SnapshotServiceImpl(SnapshotRepository repository) {
        super(repository);
    }
}
