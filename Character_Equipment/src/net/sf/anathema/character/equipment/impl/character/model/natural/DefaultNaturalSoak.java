package net.sf.anathema.character.equipment.impl.character.model.natural;

import net.sf.anathema.character.equipment.impl.character.model.stats.AbstractCombatStats;
import net.sf.anathema.character.generic.equipment.weapon.IArmourStats;
import net.sf.anathema.character.generic.health.HealthType;
import net.sf.anathema.character.generic.traits.GenericTrait;
import net.sf.anathema.character.generic.type.ICharacterType;
import net.sf.anathema.lib.util.Identifier;
import net.sf.anathema.lib.util.SimpleIdentifier;

public class DefaultNaturalSoak extends AbstractCombatStats implements IArmourStats, NaturalSoak {

  private final GenericTrait stamina;
  private final ICharacterType characterType;

  public DefaultNaturalSoak(GenericTrait stamina, ICharacterType characterType) {
    this.stamina = stamina;
    this.characterType = characterType;
  }

  @Override
  public Integer getFatigue() {
    return null;
  }

  @Override
  public Integer getHardness(HealthType type) {
    return null;
  }

  @Override
  public Integer getMobilityPenalty() {
    return null;
  }

  @Override
  public Integer getSoak(HealthType type) {
    if (type == HealthType.Aggravated) {
      return null;
    }
    if (!characterType.isEssenceUser() && type == HealthType.Lethal) {
      return 0;
    }
    return getExaltedSoak(type);
  }

  private Integer getExaltedSoak(HealthType type) {
    if (type == HealthType.Lethal) {
        return (stamina.getCurrentValue() / 2);
    }
    else {
      return stamina.getCurrentValue();
    }
  }

  @Override
  public Identifier getName() {
    return new SimpleIdentifier("NaturalSoak");
  }

  @Override
  public String getId() {
    return getName().getId();
  }
}