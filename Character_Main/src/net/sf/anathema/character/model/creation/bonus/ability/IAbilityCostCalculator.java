package net.sf.anathema.character.model.creation.bonus.ability;

import net.sf.anathema.character.library.trait.IFavorableTraitCostCalculator;

public interface IAbilityCostCalculator extends IFavorableTraitCostCalculator {

  int getSpecialtyBonusPointCosts();

  int getFreeSpecialtyPointsSpent();
}