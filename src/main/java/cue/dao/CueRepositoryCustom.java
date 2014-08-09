package cue.dao;

import cue.domain.Cue;

public interface CueRepositoryCustom {

    boolean saveIfNotExists(Cue cue) throws CueCategoryNotFoundException;

}
