package net.sf.anathema.character.main.persistence;

import net.sf.anathema.character.generic.data.IExtensibleDataSetProvider;
import net.sf.anathema.character.generic.framework.CharacterGenerics;
import net.sf.anathema.character.generic.framework.ICharacterGenerics;
import net.sf.anathema.character.generic.impl.magic.persistence.CharmCache;
import net.sf.anathema.character.generic.impl.magic.persistence.ICharmCache;
import net.sf.anathema.character.generic.impl.magic.persistence.ISpellCache;
import net.sf.anathema.character.generic.impl.magic.persistence.SpellCache;
import net.sf.anathema.character.main.testing.dummy.DummyExaltCharacterType;
import net.sf.anathema.character.main.testing.dummy.template.SimpleDummyCharacterTemplate;
import net.sf.anathema.character.model.Character;
import net.sf.anathema.character.model.CharacterStatisticsConfiguration;
import net.sf.anathema.character.persistence.DummyObjectFactory;
import net.sf.anathema.character.persistence.ExaltedCharacterPersister;
import net.sf.anathema.framework.item.IItemType;
import net.sf.anathema.framework.messaging.IMessaging;
import net.sf.anathema.framework.repository.Item;
import net.sf.anathema.lib.exception.PersistenceException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Ignore
public class ExaltedCharacterPersisterTest {
  private IMessaging messaging = mock(IMessaging.class);
  private IItemType type;
  private SimpleDummyCharacterTemplate template;
  private CharacterStatisticsConfiguration configuration;

  @Before
  public void createPersistableItemType() throws Exception {
    type = mock(IItemType.class);
    when(type.supportsRepository()).thenReturn(true);
  }

  @Before
  public void createCharacterConfiguration() {
    template = new SimpleDummyCharacterTemplate(new DummyExaltCharacterType(), null);
    configuration = new CharacterStatisticsConfiguration();
    configuration.setTemplate(template);
  }

  @Test
  public void createsFullyLoadedCharacter() throws Exception {
    ICharacterGenerics generics = createCharacterGenerics();
    ExaltedCharacterPersister persister = new ExaltedCharacterPersister(type, generics, messaging);
    Character character = createNewCharacter(persister);
    assertThat(character.isFullyLoaded(), is(true));
  }

  private ICharacterGenerics createCharacterGenerics() {
    IExtensibleDataSetProvider dataSetProvider = mock(IExtensibleDataSetProvider.class);
    when(dataSetProvider.getDataSet(ICharmCache.class)).thenReturn(new CharmCache());
    when(dataSetProvider.getDataSet(ISpellCache.class)).thenReturn(new SpellCache());
    ICharacterGenerics generics = new CharacterGenerics(null, new DummyObjectFactory(), dataSetProvider);
    generics.getTemplateRegistry().register(template);
    return generics;
  }

  private Character createNewCharacter(ExaltedCharacterPersister persister) throws PersistenceException {
    Item item = persister.createNew(configuration);
    return (net.sf.anathema.character.model.Character) item.getItemData();
  }
}
