package cuenation.api.cue.persistence;

import cuenation.api.cue.domain.CueCategory;

public interface CueCategoryRepositoryCustom {

    boolean saveIfNotExists(CueCategory category);

}
