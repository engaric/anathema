package net.sf.anathema.lib.gui.dialog.userdialog;

import net.sf.anathema.lib.gui.action.ActionConfiguration;
import net.sf.anathema.lib.gui.action.IActionConfiguration;
import net.sf.anathema.lib.gui.action.SmartAction;
import net.sf.anathema.lib.gui.dialog.core.AbstractDialog;
import net.sf.anathema.lib.gui.dialog.core.DialogButtonBarBuilder;
import net.sf.anathema.lib.gui.dialog.core.DialogResult;
import net.sf.anathema.lib.gui.dialog.core.IDialogContainer;
import net.sf.anathema.lib.gui.dialog.userdialog.buttons.IDialogButtonConfiguration;
import net.sf.anathema.lib.gui.dialog.userdialog.page.IDialogPage;
import net.sf.anathema.lib.gui.swing.GuiUtilities;
import net.sf.anathema.lib.gui.swing.RelativePosition;

import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.sf.anathema.lib.gui.dialog.core.StaticDialogResult.Confirmed;

public class UserDialog extends AbstractDialog implements IDialogContainer {

  private final DialogPageControl dialogControl;
  private JButton okButton;
  private boolean neverVisualized = true;
  private final RelativePosition relativePosition;

  public UserDialog(Component parentComponent, IDialogConfiguration<? extends IDialogPage> userDialog) {
    this(parentComponent, userDialog, RelativePosition.CENTER);
  }

  public UserDialog(Component parentComponent, IDialogConfiguration<?> dialogConfiguration,
                    RelativePosition relativePosition) {
    super(parentComponent, dialogConfiguration);
    dialogControl = new DialogPageControl(dialogConfiguration.getDialogPage());
    dialogControl.setDialogControl(this);
    initializeContent();
    setContent(dialogControl.getContent());
    updateAll();
    this.relativePosition = relativePosition;
  }

  public UserDialog(Component parentComponent, IDialogPage dialogPage) {
    this(parentComponent, DefaultDialogConfiguration.createWithOkAndCancel(dialogPage));
  }

  protected IDialogConfiguration<?> getConfiguration() {
    return (IDialogConfiguration<?>) getGenericDialog();
  }

  private void updateAll() {
    updateDescription();
    updateTitle();
    updateMessage();
    updateButtons();
  }

  @Override
  public void updateDescription() {
    setDescription(getDialogControl().getDescription());
  }

  @Override
  public void updateTitle() {
    setTitle(getDialogControl().getTitle());
  }

  @Override
  public void updateMessage() {
    setMessage(getDialogControl().getMessage());
  }

  @Override
  public void updateButtons() {
    okButton.setEnabled(getDialogControl().canFinish());
  }

  @Override
  protected final JComponent createButtonBar() {
    JComponent[] buttons = createButtons();
    if (buttons.length > 0 && buttons[0] instanceof JButton) {
      setDefaultButton((JButton) buttons[0]);
    }
    DialogButtonBarBuilder buttonBarBuilder = new DialogButtonBarBuilder();
    buttonBarBuilder.addButtons(buttons);
    return buttonBarBuilder.createButtonBar();
  }

  private JComponent[] createButtons() {
    IDialogButtonConfiguration buttonConfiguration = getConfiguration().getButtonConfiguration();
    final IActionConfiguration okActionConfiguration = buttonConfiguration.getOkActionConfiguration();

    SmartAction okAction = new SmartAction(
            okActionConfiguration != null ? okActionConfiguration : new ActionConfiguration()) {
      @Override
      protected void execute(Component parentComponent) {
        requestFinish();
      }
    };

    okButton = new JButton(okAction);

    final IActionConfiguration cancelActionConfiguration = buttonConfiguration.getCancelActionConfiguration();
    SmartAction cancelAction = new SmartAction(
            cancelActionConfiguration != null ? cancelActionConfiguration : new ActionConfiguration()) {
      @Override
      protected void execute(Component parentComponent) {
        performCancel();
      }
    };
    JButton cancelButton = new JButton(cancelAction);

    List<JComponent> buttonList = new ArrayList<>();
    if (okActionConfiguration != null) {
      buttonList.add(okButton);
    }

    buttonList.addAll(Arrays.asList(createAdditionalButtons()));

    if (cancelActionConfiguration != null) {
      buttonList.add(cancelButton);
    }
    return buttonList.toArray(new JComponent[buttonList.size()]);
  }

  protected JComponent[] createAdditionalButtons() {
    return new JComponent[0];
  }

  public DialogPageControl getDialogControl() {
    return dialogControl;
  }

  private void setVisible(boolean visible) {
    if (visible) {
      if (neverVisualized) {
        placeRelativeToOwner();
        neverVisualized = false;
      }
      getDialog().show();
    } else {
      closeDialog();
    }
  }

  private void placeRelativeToOwner() {
    GuiUtilities.placeRelativeToOwner(getDialog().getWindow(), relativePosition);
  }

  @Override
  public DialogResult show() {
    setVisible(true);
    return createDialogResult();
  }

  public void show(DialogCloseHandler dialogCloseHandler) {
    setCloseHandler(dialogCloseHandler);
    show();
  }

  @Override
  public final void requestFinish() {
    GuiUtilities.stopCellEditing(getDialog().getContentPane());
    if (!getDialogControl().canFinish()) {
      return;
    }
    closeDialog();
    getCloseHandler().handleDialogClose(Confirmed());
  }
}