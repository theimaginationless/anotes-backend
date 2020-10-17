package com.anotes.service;

import com.anotes.entity.Note;
import com.anotes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl extends BaseServiceImpl<Note, NoteRepository> implements NoteService {

    @Autowired
    protected NoteServiceImpl(NoteRepository repository) {
        super(repository);
    }
}
