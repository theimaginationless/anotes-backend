package com.anotes.controller;

import com.anotes.controller.request.NoteRequest;
import com.anotes.controller.response.NoteResponse;
import com.anotes.entity.Note;
import com.anotes.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NoteController {

    private final NoteService noteService;

    @PostMapping("note")
    NoteResponse saveNote(@Valid @RequestBody NoteRequest request) {
        Note savedNote = noteService.save(
                new Note(
                        request.getTitle(),
                        request.getText(),
                        request.getPinned(),
                        request.getReminderDate()
                )
        );
        return NoteResponse.from(savedNote);
    }
}
