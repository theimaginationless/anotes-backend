package com.anotes.controller.response;

import com.anotes.controller.model.NoteModel;
import com.anotes.entity.Note;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestoreResponse {

    private List<NoteModel> notes;

    public static RestoreResponse from(List<Note> notes) {
        return new RestoreResponse(
                notes.stream()
                    .map(note ->
                            new NoteModel(
                                    note.getTitle(),
                                    note.getText(),
                                    note.getPinned(),
                                    note.getReminderDate(),
                                    note.getCreationDate(),
                                    note.getEditDate()
                            )
                    )
                    .collect(Collectors.toList())
        );
    }
}
