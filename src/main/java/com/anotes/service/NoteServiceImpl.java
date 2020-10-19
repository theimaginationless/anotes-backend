package com.anotes.service;

import com.anotes.controller.request.NoteRequest;
import com.anotes.entity.Note;
import com.anotes.entity.Snapshot;
import com.anotes.entity.User;
import com.anotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
public class NoteServiceImpl extends BaseServiceImpl<Note, NoteRepository> implements NoteService {

    private final SnapshotService snapshotService;

    @Autowired
    protected NoteServiceImpl(
            NoteRepository repository,
            SnapshotService snapshotService
    ) {
        super(repository);
        this.snapshotService = snapshotService;
    }

    @Override
    @Transactional
    public Note createNote(NoteRequest request, User user) {
        // Create Snapshot
        String noteMd5 = DigestUtils.md5DigestAsHex(request.toString().getBytes());
        Snapshot snapshot = new Snapshot(noteMd5);
        snapshot = snapshotService.save(snapshot);

        // Create Note
        Note savedNote = getRepository().save(
                new Note(
                        user,
                        snapshot,
                        request.getTitle(),
                        request.getText(),
                        request.getPinned(),
                        request.getReminderDate()
                )
        );
        return savedNote;
    }
}
