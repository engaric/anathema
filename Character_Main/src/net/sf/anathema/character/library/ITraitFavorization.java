package net.sf.anathema.character.library;

import net.sf.anathema.character.generic.caste.CasteType;
import net.sf.anathema.character.library.trait.favorable.FavorableState;
import net.sf.anathema.character.library.trait.favorable.IFavorableStateChangedListener;

public interface ITraitFavorization {

  CasteType[] getCastes();

  void addFavorableStateChangedListener(IFavorableStateChangedListener listener);

  FavorableState getFavorableState();

  boolean isCaste();

  boolean isCasteOrFavored();

  boolean isFavored();

  void setFavorableState(FavorableState state);

  void setFavored(boolean favored);

  void updateFavorableStateToCaste();

  int getMinimalValue();

  void ensureMinimalValue();
}