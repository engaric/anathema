package net.sf.anathema.charmtree.builder.stringbuilder;

import net.sf.anathema.character.generic.magic.IMagic;

public interface IMagicTooltipStringBuilder {

  void buildStringForMagic(StringBuilder builder, IMagic magic, Object specialDetails);
}
