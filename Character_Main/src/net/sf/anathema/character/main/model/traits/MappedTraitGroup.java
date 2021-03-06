package net.sf.anathema.character.main.model.traits;

import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.character.generic.traits.groups.IIdentifiedTraitTypeGroup;
import net.sf.anathema.character.library.trait.Trait;
import net.sf.anathema.character.library.trait.TraitGroup;
import net.sf.anathema.lib.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class MappedTraitGroup implements TraitGroup {
  private final TraitMap traitMap;
  private final IIdentifiedTraitTypeGroup typeGroup;

  public MappedTraitGroup(TraitMap traitMap, IIdentifiedTraitTypeGroup typeGroup) {
    this.traitMap = traitMap;
    this.typeGroup = typeGroup;
  }

  @Override
  public Trait[] getGroupTraits() {
    List<Trait> traits = new ArrayList<>();
    for (TraitType type : typeGroup.getAllGroupTypes()) {
      traits.add(traitMap.getTrait(type));
    }
    return traits.toArray(new Trait[traits.size()]);
  }

  @Override
  public Identifier getGroupId() {
    return typeGroup.getGroupId();
  }
}
