package net.sf.anathema.framework.styledtext.model;

import net.disy.commons.core.text.FontStyle;

import java.awt.Color;

public interface ITextFormat {

  public boolean isUnderline();

  public FontStyle getFontStyle();

  public Integer getFontSize();

  public String getFontName();

  public Color getColor();

  public boolean isStyled();
}