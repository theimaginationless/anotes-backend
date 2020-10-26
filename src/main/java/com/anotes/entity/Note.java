package com.anotes.entity;

import com.anotes.util.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Note extends BaseEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_note"
    )
    @SequenceGenerator(
            name = "seq_note",
            allocationSize = Constants.ENTITY_ID_GENERATOR_STEP
    )
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Snapshot snapshot;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String text;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean pinned;

    private LocalDateTime reminderDate;

    private LocalDateTime creationDate;

    private LocalDateTime editDate;

    public Note(
            User user,
            Snapshot snapshot,
            String title,
            String text,
            Boolean pinned,
            LocalDateTime reminderDate,
            LocalDateTime creationDate,
            LocalDateTime editDate
    ) {
        this.user = user;
        this.snapshot = snapshot;
        this.title = title;
        this.text = text;
        this.pinned = pinned;
        this.reminderDate = reminderDate;
        this.creationDate = creationDate;
        this.editDate = editDate;
    }
}
