package net.sf.anathema.character.generic.impl.magic.persistence.builder.prerequisite;

import net.sf.anathema.character.generic.traits.GenericTrait;
import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.lib.exception.PersistenceException;
import net.sf.anathema.lib.xml.ElementUtilities;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

import static net.sf.anathema.character.generic.impl.magic.ICharmXMLConstants.TAG_TRAIT;

public class GenericTraitPrerequisitesBuilder implements ITraitPrerequisitesBuilder {

  private final GenericTraitPrerequisiteBuilder genericBuilder = new GenericTraitPrerequisiteBuilder();

  @Override
  public GenericTrait[] buildTraitPrerequisites(Element prerequisiteListElement) throws PersistenceException {
    List<GenericTrait> allPrerequisites = new ArrayList<>();
    ITraitPrerequisiteBuilder traitBuilder = new TraitPrerequisiteBuilder();
    List<Element> elements = ElementUtilities.elements(prerequisiteListElement, TAG_TRAIT);
    try {
      allPrerequisites.add(genericBuilder.build(elements.get(0)));
      for (Element element : elements.subList(1, elements.size())) {
        allPrerequisites.add(traitBuilder.build(element));
      }
    } catch (Exception e) {
      throw new PersistenceException(e);
    }
    return allPrerequisites.toArray(new GenericTrait[allPrerequisites.size()]);
  }

  public void setType(TraitType type) {
    genericBuilder.setType(type);
  }
}