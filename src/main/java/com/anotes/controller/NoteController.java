package com.anotes.controller;

import com.anotes.controller.request.BackupRequest;
import com.anotes.controller.response.BackupResponse;
import com.anotes.entity.Snapshot;
import com.anotes.entity.User;
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

    @PostMapping("backup")
    @PreAuthorize("hasAuthority(T(com.anotes.security.AppAuthority).USER_WRITE.name())")
    BackupResponse backup(
            @Valid @RequestBody BackupRequest request,
            Authentication authentication
    ) {
        User user = Utils.castAuthToAppUser(authentication).getUser();
        Snapshot snapshot = noteService.backup(request, user);
        return BackupResponse.from(snapshot, user);
    }
}
