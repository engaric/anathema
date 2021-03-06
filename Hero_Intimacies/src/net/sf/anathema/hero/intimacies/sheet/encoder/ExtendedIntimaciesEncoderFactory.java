package net.sf.anathema.hero.intimacies.sheet.encoder;

import net.sf.anathema.character.reporting.pdf.content.BasicContent;
import net.sf.anathema.character.reporting.pdf.rendering.EncoderIds;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.AbstractEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.RegisteredEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.lib.resources.Resources;

@RegisteredEncoderFactory
public class ExtendedIntimaciesEncoderFactory extends AbstractEncoderFactory {

  public ExtendedIntimaciesEncoderFactory() {
    super(EncoderIds.INTIMACIES_EXTENDED);
  }

  @Override
  public ContentEncoder create(Resources resources, BasicContent content) {
    return new ExtendedIntimaciesEncoder();
  }

  @Override
  public boolean supports(BasicContent content) {
    return true;
  }
}
