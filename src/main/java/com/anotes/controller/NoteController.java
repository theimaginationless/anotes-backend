package com.anotes.controller;

import com.anotes.controller.request.BackupRequest;
import com.anotes.controller.response.BackupResponse;
import com.anotes.controller.response.RestoreResponse;
import com.anotes.entity.Note;
import com.anotes.entity.Snapshot;
import com.anotes.entity.User;
import com.anotes.service.NoteService;
import com.anotes.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NoteController {

    private final NoteService noteService;

    @PostMapping("backup")
    @PreAuthorize("hasAuthority(T(com.anotes.security.AppAuthority).USER_WRITE.name())")
    BackupResponse backup(
            @RequestBody BackupRequest request,
            Authentication authentication
    ) {
        User user = Utils.castAuthToAppUser(authentication).getUser();
        Snapshot snapshot = noteService.backup(request, user);
        return BackupResponse.from(snapshot, user);
    }

    @GetMapping("restore")
    @PreAuthorize("hasAuthority(T(com.anotes.security.AppAuthority).USER_READ.name())")
    RestoreResponse restore(
            Authentication authentication
    ) {
        User user = Utils.castAuthToAppUser(authentication).getUser();
        List<Note> foundNotes = noteService.restoreLast(user);
        return RestoreResponse.from(foundNotes);
    }
}
