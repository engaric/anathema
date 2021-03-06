package net.sf.anathema.character.presenter.magic.combo;

import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.charmtree.builder.MagicDisplayLabeler;
import net.sf.anathema.charmtree.builder.stringbuilder.ICharmInfoStringBuilder;
import net.sf.anathema.lib.gui.AbstractUIConfiguration;
import net.sf.anathema.lib.resources.Resources;
import net.sf.anathema.lib.util.Identifier;

public class CharmUIConfiguration extends AbstractUIConfiguration<Identifier> {
  private final Resources resources;
  private final ICharmInfoStringBuilder charmInfoStringProvider;

  public CharmUIConfiguration(Resources resources, ICharmInfoStringBuilder charmInfoStringProvider) {
    this.resources = resources;
    this.charmInfoStringProvider = charmInfoStringProvider;
  }

  @Override
  public String getLabel(Identifier value) {
    return new MagicDisplayLabeler(resources).getLabelForMagic((ICharm) value);
  }

  @Override
  public String getToolTipText(Identifier value) {
    return charmInfoStringProvider.getInfoString((ICharm) value, null);
  }
}