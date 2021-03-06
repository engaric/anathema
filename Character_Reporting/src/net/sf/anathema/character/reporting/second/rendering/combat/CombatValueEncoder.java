package net.sf.anathema.character.reporting.second.rendering.combat;

import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Position;
import net.sf.anathema.character.reporting.pdf.rendering.general.LabelledValueEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.IContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;
import net.sf.anathema.character.reporting.second.content.combat.CombatStatsContent;

public class CombatValueEncoder implements IContentEncoder {

  @Override
  public float encode(SheetGraphics graphics, ReportSession reportSession, Bounds bounds) {
    CombatStatsContent content = createContent(reportSession);
    Position upperLeft = new Position(bounds.x, bounds.getMaxY());
    LabelledValueEncoder encoder = new LabelledValueEncoder(4, upperLeft, bounds.width, 3);
    displayJoinBattleWithSpecialty(graphics, encoder, content);
    displayDodgeWithSpecialty(graphics, encoder, content);
    encoder.addLabelledValue(graphics, 2, content.getKnockdownLabel(), content.getKnockdownThreshold(), content.getKnockdownPool());
    encoder.addLabelledValue(graphics, 3, content.getStunningLabel(), content.getStunningThreshold(), content.getStunningPool());
    encoder.addComment(graphics, content.getThresholdPoolLabel(), 2);
    encoder.addComment(graphics, content.getThresholdPoolLabel(), 3);
    return encoder.getHeight();
  }

  private CombatStatsContent createContent(ReportSession session) {
    return session.createContent(CombatStatsContent.class);
  }

  private void displayJoinBattleWithSpecialty(SheetGraphics graphics, LabelledValueEncoder encoder, CombatStatsContent content) {
    if (content.getJoinBattle() != content.getJoinBattleWithSpecialty()) {
      encoder.addLabelledValue(graphics, 0, content.getJoinLabel(), content.getJoinBattle(), content.getJoinBattleWithSpecialty());
      encoder.addComment(graphics, content.getJoinBattleSpecialtyLabel(), 0);
    } else {
      encoder.addLabelledValue(graphics, 0, content.getJoinLabel(), content.getJoinBattle());
    }
  }

  private void displayDodgeWithSpecialty(SheetGraphics graphics, LabelledValueEncoder encoder, CombatStatsContent content) {
    if (content.getDodgeDv() != content.getDodgeDvWithSpecialty()) {
      encoder.addLabelledValue(graphics, 1, content.getDodgeLabel(), content.getDodgeDv(), content.getDodgeDvWithSpecialty());
      encoder.addComment(graphics, content.getDodgeSpecialtyLabel(), 1);
    } else {
      encoder.addLabelledValue(graphics, 1, content.getDodgeLabel(), content.getDodgeDv());
    }
  }
}
