package com.anotes.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteModel {

    @NotEmpty
    private String title;
    @NotEmpty
    private String text;
    @NotNull
    private Boolean pinned;
    private LocalDateTime reminderDate;
}
