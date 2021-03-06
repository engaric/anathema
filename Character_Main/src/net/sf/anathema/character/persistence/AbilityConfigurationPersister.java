package net.sf.anathema.character.persistence;

import net.sf.anathema.character.generic.framework.ITraitReference;
import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.character.generic.traits.types.AbilityType;
import net.sf.anathema.character.library.trait.Trait;
import net.sf.anathema.character.library.trait.persistence.TraitPersister;
import net.sf.anathema.character.library.trait.specialties.DefaultTraitReference;
import net.sf.anathema.character.library.trait.specialties.SpecialtiesModel;
import net.sf.anathema.character.library.trait.specialties.Specialty;
import net.sf.anathema.character.library.trait.subtrait.ISubTraitContainer;
import net.sf.anathema.character.main.model.abilities.AbilitiesModel;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.xml.ElementUtilities;
import org.dom4j.Element;

import java.util.List;

import static net.sf.anathema.character.persistence.ICharacterXmlConstants.ATTRIB_FAVORED;
import static net.sf.anathema.character.persistence.ICharacterXmlConstants.ATTRIB_NAME;
import static net.sf.anathema.character.persistence.ICharacterXmlConstants.TAG_ABILITIES;
import static net.sf.anathema.character.persistence.ICharacterXmlConstants.TAG_SPECIALTY;

public class AbilityConfigurationPersister {

  private final TraitPersister persister = new TraitPersister();

  public void save(Element parent, AbilitiesModel abilities, SpecialtiesModel specialties) {
    Element abilitiesElement = parent.addElement(TAG_ABILITIES);
    for (Trait ability : abilities.getAll()) {
      saveAbility(abilitiesElement, ability, specialties);
    }
  }

  private void saveAbility(Element parent, Trait ability, final SpecialtiesModel specialtyConfiguration) {
    TraitType traitType = ability.getType();
    final Element abilityElement = persister.saveTrait(parent, traitType.getId(), ability);
    if (ability.getFavorization().isFavored()) {
      ElementUtilities.addAttribute(abilityElement, ATTRIB_FAVORED, ability.getFavorization().isFavored());
    }
    DefaultTraitReference reference = new DefaultTraitReference(ability);
    saveSpecialties(specialtyConfiguration, abilityElement, reference);
  }

  private void saveSpecialties(SpecialtiesModel specialtyConfiguration, Element abilityElement, ITraitReference reference) {
    for (Specialty specialty : specialtyConfiguration.getSpecialtiesContainer(reference).getSubTraits()) {
      Element specialtyElement = persister.saveTrait(abilityElement, TAG_SPECIALTY, specialty);
      specialtyElement.addAttribute(ATTRIB_NAME, specialty.getName());
    }
  }

  public void load(Element parent, AbilitiesModel abilitiesModel, SpecialtiesModel specialties) throws PersistenceException {
    Element abilitiesElement = ElementUtilities.getRequiredElement(parent, TAG_ABILITIES);
    List<Element> abilityElements = ElementUtilities.elements(abilitiesElement);
    for (Element element : abilityElements) {
      loadAbility(element, abilitiesModel, specialties);
    }
  }

  private void loadAbility(final Element abilityElement, AbilitiesModel abilitiesModel, SpecialtiesModel specialties) throws PersistenceException {
    AbilityType abilityType = AbilityType.valueOf(abilityElement.getName());
    Trait ability = abilitiesModel.getTrait(abilityType);
    persister.restoreTrait(abilityElement, ability);
    boolean favored = ElementUtilities.getBooleanAttribute(abilityElement, ATTRIB_FAVORED, false);
    ability.getFavorization().setFavored(favored);
    loadSpecialties(abilityElement, specialties, new DefaultTraitReference(ability));
  }

  private void loadSpecialties(Element abilityElement, SpecialtiesModel specialtyConfiguration, ITraitReference reference) throws
          PersistenceException {
    List<Element> specialtyElements = ElementUtilities.elements(abilityElement, TAG_SPECIALTY);
    for (Element specialtyElement : specialtyElements) {
      String specialtyName = specialtyElement.attributeValue(ATTRIB_NAME);
      ISubTraitContainer specialtiesContainer = specialtyConfiguration.getSpecialtiesContainer(reference);
      Specialty specialty = specialtiesContainer.addSubTrait(specialtyName);
      persister.restoreTrait(specialtyElement, specialty);
    }
  }
}