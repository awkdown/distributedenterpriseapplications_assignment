package domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Version;
import java.io.Serializable;

public abstract class BaseEntity<PK extends Serializable> extends AbstractPersistable<PK> {
    @Version
    protected Integer version;
}