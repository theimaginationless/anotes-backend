package com.anotes.repository;

import com.anotes.entity.Note;
import com.anotes.entity.Snapshot;
import com.anotes.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends BaseRepository<Note> {

    List<Note> findAllByUserAndSnapshot(User user, Snapshot snapshot);
}
