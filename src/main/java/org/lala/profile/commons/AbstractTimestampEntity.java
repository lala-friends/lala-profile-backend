package org.lala.profile.commons;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class AbstractTimestampEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reg_dt", nullable = false)
    private Date regDt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "mod_dt", nullable = false)
    private Date modDt;

    @PrePersist
    protected void onCreate() {
        modDt = regDt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        modDt = new Date();
    }
}
