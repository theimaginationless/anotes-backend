package com.anotes.entity;

import com.anotes.util.Constants;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Snapshot {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "seq_snapshot"
    )
    @SequenceGenerator(
            name = "seq_snapshot",
            allocationSize = Constants.ENTITY_ID_GENERATOR_STEP
    )
    private Long id;

    @ManyToOne
    private User user;

    @NotEmpty
    @Column(unique = true)
    private String md5;

    @CreationTimestamp
    private LocalDateTime creationDate;

    public Snapshot(User user, String md5) {
        this.user = user;
        this.md5 = md5;
    }
}
