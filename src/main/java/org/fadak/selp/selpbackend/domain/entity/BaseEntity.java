package org.fadak.selp.selpbackend.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "CREATED_BY")
    private Long createdBy;

    @Column(name = "EDIT_DATE")
    private LocalDateTime editDate;

    @Column(name = "EDIT_BY")
    private Long editBy;
}