package com.laptrinhoop.entity;


import com.laptrinhoop.constants.State;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
@MappedSuperclass
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class AuditableEntity  {
    @Enumerated(EnumType.STRING)
    State state;

    @Column(name = "created_date")
    String createdDate;

    @Column(name = "created_by")
    String createdBy;

    @Column(name = "modified_date")
    String modifiedDate;

    @Column(name = "modified_by")
    String modifiedBy;

    @PrePersist
    public void preCreate() {
        this.setState(Objects.isNull(this.getState()) ? State.ACTIVE : this.getState());
        this.setCreatedDate(LocalDateTime.now().toString());
        this.setCreatedBy("system");
        this.setModifiedDate(this.getCreatedDate());
    }

    @PreUpdate
    public void preUpdate() {
        this.setModifiedDate(LocalDateTime.now().toString());
        this.setModifiedBy("system");
    }

}
