package com.anotes.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequest {

    private String title;
    private String text;
    private Boolean pinned;
    private LocalDateTime reminderDate;
}
