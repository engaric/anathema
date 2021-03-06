package net.sf.anathema.character.model.creation.bonus.ability;

import net.sf.anathema.character.generic.template.creation.ICreationPoints;
import net.sf.anathema.character.library.trait.IFavorableTraitCostCalculator;
import net.sf.anathema.character.model.advance.models.AbstractSpendingModel;

public class FavoredAbilityBonusModel extends AbstractSpendingModel {
  private final IFavorableTraitCostCalculator abilityCalculator;
  private final ICreationPoints creationPoints;

  public FavoredAbilityBonusModel(IFavorableTraitCostCalculator abilityCalculator, ICreationPoints creationPoints) {
    super("Abilities", "FavoredDot");
    this.abilityCalculator = abilityCalculator;
    this.creationPoints = creationPoints;
  }

  @Override
  public Integer getValue() {
    return abilityCalculator.getFreePointsSpent(true);
  }

  @Override
  public int getSpentBonusPoints() {
    return 0;
  }

  @Override
  public int getAlotment() {
    return creationPoints.getAbilityCreationPoints().getFavoredDotCount();
  }
}