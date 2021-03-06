package net.sf.anathema.character.model.advance;

import net.sf.anathema.character.generic.additionaltemplate.HeroModelExperienceCalculator;
import net.sf.anathema.character.main.model.traits.TraitMap;
import net.sf.anathema.character.main.model.traits.TraitModelFetcher;
import net.sf.anathema.character.model.advance.models.AbilityExperienceModel;
import net.sf.anathema.character.model.advance.models.AttributeExperienceModel;
import net.sf.anathema.character.model.advance.models.CharmExperienceModel;
import net.sf.anathema.character.model.advance.models.EssenceExperienceModel;
import net.sf.anathema.character.model.advance.models.MiscellaneousExperienceModel;
import net.sf.anathema.character.model.advance.models.SpellExperienceModel;
import net.sf.anathema.character.model.advance.models.VirtueExperienceModel;
import net.sf.anathema.character.model.advance.models.WillpowerExperienceModel;
import net.sf.anathema.character.presenter.overview.IValueModel;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.hero.points.PointModelFetcher;

import java.util.ArrayList;
import java.util.List;

public class ExperiencePointManagement implements IExperiencePointManagement {

  private final IPointCostCalculator calculator;
  private final Hero hero;
  private final TraitMap traitMap;

  public ExperiencePointManagement(Hero hero) {
    this.hero = hero;
    this.traitMap = TraitModelFetcher.fetch(hero);
    this.calculator = new ExperiencePointCostCalculator(hero.getTemplate().getExperienceCost());
  }

  private IValueModel<Integer> getAbilityModel() {
    return new AbilityExperienceModel(traitMap, calculator, hero);
  }

  @Override
  public List<IValueModel<Integer>> getAllModels() {
    final List<IValueModel<Integer>> allModels = new ArrayList<>();
    // todo (sandra): Sorting
    allModels.add(getAbilityModel());
    allModels.add(getAttributeModel());
    allModels.add(getCharmModel());
    allModels.add(getEssenceModel());
    allModels.add(getSpellModel());
    allModels.add(getVirtueModel());
    allModels.add(getWillpowerModel());
    allModels.add(getMiscModel());
    for (IValueModel<Integer>  model : PointModelFetcher.fetch(hero).getExperienceOverviewModels()) {
      allModels.add(model);
    }
    return allModels;
  }

  private IValueModel<Integer> getAttributeModel() {
    return new AttributeExperienceModel(traitMap, calculator, hero);
  }

  private IValueModel<Integer> getCharmModel() {
    return new CharmExperienceModel(traitMap, calculator, hero);
  }

  private IValueModel<Integer> getEssenceModel() {
    return new EssenceExperienceModel(traitMap, calculator);
  }

  @Override
  public int getMiscGain() {
    int total = 0;
    for (HeroModelExperienceCalculator experienceCalculator : PointModelFetcher.fetch(hero).getExperienceCalculators()) {
      total += experienceCalculator.calculateGain();
    }
    return total;
  }

  private IValueModel<Integer> getMiscModel() {
    return new MiscellaneousExperienceModel(hero);
  }

  private IValueModel<Integer> getSpellModel() {
    return new SpellExperienceModel(hero, calculator, traitMap);
  }

  @Override
  public int getTotalCosts() {
    int experienceCosts = 0;
    for (IValueModel<Integer> model : getAllModels()) {
      experienceCosts += model.getValue();
    }
    return experienceCosts;
  }

  private IValueModel<Integer> getVirtueModel() {
    return new VirtueExperienceModel(traitMap, calculator);
  }

  private IValueModel<Integer> getWillpowerModel() {
    return new WillpowerExperienceModel(traitMap, calculator);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (IValueModel<Integer> model : getAllModels()) {
      builder.append(model.getCategoryId());
      builder.append(": ");
      builder.append(model.getValue());
    }
    builder.append("Overall: ");
    builder.append(getTotalCosts());
    return builder.toString();
  }
}