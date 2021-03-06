package net.sf.anathema.character.reporting.pdf.rendering.boxes.social;

import net.sf.anathema.character.reporting.pdf.content.BasicContent;
import net.sf.anathema.character.reporting.pdf.rendering.EncoderIds;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.AbstractEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.RegisteredEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.lib.resources.Resources;

@RegisteredEncoderFactory
public class SocialCombatEncoderFactory extends AbstractEncoderFactory {

  public SocialCombatEncoderFactory() {
    super(EncoderIds.SOCIAL_COMBAT);
  }

  @Override
  public ContentEncoder create(Resources resources, BasicContent content) {
    return new SocialCombatStatsBoxEncoder(resources);
  }

  @Override
  public boolean supports(BasicContent content) {
    return true;
  }
}
