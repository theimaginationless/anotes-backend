package com.anotes.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class DatedEntity {

    @CreationTimestamp
    LocalDateTime creationDate;
    @UpdateTimestamp
    LocalDateTime editDate;
}
