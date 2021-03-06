package net.sf.anathema.hero.languages.sheet.encoder;

import net.sf.anathema.character.reporting.pdf.rendering.general.LineFillingContentEncoder;
import net.sf.anathema.hero.languages.sheet.content.LanguagesContent;

public class LanguagesEncoder extends LineFillingContentEncoder<LanguagesContent> {

  public LanguagesEncoder() {
    super(LanguagesContent.class);
  }
}
