package com.anotes.service;

import com.anotes.controller.request.BackupRequest;
import com.anotes.entity.Note;
import com.anotes.entity.Snapshot;
import com.anotes.entity.User;

public interface NoteService extends BaseService<Note> {

    Snapshot backup(BackupRequest request, User user);
}
