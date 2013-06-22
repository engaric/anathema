package net.sf.anathema.hero.model;

import net.sf.anathema.character.generic.framework.ICharacterGenerics;
import net.sf.anathema.character.generic.impl.magic.persistence.ISpellCache;
import net.sf.anathema.character.generic.impl.template.magic.ICharmProvider;
import net.sf.anathema.character.generic.template.ITemplateRegistry;
import net.sf.anathema.character.generic.type.CharacterTypes;

public class ModelInitializationContext implements InitializationContext {

  private ICharacterGenerics generics;

  public ModelInitializationContext(ICharacterGenerics generics) {
    this.generics = generics;
  }


  @Override
  @Deprecated
  public ISpellCache getSpellCache() {
    return generics.getDataSet(ISpellCache.class);
  }

  @Override
  public CharacterTypes getCharacterTypes() {
    return generics.getCharacterTypes();
  }

  @Override
  public ITemplateRegistry getTemplateRegistry() {
    return generics.getTemplateRegistry();
  }

  @Override
  public ICharmProvider getCharmProvider() {
    return generics.getCharmProvider();
  }
}