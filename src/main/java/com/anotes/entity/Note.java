package com.anotes.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Note extends DatedEntity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_note"
    )
    @SequenceGenerator(
            name = "seq_note",
            allocationSize = 5
    )
    private Long id;

    @OneToOne
    private User user;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, columnDefinition = "text")
    private String text;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean pinned;

    private LocalDateTime reminderDate;

    public Note(User user, String title, String text, Boolean pinned, LocalDateTime reminderDate) {
        this.user = user;
        this.title = title;
        this.text = text;
        this.pinned = pinned;
        this.reminderDate = reminderDate;
    }
}
