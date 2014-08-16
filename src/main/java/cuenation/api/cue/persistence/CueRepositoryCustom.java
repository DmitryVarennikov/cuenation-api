package cuenation.api.cue.persistence;

import cuenation.api.cue.domain.Cue;

public interface CueRepositoryCustom {

    boolean saveIfNotExists(Cue cue) throws CueCategoryNotFoundException;

}
