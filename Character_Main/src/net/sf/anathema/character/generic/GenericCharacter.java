package net.sf.anathema.character.generic;

import net.sf.anathema.character.generic.caste.CasteType;
import net.sf.anathema.character.generic.character.IConcept;
import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericDescription;
import net.sf.anathema.character.generic.character.IGenericTraitCollection;
import net.sf.anathema.character.generic.health.HealthLevelType;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.IGenericCombo;
import net.sf.anathema.character.generic.magic.IMagic;
import net.sf.anathema.character.generic.magic.IMagicVisitor;
import net.sf.anathema.character.generic.magic.ISpell;
import net.sf.anathema.character.generic.magic.charms.special.ISpecialCharmConfiguration;
import net.sf.anathema.character.generic.magic.charms.special.ISubeffect;
import net.sf.anathema.character.generic.template.HeroTemplate;
import net.sf.anathema.character.generic.template.ITraitLimitation;
import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.character.generic.traits.groups.IIdentifiedTraitTypeGroup;
import net.sf.anathema.character.library.trait.specialties.SpecialtiesModel;
import net.sf.anathema.character.library.trait.specialties.SpecialtiesModelFetcher;
import net.sf.anathema.character.library.trait.specialties.Specialty;
import net.sf.anathema.character.main.model.abilities.AbilityModelFetcher;
import net.sf.anathema.character.main.model.attributes.AttributesModelFetcher;
import net.sf.anathema.character.main.model.charms.CharmsModel;
import net.sf.anathema.character.main.model.charms.CharmsModelFetcher;
import net.sf.anathema.character.main.model.combos.CombosModelFetcher;
import net.sf.anathema.character.main.model.concept.HeroConcept;
import net.sf.anathema.character.main.model.concept.HeroConceptFetcher;
import net.sf.anathema.character.main.model.description.HeroDescriptionFetcher;
import net.sf.anathema.character.main.model.essencepool.EssencePoolModelFetcher;
import net.sf.anathema.character.main.model.experience.ExperienceModelFetcher;
import net.sf.anathema.character.main.model.health.HealthModelFetcher;
import net.sf.anathema.character.main.model.othertraits.OtherTraitModelFetcher;
import net.sf.anathema.character.main.model.spells.SpellsModelFetcher;
import net.sf.anathema.character.main.model.traits.GenericTraitCollectionFacade;
import net.sf.anathema.character.main.model.traits.TraitMap;
import net.sf.anathema.character.main.model.traits.TraitModelFetcher;
import net.sf.anathema.character.model.ITypedDescription;
import net.sf.anathema.character.model.advance.ExperiencePointManagement;
import net.sf.anathema.character.model.charm.ICombo;
import net.sf.anathema.character.model.charm.ILearningCharmGroup;
import net.sf.anathema.character.model.charm.special.IMultipleEffectCharmConfiguration;
import net.sf.anathema.character.model.charm.special.ISubeffectCharmConfiguration;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.lib.exception.ContractFailedException;
import net.sf.anathema.lib.util.IdentifiedInteger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenericCharacter implements IGenericCharacter {

  private final Hero hero;

  public GenericCharacter(Hero hero) {
    this.hero = hero;
  }

  @Override
  public IGenericTraitCollection getTraitCollection() {
    return new GenericTraitCollectionFacade(getTraitMap());
  }

  @Override
  public boolean isLearned(IMagic magic) {
    final boolean[] isLearned = new boolean[1];
    magic.accept(new IMagicVisitor() {
      @Override
      public void visitSpell(ISpell spell) {
        isLearned[0] = SpellsModelFetcher.fetch(hero).isLearned(spell);
      }

      @Override
      public void visitCharm(ICharm charm) {
        isLearned[0] = CharmsModelFetcher.fetch(hero).isLearned(charm);
      }
    });
    return isLearned[0];
  }

  @Override
  public IGenericDescription getDescription() {
    return new GenericDescription(HeroDescriptionFetcher.fetch(hero));
  }

  @Override
  public boolean isAlienCharm(ICharm charm) {
    return CharmsModelFetcher.fetch(hero).isAlienCharm(charm);
  }

  @Override
  public HeroTemplate getTemplate() {
    return hero.getTemplate();
  }

  @Override
  public Specialty[] getSpecialties(TraitType traitType) {
    SpecialtiesModel specialtyConfiguration = SpecialtiesModelFetcher.fetch(hero);
    return specialtyConfiguration.getSpecialtiesContainer(traitType).getSubTraits();
  }

  @Override
  public CasteType getCasteType() {
    HeroConcept heroConcept = HeroConceptFetcher.fetch(hero);
    ITypedDescription<CasteType> caste = heroConcept.getCaste();
    return caste.getType();
  }

  @Override
  public int getHealthLevelTypeCount(HealthLevelType type) {
    return HealthModelFetcher.fetch(hero).getHealthLevelTypeCount(type);
  }

  @Override
  public String getPeripheralPool() {
    try {
      return getTemplate().getEssenceTemplate().isEssenceUser() ? EssencePoolModelFetcher.fetch(hero).getPeripheralPool() : null;
    } catch (IllegalArgumentException e) {
      return null;
    }
  }

  @Override
  public int getPeripheralPoolValue() {
    return getTemplate().getEssenceTemplate().isEssenceUser() ? EssencePoolModelFetcher.fetch(hero).getPeripheralPoolValue() : 0;
  }

  @Override
  public String getPersonalPool() {
    try {
      return getTemplate().getEssenceTemplate().isEssenceUser() ? EssencePoolModelFetcher.fetch(hero).getPersonalPool() : null;
    } catch (ContractFailedException e) {
      return null;
    }
  }

  @Override
  public int getPersonalPoolValue() {
    return getTemplate().getEssenceTemplate().isEssenceUser() ? EssencePoolModelFetcher.fetch(hero).getPersonalPoolValue() : 0;
  }

  @Override
  public int getOverdrivePoolValue() {
    return getTemplate().getEssenceTemplate().isEssenceUser() ? EssencePoolModelFetcher.fetch(hero).getOverdrivePoolValue() : 0;
  }

  @Override
  public IdentifiedInteger[] getComplexPools() {
    if (getTemplate().getEssenceTemplate().isEssenceUser()) {
      return EssencePoolModelFetcher.fetch(hero).getComplexPools();
    } else {
      return new IdentifiedInteger[0];
    }
  }

  @Override
  public int getAttunedPoolValue() {
    return getTemplate().getEssenceTemplate().isEssenceUser() ? EssencePoolModelFetcher.fetch(hero).getAttunedPoolValue() : 0;
  }

  @Override
  public IConcept getConcept() {
    return new GenericConcept(HeroConceptFetcher.fetch(hero));
  }

  @Override
  public List<IMagic> getAllLearnedMagic() {
    List<IMagic> magicList = new ArrayList<>();
    magicList.addAll(Arrays.asList(getLearnedCharms()));
    boolean experienced = ExperienceModelFetcher.fetch(hero).isExperienced();
    magicList.addAll(Arrays.asList(SpellsModelFetcher.fetch(hero).getLearnedSpells(experienced)));
    return magicList;
  }

  @Override
  public int getLearnCount(ICharm charm) {
    return getLearnCount(charm, CharmsModelFetcher.fetch(hero));
  }

  private int getLearnCount(ICharm charm, CharmsModel configuration) {
    ISpecialCharmConfiguration specialCharmConfiguration = configuration.getSpecialCharmConfiguration(charm.getId());
    if (specialCharmConfiguration != null) {
      return specialCharmConfiguration.getCurrentLearnCount();
    }
    return configuration.isLearned(charm) ? 1 : 0;
  }

  @Override
  public IGenericCombo[] getCombos() {
    List<IGenericCombo> genericCombos = new ArrayList<>();
    for (ICombo combo : CombosModelFetcher.fetch(hero).getAllCombos()) {
      genericCombos.add(new GenericCombo(combo));
    }
    return genericCombos.toArray(new IGenericCombo[genericCombos.size()]);
  }

  @Override
  public boolean isExperienced() {
    return ExperienceModelFetcher.fetch(hero).isExperienced();
  }

  @Override
  public int getPainTolerance() {
    return HealthModelFetcher.fetch(hero).getPainToleranceLevel();
  }

  @Override
  public ITraitLimitation getEssenceLimitation() {
    return OtherTraitModelFetcher.fetch(hero).getEssenceLimitation();
  }

  @Override
  public IIdentifiedTraitTypeGroup[] getAbilityTypeGroups() {
    return AbilityModelFetcher.fetch(hero).getAbilityTypeGroups();
  }

  @Override
  public IIdentifiedTraitTypeGroup[] getAttributeTypeGroups() {
    return AttributesModelFetcher.fetch(hero).getAttributeTypeGroups();
  }

  @Override
  public int getSpentExperiencePoints() {
    return new ExperiencePointManagement(hero).getTotalCosts();
  }

  @Override
  public int getTotalExperiencePoints() {
    return ExperienceModelFetcher.fetch(hero).getExperiencePoints().getTotalExperiencePoints();
  }

  @Override
  public boolean isSubeffectCharm(ICharm charm) {
    ISpecialCharmConfiguration charmConfiguration = CharmsModelFetcher.fetch(hero).getSpecialCharmConfiguration(charm);
    return charmConfiguration instanceof ISubeffectCharmConfiguration;
  }

  @Override
  public boolean isMultipleEffectCharm(ICharm charm) {
    ISpecialCharmConfiguration charmConfiguration = CharmsModelFetcher.fetch(hero).getSpecialCharmConfiguration(charm);
    return charmConfiguration instanceof IMultipleEffectCharmConfiguration && !(charmConfiguration instanceof ISubeffectCharmConfiguration);
  }

  @Override
  public String[] getLearnedEffects(ICharm charm) {
    ISpecialCharmConfiguration charmConfiguration = CharmsModelFetcher.fetch(hero).getSpecialCharmConfiguration(charm);
    if (!(charmConfiguration instanceof IMultipleEffectCharmConfiguration)) {
      return new String[0];
    }
    IMultipleEffectCharmConfiguration configuration = (IMultipleEffectCharmConfiguration) charmConfiguration;
    List<String> learnedEffectIds = new ArrayList<>();
    for (ISubeffect effect : configuration.getEffects()) {
      if (effect.isLearned()) {
        learnedEffectIds.add(effect.getId());
      }
    }
    return learnedEffectIds.toArray(new String[learnedEffectIds.size()]);
  }

  @Override
  public ICharm[] getGenericCharms() {
    List<ICharm> genericCharms = new ArrayList<>();
    for (ILearningCharmGroup group : CharmsModelFetcher.fetch(hero).getAllGroups()) {
      for (ICharm charm : group.getAllCharms()) {
        if (charm.isInstanceOfGenericCharm() &&
            charm.getCharacterType().equals(hero.getTemplate().getTemplateType().getCharacterType())) {
          genericCharms.add(charm);
        }
      }
    }
    return genericCharms.toArray(new ICharm[genericCharms.size()]);
  }

  private ICharm[] getLearnedCharms() {
    boolean experienced = ExperienceModelFetcher.fetch(hero).isExperienced();
    return CharmsModelFetcher.fetch(hero).getLearnedCharms(experienced);
  }

  private TraitMap getTraitMap() {
    return TraitModelFetcher.fetch(hero);
  }
}