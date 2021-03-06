package net.sf.anathema.character.reporting.pdf.content.magic;

import com.google.common.base.Function;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.charmtree.builder.MagicDisplayLabeler;
import net.sf.anathema.lib.resources.Resources;

public class CharmPrintNameTransformer implements Function<ICharm, String> {

  private final MagicDisplayLabeler labeler;

  public CharmPrintNameTransformer(Resources resources) {
    this.labeler = new MagicDisplayLabeler(resources);
  }

  @Override
  public String apply(ICharm input) {
    return labeler.getLabelForMagic(input);
  }
}
