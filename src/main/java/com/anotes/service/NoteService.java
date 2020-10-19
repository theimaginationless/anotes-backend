package com.anotes.service;

import com.anotes.controller.request.NoteRequest;
import com.anotes.entity.Note;
import com.anotes.entity.User;

public interface NoteService extends BaseService<Note> {

    Note createNote(NoteRequest request, User user);
}
