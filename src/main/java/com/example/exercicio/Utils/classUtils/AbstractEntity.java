package com.example.exercicio.Utils.classUtils;

import java.io.Serializable;


//@MappedSuperclass
//@EntityListeners(DefaultValueListener.class)
public abstract class AbstractEntity<PK extends Serializable>
        implements Cloneable, EntityInterface<PK> {

    private static final long serialVersionUID = 1L;

    protected PK id;

    public AbstractEntity() { }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cnse) {
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractEntity)) {
            return false;
        }

        final AbstractEntity<PK> that = (AbstractEntity<PK>) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }

    @Override
    public String toString() {
        return "AbstractEntity{" + "id=" + id + '}';
    }
}
