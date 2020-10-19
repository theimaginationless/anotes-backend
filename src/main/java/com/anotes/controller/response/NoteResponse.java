package com.anotes.controller.response;

import com.anotes.entity.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteResponse {

    private Long id;
    private Long userId;
    private String snapshot;
    private String title;
    private String text;
    private Boolean pinned;
    private LocalDateTime creationDate;
    private LocalDateTime editDate;
    private LocalDateTime reminderDate;

    public static NoteResponse from(Note note) {
        return new NoteResponse(
                note.getUser().getId(),
                note.getId(),
                note.getSnapshot().getMd5(),
                note.getTitle(),
                note.getText(),
                note.getPinned(),
                note.getCreationDate(),
                note.getEditDate(),
                note.getReminderDate()
        );
    }
}
