package net.sf.anathema.character.main.trait;

import net.sf.anathema.character.generic.caste.CasteType;
import net.sf.anathema.character.generic.caste.ICasteCollection;
import net.sf.anathema.character.generic.impl.caste.CasteCollection;
import net.sf.anathema.character.generic.template.abilities.GroupedTraitType;
import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.character.generic.traits.groups.IIdentifiedTraitTypeGroup;
import net.sf.anathema.character.main.testing.dummy.DummyCasteType;
import net.sf.anathema.character.model.traits.creation.AbilityTypeGroupFactory;
import org.junit.Test;

import java.util.Collections;

import static net.sf.anathema.character.generic.template.abilities.AbilityGroupType.Life;
import static net.sf.anathema.character.generic.template.abilities.AbilityGroupType.War;
import static net.sf.anathema.character.generic.traits.types.AbilityType.Archery;
import static net.sf.anathema.character.generic.traits.types.AbilityType.Medicine;
import static net.sf.anathema.character.generic.traits.types.AbilityType.Melee;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class AbilityTraitTypeGroupFactoryTest {

  private AbilityTypeGroupFactory factory = new AbilityTypeGroupFactory();

  @Test
  public void testOneGroup() throws Exception {
    ICasteCollection casteCollection = new CasteCollection(new CasteType[0]);
    GroupedTraitType[] abilityTypes = new GroupedTraitType[]{
            new GroupedTraitType(Archery, "War", Collections.<String>emptyList()),
            new GroupedTraitType(Melee, "War", Collections.<String>emptyList())
    };
    IIdentifiedTraitTypeGroup[] typeGroups = factory.createTraitGroups(casteCollection, abilityTypes);
    assertEquals(1, typeGroups.length);
    assertArrayEquals(new TraitType[]{Archery, Melee}, typeGroups[0].getAllGroupTypes());
    assertEquals(War, typeGroups[0].getGroupId());
  }

  @Test
  public void testDifferentGroups() throws Exception {
    ICasteCollection casteCollection = new CasteCollection(new CasteType[0]);
    GroupedTraitType[] abilityTypes = new GroupedTraitType[]{
            new GroupedTraitType(Archery, "War", Collections.<String>emptyList()),
            new GroupedTraitType(Medicine, "Life", Collections.<String>emptyList())
    };
    IIdentifiedTraitTypeGroup[] typeGroups = factory.createTraitGroups(casteCollection, abilityTypes);
    assertEquals(2, typeGroups.length);
    assertArrayEquals(new TraitType[]{Archery}, typeGroups[0].getAllGroupTypes());
    assertArrayEquals(new TraitType[]{Medicine}, typeGroups[1].getAllGroupTypes());
    assertEquals(War, typeGroups[0].getGroupId());
    assertEquals(Life, typeGroups[1].getGroupId());
  }

  @Test
  public void testCasteAsGroupIdentificate() throws Exception {
    DummyCasteType caste = new DummyCasteType("Caste");
    ICasteCollection casteCollection = new CasteCollection(new CasteType[]{caste});
    GroupedTraitType[] abilityTypes = new GroupedTraitType[]{
            new GroupedTraitType(Archery, caste.getId(), Collections.singletonList(caste.getId())),
            new GroupedTraitType(Melee, caste.getId(), Collections.singletonList(caste.getId()))};
    IIdentifiedTraitTypeGroup[] typeGroups = factory.createTraitGroups(casteCollection, abilityTypes);
    assertEquals(1, typeGroups.length);
    assertArrayEquals(new TraitType[]{Archery, Melee}, typeGroups[0].getAllGroupTypes());
    assertSame(caste, typeGroups[0].getGroupId());
  }
}