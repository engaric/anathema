package net.sf.anathema.character.presenter;

import net.sf.anathema.character.presenter.initializers.HeroModelInitializer;
import net.sf.anathema.character.presenter.initializers.RegisteredInitializer;
import net.sf.anathema.character.view.SectionView;
import net.sf.anathema.framework.model.ApplicationModel;
import net.sf.anathema.hero.display.HeroModelGroup;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.lib.resources.Resources;

@RegisteredInitializer(HeroModelGroup.Miscellaneous)
public class PlayerHeroPointPresentation implements HeroModelInitializer {

  @SuppressWarnings("UnusedParameters")
  public PlayerHeroPointPresentation(ApplicationModel model) {
    //nothing to do
  }

  @Override
  public void initialize(SectionView sectionView, Hero hero, Resources resources) {
    new ExperiencePointPresenter(resources, hero).initPresentation(sectionView);
  }
}