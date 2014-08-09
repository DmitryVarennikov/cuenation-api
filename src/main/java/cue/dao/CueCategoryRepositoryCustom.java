package cue.dao;

import cue.domain.CueCategory;

public interface CueCategoryRepositoryCustom {

    boolean saveIfNotExists(CueCategory category);

}
