package com.anotes.controller.request;

import com.anotes.controller.model.NoteModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BackupRequest {

    @Valid
    @NotEmpty
    private List<NoteModel> notes;
}
