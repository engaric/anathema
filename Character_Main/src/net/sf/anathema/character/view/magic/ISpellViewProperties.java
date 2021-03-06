package net.sf.anathema.character.view.magic;

import net.sf.anathema.character.generic.framework.magic.view.IMagicLearnProperties;
import net.sf.anathema.lib.gui.AgnosticUIConfiguration;
import net.sf.anathema.lib.util.Identifier;

public interface ISpellViewProperties extends IMagicLearnProperties {

  String getCircleLabel();

  AgnosticUIConfiguration<Identifier> getCircleSelectionRenderer();
}