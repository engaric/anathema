package net.sf.anathema.hero.template;

import net.sf.anathema.character.generic.framework.ICharacterGenerics;
import net.sf.anathema.lib.util.Identifier;

public class DefaultTemplateFactory implements TemplateFactory {

  private final HeroModelTemplateCache templateCache;

  public DefaultTemplateFactory(ICharacterGenerics generics) {
    this.templateCache = generics.getDataSet(HeroModelTemplateCache.class);
  }

  public <T> T loadModelTemplate(Identifier templateId, TemplateLoader<T> loader) {
    return templateCache.loadTemplate(templateId, loader);
  }
}
