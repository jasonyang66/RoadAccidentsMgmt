package com.epam.repository;

import com.epam.repository.impl.Accident;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

import java.util.List;

/**
 * Created by yangyi on 3/1/17.
 */
public interface AccidentRepository {

    public Accident save(Accident accident);

    public List<Accident> all();
}
