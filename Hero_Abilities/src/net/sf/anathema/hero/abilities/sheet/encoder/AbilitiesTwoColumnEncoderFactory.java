package net.sf.anathema.hero.abilities.sheet.encoder;

import net.sf.anathema.character.reporting.pdf.content.BasicContent;
import net.sf.anathema.character.reporting.pdf.rendering.EncoderIds;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.GlobalEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.RegisteredEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.lib.resources.Resources;

@RegisteredEncoderFactory
public class AbilitiesTwoColumnEncoderFactory extends GlobalEncoderFactory {

  public AbilitiesTwoColumnEncoderFactory() {
    super(EncoderIds.ABILITIES_WITH_SPECIALS_TWO_COLUMN);
  }

  @Override
  public ContentEncoder create(Resources resources, BasicContent content) {
    return new AbilitiesTwoColumnEncoder(resources);
  }
}
