package net.sf.anathema.character.view;

import net.sf.anathema.character.library.trait.Trait;
import net.sf.anathema.framework.value.IIntValueView;
import net.sf.anathema.lib.workflow.labelledvalue.IValueView;

public interface AdvantageView {

  void initGui(AdvantageViewProperties properties);

  IIntValueView addVirtue(String labelText, int value, int maxValue);

  IIntValueView addWillpower(String labelText, int value, int maxValue);

  IIntValueView addEssenceView(String labelText, int value, int maxValue, Trait trait);

  IValueView<String> addPoolView(String labelText, String value);
}