package net.sf.anathema.character.equipment.impl.reporting.content;

import net.sf.anathema.character.equipment.character.model.IEquipmentPrintModel;
import net.sf.anathema.character.equipment.impl.character.model.stats.CharacterStatsModifiers;
import net.sf.anathema.character.generic.equipment.ICharacterStatsModifiers;
import net.sf.anathema.character.generic.equipment.weapon.IEquipmentStats;
import net.sf.anathema.character.reporting.pdf.content.AbstractSubContent;
import net.sf.anathema.character.reporting.pdf.content.stats.FixedLineStatsContent;
import net.sf.anathema.hero.equipment.EquipmentModel;
import net.sf.anathema.hero.equipment.EquipmentModelFetcher;
import net.sf.anathema.hero.model.Hero;
import net.sf.anathema.lib.resources.Resources;

public abstract class AbstractEquipmentContent<STATS extends IEquipmentStats> extends AbstractSubContent implements FixedLineStatsContent<STATS> {

  private Hero hero;

  public AbstractEquipmentContent(Hero hero, Resources resources) {
    super(resources);
    this.hero = hero;
  }

  protected Hero getHero() {
    return hero;
  }

  protected IEquipmentPrintModel getEquipmentModel() {
    EquipmentModel model = EquipmentModelFetcher.fetch(hero);
    return model.getPrintModel();
  }

  protected ICharacterStatsModifiers getEquipmentModifiers() {
    return CharacterStatsModifiers.extractFromCharacter(hero);
  }
}
