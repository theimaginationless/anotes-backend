package com.anotes.service;

import com.anotes.entity.Snapshot;
import com.anotes.entity.User;
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
    public Option<Snapshot> findByUserAndMd5(User user, String md5) {
        return getRepository().findByUserAndMd5(user, md5);
    }

    @Override
    public Option<Snapshot> findLastSnapshotByUser(User user) {
        return getRepository().findFirstByUserOrderByCreationDateDesc(user);
    }
}
