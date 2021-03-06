package net.sf.anathema.hero.othertraits.sheet.willpower.encoder;

import net.sf.anathema.character.generic.traits.types.OtherTraitType;
import net.sf.anathema.character.reporting.pdf.content.BasicContent;
import net.sf.anathema.character.reporting.pdf.rendering.EncoderIds;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.AbstractEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.RegisteredEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.general.DotBoxContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.lib.resources.Resources;

@RegisteredEncoderFactory
public class WillpowerDotEncoderFactory extends AbstractEncoderFactory {

  public WillpowerDotEncoderFactory() {
    super(EncoderIds.WILLPOWER_DOTS);
  }

  @Override
  public ContentEncoder create(Resources resources, BasicContent content) {
    return new DotBoxContentEncoder(OtherTraitType.Willpower, 10, resources, "Willpower");
  }

  @Override
  public boolean supports(BasicContent content) {
    return true;
  }
}
