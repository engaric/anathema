package net.sf.anathema.hero.othertraits.sheet.willpower.encoder;

import net.sf.anathema.character.reporting.pdf.content.BasicContent;
import net.sf.anathema.character.reporting.pdf.rendering.EncoderIds;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.GlobalEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.RegisteredEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.lib.resources.Resources;

@RegisteredEncoderFactory
public class SimpleWillpowerEncoderFactory extends GlobalEncoderFactory {

  public SimpleWillpowerEncoderFactory() {
    super(EncoderIds.WILLPOWER_SIMPLE);
  }

  @Override
  public ContentEncoder create(Resources resources, BasicContent content) {
    return new SimpleWillpowerEncoder();
  }
}
