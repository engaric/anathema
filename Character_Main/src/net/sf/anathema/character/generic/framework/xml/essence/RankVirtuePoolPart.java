package net.sf.anathema.character.generic.framework.xml.essence;

import net.sf.anathema.character.generic.impl.traits.ValueWeightGenericTraitSorter;
import net.sf.anathema.character.generic.template.essence.FactorizedTrait;
import net.sf.anathema.character.generic.traits.GenericTrait;
import org.apache.commons.lang3.builder.EqualsBuilder;

import java.util.List;

public class RankVirtuePoolPart implements IVirtuePoolPart {

  private final int ranks;
  private final int virtueMultiplier;

  public RankVirtuePoolPart(int ranks, int virtueMultiplier) {
    this.ranks = ranks;
    this.virtueMultiplier = virtueMultiplier;
  }

  @Override
  public FactorizedTrait createFactorizedTrait(GenericTrait[] virtues) {
    List<GenericTrait> sortedVirtues = new ValueWeightGenericTraitSorter().sortDescending(virtues);
    GenericTrait[] topVirtues = new GenericTrait[ranks];
    for (int index = 0; index < ranks; index++) {
      topVirtues[index] = sortedVirtues.get(index);
    }
    return new FactorizedTrait(topVirtues, virtueMultiplier);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

  @Override
  public int hashCode() {
    return ranks + 7 * virtueMultiplier;
  }
}