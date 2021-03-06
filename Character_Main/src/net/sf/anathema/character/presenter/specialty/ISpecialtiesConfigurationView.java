package net.sf.anathema.character.presenter.specialty;

import net.sf.anathema.character.generic.framework.ITraitReference;
import net.sf.anathema.character.presenter.ExtensibleTraitView;
import net.sf.anathema.framework.presenter.view.IButtonControlledComboEditView;
import net.sf.anathema.lib.file.RelativePath;

import javax.swing.ListCellRenderer;

public interface ISpecialtiesConfigurationView {

  ExtensibleTraitView addSpecialtyView(String abilityName, String specialtyName, RelativePath deleteIcon, int value, int maxValue);

  IButtonControlledComboEditView<ITraitReference> addSpecialtySelectionView(String labelText, ListCellRenderer renderer, RelativePath addIcon);
}