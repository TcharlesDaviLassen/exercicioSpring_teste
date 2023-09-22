package com.example.exercicio.classUtils;

import java.io.Serializable;

public interface EntityInterface<PK extends Serializable> extends Serializable {

    PK getId();

    void setId(PK id);

}

