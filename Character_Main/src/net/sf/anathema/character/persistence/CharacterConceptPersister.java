package net.sf.anathema.character.persistence;

import net.sf.anathema.character.generic.caste.CasteType;
import net.sf.anathema.character.generic.caste.ICasteCollection;
import net.sf.anathema.character.main.model.concept.HeroConcept;
import net.sf.anathema.character.main.model.description.HeroDescription;
import net.sf.anathema.character.model.IIntegerDescription;
import net.sf.anathema.character.model.ITypedDescription;
import net.sf.anathema.framework.persistence.TextPersister;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.xml.ElementUtilities;
import org.dom4j.Element;

import static net.sf.anathema.character.persistence.ICharacterXmlConstants.ATTRIB_AGE;
import static net.sf.anathema.character.persistence.ICharacterXmlConstants.ATTRIB_TYPE;
import static net.sf.anathema.character.persistence.ICharacterXmlConstants.TAG_CASTE;
import static net.sf.anathema.character.persistence.ICharacterXmlConstants.TAG_CHARACTER_CONCEPT;
import static net.sf.anathema.character.persistence.ICharacterXmlConstants.TAG_CONCEPT;

public class CharacterConceptPersister {

  private final TextPersister textPersister = new TextPersister();

  public void save(Element parent, HeroConcept heroConcept) {
    Element characterConceptElement = parent.addElement(TAG_CHARACTER_CONCEPT);
    saveCaste(characterConceptElement, heroConcept.getCaste());
    saveAge(characterConceptElement, heroConcept.getAge());
  }

  private void saveCaste(Element parent, ITypedDescription<CasteType> caste) {
    CasteType casteType = caste.getType();
    if (casteType.getId() != null) {
      Element casteElement = parent.addElement(TAG_CASTE);
      casteElement.addAttribute(ATTRIB_TYPE, casteType.getId());
    }
  }

  private void saveAge(Element parent, IIntegerDescription age) {
    parent.addAttribute(ATTRIB_AGE, Integer.toString(age.getValue()));
  }

  public void load(Element parent, HeroConcept heroConcept, HeroDescription description, ICasteCollection casteCollection) throws
          PersistenceException {
    Element conceptElement = parent.element(TAG_CHARACTER_CONCEPT);
    loadCaste(conceptElement, heroConcept, casteCollection);
    loadAge(conceptElement, heroConcept);
    textPersister.restoreTextualDescription(conceptElement, TAG_CONCEPT, description.getConcept());
  }

  private void loadCaste(Element parent, HeroConcept heroConcept, ICasteCollection casteCollection) throws PersistenceException {
    Element casteElement = parent.element(TAG_CASTE);
    if (casteElement == null) {
      return;
    }
    String casteTypeId = ElementUtilities.getRequiredAttrib(casteElement, ATTRIB_TYPE);
    heroConcept.getCaste().setType(casteCollection.getById(casteTypeId));
  }

  private void loadAge(Element parent, HeroConcept heroConcept) throws PersistenceException {
    int age = ElementUtilities.getIntAttrib(parent, ATTRIB_AGE, 0);
    heroConcept.getAge().setValue(age);
  }
}