package net.sf.anathema.character.equipment.impl.character.model.natural.secondedition;

import net.sf.anathema.character.equipment.impl.character.model.natural.AbstractNaturalWeaponStats;
import net.sf.anathema.character.equipment.impl.creation.model.WeaponTag;
import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.character.generic.traits.types.AbilityType;
import net.sf.anathema.character.generic.traits.types.AttributeType;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;

public class Clinch extends AbstractNaturalWeaponStats {

  @Override
  public int getAccuracy() {
    return 0;
  }

  @Override
  public int getDamage() {
    return 0;
  }

  @Override
  public Integer getDefence() {
    return 0;
  }

  @Override
  public int getMinimumDamage() {
    return 1;
  }

  @Override
  public Integer getRate() {
    return 1;
  }

  @Override
  public int getSpeed() {
    return 6;
  }

  @Override
  public Identifier[] getTags() {
    return new Identifier[]{WeaponTag.ClinchEnhancer, WeaponTag.Natural, WeaponTag.Piercing};
  }

  @Override
  public TraitType getTraitType() {
    return AbilityType.MartialArts;
  }

  @Override
  public TraitType getDamageTraitType() {
    return AttributeType.Strength;
  }

  @Override
  public boolean inflictsNoDamage() {
    return false;
  }

  @Override
  public Identifier getName() {
    return new SimpleIdentifier("Clinch");
  }
}