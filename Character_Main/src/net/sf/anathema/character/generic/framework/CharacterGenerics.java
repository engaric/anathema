package net.sf.anathema.character.generic.framework;

import net.sf.anathema.character.generic.caste.ICasteCollection;
import net.sf.anathema.character.generic.data.IExtensibleDataSet;
import net.sf.anathema.character.generic.data.IExtensibleDataSetProvider;
import net.sf.anathema.character.generic.framework.xml.registry.CharacterTemplateRegistryCollection;
import net.sf.anathema.character.generic.impl.magic.persistence.ICharmCache;
import net.sf.anathema.character.generic.impl.template.TemplateRegistry;
import net.sf.anathema.character.generic.impl.template.magic.CharmProvider;
import net.sf.anathema.character.generic.impl.template.magic.ICharmProvider;
import net.sf.anathema.character.generic.template.ITemplateRegistry;
import net.sf.anathema.character.generic.type.CharacterTypes;
import net.sf.anathema.character.generic.type.ICharacterType;
import net.sf.anathema.character.generic.type.ReflectionCharacterTypes;
import net.sf.anathema.initialization.ObjectFactory;
import net.sf.anathema.initialization.repository.DataFileProvider;
import net.sf.anathema.lib.registry.IRegistry;
import net.sf.anathema.lib.registry.Registry;

public class CharacterGenerics implements ICharacterGenerics {

  private final ITemplateRegistry templateRegistry = new TemplateRegistry();
  private final ICharacterTemplateRegistryCollection templateRegistries;
  private final IRegistry<ICharacterType, ICasteCollection> casteCollectionRegistry = new Registry<>();
  private final ICharmProvider charmProvider;
  private final DataFileProvider dataFileProvider;
  private final IExtensibleDataSetProvider dataSetProvider;
  private final ObjectFactory objectFactory;
  private final CharacterTypes characterTypes;

  public CharacterGenerics(DataFileProvider dataFileProvider, ObjectFactory objectFactory, IExtensibleDataSetProvider dataSetProvider) {
    this.objectFactory = objectFactory;
    this.dataFileProvider = dataFileProvider;
    this.dataSetProvider = dataSetProvider;
    this.charmProvider = new CharmProvider(dataSetProvider.getDataSet(ICharmCache.class));
    this.templateRegistries = new CharacterTemplateRegistryCollection(dataSetProvider.getDataSet(ICharacterTemplateExtensionResourceCache.class));
    this.characterTypes = new ReflectionCharacterTypes(objectFactory);
  }

  @Override
  public ITemplateRegistry getTemplateRegistry() {
    return templateRegistry;
  }

  @Override
  public ICharacterTemplateRegistryCollection getCharacterTemplateRegistries() {
    return templateRegistries;
  }

  @Override
  public IRegistry<ICharacterType, ICasteCollection> getCasteCollectionRegistry() {
    return casteCollectionRegistry;
  }

  @Override
  public ObjectFactory getInstantiater() {
    return objectFactory;
  }

  @Override
  public CharacterTypes getCharacterTypes() {
    return characterTypes;
  }

  @Override
  public ICharmProvider getCharmProvider() {
    return charmProvider;
  }

  @Override
  public DataFileProvider getDataFileProvider() {
    return dataFileProvider;
  }

  @Override
  public <T extends IExtensibleDataSet> T getDataSet(Class<T> set) {
    return dataSetProvider.getDataSet(set);
  }
}
