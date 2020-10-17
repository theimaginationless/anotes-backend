package com.anotes.repository;

import com.anotes.entity.Note;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends BaseRepository<Note> {
}
