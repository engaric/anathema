package net.sf.anathema.character.equipment.character.view;

import net.sf.anathema.framework.swing.IView;
import net.sf.anathema.interaction.Tool;
import net.sf.anathema.lib.gui.selection.IListObjectSelectionView;

public interface IEquipmentAdditionalView extends IView {

  IListObjectSelectionView<String> getEquipmentTemplatePickList();

  IEquipmentObjectView addEquipmentObjectView();

  void removeEquipmentObjectView(IEquipmentObjectView objectView);

  Tool addToolButton();

  IMagicalMaterialView getMagicMaterialView();

  void revalidateEquipmentViews();
}