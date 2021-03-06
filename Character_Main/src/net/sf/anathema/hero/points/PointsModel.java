package net.sf.anathema.hero.points;

import net.sf.anathema.character.generic.additionaltemplate.HeroModelBonusPointCalculator;
import net.sf.anathema.character.generic.additionaltemplate.HeroModelExperienceCalculator;
import net.sf.anathema.character.presenter.overview.IValueModel;
import net.sf.anathema.hero.model.HeroModel;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;

public interface PointsModel extends HeroModel {

  Identifier ID = new SimpleIdentifier("Points");

  void addBonusPointCalculator(HeroModelBonusPointCalculator bonusPointCalculator);

  void addExperienceCalculator(HeroModelExperienceCalculator bonusPointCalculator);

  void addToExperienceOverview(IValueModel<Integer> model);

  Iterable<HeroModelBonusPointCalculator> getBonusPointCalculators();

  Iterable<HeroModelExperienceCalculator> getExperienceCalculators();

  Iterable<IValueModel<Integer>> getExperienceOverviewModels();
}
