package com.anotes.controller;

import com.anotes.controller.request.NoteRequest;
import com.anotes.controller.response.NoteResponse;
import com.anotes.entity.Note;
import com.anotes.security.AppUser;
import com.anotes.service.NoteService;
import com.anotes.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
    @PreAuthorize("hasAuthority(T(com.anotes.security.AppAuthority).USER_WRITE.name())")
    NoteResponse saveNote(
            @Valid @RequestBody NoteRequest request,
            Authentication authentication
    ) {
        AppUser appUser = Utils.castAuthToAppUser(authentication);
        Note savedNote = noteService.save(
                new Note(
                        appUser.getUser(),
                        request.getTitle(),
                        request.getText(),
                        request.getPinned(),
                        request.getReminderDate()
                )
        );
        return NoteResponse.from(savedNote);
    }
}
