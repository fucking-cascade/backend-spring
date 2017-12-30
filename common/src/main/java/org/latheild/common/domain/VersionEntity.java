package org.latheild.common.domain;

import org.springframework.data.annotation.Version;

public abstract class VersionEntity extends AuditEntity {
    @Version
    private long version;

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
}
