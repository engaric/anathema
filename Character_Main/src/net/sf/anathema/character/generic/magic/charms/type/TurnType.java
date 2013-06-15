package net.sf.anathema.character.generic.magic.charms.type;

import net.sf.anathema.lib.util.Identifier;

public enum TurnType implements Identifier {

  Tick, LongTick, DramaticAction;

  @Override
  public String getId() {
    return name();
  }
}