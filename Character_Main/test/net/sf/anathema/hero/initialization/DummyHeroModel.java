package net.sf.anathema.hero.initialization;

import net.sf.anathema.hero.change.ChangeAnnouncer;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.model.HeroModel;
import net.sf.anathema.hero.model.InitializationContext;
import net.sf.anathema.lib.util.Identifier;

public class DummyHeroModel implements HeroModel {
  private Identifier id;

  public DummyHeroModel(Identifier id) {
    this.id = id;
  }

  @Override
  public Identifier getId() {
    return id;
  }

  @Override
  public void initialize(InitializationContext context, Hero hero) {
    //nothing to do
  }

  @Override
  public void initializeListening(ChangeAnnouncer announcer) {
    //nothing to do
  }
}