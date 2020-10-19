package com.anotes.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BackupRequest {

    @Valid
    @NotEmpty
    private List<Note> notes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Note {

        @NotEmpty
        private String title;
        @NotEmpty
        private String text;
        @NotNull
        private Boolean pinned;
        private LocalDateTime reminderDate;
    }
}
